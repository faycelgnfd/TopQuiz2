package com.examplefg.topquiz2.Controller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.examplefg.topquiz2.Model.*;
import com.examplefg.topquiz2.R;

import java.util.ArrayList;
import java.util.Arrays;

public class GameActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mQuestion;
    private Button mAnswer1;
    private Button mAnswer2;
    private Button mAnswer3;
    private Button mAnswer4;
    private QuestionBank mBank;
    private Question mCurrentQuestion;
    private int mQuestionsLeft = 3;
    private int mScore = 0;

    public static final String BUNDLE_EXTRA_SCORE = "BUNDLE_EXTRA_SCORE";
    public static final String BUNDLE_STATE_SCORE = "BUNDLE_STATE_SCORE";
    public static final String BUNDLE_STATE_QUESTIONS = "BUNDLE_STATE_QUESTIONS";

    public QuestionBank generateQuestions()
    {
        ArrayList<Question> listeTemp = new ArrayList<Question>();
        try
        {
            listeTemp.add(new Question("What is the house number of The Simpsons?",
                        new ArrayList<String>(Arrays.asList("42","101","666","742")),3));
            listeTemp.add(new Question("Who wrote Oliver Twist?",
                    new ArrayList<String>(Arrays.asList("Victor Hugo",
                                                        "Jane Austen",
                                                        "Charle Dickens",
                                                        "Shakespear")),
                    2));
            listeTemp.add(new Question("In what year was Google launched on the web?",
                    new ArrayList<String>(Arrays.asList("1998","1986","1999","2000")),
                    0));
        }
        catch (Exception x)
        {
            x.printStackTrace();
        }

        return new QuestionBank(listeTemp);
    }

    public void displayQuestion(Question q)
    {
        mQuestion.setText(q.getQuestion());
        mAnswer1.setText(q.getListChoix().get(0));
        mAnswer2.setText(q.getListChoix().get(1));
        mAnswer3.setText(q.getListChoix().get(2));
        mAnswer4.setText(q.getListChoix().get(3));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game2);

        if(savedInstanceState != null)
        {
            mScore = savedInstanceState.getInt(BUNDLE_STATE_SCORE);
            mQuestionsLeft = savedInstanceState.getInt(BUNDLE_STATE_QUESTIONS);
        }

        mQuestion = findViewById(R.id.activity_game2_question);
        mAnswer1 = findViewById(R.id.activity_game2_answer1);
        mAnswer2 = findViewById(R.id.activity_game2_answer2);
        mAnswer3 = findViewById(R.id.activity_game2_answer3);
        mAnswer4 = findViewById(R.id.activity_game2_answer4);

        mBank = this.generateQuestions();

        //setting the tags to distinct on which button the user clicked
        mAnswer1.setTag(0);
        mAnswer2.setTag(1);
        mAnswer3.setTag(2);
        mAnswer4.setTag(3);

        //setting clock listeners
        mAnswer1.setOnClickListener(this);
        mAnswer2.setOnClickListener(this);
        mAnswer3.setOnClickListener(this);
        mAnswer4.setOnClickListener(this);

        //display first question
        this.mCurrentQuestion = mBank.getNextQuestion();
        this.displayQuestion(this.mCurrentQuestion);

    }

    @Override
    public void onClick(View v) {

        if(this.mCurrentQuestion.getIndexAnswer() == (int)v.getTag())
        {
            Toast.makeText(this,"Correct!",Toast.LENGTH_SHORT).show();
            this.mScore++;
        }
        else
        {
            Toast.makeText(this,"Oops! Wrong answer",Toast.LENGTH_SHORT).show();
        }

        this.mQuestionsLeft--;

        if(this.mQuestionsLeft > 0)
        {
            this.mCurrentQuestion = this.mBank.getNextQuestion();
            this.displayQuestion(this.mCurrentQuestion);
        }
        else
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Well done !")
                    .setMessage("Your score : "+mScore)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent scoreResponse = new Intent();
                            scoreResponse.putExtra(BUNDLE_EXTRA_SCORE,mScore);
                            setResult(RESULT_OK,scoreResponse);
                            finish();
                        }})
                    .create()
                    .show();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        System.out.println("GameActivity :: saveInstance");
        outState.putInt(BUNDLE_STATE_SCORE,mScore);
        outState.putInt(BUNDLE_STATE_QUESTIONS,mQuestionsLeft);
    }
}
