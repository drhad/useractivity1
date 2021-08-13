package com.org.skillzag.service.impl;

import com.org.skillzag.domain.Announcements;
import com.org.skillzag.repository.AnnouncementsRepository;
import com.org.skillzag.service.AnnouncementsService;
import com.org.skillzag.service.dto.AnnouncementsDTO;
import com.org.skillzag.service.mapper.AnnouncementsMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Announcements}.
 */
@Service
@Transactional
public class AnnouncementsServiceImpl implements AnnouncementsService {

    private final Logger log = LoggerFactory.getLogger(AnnouncementsServiceImpl.class);

    private final AnnouncementsRepository announcementsRepository;

    private final AnnouncementsMapper announcementsMapper;

    public AnnouncementsServiceImpl(AnnouncementsRepository announcementsRepository, AnnouncementsMapper announcementsMapper) {
        this.announcementsRepository = announcementsRepository;
        this.announcementsMapper = announcementsMapper;
    }

    @Override
    public AnnouncementsDTO save(AnnouncementsDTO announcementsDTO) {
        log.debug("Request to save Announcements : {}", announcementsDTO);
        Announcements announcements = announcementsMapper.toEntity(announcementsDTO);
        announcements = announcementsRepository.save(announcements);
        return announcementsMapper.toDto(announcements);
    }

    @Override
    public Optional<AnnouncementsDTO> partialUpdate(AnnouncementsDTO announcementsDTO) {
        log.debug("Request to partially update Announcements : {}", announcementsDTO);

        return announcementsRepository
            .findById(announcementsDTO.getId())
            .map(
                existingAnnouncements -> {
                    announcementsMapper.partialUpdate(existingAnnouncements, announcementsDTO);

                    return existingAnnouncements;
                }
            )
            .map(announcementsRepository::save)
            .map(announcementsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnouncementsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Announcements");
        return announcementsRepository.findAll(pageable).map(announcementsMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnnouncementsDTO> findOne(Long id) {
        log.debug("Request to get Announcements : {}", id);
        return announcementsRepository.findById(id).map(announcementsMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Announcements : {}", id);
        announcementsRepository.deleteById(id);
    }
}
