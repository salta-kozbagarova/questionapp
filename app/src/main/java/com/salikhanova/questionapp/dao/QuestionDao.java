package com.salikhanova.questionapp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import com.salikhanova.questionapp.entity.Question;

import java.util.List;

/**
 * Created by User on 02.03.2018.
 */

@Dao
public interface QuestionDao {

    @Query("SELECT * FROM question")
    List<Question> getAll();

    @Query("SELECT * FROM question WHERE id = :id")
    Question getById(int id);
}
