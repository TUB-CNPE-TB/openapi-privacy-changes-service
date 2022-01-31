package cnpe.team.blue.privacychangesservice.service.filter;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class PrivacyRulesTree {

    private final Node root;

    public PrivacyRulesTree() {
        this.root = new Node("");
        this.root.setChildren(List.of(createPathsRules(), createSchemasRules()));
    }

    public boolean isPrivacyRelatedChange(LinkedList<String> paths) {
        Node currentNode = this.root;
        String currentPath = paths.poll();

        while (!currentNode.isEndNode() && currentPath != null) {
            boolean found = false;
            for (Node childNode: currentNode.getChildren()) {
                if (childNode.getData().equals(currentPath)) {
                    currentNode = childNode;
                    currentPath = paths.poll();
                    found = true;
                }
            }
            if (!found) {
                Optional<Node> anyNode = currentNode.getChildren().stream().filter(node -> node.getData().equals("any")).findFirst();
                if (anyNode.isPresent()) {
                    currentNode = anyNode.get();
                    currentPath = paths.poll();
                }
                else {
                    break;
                }
            }
        }

        return currentNode.isEndNode();
    }

    private Node createSchemasRules() {
        Node components = new Node("components");
        Node schemas = createEndNode("schemas");

        Node responses = new Node("responses");
        Node responsesAny = new Node("any");
        responsesAny.setChildren(createResponseBodyObjectRules());
        responses.setChildren(List.of(responsesAny));

        Node requestBodies = new Node("requestBodies");
        Node requestBodyAny = new Node("any");
        responsesAny.setChildren(createRequestBodyObjectRules());
        requestBodies.setChildren(List.of(requestBodyAny));

        components.setChildren(List.of(schemas, responses, requestBodies));

        return components;
    }

    private Node createPathsRules() {
        Node paths = createEndNode("paths");
        Node any = new Node("any");
        any.setEndNode();

        Node get = createPathRules("get");
        Node post = createPathRules("post");
        Node put = createPathRules("put");
        Node delete = createPathRules("delete");
        Node patch = createPathRules("patch");
        Node head = createPathRules("head");

        any.setChildren(List.of(get, post, put, delete, patch, head));
        paths.setChildren(List.of(any));

        return paths;
    }

    private Node createPathRules(String httpMethod) {
        Node pathRules = new Node(httpMethod);
        pathRules.setChildren(createOperationObjectRules());
        return pathRules;
    }

    private List<Node> createOperationObjectRules() {
        Node parameters = new Node("parameters");
        parameters.setEndNode();

        Node requestBody = new Node("requestBody");
        requestBody.setChildren(createRequestBodyObjectRules());

        Node responses = new Node("responses");
        responses.setChildren(createResponseBodyObjectRules());

        Node callbacks = new Node("callbacks");
        callbacks.setEndNode();

        return List.of(parameters, requestBody, responses, callbacks);
    }

    private List<Node> createResponseBodyObjectRules() {
        return List.of(createEndNode("headers"), createEndNode("content"), createEndNode("$ref"));
    }

    private List<Node> createRequestBodyObjectRules() {
        return List.of(createEndNode("content"), createEndNode("$ref"));
    }

    private Node createEndNode(String name) {
        Node endNode = new Node(name);
        endNode.setEndNode();
        return endNode;
    }
}
