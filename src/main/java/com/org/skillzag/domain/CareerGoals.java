package com.org.skillzag.domain;

import com.org.skillzag.domain.enumeration.GoalStatus;
import com.org.skillzag.domain.enumeration.GoalType;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A CareerGoals.
 */
@Entity
@Table(name = "career_goals")
public class CareerGoals implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "industry")
    private String industry;

    @Column(name = "goal")
    private String goal;

    @Column(name = "time")
    private String time;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private GoalStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal_type")
    private GoalType goalType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CareerGoals id(Long id) {
        this.id = id;
        return this;
    }

    public String getIndustry() {
        return this.industry;
    }

    public CareerGoals industry(String industry) {
        this.industry = industry;
        return this;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getGoal() {
        return this.goal;
    }

    public CareerGoals goal(String goal) {
        this.goal = goal;
        return this;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getTime() {
        return this.time;
    }

    public CareerGoals time(String time) {
        this.time = time;
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public GoalStatus getStatus() {
        return this.status;
    }

    public CareerGoals status(GoalStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(GoalStatus status) {
        this.status = status;
    }

    public GoalType getGoalType() {
        return this.goalType;
    }

    public CareerGoals goalType(GoalType goalType) {
        this.goalType = goalType;
        return this;
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CareerGoals)) {
            return false;
        }
        return id != null && id.equals(((CareerGoals) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CareerGoals{" +
            "id=" + getId() +
            ", industry='" + getIndustry() + "'" +
            ", goal='" + getGoal() + "'" +
            ", time='" + getTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", goalType='" + getGoalType() + "'" +
            "}";
    }
}
