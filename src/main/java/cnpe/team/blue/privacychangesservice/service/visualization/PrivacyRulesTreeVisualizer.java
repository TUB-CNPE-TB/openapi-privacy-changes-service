package cnpe.team.blue.privacychangesservice.service.visualization;

import cnpe.team.blue.privacychangesservice.service.filter.Node;
import cnpe.team.blue.privacychangesservice.service.filter.PrivacyRulesTree;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrivacyRulesTreeVisualizer {

    @Autowired
    private PrivacyRulesTree privacyRulesTree;

    public JsonObject parseTree() {
        return parseTreeNode(privacyRulesTree.getRoot());
    }

    private JsonObject parseTreeNode(Node node) {
        JsonObject newTreeNode = new JsonObject();

        newTreeNode.addProperty("name", node.getData());

        JsonArray children = new JsonArray();

        boolean httpRequestChildren = node.getChildren()
                .stream()
                .map(Node::getData)
                .filter(s -> s.equalsIgnoreCase("head") || s.equalsIgnoreCase("post") ||
                        s.equalsIgnoreCase("get") || s.equalsIgnoreCase("patch") ||
                        s.equalsIgnoreCase("put") || s.equalsIgnoreCase("delete")).count() == 6;

        if (httpRequestChildren) {
            JsonObject httpChild = new JsonObject();
            httpChild.addProperty("name", "HTTP Method");
            JsonArray httpChildren = new JsonArray();
            httpChildren.add(parseTreeNode(node.getChildren().get(0)));
            httpChild.add("children", httpChildren);

            children.add(httpChild);
        }
        else {
            for (Node childNode : node.getChildren()) {
                children.add(parseTreeNode(childNode));
            }
        }

        for (String type: node.getPrivacyRelatedChangeTypes()) {
            JsonObject end = new JsonObject();
            end.addProperty("name", "END: " + type);
            children.add(end);
        }

        newTreeNode.add("children", children);

        return newTreeNode;
    }
}
