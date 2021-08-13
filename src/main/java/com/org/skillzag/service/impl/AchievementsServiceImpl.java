package com.org.skillzag.service.impl;

import com.org.skillzag.domain.Achievements;
import com.org.skillzag.repository.AchievementsRepository;
import com.org.skillzag.service.AchievementsService;
import com.org.skillzag.service.dto.AchievementsDTO;
import com.org.skillzag.service.mapper.AchievementsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Achievements}.
 */
@Service
@Transactional
public class AchievementsServiceImpl implements AchievementsService {

    private final Logger log = LoggerFactory.getLogger(AchievementsServiceImpl.class);

    private final AchievementsRepository achievementsRepository;

    private final AchievementsMapper achievementsMapper;

    public AchievementsServiceImpl(AchievementsRepository achievementsRepository, AchievementsMapper achievementsMapper) {
        this.achievementsRepository = achievementsRepository;
        this.achievementsMapper = achievementsMapper;
    }

    @Override
    public AchievementsDTO save(AchievementsDTO achievementsDTO) {
        log.debug("Request to save Achievements : {}", achievementsDTO);
        Achievements achievements = achievementsMapper.toEntity(achievementsDTO);
        achievements = achievementsRepository.save(achievements);
        return achievementsMapper.toDto(achievements);
    }

    @Override
    public Optional<AchievementsDTO> partialUpdate(AchievementsDTO achievementsDTO) {
        log.debug("Request to partially update Achievements : {}", achievementsDTO);

        return achievementsRepository
            .findById(achievementsDTO.getId())
            .map(
                existingAchievements -> {
                    achievementsMapper.partialUpdate(existingAchievements, achievementsDTO);

                    return existingAchievements;
                }
            )
            .map(achievementsRepository::save)
            .map(achievementsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AchievementsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Achievements");
        return achievementsRepository.findAll(pageable).map(achievementsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AchievementsDTO> findOne(Long id) {
        log.debug("Request to get Achievements : {}", id);
        return achievementsRepository.findById(id).map(achievementsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Achievements : {}", id);
        achievementsRepository.deleteById(id);
    }
}
