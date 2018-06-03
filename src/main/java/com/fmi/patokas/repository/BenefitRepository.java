package com.fmi.patokas.repository;

import com.fmi.patokas.domain.Benefit;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Benefit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {

}
