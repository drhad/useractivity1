package com.org.skillzag.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AnnouncementsMapperTest {

    private AnnouncementsMapper announcementsMapper;

    @BeforeEach
    public void setUp() {
        announcementsMapper = new AnnouncementsMapperImpl();
    }
}
