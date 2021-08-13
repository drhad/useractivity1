package com.org.skillzag.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.org.skillzag.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnnouncementsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Announcements.class);
        Announcements announcements1 = new Announcements();
        announcements1.setId(1L);
        Announcements announcements2 = new Announcements();
        announcements2.setId(announcements1.getId());
        assertThat(announcements1).isEqualTo(announcements2);
        announcements2.setId(2L);
        assertThat(announcements1).isNotEqualTo(announcements2);
        announcements1.setId(null);
        assertThat(announcements1).isNotEqualTo(announcements2);
    }
}
