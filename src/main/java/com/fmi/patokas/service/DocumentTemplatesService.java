package com.fmi.patokas.service;

import com.fmi.patokas.domain.DocumentTemplates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing DocumentTemplates.
 */
public interface DocumentTemplatesService {

    /**
     * Save a documentTemplates.
     *
     * @param documentTemplates the entity to save
     * @return the persisted entity
     */
    DocumentTemplates save(DocumentTemplates documentTemplates);

    /**
     * Get all the documentTemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DocumentTemplates> findAll(Pageable pageable);

    /**
     * Get the "id" documentTemplates.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DocumentTemplates findOne(Long id);

    /**
     * Delete the "id" documentTemplates.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
