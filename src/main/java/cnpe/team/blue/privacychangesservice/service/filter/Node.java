package cnpe.team.blue.privacychangesservice.service.filter;

import java.util.List;

public class Node {
    private String data;
    private boolean endNode = false;
    private List<Node> children;

    public Node(String data) {
        this.data = data;
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

    @Override
    public String toString() {
        return "Node{" +
                "data='" + data + '\'' +
                ", children=" + children +
                '}';
    }


}