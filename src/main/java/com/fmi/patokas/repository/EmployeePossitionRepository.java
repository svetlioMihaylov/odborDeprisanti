package com.fmi.patokas.repository;

import com.fmi.patokas.domain.EmployeePossition;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EmployeePossition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EmployeePossitionRepository extends JpaRepository<EmployeePossition, Long> {

}
