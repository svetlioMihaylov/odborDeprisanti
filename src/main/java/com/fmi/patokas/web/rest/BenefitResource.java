package com.fmi.patokas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fmi.patokas.domain.Benefit;
import com.fmi.patokas.service.BenefitService;
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
 * REST controller for managing Benefit.
 */
@RestController
@RequestMapping("/api")
public class BenefitResource {

    private final Logger log = LoggerFactory.getLogger(BenefitResource.class);

    private static final String ENTITY_NAME = "benefit";

    private final BenefitService benefitService;

    public BenefitResource(BenefitService benefitService) {
        this.benefitService = benefitService;
    }

    /**
     * POST  /benefits : Create a new benefit.
     *
     * @param benefit the benefit to create
     * @return the ResponseEntity with status 201 (Created) and with body the new benefit, or with status 400 (Bad Request) if the benefit has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/benefits")
    @Timed
    public ResponseEntity<Benefit> createBenefit(@Valid @RequestBody Benefit benefit) throws URISyntaxException {
        log.debug("REST request to save Benefit : {}", benefit);
        if (benefit.getId() != null) {
            throw new BadRequestAlertException("A new benefit cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Benefit result = benefitService.save(benefit);
        return ResponseEntity.created(new URI("/api/benefits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /benefits : Updates an existing benefit.
     *
     * @param benefit the benefit to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated benefit,
     * or with status 400 (Bad Request) if the benefit is not valid,
     * or with status 500 (Internal Server Error) if the benefit couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/benefits")
    @Timed
    public ResponseEntity<Benefit> updateBenefit(@Valid @RequestBody Benefit benefit) throws URISyntaxException {
        log.debug("REST request to update Benefit : {}", benefit);
        if (benefit.getId() == null) {
            return createBenefit(benefit);
        }
        Benefit result = benefitService.save(benefit);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, benefit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /benefits : get all the benefits.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of benefits in body
     */
    @GetMapping("/benefits")
    @Timed
    public ResponseEntity<List<Benefit>> getAllBenefits(Pageable pageable) {
        log.debug("REST request to get a page of Benefits");
        Page<Benefit> page = benefitService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/benefits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /benefits/:id : get the "id" benefit.
     *
     * @param id the id of the benefit to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the benefit, or with status 404 (Not Found)
     */
    @GetMapping("/benefits/{id}")
    @Timed
    public ResponseEntity<Benefit> getBenefit(@PathVariable Long id) {
        log.debug("REST request to get Benefit : {}", id);
        Benefit benefit = benefitService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(benefit));
    }

    /**
     * DELETE  /benefits/:id : delete the "id" benefit.
     *
     * @param id the id of the benefit to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/benefits/{id}")
    @Timed
    public ResponseEntity<Void> deleteBenefit(@PathVariable Long id) {
        log.debug("REST request to delete Benefit : {}", id);
        benefitService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
