package com.fmi.patokas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fmi.patokas.domain.DocumentTemplates;
import com.fmi.patokas.service.DocumentTemplatesService;
import com.fmi.patokas.web.rest.errors.BadRequestAlertException;
import com.fmi.patokas.web.rest.util.HeaderUtil;
import com.fmi.patokas.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing DocumentTemplates.
 */
@RestController
@RequestMapping("/api")
public class DocumentTemplatesResource {

    private final Logger log = LoggerFactory.getLogger(DocumentTemplatesResource.class);

    private static final String ENTITY_NAME = "documentTemplates";

    private final DocumentTemplatesService documentTemplatesService;

    public DocumentTemplatesResource(DocumentTemplatesService documentTemplatesService) {
        this.documentTemplatesService = documentTemplatesService;
    }

    /**
     * POST  /document-templates : Create a new documentTemplates.
     *
     * @param documentTemplates the documentTemplates to create
     * @return the ResponseEntity with status 201 (Created) and with body the new documentTemplates, or with status 400 (Bad Request) if the documentTemplates has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/document-templates")
    @Timed
    public ResponseEntity<DocumentTemplates> createDocumentTemplates(@RequestBody DocumentTemplates documentTemplates) throws URISyntaxException {
        log.debug("REST request to save DocumentTemplates : {}", documentTemplates);
        if (documentTemplates.getId() != null) {
            throw new BadRequestAlertException("A new documentTemplates cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DocumentTemplates result = documentTemplatesService.save(documentTemplates);
        return ResponseEntity.created(new URI("/api/document-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /document-templates : Updates an existing documentTemplates.
     *
     * @param documentTemplates the documentTemplates to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated documentTemplates,
     * or with status 400 (Bad Request) if the documentTemplates is not valid,
     * or with status 500 (Internal Server Error) if the documentTemplates couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/document-templates")
    @Timed
    public ResponseEntity<DocumentTemplates> updateDocumentTemplates(@RequestBody DocumentTemplates documentTemplates) throws URISyntaxException {
        log.debug("REST request to update DocumentTemplates : {}", documentTemplates);
        if (documentTemplates.getId() == null) {
            return createDocumentTemplates(documentTemplates);
        }
        DocumentTemplates result = documentTemplatesService.save(documentTemplates);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, documentTemplates.getId().toString()))
            .body(result);
    }

    /**
     * GET  /document-templates : get all the documentTemplates.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of documentTemplates in body
     */
    @GetMapping("/document-templates")
    @Timed
    public ResponseEntity<List<DocumentTemplates>> getAllDocumentTemplates(Pageable pageable) {
        log.debug("REST request to get a page of DocumentTemplates");
        Page<DocumentTemplates> page = documentTemplatesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/document-templates");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /document-templates/:id : get the "id" documentTemplates.
     *
     * @param id the id of the documentTemplates to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the documentTemplates, or with status 404 (Not Found)
     */
    @GetMapping("/document-templates/{id}")
    @Timed
    public ResponseEntity<DocumentTemplates> getDocumentTemplates(@PathVariable Long id) {
        log.debug("REST request to get DocumentTemplates : {}", id);
        DocumentTemplates documentTemplates = documentTemplatesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(documentTemplates));
    }

    /**
     * DELETE  /document-templates/:id : delete the "id" documentTemplates.
     *
     * @param id the id of the documentTemplates to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/document-templates/{id}")
    @Timed
    public ResponseEntity<Void> deleteDocumentTemplates(@PathVariable Long id) {
        log.debug("REST request to delete DocumentTemplates : {}", id);
        documentTemplatesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
