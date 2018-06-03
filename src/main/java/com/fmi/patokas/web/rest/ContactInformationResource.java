package com.fmi.patokas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fmi.patokas.domain.ContactInformation;
import com.fmi.patokas.service.ContactInformationService;
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
 * REST controller for managing ContactInformation.
 */
@RestController
@RequestMapping("/api")
public class ContactInformationResource {

    private final Logger log = LoggerFactory.getLogger(ContactInformationResource.class);

    private static final String ENTITY_NAME = "contactInformation";

    private final ContactInformationService contactInformationService;

    public ContactInformationResource(ContactInformationService contactInformationService) {
        this.contactInformationService = contactInformationService;
    }

    /**
     * POST  /contact-informations : Create a new contactInformation.
     *
     * @param contactInformation the contactInformation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contactInformation, or with status 400 (Bad Request) if the contactInformation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/contact-informations")
    @Timed
    public ResponseEntity<ContactInformation> createContactInformation(@Valid @RequestBody ContactInformation contactInformation) throws URISyntaxException {
        log.debug("REST request to save ContactInformation : {}", contactInformation);
        if (contactInformation.getId() != null) {
            throw new BadRequestAlertException("A new contactInformation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContactInformation result = contactInformationService.save(contactInformation);
        return ResponseEntity.created(new URI("/api/contact-informations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contact-informations : Updates an existing contactInformation.
     *
     * @param contactInformation the contactInformation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contactInformation,
     * or with status 400 (Bad Request) if the contactInformation is not valid,
     * or with status 500 (Internal Server Error) if the contactInformation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/contact-informations")
    @Timed
    public ResponseEntity<ContactInformation> updateContactInformation(@Valid @RequestBody ContactInformation contactInformation) throws URISyntaxException {
        log.debug("REST request to update ContactInformation : {}", contactInformation);
        if (contactInformation.getId() == null) {
            return createContactInformation(contactInformation);
        }
        ContactInformation result = contactInformationService.save(contactInformation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, contactInformation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contact-informations : get all the contactInformations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of contactInformations in body
     */
    @GetMapping("/contact-informations")
    @Timed
    public ResponseEntity<List<ContactInformation>> getAllContactInformations(Pageable pageable) {
        log.debug("REST request to get a page of ContactInformations");
        Page<ContactInformation> page = contactInformationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/contact-informations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /contact-informations/:id : get the "id" contactInformation.
     *
     * @param id the id of the contactInformation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contactInformation, or with status 404 (Not Found)
     */
    @GetMapping("/contact-informations/{id}")
    @Timed
    public ResponseEntity<ContactInformation> getContactInformation(@PathVariable Long id) {
        log.debug("REST request to get ContactInformation : {}", id);
        ContactInformation contactInformation = contactInformationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(contactInformation));
    }

    /**
     * DELETE  /contact-informations/:id : delete the "id" contactInformation.
     *
     * @param id the id of the contactInformation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/contact-informations/{id}")
    @Timed
    public ResponseEntity<Void> deleteContactInformation(@PathVariable Long id) {
        log.debug("REST request to delete ContactInformation : {}", id);
        contactInformationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
