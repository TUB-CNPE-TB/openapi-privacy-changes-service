package cnpe.team.blue.privacychangesservice.controller;

import cnpe.team.blue.privacychangesservice.dto.SpecificationChanges;
import cnpe.team.blue.privacychangesservice.repository.SpecificationChangesRepository;
import cnpe.team.blue.privacychangesservice.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

import java.util.List;

@RestController
public class SpecificationChangesController {

    private SpecificationChangesRepository specificationChangesRepository;

    public SpecificationChangesController(SpecificationChangesRepository specificationChangesRepository) {
        this.specificationChangesRepository = specificationChangesRepository;
    }

    @GetMapping("/changes")
    @Transactional(propagation = REQUIRES_NEW)
    public HttpEntity<List<SpecificationChanges>> listSpecificationChanges(@PageableDefault(size = 5, direction = Sort.Direction.ASC) Pageable page) {
        List<SpecificationChanges> specificationChanges = specificationChangesRepository.findAll(page).getContent();
        return ResponseEntity.ok(specificationChanges);
    }
}
