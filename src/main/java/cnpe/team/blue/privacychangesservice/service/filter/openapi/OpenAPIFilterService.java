package cnpe.team.blue.privacychangesservice.service.filter.openapi;

import cnpe.team.blue.privacychangesservice.dto.SpecificationChanges;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OpenAPIFilterService {

    @Autowired
    private PrivacyRulesTree privacyRulesTree;

    public Collection<JsonObject> filterPrivacySpecificationChanges(SpecificationChanges specificationChanges) {
        List<JsonObject> privacyChanges = new ArrayList<>();
        JsonArray differencesList = JsonParser.parseString(specificationChanges.getDifferences()).getAsJsonArray();

        for (JsonElement element : differencesList) {
            JsonObject jsonObject = element.getAsJsonObject();

            String changeType = jsonObject.get("kind").getAsString();

            LinkedList<String> paths = new LinkedList<>();
            for (JsonElement jsonElement : jsonObject.get("path").getAsJsonArray()) {
                paths.add(jsonElement.getAsString());
            }

            if (privacyRulesTree.isPrivacyRelatedChange(paths, changeType)) {
                privacyChanges.add(jsonObject);
            }
        }

        return privacyChanges;
    }
}
