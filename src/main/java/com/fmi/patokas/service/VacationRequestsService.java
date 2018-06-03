package com.fmi.patokas.service;

import com.fmi.patokas.domain.VacationRequests;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing VacationRequests.
 */
public interface VacationRequestsService {

    /**
     * Save a vacationRequests.
     *
     * @param vacationRequests the entity to save
     * @return the persisted entity
     */
    VacationRequests save(VacationRequests vacationRequests);

    /**
     * Get all the vacationRequests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<VacationRequests> findAll(Pageable pageable);

    /**
     * Get the "id" vacationRequests.
     *
     * @param id the id of the entity
     * @return the entity
     */
    VacationRequests findOne(Long id);

    /**
     * Delete the "id" vacationRequests.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
