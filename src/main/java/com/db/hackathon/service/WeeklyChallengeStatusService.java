package com.db.hackathon.service;

import com.db.hackathon.domain.WeeklyChallengeStatus;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link WeeklyChallengeStatus}.
 */
public interface WeeklyChallengeStatusService {

    /**
     * Save a weeklyChallengeStatus.
     *
     * @param weeklyChallengeStatus the entity to save.
     * @return the persisted entity.
     */
    WeeklyChallengeStatus save(WeeklyChallengeStatus weeklyChallengeStatus);

    /**
     * Get all the weeklyChallengeStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<WeeklyChallengeStatus> findAll(Pageable pageable);


    /**
     * Get the "id" weeklyChallengeStatus.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<WeeklyChallengeStatus> findOne(String id);

    /**
     * Delete the "id" weeklyChallengeStatus.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
