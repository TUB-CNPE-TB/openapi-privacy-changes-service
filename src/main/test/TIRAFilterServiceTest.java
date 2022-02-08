import cnpe.team.blue.privacychangesservice.dto.SpecificationChanges;
import cnpe.team.blue.privacychangesservice.service.filter.tira.TIRAFilterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TIRAFilterServiceTest {

    private TIRAFilterService tiraFilterService;

    @BeforeEach
    public void init() {
        tiraFilterService = new TIRAFilterService();
    }


    @Test
    public void testSpecWithTira() {
        SpecificationChanges specificationChanges = new SpecificationChanges();
        specificationChanges.setChangedSpecification("{\n" +
                "  \"openapi\": \"3.0.1\",\n" +
                "  \"info\": {\n" +
                "    \"title\": \"User Service\",\n" +
                "    \"version\": \"1.0.0\"\n" +
                "  },\n" +
                "  \"paths\": {\n" +
                "    \"/users\": {\n" +
                "      \"post\": {\n" +
                "        \"requestBody\": {\n" +
                "          \"content\": {\n" +
                "            \"application/json\": {\n" +
                "              \"schema\": {\n" +
                "                \"x-tira\": true,\n" +
                "                \"properties\": {\n" +
                "                  \"name\": {\n" +
                "                    \"type\": \"string\"\n" +
                "                  }\n" +
                "                },\n" +
                "                \"required\": [\n" +
                "                  \"name\"\n" +
                "                ],\n" +
                "                \"type\": \"object\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"required\": true\n" +
                "        },\n" +
                "        \"responses\": {\n" +
                "          \"201\": {\n" +
                "            \"description\": \"Created\",\n" +
                "            \"content\": {\n" +
                "              \"application/json\": {\n" +
                "                \"schema\": {\n" +
                "                  \"properties\": {\n" +
                "                    \"id\": {\n" +
                "                      \"type\": \"string\"\n" +
                "                    }\n" +
                "                  },\n" +
                "                  \"required\": [\n" +
                "                    \"id\",\n" +
                "                    \"name\",\n" +
                "                    \"age\"\n" +
                "                  ],\n" +
                "                  \"type\": \"object\"\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");

        assert tiraFilterService.isTIRAIncludedInSpecification(specificationChanges);
    }

    @Test
    public void testSpecWithoutTira() {
        SpecificationChanges specificationChanges = new SpecificationChanges();
        specificationChanges.setChangedSpecification("{\n" +
                "  \"openapi\": \"3.0.1\",\n" +
                "  \"info\": {\n" +
                "    \"title\": \"User Service\",\n" +
                "    \"version\": \"1.0.0\"\n" +
                "  },\n" +
                "  \"paths\": {\n" +
                "    \"/users\": {\n" +
                "      \"post\": {\n" +
                "        \"requestBody\": {\n" +
                "          \"content\": {\n" +
                "            \"application/json\": {\n" +
                "              \"schema\": {\n" +
                "                \"properties\": {\n" +
                "                  \"name\": {\n" +
                "                    \"type\": \"string\"\n" +
                "                  }\n" +
                "                },\n" +
                "                \"required\": [\n" +
                "                  \"name\"\n" +
                "                ],\n" +
                "                \"type\": \"object\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"required\": true\n" +
                "        },\n" +
                "        \"responses\": {\n" +
                "          \"201\": {\n" +
                "            \"description\": \"Created\",\n" +
                "            \"content\": {\n" +
                "              \"application/json\": {\n" +
                "                \"schema\": {\n" +
                "                  \"properties\": {\n" +
                "                    \"id\": {\n" +
                "                      \"type\": \"string\"\n" +
                "                    }\n" +
                "                  },\n" +
                "                  \"required\": [\n" +
                "                    \"id\",\n" +
                "                    \"name\",\n" +
                "                    \"age\"\n" +
                "                  ],\n" +
                "                  \"type\": \"object\"\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}");

        assert !tiraFilterService.isTIRAIncludedInSpecification(specificationChanges);
    }

    @Test
    public void testTIRAFilter() {
        String changes = "[{\"kind\":\"E\",\"path\":[\"paths\",\"/users\",\"post\",\"requestBody\",\"content\",\"application/json\",\"schema\",\"properties\",\"name\",\"type\"],\"lhs\":\"string\",\"rhs\":\"int\"},{\"kind\":\"A\",\"path\":[\"paths\",\"/users\",\"post\",\"requestBody\",\"content\",\"application/json\",\"schema\",\"required\"],\"index\":1,\"item\":{\"kind\":\"N\",\"rhs\":\"id\"}},{\"kind\":\"E\",\"path\":[\"paths\",\"/users\",\"post\",\"requestBody\",\"required\"],\"lhs\":true,\"rhs\":false},{\"kind\":\"N\",\"path\":[\"paths\",\"/users\",\"post\",\"responses\",\"201\",\"content\",\"application/json\",\"schema\",\"properties\",\"name\"],\"rhs\":{\"type\":\"string\"}},{\"kind\":\"A\",\"path\":[\"paths\",\"/users\",\"post\",\"responses\",\"201\",\"content\",\"application/json\",\"schema\",\"required\"],\"index\":2,\"item\":{\"kind\":\"D\",\"lhs\":\"age\"}},{\"kind\":\"E\",\"path\":[\"paths\",\"/users\",\"post\",\"responses\",\"201\",\"content\",\"application/json\",\"schema\",\"required\",1],\"lhs\":\"name\",\"rhs\":\"age\"},{\"kind\":\"E\",\"path\":[\"paths\",\"/users\",\"post\",\"responses\",\"201\",\"content\",\"application/json\",\"schema\",\"required\",0],\"lhs\":\"id\",\"rhs\":\"name\"},{\"kind\":\"D\",\"path\":[\"paths\",\"/users/{userId}\"],\"lhs\":{\"get\":{\"parameters\":[{\"name\":\"userId\",\"in\":\"path\",\"required\":true,\"schema\":{\"type\":\"string\"}}],\"responses\":{\"200\":{\"description\":\"The user\",\"content\":{\"application/json\":{\"schema\":{\"properties\":{\"name\":{\"type\":\"string\"}},\"required\":[\"name\"],\"type\":\"object\"}}}}}}}}]";
        String sourceSpec = "{\n" +
                "  \"openapi\": \"3.0.1\",\n" +
                "  \"info\": {\n" +
                "    \"title\": \"User Service\",\n" +
                "    \"version\": \"1.0.0\"\n" +
                "  },\n" +
                "  \"paths\": {\n" +
                "    \"/users\": {\n" +
                "      \"x-tira\": true,\n" +
                "      \"post\": {\n" +
                "        \"requestBody\": {\n" +
                "          \"content\": {\n" +
                "            \"application/json\": {\n" +
                "              \"schema\": {\n" +
                "                \"properties\": {\n" +
                "                  \"x-tira-ignore\": true,\n" +
                "                  \"name\": {\n" +
                "                    \"type\": \"string\"\n" +
                "                  }\n" +
                "                },\n" +
                "                \"required\": [\n" +
                "                  \"name\"\n" +
                "                ],\n" +
                "                \"type\": \"object\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"required\": true\n" +
                "        },\n" +
                "        \"responses\": {\n" +
                "          \"201\": {\n" +
                "            \"description\": \"Created\",\n" +
                "            \"content\": {\n" +
                "              \"application/json\": {\n" +
                "                \"schema\": {\n" +
                "                  \"properties\": {\n" +
                "                    \"id\": {\n" +
                "                      \"type\": \"string\"\n" +
                "                    }\n" +
                "                  },\n" +
                "                  \"required\": [\n" +
                "                    \"id\",\n" +
                "                    \"name\",\n" +
                "                    \"age\"\n" +
                "                  ],\n" +
                "                  \"type\": \"object\"\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    },\n" +
                "    \"/users/{userId}\": {\n" +
                "      \"get\": {\n" +
                "        \"parameters\": [\n" +
                "          {\n" +
                "            \"name\": \"userId\",\n" +
                "            \"in\": \"path\",\n" +
                "            \"required\": true,\n" +
                "            \"schema\": {\n" +
                "              \"type\": \"string\"\n" +
                "            }\n" +
                "          }\n" +
                "        ],\n" +
                "        \"responses\": {\n" +
                "          \"200\": {\n" +
                "            \"description\": \"The user\",\n" +
                "            \"content\": {\n" +
                "              \"application/json\": {\n" +
                "                \"schema\": {\n" +
                "                  \"properties\": {\n" +
                "                    \"name\": {\n" +
                "                      \"type\": \"string\"\n" +
                "                    }\n" +
                "                  },\n" +
                "                  \"required\": [\n" +
                "                    \"name\"\n" +
                "                  ],\n" +
                "                  \"type\": \"object\"\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";
        String changedSpec = "{\n" +
                "  \"openapi\": \"3.0.1\",\n" +
                "  \"info\": {\n" +
                "    \"title\": \"User Service\",\n" +
                "    \"version\": \"1.0.0\"\n" +
                "  },\n" +
                "  \"paths\": {\n" +
                "    \"/users\": {\n" +
                "      \"x-tira\": true,\n" +
                "      \"post\": {\n" +
                "        \"requestBody\": {\n" +
                "          \"content\": {\n" +
                "            \"application/json\": {\n" +
                "              \"schema\": {\n" +
                "                \"properties\": {\n" +
                "                  \"x-tira-ignore\": true,\n" +
                "                  \"name\": {\n" +
                "                    \"type\": \"int\"\n" +
                "                  }\n" +
                "                },\n" +
                "                \"required\": [\n" +
                "                  \"name\",\n" +
                "                  \"id\"\n" +
                "                ],\n" +
                "                \"type\": \"object\"\n" +
                "              }\n" +
                "            }\n" +
                "          },\n" +
                "          \"required\": false\n" +
                "        },\n" +
                "        \"responses\": {\n" +
                "          \"201\": {\n" +
                "            \"description\": \"Created\",\n" +
                "            \"content\": {\n" +
                "              \"application/json\": {\n" +
                "                \"schema\": {\n" +
                "                  \"properties\": {\n" +
                "                    \"id\": {\n" +
                "                      \"type\": \"string\"\n" +
                "                    },\n" +
                "                    \"name\": {\n" +
                "                      \"type\": \"string\"\n" +
                "                    }\n" +
                "                  },\n" +
                "                  \"required\": [\n" +
                "                    \"name\",\n" +
                "                    \"age\"\n" +
                "                  ],\n" +
                "                  \"type\": \"object\"\n" +
                "                }\n" +
                "              }\n" +
                "            }\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "}";

        SpecificationChanges specificationChanges = new SpecificationChanges();
        specificationChanges.setSourceSpecification(sourceSpec);
        specificationChanges.setChangedSpecification(changedSpec);
        specificationChanges.setDifferences(changes);

        assert tiraFilterService.filterPrivacySpecificationChanges(specificationChanges).size() == 6;
    }
}
