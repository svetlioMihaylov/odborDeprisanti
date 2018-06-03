package com.fmi.patokas.service.impl;

import com.fmi.patokas.service.EmployeePhotoService;
import com.fmi.patokas.domain.EmployeePhoto;
import com.fmi.patokas.repository.EmployeePhotoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EmployeePhoto.
 */
@Service
@Transactional
public class EmployeePhotoServiceImpl implements EmployeePhotoService {

    private final Logger log = LoggerFactory.getLogger(EmployeePhotoServiceImpl.class);

    private final EmployeePhotoRepository employeePhotoRepository;

    public EmployeePhotoServiceImpl(EmployeePhotoRepository employeePhotoRepository) {
        this.employeePhotoRepository = employeePhotoRepository;
    }

    /**
     * Save a employeePhoto.
     *
     * @param employeePhoto the entity to save
     * @return the persisted entity
     */
    @Override
    public EmployeePhoto save(EmployeePhoto employeePhoto) {
        log.debug("Request to save EmployeePhoto : {}", employeePhoto);
        return employeePhotoRepository.save(employeePhoto);
    }

    /**
     * Get all the employeePhotos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeePhoto> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeePhotos");
        return employeePhotoRepository.findAll(pageable);
    }

    /**
     * Get one employeePhoto by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EmployeePhoto findOne(Long id) {
        log.debug("Request to get EmployeePhoto : {}", id);
        return employeePhotoRepository.findOne(id);
    }

    /**
     * Delete the employeePhoto by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmployeePhoto : {}", id);
        employeePhotoRepository.delete(id);
    }
}
