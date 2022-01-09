package com.example.a2d_game_template;

import android.graphics.Canvas;

/*
* GameOjbect is an abstract class which is the foundation of all world objects in the game
* */

public abstract class GameObject {
    protected double positionX;
    protected double positionY;

    protected double velocityX;
    protected double velocityY;

    public GameObject(double positionX, double positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public abstract void draw(Canvas canvas, double RS);

    public abstract void update();

}
