package com.fmi.patokas.service;

import com.fmi.patokas.domain.IDCard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing IDCard.
 */
public interface IDCardService {

    /**
     * Save a iDCard.
     *
     * @param iDCard the entity to save
     * @return the persisted entity
     */
    IDCard save(IDCard iDCard);

    /**
     * Get all the iDCards.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<IDCard> findAll(Pageable pageable);

    /**
     * Get the "id" iDCard.
     *
     * @param id the id of the entity
     * @return the entity
     */
    IDCard findOne(Long id);

    /**
     * Delete the "id" iDCard.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
