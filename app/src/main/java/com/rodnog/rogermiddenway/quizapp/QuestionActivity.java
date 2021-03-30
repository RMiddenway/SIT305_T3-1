package com.rodnog.rogermiddenway.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    private Button[] answerButtons;
    private Integer currentAnswer;
    private Integer correctAnswer = 0; // TODO SET THIS IF RANDOMISED BUTTONS
    Button submitButton;
    Button nextButton;

    String playerName;
    Integer questionNumber;
    Integer score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_question);

        // Getting extras
        Intent receivedIntent = getIntent();

        questionNumber = receivedIntent.getIntExtra("questionNo", -1);
        score = receivedIntent.getIntExtra("score", -1);

        TextView welcomeTextView = findViewById(R.id.welcomeTextView);
        ProgressBar progressBar = findViewById(R.id.progressBar);
        TextView questionNumberTextView = findViewById(R.id.questionNumberTextView);

        // Getting current question number from intent - if it's question 1 display the welcome message
        // Otherwise, display the progress bar

        if(questionNumber == -1) {

        }

        if(questionNumber == 1) {
            playerName = receivedIntent.getStringExtra("name").toString();
            welcomeTextView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            welcomeTextView.setText("Welcome " + playerName + "!");
        }
        else {
            welcomeTextView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }

        // Set progress bar and X/5 TextView
        progressBar.setProgress(questionNumber);
        questionNumberTextView.setText(questionNumber.toString() + "/5");

        // Get id for current question array
        Resources res = getResources();
        TypedArray index = res.obtainTypedArray(R.array.arrayIndex);
        Integer id = index.getResourceId(questionNumber - 1, -1);

        //Get current question array
        String[] questionData = getResources().getStringArray(id);

        // Get question title and text
        TextView questionTitle = findViewById(R.id.questionTitleTextView);
        TextView questionText = findViewById(R.id.questionTextView);

        // Get question buttons
        answerButtons = new Button[]{findViewById(R.id.answer1Button), findViewById(R.id.answer2Button), findViewById(R.id.answer3Button)};

        // Set text for question title, text and answer buttons
        questionTitle.setText(questionData[0]);
        questionText.setText(questionData[1]);
        answerButtons[0].setText(questionData[2]);
        answerButtons[1].setText(questionData[3]);
        answerButtons[2].setText(questionData[4]);

        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);

    }
    public void selectAnswer(View view) {
        // Highlights current answer and stores tag associated with it
        for(int i = 0; i < 3; i++) {
            answerButtons[i].setBackgroundColor(Color.WHITE);
        }
        answerButtons[Integer.parseInt(view.getTag().toString())].setBackgroundColor(Color.YELLOW);

        currentAnswer = Integer.parseInt(view.getTag().toString());
    }

    public void submitAnswer(View view) {
        // Disable buttons
        for(int i = 0; i < 3; i++) {
            answerButtons[i].setClickable(false);
        }
        // If correct, highlight correct answer green
        if(currentAnswer == correctAnswer) {
            answerButtons[currentAnswer].setBackgroundColor(Color.GREEN);
            score = score + 1;
        }
        // If incorrect, highlight correct answer green and selected answer red
        else {
            answerButtons[currentAnswer].setBackgroundColor(Color.RED);
            answerButtons[correctAnswer].setBackgroundColor(Color.GREEN);
        }
        // Hide submit button, show next button
        submitButton.setVisibility(View.GONE);
        nextButton.setVisibility(View.VISIBLE);
    }

    public void nextQuestion(View view) {

        if(questionNumber < 5) {
            Intent nextIntent = new Intent(getApplicationContext(), QuestionActivity.class);
            nextIntent.putExtra("name", playerName);
            nextIntent.putExtra("questionNo", questionNumber + 1);
            nextIntent.putExtra("score", score);
            startActivity(nextIntent);
            finish();
        }
        else {
            // Returning player name to Main Activity
            Intent returnIntent = new Intent();
            returnIntent.putExtra("name", playerName);
            setResult(RESULT_OK, returnIntent);

            // Starting result screen activity
            Intent nextIntent = new Intent(getApplicationContext(), EndQuizActivity.class);
//            nextIntent.putExtra("name", playerName);
            nextIntent.putExtra("score", score);
            startActivity(nextIntent);
            finish();
        }

    }

}