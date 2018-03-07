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
import com.salikhanova.questionapp.entity.QuestionAnswer;
import com.salikhanova.questionapp.entity.QuestionCustomAnswer;

import java.util.ArrayList;
import java.util.List;

public class QuestionActivity extends AppCompatActivity {

    Button answer1, answer2, answer3, answer4, answer5, acceptBtn;
    Button[] answersBtn;
    TextView question;
    EditText customAnswer;
    AppDatabase db;
    List<Question> questions = new ArrayList<>();
    List<Answer> answers = new ArrayList<>();
    List<QuestionAnswer> questAnswers = new ArrayList<>();
    List<QuestionCustomAnswer> questCustAnswers = new ArrayList<>();
    private int questionIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        question = (TextView) findViewById(R.id.question1);
        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);
        answer5 = (Button) findViewById(R.id.answer5);
        acceptBtn = (Button) findViewById(R.id.accept_btn);
        customAnswer = (EditText) findViewById(R.id.customAnswer1);
        answersBtn = new Button[]{answer1, answer2, answer3, answer4, answer5};

        db = AppDatabase.getDatabase(this);
        new Thread(new Runnable() {
            @Override
            public void run() {
                String msg = "Exception ";
                try {
                    //rePopulateDb();
                    questions = db.questionDao().getAll();
                    answers = db.answerDao().getAll();
                    //customAnswer.setText(msg + questions.size());
                    for(Question q : questions){
                        q.setAnswers(db.answerDao().getAllByQuestionId(q.getId()));
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            changeQuestion();
                        }
                    });
                }catch (Exception e){
                    Log.d("Retrieving data", msg + e.getMessage());
                    customAnswer.setText(msg + e.getMessage() + e.getStackTrace() + e.getCause());
                }
            }
        }).start();

        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer(view);
                changeQuestion();
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer(view);
                changeQuestion();
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer(view);
                changeQuestion();
            }
        });

        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer(view);
                changeQuestion();
            }
        });

        answer5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer(view);
                changeQuestion();
            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerCustom(view);
                changeQuestion();
            }
        });
    }

    private void answer(View view){
        QuestionAnswer qa = new QuestionAnswer();
        qa.setQuestionId(Integer.parseInt(view.getTag(R.string.quest).toString()));
        qa.setAnswerId(Integer.parseInt(view.getTag(R.string.answ).toString()));
        questAnswers.add(qa);
    }

    private void answerCustom(View view){
        QuestionCustomAnswer qa = new QuestionCustomAnswer();
        qa.setQuestionId(Integer.parseInt(view.getTag(R.string.quest).toString()));
        qa.setAnswer(customAnswer.getText().toString());
        questCustAnswers.add(qa);
    }

    private void changeQuestion(){
        if(questionIndex >= questions.size()){
            storeAnswers();
            questionIndex = 0;
        }
        if(questionIndex < questions.size()) {
            if(questionIndex == 0){
                answer5.setVisibility(View.INVISIBLE);
                customAnswer.setVisibility(View.VISIBLE);
                acceptBtn.setVisibility(View.VISIBLE);
                acceptBtn.setTag(R.string.quest,questions.get(questionIndex).getId());
            } else if(questionIndex > 0){
                answer5.setVisibility(View.VISIBLE);
                customAnswer.setVisibility(View.INVISIBLE);
                acceptBtn.setVisibility(View.INVISIBLE);
            }
            question.setText(questions.get(questionIndex).getText());
            Question curQuest = questions.get(questionIndex);
            List<Answer> curAnswers = curQuest.getAnswers();
            for(int i = 0; i < curAnswers.size(); i++){
                answersBtn[i].setText(curAnswers.get(i).getText());
                answersBtn[i].setTag(R.string.quest,questions.get(questionIndex).getId());
                answersBtn[i].setTag(R.string.answ,curAnswers.get(i).getId());
            }
            questionIndex++;
        }
    }

    private void storeAnswers(){
        Log.d("STORE ANSWERS", "dvfdfvdfvdfv");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    for(QuestionAnswer qa : questAnswers){
                        db.questionAnswerDao().insertAll(qa);
                    }
                    for(QuestionCustomAnswer qa : questCustAnswers){
                        db.questionCustomAnswerDao().insertAll(qa);
                    }
                    Log.d("INSERTED ANSWRS SUCCESS", "dvfdfvdfvdfv");
                }catch (Exception e){
                    Log.d("INSERTING ANSWERS", e.getMessage());
                }
            }
        }).start();
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
