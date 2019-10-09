package com.db.hackathon.service;

import com.db.hackathon.domain.Challenge;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Challenge}.
 */
public interface ChallengeService {

    /**
     * Save a challenge.
     *
     * @param challenge the entity to save.
     * @return the persisted entity.
     */
    Challenge save(Challenge challenge);

    /**
     * Get all the challenges.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Challenge> findAll(Pageable pageable);


    /**
     * Get the "id" challenge.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Challenge> findOne(String id);

    /**
     * Delete the "id" challenge.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
