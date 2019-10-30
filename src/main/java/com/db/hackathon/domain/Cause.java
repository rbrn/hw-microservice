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

    @Field("unlock_level")
    private Integer unlockLevel;

    @Field("target")
    private Long target;

    @Field("currency_amount")
    private Long currencyAmount;

    private Long totalUsers;

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

    public Integer getUnlockLevel() {
        return unlockLevel;
    }

    public Cause unlockLevel(Integer unlockLevel) {
        this.unlockLevel = unlockLevel;
        return this;
    }

    public void setUnlockLevel(Integer unlockLevel) {
        this.unlockLevel = unlockLevel;
    }

    public Long getTarget() {
        return target;
    }

    public Cause target(Long target) {
        this.target = target;
        return this;
    }

    public void setTarget(Long target) {
        this.target = target;
    }

    public Long getCurrencyAmount() {
        return currencyAmount;
    }

    public Cause currencyAmount(Long currencyAmount) {
        this.currencyAmount = currencyAmount;
        return this;
    }

    public void setCurrencyAmount(Long currencyAmount) {
        this.currencyAmount = currencyAmount;
    }

    public Long getTotalUsers() {
        return totalUsers;
    }

    public Cause totalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
        return this;
    }

    public void setTotalUsers(Long totalUsers) {
        this.totalUsers = totalUsers;
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
            ", unlockLevel=" + getUnlockLevel() +
            ", target=" + getTarget() +
            ", currencyAmount=" + getCurrencyAmount() +
            "}";
    }
}
