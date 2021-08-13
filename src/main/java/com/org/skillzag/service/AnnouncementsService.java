package com.org.skillzag.service;

import com.org.skillzag.service.dto.AnnouncementsDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.org.skillzag.domain.Announcements}.
 */
public interface AnnouncementsService {
    /**
     * Save a announcements.
     *
     * @param announcementsDTO the entity to save.
     * @return the persisted entity.
     */
    AnnouncementsDTO save(AnnouncementsDTO announcementsDTO);

    /**
     * Partially updates a announcements.
     *
     * @param announcementsDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnnouncementsDTO> partialUpdate(AnnouncementsDTO announcementsDTO);

    /**
     * Get all the announcements.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnnouncementsDTO> findAll(Pageable pageable);

    /**
     * Get the "id" announcements.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnnouncementsDTO> findOne(Long id);

    /**
     * Delete the "id" announcements.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
