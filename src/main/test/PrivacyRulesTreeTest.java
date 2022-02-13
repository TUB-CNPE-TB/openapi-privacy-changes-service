import cnpe.team.blue.privacychangesservice.service.filter.openapi.PrivacyRulesTree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

public class PrivacyRulesTreeTest {

    private PrivacyRulesTree privacyRulesTree;

    @BeforeEach
    public void init() {
        privacyRulesTree = new PrivacyRulesTree();
    }

    @Test
    public void testParametersEndNode(){
        LinkedList<String> path = new LinkedList<>();
        path.add("paths");
        path.add("test");
        path.add("get");
        path.add("parameters");

        assert privacyRulesTree.isPrivacyRelatedChange(path, "A");

        path.clear();
        path.add("paths");
        path.add("test");
        path.add("get");
        path.add("parameters");
        path.add("test");
        path.add("name");

        assert privacyRulesTree.isPrivacyRelatedChange(path, "E");

        path.clear();
        path.add("paths");
        path.add("test");
        path.add("get");
        path.add("parameters");
        path.add("test");
        path.add("$ref");

        assert privacyRulesTree.isPrivacyRelatedChange(path, "E");
    }

    @Test
    public void testParametersDescription() {
        LinkedList<String> path = new LinkedList<>();
        path.add("paths");
        path.add("test");
        path.add("get");
        path.add("parameters");
        path.add("test");
        path.add("description");

        assert !privacyRulesTree.isPrivacyRelatedChange(path, "E");
    }

    @Test
    public void testRequestBody(){
        LinkedList<String> path = new LinkedList<>();
        path.add("paths");
        path.add("test");
        path.add("get");
        path.add("requestBody");
        path.add("content");

        assert privacyRulesTree.isPrivacyRelatedChange(path, "E");

        path.clear();
        path.add("paths");
        path.add("test");
        path.add("get");
        path.add("requestBody");
        path.add("$ref");

        assert privacyRulesTree.isPrivacyRelatedChange(path, "E");
    }

    @Test
    public void testResponses(){
        LinkedList<String> path = new LinkedList<>();
        path.add("paths");
        path.add("test");
        path.add("get");
        path.add("responses");
        path.add("content");

        assert privacyRulesTree.isPrivacyRelatedChange(path, "N");

        path.clear();
        path.add("paths");
        path.add("test");
        path.add("get");
        path.add("responses");
        path.add("0");
        path.add("headers");

        assert privacyRulesTree.isPrivacyRelatedChange(path, "A");

        path.clear();
        path.add("paths");
        path.add("test");
        path.add("get");
        path.add("responses");
        path.add("1");
        path.add("$ref");

        assert privacyRulesTree.isPrivacyRelatedChange(path, "E");
    }

    @Test
    public void testCallback() {
        LinkedList<String> path = new LinkedList<>();
        path.add("paths");
        path.add("test");
        path.add("get");
        path.add("callbacks");

        assert privacyRulesTree.isPrivacyRelatedChange(path, "N");
    }

    @Test
    public void testComponents() {
        LinkedList<String> path = new LinkedList<>();
        path.add("components");
        path.add("schemas");
        path.add("test");

        assert privacyRulesTree.isPrivacyRelatedChange(path, "E");
    }
}
