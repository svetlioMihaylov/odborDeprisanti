package com.fmi.patokas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fmi.patokas.domain.EmployeeNote;
import com.fmi.patokas.service.EmployeeNoteService;
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
 * REST controller for managing EmployeeNote.
 */
@RestController
@RequestMapping("/api")
public class EmployeeNoteResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeNoteResource.class);

    private static final String ENTITY_NAME = "employeeNote";

    private final EmployeeNoteService employeeNoteService;

    public EmployeeNoteResource(EmployeeNoteService employeeNoteService) {
        this.employeeNoteService = employeeNoteService;
    }

    /**
     * POST  /employee-notes : Create a new employeeNote.
     *
     * @param employeeNote the employeeNote to create
     * @return the ResponseEntity with status 201 (Created) and with body the new employeeNote, or with status 400 (Bad Request) if the employeeNote has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/employee-notes")
    @Timed
    public ResponseEntity<EmployeeNote> createEmployeeNote(@Valid @RequestBody EmployeeNote employeeNote) throws URISyntaxException {
        log.debug("REST request to save EmployeeNote : {}", employeeNote);
        if (employeeNote.getId() != null) {
            throw new BadRequestAlertException("A new employeeNote cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EmployeeNote result = employeeNoteService.save(employeeNote);
        return ResponseEntity.created(new URI("/api/employee-notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employee-notes : Updates an existing employeeNote.
     *
     * @param employeeNote the employeeNote to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated employeeNote,
     * or with status 400 (Bad Request) if the employeeNote is not valid,
     * or with status 500 (Internal Server Error) if the employeeNote couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/employee-notes")
    @Timed
    public ResponseEntity<EmployeeNote> updateEmployeeNote(@Valid @RequestBody EmployeeNote employeeNote) throws URISyntaxException {
        log.debug("REST request to update EmployeeNote : {}", employeeNote);
        if (employeeNote.getId() == null) {
            return createEmployeeNote(employeeNote);
        }
        EmployeeNote result = employeeNoteService.save(employeeNote);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, employeeNote.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employee-notes : get all the employeeNotes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of employeeNotes in body
     */
    @GetMapping("/employee-notes")
    @Timed
    public ResponseEntity<List<EmployeeNote>> getAllEmployeeNotes(Pageable pageable) {
        log.debug("REST request to get a page of EmployeeNotes");
        Page<EmployeeNote> page = employeeNoteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employee-notes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employee-notes/:id : get the "id" employeeNote.
     *
     * @param id the id of the employeeNote to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the employeeNote, or with status 404 (Not Found)
     */
    @GetMapping("/employee-notes/{id}")
    @Timed
    public ResponseEntity<EmployeeNote> getEmployeeNote(@PathVariable Long id) {
        log.debug("REST request to get EmployeeNote : {}", id);
        EmployeeNote employeeNote = employeeNoteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(employeeNote));
    }

    /**
     * DELETE  /employee-notes/:id : delete the "id" employeeNote.
     *
     * @param id the id of the employeeNote to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/employee-notes/{id}")
    @Timed
    public ResponseEntity<Void> deleteEmployeeNote(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeNote : {}", id);
        employeeNoteService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
