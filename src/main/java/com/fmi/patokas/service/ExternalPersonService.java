package com.fmi.patokas.service;

import com.fmi.patokas.domain.ExternalPerson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ExternalPerson.
 */
public interface ExternalPersonService {

    /**
     * Save a externalPerson.
     *
     * @param externalPerson the entity to save
     * @return the persisted entity
     */
    ExternalPerson save(ExternalPerson externalPerson);

    /**
     * Get all the externalPeople.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ExternalPerson> findAll(Pageable pageable);

    /**
     * Get the "id" externalPerson.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ExternalPerson findOne(Long id);

    /**
     * Delete the "id" externalPerson.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
