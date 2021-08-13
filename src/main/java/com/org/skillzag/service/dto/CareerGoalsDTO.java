package com.org.skillzag.service.dto;

import com.org.skillzag.domain.enumeration.GoalStatus;
import com.org.skillzag.domain.enumeration.GoalType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.org.skillzag.domain.CareerGoals} entity.
 */
public class CareerGoalsDTO implements Serializable {

    private Long id;

    private String industry;

    private String goal;

    private String time;

    private GoalStatus status;

    private GoalType goalType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public GoalStatus getStatus() {
        return status;
    }

    public void setStatus(GoalStatus status) {
        this.status = status;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public void setGoalType(GoalType goalType) {
        this.goalType = goalType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CareerGoalsDTO)) {
            return false;
        }

        CareerGoalsDTO careerGoalsDTO = (CareerGoalsDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, careerGoalsDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CareerGoalsDTO{" +
            "id=" + getId() +
            ", industry='" + getIndustry() + "'" +
            ", goal='" + getGoal() + "'" +
            ", time='" + getTime() + "'" +
            ", status='" + getStatus() + "'" +
            ", goalType='" + getGoalType() + "'" +
            "}";
    }
}
