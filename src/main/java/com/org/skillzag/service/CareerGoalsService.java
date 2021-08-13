package com.org.skillzag.service;

import com.org.skillzag.service.dto.CareerGoalsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.org.skillzag.domain.CareerGoals}.
 */
public interface CareerGoalsService {
    /**
     * Save a careerGoals.
     *
     * @param careerGoalsDTO the entity to save.
     * @return the persisted entity.
     */
    CareerGoalsDTO save(CareerGoalsDTO careerGoalsDTO);

    /**
     * Partially updates a careerGoals.
     *
     * @param careerGoalsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CareerGoalsDTO> partialUpdate(CareerGoalsDTO careerGoalsDTO);

    /**
     * Get all the careerGoals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CareerGoalsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" careerGoals.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CareerGoalsDTO> findOne(Long id);

    /**
     * Delete the "id" careerGoals.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
