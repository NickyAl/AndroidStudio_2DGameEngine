package com.example.a2d_game_template.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_template.GameLoop;
import com.example.a2d_game_template.R;

public class Performance {

    private final int FONT_SIZE = 40;
    private GameLoop gameLoop;
    private Context context;
    int resolutionWidth;
    int resolutionHeight;

    public Performance(Context context, GameLoop gameLoop, int resolutionWidth, int resolutionHeight) {
        this.context = context;
        this.gameLoop = gameLoop;
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;
    }

    public void draw(Canvas canvas, final double RS) {
        drawUPS(canvas, RS);
        drawFPS(canvas, RS);
        drawResolution(canvas, RS);
    }

    public void drawUPS(Canvas canvas, final double RS) {
        String averageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(FONT_SIZE * (float) RS);
        canvas.drawText("UPS: " + averageUPS, 100 * (float) RS, 100 * (float) RS, paint);
    }

    public void drawFPS(Canvas canvas, final double RS) {
        String averageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(FONT_SIZE * (float) RS);
        canvas.drawText("FPS: " + averageFPS, 100 * (float) RS, 150 * (float) RS, paint);
    }

    public void drawResolution(Canvas canvas, final double RS) {
        Paint paint = new Paint();
        int color = ContextCompat.getColor(context, R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(FONT_SIZE * (float) RS);
        canvas.drawText("Resolution: " + resolutionWidth + " " + resolutionHeight + " Resolution scale:" + RS, 100 * (float) RS, 200 * (float) RS, paint);
    }
}
