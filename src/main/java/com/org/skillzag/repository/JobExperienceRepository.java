package com.org.skillzag.repository;

import com.org.skillzag.domain.JobExperience;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the JobExperience entity.
 */
@SuppressWarnings("unused")
@Repository
public interface JobExperienceRepository extends JpaRepository<JobExperience, Long> {}
