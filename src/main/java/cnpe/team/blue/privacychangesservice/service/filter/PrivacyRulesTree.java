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

    public Node getRoot() {
        return root;
    }

    public boolean isPrivacyRelatedChange(LinkedList<String> paths, String changeType) {
        Node currentNode = this.root;
        String currentPath = paths.poll();

        while (!isEndNodeReached(currentNode, changeType) && currentPath != null) {
            boolean found = false;
            for (Node childNode: currentNode.getChildren()) {
                if (childNode.getData().equalsIgnoreCase(currentPath)) {
                    currentNode = childNode;
                    currentPath = paths.poll();
                    found = true;
                    break;
                }
            }
            if (!found) {
                Optional<Node> anyNode = currentNode.getChildren().stream().filter(node -> node.getData().equalsIgnoreCase("any")).findFirst();
                if (anyNode.isPresent()) {
                    currentNode = anyNode.get();
                    currentPath = paths.poll();
                }
                else {
                    break;
                }
            }
        }

        return isEndNodeReached(currentNode, changeType);
    }

    private boolean isEndNodeReached(Node currentNode, String changeType) {
        return currentNode.isEndNode() && currentNode.getPrivacyRelatedChangeTypes().stream().anyMatch(s -> s.equalsIgnoreCase(changeType));
    }

    private Node createSchemasRules() {
        Node components = new Node("components");
        Node schemas = createEndNode("schemas");

        Node responses = new Node("responses");
        Node responsesAny = createEndNode("any");
        responsesAny.setPrivacyRelatedChangeTypes(List.of("N", "D"));
        responsesAny.setChildren(createResponseBodyObjectRules());
        responses.setChildren(List.of(responsesAny));

        Node requestBodies = new Node("requestBodies");
        Node requestBodyAny = createEndNode("any");
        requestBodyAny.setPrivacyRelatedChangeTypes(List.of("N", "D"));
        requestBodyAny.setChildren(createRequestBodyObjectRules());
        requestBodies.setChildren(List.of(requestBodyAny));

        components.setChildren(List.of(schemas, responses, requestBodies));

        return components;
    }

    private Node createPathsRules() {
        Node paths = new Node("paths");

        Node any = createEndNode("any");
        any.setPrivacyRelatedChangeTypes(List.of("N", "D"));

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
        Node parameters = createEndNode("parameters");
        parameters.setPrivacyRelatedChangeTypes(List.of("A"));
        parameters.setChildren(createParameterObjectRules());

        Node requestBody = new Node("requestBody");
        requestBody.setChildren(createRequestBodyObjectRules());

        Node responses = new Node("responses");
        responses.setChildren(createResponseBodyObjectRules());

        Node callbacks = createEndNode("callbacks");

        return List.of(parameters, requestBody, responses, callbacks);
    }

    private List<Node> createParameterObjectRules() {
        Node any = new Node("any");

        Node name = createEndNode("name");
        Node in = createEndNode("in");
        Node schema = createEndNode("schema");
        Node ref = createEndNode("$ref");
        any.setChildren(List.of(name, ref, schema, in));

        return List.of(any);
    }

    private List<Node> createResponseBodyObjectRules() {
        Node any = new Node("any");

        any.setChildren(List.of(createEndNode("headers"), createEndNode("content"), createEndNode("$ref")));
        return List.of(any);
    }

    private List<Node> createRequestBodyObjectRules() {
        return List.of(createEndNode("content"), createEndNode("$ref"));
    }

    private Node createEndNode(String name) {
        Node endNode = new Node(name);
        endNode.setEndNode();
        endNode.setPrivacyRelatedChangeTypes(List.of("N", "D", "A", "E"));
        return endNode;
    }
}
