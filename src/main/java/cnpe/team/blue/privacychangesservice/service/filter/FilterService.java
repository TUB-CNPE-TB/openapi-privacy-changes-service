package cnpe.team.blue.privacychangesservice.service.filter;

import cnpe.team.blue.privacychangesservice.dto.FilterType;
import cnpe.team.blue.privacychangesservice.dto.SpecificationChanges;
import cnpe.team.blue.privacychangesservice.service.filter.openapi.OpenAPIFilterService;
import cnpe.team.blue.privacychangesservice.service.filter.tira.TIRAFilterService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class FilterService {

    @Autowired
    private OpenAPIFilterService openAPIFilterService;

    @Autowired
    private TIRAFilterService tiraFilterService;

    public Collection<JsonObject> filterPrivacySpecificationChanges(SpecificationChanges specificationChanges) {
        Collection<JsonObject> privacyRelatedChanges;
        if (tiraFilterService.isTIRAIncludedInSpecification(specificationChanges)) {
            privacyRelatedChanges = tiraFilterService.filterPrivacySpecificationChanges(specificationChanges);
        }
        else {
            privacyRelatedChanges = openAPIFilterService.filterPrivacySpecificationChanges(specificationChanges);
        }
        return privacyRelatedChanges;
    }

    public FilterType getUsedFilter(SpecificationChanges specificationChanges) {
        if (tiraFilterService.isTIRAIncludedInSpecification(specificationChanges)) {
            return FilterType.tira;
        }
        else {
            return FilterType.tree;
        }
    }
}
