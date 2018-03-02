package com.salikhanova.questionapp.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by User on 02.03.2018.
 */
@Entity(foreignKeys = {@ForeignKey(entity = Question.class,
        parentColumns = "id",
        childColumns = "question_id"),
        @ForeignKey(entity = Answer.class,
                parentColumns = "id",
                childColumns = "answer_id")
        },
        indices = {@Index("question_id"), @Index("answer_id")})
public class QuestionAnswer {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "question_id")
    private int questionId;

    @ColumnInfo(name = "answer_id")
    private int answerId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }
}
