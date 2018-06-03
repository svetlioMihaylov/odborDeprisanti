package com.fmi.patokas.service.impl;

import com.fmi.patokas.service.IDCardService;
import com.fmi.patokas.domain.IDCard;
import com.fmi.patokas.repository.IDCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing IDCard.
 */
@Service
@Transactional
public class IDCardServiceImpl implements IDCardService {

    private final Logger log = LoggerFactory.getLogger(IDCardServiceImpl.class);

    private final IDCardRepository iDCardRepository;

    public IDCardServiceImpl(IDCardRepository iDCardRepository) {
        this.iDCardRepository = iDCardRepository;
    }

    /**
     * Save a iDCard.
     *
     * @param iDCard the entity to save
     * @return the persisted entity
     */
    @Override
    public IDCard save(IDCard iDCard) {
        log.debug("Request to save IDCard : {}", iDCard);
        return iDCardRepository.save(iDCard);
    }

    /**
     * Get all the iDCards.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<IDCard> findAll(Pageable pageable) {
        log.debug("Request to get all IDCards");
        return iDCardRepository.findAll(pageable);
    }

    /**
     * Get one iDCard by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public IDCard findOne(Long id) {
        log.debug("Request to get IDCard : {}", id);
        return iDCardRepository.findOne(id);
    }

    /**
     * Delete the iDCard by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IDCard : {}", id);
        iDCardRepository.delete(id);
    }
}
