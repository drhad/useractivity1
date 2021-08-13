package com.org.skillzag.service.dto;

import com.org.skillzag.domain.enumeration.ProjectStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.org.skillzag.domain.ProjectManagement} entity.
 */
public class ProjectManagementDTO implements Serializable {

    private Long id;

    private String emailID;

    private String projectName;

    private String companyName;

    private String companyProfile;

    private String companyDescription;

    private String projectDescription;

    private String objective;

    private Instant dates;

    private Instant validFrom;

    private Instant validTo;

    private String logo;

    private String videoUrl;

    private String description;

    private String createdBy;

    private Instant createdTime;

    private ProjectStatus projectStatus;

    private String url1;

    private String url2;

    private String url3;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(String companyProfile) {
        this.companyProfile = companyProfile;
    }

    public String getCompanyDescription() {
        return companyDescription;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public Instant getDates() {
        return dates;
    }

    public void setDates(Instant dates) {
        this.dates = dates;
    }

    public Instant getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return validTo;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getUrl1() {
        return url1;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return url2;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return url3;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectManagementDTO)) {
            return false;
        }

        ProjectManagementDTO projectManagementDTO = (ProjectManagementDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, projectManagementDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectManagementDTO{" +
            "id=" + getId() +
            ", emailID='" + getEmailID() + "'" +
            ", projectName='" + getProjectName() + "'" +
            ", companyName='" + getCompanyName() + "'" +
            ", companyProfile='" + getCompanyProfile() + "'" +
            ", companyDescription='" + getCompanyDescription() + "'" +
            ", projectDescription='" + getProjectDescription() + "'" +
            ", objective='" + getObjective() + "'" +
            ", dates='" + getDates() + "'" +
            ", validFrom='" + getValidFrom() + "'" +
            ", validTo='" + getValidTo() + "'" +
            ", logo='" + getLogo() + "'" +
            ", videoUrl='" + getVideoUrl() + "'" +
            ", description='" + getDescription() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdTime='" + getCreatedTime() + "'" +
            ", projectStatus='" + getProjectStatus() + "'" +
            ", url1='" + getUrl1() + "'" +
            ", url2='" + getUrl2() + "'" +
            ", url3='" + getUrl3() + "'" +
            "}";
    }
}
