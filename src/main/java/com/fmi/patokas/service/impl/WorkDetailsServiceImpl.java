package com.fmi.patokas.service.impl;

import com.fmi.patokas.service.WorkDetailsService;
import com.fmi.patokas.domain.WorkDetails;
import com.fmi.patokas.repository.WorkDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing WorkDetails.
 */
@Service
@Transactional
public class WorkDetailsServiceImpl implements WorkDetailsService {

    private final Logger log = LoggerFactory.getLogger(WorkDetailsServiceImpl.class);

    private final WorkDetailsRepository workDetailsRepository;

    public WorkDetailsServiceImpl(WorkDetailsRepository workDetailsRepository) {
        this.workDetailsRepository = workDetailsRepository;
    }

    /**
     * Save a workDetails.
     *
     * @param workDetails the entity to save
     * @return the persisted entity
     */
    @Override
    public WorkDetails save(WorkDetails workDetails) {
        log.debug("Request to save WorkDetails : {}", workDetails);
        return workDetailsRepository.save(workDetails);
    }

    /**
     * Get all the workDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<WorkDetails> findAll(Pageable pageable) {
        log.debug("Request to get all WorkDetails");
        return workDetailsRepository.findAll(pageable);
    }

    /**
     * Get one workDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public WorkDetails findOne(Long id) {
        log.debug("Request to get WorkDetails : {}", id);
        return workDetailsRepository.findOne(id);
    }

    /**
     * Delete the workDetails by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete WorkDetails : {}", id);
        workDetailsRepository.delete(id);
    }
}
