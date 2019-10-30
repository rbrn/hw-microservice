package com.db.hackathon.web.rest;

import com.db.hackathon.domain.Challenge;
import com.db.hackathon.service.ChallengeService;
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
 * REST controller for managing {@link com.db.hackathon.domain.Challenge}.
 */
@RestController
@RequestMapping("/api")
public class ChallengeResource {

    private final Logger log = LoggerFactory.getLogger(ChallengeResource.class);

    private static final String ENTITY_NAME = "helloworldChallenge";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ChallengeService challengeService;

    public ChallengeResource(ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    /**
     * {@code POST  /challenges} : Create a new challenge.
     *
     * @param challenge the challenge to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new challenge, or with status {@code 400 (Bad Request)} if the challenge has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/challenges")
    public ResponseEntity<Challenge> createChallenge(@RequestBody Challenge challenge) throws URISyntaxException {
        log.debug("REST request to save Challenge : {}", challenge);
        if (challenge.getId() != null) {
            throw new BadRequestAlertException("A new challenge cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Challenge result = challengeService.save(challenge);
        return ResponseEntity.created(new URI("/api/challenges/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /challenges} : Updates an existing challenge.
     *
     * @param challenge the challenge to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated challenge,
     * or with status {@code 400 (Bad Request)} if the challenge is not valid,
     * or with status {@code 500 (Internal Server Error)} if the challenge couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/challenges")
    public ResponseEntity<Challenge> updateChallenge(@RequestBody Challenge challenge) throws URISyntaxException {
        log.debug("REST request to update Challenge : {}", challenge);
        if (challenge.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Challenge result = challengeService.save(challenge);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, challenge.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /challenges} : get all the challenges.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of challenges in body.
     */
    @GetMapping("/challenges")
    public ResponseEntity<List<Challenge>> getAllChallenges(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Challenges");
        Page<Challenge> page = challengeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /challenges/:id} : get the "id" challenge.
     *
     * @param id the id of the challenge to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the challenge, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/challenges/{id}")
    public ResponseEntity<Challenge> getChallenge(@PathVariable String id) {
        log.debug("REST request to get Challenge : {}", id);
        Optional<Challenge> challenge = challengeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(challenge);
    }

    /**
     * {@code DELETE  /challenges/:id} : delete the "id" challenge.
     *
     * @param id the id of the challenge to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/challenges/{id}")
    public ResponseEntity<Void> deleteChallenge(@PathVariable String id) {
        log.debug("REST request to delete Challenge : {}", id);
        challengeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
