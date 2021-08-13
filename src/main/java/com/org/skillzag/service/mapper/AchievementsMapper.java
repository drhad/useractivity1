package com.org.skillzag.service.mapper;

import com.org.skillzag.domain.*;
import com.org.skillzag.service.dto.AchievementsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Achievements} and its DTO {@link AchievementsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AchievementsMapper extends EntityMapper<AchievementsDTO, Achievements> {}
