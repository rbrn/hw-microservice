package com.db.hackathon.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Status.
 */
@Document(collection = "status")
public class Status implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("level")
    private String level;

    @Field("sub_level")
    private Float subLevel;

    @Field("currency_amount")
    private Long currencyAmount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLevel() {
        return level;
    }

    public Status level(String level) {
        this.level = level;
        return this;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Float getSubLevel() {
        return subLevel;
    }

    public Status subLevel(Float subLevel) {
        this.subLevel = subLevel;
        return this;
    }

    public void setSubLevel(Float subLevel) {
        this.subLevel = subLevel;
    }

    public Long getCurrencyAmount() {
        return currencyAmount;
    }

    public Status currencyAmount(Long currencyAmount) {
        this.currencyAmount = currencyAmount;
        return this;
    }

    public void setCurrencyAmount(Long currencyAmount) {
        this.currencyAmount = currencyAmount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Status)) {
            return false;
        }
        return id != null && id.equals(((Status) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Status{" +
            "id=" + getId() +
            ", level='" + getLevel() + "'" +
            ", subLevel=" + getSubLevel() +
            ", currencyAmount=" + getCurrencyAmount() +
            "}";
    }
}
