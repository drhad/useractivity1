package com.org.skillzag.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.org.skillzag.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CareerGoalsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CareerGoalsDTO.class);
        CareerGoalsDTO careerGoalsDTO1 = new CareerGoalsDTO();
        careerGoalsDTO1.setId(1L);
        CareerGoalsDTO careerGoalsDTO2 = new CareerGoalsDTO();
        assertThat(careerGoalsDTO1).isNotEqualTo(careerGoalsDTO2);
        careerGoalsDTO2.setId(careerGoalsDTO1.getId());
        assertThat(careerGoalsDTO1).isEqualTo(careerGoalsDTO2);
        careerGoalsDTO2.setId(2L);
        assertThat(careerGoalsDTO1).isNotEqualTo(careerGoalsDTO2);
        careerGoalsDTO1.setId(null);
        assertThat(careerGoalsDTO1).isNotEqualTo(careerGoalsDTO2);
    }
}
