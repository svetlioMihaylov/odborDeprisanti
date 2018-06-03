package com.fmi.patokas.service.impl;

import com.fmi.patokas.service.DocumentTemplatesService;
import com.fmi.patokas.domain.DocumentTemplates;
import com.fmi.patokas.repository.DocumentTemplatesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing DocumentTemplates.
 */
@Service
@Transactional
public class DocumentTemplatesServiceImpl implements DocumentTemplatesService {

    private final Logger log = LoggerFactory.getLogger(DocumentTemplatesServiceImpl.class);

    private final DocumentTemplatesRepository documentTemplatesRepository;

    public DocumentTemplatesServiceImpl(DocumentTemplatesRepository documentTemplatesRepository) {
        this.documentTemplatesRepository = documentTemplatesRepository;
    }

    /**
     * Save a documentTemplates.
     *
     * @param documentTemplates the entity to save
     * @return the persisted entity
     */
    @Override
    public DocumentTemplates save(DocumentTemplates documentTemplates) {
        log.debug("Request to save DocumentTemplates : {}", documentTemplates);
        return documentTemplatesRepository.save(documentTemplates);
    }

    /**
     * Get all the documentTemplates.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DocumentTemplates> findAll(Pageable pageable) {
        log.debug("Request to get all DocumentTemplates");
        return documentTemplatesRepository.findAll(pageable);
    }

    /**
     * Get one documentTemplates by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DocumentTemplates findOne(Long id) {
        log.debug("Request to get DocumentTemplates : {}", id);
        return documentTemplatesRepository.findOne(id);
    }

    /**
     * Delete the documentTemplates by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DocumentTemplates : {}", id);
        documentTemplatesRepository.delete(id);
    }
}
