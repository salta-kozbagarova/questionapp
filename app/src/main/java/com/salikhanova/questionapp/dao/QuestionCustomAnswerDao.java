package com.salikhanova.questionapp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.salikhanova.questionapp.entity.QuestionCustomAnswer;

import java.util.List;

/**
 * Created by User on 07.03.2018.
 */
@Dao
public interface QuestionCustomAnswerDao {

    @Query("SELECT * FROM questioncustomanswer")
    List<QuestionCustomAnswer> getAll();

    @Query("SELECT * FROM questioncustomanswer WHERE id = :id")
    QuestionCustomAnswer getById(int id);

    @Insert
    void insertAll(QuestionCustomAnswer... questionCustomAnswers);

    @Query("DELETE FROM questioncustomanswer")
    void cleanTable();
}
