package com.fmi.patokas.service;

import com.fmi.patokas.domain.EmployeeNote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EmployeeNote.
 */
public interface EmployeeNoteService {

    /**
     * Save a employeeNote.
     *
     * @param employeeNote the entity to save
     * @return the persisted entity
     */
    EmployeeNote save(EmployeeNote employeeNote);

    /**
     * Get all the employeeNotes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmployeeNote> findAll(Pageable pageable);

    /**
     * Get the "id" employeeNote.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EmployeeNote findOne(Long id);

    /**
     * Delete the "id" employeeNote.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
