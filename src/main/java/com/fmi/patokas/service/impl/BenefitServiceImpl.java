package com.fmi.patokas.service.impl;

import com.fmi.patokas.service.BenefitService;
import com.fmi.patokas.domain.Benefit;
import com.fmi.patokas.repository.BenefitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Benefit.
 */
@Service
@Transactional
public class BenefitServiceImpl implements BenefitService {

    private final Logger log = LoggerFactory.getLogger(BenefitServiceImpl.class);

    private final BenefitRepository benefitRepository;

    public BenefitServiceImpl(BenefitRepository benefitRepository) {
        this.benefitRepository = benefitRepository;
    }

    /**
     * Save a benefit.
     *
     * @param benefit the entity to save
     * @return the persisted entity
     */
    @Override
    public Benefit save(Benefit benefit) {
        log.debug("Request to save Benefit : {}", benefit);
        return benefitRepository.save(benefit);
    }

    /**
     * Get all the benefits.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Benefit> findAll(Pageable pageable) {
        log.debug("Request to get all Benefits");
        return benefitRepository.findAll(pageable);
    }

    /**
     * Get one benefit by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Benefit findOne(Long id) {
        log.debug("Request to get Benefit : {}", id);
        return benefitRepository.findOne(id);
    }

    /**
     * Delete the benefit by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Benefit : {}", id);
        benefitRepository.delete(id);
    }
}
