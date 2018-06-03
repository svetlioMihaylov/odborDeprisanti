package com.fmi.patokas.service.impl;

import com.fmi.patokas.service.EmployeePossitionService;
import com.fmi.patokas.domain.EmployeePossition;
import com.fmi.patokas.repository.EmployeePossitionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EmployeePossition.
 */
@Service
@Transactional
public class EmployeePossitionServiceImpl implements EmployeePossitionService {

    private final Logger log = LoggerFactory.getLogger(EmployeePossitionServiceImpl.class);

    private final EmployeePossitionRepository employeePossitionRepository;

    public EmployeePossitionServiceImpl(EmployeePossitionRepository employeePossitionRepository) {
        this.employeePossitionRepository = employeePossitionRepository;
    }

    /**
     * Save a employeePossition.
     *
     * @param employeePossition the entity to save
     * @return the persisted entity
     */
    @Override
    public EmployeePossition save(EmployeePossition employeePossition) {
        log.debug("Request to save EmployeePossition : {}", employeePossition);
        return employeePossitionRepository.save(employeePossition);
    }

    /**
     * Get all the employeePossitions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmployeePossition> findAll(Pageable pageable) {
        log.debug("Request to get all EmployeePossitions");
        return employeePossitionRepository.findAll(pageable);
    }

    /**
     * Get one employeePossition by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EmployeePossition findOne(Long id) {
        log.debug("Request to get EmployeePossition : {}", id);
        return employeePossitionRepository.findOne(id);
    }

    /**
     * Delete the employeePossition by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmployeePossition : {}", id);
        employeePossitionRepository.delete(id);
    }
}
