package com.fmi.patokas.service;

import com.fmi.patokas.domain.EmployeePhoto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing EmployeePhoto.
 */
public interface EmployeePhotoService {

    /**
     * Save a employeePhoto.
     *
     * @param employeePhoto the entity to save
     * @return the persisted entity
     */
    EmployeePhoto save(EmployeePhoto employeePhoto);

    /**
     * Get all the employeePhotos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EmployeePhoto> findAll(Pageable pageable);

    /**
     * Get the "id" employeePhoto.
     *
     * @param id the id of the entity
     * @return the entity
     */
    EmployeePhoto findOne(Long id);

    /**
     * Delete the "id" employeePhoto.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
