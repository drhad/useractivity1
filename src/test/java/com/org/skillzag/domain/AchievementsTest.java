package com.org.skillzag.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.org.skillzag.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AchievementsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Achievements.class);
        Achievements achievements1 = new Achievements();
        achievements1.setId(1L);
        Achievements achievements2 = new Achievements();
        achievements2.setId(achievements1.getId());
        assertThat(achievements1).isEqualTo(achievements2);
        achievements2.setId(2L);
        assertThat(achievements1).isNotEqualTo(achievements2);
        achievements1.setId(null);
        assertThat(achievements1).isNotEqualTo(achievements2);
    }
}
