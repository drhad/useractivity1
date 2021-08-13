package com.org.skillzag.web.rest;

import com.org.skillzag.repository.JobExperienceRepository;
import com.org.skillzag.service.JobExperienceService;
import com.org.skillzag.service.dto.JobExperienceDTO;
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
 * REST controller for managing {@link com.org.skillzag.domain.JobExperience}.
 */
@RestController
@RequestMapping("/api")
public class JobExperienceResource {

    private final Logger log = LoggerFactory.getLogger(JobExperienceResource.class);

    private static final String ENTITY_NAME = "skillzaguseractivityJobExperience";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final JobExperienceService jobExperienceService;

    private final JobExperienceRepository jobExperienceRepository;

    public JobExperienceResource(JobExperienceService jobExperienceService, JobExperienceRepository jobExperienceRepository) {
        this.jobExperienceService = jobExperienceService;
        this.jobExperienceRepository = jobExperienceRepository;
    }

    /**
     * {@code POST  /job-experiences} : Create a new jobExperience.
     *
     * @param jobExperienceDTO the jobExperienceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobExperienceDTO, or with status {@code 400 (Bad Request)} if the jobExperience has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/job-experiences")
    public ResponseEntity<JobExperienceDTO> createJobExperience(@RequestBody JobExperienceDTO jobExperienceDTO) throws URISyntaxException {
        log.debug("REST request to save JobExperience : {}", jobExperienceDTO);
        if (jobExperienceDTO.getId() != null) {
            throw new BadRequestAlertException("A new jobExperience cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JobExperienceDTO result = jobExperienceService.save(jobExperienceDTO);
        return ResponseEntity
            .created(new URI("/api/job-experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /job-experiences/:id} : Updates an existing jobExperience.
     *
     * @param id the id of the jobExperienceDTO to save.
     * @param jobExperienceDTO the jobExperienceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobExperienceDTO,
     * or with status {@code 400 (Bad Request)} if the jobExperienceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the jobExperienceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/job-experiences/{id}")
    public ResponseEntity<JobExperienceDTO> updateJobExperience(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobExperienceDTO jobExperienceDTO
    ) throws URISyntaxException {
        log.debug("REST request to update JobExperience : {}, {}", id, jobExperienceDTO);
        if (jobExperienceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobExperienceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobExperienceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        JobExperienceDTO result = jobExperienceService.save(jobExperienceDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jobExperienceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /job-experiences/:id} : Partial updates given fields of an existing jobExperience, field will ignore if it is null
     *
     * @param id the id of the jobExperienceDTO to save.
     * @param jobExperienceDTO the jobExperienceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobExperienceDTO,
     * or with status {@code 400 (Bad Request)} if the jobExperienceDTO is not valid,
     * or with status {@code 404 (Not Found)} if the jobExperienceDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobExperienceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/job-experiences/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<JobExperienceDTO> partialUpdateJobExperience(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody JobExperienceDTO jobExperienceDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update JobExperience partially : {}, {}", id, jobExperienceDTO);
        if (jobExperienceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, jobExperienceDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!jobExperienceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<JobExperienceDTO> result = jobExperienceService.partialUpdate(jobExperienceDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, jobExperienceDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /job-experiences} : get all the jobExperiences.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobExperiences in body.
     */
    @GetMapping("/job-experiences")
    public ResponseEntity<List<JobExperienceDTO>> getAllJobExperiences(Pageable pageable) {
        log.debug("REST request to get a page of JobExperiences");
        Page<JobExperienceDTO> page = jobExperienceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /job-experiences/:id} : get the "id" jobExperience.
     *
     * @param id the id of the jobExperienceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobExperienceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/job-experiences/{id}")
    public ResponseEntity<JobExperienceDTO> getJobExperience(@PathVariable Long id) {
        log.debug("REST request to get JobExperience : {}", id);
        Optional<JobExperienceDTO> jobExperienceDTO = jobExperienceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(jobExperienceDTO);
    }

    /**
     * {@code DELETE  /job-experiences/:id} : delete the "id" jobExperience.
     *
     * @param id the id of the jobExperienceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/job-experiences/{id}")
    public ResponseEntity<Void> deleteJobExperience(@PathVariable Long id) {
        log.debug("REST request to delete JobExperience : {}", id);
        jobExperienceService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
