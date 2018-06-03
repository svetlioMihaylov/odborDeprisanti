package com.fmi.patokas.service;

import com.fmi.patokas.domain.Benefit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Benefit.
 */
public interface BenefitService {

    /**
     * Save a benefit.
     *
     * @param benefit the entity to save
     * @return the persisted entity
     */
    Benefit save(Benefit benefit);

    /**
     * Get all the benefits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Benefit> findAll(Pageable pageable);

    /**
     * Get the "id" benefit.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Benefit findOne(Long id);

    /**
     * Delete the "id" benefit.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
