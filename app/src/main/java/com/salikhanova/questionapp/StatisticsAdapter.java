package com.salikhanova.questionapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.salikhanova.questionapp.entity.Question;
import com.salikhanova.questionapp.entity.QuestionAnswer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by User on 09.03.2018.
 */

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.MyViewHolder> {

    private Context mContext;
    private List<QuestionAnswer> qaList;
    private List<Question> questions;
    List<PieEntry> entries;
    List<Integer> colors = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public PieChart pieChart;
        public TextView question, answer1, answer2, answer3, answer4, answer5, link;
        public TextView[] answers;

        public MyViewHolder(View view) {
            super(view);
            pieChart = (PieChart) view.findViewById(R.id.chart);
            question = view.findViewById(R.id.question);
            answer1 = view.findViewById(R.id.answer1);
            answer2 = view.findViewById(R.id.answer2);
            answer3 = view.findViewById(R.id.answer3);
            answer4 = view.findViewById(R.id.answer4);
            answer5 = view.findViewById(R.id.answer5);
            link = view.findViewById(R.id.link);
            answers = new TextView[]{answer1, answer2, answer3, answer4, answer5};
            Log.d("ADAPTEEEERRRR", "extending ViewHolder");
        }
    }

    public StatisticsAdapter(Context mContext, List<QuestionAnswer> qaList, List<Question> questions) {
        this.mContext = mContext;
        this.qaList = qaList;
        this.questions = questions;
        colors.add(mContext.getResources().getColor(R.color.pieColor1));
        colors.add(mContext.getResources().getColor(R.color.pieColor2));
        colors.add(mContext.getResources().getColor(R.color.pieColor3));
        colors.add(mContext.getResources().getColor(R.color.pieColor4));
        colors.add(mContext.getResources().getColor(R.color.pieColor5));
        Log.d("ADAPTEEEERRRR", "constructor");
    }

    @Override
    public StatisticsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.statistics_item, parent, false);
        Log.d("ADAPTEEEERRRR", "onCreateViewHolder");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Log.d("ADAPTEEEERRRR", "on bind view holder");
        Log.d("ADAPTEEEERRRR", "position : " + position);
        List<Integer> answers = new ArrayList<>();
        Question question = null;
        for(Question q : questions){
            if(q.getId() == (position+1)){
                question = q;
            }
        }
        entries = new ArrayList<>();
        for(QuestionAnswer qa : qaList){
            if(qa.getQuestionId() == (position+1)){
                answers.add(qa.getAnswerId());
            }
        }
        Set<Integer> answersSet = new HashSet<Integer>(answers);
        int occurrences;
        for(Integer a : answersSet){
            occurrences = Collections.frequency(answers, a);
            entries.add(new PieEntry(Float.valueOf(occurrences)));
        }
        Integer colorIndexForCustom = 0;
        if(question.getId() == 1){
            entries.add(new PieEntry(Float.valueOf(question.getCustomAnswers().size())));
            colorIndexForCustom = entries.size()-1;
        }
        holder.question.setText(question.getText());
        for(int i = 0; i < question.getAnswers().size(); i++){
            holder.answers[i].setText("--" + question.getAnswers().get(i).getText());
            holder.answers[i].setTextColor(colors.get(i));
            if(question.getAnswers().size() == 4){
                holder.answers[4].setText("--Другие ответы");
                holder.answers[4].setTextColor(colors.get(colorIndexForCustom));
                holder.link.setVisibility(View.VISIBLE);
                holder.link.setMovementMethod(LinkMovementMethod.getInstance());
                holder.link.setText("Просмотреть другие ответы");
                holder.link.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
                holder.link.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, CustomAnswerActivity.class);
                        mContext.startActivity(intent);
                    }
                });
            } else{
                holder.link.setVisibility(View.INVISIBLE);
            }
        }
        PieDataSet pieDataSet = new PieDataSet(entries, question.getText());

        pieDataSet.setColors(colors);
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(15);

        PieData pieData = new PieData(pieDataSet);
        holder.pieChart.setData(pieData);
        //pieChart.setUsePercentValues(true);
        holder.pieChart.setRotationEnabled(true);
        Integer fullQuantity = answers.size();
        if(question.getId() == 1){
            fullQuantity += question.getCustomAnswers().size();
        }
        holder.pieChart.setCenterText("Всего: " + String.valueOf(fullQuantity));
        holder.pieChart.setCenterTextSize(14);
        //pieChart.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.colorPrimary));
        holder.pieChart.setDrawEntryLabels(false);
        Description description = new Description();
        description.setText("");
        holder.pieChart.setDescription(description);
        holder.pieChart.getLegend().setEnabled(false);
        holder.pieChart.notifyDataSetChanged();
        holder.pieChart.invalidate();
    }

    @Override
    public int getItemCount() {
        Log.d("ADAPTEEEERRRR", "got item count");
        return questions.size();
    }
}
