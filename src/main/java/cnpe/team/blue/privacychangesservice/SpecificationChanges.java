package cnpe.team.blue.privacychangesservice;

import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonRawValue;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "teamblueprivacymonitoring.specificationchanges")
public class SpecificationChanges {
    
    @Id
    @Column
    @GenericGenerator(
        name = "UUID",
        strategy = "org.hibernate.id.UUIDGenerator",
        parameters = {
            @Parameter(
                name = "uuid_gen_strategy_class",
                value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
            )
        }
    )
    private UUID id;

    @Column(columnDefinition = "json")
    @JsonRawValue
    private String differences;

    public UUID getId() {
        return id;
    }

    public String getDifferences() {
        return differences;
    }
}
