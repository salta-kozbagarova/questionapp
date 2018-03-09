package com.salikhanova.questionapp;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class StatisticsActivity extends AppCompatActivity {

    PieChart pieChart;
    HashMap datas = new HashMap();
    List<PieEntry> entries = new ArrayList<>();
    AppDatabase db;
    List<QuestionAnswer> qaList;
    List<Question> questions;
    List<Answer> answers = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        db = AppDatabase.getDatabase(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);


//        qaList = new ArrayList<>();
//        questions = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = "Exception ";
                try {
                    //rePopulateDb();
                    qaList = db.questionAnswerDao().getAll();
                    questions = db.questionDao().getAll();
                    answers = db.answerDao().getAll();
                    //customAnswer.setText(msg + questions.size());
                    for(Question q : questions){
                        q.setAnswers(db.answerDao().getAllByQuestionId(q.getId()));
                    }
                    Log.d("Retrieving qa data", "sdcvsdcsdcsd " + qaList.size());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("Retrieving qa data", "running on ui thread");
                            //drawChart();
                            mAdapter = new StatisticsAdapter(StatisticsActivity.this, qaList, questions);
                            mLayoutManager = new GridLayoutManager(StatisticsActivity.this, 1);
                            mRecyclerView.setLayoutManager(mLayoutManager);
                            //recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
                            //recyclerView.setItemAnimator(new DefaultItemAnimator());
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    });
                }catch (Exception e){
                    Log.d("Retrieving data", msg + e.getMessage());
                }
            }
        }).start();



        //prepareAlbums();

        // in this example, a LineChart is initialized from xml
        //pieChart = (PieChart) findViewById(R.id.chart);

    }

    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        new Thread(new Runnable() {
            @Override
            public void run() {
            String msg = "Exception ";
            try {
                //rePopulateDb();
                qaList = db.questionAnswerDao().getAll();
                questions = db.questionDao().getAll();
                Log.d("Retrieving qa data", "sdcvsdcsdcsd " + qaList.size());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("Retrieving qa data", "running on ui thread");
                        //drawChart();
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }catch (Exception e){
                Log.d("Retrieving data", msg + e.getMessage());
            }
            }
        }).start();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
//    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
//
//        private int spanCount;
//        private int spacing;
//        private boolean includeEdge;
//
//        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
//            this.spanCount = spanCount;
//            this.spacing = spacing;
//            this.includeEdge = includeEdge;
//        }
//
//        @Override
//        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//            int position = parent.getChildAdapterPosition(view); // item position
//            int column = position % spanCount; // item column
//
//            if (includeEdge) {
//                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
//                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)
//
//                if (position < spanCount) { // top edge
//                    outRect.top = spacing;
//                }
//                outRect.bottom = spacing; // item bottom
//            } else {
//                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
//                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
//                if (position >= spanCount) {
//                    outRect.top = spacing; // item top
//                }
//            }
//        }
//    }

    /**
     * Converting dp to pixel
     */
//    private int dpToPx(int dp) {
//        Resources r = getResources();
//        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
//    }

    private void drawChart(){
        List<Integer> answers = new ArrayList<>();
        for(QuestionAnswer qa : qaList){
            if(qa.getQuestionId() == 1){
                answers.add(qa.getAnswerId());
            }
        }
        int occurrences;
        for(int i=1;i<5;i++){
            occurrences = Collections.frequency(answers, i);
            entries.add(new PieEntry(Float.valueOf(occurrences), "Ответ 1"));
        }


//        entries.add(new PieEntry(4.85f));
//        entries.add(new PieEntry(19.45f));
//        entries.add(new PieEntry(11.058f));
        String msg = String.valueOf(qaList.size());
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();

        PieDataSet pieDataSet = new PieDataSet(entries, "Вопрос 1");
        List<Integer> colors = new ArrayList<>();
        colors.add(getApplicationContext().getResources().getColor(R.color.pieColor1));
        colors.add(getApplicationContext().getResources().getColor(R.color.pieColor2));
        colors.add(getApplicationContext().getResources().getColor(R.color.pieColor3));
        colors.add(getApplicationContext().getResources().getColor(R.color.pieColor4));
        colors.add(getApplicationContext().getResources().getColor(R.color.pieColor5));
        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(15);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        //pieChart.setUsePercentValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.setCenterText("Всего: " + String.valueOf(answers.size()));
        pieChart.setCenterTextSize(12);
        //pieChart.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
        pieChart.setDrawEntryLabels(false);
        Description description = new Description();
        description.setText("Диаграмма для вопроса 1");
        pieChart.setDescription(description);
        pieChart.invalidate();
    }
}
