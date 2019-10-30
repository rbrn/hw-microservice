package com.db.hackathon.repository;

import com.db.hackathon.domain.AnswerChoice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the AnswerChoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerChoiceRepository extends MongoRepository<AnswerChoice, String> {

}
