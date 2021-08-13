package com.org.skillzag.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.org.skillzag.IntegrationTest;
import com.org.skillzag.domain.CareerGoals;
import com.org.skillzag.domain.enumeration.GoalStatus;
import com.org.skillzag.domain.enumeration.GoalType;
import com.org.skillzag.repository.CareerGoalsRepository;
import com.org.skillzag.service.dto.CareerGoalsDTO;
import com.org.skillzag.service.mapper.CareerGoalsMapper;
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
 * Integration tests for the {@link CareerGoalsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CareerGoalsResourceIT {

    private static final String DEFAULT_INDUSTRY = "AAAAAAAAAA";
    private static final String UPDATED_INDUSTRY = "BBBBBBBBBB";

    private static final String DEFAULT_GOAL = "AAAAAAAAAA";
    private static final String UPDATED_GOAL = "BBBBBBBBBB";

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final GoalStatus DEFAULT_STATUS = GoalStatus.INPROGRESS;
    private static final GoalStatus UPDATED_STATUS = GoalStatus.STARTED;

    private static final GoalType DEFAULT_GOAL_TYPE = GoalType.SHORTTERM;
    private static final GoalType UPDATED_GOAL_TYPE = GoalType.LONGTERM;

    private static final String ENTITY_API_URL = "/api/career-goals";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CareerGoalsRepository careerGoalsRepository;

    @Autowired
    private CareerGoalsMapper careerGoalsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCareerGoalsMockMvc;

    private CareerGoals careerGoals;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CareerGoals createEntity(EntityManager em) {
        CareerGoals careerGoals = new CareerGoals()
            .industry(DEFAULT_INDUSTRY)
            .goal(DEFAULT_GOAL)
            .time(DEFAULT_TIME)
            .status(DEFAULT_STATUS)
            .goalType(DEFAULT_GOAL_TYPE);
        return careerGoals;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CareerGoals createUpdatedEntity(EntityManager em) {
        CareerGoals careerGoals = new CareerGoals()
            .industry(UPDATED_INDUSTRY)
            .goal(UPDATED_GOAL)
            .time(UPDATED_TIME)
            .status(UPDATED_STATUS)
            .goalType(UPDATED_GOAL_TYPE);
        return careerGoals;
    }

    @BeforeEach
    public void initTest() {
        careerGoals = createEntity(em);
    }

    @Test
    @Transactional
    void createCareerGoals() throws Exception {
        int databaseSizeBeforeCreate = careerGoalsRepository.findAll().size();
        // Create the CareerGoals
        CareerGoalsDTO careerGoalsDTO = careerGoalsMapper.toDto(careerGoals);
        restCareerGoalsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(careerGoalsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the CareerGoals in the database
        List<CareerGoals> careerGoalsList = careerGoalsRepository.findAll();
        assertThat(careerGoalsList).hasSize(databaseSizeBeforeCreate + 1);
        CareerGoals testCareerGoals = careerGoalsList.get(careerGoalsList.size() - 1);
        assertThat(testCareerGoals.getIndustry()).isEqualTo(DEFAULT_INDUSTRY);
        assertThat(testCareerGoals.getGoal()).isEqualTo(DEFAULT_GOAL);
        assertThat(testCareerGoals.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testCareerGoals.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCareerGoals.getGoalType()).isEqualTo(DEFAULT_GOAL_TYPE);
    }

    @Test
    @Transactional
    void createCareerGoalsWithExistingId() throws Exception {
        // Create the CareerGoals with an existing ID
        careerGoals.setId(1L);
        CareerGoalsDTO careerGoalsDTO = careerGoalsMapper.toDto(careerGoals);

        int databaseSizeBeforeCreate = careerGoalsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCareerGoalsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(careerGoalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CareerGoals in the database
        List<CareerGoals> careerGoalsList = careerGoalsRepository.findAll();
        assertThat(careerGoalsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCareerGoals() throws Exception {
        // Initialize the database
        careerGoalsRepository.saveAndFlush(careerGoals);

        // Get all the careerGoalsList
        restCareerGoalsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(careerGoals.getId().intValue())))
            .andExpect(jsonPath("$.[*].industry").value(hasItem(DEFAULT_INDUSTRY)))
            .andExpect(jsonPath("$.[*].goal").value(hasItem(DEFAULT_GOAL)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].goalType").value(hasItem(DEFAULT_GOAL_TYPE.toString())));
    }

    @Test
    @Transactional
    void getCareerGoals() throws Exception {
        // Initialize the database
        careerGoalsRepository.saveAndFlush(careerGoals);

        // Get the careerGoals
        restCareerGoalsMockMvc
            .perform(get(ENTITY_API_URL_ID, careerGoals.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(careerGoals.getId().intValue()))
            .andExpect(jsonPath("$.industry").value(DEFAULT_INDUSTRY))
            .andExpect(jsonPath("$.goal").value(DEFAULT_GOAL))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.goalType").value(DEFAULT_GOAL_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCareerGoals() throws Exception {
        // Get the careerGoals
        restCareerGoalsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCareerGoals() throws Exception {
        // Initialize the database
        careerGoalsRepository.saveAndFlush(careerGoals);

        int databaseSizeBeforeUpdate = careerGoalsRepository.findAll().size();

        // Update the careerGoals
        CareerGoals updatedCareerGoals = careerGoalsRepository.findById(careerGoals.getId()).get();
        // Disconnect from session so that the updates on updatedCareerGoals are not directly saved in db
        em.detach(updatedCareerGoals);
        updatedCareerGoals
            .industry(UPDATED_INDUSTRY)
            .goal(UPDATED_GOAL)
            .time(UPDATED_TIME)
            .status(UPDATED_STATUS)
            .goalType(UPDATED_GOAL_TYPE);
        CareerGoalsDTO careerGoalsDTO = careerGoalsMapper.toDto(updatedCareerGoals);

        restCareerGoalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, careerGoalsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(careerGoalsDTO))
            )
            .andExpect(status().isOk());

        // Validate the CareerGoals in the database
        List<CareerGoals> careerGoalsList = careerGoalsRepository.findAll();
        assertThat(careerGoalsList).hasSize(databaseSizeBeforeUpdate);
        CareerGoals testCareerGoals = careerGoalsList.get(careerGoalsList.size() - 1);
        assertThat(testCareerGoals.getIndustry()).isEqualTo(UPDATED_INDUSTRY);
        assertThat(testCareerGoals.getGoal()).isEqualTo(UPDATED_GOAL);
        assertThat(testCareerGoals.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testCareerGoals.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCareerGoals.getGoalType()).isEqualTo(UPDATED_GOAL_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingCareerGoals() throws Exception {
        int databaseSizeBeforeUpdate = careerGoalsRepository.findAll().size();
        careerGoals.setId(count.incrementAndGet());

        // Create the CareerGoals
        CareerGoalsDTO careerGoalsDTO = careerGoalsMapper.toDto(careerGoals);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCareerGoalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, careerGoalsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(careerGoalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CareerGoals in the database
        List<CareerGoals> careerGoalsList = careerGoalsRepository.findAll();
        assertThat(careerGoalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCareerGoals() throws Exception {
        int databaseSizeBeforeUpdate = careerGoalsRepository.findAll().size();
        careerGoals.setId(count.incrementAndGet());

        // Create the CareerGoals
        CareerGoalsDTO careerGoalsDTO = careerGoalsMapper.toDto(careerGoals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCareerGoalsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(careerGoalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CareerGoals in the database
        List<CareerGoals> careerGoalsList = careerGoalsRepository.findAll();
        assertThat(careerGoalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCareerGoals() throws Exception {
        int databaseSizeBeforeUpdate = careerGoalsRepository.findAll().size();
        careerGoals.setId(count.incrementAndGet());

        // Create the CareerGoals
        CareerGoalsDTO careerGoalsDTO = careerGoalsMapper.toDto(careerGoals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCareerGoalsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(careerGoalsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CareerGoals in the database
        List<CareerGoals> careerGoalsList = careerGoalsRepository.findAll();
        assertThat(careerGoalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCareerGoalsWithPatch() throws Exception {
        // Initialize the database
        careerGoalsRepository.saveAndFlush(careerGoals);

        int databaseSizeBeforeUpdate = careerGoalsRepository.findAll().size();

        // Update the careerGoals using partial update
        CareerGoals partialUpdatedCareerGoals = new CareerGoals();
        partialUpdatedCareerGoals.setId(careerGoals.getId());

        partialUpdatedCareerGoals.goal(UPDATED_GOAL).goalType(UPDATED_GOAL_TYPE);

        restCareerGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCareerGoals.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCareerGoals))
            )
            .andExpect(status().isOk());

        // Validate the CareerGoals in the database
        List<CareerGoals> careerGoalsList = careerGoalsRepository.findAll();
        assertThat(careerGoalsList).hasSize(databaseSizeBeforeUpdate);
        CareerGoals testCareerGoals = careerGoalsList.get(careerGoalsList.size() - 1);
        assertThat(testCareerGoals.getIndustry()).isEqualTo(DEFAULT_INDUSTRY);
        assertThat(testCareerGoals.getGoal()).isEqualTo(UPDATED_GOAL);
        assertThat(testCareerGoals.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testCareerGoals.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testCareerGoals.getGoalType()).isEqualTo(UPDATED_GOAL_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateCareerGoalsWithPatch() throws Exception {
        // Initialize the database
        careerGoalsRepository.saveAndFlush(careerGoals);

        int databaseSizeBeforeUpdate = careerGoalsRepository.findAll().size();

        // Update the careerGoals using partial update
        CareerGoals partialUpdatedCareerGoals = new CareerGoals();
        partialUpdatedCareerGoals.setId(careerGoals.getId());

        partialUpdatedCareerGoals
            .industry(UPDATED_INDUSTRY)
            .goal(UPDATED_GOAL)
            .time(UPDATED_TIME)
            .status(UPDATED_STATUS)
            .goalType(UPDATED_GOAL_TYPE);

        restCareerGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCareerGoals.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCareerGoals))
            )
            .andExpect(status().isOk());

        // Validate the CareerGoals in the database
        List<CareerGoals> careerGoalsList = careerGoalsRepository.findAll();
        assertThat(careerGoalsList).hasSize(databaseSizeBeforeUpdate);
        CareerGoals testCareerGoals = careerGoalsList.get(careerGoalsList.size() - 1);
        assertThat(testCareerGoals.getIndustry()).isEqualTo(UPDATED_INDUSTRY);
        assertThat(testCareerGoals.getGoal()).isEqualTo(UPDATED_GOAL);
        assertThat(testCareerGoals.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testCareerGoals.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testCareerGoals.getGoalType()).isEqualTo(UPDATED_GOAL_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingCareerGoals() throws Exception {
        int databaseSizeBeforeUpdate = careerGoalsRepository.findAll().size();
        careerGoals.setId(count.incrementAndGet());

        // Create the CareerGoals
        CareerGoalsDTO careerGoalsDTO = careerGoalsMapper.toDto(careerGoals);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCareerGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, careerGoalsDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(careerGoalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CareerGoals in the database
        List<CareerGoals> careerGoalsList = careerGoalsRepository.findAll();
        assertThat(careerGoalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCareerGoals() throws Exception {
        int databaseSizeBeforeUpdate = careerGoalsRepository.findAll().size();
        careerGoals.setId(count.incrementAndGet());

        // Create the CareerGoals
        CareerGoalsDTO careerGoalsDTO = careerGoalsMapper.toDto(careerGoals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCareerGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(careerGoalsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CareerGoals in the database
        List<CareerGoals> careerGoalsList = careerGoalsRepository.findAll();
        assertThat(careerGoalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCareerGoals() throws Exception {
        int databaseSizeBeforeUpdate = careerGoalsRepository.findAll().size();
        careerGoals.setId(count.incrementAndGet());

        // Create the CareerGoals
        CareerGoalsDTO careerGoalsDTO = careerGoalsMapper.toDto(careerGoals);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCareerGoalsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(careerGoalsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the CareerGoals in the database
        List<CareerGoals> careerGoalsList = careerGoalsRepository.findAll();
        assertThat(careerGoalsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCareerGoals() throws Exception {
        // Initialize the database
        careerGoalsRepository.saveAndFlush(careerGoals);

        int databaseSizeBeforeDelete = careerGoalsRepository.findAll().size();

        // Delete the careerGoals
        restCareerGoalsMockMvc
            .perform(delete(ENTITY_API_URL_ID, careerGoals.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CareerGoals> careerGoalsList = careerGoalsRepository.findAll();
        assertThat(careerGoalsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
