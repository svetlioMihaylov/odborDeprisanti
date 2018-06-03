package com.fmi.patokas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fmi.patokas.domain.VacationRequests;
import com.fmi.patokas.service.VacationRequestsService;
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
 * REST controller for managing VacationRequests.
 */
@RestController
@RequestMapping("/api")
public class VacationRequestsResource {

    private final Logger log = LoggerFactory.getLogger(VacationRequestsResource.class);

    private static final String ENTITY_NAME = "vacationRequests";

    private final VacationRequestsService vacationRequestsService;

    public VacationRequestsResource(VacationRequestsService vacationRequestsService) {
        this.vacationRequestsService = vacationRequestsService;
    }

    /**
     * POST  /vacation-requests : Create a new vacationRequests.
     *
     * @param vacationRequests the vacationRequests to create
     * @return the ResponseEntity with status 201 (Created) and with body the new vacationRequests, or with status 400 (Bad Request) if the vacationRequests has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/vacation-requests")
    @Timed
    public ResponseEntity<VacationRequests> createVacationRequests(@Valid @RequestBody VacationRequests vacationRequests) throws URISyntaxException {
        log.debug("REST request to save VacationRequests : {}", vacationRequests);
        if (vacationRequests.getId() != null) {
            throw new BadRequestAlertException("A new vacationRequests cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VacationRequests result = vacationRequestsService.save(vacationRequests);
        return ResponseEntity.created(new URI("/api/vacation-requests/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vacation-requests : Updates an existing vacationRequests.
     *
     * @param vacationRequests the vacationRequests to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated vacationRequests,
     * or with status 400 (Bad Request) if the vacationRequests is not valid,
     * or with status 500 (Internal Server Error) if the vacationRequests couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/vacation-requests")
    @Timed
    public ResponseEntity<VacationRequests> updateVacationRequests(@Valid @RequestBody VacationRequests vacationRequests) throws URISyntaxException {
        log.debug("REST request to update VacationRequests : {}", vacationRequests);
        if (vacationRequests.getId() == null) {
            return createVacationRequests(vacationRequests);
        }
        VacationRequests result = vacationRequestsService.save(vacationRequests);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, vacationRequests.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vacation-requests : get all the vacationRequests.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of vacationRequests in body
     */
    @GetMapping("/vacation-requests")
    @Timed
    public ResponseEntity<List<VacationRequests>> getAllVacationRequests(Pageable pageable) {
        log.debug("REST request to get a page of VacationRequests");
        Page<VacationRequests> page = vacationRequestsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vacation-requests");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vacation-requests/:id : get the "id" vacationRequests.
     *
     * @param id the id of the vacationRequests to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the vacationRequests, or with status 404 (Not Found)
     */
    @GetMapping("/vacation-requests/{id}")
    @Timed
    public ResponseEntity<VacationRequests> getVacationRequests(@PathVariable Long id) {
        log.debug("REST request to get VacationRequests : {}", id);
        VacationRequests vacationRequests = vacationRequestsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(vacationRequests));
    }

    /**
     * DELETE  /vacation-requests/:id : delete the "id" vacationRequests.
     *
     * @param id the id of the vacationRequests to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/vacation-requests/{id}")
    @Timed
    public ResponseEntity<Void> deleteVacationRequests(@PathVariable Long id) {
        log.debug("REST request to delete VacationRequests : {}", id);
        vacationRequestsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
