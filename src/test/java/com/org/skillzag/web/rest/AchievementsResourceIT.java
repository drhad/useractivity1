package com.org.skillzag.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.org.skillzag.IntegrationTest;
import com.org.skillzag.domain.Achievements;
import com.org.skillzag.domain.enumeration.CertificationType;
import com.org.skillzag.repository.AchievementsRepository;
import com.org.skillzag.service.dto.AchievementsDTO;
import com.org.skillzag.service.mapper.AchievementsMapper;
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
 * Integration tests for the {@link AchievementsResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AchievementsResourceIT {

    private static final String DEFAULT_CERTIFICATION_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATION_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_CERTIFICATE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CERTIFICATE_DESCRIPTION = "BBBBBBBBBB";

    private static final CertificationType DEFAULT_CERTIFICATION_TYPE = CertificationType.INTERNAL;
    private static final CertificationType UPDATED_CERTIFICATION_TYPE = CertificationType.EXTERNAL;

    private static final Instant DEFAULT_DATE_OF_COMPLETION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DATE_OF_COMPLETION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_CERTIFICATION_SCORE = 1L;
    private static final Long UPDATED_CERTIFICATION_SCORE = 2L;

    private static final String DEFAULT_UPLOAD_CERTIFICATE = "AAAAAAAAAA";
    private static final String UPDATED_UPLOAD_CERTIFICATE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/achievements";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AchievementsRepository achievementsRepository;

    @Autowired
    private AchievementsMapper achievementsMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAchievementsMockMvc;

    private Achievements achievements;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Achievements createEntity(EntityManager em) {
        Achievements achievements = new Achievements()
            .certificationTitle(DEFAULT_CERTIFICATION_TITLE)
            .certificateDescription(DEFAULT_CERTIFICATE_DESCRIPTION)
            .certificationType(DEFAULT_CERTIFICATION_TYPE)
            .dateOfCompletion(DEFAULT_DATE_OF_COMPLETION)
            .certificationScore(DEFAULT_CERTIFICATION_SCORE)
            .uploadCertificate(DEFAULT_UPLOAD_CERTIFICATE);
        return achievements;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Achievements createUpdatedEntity(EntityManager em) {
        Achievements achievements = new Achievements()
            .certificationTitle(UPDATED_CERTIFICATION_TITLE)
            .certificateDescription(UPDATED_CERTIFICATE_DESCRIPTION)
            .certificationType(UPDATED_CERTIFICATION_TYPE)
            .dateOfCompletion(UPDATED_DATE_OF_COMPLETION)
            .certificationScore(UPDATED_CERTIFICATION_SCORE)
            .uploadCertificate(UPDATED_UPLOAD_CERTIFICATE);
        return achievements;
    }

    @BeforeEach
    public void initTest() {
        achievements = createEntity(em);
    }

    @Test
    @Transactional
    void createAchievements() throws Exception {
        int databaseSizeBeforeCreate = achievementsRepository.findAll().size();
        // Create the Achievements
        AchievementsDTO achievementsDTO = achievementsMapper.toDto(achievements);
        restAchievementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(achievementsDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Achievements in the database
        List<Achievements> achievementsList = achievementsRepository.findAll();
        assertThat(achievementsList).hasSize(databaseSizeBeforeCreate + 1);
        Achievements testAchievements = achievementsList.get(achievementsList.size() - 1);
        assertThat(testAchievements.getCertificationTitle()).isEqualTo(DEFAULT_CERTIFICATION_TITLE);
        assertThat(testAchievements.getCertificateDescription()).isEqualTo(DEFAULT_CERTIFICATE_DESCRIPTION);
        assertThat(testAchievements.getCertificationType()).isEqualTo(DEFAULT_CERTIFICATION_TYPE);
        assertThat(testAchievements.getDateOfCompletion()).isEqualTo(DEFAULT_DATE_OF_COMPLETION);
        assertThat(testAchievements.getCertificationScore()).isEqualTo(DEFAULT_CERTIFICATION_SCORE);
        assertThat(testAchievements.getUploadCertificate()).isEqualTo(DEFAULT_UPLOAD_CERTIFICATE);
    }

    @Test
    @Transactional
    void createAchievementsWithExistingId() throws Exception {
        // Create the Achievements with an existing ID
        achievements.setId(1L);
        AchievementsDTO achievementsDTO = achievementsMapper.toDto(achievements);

        int databaseSizeBeforeCreate = achievementsRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAchievementsMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(achievementsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Achievements in the database
        List<Achievements> achievementsList = achievementsRepository.findAll();
        assertThat(achievementsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAchievements() throws Exception {
        // Initialize the database
        achievementsRepository.saveAndFlush(achievements);

        // Get all the achievementsList
        restAchievementsMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(achievements.getId().intValue())))
            .andExpect(jsonPath("$.[*].certificationTitle").value(hasItem(DEFAULT_CERTIFICATION_TITLE)))
            .andExpect(jsonPath("$.[*].certificateDescription").value(hasItem(DEFAULT_CERTIFICATE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].certificationType").value(hasItem(DEFAULT_CERTIFICATION_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dateOfCompletion").value(hasItem(DEFAULT_DATE_OF_COMPLETION.toString())))
            .andExpect(jsonPath("$.[*].certificationScore").value(hasItem(DEFAULT_CERTIFICATION_SCORE.intValue())))
            .andExpect(jsonPath("$.[*].uploadCertificate").value(hasItem(DEFAULT_UPLOAD_CERTIFICATE)));
    }

    @Test
    @Transactional
    void getAchievements() throws Exception {
        // Initialize the database
        achievementsRepository.saveAndFlush(achievements);

        // Get the achievements
        restAchievementsMockMvc
            .perform(get(ENTITY_API_URL_ID, achievements.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(achievements.getId().intValue()))
            .andExpect(jsonPath("$.certificationTitle").value(DEFAULT_CERTIFICATION_TITLE))
            .andExpect(jsonPath("$.certificateDescription").value(DEFAULT_CERTIFICATE_DESCRIPTION))
            .andExpect(jsonPath("$.certificationType").value(DEFAULT_CERTIFICATION_TYPE.toString()))
            .andExpect(jsonPath("$.dateOfCompletion").value(DEFAULT_DATE_OF_COMPLETION.toString()))
            .andExpect(jsonPath("$.certificationScore").value(DEFAULT_CERTIFICATION_SCORE.intValue()))
            .andExpect(jsonPath("$.uploadCertificate").value(DEFAULT_UPLOAD_CERTIFICATE));
    }

    @Test
    @Transactional
    void getNonExistingAchievements() throws Exception {
        // Get the achievements
        restAchievementsMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAchievements() throws Exception {
        // Initialize the database
        achievementsRepository.saveAndFlush(achievements);

        int databaseSizeBeforeUpdate = achievementsRepository.findAll().size();

        // Update the achievements
        Achievements updatedAchievements = achievementsRepository.findById(achievements.getId()).get();
        // Disconnect from session so that the updates on updatedAchievements are not directly saved in db
        em.detach(updatedAchievements);
        updatedAchievements
            .certificationTitle(UPDATED_CERTIFICATION_TITLE)
            .certificateDescription(UPDATED_CERTIFICATE_DESCRIPTION)
            .certificationType(UPDATED_CERTIFICATION_TYPE)
            .dateOfCompletion(UPDATED_DATE_OF_COMPLETION)
            .certificationScore(UPDATED_CERTIFICATION_SCORE)
            .uploadCertificate(UPDATED_UPLOAD_CERTIFICATE);
        AchievementsDTO achievementsDTO = achievementsMapper.toDto(updatedAchievements);

        restAchievementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, achievementsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(achievementsDTO))
            )
            .andExpect(status().isOk());

        // Validate the Achievements in the database
        List<Achievements> achievementsList = achievementsRepository.findAll();
        assertThat(achievementsList).hasSize(databaseSizeBeforeUpdate);
        Achievements testAchievements = achievementsList.get(achievementsList.size() - 1);
        assertThat(testAchievements.getCertificationTitle()).isEqualTo(UPDATED_CERTIFICATION_TITLE);
        assertThat(testAchievements.getCertificateDescription()).isEqualTo(UPDATED_CERTIFICATE_DESCRIPTION);
        assertThat(testAchievements.getCertificationType()).isEqualTo(UPDATED_CERTIFICATION_TYPE);
        assertThat(testAchievements.getDateOfCompletion()).isEqualTo(UPDATED_DATE_OF_COMPLETION);
        assertThat(testAchievements.getCertificationScore()).isEqualTo(UPDATED_CERTIFICATION_SCORE);
        assertThat(testAchievements.getUploadCertificate()).isEqualTo(UPDATED_UPLOAD_CERTIFICATE);
    }

    @Test
    @Transactional
    void putNonExistingAchievements() throws Exception {
        int databaseSizeBeforeUpdate = achievementsRepository.findAll().size();
        achievements.setId(count.incrementAndGet());

        // Create the Achievements
        AchievementsDTO achievementsDTO = achievementsMapper.toDto(achievements);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAchievementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, achievementsDTO.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(achievementsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Achievements in the database
        List<Achievements> achievementsList = achievementsRepository.findAll();
        assertThat(achievementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAchievements() throws Exception {
        int databaseSizeBeforeUpdate = achievementsRepository.findAll().size();
        achievements.setId(count.incrementAndGet());

        // Create the Achievements
        AchievementsDTO achievementsDTO = achievementsMapper.toDto(achievements);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAchievementsMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(achievementsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Achievements in the database
        List<Achievements> achievementsList = achievementsRepository.findAll();
        assertThat(achievementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAchievements() throws Exception {
        int databaseSizeBeforeUpdate = achievementsRepository.findAll().size();
        achievements.setId(count.incrementAndGet());

        // Create the Achievements
        AchievementsDTO achievementsDTO = achievementsMapper.toDto(achievements);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAchievementsMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(achievementsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Achievements in the database
        List<Achievements> achievementsList = achievementsRepository.findAll();
        assertThat(achievementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAchievementsWithPatch() throws Exception {
        // Initialize the database
        achievementsRepository.saveAndFlush(achievements);

        int databaseSizeBeforeUpdate = achievementsRepository.findAll().size();

        // Update the achievements using partial update
        Achievements partialUpdatedAchievements = new Achievements();
        partialUpdatedAchievements.setId(achievements.getId());

        partialUpdatedAchievements
            .certificationTitle(UPDATED_CERTIFICATION_TITLE)
            .certificationType(UPDATED_CERTIFICATION_TYPE)
            .dateOfCompletion(UPDATED_DATE_OF_COMPLETION)
            .certificationScore(UPDATED_CERTIFICATION_SCORE);

        restAchievementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAchievements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAchievements))
            )
            .andExpect(status().isOk());

        // Validate the Achievements in the database
        List<Achievements> achievementsList = achievementsRepository.findAll();
        assertThat(achievementsList).hasSize(databaseSizeBeforeUpdate);
        Achievements testAchievements = achievementsList.get(achievementsList.size() - 1);
        assertThat(testAchievements.getCertificationTitle()).isEqualTo(UPDATED_CERTIFICATION_TITLE);
        assertThat(testAchievements.getCertificateDescription()).isEqualTo(DEFAULT_CERTIFICATE_DESCRIPTION);
        assertThat(testAchievements.getCertificationType()).isEqualTo(UPDATED_CERTIFICATION_TYPE);
        assertThat(testAchievements.getDateOfCompletion()).isEqualTo(UPDATED_DATE_OF_COMPLETION);
        assertThat(testAchievements.getCertificationScore()).isEqualTo(UPDATED_CERTIFICATION_SCORE);
        assertThat(testAchievements.getUploadCertificate()).isEqualTo(DEFAULT_UPLOAD_CERTIFICATE);
    }

    @Test
    @Transactional
    void fullUpdateAchievementsWithPatch() throws Exception {
        // Initialize the database
        achievementsRepository.saveAndFlush(achievements);

        int databaseSizeBeforeUpdate = achievementsRepository.findAll().size();

        // Update the achievements using partial update
        Achievements partialUpdatedAchievements = new Achievements();
        partialUpdatedAchievements.setId(achievements.getId());

        partialUpdatedAchievements
            .certificationTitle(UPDATED_CERTIFICATION_TITLE)
            .certificateDescription(UPDATED_CERTIFICATE_DESCRIPTION)
            .certificationType(UPDATED_CERTIFICATION_TYPE)
            .dateOfCompletion(UPDATED_DATE_OF_COMPLETION)
            .certificationScore(UPDATED_CERTIFICATION_SCORE)
            .uploadCertificate(UPDATED_UPLOAD_CERTIFICATE);

        restAchievementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAchievements.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAchievements))
            )
            .andExpect(status().isOk());

        // Validate the Achievements in the database
        List<Achievements> achievementsList = achievementsRepository.findAll();
        assertThat(achievementsList).hasSize(databaseSizeBeforeUpdate);
        Achievements testAchievements = achievementsList.get(achievementsList.size() - 1);
        assertThat(testAchievements.getCertificationTitle()).isEqualTo(UPDATED_CERTIFICATION_TITLE);
        assertThat(testAchievements.getCertificateDescription()).isEqualTo(UPDATED_CERTIFICATE_DESCRIPTION);
        assertThat(testAchievements.getCertificationType()).isEqualTo(UPDATED_CERTIFICATION_TYPE);
        assertThat(testAchievements.getDateOfCompletion()).isEqualTo(UPDATED_DATE_OF_COMPLETION);
        assertThat(testAchievements.getCertificationScore()).isEqualTo(UPDATED_CERTIFICATION_SCORE);
        assertThat(testAchievements.getUploadCertificate()).isEqualTo(UPDATED_UPLOAD_CERTIFICATE);
    }

    @Test
    @Transactional
    void patchNonExistingAchievements() throws Exception {
        int databaseSizeBeforeUpdate = achievementsRepository.findAll().size();
        achievements.setId(count.incrementAndGet());

        // Create the Achievements
        AchievementsDTO achievementsDTO = achievementsMapper.toDto(achievements);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAchievementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, achievementsDTO.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(achievementsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Achievements in the database
        List<Achievements> achievementsList = achievementsRepository.findAll();
        assertThat(achievementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAchievements() throws Exception {
        int databaseSizeBeforeUpdate = achievementsRepository.findAll().size();
        achievements.setId(count.incrementAndGet());

        // Create the Achievements
        AchievementsDTO achievementsDTO = achievementsMapper.toDto(achievements);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAchievementsMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(achievementsDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Achievements in the database
        List<Achievements> achievementsList = achievementsRepository.findAll();
        assertThat(achievementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAchievements() throws Exception {
        int databaseSizeBeforeUpdate = achievementsRepository.findAll().size();
        achievements.setId(count.incrementAndGet());

        // Create the Achievements
        AchievementsDTO achievementsDTO = achievementsMapper.toDto(achievements);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAchievementsMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(achievementsDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Achievements in the database
        List<Achievements> achievementsList = achievementsRepository.findAll();
        assertThat(achievementsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAchievements() throws Exception {
        // Initialize the database
        achievementsRepository.saveAndFlush(achievements);

        int databaseSizeBeforeDelete = achievementsRepository.findAll().size();

        // Delete the achievements
        restAchievementsMockMvc
            .perform(delete(ENTITY_API_URL_ID, achievements.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Achievements> achievementsList = achievementsRepository.findAll();
        assertThat(achievementsList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
