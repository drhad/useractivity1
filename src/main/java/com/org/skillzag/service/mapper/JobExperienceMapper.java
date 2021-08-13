package com.org.skillzag.service.mapper;

import com.org.skillzag.domain.*;
import com.org.skillzag.service.dto.JobExperienceDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link JobExperience} and its DTO {@link JobExperienceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface JobExperienceMapper extends EntityMapper<JobExperienceDTO, JobExperience> {}
