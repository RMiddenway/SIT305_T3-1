package com.rodnog.rogermiddenway.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class EndQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_end_quiz);

        //Getting UI elements
        TextView congratulationsTextView = findViewById(R.id.congratTextView);
        TextView scoreTextView = findViewById(R.id.scoreTextView);

        Button newQuizButton = findViewById(R.id.newQuizButton);
        Button finishButton = findViewById(R.id.finishButton);

        // Getting extras from intent
        Intent intent = getIntent();
        String playerName = intent.getStringExtra("name");
        Integer score = intent.getIntExtra("score", -1);

        // Setting name and score text fields
        congratulationsTextView.setText("Congratulations " + playerName);
        scoreTextView.setText(score.toString() + "/5");

        // Set button onclicks
        newQuizButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }
}