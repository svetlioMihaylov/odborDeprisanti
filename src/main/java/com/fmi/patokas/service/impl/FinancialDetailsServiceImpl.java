package com.fmi.patokas.service.impl;

import com.fmi.patokas.service.FinancialDetailsService;
import com.fmi.patokas.domain.FinancialDetails;
import com.fmi.patokas.repository.FinancialDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing FinancialDetails.
 */
@Service
@Transactional
public class FinancialDetailsServiceImpl implements FinancialDetailsService {

    private final Logger log = LoggerFactory.getLogger(FinancialDetailsServiceImpl.class);

    private final FinancialDetailsRepository financialDetailsRepository;

    public FinancialDetailsServiceImpl(FinancialDetailsRepository financialDetailsRepository) {
        this.financialDetailsRepository = financialDetailsRepository;
    }

    /**
     * Save a financialDetails.
     *
     * @param financialDetails the entity to save
     * @return the persisted entity
     */
    @Override
    public FinancialDetails save(FinancialDetails financialDetails) {
        log.debug("Request to save FinancialDetails : {}", financialDetails);
        return financialDetailsRepository.save(financialDetails);
    }

    /**
     * Get all the financialDetails.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FinancialDetails> findAll(Pageable pageable) {
        log.debug("Request to get all FinancialDetails");
        return financialDetailsRepository.findAll(pageable);
    }

    /**
     * Get one financialDetails by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public FinancialDetails findOne(Long id) {
        log.debug("Request to get FinancialDetails : {}", id);
        return financialDetailsRepository.findOne(id);
    }

    /**
     * Delete the financialDetails by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FinancialDetails : {}", id);
        financialDetailsRepository.delete(id);
    }
}
