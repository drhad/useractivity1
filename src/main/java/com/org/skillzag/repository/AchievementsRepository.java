package com.org.skillzag.repository;

import com.org.skillzag.domain.Achievements;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Achievements entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AchievementsRepository extends JpaRepository<Achievements, Long> {}
