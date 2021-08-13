package com.org.skillzag.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.org.skillzag.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CareerGoalsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CareerGoals.class);
        CareerGoals careerGoals1 = new CareerGoals();
        careerGoals1.setId(1L);
        CareerGoals careerGoals2 = new CareerGoals();
        careerGoals2.setId(careerGoals1.getId());
        assertThat(careerGoals1).isEqualTo(careerGoals2);
        careerGoals2.setId(2L);
        assertThat(careerGoals1).isNotEqualTo(careerGoals2);
        careerGoals1.setId(null);
        assertThat(careerGoals1).isNotEqualTo(careerGoals2);
    }
}
