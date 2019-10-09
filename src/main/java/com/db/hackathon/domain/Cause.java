package com.db.hackathon.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Cause.
 */
@Document(collection = "cause")
public class Cause implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("description")
    private String description;

    @Field("unlock_currency_amount")
    private Long unlockCurrencyAmount;

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

    public Cause title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public Cause description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUnlockCurrencyAmount() {
        return unlockCurrencyAmount;
    }

    public Cause unlockCurrencyAmount(Long unlockCurrencyAmount) {
        this.unlockCurrencyAmount = unlockCurrencyAmount;
        return this;
    }

    public void setUnlockCurrencyAmount(Long unlockCurrencyAmount) {
        this.unlockCurrencyAmount = unlockCurrencyAmount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Cause)) {
            return false;
        }
        return id != null && id.equals(((Cause) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cause{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", description='" + getDescription() + "'" +
            ", unlockCurrencyAmount=" + getUnlockCurrencyAmount() +
            "}";
    }
}
