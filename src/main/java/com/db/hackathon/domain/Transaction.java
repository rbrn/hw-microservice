package com.db.hackathon.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Transaction.
 */
@Document(collection = "transaction")
public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Field("user_id")
    private String userId;

    @Field("cause_id")
    private String causeId;

    @Field("status_id")
    private String statusId;

    @Field("transaction_amount")
    private Long transactionAmount;

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

    public Transaction userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCauseId() {
        return causeId;
    }

    public Transaction causeId(String causeId) {
        this.causeId = causeId;
        return this;
    }

    public void setCauseId(String causeId) {
        this.causeId = causeId;
    }

    public String getStatusId() {
        return statusId;
    }

    public Transaction statusId(String statusId) {
        this.statusId = statusId;
        return this;
    }

    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    public Long getTransactionAmount() {
        return transactionAmount;
    }

    public Transaction transactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public void setTransactionAmount(Long transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Transaction)) {
            return false;
        }
        return id != null && id.equals(((Transaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", causeId='" + getCauseId() + "'" +
            ", statusId='" + getStatusId() + "'" +
            ", transactionAmount=" + getTransactionAmount() +
            "}";
    }
}
