package com.example.a2d_game_template.object;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_template.GameLoop;
import com.example.a2d_game_template.R;

/*
 * Enemy is a character that always moves in the direction of the player.
 * The Enemy class is an extension of a Circle, which is an extension of a GameObject
 * */

public class Enemy extends Circle {

    private static final double SPEED_UNITS_PER_SECOND = 400.0; //1 unit = 1px * RS
    private static final double MAX_SPEED = SPEED_UNITS_PER_SECOND / GameLoop.MAX_UPS;
    private final Player player;

    public Enemy(Context context, Player player, double positionX, double positionY, double radius) {
        super(context, ContextCompat.getColor(context, R.color.enemy), positionX, positionY, radius);
        this.player = player;
    }

    @Override
    public void update() { //Updates velocity of the enemy so that the velocity is in the direction of the player

        //Calculate vector from enemy to player
        double distanceToPlayerX = player.getPositionX() - positionX;
        double distanceToPlayerY = player.getPositionY() - positionY;

        //Calculate absolute distance between enemy and player
        double distanceToPlayer = GameObject.getDistanceBetweenObjects(this, player);

        //Calculate direction from enemy to player
        double directionX = 0, directionY = 0;
        if(distanceToPlayer != 0) {
            directionX = distanceToPlayerX / distanceToPlayer;
            directionY = distanceToPlayerY / distanceToPlayer;
        }

        //Set velocity in the direction to the player
        velocityX = directionX * MAX_SPEED;
        velocityY = directionY * MAX_SPEED;

        //Update the position of the enemy
        positionX += velocityX;
        positionY += velocityY;

    }
}
