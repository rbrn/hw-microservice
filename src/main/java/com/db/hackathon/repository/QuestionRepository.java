package com.db.hackathon.repository;

import com.db.hackathon.domain.Question;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data MongoDB repository for the Question entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {

}
