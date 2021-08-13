package com.org.skillzag.service.mapper;

import com.org.skillzag.domain.*;
import com.org.skillzag.service.dto.ProjectManagementDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link ProjectManagement} and its DTO {@link ProjectManagementDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ProjectManagementMapper extends EntityMapper<ProjectManagementDTO, ProjectManagement> {}
