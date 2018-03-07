package com.salikhanova.questionapp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.salikhanova.questionapp.entity.QuestionAnswer;

import java.util.List;

/**
 * Created by User on 02.03.2018.
 */
@Dao
public interface QuestionAnswerDao {

    @Query("SELECT * FROM questionanswer")
    List<QuestionAnswer> getAll();

    @Query("SELECT * FROM questionanswer WHERE id = :id")
    QuestionAnswer getById(int id);

    @Insert
    void insertAll(QuestionAnswer... questionAnswers);

    @Query("DELETE FROM questionanswer")
    void cleanTable();
}
