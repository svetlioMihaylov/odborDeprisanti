package com.fmi.patokas.repository;

import com.fmi.patokas.domain.VacationRequests;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the VacationRequests entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VacationRequestsRepository extends JpaRepository<VacationRequests, Long> {

}
