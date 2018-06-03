package com.fmi.patokas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fmi.patokas.domain.ExternalPerson;
import com.fmi.patokas.service.ExternalPersonService;
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
 * REST controller for managing ExternalPerson.
 */
@RestController
@RequestMapping("/api")
public class ExternalPersonResource {

    private final Logger log = LoggerFactory.getLogger(ExternalPersonResource.class);

    private static final String ENTITY_NAME = "externalPerson";

    private final ExternalPersonService externalPersonService;

    public ExternalPersonResource(ExternalPersonService externalPersonService) {
        this.externalPersonService = externalPersonService;
    }

    /**
     * POST  /external-people : Create a new externalPerson.
     *
     * @param externalPerson the externalPerson to create
     * @return the ResponseEntity with status 201 (Created) and with body the new externalPerson, or with status 400 (Bad Request) if the externalPerson has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/external-people")
    @Timed
    public ResponseEntity<ExternalPerson> createExternalPerson(@Valid @RequestBody ExternalPerson externalPerson) throws URISyntaxException {
        log.debug("REST request to save ExternalPerson : {}", externalPerson);
        if (externalPerson.getId() != null) {
            throw new BadRequestAlertException("A new externalPerson cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ExternalPerson result = externalPersonService.save(externalPerson);
        return ResponseEntity.created(new URI("/api/external-people/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /external-people : Updates an existing externalPerson.
     *
     * @param externalPerson the externalPerson to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated externalPerson,
     * or with status 400 (Bad Request) if the externalPerson is not valid,
     * or with status 500 (Internal Server Error) if the externalPerson couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/external-people")
    @Timed
    public ResponseEntity<ExternalPerson> updateExternalPerson(@Valid @RequestBody ExternalPerson externalPerson) throws URISyntaxException {
        log.debug("REST request to update ExternalPerson : {}", externalPerson);
        if (externalPerson.getId() == null) {
            return createExternalPerson(externalPerson);
        }
        ExternalPerson result = externalPersonService.save(externalPerson);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, externalPerson.getId().toString()))
            .body(result);
    }

    /**
     * GET  /external-people : get all the externalPeople.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of externalPeople in body
     */
    @GetMapping("/external-people")
    @Timed
    public ResponseEntity<List<ExternalPerson>> getAllExternalPeople(Pageable pageable) {
        log.debug("REST request to get a page of ExternalPeople");
        Page<ExternalPerson> page = externalPersonService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/external-people");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /external-people/:id : get the "id" externalPerson.
     *
     * @param id the id of the externalPerson to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the externalPerson, or with status 404 (Not Found)
     */
    @GetMapping("/external-people/{id}")
    @Timed
    public ResponseEntity<ExternalPerson> getExternalPerson(@PathVariable Long id) {
        log.debug("REST request to get ExternalPerson : {}", id);
        ExternalPerson externalPerson = externalPersonService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(externalPerson));
    }

    /**
     * DELETE  /external-people/:id : delete the "id" externalPerson.
     *
     * @param id the id of the externalPerson to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/external-people/{id}")
    @Timed
    public ResponseEntity<Void> deleteExternalPerson(@PathVariable Long id) {
        log.debug("REST request to delete ExternalPerson : {}", id);
        externalPersonService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
