package com.salikhanova.questionapp.repository;

import android.app.Application;
import android.os.AsyncTask;

import com.salikhanova.questionapp.dao.QuestionDao;
import com.salikhanova.questionapp.database.AppDatabase;
import com.salikhanova.questionapp.entity.Question;

import java.util.List;

/**
 * Created by User on 03.03.2018.
 */

public class QuestionRepository {

    private QuestionDao mQuestDao;
    private static List<Question> questions;

    public QuestionRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mQuestDao = db.questionDao();
        new retrieveAsyncTask(mQuestDao).execute();
    }

    public List<Question> getAll() {
        return questions;
    }

    private static class retrieveAsyncTask extends AsyncTask<Void, Void, Void> {

        private QuestionDao mAsyncQuestDao;

        retrieveAsyncTask(QuestionDao dao) {
            mAsyncQuestDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            questions = mAsyncQuestDao.getAll();
            return null;
        }
    }
}
