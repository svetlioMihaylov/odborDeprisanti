package com.fmi.patokas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fmi.patokas.domain.WorkDetails;
import com.fmi.patokas.service.WorkDetailsService;
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
 * REST controller for managing WorkDetails.
 */
@RestController
@RequestMapping("/api")
public class WorkDetailsResource {

    private final Logger log = LoggerFactory.getLogger(WorkDetailsResource.class);

    private static final String ENTITY_NAME = "workDetails";

    private final WorkDetailsService workDetailsService;

    public WorkDetailsResource(WorkDetailsService workDetailsService) {
        this.workDetailsService = workDetailsService;
    }

    /**
     * POST  /work-details : Create a new workDetails.
     *
     * @param workDetails the workDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new workDetails, or with status 400 (Bad Request) if the workDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/work-details")
    @Timed
    public ResponseEntity<WorkDetails> createWorkDetails(@Valid @RequestBody WorkDetails workDetails) throws URISyntaxException {
        log.debug("REST request to save WorkDetails : {}", workDetails);
        if (workDetails.getId() != null) {
            throw new BadRequestAlertException("A new workDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WorkDetails result = workDetailsService.save(workDetails);
        return ResponseEntity.created(new URI("/api/work-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /work-details : Updates an existing workDetails.
     *
     * @param workDetails the workDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated workDetails,
     * or with status 400 (Bad Request) if the workDetails is not valid,
     * or with status 500 (Internal Server Error) if the workDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/work-details")
    @Timed
    public ResponseEntity<WorkDetails> updateWorkDetails(@Valid @RequestBody WorkDetails workDetails) throws URISyntaxException {
        log.debug("REST request to update WorkDetails : {}", workDetails);
        if (workDetails.getId() == null) {
            return createWorkDetails(workDetails);
        }
        WorkDetails result = workDetailsService.save(workDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, workDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /work-details : get all the workDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of workDetails in body
     */
    @GetMapping("/work-details")
    @Timed
    public ResponseEntity<List<WorkDetails>> getAllWorkDetails(Pageable pageable) {
        log.debug("REST request to get a page of WorkDetails");
        Page<WorkDetails> page = workDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/work-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /work-details/:id : get the "id" workDetails.
     *
     * @param id the id of the workDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the workDetails, or with status 404 (Not Found)
     */
    @GetMapping("/work-details/{id}")
    @Timed
    public ResponseEntity<WorkDetails> getWorkDetails(@PathVariable Long id) {
        log.debug("REST request to get WorkDetails : {}", id);
        WorkDetails workDetails = workDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(workDetails));
    }

    /**
     * DELETE  /work-details/:id : delete the "id" workDetails.
     *
     * @param id the id of the workDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/work-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteWorkDetails(@PathVariable Long id) {
        log.debug("REST request to delete WorkDetails : {}", id);
        workDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
