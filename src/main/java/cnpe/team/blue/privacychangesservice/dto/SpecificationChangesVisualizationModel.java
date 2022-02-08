package cnpe.team.blue.privacychangesservice.dto;

import java.time.format.DateTimeFormatter;

public class SpecificationChangesVisualizationModel {

    private SpecificationChanges specificationChanges;

    private String date;

    private int privacyRelatedChangesCount;

    private FilterType filterType;

    public SpecificationChangesVisualizationModel(SpecificationChanges specificationChanges, int privacyRelatedChangesCount, FilterType filterType) {
        this.specificationChanges = specificationChanges;
        this.privacyRelatedChangesCount = privacyRelatedChangesCount;
        this.date = specificationChanges.getCreated().toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm yyyy-MM-dd"));
        this.filterType = filterType;
    }

    public SpecificationChanges getSpecificationChanges() {
        return specificationChanges;
    }

    public void setSpecificationChanges(SpecificationChanges specificationChanges) {
        this.specificationChanges = specificationChanges;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPrivacyRelatedChangesCount() {
        return privacyRelatedChangesCount;
    }

    public void setPrivacyRelatedChangesCount(int privacyRelatedChangesCount) {
        this.privacyRelatedChangesCount = privacyRelatedChangesCount;
    }

    public FilterType getFilterType() {
        return filterType;
    }

    public void setFilterType(FilterType filterType) {
        this.filterType = filterType;
    }
}
