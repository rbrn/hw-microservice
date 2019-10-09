package com.db.hackathon.repository;

import com.db.hackathon.domain.Challenge;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Challenge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ChallengeRepository extends MongoRepository<Challenge, String> {

}
