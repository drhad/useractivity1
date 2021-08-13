package com.org.skillzag.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.org.skillzag.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class JobExperienceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(JobExperience.class);
        JobExperience jobExperience1 = new JobExperience();
        jobExperience1.setId(1L);
        JobExperience jobExperience2 = new JobExperience();
        jobExperience2.setId(jobExperience1.getId());
        assertThat(jobExperience1).isEqualTo(jobExperience2);
        jobExperience2.setId(2L);
        assertThat(jobExperience1).isNotEqualTo(jobExperience2);
        jobExperience1.setId(null);
        assertThat(jobExperience1).isNotEqualTo(jobExperience2);
    }
}
