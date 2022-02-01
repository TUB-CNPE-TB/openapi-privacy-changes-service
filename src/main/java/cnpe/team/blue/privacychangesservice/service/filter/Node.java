package cnpe.team.blue.privacychangesservice.service.filter;

import java.util.ArrayList;
import java.util.List;

public class Node {

    private String data;

    private boolean endNode = false;

    private List<Node> children;

    private List<String> privacyRelatedChangeTypes;

    public Node(String data) {
        this.data = data;
        this.privacyRelatedChangeTypes = new ArrayList<>();
        this.children = List.of();
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void setEndNode() {
        this.endNode = true;
    }

    public boolean isEndNode() {
        return endNode;
    }

    public List<String> getPrivacyRelatedChangeTypes() {
        return privacyRelatedChangeTypes;
    }

    public void setPrivacyRelatedChangeTypes(List<String> privacyRelatedChangeTypes) {
        this.privacyRelatedChangeTypes = privacyRelatedChangeTypes;
    }

    @Override
    public String toString() {
        return "Node{" +
                "data='" + data + '\'' +
                ", children=" + children +
                '}';
    }


}