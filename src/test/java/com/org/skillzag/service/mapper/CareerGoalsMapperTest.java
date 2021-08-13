package com.org.skillzag.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CareerGoalsMapperTest {

    private CareerGoalsMapper careerGoalsMapper;

    @BeforeEach
    public void setUp() {
        careerGoalsMapper = new CareerGoalsMapperImpl();
    }
}
