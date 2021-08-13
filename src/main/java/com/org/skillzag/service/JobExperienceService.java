package com.org.skillzag.service;

import com.org.skillzag.service.dto.JobExperienceDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.org.skillzag.domain.JobExperience}.
 */
public interface JobExperienceService {
    /**
     * Save a jobExperience.
     *
     * @param jobExperienceDTO the entity to save.
     * @return the persisted entity.
     */
    JobExperienceDTO save(JobExperienceDTO jobExperienceDTO);

    /**
     * Partially updates a jobExperience.
     *
     * @param jobExperienceDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JobExperienceDTO> partialUpdate(JobExperienceDTO jobExperienceDTO);

    /**
     * Get all the jobExperiences.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JobExperienceDTO> findAll(Pageable pageable);

    /**
     * Get the "id" jobExperience.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobExperienceDTO> findOne(Long id);

    /**
     * Delete the "id" jobExperience.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
