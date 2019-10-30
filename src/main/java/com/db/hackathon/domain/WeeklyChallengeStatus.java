package com.db.hackathon.domain;


import com.fasterxml.jackson.annotation.JsonGetter;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.time.Instant;

/**
 * A WeeklyChallengeStatus.
 */
@Document(collection = "weekly_challenge_status")
public class WeeklyChallengeStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("count")
    private Long count = 0L;

    @Field("challenge_id")
    private String challengeId;

    //@CreatedBy
    private String user;

    @CreatedDate
    private Instant createdDate;

    @LastModifiedBy
    private String lastModifiedUser;

    @LastModifiedDate
    private Instant lastModifiedDate;

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    @Transient
    private Challenge challenge;

    @Transient
    @JsonGetter(value = "lastModified")
    public Instant lastModified() {
        return this.lastModifiedDate;
    }


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

    public WeeklyChallengeStatus userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getChallengeId() {
        return challengeId;
    }

    public WeeklyChallengeStatus challengeId(String challengeId) {
        this.challengeId = challengeId;
        return this;
    }

    public void setChallengeId(String challengeId) {
        this.challengeId = challengeId;
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
            ", userId='" + getUserId() + "'" +
            ", count=" + getCount() +
            ", challengeId='" + getChallengeId() + "'" +
            "}";
    }
}
