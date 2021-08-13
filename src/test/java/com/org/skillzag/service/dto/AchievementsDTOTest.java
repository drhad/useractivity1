package com.org.skillzag.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.org.skillzag.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AchievementsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AchievementsDTO.class);
        AchievementsDTO achievementsDTO1 = new AchievementsDTO();
        achievementsDTO1.setId(1L);
        AchievementsDTO achievementsDTO2 = new AchievementsDTO();
        assertThat(achievementsDTO1).isNotEqualTo(achievementsDTO2);
        achievementsDTO2.setId(achievementsDTO1.getId());
        assertThat(achievementsDTO1).isEqualTo(achievementsDTO2);
        achievementsDTO2.setId(2L);
        assertThat(achievementsDTO1).isNotEqualTo(achievementsDTO2);
        achievementsDTO1.setId(null);
        assertThat(achievementsDTO1).isNotEqualTo(achievementsDTO2);
    }
}
