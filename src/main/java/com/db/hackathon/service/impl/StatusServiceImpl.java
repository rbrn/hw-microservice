package com.db.hackathon.service.impl;

import com.db.hackathon.service.StatusService;
import com.db.hackathon.domain.Status;
import com.db.hackathon.repository.StatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Status}.
 */
@Service
public class StatusServiceImpl implements StatusService {

    private final Logger log = LoggerFactory.getLogger(StatusServiceImpl.class);

    private final StatusRepository statusRepository;

    public StatusServiceImpl(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    /**
     * Save a status.
     *
     * @param status the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Status save(Status status) {
        log.debug("Request to save Status : {}", status);
        return statusRepository.save(status);
    }

    /**
     * Get all the statuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Status> findAll(Pageable pageable) {
        log.debug("Request to get all Statuses");
        return statusRepository.findAll(pageable);
    }


    /**
     * Get one status by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Status> findOne(String id) {
        log.debug("Request to get Status : {}", id);
        return statusRepository.findById(id);
    }

    /**
     * Delete the status by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Status : {}", id);
        statusRepository.deleteById(id);
    }

    @Override
    public Optional<Status> findByUserId(String userId) {
        return statusRepository.findByUserId(userId);
    }
}
