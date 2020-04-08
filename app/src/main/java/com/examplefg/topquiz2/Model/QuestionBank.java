package com.examplefg.topquiz2.Model;

import java.util.ArrayList;
import java.util.Collections;

public class QuestionBank {

    private ArrayList<Question> mListeQuestion;
    private int mIndexOfNext=0;

    public QuestionBank(ArrayList<Question> liste)
    {
        this.mListeQuestion = liste;

        //mélanger les question
        Collections.shuffle(mListeQuestion);
    }

    public Question getNextQuestion()
    {
        //loop again
        if(mIndexOfNext == mListeQuestion.size())
            mIndexOfNext = 0;

        mIndexOfNext++;
        return mListeQuestion.get(mIndexOfNext-1);
    }

}
