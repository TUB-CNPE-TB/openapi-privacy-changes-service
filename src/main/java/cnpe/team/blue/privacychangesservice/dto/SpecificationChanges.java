package cnpe.team.blue.privacychangesservice.dto;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonRawValue;

@Entity
@Table(name = "${app.database.name}.${app.database.table}")
public class SpecificationChanges {
    
    @Id
    @Column
    private UUID id;

    @Column
    private Timestamp created;

    @Column(name = "service_name")
    private String serviceName;

    @Column
    private String commit;

    @Column(columnDefinition = "json")
    @JsonRawValue
    private String differences;

    public UUID getId() {
        return id;
    }

    public String getDifferences() {
        return differences;
    }

    public Timestamp getCreated() {
        return created;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getCommit() {
        return commit;
    }

    @Override
    public String toString() {
        return "SpecificationChanges{" +
                "id=" + id +
                ", created=" + created +
                ", serviceName='" + serviceName + '\'' +
                ", commit='" + commit + '\'' +
                ", differences='" + differences + '\'' +
                '}';
    }
}
