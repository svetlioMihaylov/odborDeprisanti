package com.fmi.patokas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fmi.patokas.domain.EmergancyContact;
import com.fmi.patokas.service.EmergancyContactService;
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

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing EmergancyContact.
 */
@RestController
@RequestMapping("/api")
public class EmergancyContactResource {

    private final Logger log = LoggerFactory.getLogger(EmergancyContactResource.class);

    private static final String ENTITY_NAME = "emergancyContact";

    private final EmergancyContactService emergancyContactService;

    public EmergancyContactResource(EmergancyContactService emergancyContactService) {
        this.emergancyContactService = emergancyContactService;
    }

    /**
     * POST  /emergancy-contacts : Create a new emergancyContact.
     *
     * @param emergancyContact the emergancyContact to create
     * @return the ResponseEntity with status 201 (Created) and with body the new emergancyContact, or with status 400 (Bad Request) if the emergancyContact has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/emergancy-contacts")
    @Timed
    public ResponseEntity<EmergancyContact> createEmergancyContact(@Valid @RequestBody EmergancyContact emergancyContact) throws URISyntaxException {
        log.debug("REST request to save EmergancyContact : {}", emergancyContact);
        if (emergancyContact.getId() != null) {
            throw new BadRequestAlertException("A new emergancyContact cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmergancyContact result = emergancyContactService.save(emergancyContact);
        return ResponseEntity.created(new URI("/api/emergancy-contacts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /emergancy-contacts : Updates an existing emergancyContact.
     *
     * @param emergancyContact the emergancyContact to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated emergancyContact,
     * or with status 400 (Bad Request) if the emergancyContact is not valid,
     * or with status 500 (Internal Server Error) if the emergancyContact couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/emergancy-contacts")
    @Timed
    public ResponseEntity<EmergancyContact> updateEmergancyContact(@Valid @RequestBody EmergancyContact emergancyContact) throws URISyntaxException {
        log.debug("REST request to update EmergancyContact : {}", emergancyContact);
        if (emergancyContact.getId() == null) {
            return createEmergancyContact(emergancyContact);
        }
        EmergancyContact result = emergancyContactService.save(emergancyContact);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, emergancyContact.getId().toString()))
            .body(result);
    }

    /**
     * GET  /emergancy-contacts : get all the emergancyContacts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of emergancyContacts in body
     */
    @GetMapping("/emergancy-contacts")
    @Timed
    public ResponseEntity<List<EmergancyContact>> getAllEmergancyContacts(Pageable pageable) {
        log.debug("REST request to get a page of EmergancyContacts");
        Page<EmergancyContact> page = emergancyContactService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/emergancy-contacts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /emergancy-contacts/:id : get the "id" emergancyContact.
     *
     * @param id the id of the emergancyContact to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the emergancyContact, or with status 404 (Not Found)
     */
    @GetMapping("/emergancy-contacts/{id}")
    @Timed
    public ResponseEntity<EmergancyContact> getEmergancyContact(@PathVariable Long id) {
        log.debug("REST request to get EmergancyContact : {}", id);
        EmergancyContact emergancyContact = emergancyContactService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(emergancyContact));
    }

    /**
     * DELETE  /emergancy-contacts/:id : delete the "id" emergancyContact.
     *
     * @param id the id of the emergancyContact to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/emergancy-contacts/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmergancyContact(@PathVariable Long id) {
        log.debug("REST request to delete EmergancyContact : {}", id);
        emergancyContactService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
