package com.fmi.patokas.service.impl;

import com.fmi.patokas.service.EmployeeNoteService;
import com.fmi.patokas.domain.EmployeeNote;
import com.fmi.patokas.repository.EmployeeNoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EmployeeNote.
 */
@Service
@Transactional
public class EmployeeNoteServiceImpl implements EmployeeNoteService {

    private final Logger log = LoggerFactory.getLogger(EmployeeNoteServiceImpl.class);

    private final EmployeeNoteRepository employeeNoteRepository;

    public EmployeeNoteServiceImpl(EmployeeNoteRepository employeeNoteRepository) {
        this.employeeNoteRepository = employeeNoteRepository;
    }

    /**
     * Save a employeeNote.
     *
     * @param employeeNote the entity to save
     * @return the persisted entity
     */
    @Override
    public EmployeeNote save(EmployeeNote employeeNote) {
        log.debug("Request to save EmployeeNote : {}", employeeNote);
        return employeeNoteRepository.save(employeeNote);
    }

    /**
     * Get all the employeeNotes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeeNote> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeeNotes");
        return employeeNoteRepository.findAll(pageable);
    }

    /**
     * Get one employeeNote by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EmployeeNote findOne(Long id) {
        log.debug("Request to get EmployeeNote : {}", id);
        return employeeNoteRepository.findOne(id);
    }

    /**
     * Delete the employeeNote by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmployeeNote : {}", id);
        employeeNoteRepository.delete(id);
    }
}
