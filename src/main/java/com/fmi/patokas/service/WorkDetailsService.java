package com.fmi.patokas.service;

import com.fmi.patokas.domain.WorkDetails;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing WorkDetails.
 */
public interface WorkDetailsService {

    /**
     * Save a workDetails.
     *
     * @param workDetails the entity to save
     * @return the persisted entity
     */
    WorkDetails save(WorkDetails workDetails);

    /**
     * Get all the workDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<WorkDetails> findAll(Pageable pageable);

    /**
     * Get the "id" workDetails.
     *
     * @param id the id of the entity
     * @return the entity
     */
    WorkDetails findOne(Long id);

    /**
     * Delete the "id" workDetails.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
