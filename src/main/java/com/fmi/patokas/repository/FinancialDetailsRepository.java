package com.fmi.patokas.repository;

import com.fmi.patokas.domain.FinancialDetails;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the FinancialDetails entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FinancialDetailsRepository extends JpaRepository<FinancialDetails, Long> {

}
