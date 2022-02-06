package cnpe.team.blue.privacychangesservice.service.visualization;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class VisualizationConverter {

    public JsonElement convertForVisualization(JsonElement sourceRoot) {
        JsonObject outputRoot = new JsonObject();
        outputRoot.addProperty("name", "root");
        outputRoot.add("children", parseNode(sourceRoot));

        return outputRoot;
    }

    private JsonArray parseNode(JsonElement node) {
        JsonArray children = new JsonArray();

        if (node.isJsonArray()) {
            int i = 0;
            for (JsonElement jsonElement: node.getAsJsonArray()) {
                JsonObject child = new JsonObject();
                child.addProperty("name", String.valueOf(i));
                child.add("children", parseNode(jsonElement));
                children.add(child);
                i++;
            }
        }
        else if (node.isJsonPrimitive()) {
            JsonObject child = new JsonObject();
            child.addProperty("name", node.toString());
            children.add(child);
        }
        else {
            for (Map.Entry<String,JsonElement> entry : node.getAsJsonObject().entrySet()) {
                JsonObject child = new JsonObject();
                child.addProperty("name", entry.getKey());
                child.add("children", parseNode(entry.getValue()));
                children.add(child);
            }
        }

        return children;
    }
}
