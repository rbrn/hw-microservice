package com.db.hackathon.service.impl;

import com.db.hackathon.service.AnswerChoiceService;
import com.db.hackathon.domain.AnswerChoice;
import com.db.hackathon.repository.AnswerChoiceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link AnswerChoice}.
 */
@Service
public class AnswerChoiceServiceImpl implements AnswerChoiceService {

    private final Logger log = LoggerFactory.getLogger(AnswerChoiceServiceImpl.class);

    private final AnswerChoiceRepository answerChoiceRepository;

    public AnswerChoiceServiceImpl(AnswerChoiceRepository answerChoiceRepository) {
        this.answerChoiceRepository = answerChoiceRepository;
    }

    /**
     * Save a answerChoice.
     *
     * @param answerChoice the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AnswerChoice save(AnswerChoice answerChoice) {
        log.debug("Request to save AnswerChoice : {}", answerChoice);
        return answerChoiceRepository.save(answerChoice);
    }

    /**
     * Get all the answerChoices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<AnswerChoice> findAll(Pageable pageable) {
        log.debug("Request to get all AnswerChoices");
        return answerChoiceRepository.findAll(pageable);
    }


    /**
     * Get one answerChoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<AnswerChoice> findOne(String id) {
        log.debug("Request to get AnswerChoice : {}", id);
        return answerChoiceRepository.findById(id);
    }

    /**
     * Delete the answerChoice by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete AnswerChoice : {}", id);
        answerChoiceRepository.deleteById(id);
    }
}
