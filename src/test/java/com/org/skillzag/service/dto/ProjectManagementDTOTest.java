package com.org.skillzag.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.org.skillzag.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectManagementDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProjectManagementDTO.class);
        ProjectManagementDTO projectManagementDTO1 = new ProjectManagementDTO();
        projectManagementDTO1.setId(1L);
        ProjectManagementDTO projectManagementDTO2 = new ProjectManagementDTO();
        assertThat(projectManagementDTO1).isNotEqualTo(projectManagementDTO2);
        projectManagementDTO2.setId(projectManagementDTO1.getId());
        assertThat(projectManagementDTO1).isEqualTo(projectManagementDTO2);
        projectManagementDTO2.setId(2L);
        assertThat(projectManagementDTO1).isNotEqualTo(projectManagementDTO2);
        projectManagementDTO1.setId(null);
        assertThat(projectManagementDTO1).isNotEqualTo(projectManagementDTO2);
    }
}
