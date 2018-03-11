package com.salikhanova.questionapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.salikhanova.questionapp.database.AppDatabase;
import com.salikhanova.questionapp.entity.Question;
import com.salikhanova.questionapp.entity.QuestionAnswer;
import com.salikhanova.questionapp.entity.QuestionCustomAnswer;

import java.util.List;

public class CustomAnswerActivity extends AppCompatActivity {

    AppDatabase db;
    List<QuestionCustomAnswer> customAnswers;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_answer);

        db = AppDatabase.getDatabase(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = "Exception ";
                try {
                    //rePopulateDb();
                    customAnswers = db.questionCustomAnswerDao().getAll();
                    Log.d("Retrieving qa data", "sdcvsdcsdcsd " + customAnswers.size());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Retrieving qa data", "running on ui thread");
                            //drawChart();
                            mAdapter = new CustomAnswerAdapter(CustomAnswerActivity.this, customAnswers);
                            mLayoutManager = new GridLayoutManager(CustomAnswerActivity.this, 1);
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            mRecyclerView.setAdapter(mAdapter);
//                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
//                                    DividerItemDecoration.VERTICAL);
//                            mRecyclerView.addItemDecoration(dividerItemDecoration);
//                            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        }
                    });
                }catch (Exception e){
                    Log.d("Retrieving data", msg + e.getMessage());
                }
            }
        }).start();
    }
}
