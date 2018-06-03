package com.fmi.patokas.service;

import com.fmi.patokas.domain.EmergancyContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EmergancyContact.
 */
public interface EmergancyContactService {

    /**
     * Save a emergancyContact.
     *
     * @param emergancyContact the entity to save
     * @return the persisted entity
     */
    EmergancyContact save(EmergancyContact emergancyContact);

    /**
     * Get all the emergancyContacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmergancyContact> findAll(Pageable pageable);

    /**
     * Get the "id" emergancyContact.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EmergancyContact findOne(Long id);

    /**
     * Delete the "id" emergancyContact.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
