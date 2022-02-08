package cnpe.team.blue.privacychangesservice.service.filter.tira;

import cnpe.team.blue.privacychangesservice.dto.SpecificationChanges;
import com.google.gson.*;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TIRAFilterService {

    public boolean isTIRAIncludedInSpecification(SpecificationChanges specificationChanges) {
        JsonElement root = JsonParser.parseString(specificationChanges.getChangedSpecification());
        return checkJSONObject(root.getAsJsonObject());
    }

    public Collection<JsonObject> filterPrivacySpecificationChanges(SpecificationChanges specificationChanges) {
        List<JsonObject> privacyChanges = new ArrayList<>();
        JsonArray differencesList = JsonParser.parseString(specificationChanges.getDifferences()).getAsJsonArray();

        for (JsonElement element : differencesList) {
            if (isChangePrivacyRelated(element, specificationChanges)) {
                privacyChanges.add(element.getAsJsonObject());
            }
        }

        return privacyChanges;
    }

    private boolean isChangePrivacyRelated(JsonElement difference, SpecificationChanges specificationChanges) {
        String kind = difference.getAsJsonObject().get("kind").getAsString();
        if (kind.equalsIgnoreCase("a")) {
            kind = difference.getAsJsonObject().get("item").getAsJsonObject().get("kind").getAsString();
        }

        JsonElement root;
        if (kind.equalsIgnoreCase("d")) {
            root = JsonParser.parseString(specificationChanges.getSourceSpecification()).getAsJsonObject();
        }
        else {
            root = JsonParser.parseString(specificationChanges.getChangedSpecification()).getAsJsonObject();
        }
        return isChangePrivacyRelated(root, difference.getAsJsonObject().get("path").getAsJsonArray());
    }

    private boolean isChangePrivacyRelated(JsonElement root, JsonArray paths) {
        JsonElement currentNode = root;
        boolean tiraFound = false;

        for (JsonElement currentPath : paths) {
            if (currentNode.isJsonObject()) {
                Set<Map.Entry<String, JsonElement>> entries = currentNode.getAsJsonObject().entrySet();
                tiraFound = tiraFound || entries.stream().anyMatch(e -> e.getKey().equalsIgnoreCase("x-tira"));

                if (currentNode.getAsJsonObject().get(currentPath.getAsString()).isJsonObject() &&
                        currentNode.getAsJsonObject().get(currentPath.getAsString()).getAsJsonObject().entrySet()
                                .stream().anyMatch(e -> e.getKey().equalsIgnoreCase("x-tira-ignore"))) {
                    return false;
                }

                currentNode = currentNode.getAsJsonObject().get(currentPath.getAsString());
            }
            else if (currentNode.isJsonArray()) {
                currentNode = currentNode.getAsJsonArray().get(Integer.parseInt(currentPath.getAsString()));
            }
            else {
                break;
            }
        }

        return tiraFound;
    }

    private boolean checkJSONObject(JsonObject jsonObject) {
        boolean result = false;

        for (Map.Entry<String, JsonElement> keyValue: jsonObject.entrySet()) {
            if (keyValue.getKey().contains("x-tira")) {
                return true;
            }
            else {
                if (keyValue.getValue().isJsonObject()) {
                    result = result || checkJSONObject(keyValue.getValue().getAsJsonObject());
                }
                else if (keyValue.getValue().isJsonArray()) {
                    result = result || checkJSONArray(keyValue.getValue().getAsJsonArray());
                }
            }
        }

        return result;
    }

    private boolean checkJSONArray(JsonArray jsonArray) {
        boolean result = false;

        for (JsonElement jsonElement : jsonArray) {
            if (jsonElement.isJsonObject()) {
                result = result || checkJSONObject(jsonElement.getAsJsonObject());
            }
        }

        return result;
    }
}
