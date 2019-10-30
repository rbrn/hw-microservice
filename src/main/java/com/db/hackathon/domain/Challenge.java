package com.db.hackathon.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Challenge.
 */
@Document(collection = "challenge")
public class Challenge implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("credit_amount")
    private Long creditAmount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public Challenge title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getCreditAmount() {
        return creditAmount;
    }

    public Challenge creditAmount(Long creditAmount) {
        this.creditAmount = creditAmount;
        return this;
    }

    public void setCreditAmount(Long creditAmount) {
        this.creditAmount = creditAmount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Challenge)) {
            return false;
        }
        return id != null && id.equals(((Challenge) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Challenge{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", creditAmount=" + getCreditAmount() +
            "}";
    }
}
