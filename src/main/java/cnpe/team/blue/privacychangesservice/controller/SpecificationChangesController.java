package cnpe.team.blue.privacychangesservice.controller;

import cnpe.team.blue.privacychangesservice.dto.SpecificationChanges;
import cnpe.team.blue.privacychangesservice.dto.SpecificationChangesVisualizationModel;
import cnpe.team.blue.privacychangesservice.repository.SpecificationChangesRepository;
import cnpe.team.blue.privacychangesservice.service.filter.FilterService;
import cnpe.team.blue.privacychangesservice.service.visualization.VisualizationConverter;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class SpecificationChangesController {

    @Autowired
    private SpecificationChangesRepository specificationChangesRepository;

    @Autowired
    private VisualizationConverter visualizationConverter;

    @Autowired
    private FilterService filterService;

    @GetMapping("/index")
    public String listPrivacyChanges(Model model) {

        List<SpecificationChangesVisualizationModel> visualizationModels =
                specificationChangesRepository.findAll()
                        .stream()
                        .sorted(Comparator.comparing(SpecificationChanges::getCreated).reversed())
                        .map(s -> new SpecificationChangesVisualizationModel(s, filterService.filterPrivacySpecificationChanges(s).size()))
                        .collect(Collectors.toList());
        model.addAttribute("privacyChanges", visualizationModels);

        return "index";
    }

    @GetMapping("/privacyChanges/{id}")
    public String visualize(@PathVariable String id, Model model) {

        SpecificationChanges specificationChanges = specificationChangesRepository.findById(UUID.fromString(id)).get();

        Collection<JsonObject> privacyRelatedChanges = filterService.filterPrivacySpecificationChanges(specificationChanges);

        JsonElement sourceSpecification = visualizationConverter.convertForVisualization(JsonParser.parseString(specificationChanges.getSourceSpecification()));
        JsonElement changedSpecification = visualizationConverter.convertForVisualization(JsonParser.parseString(specificationChanges.getChangedSpecification()));

        Gson gson = new Gson();
        model.addAttribute("sourceSpecification", gson.toJson(sourceSpecification));
        model.addAttribute("changedSpecification", gson.toJson(changedSpecification));
        model.addAttribute("privacyRelatedChanges", privacyRelatedChanges.stream().map(gson::toJson).toArray());

        return "privacyChangesVisualization";
    }
}
