package com.example.a2d_game_template;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.a2d_game_template.object.Circle;
import com.example.a2d_game_template.object.Enemy;
import com.example.a2d_game_template.object.Player;
import com.example.a2d_game_template.object.Spell;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
* Game maneges all objects in the game and is responsible for
* updating all states and render all objects to the screen
* */

class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    private GameLoop gameLoop;
    private List<Enemy> enemyList = new ArrayList<Enemy>();
    private List<Spell> spellList = new ArrayList<Spell>();

    private int resolutionWidth;
    private int resolutionHeight;

    final double RS; //Resolution scale
    private int joystickPointerId = 0;
    private int numberOfSpellsToCast = 0;

    public Game(Context context, int resolutionWidth, int resolutionHeight) {
        super(context);

        //set resolution
        this.resolutionWidth = resolutionWidth;
        this.resolutionHeight = resolutionHeight;

        RS = resolutionHeight / 1000.0; //so that we work in a plane with height 1000 units
        //RS = resolutionWidth if the game is portrait(vertical) display mode

        //Get surface holder and add callback
        SurfaceHolder surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);

        gameLoop = new GameLoop(this, surfaceHolder);

        //Initialize game objects
        joystick = new Joystick(220, 780, 150, 75);
        player = new Player(getContext(), joystick, 500, 500, 30);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //Handle touch event actions
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_POINTER_DOWN:
                if(joystick.getIsPressed()) {
                    //Joystick was pressed before this event -> cast spell
                    numberOfSpellsToCast++;
                } else if(joystick.isPressed(((double) event.getX()) / RS, ((double) event.getY()) / RS)) {
                    //Joystick is pressed din this event -> setIsPressed(true) and store ID
                    joystickPointerId = event.getPointerId(event.getActionIndex());
                    joystick.setIsPressed(true);
                } else {
                    //Joystick was not previously pressed nor is it this event -> cast spell
                    numberOfSpellsToCast++;
                }
                return true;

            case MotionEvent.ACTION_MOVE:
                //Joystick was pressed previously and is now moved
                if(joystick.getIsPressed()) {
                    joystick.setActuator(((double) event.getX())/ RS, ((double) event.getY()) / RS);
                }
                return true;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(joystickPointerId == event.getPointerId(event.getActionIndex())) {
                    // Joystick was let go of -> setIsPressed(false) and resetActuator
                    joystick.setIsPressed(false);
                    joystick.resetActuator();
                }
                return true;
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        gameLoop.startLoop();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        drawUPS(canvas);
        drawFPS(canvas);
        drawResolution(canvas);

        player.draw(canvas, RS);
        joystick.draw(canvas, RS);

        for(Enemy enemy: enemyList) {
            enemy.draw(canvas, RS);
        }
        for(Spell spell : spellList) {
            spell.draw(canvas, RS);
        }

    }

    public void drawUPS(Canvas canvas) {
        String avarageUPS = Double.toString(gameLoop.getAverageUPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("UPS: " + avarageUPS, 100, 100, paint);
    }

    public void drawFPS(Canvas canvas) {
        String avarageFPS = Double.toString(gameLoop.getAverageFPS());
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("FPS: " + avarageFPS, 100, 200, paint);
    }

    public void drawResolution(Canvas canvas) {
        Paint paint = new Paint();
        int color = ContextCompat.getColor(getContext(), R.color.magenta);
        paint.setColor(color);
        paint.setTextSize(50);
        canvas.drawText("Resolution: " + resolutionWidth + " " + resolutionHeight + " Resolution scale:" + RS, 100, 300, paint);
    }

    public void update() {
        //Update game state
        joystick.update();
        player.update();

        //Spawn enemy if it is time to spawn new enemies
        if(Enemy.readyToSpawn()) {
            enemyList.add(new Enemy(getContext(), player));
        }

        //Update state of each enemy
        for(Enemy enemy : enemyList) {
            enemy.update();
        }

        //Update state of each spell
        while(numberOfSpellsToCast > 0) { //to prevent crashing from spawning to many spells
            spellList.add(new Spell(getContext(), player));
            numberOfSpellsToCast--;
        }

        for(Spell spell : spellList) {
            spell.update();
        }

        //Iterate through enemyList and check for collision between each enemy and the player and all spells
        Iterator<Enemy> iteratorEnemy = enemyList.iterator();
        while (iteratorEnemy.hasNext()) {
            Circle enemy = iteratorEnemy.next();
            if(Circle.isColliding(enemy, player)) {
                //Remove enemy if it collides with the player
                iteratorEnemy.remove();
                player.setHealthPoints(player.getHealthPoints() - 1);
                continue;
            }

            Iterator<Spell> iteratorSpell = spellList.iterator();
            while(iteratorSpell.hasNext()) {
                Circle spell = iteratorSpell.next();
                //remove spell if it collides with an enemy
                if(Circle.isColliding(spell, enemy)) {
                    iteratorSpell.remove();
                    iteratorEnemy.remove();
                    break;
                }

                //removes spell if it goes outside of screen visible area
                if(spell.isOutsideOfScreen()) {
                    iteratorSpell.remove();
                }
            }
        }
    }
}
