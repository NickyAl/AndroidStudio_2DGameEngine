package com.example.a2d_game_template.gamepanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_template.GameDisplay;
import com.example.a2d_game_template.R;
import com.example.a2d_game_template.gameobject.Player;

/*
 * HealthBar displays the players health to the screen
 * */

public class HealthBar {

    private final Paint borderPaint;
    private final Paint healthPaint;
    private Player player;
    private float width, height, margin;
    private float healthWidth;
    private float healthHeight;

    public  HealthBar(Context context, Player player) {
        this.player = player;
        this.width = 100f;
        this.height = 20f;
        this.margin = 2f;

        this.borderPaint = new Paint();
        int borderColor = ContextCompat.getColor(context, R.color.healthBarBorder);
        this.borderPaint.setStrokeWidth(4);
        this.borderPaint.setStyle(Paint.Style.STROKE);
        this.borderPaint.setColor(borderColor);

        this.healthPaint = new Paint();
        int healthColor = ContextCompat.getColor(context, R.color.healthBarHealth);
        this.healthPaint.setColor(healthColor);
    }

    public void  draw(Canvas canvas, GameDisplay gameDisplay, final double RS) {
        float x = (float) player.getPositionX(); //float because drawRect has arguments from type float
        float y = (float) player.getPositionY();
        float distanceToPlayer = 40 * (float) RS;
        float healthPointPercentage = (float) player.getHealthPoints() / player.MAX_HEALTH_POINTS;

        //Draw health;
        float healthLeft, healthTop, healthRight, healthBottom;
        healthLeft = x - width / 2;
        healthRight = healthLeft + width * healthPointPercentage;
        healthBottom = y - distanceToPlayer;
        healthTop = healthBottom - height;

        canvas.drawRect(
                (float) (gameDisplay.gameToDisplayCoordinatesX(healthLeft) * RS),
                (float) (gameDisplay.gameToDisplayCoordinatesY(healthTop) * RS),
                (float) (gameDisplay.gameToDisplayCoordinatesX(healthRight) * RS),
                (float) (gameDisplay.gameToDisplayCoordinatesY(healthBottom) * RS),
                healthPaint);

        //Draw border
        float borderLeft = x - width / 2,
                borderRight = x + width / 2,
                borderBottom = y - distanceToPlayer,
                borderTop = borderBottom - height;

        canvas.drawRect(
                (float) (gameDisplay.gameToDisplayCoordinatesX(borderLeft) * RS),
                (float) (gameDisplay.gameToDisplayCoordinatesY(borderTop) * RS),
                (float) (gameDisplay.gameToDisplayCoordinatesX(borderRight) * RS),
                (float) (gameDisplay.gameToDisplayCoordinatesY(borderBottom) * RS),
                borderPaint);
    }
}
