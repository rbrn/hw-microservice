package com.db.hackathon.repository;

import com.db.hackathon.domain.Cause;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Cause entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CauseRepository extends MongoRepository<Cause, String> {

}
