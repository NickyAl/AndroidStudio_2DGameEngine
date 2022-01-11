package com.example.a2d_game_template.gameobject;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.a2d_game_template.GameLoop;
import com.example.a2d_game_template.R;

public class Spell extends Circle {

    private static final double SPEED_UNITS_PER_SECOND = 1200.0; //1 unit = 1px * RS
    private static final double MAX_SPEED = SPEED_UNITS_PER_SECOND / GameLoop.MAX_UPS;

    public Spell(Context context, Player spellCaster) {
        super(
                context,
                ContextCompat.getColor(context, R.color.spell),
                spellCaster.positionX,
                spellCaster.positionY,
                20);

        velocityX = spellCaster.getDirectionX() * MAX_SPEED;
        velocityY = spellCaster.getDirectionY() * MAX_SPEED;
    }

    @Override
    public void update() {
        positionX += velocityX;
        positionY += velocityY;
    }
}
