package com.org.skillzag.service.impl;

import com.org.skillzag.domain.CareerGoals;
import com.org.skillzag.repository.CareerGoalsRepository;
import com.org.skillzag.service.CareerGoalsService;
import com.org.skillzag.service.dto.CareerGoalsDTO;
import com.org.skillzag.service.mapper.CareerGoalsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CareerGoals}.
 */
@Service
@Transactional
public class CareerGoalsServiceImpl implements CareerGoalsService {

    private final Logger log = LoggerFactory.getLogger(CareerGoalsServiceImpl.class);

    private final CareerGoalsRepository careerGoalsRepository;

    private final CareerGoalsMapper careerGoalsMapper;

    public CareerGoalsServiceImpl(CareerGoalsRepository careerGoalsRepository, CareerGoalsMapper careerGoalsMapper) {
        this.careerGoalsRepository = careerGoalsRepository;
        this.careerGoalsMapper = careerGoalsMapper;
    }

    @Override
    public CareerGoalsDTO save(CareerGoalsDTO careerGoalsDTO) {
        log.debug("Request to save CareerGoals : {}", careerGoalsDTO);
        CareerGoals careerGoals = careerGoalsMapper.toEntity(careerGoalsDTO);
        careerGoals = careerGoalsRepository.save(careerGoals);
        return careerGoalsMapper.toDto(careerGoals);
    }

    @Override
    public Optional<CareerGoalsDTO> partialUpdate(CareerGoalsDTO careerGoalsDTO) {
        log.debug("Request to partially update CareerGoals : {}", careerGoalsDTO);

        return careerGoalsRepository
            .findById(careerGoalsDTO.getId())
            .map(
                existingCareerGoals -> {
                    careerGoalsMapper.partialUpdate(existingCareerGoals, careerGoalsDTO);

                    return existingCareerGoals;
                }
            )
            .map(careerGoalsRepository::save)
            .map(careerGoalsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CareerGoalsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CareerGoals");
        return careerGoalsRepository.findAll(pageable).map(careerGoalsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CareerGoalsDTO> findOne(Long id) {
        log.debug("Request to get CareerGoals : {}", id);
        return careerGoalsRepository.findById(id).map(careerGoalsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete CareerGoals : {}", id);
        careerGoalsRepository.deleteById(id);
    }
}
