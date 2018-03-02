package com.salikhanova.questionapp.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by User on 02.03.2018.
 */

@Entity(foreignKeys = @ForeignKey(entity = Question.class,
                                parentColumns = "id",
                                childColumns = "question_id"),
        indices = @Index("question_id"))
public class Answer {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "text")
    private String text;

    @ColumnInfo(name = "question_id")
    private int questionId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }
}
