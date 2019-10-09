package com.db.hackathon.service;

import com.db.hackathon.domain.Status;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Status}.
 */
public interface StatusService {

    /**
     * Save a status.
     *
     * @param status the entity to save.
     * @return the persisted entity.
     */
    Status save(Status status);

    /**
     * Get all the statuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Status> findAll(Pageable pageable);


    /**
     * Get the "id" status.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Status> findOne(String id);

    /**
     * Delete the "id" status.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
