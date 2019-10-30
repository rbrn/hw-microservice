package com.db.hackathon.service.impl;

import com.db.hackathon.service.CauseService;
import com.db.hackathon.domain.Cause;
import com.db.hackathon.repository.CauseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Cause}.
 */
@Service
public class CauseServiceImpl implements CauseService {

    private final Logger log = LoggerFactory.getLogger(CauseServiceImpl.class);

    private final CauseRepository causeRepository;

    public CauseServiceImpl(CauseRepository causeRepository) {
        this.causeRepository = causeRepository;
    }

    /**
     * Save a cause.
     *
     * @param cause the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Cause save(Cause cause) {
        log.debug("Request to save Cause : {}", cause);
        return causeRepository.save(cause);
    }

    /**
     * Get all the causes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<Cause> findAll(Pageable pageable) {
        log.debug("Request to get all Causes");
        return causeRepository.findAll(pageable);
    }


    /**
     * Get one cause by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<Cause> findOne(String id) {
        log.debug("Request to get Cause : {}", id);
        return causeRepository.findById(id);
    }

    /**
     * Delete the cause by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Cause : {}", id);
        causeRepository.deleteById(id);
    }
}
