package com.fmi.patokas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fmi.patokas.domain.FinancialDetails;
import com.fmi.patokas.service.FinancialDetailsService;
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
 * REST controller for managing FinancialDetails.
 */
@RestController
@RequestMapping("/api")
public class FinancialDetailsResource {

    private final Logger log = LoggerFactory.getLogger(FinancialDetailsResource.class);

    private static final String ENTITY_NAME = "financialDetails";

    private final FinancialDetailsService financialDetailsService;

    public FinancialDetailsResource(FinancialDetailsService financialDetailsService) {
        this.financialDetailsService = financialDetailsService;
    }

    /**
     * POST  /financial-details : Create a new financialDetails.
     *
     * @param financialDetails the financialDetails to create
     * @return the ResponseEntity with status 201 (Created) and with body the new financialDetails, or with status 400 (Bad Request) if the financialDetails has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/financial-details")
    @Timed
    public ResponseEntity<FinancialDetails> createFinancialDetails(@Valid @RequestBody FinancialDetails financialDetails) throws URISyntaxException {
        log.debug("REST request to save FinancialDetails : {}", financialDetails);
        if (financialDetails.getId() != null) {
            throw new BadRequestAlertException("A new financialDetails cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FinancialDetails result = financialDetailsService.save(financialDetails);
        return ResponseEntity.created(new URI("/api/financial-details/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /financial-details : Updates an existing financialDetails.
     *
     * @param financialDetails the financialDetails to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated financialDetails,
     * or with status 400 (Bad Request) if the financialDetails is not valid,
     * or with status 500 (Internal Server Error) if the financialDetails couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/financial-details")
    @Timed
    public ResponseEntity<FinancialDetails> updateFinancialDetails(@Valid @RequestBody FinancialDetails financialDetails) throws URISyntaxException {
        log.debug("REST request to update FinancialDetails : {}", financialDetails);
        if (financialDetails.getId() == null) {
            return createFinancialDetails(financialDetails);
        }
        FinancialDetails result = financialDetailsService.save(financialDetails);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, financialDetails.getId().toString()))
            .body(result);
    }

    /**
     * GET  /financial-details : get all the financialDetails.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of financialDetails in body
     */
    @GetMapping("/financial-details")
    @Timed
    public ResponseEntity<List<FinancialDetails>> getAllFinancialDetails(Pageable pageable) {
        log.debug("REST request to get a page of FinancialDetails");
        Page<FinancialDetails> page = financialDetailsService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/financial-details");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /financial-details/:id : get the "id" financialDetails.
     *
     * @param id the id of the financialDetails to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the financialDetails, or with status 404 (Not Found)
     */
    @GetMapping("/financial-details/{id}")
    @Timed
    public ResponseEntity<FinancialDetails> getFinancialDetails(@PathVariable Long id) {
        log.debug("REST request to get FinancialDetails : {}", id);
        FinancialDetails financialDetails = financialDetailsService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(financialDetails));
    }

    /**
     * DELETE  /financial-details/:id : delete the "id" financialDetails.
     *
     * @param id the id of the financialDetails to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/financial-details/{id}")
    @Timed
    public ResponseEntity<Void> deleteFinancialDetails(@PathVariable Long id) {
        log.debug("REST request to delete FinancialDetails : {}", id);
        financialDetailsService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
