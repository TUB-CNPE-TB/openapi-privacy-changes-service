package cnpe.team.blue.privacychangesservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.MANDATORY;

@Repository
@Transactional(propagation = MANDATORY)
public interface SpecificationChangesRepository extends JpaRepository<SpecificationChanges, Long>, JpaSpecificationExecutor<SpecificationChanges> {
}