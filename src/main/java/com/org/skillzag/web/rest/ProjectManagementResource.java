package com.org.skillzag.web.rest;

import com.org.skillzag.repository.ProjectManagementRepository;
import com.org.skillzag.service.ProjectManagementService;
import com.org.skillzag.service.dto.ProjectManagementDTO;
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
 * REST controller for managing {@link com.org.skillzag.domain.ProjectManagement}.
 */
@RestController
@RequestMapping("/api")
public class ProjectManagementResource {

    private final Logger log = LoggerFactory.getLogger(ProjectManagementResource.class);

    private static final String ENTITY_NAME = "skillzaguseractivityProjectManagement";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProjectManagementService projectManagementService;

    private final ProjectManagementRepository projectManagementRepository;

    public ProjectManagementResource(
        ProjectManagementService projectManagementService,
        ProjectManagementRepository projectManagementRepository
    ) {
        this.projectManagementService = projectManagementService;
        this.projectManagementRepository = projectManagementRepository;
    }

    /**
     * {@code POST  /project-managements} : Create a new projectManagement.
     *
     * @param projectManagementDTO the projectManagementDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new projectManagementDTO, or with status {@code 400 (Bad Request)} if the projectManagement has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/project-managements")
    public ResponseEntity<ProjectManagementDTO> createProjectManagement(@RequestBody ProjectManagementDTO projectManagementDTO)
        throws URISyntaxException {
        log.debug("REST request to save ProjectManagement : {}", projectManagementDTO);
        if (projectManagementDTO.getId() != null) {
            throw new BadRequestAlertException("A new projectManagement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ProjectManagementDTO result = projectManagementService.save(projectManagementDTO);
        return ResponseEntity
            .created(new URI("/api/project-managements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /project-managements/:id} : Updates an existing projectManagement.
     *
     * @param id the id of the projectManagementDTO to save.
     * @param projectManagementDTO the projectManagementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectManagementDTO,
     * or with status {@code 400 (Bad Request)} if the projectManagementDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the projectManagementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/project-managements/{id}")
    public ResponseEntity<ProjectManagementDTO> updateProjectManagement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectManagementDTO projectManagementDTO
    ) throws URISyntaxException {
        log.debug("REST request to update ProjectManagement : {}, {}", id, projectManagementDTO);
        if (projectManagementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectManagementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectManagementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        ProjectManagementDTO result = projectManagementService.save(projectManagementDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectManagementDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /project-managements/:id} : Partial updates given fields of an existing projectManagement, field will ignore if it is null
     *
     * @param id the id of the projectManagementDTO to save.
     * @param projectManagementDTO the projectManagementDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated projectManagementDTO,
     * or with status {@code 400 (Bad Request)} if the projectManagementDTO is not valid,
     * or with status {@code 404 (Not Found)} if the projectManagementDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the projectManagementDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/project-managements/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<ProjectManagementDTO> partialUpdateProjectManagement(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ProjectManagementDTO projectManagementDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update ProjectManagement partially : {}, {}", id, projectManagementDTO);
        if (projectManagementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, projectManagementDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!projectManagementRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ProjectManagementDTO> result = projectManagementService.partialUpdate(projectManagementDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, projectManagementDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /project-managements} : get all the projectManagements.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of projectManagements in body.
     */
    @GetMapping("/project-managements")
    public ResponseEntity<List<ProjectManagementDTO>> getAllProjectManagements(Pageable pageable) {
        log.debug("REST request to get a page of ProjectManagements");
        Page<ProjectManagementDTO> page = projectManagementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /project-managements/:id} : get the "id" projectManagement.
     *
     * @param id the id of the projectManagementDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the projectManagementDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/project-managements/{id}")
    public ResponseEntity<ProjectManagementDTO> getProjectManagement(@PathVariable Long id) {
        log.debug("REST request to get ProjectManagement : {}", id);
        Optional<ProjectManagementDTO> projectManagementDTO = projectManagementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(projectManagementDTO);
    }

    /**
     * {@code DELETE  /project-managements/:id} : delete the "id" projectManagement.
     *
     * @param id the id of the projectManagementDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/project-managements/{id}")
    public ResponseEntity<Void> deleteProjectManagement(@PathVariable Long id) {
        log.debug("REST request to delete ProjectManagement : {}", id);
        projectManagementService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
