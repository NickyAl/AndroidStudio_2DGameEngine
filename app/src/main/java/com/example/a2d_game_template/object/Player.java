package com.example.a2d_game_template.object;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_template.GameLoop;
import com.example.a2d_game_template.Joystick;
import com.example.a2d_game_template.R;
import com.example.a2d_game_template.Utils;

/*
* Player is the main character of the game, which the user controls via a touch joystick.
* The player class is an extension of a Circle, which is an extension of a GameObject
* */

public class Player extends Circle {

    public static final int MAX_HEALTH_POINTS = 10;
    public static final double SPEED_UNITS_PER_SECOND = 800.0; //1 unit = 1px * RS
    private static final double MAX_SPEED = SPEED_UNITS_PER_SECOND / GameLoop.MAX_UPS;
    private double radius;
    private Paint paint;
    private Joystick joystick;
    private HealthBar healthBar;
    private int healthPoints;

    public Player(Context context, Joystick joystick, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.player), positionX, positionY, radius);

        this.joystick = joystick;
        this.healthBar = new HealthBar(context, this);
        this.healthPoints = MAX_HEALTH_POINTS;
    }

    /*public void draw(Canvas canvas, double RS) {
        canvas.drawCircle((float)(positionX * RS), (float)(positionY * RS), (float)(radius * RS), paint);
    }*/

    public void update() {
        //Updates velocity based on actuator of joystick
        velocityX = joystick.getActuatorX()*MAX_SPEED;
        velocityY = joystick.getActuatorY()*MAX_SPEED;

        //Updates position
        positionX += velocityX;
        positionY += velocityY;

        //Update direction
        if(velocityX != 0 || velocityY  != 0) {
            //Normalize velocity to get direction (unit vector of velocity)
            double distance = Utils.getDistanceBetweenPoints(0, 0, velocityX, velocityY);
            directionX = velocityX / distance;
            directionY = velocityY / distance;
        }
    }

    public void setPosition(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public void draw(Canvas canvas, final double RS) {
        super.draw(canvas, RS);
        healthBar.draw(canvas, RS);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints >= 0 ? healthPoints : 0;
    }
}
