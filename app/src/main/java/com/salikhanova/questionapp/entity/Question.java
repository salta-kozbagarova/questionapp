package com.salikhanova.questionapp.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

/**
 * Created by User on 02.03.2018.
 */
@Entity
public class Question {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "text")
    private String text;

    @Ignore
    private List<Answer> answers;

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    @Ignore
    private List<String> customAnswers;

    public List<String> getCustomAnswers() {
        return customAnswers;
    }

    public void setCustomAnswers(List<String> customAnswers) {
        this.customAnswers = customAnswers;
    }

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
}
