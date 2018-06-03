package com.fmi.patokas.service.impl;

import com.fmi.patokas.service.ExternalPersonService;
import com.fmi.patokas.domain.ExternalPerson;
import com.fmi.patokas.repository.ExternalPersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing ExternalPerson.
 */
@Service
@Transactional
public class ExternalPersonServiceImpl implements ExternalPersonService {

    private final Logger log = LoggerFactory.getLogger(ExternalPersonServiceImpl.class);

    private final ExternalPersonRepository externalPersonRepository;

    public ExternalPersonServiceImpl(ExternalPersonRepository externalPersonRepository) {
        this.externalPersonRepository = externalPersonRepository;
    }

    /**
     * Save a externalPerson.
     *
     * @param externalPerson the entity to save
     * @return the persisted entity
     */
    @Override
    public ExternalPerson save(ExternalPerson externalPerson) {
        log.debug("Request to save ExternalPerson : {}", externalPerson);
        return externalPersonRepository.save(externalPerson);
    }

    /**
     * Get all the externalPeople.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ExternalPerson> findAll(Pageable pageable) {
        log.debug("Request to get all ExternalPeople");
        return externalPersonRepository.findAll(pageable);
    }

    /**
     * Get one externalPerson by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public ExternalPerson findOne(Long id) {
        log.debug("Request to get ExternalPerson : {}", id);
        return externalPersonRepository.findOne(id);
    }

    /**
     * Delete the externalPerson by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ExternalPerson : {}", id);
        externalPersonRepository.delete(id);
    }
}
