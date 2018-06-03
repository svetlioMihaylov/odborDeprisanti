package com.fmi.patokas.service;

import com.fmi.patokas.domain.EmployeePossition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EmployeePossition.
 */
public interface EmployeePossitionService {

    /**
     * Save a employeePossition.
     *
     * @param employeePossition the entity to save
     * @return the persisted entity
     */
    EmployeePossition save(EmployeePossition employeePossition);

    /**
     * Get all the employeePossitions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmployeePossition> findAll(Pageable pageable);

    /**
     * Get the "id" employeePossition.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EmployeePossition findOne(Long id);

    /**
     * Delete the "id" employeePossition.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
