package com.db.hackathon.repository;

import com.db.hackathon.domain.Answer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data MongoDB repository for the Answer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnswerRepository extends MongoRepository<Answer, String> {

    List<Answer> findAllByUserId(String userid);
}
