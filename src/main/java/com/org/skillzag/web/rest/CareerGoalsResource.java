package com.org.skillzag.web.rest;

import com.org.skillzag.repository.CareerGoalsRepository;
import com.org.skillzag.service.CareerGoalsService;
import com.org.skillzag.service.dto.CareerGoalsDTO;
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
 * REST controller for managing {@link com.org.skillzag.domain.CareerGoals}.
 */
@RestController
@RequestMapping("/api")
public class CareerGoalsResource {

    private final Logger log = LoggerFactory.getLogger(CareerGoalsResource.class);

    private static final String ENTITY_NAME = "skillzaguseractivityCareerGoals";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CareerGoalsService careerGoalsService;

    private final CareerGoalsRepository careerGoalsRepository;

    public CareerGoalsResource(CareerGoalsService careerGoalsService, CareerGoalsRepository careerGoalsRepository) {
        this.careerGoalsService = careerGoalsService;
        this.careerGoalsRepository = careerGoalsRepository;
    }

    /**
     * {@code POST  /career-goals} : Create a new careerGoals.
     *
     * @param careerGoalsDTO the careerGoalsDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new careerGoalsDTO, or with status {@code 400 (Bad Request)} if the careerGoals has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/career-goals")
    public ResponseEntity<CareerGoalsDTO> createCareerGoals(@RequestBody CareerGoalsDTO careerGoalsDTO) throws URISyntaxException {
        log.debug("REST request to save CareerGoals : {}", careerGoalsDTO);
        if (careerGoalsDTO.getId() != null) {
            throw new BadRequestAlertException("A new careerGoals cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CareerGoalsDTO result = careerGoalsService.save(careerGoalsDTO);
        return ResponseEntity
            .created(new URI("/api/career-goals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /career-goals/:id} : Updates an existing careerGoals.
     *
     * @param id the id of the careerGoalsDTO to save.
     * @param careerGoalsDTO the careerGoalsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated careerGoalsDTO,
     * or with status {@code 400 (Bad Request)} if the careerGoalsDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the careerGoalsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/career-goals/{id}")
    public ResponseEntity<CareerGoalsDTO> updateCareerGoals(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CareerGoalsDTO careerGoalsDTO
    ) throws URISyntaxException {
        log.debug("REST request to update CareerGoals : {}, {}", id, careerGoalsDTO);
        if (careerGoalsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, careerGoalsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!careerGoalsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        CareerGoalsDTO result = careerGoalsService.save(careerGoalsDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, careerGoalsDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /career-goals/:id} : Partial updates given fields of an existing careerGoals, field will ignore if it is null
     *
     * @param id the id of the careerGoalsDTO to save.
     * @param careerGoalsDTO the careerGoalsDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated careerGoalsDTO,
     * or with status {@code 400 (Bad Request)} if the careerGoalsDTO is not valid,
     * or with status {@code 404 (Not Found)} if the careerGoalsDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the careerGoalsDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/career-goals/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<CareerGoalsDTO> partialUpdateCareerGoals(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CareerGoalsDTO careerGoalsDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update CareerGoals partially : {}, {}", id, careerGoalsDTO);
        if (careerGoalsDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, careerGoalsDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!careerGoalsRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CareerGoalsDTO> result = careerGoalsService.partialUpdate(careerGoalsDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, careerGoalsDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /career-goals} : get all the careerGoals.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of careerGoals in body.
     */
    @GetMapping("/career-goals")
    public ResponseEntity<List<CareerGoalsDTO>> getAllCareerGoals(Pageable pageable) {
        log.debug("REST request to get a page of CareerGoals");
        Page<CareerGoalsDTO> page = careerGoalsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /career-goals/:id} : get the "id" careerGoals.
     *
     * @param id the id of the careerGoalsDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the careerGoalsDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/career-goals/{id}")
    public ResponseEntity<CareerGoalsDTO> getCareerGoals(@PathVariable Long id) {
        log.debug("REST request to get CareerGoals : {}", id);
        Optional<CareerGoalsDTO> careerGoalsDTO = careerGoalsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(careerGoalsDTO);
    }

    /**
     * {@code DELETE  /career-goals/:id} : delete the "id" careerGoals.
     *
     * @param id the id of the careerGoalsDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/career-goals/{id}")
    public ResponseEntity<Void> deleteCareerGoals(@PathVariable Long id) {
        log.debug("REST request to delete CareerGoals : {}", id);
        careerGoalsService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
