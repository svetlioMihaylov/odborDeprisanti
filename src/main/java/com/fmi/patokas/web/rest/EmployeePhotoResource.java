package com.fmi.patokas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fmi.patokas.domain.EmployeePhoto;
import com.fmi.patokas.service.EmployeePhotoService;
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
 * REST controller for managing EmployeePhoto.
 */
@RestController
@RequestMapping("/api")
public class EmployeePhotoResource {

    private final Logger log = LoggerFactory.getLogger(EmployeePhotoResource.class);

    private static final String ENTITY_NAME = "employeePhoto";

    private final EmployeePhotoService employeePhotoService;

    public EmployeePhotoResource(EmployeePhotoService employeePhotoService) {
        this.employeePhotoService = employeePhotoService;
    }

    /**
     * POST  /employee-photos : Create a new employeePhoto.
     *
     * @param employeePhoto the employeePhoto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeePhoto, or with status 400 (Bad Request) if the employeePhoto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-photos")
    @Timed
    public ResponseEntity<EmployeePhoto> createEmployeePhoto(@RequestBody EmployeePhoto employeePhoto) throws URISyntaxException {
        log.debug("REST request to save EmployeePhoto : {}", employeePhoto);
        if (employeePhoto.getId() != null) {
            throw new BadRequestAlertException("A new employeePhoto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeePhoto result = employeePhotoService.save(employeePhoto);
        return ResponseEntity.created(new URI("/api/employee-photos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-photos : Updates an existing employeePhoto.
     *
     * @param employeePhoto the employeePhoto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeePhoto,
     * or with status 400 (Bad Request) if the employeePhoto is not valid,
     * or with status 500 (Internal Server Error) if the employeePhoto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-photos")
    @Timed
    public ResponseEntity<EmployeePhoto> updateEmployeePhoto(@RequestBody EmployeePhoto employeePhoto) throws URISyntaxException {
        log.debug("REST request to update EmployeePhoto : {}", employeePhoto);
        if (employeePhoto.getId() == null) {
            return createEmployeePhoto(employeePhoto);
        }
        EmployeePhoto result = employeePhotoService.save(employeePhoto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeePhoto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-photos : get all the employeePhotos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of employeePhotos in body
     */
    @GetMapping("/employee-photos")
    @Timed
    public ResponseEntity<List<EmployeePhoto>> getAllEmployeePhotos(Pageable pageable) {
        log.debug("REST request to get a page of EmployeePhotos");
        Page<EmployeePhoto> page = employeePhotoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employee-photos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employee-photos/:id : get the "id" employeePhoto.
     *
     * @param id the id of the employeePhoto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeePhoto, or with status 404 (Not Found)
     */
    @GetMapping("/employee-photos/{id}")
    @Timed
    public ResponseEntity<EmployeePhoto> getEmployeePhoto(@PathVariable Long id) {
        log.debug("REST request to get EmployeePhoto : {}", id);
        EmployeePhoto employeePhoto = employeePhotoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeePhoto));
    }

    /**
     * DELETE  /employee-photos/:id : delete the "id" employeePhoto.
     *
     * @param id the id of the employeePhoto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-photos/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeePhoto(@PathVariable Long id) {
        log.debug("REST request to delete EmployeePhoto : {}", id);
        employeePhotoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
