package com.org.skillzag.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.org.skillzag.IntegrationTest;
import com.org.skillzag.domain.ProjectManagement;
import com.org.skillzag.domain.enumeration.ProjectStatus;
import com.org.skillzag.repository.ProjectManagementRepository;
import com.org.skillzag.service.dto.ProjectManagementDTO;
import com.org.skillzag.service.mapper.ProjectManagementMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link ProjectManagementResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProjectManagementResourceIT {

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_PROFILE = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_PROFILE = "BBBBBBBBBB";

    private static final String DEFAULT_COMPANY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_COMPANY_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_PROJECT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PROJECT_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECTIVE = "AAAAAAAAAA";
    private static final String UPDATED_OBJECTIVE = "BBBBBBBBBB";

    private static final Instant DEFAULT_DATES = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATES = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_VALID_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VALID_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LOGO = "AAAAAAAAAA";
    private static final String UPDATED_LOGO = "BBBBBBBBBB";

    private static final String DEFAULT_VIDEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_TIME = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_TIME = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final ProjectStatus DEFAULT_PROJECT_STATUS = ProjectStatus.INPROGRESS;
    private static final ProjectStatus UPDATED_PROJECT_STATUS = ProjectStatus.STARTED;

    private static final String DEFAULT_URL_1 = "AAAAAAAAAA";
    private static final String UPDATED_URL_1 = "BBBBBBBBBB";

    private static final String DEFAULT_URL_2 = "AAAAAAAAAA";
    private static final String UPDATED_URL_2 = "BBBBBBBBBB";

    private static final String DEFAULT_URL_3 = "AAAAAAAAAA";
    private static final String UPDATED_URL_3 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/project-managements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProjectManagementRepository projectManagementRepository;

    @Autowired
    private ProjectManagementMapper projectManagementMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProjectManagementMockMvc;

    private ProjectManagement projectManagement;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectManagement createEntity(EntityManager em) {
        ProjectManagement projectManagement = new ProjectManagement()
            .emailID(DEFAULT_EMAIL_ID)
            .projectName(DEFAULT_PROJECT_NAME)
            .companyName(DEFAULT_COMPANY_NAME)
            .companyProfile(DEFAULT_COMPANY_PROFILE)
            .companyDescription(DEFAULT_COMPANY_DESCRIPTION)
            .projectDescription(DEFAULT_PROJECT_DESCRIPTION)
            .objective(DEFAULT_OBJECTIVE)
            .dates(DEFAULT_DATES)
            .validFrom(DEFAULT_VALID_FROM)
            .validTo(DEFAULT_VALID_TO)
            .logo(DEFAULT_LOGO)
            .videoUrl(DEFAULT_VIDEO_URL)
            .description(DEFAULT_DESCRIPTION)
            .createdBy(DEFAULT_CREATED_BY)
            .createdTime(DEFAULT_CREATED_TIME)
            .projectStatus(DEFAULT_PROJECT_STATUS)
            .url1(DEFAULT_URL_1)
            .url2(DEFAULT_URL_2)
            .url3(DEFAULT_URL_3);
        return projectManagement;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProjectManagement createUpdatedEntity(EntityManager em) {
        ProjectManagement projectManagement = new ProjectManagement()
            .emailID(UPDATED_EMAIL_ID)
            .projectName(UPDATED_PROJECT_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .companyProfile(UPDATED_COMPANY_PROFILE)
            .companyDescription(UPDATED_COMPANY_DESCRIPTION)
            .projectDescription(UPDATED_PROJECT_DESCRIPTION)
            .objective(UPDATED_OBJECTIVE)
            .dates(UPDATED_DATES)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .logo(UPDATED_LOGO)
            .videoUrl(UPDATED_VIDEO_URL)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .projectStatus(UPDATED_PROJECT_STATUS)
            .url1(UPDATED_URL_1)
            .url2(UPDATED_URL_2)
            .url3(UPDATED_URL_3);
        return projectManagement;
    }

    @BeforeEach
    public void initTest() {
        projectManagement = createEntity(em);
    }

    @Test
    @Transactional
    void createProjectManagement() throws Exception {
        int databaseSizeBeforeCreate = projectManagementRepository.findAll().size();
        // Create the ProjectManagement
        ProjectManagementDTO projectManagementDTO = projectManagementMapper.toDto(projectManagement);
        restProjectManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectManagementDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProjectManagement in the database
        List<ProjectManagement> projectManagementList = projectManagementRepository.findAll();
        assertThat(projectManagementList).hasSize(databaseSizeBeforeCreate + 1);
        ProjectManagement testProjectManagement = projectManagementList.get(projectManagementList.size() - 1);
        assertThat(testProjectManagement.getEmailID()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testProjectManagement.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testProjectManagement.getCompanyName()).isEqualTo(DEFAULT_COMPANY_NAME);
        assertThat(testProjectManagement.getCompanyProfile()).isEqualTo(DEFAULT_COMPANY_PROFILE);
        assertThat(testProjectManagement.getCompanyDescription()).isEqualTo(DEFAULT_COMPANY_DESCRIPTION);
        assertThat(testProjectManagement.getProjectDescription()).isEqualTo(DEFAULT_PROJECT_DESCRIPTION);
        assertThat(testProjectManagement.getObjective()).isEqualTo(DEFAULT_OBJECTIVE);
        assertThat(testProjectManagement.getDates()).isEqualTo(DEFAULT_DATES);
        assertThat(testProjectManagement.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testProjectManagement.getValidTo()).isEqualTo(DEFAULT_VALID_TO);
        assertThat(testProjectManagement.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testProjectManagement.getVideoUrl()).isEqualTo(DEFAULT_VIDEO_URL);
        assertThat(testProjectManagement.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProjectManagement.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProjectManagement.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testProjectManagement.getProjectStatus()).isEqualTo(DEFAULT_PROJECT_STATUS);
        assertThat(testProjectManagement.getUrl1()).isEqualTo(DEFAULT_URL_1);
        assertThat(testProjectManagement.getUrl2()).isEqualTo(DEFAULT_URL_2);
        assertThat(testProjectManagement.getUrl3()).isEqualTo(DEFAULT_URL_3);
    }

    @Test
    @Transactional
    void createProjectManagementWithExistingId() throws Exception {
        // Create the ProjectManagement with an existing ID
        projectManagement.setId(1L);
        ProjectManagementDTO projectManagementDTO = projectManagementMapper.toDto(projectManagement);

        int databaseSizeBeforeCreate = projectManagementRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProjectManagementMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectManagementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectManagement in the database
        List<ProjectManagement> projectManagementList = projectManagementRepository.findAll();
        assertThat(projectManagementList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProjectManagements() throws Exception {
        // Initialize the database
        projectManagementRepository.saveAndFlush(projectManagement);

        // Get all the projectManagementList
        restProjectManagementMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(projectManagement.getId().intValue())))
            .andExpect(jsonPath("$.[*].emailID").value(hasItem(DEFAULT_EMAIL_ID)))
            .andExpect(jsonPath("$.[*].projectName").value(hasItem(DEFAULT_PROJECT_NAME)))
            .andExpect(jsonPath("$.[*].companyName").value(hasItem(DEFAULT_COMPANY_NAME)))
            .andExpect(jsonPath("$.[*].companyProfile").value(hasItem(DEFAULT_COMPANY_PROFILE)))
            .andExpect(jsonPath("$.[*].companyDescription").value(hasItem(DEFAULT_COMPANY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].projectDescription").value(hasItem(DEFAULT_PROJECT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].objective").value(hasItem(DEFAULT_OBJECTIVE)))
            .andExpect(jsonPath("$.[*].dates").value(hasItem(DEFAULT_DATES.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.toString())))
            .andExpect(jsonPath("$.[*].validTo").value(hasItem(DEFAULT_VALID_TO.toString())))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.[*].videoUrl").value(hasItem(DEFAULT_VIDEO_URL)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdTime").value(hasItem(DEFAULT_CREATED_TIME.toString())))
            .andExpect(jsonPath("$.[*].projectStatus").value(hasItem(DEFAULT_PROJECT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].url1").value(hasItem(DEFAULT_URL_1)))
            .andExpect(jsonPath("$.[*].url2").value(hasItem(DEFAULT_URL_2)))
            .andExpect(jsonPath("$.[*].url3").value(hasItem(DEFAULT_URL_3)));
    }

    @Test
    @Transactional
    void getProjectManagement() throws Exception {
        // Initialize the database
        projectManagementRepository.saveAndFlush(projectManagement);

        // Get the projectManagement
        restProjectManagementMockMvc
            .perform(get(ENTITY_API_URL_ID, projectManagement.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(projectManagement.getId().intValue()))
            .andExpect(jsonPath("$.emailID").value(DEFAULT_EMAIL_ID))
            .andExpect(jsonPath("$.projectName").value(DEFAULT_PROJECT_NAME))
            .andExpect(jsonPath("$.companyName").value(DEFAULT_COMPANY_NAME))
            .andExpect(jsonPath("$.companyProfile").value(DEFAULT_COMPANY_PROFILE))
            .andExpect(jsonPath("$.companyDescription").value(DEFAULT_COMPANY_DESCRIPTION))
            .andExpect(jsonPath("$.projectDescription").value(DEFAULT_PROJECT_DESCRIPTION))
            .andExpect(jsonPath("$.objective").value(DEFAULT_OBJECTIVE))
            .andExpect(jsonPath("$.dates").value(DEFAULT_DATES.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.toString()))
            .andExpect(jsonPath("$.validTo").value(DEFAULT_VALID_TO.toString()))
            .andExpect(jsonPath("$.logo").value(DEFAULT_LOGO))
            .andExpect(jsonPath("$.videoUrl").value(DEFAULT_VIDEO_URL))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdTime").value(DEFAULT_CREATED_TIME.toString()))
            .andExpect(jsonPath("$.projectStatus").value(DEFAULT_PROJECT_STATUS.toString()))
            .andExpect(jsonPath("$.url1").value(DEFAULT_URL_1))
            .andExpect(jsonPath("$.url2").value(DEFAULT_URL_2))
            .andExpect(jsonPath("$.url3").value(DEFAULT_URL_3));
    }

    @Test
    @Transactional
    void getNonExistingProjectManagement() throws Exception {
        // Get the projectManagement
        restProjectManagementMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProjectManagement() throws Exception {
        // Initialize the database
        projectManagementRepository.saveAndFlush(projectManagement);

        int databaseSizeBeforeUpdate = projectManagementRepository.findAll().size();

        // Update the projectManagement
        ProjectManagement updatedProjectManagement = projectManagementRepository.findById(projectManagement.getId()).get();
        // Disconnect from session so that the updates on updatedProjectManagement are not directly saved in db
        em.detach(updatedProjectManagement);
        updatedProjectManagement
            .emailID(UPDATED_EMAIL_ID)
            .projectName(UPDATED_PROJECT_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .companyProfile(UPDATED_COMPANY_PROFILE)
            .companyDescription(UPDATED_COMPANY_DESCRIPTION)
            .projectDescription(UPDATED_PROJECT_DESCRIPTION)
            .objective(UPDATED_OBJECTIVE)
            .dates(UPDATED_DATES)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .logo(UPDATED_LOGO)
            .videoUrl(UPDATED_VIDEO_URL)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .projectStatus(UPDATED_PROJECT_STATUS)
            .url1(UPDATED_URL_1)
            .url2(UPDATED_URL_2)
            .url3(UPDATED_URL_3);
        ProjectManagementDTO projectManagementDTO = projectManagementMapper.toDto(updatedProjectManagement);

        restProjectManagementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectManagementDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectManagementDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProjectManagement in the database
        List<ProjectManagement> projectManagementList = projectManagementRepository.findAll();
        assertThat(projectManagementList).hasSize(databaseSizeBeforeUpdate);
        ProjectManagement testProjectManagement = projectManagementList.get(projectManagementList.size() - 1);
        assertThat(testProjectManagement.getEmailID()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testProjectManagement.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testProjectManagement.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testProjectManagement.getCompanyProfile()).isEqualTo(UPDATED_COMPANY_PROFILE);
        assertThat(testProjectManagement.getCompanyDescription()).isEqualTo(UPDATED_COMPANY_DESCRIPTION);
        assertThat(testProjectManagement.getProjectDescription()).isEqualTo(UPDATED_PROJECT_DESCRIPTION);
        assertThat(testProjectManagement.getObjective()).isEqualTo(UPDATED_OBJECTIVE);
        assertThat(testProjectManagement.getDates()).isEqualTo(UPDATED_DATES);
        assertThat(testProjectManagement.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testProjectManagement.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testProjectManagement.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testProjectManagement.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testProjectManagement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectManagement.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjectManagement.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testProjectManagement.getProjectStatus()).isEqualTo(UPDATED_PROJECT_STATUS);
        assertThat(testProjectManagement.getUrl1()).isEqualTo(UPDATED_URL_1);
        assertThat(testProjectManagement.getUrl2()).isEqualTo(UPDATED_URL_2);
        assertThat(testProjectManagement.getUrl3()).isEqualTo(UPDATED_URL_3);
    }

    @Test
    @Transactional
    void putNonExistingProjectManagement() throws Exception {
        int databaseSizeBeforeUpdate = projectManagementRepository.findAll().size();
        projectManagement.setId(count.incrementAndGet());

        // Create the ProjectManagement
        ProjectManagementDTO projectManagementDTO = projectManagementMapper.toDto(projectManagement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectManagementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, projectManagementDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectManagementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectManagement in the database
        List<ProjectManagement> projectManagementList = projectManagementRepository.findAll();
        assertThat(projectManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProjectManagement() throws Exception {
        int databaseSizeBeforeUpdate = projectManagementRepository.findAll().size();
        projectManagement.setId(count.incrementAndGet());

        // Create the ProjectManagement
        ProjectManagementDTO projectManagementDTO = projectManagementMapper.toDto(projectManagement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectManagementMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectManagementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectManagement in the database
        List<ProjectManagement> projectManagementList = projectManagementRepository.findAll();
        assertThat(projectManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProjectManagement() throws Exception {
        int databaseSizeBeforeUpdate = projectManagementRepository.findAll().size();
        projectManagement.setId(count.incrementAndGet());

        // Create the ProjectManagement
        ProjectManagementDTO projectManagementDTO = projectManagementMapper.toDto(projectManagement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectManagementMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(projectManagementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectManagement in the database
        List<ProjectManagement> projectManagementList = projectManagementRepository.findAll();
        assertThat(projectManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProjectManagementWithPatch() throws Exception {
        // Initialize the database
        projectManagementRepository.saveAndFlush(projectManagement);

        int databaseSizeBeforeUpdate = projectManagementRepository.findAll().size();

        // Update the projectManagement using partial update
        ProjectManagement partialUpdatedProjectManagement = new ProjectManagement();
        partialUpdatedProjectManagement.setId(projectManagement.getId());

        partialUpdatedProjectManagement
            .companyName(UPDATED_COMPANY_NAME)
            .projectDescription(UPDATED_PROJECT_DESCRIPTION)
            .dates(UPDATED_DATES)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .videoUrl(UPDATED_VIDEO_URL)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .url1(UPDATED_URL_1)
            .url2(UPDATED_URL_2)
            .url3(UPDATED_URL_3);

        restProjectManagementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectManagement.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectManagement))
            )
            .andExpect(status().isOk());

        // Validate the ProjectManagement in the database
        List<ProjectManagement> projectManagementList = projectManagementRepository.findAll();
        assertThat(projectManagementList).hasSize(databaseSizeBeforeUpdate);
        ProjectManagement testProjectManagement = projectManagementList.get(projectManagementList.size() - 1);
        assertThat(testProjectManagement.getEmailID()).isEqualTo(DEFAULT_EMAIL_ID);
        assertThat(testProjectManagement.getProjectName()).isEqualTo(DEFAULT_PROJECT_NAME);
        assertThat(testProjectManagement.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testProjectManagement.getCompanyProfile()).isEqualTo(DEFAULT_COMPANY_PROFILE);
        assertThat(testProjectManagement.getCompanyDescription()).isEqualTo(DEFAULT_COMPANY_DESCRIPTION);
        assertThat(testProjectManagement.getProjectDescription()).isEqualTo(UPDATED_PROJECT_DESCRIPTION);
        assertThat(testProjectManagement.getObjective()).isEqualTo(DEFAULT_OBJECTIVE);
        assertThat(testProjectManagement.getDates()).isEqualTo(UPDATED_DATES);
        assertThat(testProjectManagement.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testProjectManagement.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testProjectManagement.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testProjectManagement.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testProjectManagement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectManagement.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjectManagement.getCreatedTime()).isEqualTo(DEFAULT_CREATED_TIME);
        assertThat(testProjectManagement.getProjectStatus()).isEqualTo(DEFAULT_PROJECT_STATUS);
        assertThat(testProjectManagement.getUrl1()).isEqualTo(UPDATED_URL_1);
        assertThat(testProjectManagement.getUrl2()).isEqualTo(UPDATED_URL_2);
        assertThat(testProjectManagement.getUrl3()).isEqualTo(UPDATED_URL_3);
    }

    @Test
    @Transactional
    void fullUpdateProjectManagementWithPatch() throws Exception {
        // Initialize the database
        projectManagementRepository.saveAndFlush(projectManagement);

        int databaseSizeBeforeUpdate = projectManagementRepository.findAll().size();

        // Update the projectManagement using partial update
        ProjectManagement partialUpdatedProjectManagement = new ProjectManagement();
        partialUpdatedProjectManagement.setId(projectManagement.getId());

        partialUpdatedProjectManagement
            .emailID(UPDATED_EMAIL_ID)
            .projectName(UPDATED_PROJECT_NAME)
            .companyName(UPDATED_COMPANY_NAME)
            .companyProfile(UPDATED_COMPANY_PROFILE)
            .companyDescription(UPDATED_COMPANY_DESCRIPTION)
            .projectDescription(UPDATED_PROJECT_DESCRIPTION)
            .objective(UPDATED_OBJECTIVE)
            .dates(UPDATED_DATES)
            .validFrom(UPDATED_VALID_FROM)
            .validTo(UPDATED_VALID_TO)
            .logo(UPDATED_LOGO)
            .videoUrl(UPDATED_VIDEO_URL)
            .description(UPDATED_DESCRIPTION)
            .createdBy(UPDATED_CREATED_BY)
            .createdTime(UPDATED_CREATED_TIME)
            .projectStatus(UPDATED_PROJECT_STATUS)
            .url1(UPDATED_URL_1)
            .url2(UPDATED_URL_2)
            .url3(UPDATED_URL_3);

        restProjectManagementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProjectManagement.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProjectManagement))
            )
            .andExpect(status().isOk());

        // Validate the ProjectManagement in the database
        List<ProjectManagement> projectManagementList = projectManagementRepository.findAll();
        assertThat(projectManagementList).hasSize(databaseSizeBeforeUpdate);
        ProjectManagement testProjectManagement = projectManagementList.get(projectManagementList.size() - 1);
        assertThat(testProjectManagement.getEmailID()).isEqualTo(UPDATED_EMAIL_ID);
        assertThat(testProjectManagement.getProjectName()).isEqualTo(UPDATED_PROJECT_NAME);
        assertThat(testProjectManagement.getCompanyName()).isEqualTo(UPDATED_COMPANY_NAME);
        assertThat(testProjectManagement.getCompanyProfile()).isEqualTo(UPDATED_COMPANY_PROFILE);
        assertThat(testProjectManagement.getCompanyDescription()).isEqualTo(UPDATED_COMPANY_DESCRIPTION);
        assertThat(testProjectManagement.getProjectDescription()).isEqualTo(UPDATED_PROJECT_DESCRIPTION);
        assertThat(testProjectManagement.getObjective()).isEqualTo(UPDATED_OBJECTIVE);
        assertThat(testProjectManagement.getDates()).isEqualTo(UPDATED_DATES);
        assertThat(testProjectManagement.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testProjectManagement.getValidTo()).isEqualTo(UPDATED_VALID_TO);
        assertThat(testProjectManagement.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testProjectManagement.getVideoUrl()).isEqualTo(UPDATED_VIDEO_URL);
        assertThat(testProjectManagement.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProjectManagement.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProjectManagement.getCreatedTime()).isEqualTo(UPDATED_CREATED_TIME);
        assertThat(testProjectManagement.getProjectStatus()).isEqualTo(UPDATED_PROJECT_STATUS);
        assertThat(testProjectManagement.getUrl1()).isEqualTo(UPDATED_URL_1);
        assertThat(testProjectManagement.getUrl2()).isEqualTo(UPDATED_URL_2);
        assertThat(testProjectManagement.getUrl3()).isEqualTo(UPDATED_URL_3);
    }

    @Test
    @Transactional
    void patchNonExistingProjectManagement() throws Exception {
        int databaseSizeBeforeUpdate = projectManagementRepository.findAll().size();
        projectManagement.setId(count.incrementAndGet());

        // Create the ProjectManagement
        ProjectManagementDTO projectManagementDTO = projectManagementMapper.toDto(projectManagement);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProjectManagementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, projectManagementDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectManagementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectManagement in the database
        List<ProjectManagement> projectManagementList = projectManagementRepository.findAll();
        assertThat(projectManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProjectManagement() throws Exception {
        int databaseSizeBeforeUpdate = projectManagementRepository.findAll().size();
        projectManagement.setId(count.incrementAndGet());

        // Create the ProjectManagement
        ProjectManagementDTO projectManagementDTO = projectManagementMapper.toDto(projectManagement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectManagementMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectManagementDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProjectManagement in the database
        List<ProjectManagement> projectManagementList = projectManagementRepository.findAll();
        assertThat(projectManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProjectManagement() throws Exception {
        int databaseSizeBeforeUpdate = projectManagementRepository.findAll().size();
        projectManagement.setId(count.incrementAndGet());

        // Create the ProjectManagement
        ProjectManagementDTO projectManagementDTO = projectManagementMapper.toDto(projectManagement);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProjectManagementMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(projectManagementDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProjectManagement in the database
        List<ProjectManagement> projectManagementList = projectManagementRepository.findAll();
        assertThat(projectManagementList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProjectManagement() throws Exception {
        // Initialize the database
        projectManagementRepository.saveAndFlush(projectManagement);

        int databaseSizeBeforeDelete = projectManagementRepository.findAll().size();

        // Delete the projectManagement
        restProjectManagementMockMvc
            .perform(delete(ENTITY_API_URL_ID, projectManagement.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProjectManagement> projectManagementList = projectManagementRepository.findAll();
        assertThat(projectManagementList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
