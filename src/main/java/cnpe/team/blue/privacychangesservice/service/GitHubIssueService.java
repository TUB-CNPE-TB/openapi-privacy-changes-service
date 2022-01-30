package cnpe.team.blue.privacychangesservice.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
@PropertySource("classpath:application.yml")
public class GitHubIssueService {

    private final GitHub gitHub;

    @Autowired
    public GitHubIssueService(@Value("${app.github.access-token}") String githubAccessToken) throws IOException {
        this.gitHub = new GitHubBuilder().withOAuthToken(githubAccessToken).build();
    }

    public void createIssue(String serviceName, String commit, Collection<JsonObject> privacyRelatedChanges) throws IOException {
        gitHub
                .getRepository(serviceName)
                .createIssue("CI-Privacy-Alert: Privacy related changes found in " + commit)
                .body(createIssueBody(commit, privacyRelatedChanges))
                .create();
    }

    private String createIssueBody(String commit, Collection<JsonObject> privacyRelatedChanges) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        StringBuilder body = new StringBuilder("## Privacy related changes occured in commit: " + commit + "\n" +
                "\n" +
                "\n" +
                "## Privacy relevant changes \n");

        for (JsonObject jsonObject : privacyRelatedChanges) {
            body
                    .append("```json\n")
                    .append(gson.toJson(jsonObject))
                    .append("\n```")
                    .append("\n\n");
        }

        return body.toString();
    }
}
