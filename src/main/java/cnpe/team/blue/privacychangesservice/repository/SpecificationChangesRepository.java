package cnpe.team.blue.privacychangesservice.repository;

import cnpe.team.blue.privacychangesservice.dto.SpecificationChanges;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.UUID;

@Repository
public interface SpecificationChangesRepository extends JpaRepository<SpecificationChanges, UUID>, JpaSpecificationExecutor<SpecificationChanges> {

    @Query(value = "SELECT * FROM team_blue_privacy_monitoring.specification_changes s WHERE s.created > ?1", nativeQuery = true)
    Collection<SpecificationChanges> findLatestChanges(Timestamp lastCheck);
}
