package cnpe.team.blue.privacychangesservice.service;

import cnpe.team.blue.privacychangesservice.dto.SpecificationChanges;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Component
public class FilterService {

    public Collection<JsonObject> filterPrivacySpecificationChanges(SpecificationChanges specificationChanges) {
        List<JsonObject> privacyChanges = new ArrayList<>();
        JsonArray differencesList = JsonParser.parseString(specificationChanges.getDifferences()).getAsJsonArray();

        for (Iterator<JsonElement> it = differencesList.iterator(); it.hasNext(); ) {
            JsonObject jsonObject = it.next().getAsJsonObject();

            String firstPath = jsonObject.get("path").getAsJsonArray().get(0).getAsString();

            if (firstPath.equals("paths") || firstPath.equals("components")) {
                privacyChanges.add(jsonObject);
            }
        }

        return privacyChanges;
    }
}
