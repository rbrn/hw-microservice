package com.db.hackathon.web.rest;

import com.db.hackathon.domain.WeeklyChallengeStatus;
import com.db.hackathon.service.WeeklyChallengeStatusService;
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
 * REST controller for managing {@link com.db.hackathon.domain.WeeklyChallengeStatus}.
 */
@RestController
@RequestMapping("/api")
public class WeeklyChallengeStatusResource {

    private final Logger log = LoggerFactory.getLogger(WeeklyChallengeStatusResource.class);

    private static final String ENTITY_NAME = "helloworldWeeklyChallengeStatus";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final WeeklyChallengeStatusService weeklyChallengeStatusService;

    public WeeklyChallengeStatusResource(WeeklyChallengeStatusService weeklyChallengeStatusService) {
        this.weeklyChallengeStatusService = weeklyChallengeStatusService;
    }

    /**
     * {@code POST  /weekly-challenge-statuses} : Create a new weeklyChallengeStatus.
     *
     * @param weeklyChallengeStatus the weeklyChallengeStatus to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new weeklyChallengeStatus, or with status {@code 400 (Bad Request)} if the weeklyChallengeStatus has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/weekly-challenge-statuses")
    public ResponseEntity<WeeklyChallengeStatus> createWeeklyChallengeStatus(@RequestBody WeeklyChallengeStatus weeklyChallengeStatus) throws URISyntaxException {
        log.debug("REST request to save WeeklyChallengeStatus : {}", weeklyChallengeStatus);
        if (weeklyChallengeStatus.getId() != null) {
            throw new BadRequestAlertException("A new weeklyChallengeStatus cannot already have an ID", ENTITY_NAME, "idexists");
        }
        WeeklyChallengeStatus result = weeklyChallengeStatusService.save(weeklyChallengeStatus);
        return ResponseEntity.created(new URI("/api/weekly-challenge-statuses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /weekly-challenge-statuses} : Updates an existing weeklyChallengeStatus.
     *
     * @param weeklyChallengeStatus the weeklyChallengeStatus to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated weeklyChallengeStatus,
     * or with status {@code 400 (Bad Request)} if the weeklyChallengeStatus is not valid,
     * or with status {@code 500 (Internal Server Error)} if the weeklyChallengeStatus couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/weekly-challenge-statuses")
    public ResponseEntity<WeeklyChallengeStatus> updateWeeklyChallengeStatus(@RequestBody WeeklyChallengeStatus weeklyChallengeStatus) throws URISyntaxException {
        log.debug("REST request to update WeeklyChallengeStatus : {}", weeklyChallengeStatus);
        if (weeklyChallengeStatus.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        WeeklyChallengeStatus result = weeklyChallengeStatusService.save(weeklyChallengeStatus);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, weeklyChallengeStatus.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /weekly-challenge-statuses} : get all the weeklyChallengeStatuses.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of weeklyChallengeStatuses in body.
     */
    @GetMapping("/weekly-challenge-statuses")
    public ResponseEntity<List<WeeklyChallengeStatus>> getAllWeeklyChallengeStatuses(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of WeeklyChallengeStatuses");
        Page<WeeklyChallengeStatus> page = weeklyChallengeStatusService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /weekly-challenge-statuses/:id} : get the "id" weeklyChallengeStatus.
     *
     * @param id the id of the weeklyChallengeStatus to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the weeklyChallengeStatus, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/weekly-challenge-statuses/{id}")
    public ResponseEntity<WeeklyChallengeStatus> getWeeklyChallengeStatus(@PathVariable String id) {
        log.debug("REST request to get WeeklyChallengeStatus : {}", id);
        Optional<WeeklyChallengeStatus> weeklyChallengeStatus = weeklyChallengeStatusService.findOne(id);
        return ResponseUtil.wrapOrNotFound(weeklyChallengeStatus);
    }

    /**
     * {@code DELETE  /weekly-challenge-statuses/:id} : delete the "id" weeklyChallengeStatus.
     *
     * @param id the id of the weeklyChallengeStatus to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/weekly-challenge-statuses/{id}")
    public ResponseEntity<Void> deleteWeeklyChallengeStatus(@PathVariable String id) {
        log.debug("REST request to delete WeeklyChallengeStatus : {}", id);
        weeklyChallengeStatusService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
