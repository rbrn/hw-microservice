package com.db.hackathon.service;

import com.db.hackathon.domain.Cause;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Cause}.
 */
public interface CauseService {

    /**
     * Save a cause.
     *
     * @param cause the entity to save.
     * @return the persisted entity.
     */
    Cause save(Cause cause);

    /**
     * Get all the causes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Cause> findAll(Pageable pageable);


    /**
     * Get the "id" cause.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Cause> findOne(String id);

    /**
     * Delete the "id" cause.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
