package com.salikhanova.questionapp;

/**
 * Created by User on 02.03.2018.
 */

public class QuestionTest {

    public String myQuestion[] = {
            "Как вы о нас узнали",
            "Как часто пользуетесь нашими услугами",
            "Bla bla this some question for use looong text",
            "QuestionTest question fishwords fishtext"
    };

    public String myChoices[][] = {
            {"Ответ 1","Ответ ответ","бла бла","пчхапиапи"},
            {"Гдето в","Паралала","ваипк","ваиапи"},
            {"мвыявм", "ыяввыфыв", "ваик5е", "чпиапаиук45"},
            {"явмыямы","вмяывмяы","мяывмяы","ывмывмыывм"}
    };

    public String getQuestion(int id){
        return myQuestion[id];
    }

    public String[] getChoices(int id){
        return myChoices[id];
    }
}
