package com.salikhanova.questionapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.salikhanova.questionapp.database.AppDatabase;
import com.salikhanova.questionapp.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button answer1, answer2, answer3, answer4;
    Button[] answers;
    TextView question;
    EditText customAnswer;
    AppDatabase db;
    private QuestionTest questions = new QuestionTest();
    List<Question> quest = new ArrayList<>();
    private int questionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "I AM A TOAST", Toast.LENGTH_LONG).show();
        db = AppDatabase.getInstance(this);
        // run the sentence in a new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                quest = db.questionDao().getAll();
                Toast.makeText(MainActivity.this, "SIZE IS ------------- SIZE: " + quest.size(), Toast.LENGTH_LONG).show();
            }
        }).start();
        question = (TextView) findViewById(R.id.question1);
        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);
        customAnswer = (EditText) findViewById(R.id.customAnswer1);
        answers = new Button[]{answer1, answer2, answer3, answer4};

        changeQuestion(questionIndex);

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeQuestion(questionIndex);
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeQuestion(questionIndex);
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeQuestion(questionIndex);
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeQuestion(questionIndex);
            }
        });
    }

    private void changeQuestion(int num){
        for(Question q : quest){
            Toast.makeText(this, q.getText(), Toast.LENGTH_LONG).show();
        }

        if(questionIndex <= questions.myQuestion.length) {
            question.setText(questions.getQuestion(num));
            //        for(String ans : questions.getChoices(num)){
            //            answers.setText(ans);
            //        }
            question.setText(questions.myQuestion[num]);
            for(int i = 0; i < questions.getChoices(num).length; i++){
                answers[i].setText(questions.getChoices(num)[i]);
            }
            questionIndex++;
        }
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }
}
