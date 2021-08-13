package com.org.skillzag.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.org.skillzag.domain.JobExperience} entity.
 */
public class JobExperienceDTO implements Serializable {

    private Long id;

    private String degreeTitle;

    private Integer startYear;

    private Integer endYear;

    private String yearsOfExperience;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDegreeTitle() {
        return degreeTitle;
    }

    public void setDegreeTitle(String degreeTitle) {
        this.degreeTitle = degreeTitle;
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobExperienceDTO)) {
            return false;
        }

        JobExperienceDTO jobExperienceDTO = (JobExperienceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, jobExperienceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobExperienceDTO{" +
            "id=" + getId() +
            ", degreeTitle='" + getDegreeTitle() + "'" +
            ", startYear=" + getStartYear() +
            ", endYear=" + getEndYear() +
            ", yearsOfExperience='" + getYearsOfExperience() + "'" +
            "}";
    }
}
