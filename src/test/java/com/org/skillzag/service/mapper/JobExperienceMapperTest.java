package com.org.skillzag.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JobExperienceMapperTest {

    private JobExperienceMapper jobExperienceMapper;

    @BeforeEach
    public void setUp() {
        jobExperienceMapper = new JobExperienceMapperImpl();
    }
}
