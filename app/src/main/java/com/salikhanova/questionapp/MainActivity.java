package com.salikhanova.questionapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.salikhanova.questionapp.dao.AnswerDao;
import com.salikhanova.questionapp.dao.QuestionDao;
import com.salikhanova.questionapp.database.AppDatabase;
import com.salikhanova.questionapp.entity.Answer;
import com.salikhanova.questionapp.entity.Question;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button answer1, answer2, answer3, answer4;
    Button[] answersBtn;
    TextView question;
    EditText customAnswer;
    AppDatabase db;
    List<Question> questions = new ArrayList<>();
    List<Answer> answers = new ArrayList<>();
    private int questionIndex = 0;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = AppDatabase.getDatabase(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = "";
                try {
                    //rePopulateDb();
                    questions = db.questionDao().getAll();
                    answers = db.answerDao().getAll();
                    for(Question q : questions){
                        q.setAnswers(db.answerDao().getAllByQuestionId(q.getId()));
                    }
                    changeQuestion(questionIndex);
                }catch (Exception e){
                    Log.d("Retrieving data", msg + e.getMessage());
                    customAnswer.setText(msg + e.getMessage());
                }
            }
        }).start();
        question = (TextView) findViewById(R.id.question1);
        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);
        customAnswer = (EditText) findViewById(R.id.customAnswer1);
        answersBtn = new Button[]{answer1, answer2, answer3, answer4};

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
        if(questionIndex <= questions.size()) {
            question.setText(questions.get(num).getText());
            Question curQuest = questions.get(num);
            List<Answer> curAnswers = curQuest.getAnswers();
            for(int i = 0; i < curAnswers.size(); i++){
                answersBtn[i].setText(curAnswers.get(i).getText());
            }
            questionIndex++;
        }
    }

    @Override
    protected void onDestroy() {
        AppDatabase.destroyInstance();
        super.onDestroy();
    }

    private void rePopulateDb(){
        QuestionDao qd = db.questionDao();
        qd.cleanTable();
        Question q = new Question();
        q.setText("Как вы о нас узнали");
        qd.insert(q);
        q = new Question();
        q.setText("Где вы о нас узнали");
        qd.insert(q);

        AnswerDao ad = db.answerDao();
        ad.cleanTable();
        Answer a = new Answer();
        a.setText("Где то зачем то");
        a.setQuestionId(1);
        ad.insert(a);
        a = new Answer();
        a.setText("Где тsdsdsdsd то");
        a.setQuestionId(1);
        ad.insert(a);
        a = new Answer();
        a.setText("Где 258258 то");
        a.setQuestionId(1);
        ad.insert(a);
        a = new Answer();
        a.setText("102587tghзачем то");
        a.setQuestionId(1);
        ad.insert(a);
        a = new Answer();
        a.setText("Где то зачем то");
        a.setQuestionId(2);
        ad.insert(a);
        a = new Answer();
        a.setText("Где тsdsdsdsd то");
        a.setQuestionId(2);
        ad.insert(a);
        a = new Answer();
        a.setText("Где 258258 то");
        a.setQuestionId(2);
        ad.insert(a);
        a = new Answer();
        a.setText("102587tghзачем то");
        a.setQuestionId(2);
        ad.insert(a);
    }
}
