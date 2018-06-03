package com.fmi.patokas.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.fmi.patokas.domain.IDCard;
import com.fmi.patokas.service.IDCardService;
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
 * REST controller for managing IDCard.
 */
@RestController
@RequestMapping("/api")
public class IDCardResource {

    private final Logger log = LoggerFactory.getLogger(IDCardResource.class);

    private static final String ENTITY_NAME = "iDCard";

    private final IDCardService iDCardService;

    public IDCardResource(IDCardService iDCardService) {
        this.iDCardService = iDCardService;
    }

    /**
     * POST  /id-cards : Create a new iDCard.
     *
     * @param iDCard the iDCard to create
     * @return the ResponseEntity with status 201 (Created) and with body the new iDCard, or with status 400 (Bad Request) if the iDCard has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/id-cards")
    @Timed
    public ResponseEntity<IDCard> createIDCard(@Valid @RequestBody IDCard iDCard) throws URISyntaxException {
        log.debug("REST request to save IDCard : {}", iDCard);
        if (iDCard.getId() != null) {
            throw new BadRequestAlertException("A new iDCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IDCard result = iDCardService.save(iDCard);
        return ResponseEntity.created(new URI("/api/id-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /id-cards : Updates an existing iDCard.
     *
     * @param iDCard the iDCard to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated iDCard,
     * or with status 400 (Bad Request) if the iDCard is not valid,
     * or with status 500 (Internal Server Error) if the iDCard couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/id-cards")
    @Timed
    public ResponseEntity<IDCard> updateIDCard(@Valid @RequestBody IDCard iDCard) throws URISyntaxException {
        log.debug("REST request to update IDCard : {}", iDCard);
        if (iDCard.getId() == null) {
            return createIDCard(iDCard);
        }
        IDCard result = iDCardService.save(iDCard);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, iDCard.getId().toString()))
            .body(result);
    }

    /**
     * GET  /id-cards : get all the iDCards.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of iDCards in body
     */
    @GetMapping("/id-cards")
    @Timed
    public ResponseEntity<List<IDCard>> getAllIDCards(Pageable pageable) {
        log.debug("REST request to get a page of IDCards");
        Page<IDCard> page = iDCardService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/id-cards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /id-cards/:id : get the "id" iDCard.
     *
     * @param id the id of the iDCard to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the iDCard, or with status 404 (Not Found)
     */
    @GetMapping("/id-cards/{id}")
    @Timed
    public ResponseEntity<IDCard> getIDCard(@PathVariable Long id) {
        log.debug("REST request to get IDCard : {}", id);
        IDCard iDCard = iDCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(iDCard));
    }

    /**
     * DELETE  /id-cards/:id : delete the "id" iDCard.
     *
     * @param id the id of the iDCard to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/id-cards/{id}")
    @Timed
    public ResponseEntity<Void> deleteIDCard(@PathVariable Long id) {
        log.debug("REST request to delete IDCard : {}", id);
        iDCardService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
