package cnpe.team.blue.privacychangesservice.service.github;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

@Component
@PropertySource("classpath:application.yml")
public class GitHubIssueService {

    private final GitHub gitHub;

    @Autowired
    public GitHubIssueService(@Value("${app.github.access-token}") String githubAccessToken) throws IOException {
        this.gitHub = new GitHubBuilder().withOAuthToken(githubAccessToken).build();
    }

    public void createIssue(String changesId, String serviceName, String commit, Collection<JsonObject> privacyRelatedChanges) throws IOException {
        gitHub
                .getRepository(serviceName)
                .createIssue("openapi-privacy-alert: Detected " + privacyRelatedChanges.size() + " privacy related changes")
                .body(createIssueBody(changesId, commit, privacyRelatedChanges))
                .create();
    }

    private String createIssueBody(String changesId, String commit, Collection<JsonObject> privacyRelatedChanges) {

        StringBuilder body = new StringBuilder();
        body.append("# :rotating_light: openapi-privacy-alert: Detected ");
        body.append(privacyRelatedChanges.size());
        body.append(" privacy related changes \n");
        body.append("## Privacy related changes found in commit: ").append(commit).append(" \n");

        body.append("### Deleted paths: \n");
        body.append(createPathEntries("d", privacyRelatedChanges));
        body.append("\n");

        body.append("### Added paths: \n");
        body.append(createPathEntries("n", privacyRelatedChanges));
        body.append("\n");

        body.append("### Edited paths: \n");
        body.append(createPathEntries("e", privacyRelatedChanges));
        body.append("\n");

        body.append("## View changes in [Dashboard](http://localhost:9090/privacyChanges/").append(changesId).append(") :eyes: ");

        return body.toString();
    }

    private String createPathEntries(String changeType, Collection<JsonObject> privacyRelatedChanges) {
        StringBuilder paths = new StringBuilder();

        boolean found = false;
        for (JsonObject change : privacyRelatedChanges) {
            if (change.get("kind").getAsString().equalsIgnoreCase(changeType) ||
                    (change.get("kind").getAsString().equalsIgnoreCase("a") && change.get("item").getAsJsonObject().get("kind").getAsString().equalsIgnoreCase(changeType))) {
                paths.append(" - ");

                LinkedList<String> currentPath = new LinkedList<>();
                for (JsonElement jsonElement : change.get("path").getAsJsonArray()) {
                    currentPath.add(jsonElement.getAsString());
                }
                paths.append(String.join(" :arrow_right: ", currentPath));

                if (change.get("kind").getAsString().equalsIgnoreCase("a")) {
                    paths.append(" :arrow_right: ");
                    paths.append(change.get("index").getAsString());
                }

                paths.append("\n");
                found = true;
            }
        }

        if (!found) {
            paths.append("None \n");
        }

        return paths.toString();
    }
}
