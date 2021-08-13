package com.org.skillzag.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.org.skillzag.IntegrationTest;
import com.org.skillzag.domain.Announcements;
import com.org.skillzag.repository.AnnouncementsRepository;
import com.org.skillzag.service.dto.AnnouncementsDTO;
import com.org.skillzag.service.mapper.AnnouncementsMapper;
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
 * Integration tests for the {@link AnnouncementsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AnnouncementsResourceIT {

    private static final String DEFAULT_ANNOUNCEMENT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_ANNOUNCEMENT_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_ANNOUNCEMENT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_ANNOUNCEMENT_DESCRIPTION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CALENDAR = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CALENDAR = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_EMAIL_ID = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL_ID = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/announcements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AnnouncementsRepository announcementsRepository;

    @Autowired
    private AnnouncementsMapper announcementsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAnnouncementsMockMvc;

    private Announcements announcements;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Announcements createEntity(EntityManager em) {
        Announcements announcements = new Announcements()
            .announcementTitle(DEFAULT_ANNOUNCEMENT_TITLE)
            .announcementDescription(DEFAULT_ANNOUNCEMENT_DESCRIPTION)
            .calendar(DEFAULT_CALENDAR)
            .emailID(DEFAULT_EMAIL_ID);
        return announcements;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Announcements createUpdatedEntity(EntityManager em) {
        Announcements announcements = new Announcements()
            .announcementTitle(UPDATED_ANNOUNCEMENT_TITLE)
            .announcementDescription(UPDATED_ANNOUNCEMENT_DESCRIPTION)
            .calendar(UPDATED_CALENDAR)
            .emailID(UPDATED_EMAIL_ID);
        return announcements;
    }

    @BeforeEach
    public void initTest() {
        announcements = createEntity(em);
    }

    @Test
    @Transactional
    void createAnnouncements() throws Exception {
        int databaseSizeBeforeCreate = announcementsRepository.findAll().size();
        // Create the Announcements
        AnnouncementsDTO announcementsDTO = announcementsMapper.toDto(announcements);
        restAnnouncementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(announcementsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Announcements in the database
        List<Announcements> announcementsList = announcementsRepository.findAll();
        assertThat(announcementsList).hasSize(databaseSizeBeforeCreate + 1);
        Announcements testAnnouncements = announcementsList.get(announcementsList.size() - 1);
        assertThat(testAnnouncements.getAnnouncementTitle()).isEqualTo(DEFAULT_ANNOUNCEMENT_TITLE);
        assertThat(testAnnouncements.getAnnouncementDescription()).isEqualTo(DEFAULT_ANNOUNCEMENT_DESCRIPTION);
        assertThat(testAnnouncements.getCalendar()).isEqualTo(DEFAULT_CALENDAR);
        assertThat(testAnnouncements.getEmailID()).isEqualTo(DEFAULT_EMAIL_ID);
    }

    @Test
    @Transactional
    void createAnnouncementsWithExistingId() throws Exception {
        // Create the Announcements with an existing ID
        announcements.setId(1L);
        AnnouncementsDTO announcementsDTO = announcementsMapper.toDto(announcements);

        int databaseSizeBeforeCreate = announcementsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnouncementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(announcementsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Announcements in the database
        List<Announcements> announcementsList = announcementsRepository.findAll();
        assertThat(announcementsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAnnouncements() throws Exception {
        // Initialize the database
        announcementsRepository.saveAndFlush(announcements);

        // Get all the announcementsList
        restAnnouncementsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(announcements.getId().intValue())))
            .andExpect(jsonPath("$.[*].announcementTitle").value(hasItem(DEFAULT_ANNOUNCEMENT_TITLE)))
            .andExpect(jsonPath("$.[*].announcementDescription").value(hasItem(DEFAULT_ANNOUNCEMENT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].calendar").value(hasItem(DEFAULT_CALENDAR.toString())))
            .andExpect(jsonPath("$.[*].emailID").value(hasItem(DEFAULT_EMAIL_ID)));
    }

    @Test
    @Transactional
    void getAnnouncements() throws Exception {
        // Initialize the database
        announcementsRepository.saveAndFlush(announcements);

        // Get the announcements
        restAnnouncementsMockMvc
            .perform(get(ENTITY_API_URL_ID, announcements.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(announcements.getId().intValue()))
            .andExpect(jsonPath("$.announcementTitle").value(DEFAULT_ANNOUNCEMENT_TITLE))
            .andExpect(jsonPath("$.announcementDescription").value(DEFAULT_ANNOUNCEMENT_DESCRIPTION))
            .andExpect(jsonPath("$.calendar").value(DEFAULT_CALENDAR.toString()))
            .andExpect(jsonPath("$.emailID").value(DEFAULT_EMAIL_ID));
    }

    @Test
    @Transactional
    void getNonExistingAnnouncements() throws Exception {
        // Get the announcements
        restAnnouncementsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAnnouncements() throws Exception {
        // Initialize the database
        announcementsRepository.saveAndFlush(announcements);

        int databaseSizeBeforeUpdate = announcementsRepository.findAll().size();

        // Update the announcements
        Announcements updatedAnnouncements = announcementsRepository.findById(announcements.getId()).get();
        // Disconnect from session so that the updates on updatedAnnouncements are not directly saved in db
        em.detach(updatedAnnouncements);
        updatedAnnouncements
            .announcementTitle(UPDATED_ANNOUNCEMENT_TITLE)
            .announcementDescription(UPDATED_ANNOUNCEMENT_DESCRIPTION)
            .calendar(UPDATED_CALENDAR)
            .emailID(UPDATED_EMAIL_ID);
        AnnouncementsDTO announcementsDTO = announcementsMapper.toDto(updatedAnnouncements);

        restAnnouncementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, announcementsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(announcementsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Announcements in the database
        List<Announcements> announcementsList = announcementsRepository.findAll();
        assertThat(announcementsList).hasSize(databaseSizeBeforeUpdate);
        Announcements testAnnouncements = announcementsList.get(announcementsList.size() - 1);
        assertThat(testAnnouncements.getAnnouncementTitle()).isEqualTo(UPDATED_ANNOUNCEMENT_TITLE);
        assertThat(testAnnouncements.getAnnouncementDescription()).isEqualTo(UPDATED_ANNOUNCEMENT_DESCRIPTION);
        assertThat(testAnnouncements.getCalendar()).isEqualTo(UPDATED_CALENDAR);
        assertThat(testAnnouncements.getEmailID()).isEqualTo(UPDATED_EMAIL_ID);
    }

    @Test
    @Transactional
    void putNonExistingAnnouncements() throws Exception {
        int databaseSizeBeforeUpdate = announcementsRepository.findAll().size();
        announcements.setId(count.incrementAndGet());

        // Create the Announcements
        AnnouncementsDTO announcementsDTO = announcementsMapper.toDto(announcements);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnouncementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, announcementsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(announcementsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Announcements in the database
        List<Announcements> announcementsList = announcementsRepository.findAll();
        assertThat(announcementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAnnouncements() throws Exception {
        int databaseSizeBeforeUpdate = announcementsRepository.findAll().size();
        announcements.setId(count.incrementAndGet());

        // Create the Announcements
        AnnouncementsDTO announcementsDTO = announcementsMapper.toDto(announcements);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnouncementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(announcementsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Announcements in the database
        List<Announcements> announcementsList = announcementsRepository.findAll();
        assertThat(announcementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAnnouncements() throws Exception {
        int databaseSizeBeforeUpdate = announcementsRepository.findAll().size();
        announcements.setId(count.incrementAndGet());

        // Create the Announcements
        AnnouncementsDTO announcementsDTO = announcementsMapper.toDto(announcements);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnouncementsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(announcementsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Announcements in the database
        List<Announcements> announcementsList = announcementsRepository.findAll();
        assertThat(announcementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAnnouncementsWithPatch() throws Exception {
        // Initialize the database
        announcementsRepository.saveAndFlush(announcements);

        int databaseSizeBeforeUpdate = announcementsRepository.findAll().size();

        // Update the announcements using partial update
        Announcements partialUpdatedAnnouncements = new Announcements();
        partialUpdatedAnnouncements.setId(announcements.getId());

        partialUpdatedAnnouncements.announcementDescription(UPDATED_ANNOUNCEMENT_DESCRIPTION).calendar(UPDATED_CALENDAR);

        restAnnouncementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnnouncements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnnouncements))
            )
            .andExpect(status().isOk());

        // Validate the Announcements in the database
        List<Announcements> announcementsList = announcementsRepository.findAll();
        assertThat(announcementsList).hasSize(databaseSizeBeforeUpdate);
        Announcements testAnnouncements = announcementsList.get(announcementsList.size() - 1);
        assertThat(testAnnouncements.getAnnouncementTitle()).isEqualTo(DEFAULT_ANNOUNCEMENT_TITLE);
        assertThat(testAnnouncements.getAnnouncementDescription()).isEqualTo(UPDATED_ANNOUNCEMENT_DESCRIPTION);
        assertThat(testAnnouncements.getCalendar()).isEqualTo(UPDATED_CALENDAR);
        assertThat(testAnnouncements.getEmailID()).isEqualTo(DEFAULT_EMAIL_ID);
    }

    @Test
    @Transactional
    void fullUpdateAnnouncementsWithPatch() throws Exception {
        // Initialize the database
        announcementsRepository.saveAndFlush(announcements);

        int databaseSizeBeforeUpdate = announcementsRepository.findAll().size();

        // Update the announcements using partial update
        Announcements partialUpdatedAnnouncements = new Announcements();
        partialUpdatedAnnouncements.setId(announcements.getId());

        partialUpdatedAnnouncements
            .announcementTitle(UPDATED_ANNOUNCEMENT_TITLE)
            .announcementDescription(UPDATED_ANNOUNCEMENT_DESCRIPTION)
            .calendar(UPDATED_CALENDAR)
            .emailID(UPDATED_EMAIL_ID);

        restAnnouncementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAnnouncements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAnnouncements))
            )
            .andExpect(status().isOk());

        // Validate the Announcements in the database
        List<Announcements> announcementsList = announcementsRepository.findAll();
        assertThat(announcementsList).hasSize(databaseSizeBeforeUpdate);
        Announcements testAnnouncements = announcementsList.get(announcementsList.size() - 1);
        assertThat(testAnnouncements.getAnnouncementTitle()).isEqualTo(UPDATED_ANNOUNCEMENT_TITLE);
        assertThat(testAnnouncements.getAnnouncementDescription()).isEqualTo(UPDATED_ANNOUNCEMENT_DESCRIPTION);
        assertThat(testAnnouncements.getCalendar()).isEqualTo(UPDATED_CALENDAR);
        assertThat(testAnnouncements.getEmailID()).isEqualTo(UPDATED_EMAIL_ID);
    }

    @Test
    @Transactional
    void patchNonExistingAnnouncements() throws Exception {
        int databaseSizeBeforeUpdate = announcementsRepository.findAll().size();
        announcements.setId(count.incrementAndGet());

        // Create the Announcements
        AnnouncementsDTO announcementsDTO = announcementsMapper.toDto(announcements);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnouncementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, announcementsDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(announcementsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Announcements in the database
        List<Announcements> announcementsList = announcementsRepository.findAll();
        assertThat(announcementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAnnouncements() throws Exception {
        int databaseSizeBeforeUpdate = announcementsRepository.findAll().size();
        announcements.setId(count.incrementAndGet());

        // Create the Announcements
        AnnouncementsDTO announcementsDTO = announcementsMapper.toDto(announcements);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnouncementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(announcementsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Announcements in the database
        List<Announcements> announcementsList = announcementsRepository.findAll();
        assertThat(announcementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAnnouncements() throws Exception {
        int databaseSizeBeforeUpdate = announcementsRepository.findAll().size();
        announcements.setId(count.incrementAndGet());

        // Create the Announcements
        AnnouncementsDTO announcementsDTO = announcementsMapper.toDto(announcements);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAnnouncementsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(announcementsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Announcements in the database
        List<Announcements> announcementsList = announcementsRepository.findAll();
        assertThat(announcementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAnnouncements() throws Exception {
        // Initialize the database
        announcementsRepository.saveAndFlush(announcements);

        int databaseSizeBeforeDelete = announcementsRepository.findAll().size();

        // Delete the announcements
        restAnnouncementsMockMvc
            .perform(delete(ENTITY_API_URL_ID, announcements.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Announcements> announcementsList = announcementsRepository.findAll();
        assertThat(announcementsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
