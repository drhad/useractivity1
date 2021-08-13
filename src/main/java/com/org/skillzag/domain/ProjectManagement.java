package com.org.skillzag.domain;

import com.org.skillzag.domain.enumeration.ProjectStatus;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;

/**
 * A ProjectManagement.
 */
@Entity
@Table(name = "project_management")
public class ProjectManagement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "email_id")
    private String emailID;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "company_name")
    private String companyName;

    @Column(name = "company_profile")
    private String companyProfile;

    @Column(name = "company_description")
    private String companyDescription;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "objective")
    private String objective;

    @Column(name = "dates")
    private Instant dates;

    @Column(name = "valid_from")
    private Instant validFrom;

    @Column(name = "valid_to")
    private Instant validTo;

    @Column(name = "logo")
    private String logo;

    @Column(name = "video_url")
    private String videoUrl;

    @Column(name = "description")
    private String description;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_time")
    private Instant createdTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_status")
    private ProjectStatus projectStatus;

    @Column(name = "url_1")
    private String url1;

    @Column(name = "url_2")
    private String url2;

    @Column(name = "url_3")
    private String url3;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectManagement id(Long id) {
        this.id = id;
        return this;
    }

    public String getEmailID() {
        return this.emailID;
    }

    public ProjectManagement emailID(String emailID) {
        this.emailID = emailID;
        return this;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public ProjectManagement projectName(String projectName) {
        this.projectName = projectName;
        return this;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyName() {
        return this.companyName;
    }

    public ProjectManagement companyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyProfile() {
        return this.companyProfile;
    }

    public ProjectManagement companyProfile(String companyProfile) {
        this.companyProfile = companyProfile;
        return this;
    }

    public void setCompanyProfile(String companyProfile) {
        this.companyProfile = companyProfile;
    }

    public String getCompanyDescription() {
        return this.companyDescription;
    }

    public ProjectManagement companyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
        return this;
    }

    public void setCompanyDescription(String companyDescription) {
        this.companyDescription = companyDescription;
    }

    public String getProjectDescription() {
        return this.projectDescription;
    }

    public ProjectManagement projectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
        return this;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getObjective() {
        return this.objective;
    }

    public ProjectManagement objective(String objective) {
        this.objective = objective;
        return this;
    }

    public void setObjective(String objective) {
        this.objective = objective;
    }

    public Instant getDates() {
        return this.dates;
    }

    public ProjectManagement dates(Instant dates) {
        this.dates = dates;
        return this;
    }

    public void setDates(Instant dates) {
        this.dates = dates;
    }

    public Instant getValidFrom() {
        return this.validFrom;
    }

    public ProjectManagement validFrom(Instant validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public void setValidFrom(Instant validFrom) {
        this.validFrom = validFrom;
    }

    public Instant getValidTo() {
        return this.validTo;
    }

    public ProjectManagement validTo(Instant validTo) {
        this.validTo = validTo;
        return this;
    }

    public void setValidTo(Instant validTo) {
        this.validTo = validTo;
    }

    public String getLogo() {
        return this.logo;
    }

    public ProjectManagement logo(String logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getVideoUrl() {
        return this.videoUrl;
    }

    public ProjectManagement videoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        return this;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDescription() {
        return this.description;
    }

    public ProjectManagement description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public ProjectManagement createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedTime() {
        return this.createdTime;
    }

    public ProjectManagement createdTime(Instant createdTime) {
        this.createdTime = createdTime;
        return this;
    }

    public void setCreatedTime(Instant createdTime) {
        this.createdTime = createdTime;
    }

    public ProjectStatus getProjectStatus() {
        return this.projectStatus;
    }

    public ProjectManagement projectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
        return this;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getUrl1() {
        return this.url1;
    }

    public ProjectManagement url1(String url1) {
        this.url1 = url1;
        return this;
    }

    public void setUrl1(String url1) {
        this.url1 = url1;
    }

    public String getUrl2() {
        return this.url2;
    }

    public ProjectManagement url2(String url2) {
        this.url2 = url2;
        return this;
    }

    public void setUrl2(String url2) {
        this.url2 = url2;
    }

    public String getUrl3() {
        return this.url3;
    }

    public ProjectManagement url3(String url3) {
        this.url3 = url3;
        return this;
    }

    public void setUrl3(String url3) {
        this.url3 = url3;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProjectManagement)) {
            return false;
        }
        return id != null && id.equals(((ProjectManagement) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProjectManagement{" +
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
