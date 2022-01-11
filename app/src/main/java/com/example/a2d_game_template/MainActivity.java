package com.example.a2d_game_template;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import java.time.LocalDateTime;

//
// MainActivity is the entry point of the application
//

public class MainActivity extends AppCompatActivity {

    private Game game;
    private int backPressCounter = 0;
    private int backPressLastTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("MainActivity.java", "onCreate()");

        //Get phone dimensions
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        //Set window to fullscreen (will hide status bar)
        //xml solutions makes game crash
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        //Set content view to game, so that objects in the Game class can be rendered to the screen
        game = new Game(this, width, height);
        setContentView(game);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MainActivity.java", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity.java", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity.java", "onPause()");
        
        game.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MainActivity.java", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MainActivity.java", "onDestroy()");
    }

    @Override
    public void onBackPressed() {
        //if we press the back button 3 times in under 2 seconds the app is closed
        LocalDateTime date = LocalDateTime.now();
        int currentTimeInSeconds = date.toLocalTime().toSecondOfDay();

        if(currentTimeInSeconds - backPressLastTime <= 2) {
            backPressLastTime = currentTimeInSeconds;
            backPressCounter++;

            if(backPressCounter >= 3) {
                super.onBackPressed();
            }
        } else {
            backPressLastTime = currentTimeInSeconds;
            backPressCounter = 1;
        }

        Log.d("MainActivity.java", "onBackPressed()");
    }
}