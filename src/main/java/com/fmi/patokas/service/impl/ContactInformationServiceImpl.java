package com.fmi.patokas.service.impl;

import com.fmi.patokas.service.ContactInformationService;
import com.fmi.patokas.domain.ContactInformation;
import com.fmi.patokas.repository.ContactInformationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ContactInformation.
 */
@Service
@Transactional
public class ContactInformationServiceImpl implements ContactInformationService {

    private final Logger log = LoggerFactory.getLogger(ContactInformationServiceImpl.class);

    private final ContactInformationRepository contactInformationRepository;

    public ContactInformationServiceImpl(ContactInformationRepository contactInformationRepository) {
        this.contactInformationRepository = contactInformationRepository;
    }

    /**
     * Save a contactInformation.
     *
     * @param contactInformation the entity to save
     * @return the persisted entity
     */
    @Override
    public ContactInformation save(ContactInformation contactInformation) {
        log.debug("Request to save ContactInformation : {}", contactInformation);
        return contactInformationRepository.save(contactInformation);
    }

    /**
     * Get all the contactInformations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ContactInformation> findAll(Pageable pageable) {
        log.debug("Request to get all ContactInformations");
        return contactInformationRepository.findAll(pageable);
    }

    /**
     * Get one contactInformation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ContactInformation findOne(Long id) {
        log.debug("Request to get ContactInformation : {}", id);
        return contactInformationRepository.findOne(id);
    }

    /**
     * Delete the contactInformation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ContactInformation : {}", id);
        contactInformationRepository.delete(id);
    }
}
