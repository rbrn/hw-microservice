package com.db.hackathon.service;

import com.db.hackathon.domain.Answer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Answer}.
 */
public interface AnswerService {

    /**
     * Save a answer.
     *
     * @param answer the entity to save.
     * @return the persisted entity.
     */
    Answer save(Answer answer);

    /**
     * Get all the answers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Answer> findAll(Pageable pageable);


    /**
     * Get the "id" answer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Answer> findOne(String id);

    /**
     * Delete the "id" answer.
     *
     * @param id the id of the entity.
     */
    void delete(String id);

    List<Answer> save(List<Answer> answers);
}
