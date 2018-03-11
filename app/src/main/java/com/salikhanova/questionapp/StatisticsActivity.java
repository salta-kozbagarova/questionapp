package com.salikhanova.questionapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.salikhanova.questionapp.database.AppDatabase;
import com.salikhanova.questionapp.entity.Answer;
import com.salikhanova.questionapp.entity.Question;
import com.salikhanova.questionapp.entity.QuestionAnswer;
import com.salikhanova.questionapp.entity.QuestionCustomAnswer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    PieChart pieChart;
    List<PieEntry> entries = new ArrayList<>();
    AppDatabase db;
    List<QuestionAnswer> qaList;
    List<Question> questions;
    List<QuestionCustomAnswer> customAnswers;
    List<Answer> answers = new ArrayList<>();
    TextView cleanAll;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        db = AppDatabase.getDatabase(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        cleanAll = (TextView) findViewById(R.id.clean_all);
        cleanAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        db.questionAnswerDao().cleanTable();
                        db.questionCustomAnswerDao().cleanTable();
                        qaList = db.questionAnswerDao().getAll();
                        questions = db.questionDao().getAll();
                        answers = db.answerDao().getAll();
                        customAnswers = db.questionCustomAnswerDao().getAll();
                        List<String> customAnswersList = new ArrayList<>();
                        for(QuestionCustomAnswer qca : customAnswers){
                            customAnswersList.add(qca.getAnswer());
                        }
                        for(Question q : questions){
                            q.setAnswers(db.answerDao().getAllByQuestionId(q.getId()));
                            if(q.getId() == 1){
                                q.setCustomAnswers(customAnswersList);
                            }
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter = new StatisticsAdapter(StatisticsActivity.this, qaList, questions);
                                mRecyclerView.setAdapter(mAdapter);
                                ((StatisticsAdapter) mRecyclerView.getAdapter()).notifyDataSetChanged();
                                Toast.makeText(StatisticsActivity.this, "Все ответы удалены", Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).start();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    qaList = db.questionAnswerDao().getAll();
                    questions = db.questionDao().getAll();
                    answers = db.answerDao().getAll();
                    customAnswers = db.questionCustomAnswerDao().getAll();
                    List<String> customAnswersList = new ArrayList<>();
                    for(QuestionCustomAnswer qca : customAnswers){
                        customAnswersList.add(qca.getAnswer());
                    }
                    for(Question q : questions){
                        q.setAnswers(db.answerDao().getAllByQuestionId(q.getId()));
                        if(q.getId() == 1){
                            q.setCustomAnswers(customAnswersList);
                        }
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter = new StatisticsAdapter(StatisticsActivity.this, qaList, questions);
                            mLayoutManager = new GridLayoutManager(StatisticsActivity.this, 1);
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mRecyclerView.setAdapter(mAdapter);
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                                    DividerItemDecoration.VERTICAL);
                            mRecyclerView.addItemDecoration(dividerItemDecoration);
                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        }
                    });
                }catch (Exception e){
                    Log.d("Retrieving data", e.getMessage());
                }
            }
        }).start();
    }
}
