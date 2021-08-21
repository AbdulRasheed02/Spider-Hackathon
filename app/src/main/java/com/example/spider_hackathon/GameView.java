package com.example.spider_hackathon;

import static com.example.spider_hackathon.Ball.base;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Rational;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.Random;
import java.util.logging.LogRecord;

public class GameView extends SurfaceView implements Runnable {

    private Boolean canDraw,isGameOver;
    private Thread thread;

    private Handler mHandler;
    private Handler mHandler2;
    private boolean timer = true;

    private float screenX,screenY;
    public static float screenRatioX, screenRatioY;

    private Paint paint;

    private Ball ball;
    private float ballVerticalSpeed,ballMaxHeight;

    private Wall wall;
    private int wallType;

    private BackGround backGround1,backGround2;

    private int score;
    private Random random;

    private SharedPreferences prefs;
    private  GameActivity activity;

    public GameView(GameActivity activity, float screenX, float screenY) {
        super(activity);

        this.activity=activity;

        prefs=activity.getSharedPreferences("game",Context.MODE_PRIVATE);

        mHandler=new Handler();
        mHandler2=new Handler();

        this.screenX=screenX;
        this.screenY=screenY;
        screenRatioX=2400f/screenX;
        screenRatioY=1080f/screenY;

        backGround1=new BackGround(screenX,screenY,getResources());
        backGround2=new BackGround(screenX,screenY,getResources());
        backGround2.x=screenX;

        ball=new Ball(screenY,getResources());
        ballVerticalSpeed=20*screenRatioY;
        ballMaxHeight=200*screenRatioY;

        wall=new Wall(getResources(),screenX);


        score=0;
        score();
        collision();

        paint=new Paint();
        paint.setTextSize(100);
        paint.setTextAlign(Paint.Align.CENTER);

        random=new Random();
        wallType=random.nextInt(3)+1;

        isGameOver=false;

    }

    @Override
    public void run() {

        while(canDraw){

            update();
            draw();

        }
    }

    private void update() {

        backGround1.x-=10*screenRatioX;
        backGround2.x-=10*screenRatioX;

        if(backGround1.x+backGround1.background.getWidth()<0){
            backGround1.x=screenX;
        }

        if(backGround2.x+backGround2.background.getWidth()<0){
            backGround2.x=screenX;
        }

        ballBounce();
        walls();
    }

    private void ballBounce() {
        if(ball.ballBounce) {
            if (ball.ballUpward && ball.y > ballMaxHeight) {
                ball.y -= ballVerticalSpeed;
            }
            else if (ball.ballUpward && ball.y < ballMaxHeight) {
                ball.ballUpward = false;
                ball.ballDownward = true;
            }
            else if (ball.ballDownward && ball.y < base-ball.height) {
                ball.y += ballVerticalSpeed;
            }
            else {
                ball.y = base-ball.height;
                ball.atBase = true;
                ball.ballBounce = false;
            }
        }
    }

    private void walls() {
        wall.x-=wall.wallSpeed;

        if(wall.x+wall.width<0){
            int bound = 15;
            wall.wallSpeed=(random.nextInt(bound)+15)*screenRatioX;
            wall.x=screenX;
            wallType=random.nextInt(3)+1;
        }
    }

    private void collision() {
        mHandler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(RectF.intersects(ball.getCollisionShape(),wall.getCollisionShape())){
                    isGameOver=true;
                    timer=false;
                }
                mHandler2.postDelayed(this, 10);
            }
        },1000);
    }

    private void score() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (timer) {
                    score++;
                }
                mHandler.postDelayed(this, 1000);
            }
        }, 1000);
    }

    private void draw() {

        if(getHolder().getSurface().isValid()){
            Canvas canvas=getHolder().lockCanvas();
            canvas.drawBitmap(backGround1.background,backGround1.x,backGround1.y,paint);
            canvas.drawBitmap(backGround2.background,backGround2.x,backGround2.y,paint);
            canvas.drawBitmap(wall.getWall(wallType),wall.x,wall.y,paint);
            canvas.drawBitmap(ball.getBall(),ball.x,ball.y,paint);
            canvas.drawText("Score : "+score,(screenX/2),164,paint);

            if(isGameOver){
                canDraw=false;
                getHolder().unlockCanvasAndPost(canvas);
                highScore();
                exit();
                return;
            }

            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    private void exit() {

        try {
            Thread.sleep(1500);
            activity.startActivity(new Intent(activity,MainActivity.class));
            activity.finish();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void highScore() {
        if(prefs.getInt("highscore1",0)<score){
            SharedPreferences.Editor editor=prefs.edit();
            editor.putInt("highscore2",prefs.getInt("highscore1",0));
            editor.putInt("highscore3",prefs.getInt("highscore2",0));
            editor.putInt("highscore1",score);
            editor.apply();
        }
        else if(prefs.getInt("highscore2",0)<score){
            SharedPreferences.Editor editor=prefs.edit();
            editor.putInt("highscore3",prefs.getInt("highscore2",0));
            editor.putInt("highscore2",score);
            editor.apply();
        }
        else if(prefs.getInt("highscore3",0)<score){
            SharedPreferences.Editor editor=prefs.edit();
            editor.putInt("highscore3",score);
            editor.apply();
        }
    }

    public void resume(){
        canDraw=true;
        thread=new Thread(this);
        thread.start();
    }

    public void pause(){
        canDraw=false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(ball.atBase) {
            ball.ballBounce = true;
            ball.ballUpward = true;
            ball.ballDownward = false;
            ball.atBase=false;
        }
        return super.onTouchEvent(event);
    }
}
