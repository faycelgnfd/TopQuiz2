package com.examplefg.topquiz2.Model;

import java.util.ArrayList;

public class Question {

    private String mQuestion;
    private ArrayList<String> mListChoix;
    private int mIndexAnswer;

    public Question(String question, ArrayList<String> liste, int answer) throws Exception
    {
        if(liste.size()==0)
            throw new Exception("pas de reponses");
        else if(answer < 0 || answer > (liste.size()-1))
            throw new Exception("erreur index réponse");
        else
        {
            this.mQuestion = question;
            this.mListChoix = liste;
            this.mIndexAnswer = answer;
        }
    }

    public String getQuestion() {
        return mQuestion;
    }

    public ArrayList<String> getListChoix() {
        return mListChoix;
    }

    public int getIndexAnswer() {
        return mIndexAnswer;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

    public void setListChoix(ArrayList<String> listChoix) {
        mListChoix = listChoix;
    }

    public void setIndexAnswer(int indexAnswer) {
        mIndexAnswer = indexAnswer;
    }
}
