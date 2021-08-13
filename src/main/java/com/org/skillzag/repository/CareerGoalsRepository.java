package com.org.skillzag.repository;

import com.org.skillzag.domain.CareerGoals;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the CareerGoals entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CareerGoalsRepository extends JpaRepository<CareerGoals, Long> {}
