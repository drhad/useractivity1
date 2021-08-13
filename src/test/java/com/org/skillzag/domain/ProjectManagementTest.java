package com.org.skillzag.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.org.skillzag.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectManagementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectManagement.class);
        ProjectManagement projectManagement1 = new ProjectManagement();
        projectManagement1.setId(1L);
        ProjectManagement projectManagement2 = new ProjectManagement();
        projectManagement2.setId(projectManagement1.getId());
        assertThat(projectManagement1).isEqualTo(projectManagement2);
        projectManagement2.setId(2L);
        assertThat(projectManagement1).isNotEqualTo(projectManagement2);
        projectManagement1.setId(null);
        assertThat(projectManagement1).isNotEqualTo(projectManagement2);
    }
}
