package cnpe.team.blue.privacychangesservice.dto;

import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonRawValue;

@Entity
@Table(name = "team_blue_privacy_monitoring.specification_changes")
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

    @Column(columnDefinition = "json")
    @JsonRawValue
    private String sourceSpecification;

    @Column(columnDefinition = "json")
    @JsonRawValue
    private String changedSpecification;

    public UUID getId() {
        return id;
    }

    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getDifferences() {
        return differences;
    }

    public void setDifferences(String differences) {
        this.differences = differences;
    }

    public String getSourceSpecification() {
        return sourceSpecification;
    }

    public void setSourceSpecification(String sourceSpecification) {
        this.sourceSpecification = sourceSpecification;
    }

    public String getChangedSpecification() {
        return changedSpecification;
    }

    public void setChangedSpecification(String changedSpecification) {
        this.changedSpecification = changedSpecification;
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
