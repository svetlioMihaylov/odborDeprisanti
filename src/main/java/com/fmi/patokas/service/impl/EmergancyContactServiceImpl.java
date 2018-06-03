package com.fmi.patokas.service.impl;

import com.fmi.patokas.service.EmergancyContactService;
import com.fmi.patokas.domain.EmergancyContact;
import com.fmi.patokas.repository.EmergancyContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing EmergancyContact.
 */
@Service
@Transactional
public class EmergancyContactServiceImpl implements EmergancyContactService {

    private final Logger log = LoggerFactory.getLogger(EmergancyContactServiceImpl.class);

    private final EmergancyContactRepository emergancyContactRepository;

    public EmergancyContactServiceImpl(EmergancyContactRepository emergancyContactRepository) {
        this.emergancyContactRepository = emergancyContactRepository;
    }

    /**
     * Save a emergancyContact.
     *
     * @param emergancyContact the entity to save
     * @return the persisted entity
     */
    @Override
    public EmergancyContact save(EmergancyContact emergancyContact) {
        log.debug("Request to save EmergancyContact : {}", emergancyContact);
        return emergancyContactRepository.save(emergancyContact);
    }

    /**
     * Get all the emergancyContacts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EmergancyContact> findAll(Pageable pageable) {
        log.debug("Request to get all EmergancyContacts");
        return emergancyContactRepository.findAll(pageable);
    }

    /**
     * Get one emergancyContact by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public EmergancyContact findOne(Long id) {
        log.debug("Request to get EmergancyContact : {}", id);
        return emergancyContactRepository.findOne(id);
    }

    /**
     * Delete the emergancyContact by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EmergancyContact : {}", id);
        emergancyContactRepository.delete(id);
    }
}
