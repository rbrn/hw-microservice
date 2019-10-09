package com.db.hackathon.web.rest;

import com.db.hackathon.domain.Question;
import com.db.hackathon.service.QuestionService;
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
 * REST controller for managing {@link com.db.hackathon.domain.Question}.
 */
@RestController
@RequestMapping("/api")
public class QuestionResource {

    private final Logger log = LoggerFactory.getLogger(QuestionResource.class);

    private static final String ENTITY_NAME = "helloworldQuestion";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QuestionService questionService;

    public QuestionResource(QuestionService questionService) {
        this.questionService = questionService;
    }

    /**
     * {@code POST  /questions} : Create a new question.
     *
     * @param question the question to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new question, or with status {@code 400 (Bad Request)} if the question has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/questions")
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) throws URISyntaxException {
        log.debug("REST request to save Question : {}", question);
        if (question.getId() != null) {
            throw new BadRequestAlertException("A new question cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Question result = questionService.save(question);
        return ResponseEntity.created(new URI("/api/questions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /questions} : Updates an existing question.
     *
     * @param question the question to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated question,
     * or with status {@code 400 (Bad Request)} if the question is not valid,
     * or with status {@code 500 (Internal Server Error)} if the question couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/questions")
    public ResponseEntity<Question> updateQuestion(@RequestBody Question question) throws URISyntaxException {
        log.debug("REST request to update Question : {}", question);
        if (question.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Question result = questionService.save(question);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, question.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /questions} : get all the questions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of questions in body.
     */
    @GetMapping("/questions")
    public ResponseEntity<List<Question>> getAllQuestions(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Questions");
        Page<Question> page = questionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /questions/:id} : get the "id" question.
     *
     * @param id the id of the question to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the question, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/questions/{id}")
    public ResponseEntity<Question> getQuestion(@PathVariable String id) {
        log.debug("REST request to get Question : {}", id);
        Optional<Question> question = questionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(question);
    }

    /**
     * {@code DELETE  /questions/:id} : delete the "id" question.
     *
     * @param id the id of the question to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String id) {
        log.debug("REST request to delete Question : {}", id);
        questionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
