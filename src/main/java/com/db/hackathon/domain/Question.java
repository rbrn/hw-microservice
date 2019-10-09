package com.db.hackathon.domain;


import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.io.Serializable;
import java.util.Objects;

import com.db.hackathon.domain.enumeration.Category;

/**
 * A Question.
 */
@Document(collection = "question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    /**
     * The firstname attribute.
     */
    @ApiModelProperty(value = "The firstname attribute.")
    @Field("text")
    private String text;

    @Field("category")
    private Category category;

    @Field("is_for_quizz")
    private Boolean isForQuizz;

    @Field("answers")
    private String answers;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public Question text(String text) {
        this.text = text;
        return this;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Category getCategory() {
        return category;
    }

    public Question category(Category category) {
        this.category = category;
        return this;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Boolean isIsForQuizz() {
        return isForQuizz;
    }

    public Question isForQuizz(Boolean isForQuizz) {
        this.isForQuizz = isForQuizz;
        return this;
    }

    public void setIsForQuizz(Boolean isForQuizz) {
        this.isForQuizz = isForQuizz;
    }

    public String getAnswers() {
        return answers;
    }

    public Question answers(String answers) {
        this.answers = answers;
        return this;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Question)) {
            return false;
        }
        return id != null && id.equals(((Question) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Question{" +
            "id=" + getId() +
            ", text='" + getText() + "'" +
            ", category='" + getCategory() + "'" +
            ", isForQuizz='" + isIsForQuizz() + "'" +
            ", answers='" + getAnswers() + "'" +
            "}";
    }
}
