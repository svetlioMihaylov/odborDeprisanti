package com.fmi.patokas.service;

import com.fmi.patokas.domain.FinancialDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing FinancialDetails.
 */
public interface FinancialDetailsService {

    /**
     * Save a financialDetails.
     *
     * @param financialDetails the entity to save
     * @return the persisted entity
     */
    FinancialDetails save(FinancialDetails financialDetails);

    /**
     * Get all the financialDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<FinancialDetails> findAll(Pageable pageable);

    /**
     * Get the "id" financialDetails.
     *
     * @param id the id of the entity
     * @return the entity
     */
    FinancialDetails findOne(Long id);

    /**
     * Delete the "id" financialDetails.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
