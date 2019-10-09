package com.db.hackathon.web.rest;

import com.db.hackathon.domain.Cause;
import com.db.hackathon.service.CauseService;
import com.db.hackathon.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.db.hackathon.domain.Cause}.
 */
@RestController
@RequestMapping("/api")
public class CauseResource {

    private final Logger log = LoggerFactory.getLogger(CauseResource.class);

    private static final String ENTITY_NAME = "helloworldCause";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CauseService causeService;

    public CauseResource(CauseService causeService) {
        this.causeService = causeService;
    }

    /**
     * {@code POST  /causes} : Create a new cause.
     *
     * @param cause the cause to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cause, or with status {@code 400 (Bad Request)} if the cause has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/causes")
    public ResponseEntity<Cause> createCause(@RequestBody Cause cause) throws URISyntaxException {
        log.debug("REST request to save Cause : {}", cause);
        if (cause.getId() != null) {
            throw new BadRequestAlertException("A new cause cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cause result = causeService.save(cause);
        return ResponseEntity.created(new URI("/api/causes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /causes} : Updates an existing cause.
     *
     * @param cause the cause to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cause,
     * or with status {@code 400 (Bad Request)} if the cause is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cause couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/causes")
    public ResponseEntity<Cause> updateCause(@RequestBody Cause cause) throws URISyntaxException {
        log.debug("REST request to update Cause : {}", cause);
        if (cause.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cause result = causeService.save(cause);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cause.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /causes} : get all the causes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of causes in body.
     */
    @GetMapping("/causes")
    public ResponseEntity<List<Cause>> getAllCauses(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Causes");
        Page<Cause> page = causeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /causes/:id} : get the "id" cause.
     *
     * @param id the id of the cause to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cause, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/causes/{id}")
    public ResponseEntity<Cause> getCause(@PathVariable String id) {
        log.debug("REST request to get Cause : {}", id);
        Optional<Cause> cause = causeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cause);
    }

    /**
     * {@code DELETE  /causes/:id} : delete the "id" cause.
     *
     * @param id the id of the cause to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/causes/{id}")
    public ResponseEntity<Void> deleteCause(@PathVariable String id) {
        log.debug("REST request to delete Cause : {}", id);
        causeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
