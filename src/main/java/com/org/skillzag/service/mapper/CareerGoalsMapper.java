package com.org.skillzag.service.mapper;

import com.org.skillzag.domain.*;
import com.org.skillzag.service.dto.CareerGoalsDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CareerGoals} and its DTO {@link CareerGoalsDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CareerGoalsMapper extends EntityMapper<CareerGoalsDTO, CareerGoals> {}
