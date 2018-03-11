package com.salikhanova.questionapp;

import android.content.Context;
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
import com.salikhanova.questionapp.entity.QuestionCustomAnswer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by no1fa on 10.03.2018.
 */

public class CustomAnswerAdapter extends RecyclerView.Adapter<CustomAnswerAdapter.MyViewHolder> {

    private Context mContext;
    private List<QuestionCustomAnswer> customAnswers;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView customAnswer;

        public MyViewHolder(View view) {
            super(view);
            customAnswer = view.findViewById(R.id.custom_answer);
        }
    }

    public CustomAnswerAdapter(Context mContext, List<QuestionCustomAnswer> customAnswers) {
        this.mContext = mContext;
        this.customAnswers = customAnswers;
    }

    @Override
    public CustomAnswerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_answer_item, parent, false);
        return new CustomAnswerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.customAnswer.setText(customAnswers.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return customAnswers.size();
    }
}
