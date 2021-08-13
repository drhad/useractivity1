package com.org.skillzag.web.rest;

import com.org.skillzag.repository.AchievementsRepository;
import com.org.skillzag.service.AchievementsService;
import com.org.skillzag.service.dto.AchievementsDTO;
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
 * REST controller for managing {@link com.org.skillzag.domain.Achievements}.
 */
@RestController
@RequestMapping("/api")
public class AchievementsResource {

    private final Logger log = LoggerFactory.getLogger(AchievementsResource.class);

    private static final String ENTITY_NAME = "skillzaguseractivityAchievements";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AchievementsService achievementsService;

    private final AchievementsRepository achievementsRepository;

    public AchievementsResource(AchievementsService achievementsService, AchievementsRepository achievementsRepository) {
        this.achievementsService = achievementsService;
        this.achievementsRepository = achievementsRepository;
    }

    /**
     * {@code POST  /achievements} : Create a new achievements.
     *
     * @param achievementsDTO the achievementsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new achievementsDTO, or with status {@code 400 (Bad Request)} if the achievements has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/achievements")
    public ResponseEntity<AchievementsDTO> createAchievements(@RequestBody AchievementsDTO achievementsDTO) throws URISyntaxException {
        log.debug("REST request to save Achievements : {}", achievementsDTO);
        if (achievementsDTO.getId() != null) {
            throw new BadRequestAlertException("A new achievements cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AchievementsDTO result = achievementsService.save(achievementsDTO);
        return ResponseEntity
            .created(new URI("/api/achievements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /achievements/:id} : Updates an existing achievements.
     *
     * @param id the id of the achievementsDTO to save.
     * @param achievementsDTO the achievementsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated achievementsDTO,
     * or with status {@code 400 (Bad Request)} if the achievementsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the achievementsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/achievements/{id}")
    public ResponseEntity<AchievementsDTO> updateAchievements(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AchievementsDTO achievementsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Achievements : {}, {}", id, achievementsDTO);
        if (achievementsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, achievementsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!achievementsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AchievementsDTO result = achievementsService.save(achievementsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, achievementsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /achievements/:id} : Partial updates given fields of an existing achievements, field will ignore if it is null
     *
     * @param id the id of the achievementsDTO to save.
     * @param achievementsDTO the achievementsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated achievementsDTO,
     * or with status {@code 400 (Bad Request)} if the achievementsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the achievementsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the achievementsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/achievements/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AchievementsDTO> partialUpdateAchievements(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AchievementsDTO achievementsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Achievements partially : {}, {}", id, achievementsDTO);
        if (achievementsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, achievementsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!achievementsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AchievementsDTO> result = achievementsService.partialUpdate(achievementsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, achievementsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /achievements} : get all the achievements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of achievements in body.
     */
    @GetMapping("/achievements")
    public ResponseEntity<List<AchievementsDTO>> getAllAchievements(Pageable pageable) {
        log.debug("REST request to get a page of Achievements");
        Page<AchievementsDTO> page = achievementsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /achievements/:id} : get the "id" achievements.
     *
     * @param id the id of the achievementsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the achievementsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/achievements/{id}")
    public ResponseEntity<AchievementsDTO> getAchievements(@PathVariable Long id) {
        log.debug("REST request to get Achievements : {}", id);
        Optional<AchievementsDTO> achievementsDTO = achievementsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(achievementsDTO);
    }

    /**
     * {@code DELETE  /achievements/:id} : delete the "id" achievements.
     *
     * @param id the id of the achievementsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/achievements/{id}")
    public ResponseEntity<Void> deleteAchievements(@PathVariable Long id) {
        log.debug("REST request to delete Achievements : {}", id);
        achievementsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
