import cnpe.team.blue.privacychangesservice.service.visualization.VisualizationConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class VisualizationConverterTest {

    private VisualizationConverter visualizationConverter;

    @BeforeEach
    public void init() {
        visualizationConverter = new VisualizationConverter();
    }

    @Test
    public void simpleTest() {
        JsonElement srcRoot = JsonParser.parseString("{\"test\":\"test\"}");
        JsonElement output = visualizationConverter.convertForVisualization(srcRoot);

        System.out.println(output);
    }

    @Test
    public void complexTest() {
        JsonElement srcRoot = JsonParser.parseString("{\"openapi\":\"3.0.1\",\"info\":{\"title\":\"User Service\",\"version\":\"1.0.0\"},\"paths\":{\"/users\":{\"post\":{\"requestBody\":{\"content\":{\"application/json\":{\"schema\":{\"properties\":{\"name\":{\"type\":\"string\"}},\"required\":[\"name\"],\"type\":\"object\"}}},\"required\":true},\"responses\":{\"201\":{\"description\":\"Created\",\"content\":{\"application/json\":{\"schema\":{\"properties\":{\"id\":{\"type\":\"string\"}},\"required\":[\"id\"],\"type\":\"object\"}}}}}}},\"/users/{userId}\":{\"get\":{\"parameters\":[{\"name\":\"userId\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"string\"}}],\"responses\":{\"200\":{\"description\":\"The user\",\"content\":{\"application/json\":{\"schema\":{\"properties\":{\"name\":{\"type\":\"string\"}},\"required\":[\"name\"],\"type\":\"object\"}}}}}}}}}");
        JsonElement output = visualizationConverter.convertForVisualization(srcRoot);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        System.out.println(gson.toJson(output));
    }
}
