package com.example.a2d_game_template.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_template.R;


/*
* GameOver is a panel which draws the text Game Over to the screen
* */

public class GameOver {

    private Context context;

    public GameOver(Context context) {
        this.context = context;
    }

    public void draw(Canvas canvas, final float RS) {
        String text = "Game Over";
        float x = 700;
        float y = 500;

        Paint paint = new Paint();

        int color = ContextCompat.getColor(context, R.color.gameOver);

        paint.setColor(color);
        float textSize = 150 * RS;
        paint.setTextSize(textSize);
        canvas.drawText(text, x * RS, y * RS, paint);
    }
}
