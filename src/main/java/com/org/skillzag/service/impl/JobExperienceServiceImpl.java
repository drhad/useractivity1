package com.org.skillzag.service.impl;

import com.org.skillzag.domain.JobExperience;
import com.org.skillzag.repository.JobExperienceRepository;
import com.org.skillzag.service.JobExperienceService;
import com.org.skillzag.service.dto.JobExperienceDTO;
import com.org.skillzag.service.mapper.JobExperienceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link JobExperience}.
 */
@Service
@Transactional
public class JobExperienceServiceImpl implements JobExperienceService {

    private final Logger log = LoggerFactory.getLogger(JobExperienceServiceImpl.class);

    private final JobExperienceRepository jobExperienceRepository;

    private final JobExperienceMapper jobExperienceMapper;

    public JobExperienceServiceImpl(JobExperienceRepository jobExperienceRepository, JobExperienceMapper jobExperienceMapper) {
        this.jobExperienceRepository = jobExperienceRepository;
        this.jobExperienceMapper = jobExperienceMapper;
    }

    @Override
    public JobExperienceDTO save(JobExperienceDTO jobExperienceDTO) {
        log.debug("Request to save JobExperience : {}", jobExperienceDTO);
        JobExperience jobExperience = jobExperienceMapper.toEntity(jobExperienceDTO);
        jobExperience = jobExperienceRepository.save(jobExperience);
        return jobExperienceMapper.toDto(jobExperience);
    }

    @Override
    public Optional<JobExperienceDTO> partialUpdate(JobExperienceDTO jobExperienceDTO) {
        log.debug("Request to partially update JobExperience : {}", jobExperienceDTO);

        return jobExperienceRepository
            .findById(jobExperienceDTO.getId())
            .map(
                existingJobExperience -> {
                    jobExperienceMapper.partialUpdate(existingJobExperience, jobExperienceDTO);

                    return existingJobExperience;
                }
            )
            .map(jobExperienceRepository::save)
            .map(jobExperienceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobExperienceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobExperiences");
        return jobExperienceRepository.findAll(pageable).map(jobExperienceMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JobExperienceDTO> findOne(Long id) {
        log.debug("Request to get JobExperience : {}", id);
        return jobExperienceRepository.findById(id).map(jobExperienceMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobExperience : {}", id);
        jobExperienceRepository.deleteById(id);
    }
}
