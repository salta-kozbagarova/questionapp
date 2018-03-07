package com.salikhanova.questionapp.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by User on 07.03.2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = Question.class,
        parentColumns = "id",
        childColumns = "question_id"),
        indices = @Index("question_id"))
public class QuestionCustomAnswer {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "question_id")
    private int questionId;

    @ColumnInfo(name = "answer")
    private String answer;

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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
