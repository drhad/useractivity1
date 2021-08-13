package com.org.skillzag.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A JobExperience.
 */
@Entity
@Table(name = "job_experience")
public class JobExperience implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "degree_title")
    private String degreeTitle;

    @Column(name = "start_year")
    private Integer startYear;

    @Column(name = "end_year")
    private Integer endYear;

    @Column(name = "years_of_experience")
    private String yearsOfExperience;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public JobExperience id(Long id) {
        this.id = id;
        return this;
    }

    public String getDegreeTitle() {
        return this.degreeTitle;
    }

    public JobExperience degreeTitle(String degreeTitle) {
        this.degreeTitle = degreeTitle;
        return this;
    }

    public void setDegreeTitle(String degreeTitle) {
        this.degreeTitle = degreeTitle;
    }

    public Integer getStartYear() {
        return this.startYear;
    }

    public JobExperience startYear(Integer startYear) {
        this.startYear = startYear;
        return this;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getEndYear() {
        return this.endYear;
    }

    public JobExperience endYear(Integer endYear) {
        this.endYear = endYear;
        return this;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public String getYearsOfExperience() {
        return this.yearsOfExperience;
    }

    public JobExperience yearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
        return this;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof JobExperience)) {
            return false;
        }
        return id != null && id.equals(((JobExperience) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "JobExperience{" +
            "id=" + getId() +
            ", degreeTitle='" + getDegreeTitle() + "'" +
            ", startYear=" + getStartYear() +
            ", endYear=" + getEndYear() +
            ", yearsOfExperience='" + getYearsOfExperience() + "'" +
            "}";
    }
}
