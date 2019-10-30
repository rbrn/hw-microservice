package com.db.hackathon.service.impl;

import com.db.hackathon.service.WeeklyChallengeStatusService;
import com.db.hackathon.domain.WeeklyChallengeStatus;
import com.db.hackathon.repository.WeeklyChallengeStatusRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service Implementation for managing {@link WeeklyChallengeStatus}.
 */
@Service
public class WeeklyChallengeStatusServiceImpl implements WeeklyChallengeStatusService {

    private final Logger log = LoggerFactory.getLogger(WeeklyChallengeStatusServiceImpl.class);

    private final WeeklyChallengeStatusRepository weeklyChallengeStatusRepository;

    public WeeklyChallengeStatusServiceImpl(WeeklyChallengeStatusRepository weeklyChallengeStatusRepository) {
        this.weeklyChallengeStatusRepository = weeklyChallengeStatusRepository;
    }

    /**
     * Save a weeklyChallengeStatus.
     *
     * @param weeklyChallengeStatus the entity to save.
     * @return the persisted entity.
     */
    @Override
    public WeeklyChallengeStatus save(WeeklyChallengeStatus weeklyChallengeStatus) {
        log.debug("Request to save WeeklyChallengeStatus : {}", weeklyChallengeStatus);
        return weeklyChallengeStatusRepository.save(weeklyChallengeStatus);
    }

    /**
     * Get all the weeklyChallengeStatuses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    public Page<WeeklyChallengeStatus> findAll(Pageable pageable) {
        log.debug("Request to get all WeeklyChallengeStatuses");
        return weeklyChallengeStatusRepository.findAll(pageable);
    }


    /**
     * Get one weeklyChallengeStatus by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    public Optional<WeeklyChallengeStatus> findOne(String id) {
        log.debug("Request to get WeeklyChallengeStatus : {}", id);
        return weeklyChallengeStatusRepository.findById(id);
    }

    /**
     * Delete the weeklyChallengeStatus by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete WeeklyChallengeStatus : {}", id);
        weeklyChallengeStatusRepository.deleteById(id);
    }
}
