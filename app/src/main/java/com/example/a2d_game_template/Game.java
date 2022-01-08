package com.example.a2d_game_template;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

/*
* Game maneges all objects in the game and is responsible for
* updating all states and render all objects to the screen
* */

class Game extends SurfaceView implements SurfaceHolder.Callback {
    private final Player player;
    private final Joystick joystick;
    private GameLoop gameLoop;

    private int resolutionWidth;
    private int resolutionHeight;

    final double RS; //Resolution scale

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
        player = new Player(getContext(), 500, 500, 30);
        joystick = new Joystick(180, 820, 100, 50);

        setFocusable(true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //Handle touch event actions

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(joystick.isPressed(((double) event.getX()) / RS, ((double) event.getY()) / RS)) {
                    joystick.setIsPressed(true);
                }
                //.setPosition(((double) event.getX())/ RS, ((double) event.getY()) / RS); // it is divided by resolution scale because when its drawn it is multiplied by RS
                return true;

            case MotionEvent.ACTION_MOVE:
                if(joystick.getIsPressed()) {
                    joystick.setActuator(((double) event.getX())/ RS, ((double) event.getY()) / RS);
                }
                //player.setPosition(((double) event.getX())/ RS, ((double) event.getY()) / RS);
                return true;
            case MotionEvent.ACTION_UP:
                joystick.setIsPressed(false);
                joystick.resetActuator();
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
        player.update(joystick);
    }
}
