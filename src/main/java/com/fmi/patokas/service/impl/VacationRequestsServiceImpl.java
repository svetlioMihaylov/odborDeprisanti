package com.fmi.patokas.service.impl;

import com.fmi.patokas.service.VacationRequestsService;
import com.fmi.patokas.domain.VacationRequests;
import com.fmi.patokas.repository.VacationRequestsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing VacationRequests.
 */
@Service
@Transactional
public class VacationRequestsServiceImpl implements VacationRequestsService {

    private final Logger log = LoggerFactory.getLogger(VacationRequestsServiceImpl.class);

    private final VacationRequestsRepository vacationRequestsRepository;

    public VacationRequestsServiceImpl(VacationRequestsRepository vacationRequestsRepository) {
        this.vacationRequestsRepository = vacationRequestsRepository;
    }

    /**
     * Save a vacationRequests.
     *
     * @param vacationRequests the entity to save
     * @return the persisted entity
     */
    @Override
    public VacationRequests save(VacationRequests vacationRequests) {
        log.debug("Request to save VacationRequests : {}", vacationRequests);
        return vacationRequestsRepository.save(vacationRequests);
    }

    /**
     * Get all the vacationRequests.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<VacationRequests> findAll(Pageable pageable) {
        log.debug("Request to get all VacationRequests");
        return vacationRequestsRepository.findAll(pageable);
    }

    /**
     * Get one vacationRequests by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public VacationRequests findOne(Long id) {
        log.debug("Request to get VacationRequests : {}", id);
        return vacationRequestsRepository.findOne(id);
    }

    /**
     * Delete the vacationRequests by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete VacationRequests : {}", id);
        vacationRequestsRepository.delete(id);
    }
}
