package com.org.skillzag.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.org.skillzag.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnnouncementsDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnnouncementsDTO.class);
        AnnouncementsDTO announcementsDTO1 = new AnnouncementsDTO();
        announcementsDTO1.setId(1L);
        AnnouncementsDTO announcementsDTO2 = new AnnouncementsDTO();
        assertThat(announcementsDTO1).isNotEqualTo(announcementsDTO2);
        announcementsDTO2.setId(announcementsDTO1.getId());
        assertThat(announcementsDTO1).isEqualTo(announcementsDTO2);
        announcementsDTO2.setId(2L);
        assertThat(announcementsDTO1).isNotEqualTo(announcementsDTO2);
        announcementsDTO1.setId(null);
        assertThat(announcementsDTO1).isNotEqualTo(announcementsDTO2);
    }
}
