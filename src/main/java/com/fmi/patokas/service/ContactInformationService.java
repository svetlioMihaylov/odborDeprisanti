package com.fmi.patokas.service;

import com.fmi.patokas.domain.ContactInformation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing ContactInformation.
 */
public interface ContactInformationService {

    /**
     * Save a contactInformation.
     *
     * @param contactInformation the entity to save
     * @return the persisted entity
     */
    ContactInformation save(ContactInformation contactInformation);

    /**
     * Get all the contactInformations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ContactInformation> findAll(Pageable pageable);

    /**
     * Get the "id" contactInformation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    ContactInformation findOne(Long id);

    /**
     * Delete the "id" contactInformation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
