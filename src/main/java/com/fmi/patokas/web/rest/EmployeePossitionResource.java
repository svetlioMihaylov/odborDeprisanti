package com.fmi.patokas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fmi.patokas.domain.EmployeePossition;
import com.fmi.patokas.service.EmployeePossitionService;
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
 * REST controller for managing EmployeePossition.
 */
@RestController
@RequestMapping("/api")
public class EmployeePossitionResource {

    private final Logger log = LoggerFactory.getLogger(EmployeePossitionResource.class);

    private static final String ENTITY_NAME = "employeePossition";

    private final EmployeePossitionService employeePossitionService;

    public EmployeePossitionResource(EmployeePossitionService employeePossitionService) {
        this.employeePossitionService = employeePossitionService;
    }

    /**
     * POST  /employee-possitions : Create a new employeePossition.
     *
     * @param employeePossition the employeePossition to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeePossition, or with status 400 (Bad Request) if the employeePossition has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-possitions")
    @Timed
    public ResponseEntity<EmployeePossition> createEmployeePossition(@Valid @RequestBody EmployeePossition employeePossition) throws URISyntaxException {
        log.debug("REST request to save EmployeePossition : {}", employeePossition);
        if (employeePossition.getId() != null) {
            throw new BadRequestAlertException("A new employeePossition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeePossition result = employeePossitionService.save(employeePossition);
        return ResponseEntity.created(new URI("/api/employee-possitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-possitions : Updates an existing employeePossition.
     *
     * @param employeePossition the employeePossition to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeePossition,
     * or with status 400 (Bad Request) if the employeePossition is not valid,
     * or with status 500 (Internal Server Error) if the employeePossition couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-possitions")
    @Timed
    public ResponseEntity<EmployeePossition> updateEmployeePossition(@Valid @RequestBody EmployeePossition employeePossition) throws URISyntaxException {
        log.debug("REST request to update EmployeePossition : {}", employeePossition);
        if (employeePossition.getId() == null) {
            return createEmployeePossition(employeePossition);
        }
        EmployeePossition result = employeePossitionService.save(employeePossition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeePossition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-possitions : get all the employeePossitions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of employeePossitions in body
     */
    @GetMapping("/employee-possitions")
    @Timed
    public ResponseEntity<List<EmployeePossition>> getAllEmployeePossitions(Pageable pageable) {
        log.debug("REST request to get a page of EmployeePossitions");
        Page<EmployeePossition> page = employeePossitionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employee-possitions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employee-possitions/:id : get the "id" employeePossition.
     *
     * @param id the id of the employeePossition to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeePossition, or with status 404 (Not Found)
     */
    @GetMapping("/employee-possitions/{id}")
    @Timed
    public ResponseEntity<EmployeePossition> getEmployeePossition(@PathVariable Long id) {
        log.debug("REST request to get EmployeePossition : {}", id);
        EmployeePossition employeePossition = employeePossitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeePossition));
    }

    /**
     * DELETE  /employee-possitions/:id : delete the "id" employeePossition.
     *
     * @param id the id of the employeePossition to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-possitions/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeePossition(@PathVariable Long id) {
        log.debug("REST request to delete EmployeePossition : {}", id);
        employeePossitionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
