package com.org.skillzag.repository;

import com.org.skillzag.domain.Announcements;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Announcements entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnnouncementsRepository extends JpaRepository<Announcements, Long> {}
