package cnpe.team.blue.privacychangesservice.service.filter;

import cnpe.team.blue.privacychangesservice.dto.SpecificationChanges;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class FilterService {

    @Autowired
    private PrivacyRulesTree privacyRulesTree;

    public Collection<JsonObject> filterPrivacySpecificationChanges(SpecificationChanges specificationChanges) {
        List<JsonObject> privacyChanges = new ArrayList<>();
        JsonArray differencesList = JsonParser.parseString(specificationChanges.getDifferences()).getAsJsonArray();

        for (JsonElement element : differencesList) {
            JsonObject jsonObject = element.getAsJsonObject();

            LinkedList<String> paths = new LinkedList<>();
            for (JsonElement jsonElement : jsonObject.get("path").getAsJsonArray()) {
                paths.add(jsonElement.getAsString());
            }

            if (privacyRulesTree.isPrivacyRelatedChange(paths)) {
                privacyChanges.add(jsonObject);
            }
        }

        return privacyChanges;
    }
}
