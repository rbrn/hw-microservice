package com.db.hackathon.service;

import com.db.hackathon.domain.AnswerChoice;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link AnswerChoice}.
 */
public interface AnswerChoiceService {

    /**
     * Save a answerChoice.
     *
     * @param answerChoice the entity to save.
     * @return the persisted entity.
     */
    AnswerChoice save(AnswerChoice answerChoice);

    /**
     * Get all the answerChoices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnswerChoice> findAll(Pageable pageable);


    /**
     * Get the "id" answerChoice.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnswerChoice> findOne(String id);

    /**
     * Delete the "id" answerChoice.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
