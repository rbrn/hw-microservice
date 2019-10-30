package com.db.hackathon.service.impl;

import com.db.hackathon.service.QuestionService;
import com.db.hackathon.domain.Question;
import com.db.hackathon.repository.QuestionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Question}.
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private final Logger log = LoggerFactory.getLogger(QuestionServiceImpl.class);

    private final QuestionRepository questionRepository;

    public QuestionServiceImpl(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    /**
     * Save a question.
     *
     * @param question the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Question save(Question question) {
        log.debug("Request to save Question : {}", question);
        return questionRepository.save(question);
    }

    /**
     * Get all the questions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Question> findAll(Pageable pageable) {
        log.debug("Request to get all Questions");
        return questionRepository.findAll(pageable);
    }


    /**
     * Get one question by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Question> findOne(String id) {
        log.debug("Request to get Question : {}", id);
        return questionRepository.findById(id);
    }

    /**
     * Delete the question by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Question : {}", id);
        questionRepository.deleteById(id);
    }
}
