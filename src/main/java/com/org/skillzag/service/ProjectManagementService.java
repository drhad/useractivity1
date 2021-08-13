package com.org.skillzag.service;

import com.org.skillzag.service.dto.ProjectManagementDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.org.skillzag.domain.ProjectManagement}.
 */
public interface ProjectManagementService {
    /**
     * Save a projectManagement.
     *
     * @param projectManagementDTO the entity to save.
     * @return the persisted entity.
     */
    ProjectManagementDTO save(ProjectManagementDTO projectManagementDTO);

    /**
     * Partially updates a projectManagement.
     *
     * @param projectManagementDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProjectManagementDTO> partialUpdate(ProjectManagementDTO projectManagementDTO);

    /**
     * Get all the projectManagements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProjectManagementDTO> findAll(Pageable pageable);

    /**
     * Get the "id" projectManagement.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProjectManagementDTO> findOne(Long id);

    /**
     * Delete the "id" projectManagement.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
