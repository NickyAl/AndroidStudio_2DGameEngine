package com.example.a2d_game_template.gamepanel;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.example.a2d_game_template.Utils;

public class Joystick {

    private double outerCircleCenterPositionX;
    private double outerCircleCenterPositionY;
    private double innerCircleCenterPositionX;
    private double innerCircleCenterPositionY;
    private double innerCircleRadius;
    private double outerCircleRadius;
    private final Paint outerCirclePaint;
    private final Paint innerCirclePaint;
    private double joystickCenterToTouchDistance;
    private boolean isPressed;
    private double actuatorX;
    private double actuatorY;

    //Constructor
    public Joystick(int centerPositionX, int centerPositionY, int outerCircleRadius, int innerCircleRadius) {
        //Outer and inner circle make up the joystick
        outerCircleCenterPositionX = (double)centerPositionX;
        outerCircleCenterPositionY = (double)centerPositionY;
        innerCircleCenterPositionX = (double)centerPositionX;
        innerCircleCenterPositionY = (double)centerPositionY;

        //Radius
        this.outerCircleRadius = (double)outerCircleRadius;
        this.innerCircleRadius = (double)innerCircleRadius;

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
        updateInnerCirclePosition();
    }

    private void updateInnerCirclePosition() {
        innerCircleCenterPositionX = (int) (outerCircleCenterPositionX + actuatorX * outerCircleRadius);
        innerCircleCenterPositionY = (int) (outerCircleCenterPositionY + actuatorY * outerCircleRadius);
    }

    public boolean isPressed(double touchPositionX, double touchPositionY) {
        joystickCenterToTouchDistance = Utils.getDistanceBetweenPoints(
                outerCircleCenterPositionX, outerCircleCenterPositionY,
                touchPositionX, touchPositionY);

        return joystickCenterToTouchDistance < outerCircleRadius;
    }

    public void setIsPressed(boolean isPressed) {
        this.isPressed = isPressed;
    }

    public boolean getIsPressed() {
        return isPressed;
    }

    public void setActuator(double touchPositionX, double touchPositionY) {
        double deltaX = touchPositionX - outerCircleCenterPositionX;
        double deltaY = touchPositionY - outerCircleCenterPositionY;
        double deltaDistance = Utils.getDistanceBetweenPoints(
                touchPositionX, touchPositionY,
                outerCircleCenterPositionX, outerCircleCenterPositionY);

        if(deltaDistance < outerCircleRadius) {
            actuatorX = deltaX/outerCircleRadius;
            actuatorY = deltaY/outerCircleRadius;
        } else {
            actuatorX = deltaX/deltaDistance;
            actuatorY = deltaY/deltaDistance;
        }
    }

    public void resetActuator() {
        actuatorX = 0.0;
        actuatorY = 0.0;
    }

    public double getActuatorX() {
        return  actuatorX;
    }

    public double getActuatorY() {
        return  actuatorY;
    }
}
