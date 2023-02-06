package com.petrus.balloonburst;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity
{

    private TextView textViewTime, textViewCountDown, textViewScore;
    private ImageView balloon1, balloon2, balloon3, balloon4, balloon5, balloon6, balloon7, balloon8, balloon9;
    private GridLayout gridLayout;
    int score = 0;
    double timeFactor = 1;
    boolean status = false;

    Runnable runnable;
    Handler handler;

    ImageView[] balloonArray;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTime = findViewById(R.id.textViewTime);
        textViewCountDown = findViewById(R.id.textViewCountDown);
        textViewScore = findViewById(R.id.textViewScore);

        balloon1 = findViewById(R.id.balloon1);
        balloon2 = findViewById(R.id.balloon2);
        balloon3 = findViewById(R.id.balloon3);
        balloon4 = findViewById(R.id.balloon4);
        balloon5 = findViewById(R.id.balloon5);
        balloon6 = findViewById(R.id.balloon6);
        balloon7 = findViewById(R.id.balloon7);
        balloon8 = findViewById(R.id.balloon8);
        balloon9 = findViewById(R.id.balloon9);

        gridLayout = findViewById(R.id.gridLayout);

        mediaPlayer = MediaPlayer.create(this, R.raw.pop);

        balloonArray = new ImageView[]{balloon1, balloon2, balloon3, balloon4, balloon5, balloon6, balloon7, balloon8, balloon9};

        //Timer to start game after app loads
        new CountDownTimer(5000, 1000)
        {
            @Override
            public void onTick(long l)
            {
                textViewCountDown.setText(String.valueOf(l/1000));
            }

            @Override
            public void onFinish()
            {
                balloonControl();

                new CountDownTimer(30000, 1000)
                {
                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onTick(long l)
                    {
                        textViewTime.setText(String.format("%s%d", getString(R.string.remaining), l / 1000));
                    }

                    //on game end, open results page and send score
                    @Override
                    public void onFinish()
                    {
                        Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                        intent.putExtra("score", score);
                        startActivity(intent);
                        finish();
                    }
                }.start();
            }
        }.start();
    }

    //method to increase score on balloon press and play pop sound. Also increases time factor (speed) of the game as score increases
    @SuppressLint("DefaultLocale")
    public void increaseScore(View view)
    {
        score++;
        textViewScore.setText(String.format("%s%d", getString(R.string.score), score));
        if (mediaPlayer.isPlaying())
        {
            mediaPlayer.seekTo(0);
        }
            mediaPlayer.start();

        if (view.getId() == balloon1.getId())
        {
                balloon1.setImageResource(R.drawable.pop);
        }
        if (view.getId() == balloon2.getId())
        {
            balloon2.setImageResource(R.drawable.pop);
        }
        if (view.getId() == balloon3.getId())
        {
            balloon3.setImageResource(R.drawable.pop);
        }
        if (view.getId() == balloon4.getId())
        {
            balloon4.setImageResource(R.drawable.pop);
        }
        if (view.getId() == balloon5.getId())
        {
            balloon5.setImageResource(R.drawable.pop);
        }
        if (view.getId() == balloon6.getId())
        {
            balloon6.setImageResource(R.drawable.pop);
        }
        if (view.getId() == balloon7.getId())
        {
            balloon7.setImageResource(R.drawable.pop);
        }
        if (view.getId() == balloon8.getId())
        {
            balloon8.setImageResource(R.drawable.pop);
        }
        if (view.getId() == balloon9.getId())
        {
            balloon9.setImageResource(R.drawable.pop);
        }

        timeFactor = timeFactor + .1;
    }

    //method to hide balloons and make one appear at random
    public void balloonControl()
    {
        textViewCountDown.setVisibility(View.INVISIBLE);
        textViewTime.setVisibility(View.VISIBLE);
        textViewScore.setVisibility(View.VISIBLE);

        handler = new Handler();
        runnable = new Runnable()
        {

            //run method to select one balloon from the array at random and make it visible
            //balloon appears every 2 seconds divided by time factor based on score
            @Override
            public void run()
            {
                gridLayout.setVisibility(View.VISIBLE);

                for(ImageView balloon: balloonArray)
                {
                    balloon.setVisibility(View.INVISIBLE);
                    balloon.setImageResource(R.drawable.balloon);
                }

                Random random = new Random();
                int i = random.nextInt(balloonArray.length);
                balloonArray[i].setVisibility(View.VISIBLE);

                handler.postDelayed(runnable, 2000/(long)timeFactor);
            }
        };

        handler.post(runnable);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //volume menu item to mute/unmute sound effects
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        if (item.getItemId() == R.id.volume)
        {
            if (!status)
            {
                mediaPlayer.setVolume(0, 0);
                item.setIcon(R.drawable.ic_baseline_volume_off_24);

                status = true;
            }
            else
            {
                mediaPlayer.setVolume(1, 1);
                item.setIcon(R.drawable.ic_baseline_volume_up_24);

                status = false;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}