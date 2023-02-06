package com.petrus.balloonburst;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity
{
    private TextView textViewFinish, textViewPlayerScore, textViewHighScore;
    private Button buttonPlayAgain, buttonQuitGame;
    int playerScore;

    SharedPreferences sharedPreferences;

    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        textViewFinish = findViewById(R.id.textViewFinish);
        textViewPlayerScore = findViewById(R.id.textViewPlayerScore);
        textViewHighScore = findViewById(R.id.textViewHighScore);

        buttonPlayAgain = findViewById(R.id.buttonPlayAgain);
        buttonQuitGame = findViewById(R.id.buttonQuit);

        playerScore = getIntent().getIntExtra("score", 0);
        textViewPlayerScore.setText(String.format("%s%d", getString(R.string.playerScore), playerScore));

        //saves high score into Share Preferences
        sharedPreferences = this.getSharedPreferences("Score", Context.MODE_PRIVATE);
        int highScore = sharedPreferences.getInt("highScore", 0);

        if (playerScore > highScore)
        {
            sharedPreferences.edit().putInt("highScore", playerScore).apply();
            textViewHighScore.setText(String.format("%s%d", getString(R.string.highScore), playerScore));
            textViewFinish.setText(getString(R.string.newHighScore));
        }
        else
        {
            textViewHighScore.setText(String.format("%s%d", getString(R.string.highScore), highScore));
            textViewFinish.setText(getString(R.string.gameOver));
        }

        buttonPlayAgain.setOnClickListener(view ->
        {
            Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        buttonQuitGame.setOnClickListener(view ->
        {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        });

    }
}