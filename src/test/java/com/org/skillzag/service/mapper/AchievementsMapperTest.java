package com.org.skillzag.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AchievementsMapperTest {

    private AchievementsMapper achievementsMapper;

    @BeforeEach
    public void setUp() {
        achievementsMapper = new AchievementsMapperImpl();
    }
}
