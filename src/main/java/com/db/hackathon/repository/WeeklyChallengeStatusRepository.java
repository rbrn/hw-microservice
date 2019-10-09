package com.db.hackathon.repository;

import com.db.hackathon.domain.WeeklyChallengeStatus;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the WeeklyChallengeStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WeeklyChallengeStatusRepository extends MongoRepository<WeeklyChallengeStatus, String> {

}
