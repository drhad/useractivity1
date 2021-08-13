package com.org.skillzag.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProjectManagementMapperTest {

    private ProjectManagementMapper projectManagementMapper;

    @BeforeEach
    public void setUp() {
        projectManagementMapper = new ProjectManagementMapperImpl();
    }
}
