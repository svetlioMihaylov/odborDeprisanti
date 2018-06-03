package com.fmi.patokas.repository;

import com.fmi.patokas.domain.WorkDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the WorkDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkDetailsRepository extends JpaRepository<WorkDetails, Long> {

}
