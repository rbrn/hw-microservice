package com.db.hackathon.repository;

import com.db.hackathon.domain.Status;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data MongoDB repository for the Status entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StatusRepository extends MongoRepository<Status, String> {

    Optional<Status> findByUserId(String userId);
}
