package com.org.skillzag.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.org.skillzag.IntegrationTest;
import com.org.skillzag.domain.JobExperience;
import com.org.skillzag.repository.JobExperienceRepository;
import com.org.skillzag.service.dto.JobExperienceDTO;
import com.org.skillzag.service.mapper.JobExperienceMapper;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link JobExperienceResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class JobExperienceResourceIT {

    private static final String DEFAULT_DEGREE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_DEGREE_TITLE = "BBBBBBBBBB";

    private static final Integer DEFAULT_START_YEAR = 1;
    private static final Integer UPDATED_START_YEAR = 2;

    private static final Integer DEFAULT_END_YEAR = 1;
    private static final Integer UPDATED_END_YEAR = 2;

    private static final String DEFAULT_YEARS_OF_EXPERIENCE = "AAAAAAAAAA";
    private static final String UPDATED_YEARS_OF_EXPERIENCE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/job-experiences";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private JobExperienceRepository jobExperienceRepository;

    @Autowired
    private JobExperienceMapper jobExperienceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restJobExperienceMockMvc;

    private JobExperience jobExperience;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobExperience createEntity(EntityManager em) {
        JobExperience jobExperience = new JobExperience()
            .degreeTitle(DEFAULT_DEGREE_TITLE)
            .startYear(DEFAULT_START_YEAR)
            .endYear(DEFAULT_END_YEAR)
            .yearsOfExperience(DEFAULT_YEARS_OF_EXPERIENCE);
        return jobExperience;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static JobExperience createUpdatedEntity(EntityManager em) {
        JobExperience jobExperience = new JobExperience()
            .degreeTitle(UPDATED_DEGREE_TITLE)
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR)
            .yearsOfExperience(UPDATED_YEARS_OF_EXPERIENCE);
        return jobExperience;
    }

    @BeforeEach
    public void initTest() {
        jobExperience = createEntity(em);
    }

    @Test
    @Transactional
    void createJobExperience() throws Exception {
        int databaseSizeBeforeCreate = jobExperienceRepository.findAll().size();
        // Create the JobExperience
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(jobExperience);
        restJobExperienceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeCreate + 1);
        JobExperience testJobExperience = jobExperienceList.get(jobExperienceList.size() - 1);
        assertThat(testJobExperience.getDegreeTitle()).isEqualTo(DEFAULT_DEGREE_TITLE);
        assertThat(testJobExperience.getStartYear()).isEqualTo(DEFAULT_START_YEAR);
        assertThat(testJobExperience.getEndYear()).isEqualTo(DEFAULT_END_YEAR);
        assertThat(testJobExperience.getYearsOfExperience()).isEqualTo(DEFAULT_YEARS_OF_EXPERIENCE);
    }

    @Test
    @Transactional
    void createJobExperienceWithExistingId() throws Exception {
        // Create the JobExperience with an existing ID
        jobExperience.setId(1L);
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(jobExperience);

        int databaseSizeBeforeCreate = jobExperienceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restJobExperienceMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllJobExperiences() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get all the jobExperienceList
        restJobExperienceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(jobExperience.getId().intValue())))
            .andExpect(jsonPath("$.[*].degreeTitle").value(hasItem(DEFAULT_DEGREE_TITLE)))
            .andExpect(jsonPath("$.[*].startYear").value(hasItem(DEFAULT_START_YEAR)))
            .andExpect(jsonPath("$.[*].endYear").value(hasItem(DEFAULT_END_YEAR)))
            .andExpect(jsonPath("$.[*].yearsOfExperience").value(hasItem(DEFAULT_YEARS_OF_EXPERIENCE)));
    }

    @Test
    @Transactional
    void getJobExperience() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        // Get the jobExperience
        restJobExperienceMockMvc
            .perform(get(ENTITY_API_URL_ID, jobExperience.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(jobExperience.getId().intValue()))
            .andExpect(jsonPath("$.degreeTitle").value(DEFAULT_DEGREE_TITLE))
            .andExpect(jsonPath("$.startYear").value(DEFAULT_START_YEAR))
            .andExpect(jsonPath("$.endYear").value(DEFAULT_END_YEAR))
            .andExpect(jsonPath("$.yearsOfExperience").value(DEFAULT_YEARS_OF_EXPERIENCE));
    }

    @Test
    @Transactional
    void getNonExistingJobExperience() throws Exception {
        // Get the jobExperience
        restJobExperienceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewJobExperience() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        int databaseSizeBeforeUpdate = jobExperienceRepository.findAll().size();

        // Update the jobExperience
        JobExperience updatedJobExperience = jobExperienceRepository.findById(jobExperience.getId()).get();
        // Disconnect from session so that the updates on updatedJobExperience are not directly saved in db
        em.detach(updatedJobExperience);
        updatedJobExperience
            .degreeTitle(UPDATED_DEGREE_TITLE)
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR)
            .yearsOfExperience(UPDATED_YEARS_OF_EXPERIENCE);
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(updatedJobExperience);

        restJobExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobExperienceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO))
            )
            .andExpect(status().isOk());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeUpdate);
        JobExperience testJobExperience = jobExperienceList.get(jobExperienceList.size() - 1);
        assertThat(testJobExperience.getDegreeTitle()).isEqualTo(UPDATED_DEGREE_TITLE);
        assertThat(testJobExperience.getStartYear()).isEqualTo(UPDATED_START_YEAR);
        assertThat(testJobExperience.getEndYear()).isEqualTo(UPDATED_END_YEAR);
        assertThat(testJobExperience.getYearsOfExperience()).isEqualTo(UPDATED_YEARS_OF_EXPERIENCE);
    }

    @Test
    @Transactional
    void putNonExistingJobExperience() throws Exception {
        int databaseSizeBeforeUpdate = jobExperienceRepository.findAll().size();
        jobExperience.setId(count.incrementAndGet());

        // Create the JobExperience
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(jobExperience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, jobExperienceDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchJobExperience() throws Exception {
        int databaseSizeBeforeUpdate = jobExperienceRepository.findAll().size();
        jobExperience.setId(count.incrementAndGet());

        // Create the JobExperience
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(jobExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobExperienceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamJobExperience() throws Exception {
        int databaseSizeBeforeUpdate = jobExperienceRepository.findAll().size();
        jobExperience.setId(count.incrementAndGet());

        // Create the JobExperience
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(jobExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobExperienceMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateJobExperienceWithPatch() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        int databaseSizeBeforeUpdate = jobExperienceRepository.findAll().size();

        // Update the jobExperience using partial update
        JobExperience partialUpdatedJobExperience = new JobExperience();
        partialUpdatedJobExperience.setId(jobExperience.getId());

        partialUpdatedJobExperience.degreeTitle(UPDATED_DEGREE_TITLE).yearsOfExperience(UPDATED_YEARS_OF_EXPERIENCE);

        restJobExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobExperience.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobExperience))
            )
            .andExpect(status().isOk());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeUpdate);
        JobExperience testJobExperience = jobExperienceList.get(jobExperienceList.size() - 1);
        assertThat(testJobExperience.getDegreeTitle()).isEqualTo(UPDATED_DEGREE_TITLE);
        assertThat(testJobExperience.getStartYear()).isEqualTo(DEFAULT_START_YEAR);
        assertThat(testJobExperience.getEndYear()).isEqualTo(DEFAULT_END_YEAR);
        assertThat(testJobExperience.getYearsOfExperience()).isEqualTo(UPDATED_YEARS_OF_EXPERIENCE);
    }

    @Test
    @Transactional
    void fullUpdateJobExperienceWithPatch() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        int databaseSizeBeforeUpdate = jobExperienceRepository.findAll().size();

        // Update the jobExperience using partial update
        JobExperience partialUpdatedJobExperience = new JobExperience();
        partialUpdatedJobExperience.setId(jobExperience.getId());

        partialUpdatedJobExperience
            .degreeTitle(UPDATED_DEGREE_TITLE)
            .startYear(UPDATED_START_YEAR)
            .endYear(UPDATED_END_YEAR)
            .yearsOfExperience(UPDATED_YEARS_OF_EXPERIENCE);

        restJobExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedJobExperience.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedJobExperience))
            )
            .andExpect(status().isOk());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeUpdate);
        JobExperience testJobExperience = jobExperienceList.get(jobExperienceList.size() - 1);
        assertThat(testJobExperience.getDegreeTitle()).isEqualTo(UPDATED_DEGREE_TITLE);
        assertThat(testJobExperience.getStartYear()).isEqualTo(UPDATED_START_YEAR);
        assertThat(testJobExperience.getEndYear()).isEqualTo(UPDATED_END_YEAR);
        assertThat(testJobExperience.getYearsOfExperience()).isEqualTo(UPDATED_YEARS_OF_EXPERIENCE);
    }

    @Test
    @Transactional
    void patchNonExistingJobExperience() throws Exception {
        int databaseSizeBeforeUpdate = jobExperienceRepository.findAll().size();
        jobExperience.setId(count.incrementAndGet());

        // Create the JobExperience
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(jobExperience);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restJobExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, jobExperienceDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchJobExperience() throws Exception {
        int databaseSizeBeforeUpdate = jobExperienceRepository.findAll().size();
        jobExperience.setId(count.incrementAndGet());

        // Create the JobExperience
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(jobExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamJobExperience() throws Exception {
        int databaseSizeBeforeUpdate = jobExperienceRepository.findAll().size();
        jobExperience.setId(count.incrementAndGet());

        // Create the JobExperience
        JobExperienceDTO jobExperienceDTO = jobExperienceMapper.toDto(jobExperience);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restJobExperienceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(jobExperienceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the JobExperience in the database
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteJobExperience() throws Exception {
        // Initialize the database
        jobExperienceRepository.saveAndFlush(jobExperience);

        int databaseSizeBeforeDelete = jobExperienceRepository.findAll().size();

        // Delete the jobExperience
        restJobExperienceMockMvc
            .perform(delete(ENTITY_API_URL_ID, jobExperience.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<JobExperience> jobExperienceList = jobExperienceRepository.findAll();
        assertThat(jobExperienceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
