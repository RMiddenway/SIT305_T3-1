package com.rodnog.rogermiddenway.quizapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText playerNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}
        setContentView(R.layout.activity_main);

        playerNameEditText = findViewById(R.id.nameEditText);

        Button start = findViewById(R.id.startButton);
        start.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent quizIntent = new Intent(getApplicationContext(), QuestionActivity.class);
                quizIntent.putExtra("name", playerNameEditText.getText().toString());
                quizIntent.putExtra("questionNo", 1);
                quizIntent.putExtra("score", 0);
                startActivityForResult(quizIntent, 1);
            }
        });
    }

    // Gets player name as passed from return intent in question activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {

            if(requestCode == 1) {
                String message = data.getStringExtra("name");
                playerNameEditText.setText(message);
            }
        }
        else  {
            playerNameEditText.setText("");
        }
    }
}