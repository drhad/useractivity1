package com.org.skillzag.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.org.skillzag.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobExperienceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobExperienceDTO.class);
        JobExperienceDTO jobExperienceDTO1 = new JobExperienceDTO();
        jobExperienceDTO1.setId(1L);
        JobExperienceDTO jobExperienceDTO2 = new JobExperienceDTO();
        assertThat(jobExperienceDTO1).isNotEqualTo(jobExperienceDTO2);
        jobExperienceDTO2.setId(jobExperienceDTO1.getId());
        assertThat(jobExperienceDTO1).isEqualTo(jobExperienceDTO2);
        jobExperienceDTO2.setId(2L);
        assertThat(jobExperienceDTO1).isNotEqualTo(jobExperienceDTO2);
        jobExperienceDTO1.setId(null);
        assertThat(jobExperienceDTO1).isNotEqualTo(jobExperienceDTO2);
    }
}
