package com.example.a2d_game_template;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Joystick {

    private double outerCircleCenterPositionX;
    private double outerCircleCenterPositionY;
    private double innerCircleCenterPositionX;
    private double innerCircleCenterPositionY;
    private double innerCircleRadius;
    private double outerCircleRadius;
    private final Paint outerCirclePaint;
    private final Paint innerCirclePaint;

    public Joystick(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius) {
        //Outer and inner circle make up the joystick
        outerCircleCenterPositionX = centerPositionX;
        outerCircleCenterPositionY = centerPositionY;
        innerCircleCenterPositionX = centerPositionX;
        innerCircleCenterPositionY = centerPositionY;

        //Radius
        this.outerCircleRadius = outerCircleRadius;
        this.innerCircleRadius = innerCircleRadius;

        //Paint of circles
        outerCirclePaint = new Paint();
        outerCirclePaint.setColor(Color.GRAY);
        outerCirclePaint.setStrokeWidth(5);
        outerCirclePaint.setStyle(Paint.Style.STROKE);

        innerCirclePaint = new Paint();
        innerCirclePaint.setColor(Color.WHITE);
        innerCirclePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void draw(Canvas canvas, double RS) {
        //draw outer
        canvas.drawCircle(
                (int)(outerCircleCenterPositionX * RS),
                (int)(outerCircleCenterPositionY * RS),
                (int)(outerCircleRadius * RS),
                outerCirclePaint);

        //draw inner
        canvas.drawCircle(
                (int)(innerCircleCenterPositionX * RS),
                (int)(innerCircleCenterPositionY * RS),
                (int)(innerCircleRadius * RS),
                innerCirclePaint);
    }

    public void update() {
    }
}
