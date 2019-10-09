package com.db.hackathon.web.rest;

import com.db.hackathon.domain.AnswerChoice;
import com.db.hackathon.service.AnswerChoiceService;
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
 * REST controller for managing {@link com.db.hackathon.domain.AnswerChoice}.
 */
@RestController
@RequestMapping("/api")
public class AnswerChoiceResource {

    private final Logger log = LoggerFactory.getLogger(AnswerChoiceResource.class);

    private static final String ENTITY_NAME = "helloworldAnswerChoice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AnswerChoiceService answerChoiceService;

    public AnswerChoiceResource(AnswerChoiceService answerChoiceService) {
        this.answerChoiceService = answerChoiceService;
    }

    /**
     * {@code POST  /answer-choices} : Create a new answerChoice.
     *
     * @param answerChoice the answerChoice to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new answerChoice, or with status {@code 400 (Bad Request)} if the answerChoice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/answer-choices")
    public ResponseEntity<AnswerChoice> createAnswerChoice(@RequestBody AnswerChoice answerChoice) throws URISyntaxException {
        log.debug("REST request to save AnswerChoice : {}", answerChoice);
        if (answerChoice.getId() != null) {
            throw new BadRequestAlertException("A new answerChoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AnswerChoice result = answerChoiceService.save(answerChoice);
        return ResponseEntity.created(new URI("/api/answer-choices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /answer-choices} : Updates an existing answerChoice.
     *
     * @param answerChoice the answerChoice to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated answerChoice,
     * or with status {@code 400 (Bad Request)} if the answerChoice is not valid,
     * or with status {@code 500 (Internal Server Error)} if the answerChoice couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/answer-choices")
    public ResponseEntity<AnswerChoice> updateAnswerChoice(@RequestBody AnswerChoice answerChoice) throws URISyntaxException {
        log.debug("REST request to update AnswerChoice : {}", answerChoice);
        if (answerChoice.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AnswerChoice result = answerChoiceService.save(answerChoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, answerChoice.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /answer-choices} : get all the answerChoices.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of answerChoices in body.
     */
    @GetMapping("/answer-choices")
    public ResponseEntity<List<AnswerChoice>> getAllAnswerChoices(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of AnswerChoices");
        Page<AnswerChoice> page = answerChoiceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /answer-choices/:id} : get the "id" answerChoice.
     *
     * @param id the id of the answerChoice to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the answerChoice, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/answer-choices/{id}")
    public ResponseEntity<AnswerChoice> getAnswerChoice(@PathVariable String id) {
        log.debug("REST request to get AnswerChoice : {}", id);
        Optional<AnswerChoice> answerChoice = answerChoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(answerChoice);
    }

    /**
     * {@code DELETE  /answer-choices/:id} : delete the "id" answerChoice.
     *
     * @param id the id of the answerChoice to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/answer-choices/{id}")
    public ResponseEntity<Void> deleteAnswerChoice(@PathVariable String id) {
        log.debug("REST request to delete AnswerChoice : {}", id);
        answerChoiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
