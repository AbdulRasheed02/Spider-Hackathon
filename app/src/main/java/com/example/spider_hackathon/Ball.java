package com.example.spider_hackathon;

import static com.example.spider_hackathon.GameView.screenRatioX;
import static com.example.spider_hackathon.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class Ball {

    float x,y,width,height;
    public static float base;
    Bitmap ball;
    Boolean atBase=true,ballBounce=false,ballUpward=false,ballDownward=false;

    Ball (float screenY, Resources res){
        ball= BitmapFactory.decodeResource(res,R.drawable.ball);

        width=ball.getWidth();
        height=ball.getHeight();

        width*=screenRatioX;
        height*=screenRatioY;

        width/=15;
        height/=15;

        ball=Bitmap.createScaledBitmap(ball,(int)width,(int)height,false);

        base=(900)*screenRatioY;
        y=(900)*screenRatioY-height;
        x=150*screenRatioX;

    }

    Bitmap getBall(){
            return ball;
    }

    RectF getCollisionShape(){
        return new RectF(x,y,x+width,y+height);
    }

}
