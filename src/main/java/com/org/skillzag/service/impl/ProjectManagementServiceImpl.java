package com.org.skillzag.service.impl;

import com.org.skillzag.domain.ProjectManagement;
import com.org.skillzag.repository.ProjectManagementRepository;
import com.org.skillzag.service.ProjectManagementService;
import com.org.skillzag.service.dto.ProjectManagementDTO;
import com.org.skillzag.service.mapper.ProjectManagementMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ProjectManagement}.
 */
@Service
@Transactional
public class ProjectManagementServiceImpl implements ProjectManagementService {

    private final Logger log = LoggerFactory.getLogger(ProjectManagementServiceImpl.class);

    private final ProjectManagementRepository projectManagementRepository;

    private final ProjectManagementMapper projectManagementMapper;

    public ProjectManagementServiceImpl(
        ProjectManagementRepository projectManagementRepository,
        ProjectManagementMapper projectManagementMapper
    ) {
        this.projectManagementRepository = projectManagementRepository;
        this.projectManagementMapper = projectManagementMapper;
    }

    @Override
    public ProjectManagementDTO save(ProjectManagementDTO projectManagementDTO) {
        log.debug("Request to save ProjectManagement : {}", projectManagementDTO);
        ProjectManagement projectManagement = projectManagementMapper.toEntity(projectManagementDTO);
        projectManagement = projectManagementRepository.save(projectManagement);
        return projectManagementMapper.toDto(projectManagement);
    }

    @Override
    public Optional<ProjectManagementDTO> partialUpdate(ProjectManagementDTO projectManagementDTO) {
        log.debug("Request to partially update ProjectManagement : {}", projectManagementDTO);

        return projectManagementRepository
            .findById(projectManagementDTO.getId())
            .map(
                existingProjectManagement -> {
                    projectManagementMapper.partialUpdate(existingProjectManagement, projectManagementDTO);

                    return existingProjectManagement;
                }
            )
            .map(projectManagementRepository::save)
            .map(projectManagementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProjectManagementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ProjectManagements");
        return projectManagementRepository.findAll(pageable).map(projectManagementMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProjectManagementDTO> findOne(Long id) {
        log.debug("Request to get ProjectManagement : {}", id);
        return projectManagementRepository.findById(id).map(projectManagementMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ProjectManagement : {}", id);
        projectManagementRepository.deleteById(id);
    }
}
