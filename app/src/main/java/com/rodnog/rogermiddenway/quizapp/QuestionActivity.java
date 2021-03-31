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
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    private Button[] answerButtons;
    private Integer currentAnswer;
    private Integer correctAnswer = 0;

    TextView welcomeTextView;
    ProgressBar progressBar;
    TextView questionNumberTextView;

    private TextView questionTitle;
    private TextView questionText;

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

        welcomeTextView = findViewById(R.id.welcomeTextView);
        progressBar = findViewById(R.id.progressBar);
        questionNumberTextView = findViewById(R.id.questionNumberTextView);

        // Getting current question number from intent - if it's question 1 display the welcome message
        // Otherwise, display the progress bar

        if(questionNumber == -1) {

        }

        else if(questionNumber == 1) {
            playerName = receivedIntent.getStringExtra("name");
            welcomeTextView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
            welcomeTextView.setText("Welcome " + playerName + "!");

            // On first QuestionActivity, return player name to MainActivity
//            Intent returnIntent = new Intent();
//            returnIntent.putExtra("name", playerName);
//            setResult(RESULT_OK, returnIntent);
        }
        else {

        }


        // Get question title and text
        questionTitle = findViewById(R.id.questionTitleTextView);
        questionText = findViewById(R.id.questionTextView);

        // Get question buttons
        answerButtons = new Button[]{findViewById(R.id.answer1Button), findViewById(R.id.answer2Button), findViewById(R.id.answer3Button)};

        // Get navigation buuttons
        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);

        // Call function to initialise question based on current question number
        initialiseQuestion();

        submitButton = findViewById(R.id.submitButton);
        nextButton = findViewById(R.id.nextButton);

    }
    private void initialiseQuestion() {

        // Get id for current question array
        Resources res = getResources();
        TypedArray index = res.obtainTypedArray(R.array.arrayIndex);
        Integer id = index.getResourceId(questionNumber - 1, -1);

        //Get current question array | 0: Title, 1: Question, 2: Correct Answer, 3: Wrong Answer, 4: Wrong Answer
        //TODO - randomise order so correct answer isn't always first
        String[] questionData = getResources().getStringArray(id);

        // Set text for question title, text and answer buttons
        questionTitle.setText(questionData[0]);
        questionText.setText(questionData[1]);

        for(int i = 0; i < 3; i++) {
            answerButtons[i].setBackgroundColor(Color.WHITE);
            answerButtons[i].setText(questionData[i + 2]);
            answerButtons[i].setClickable(true);
        }
        progressBar.setProgress(questionNumber);
        questionNumberTextView.setText(questionNumber.toString() + "/5");

        // Hide next button and show submit button
        submitButton.setVisibility(View.VISIBLE);
        nextButton.setVisibility(View.GONE);

        // Hide welcome message after first question
        if(questionNumber > 1) {
            welcomeTextView.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
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
        if(currentAnswer== null) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_LONG).show();
        }
        else {
            // Disable buttons
            for (int i = 0; i < 3; i++) {
                answerButtons[i].setClickable(false);
            }
            // If correct, highlight correct answer green
            if (currentAnswer == correctAnswer) {
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
        questionNumber = questionNumber + 1;
    }

    public void nextQuestion(View view) {
        //TODO - make next question load without restarting activity

        if(questionNumber <= 5) {
            initialiseQuestion();

        }
        else {
            // Return player name to MainActivity
            Intent returnIntent = new Intent();
            returnIntent.putExtra("name", playerName);
            setResult(RESULT_OK, returnIntent);


            // Starting result screen activity
            Intent nextIntent = new Intent(getApplicationContext(), EndQuizActivity.class);
            nextIntent.putExtra("name", playerName);
            nextIntent.putExtra("score", score);
            startActivity(nextIntent);
            finish();
        }

    }

}