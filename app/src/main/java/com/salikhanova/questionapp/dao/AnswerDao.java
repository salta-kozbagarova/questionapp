package com.salikhanova.questionapp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.salikhanova.questionapp.entity.Answer;

import java.util.List;

/**
 * Created by User on 02.03.2018.
 */
@Dao
public interface AnswerDao {

    @Query("SELECT * FROM answer")
    List<Answer> getAll();

    @Query("SELECT * FROM answer WHERE id = :id")
    Answer getById(int id);

    @Query("SELECT * FROM answer WHERE question_id = :question_id")
    List<Answer> getAllByQuestionId(int question_id);

    @Insert
    void insert(Answer answer);

    @Query("DELETE FROM answer")
    void cleanTable();
}
