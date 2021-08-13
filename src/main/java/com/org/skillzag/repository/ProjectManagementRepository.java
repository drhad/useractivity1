package com.org.skillzag.repository;

import com.org.skillzag.domain.ProjectManagement;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProjectManagement entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProjectManagementRepository extends JpaRepository<ProjectManagement, Long> {}
