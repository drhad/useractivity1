package com.org.skillzag.service;

import com.org.skillzag.service.dto.AchievementsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.org.skillzag.domain.Achievements}.
 */
public interface AchievementsService {
    /**
     * Save a achievements.
     *
     * @param achievementsDTO the entity to save.
     * @return the persisted entity.
     */
    AchievementsDTO save(AchievementsDTO achievementsDTO);

    /**
     * Partially updates a achievements.
     *
     * @param achievementsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AchievementsDTO> partialUpdate(AchievementsDTO achievementsDTO);

    /**
     * Get all the achievements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AchievementsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" achievements.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AchievementsDTO> findOne(Long id);

    /**
     * Delete the "id" achievements.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
