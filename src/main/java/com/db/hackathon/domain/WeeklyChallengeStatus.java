package com.db.hackathon.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A WeeklyChallengeStatus.
 */
@Document(collection = "weekly_challenge_status")
public class WeeklyChallengeStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("count")
    private Long count;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getCount() {
        return count;
    }

    public WeeklyChallengeStatus count(Long count) {
        this.count = count;
        return this;
    }

    public void setCount(Long count) {
        this.count = count;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WeeklyChallengeStatus)) {
            return false;
        }
        return id != null && id.equals(((WeeklyChallengeStatus) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "WeeklyChallengeStatus{" +
            "id=" + getId() +
            ", count=" + getCount() +
            "}";
    }
}
