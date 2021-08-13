package com.org.skillzag.web.rest;

import com.org.skillzag.repository.AnnouncementsRepository;
import com.org.skillzag.service.AnnouncementsService;
import com.org.skillzag.service.dto.AnnouncementsDTO;
import com.org.skillzag.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.org.skillzag.domain.Announcements}.
 */
@RestController
@RequestMapping("/api")
public class AnnouncementsResource {

    private final Logger log = LoggerFactory.getLogger(AnnouncementsResource.class);

    private static final String ENTITY_NAME = "skillzaguseractivityAnnouncements";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnnouncementsService announcementsService;

    private final AnnouncementsRepository announcementsRepository;

    public AnnouncementsResource(AnnouncementsService announcementsService, AnnouncementsRepository announcementsRepository) {
        this.announcementsService = announcementsService;
        this.announcementsRepository = announcementsRepository;
    }

    /**
     * {@code POST  /announcements} : Create a new announcements.
     *
     * @param announcementsDTO the announcementsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new announcementsDTO, or with status {@code 400 (Bad Request)} if the announcements has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/announcements")
    public ResponseEntity<AnnouncementsDTO> createAnnouncements(@RequestBody AnnouncementsDTO announcementsDTO) throws URISyntaxException {
        log.debug("REST request to save Announcements : {}", announcementsDTO);
        if (announcementsDTO.getId() != null) {
            throw new BadRequestAlertException("A new announcements cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnnouncementsDTO result = announcementsService.save(announcementsDTO);
        return ResponseEntity
            .created(new URI("/api/announcements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /announcements/:id} : Updates an existing announcements.
     *
     * @param id the id of the announcementsDTO to save.
     * @param announcementsDTO the announcementsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated announcementsDTO,
     * or with status {@code 400 (Bad Request)} if the announcementsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the announcementsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/announcements/{id}")
    public ResponseEntity<AnnouncementsDTO> updateAnnouncements(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnnouncementsDTO announcementsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Announcements : {}, {}", id, announcementsDTO);
        if (announcementsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, announcementsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!announcementsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AnnouncementsDTO result = announcementsService.save(announcementsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, announcementsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /announcements/:id} : Partial updates given fields of an existing announcements, field will ignore if it is null
     *
     * @param id the id of the announcementsDTO to save.
     * @param announcementsDTO the announcementsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated announcementsDTO,
     * or with status {@code 400 (Bad Request)} if the announcementsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the announcementsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the announcementsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/announcements/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AnnouncementsDTO> partialUpdateAnnouncements(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AnnouncementsDTO announcementsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Announcements partially : {}, {}", id, announcementsDTO);
        if (announcementsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, announcementsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!announcementsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AnnouncementsDTO> result = announcementsService.partialUpdate(announcementsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, announcementsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /announcements} : get all the announcements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of announcements in body.
     */
    @GetMapping("/announcements")
    public ResponseEntity<List<AnnouncementsDTO>> getAllAnnouncements(Pageable pageable) {
        log.debug("REST request to get a page of Announcements");
        Page<AnnouncementsDTO> page = announcementsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /announcements/:id} : get the "id" announcements.
     *
     * @param id the id of the announcementsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the announcementsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/announcements/{id}")
    public ResponseEntity<AnnouncementsDTO> getAnnouncements(@PathVariable Long id) {
        log.debug("REST request to get Announcements : {}", id);
        Optional<AnnouncementsDTO> announcementsDTO = announcementsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(announcementsDTO);
    }

    /**
     * {@code DELETE  /announcements/:id} : delete the "id" announcements.
     *
     * @param id the id of the announcementsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/announcements/{id}")
    public ResponseEntity<Void> deleteAnnouncements(@PathVariable Long id) {
        log.debug("REST request to delete Announcements : {}", id);
        announcementsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
