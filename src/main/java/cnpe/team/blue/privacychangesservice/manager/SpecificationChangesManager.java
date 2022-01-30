package cnpe.team.blue.privacychangesservice.manager;

import cnpe.team.blue.privacychangesservice.dto.SpecificationChanges;
import cnpe.team.blue.privacychangesservice.repository.SpecificationChangesRepository;
import cnpe.team.blue.privacychangesservice.service.FilterService;
import cnpe.team.blue.privacychangesservice.service.GitHubIssueService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

@Component
@EnableScheduling
public class SpecificationChangesManager {

    private LocalDateTime lastCheck;

    @Autowired
    private FilterService filterService;

    @Autowired
    private GitHubIssueService gitHubIssueService;

    @Autowired
    private SpecificationChangesRepository specificationChangesRepository;

    @Autowired
    public SpecificationChangesManager(@Value("${last-checked-time}") String lastCheckedTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.lastCheck = LocalDateTime.parse(lastCheckedTime, formatter);
    }


    @Scheduled(fixedDelayString = "${app.check-interval-ms}")
    public void processSpecificationChanges() throws IOException {
        Collection<SpecificationChanges> latestSpecificationChanges = specificationChangesRepository.findLatestChanges(Timestamp.valueOf(lastCheck));

        for (SpecificationChanges specificationChanges: latestSpecificationChanges) {
            Collection<JsonObject> privacyRelatedChanges = filterService.filterPrivacySpecificationChanges(specificationChanges);
            if (!privacyRelatedChanges.isEmpty()) {
                gitHubIssueService.createIssue(specificationChanges.getServiceName(), specificationChanges.getCommit(), privacyRelatedChanges);
            }

            if (specificationChanges.getCreated().toLocalDateTime().compareTo(lastCheck) > 0) {
                lastCheck = specificationChanges.getCreated().toLocalDateTime();
            }
        }
    }
}
