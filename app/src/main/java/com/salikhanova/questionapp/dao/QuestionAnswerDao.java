package com.salikhanova.questionapp.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;

import com.salikhanova.questionapp.entity.QuestionAnswer;

/**
 * Created by User on 02.03.2018.
 */
@Dao
public interface QuestionAnswerDao {

    @Insert
    void insertAll(QuestionAnswer... questionAnswers);
}
