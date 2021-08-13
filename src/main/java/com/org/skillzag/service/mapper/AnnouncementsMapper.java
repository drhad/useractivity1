package com.org.skillzag.service.mapper;

import com.org.skillzag.domain.*;
import com.org.skillzag.service.dto.AnnouncementsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Announcements} and its DTO {@link AnnouncementsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AnnouncementsMapper extends EntityMapper<AnnouncementsDTO, Announcements> {}
