package com.db.hackathon.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Answer.
 */
@Document(collection = "answer")
public class Answer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("answer_choise_id")
    private String answerChoiseId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public Answer userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAnswerChoiseId() {
        return answerChoiseId;
    }

    public Answer answerChoiseId(String answerChoiseId) {
        this.answerChoiseId = answerChoiseId;
        return this;
    }

    public void setAnswerChoiseId(String answerChoiseId) {
        this.answerChoiseId = answerChoiseId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Answer)) {
            return false;
        }
        return id != null && id.equals(((Answer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Answer{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", answerChoiseId='" + getAnswerChoiseId() + "'" +
            "}";
    }
}
