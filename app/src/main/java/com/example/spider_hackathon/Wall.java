package com.example.spider_hackathon;

import static com.example.spider_hackathon.Ball.base;
import static com.example.spider_hackathon.GameView.screenRatioX;
import static com.example.spider_hackathon.GameView.screenRatioY;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class Wall {

    float x=0,y,width,height,wallSpeed=15;
    Bitmap wall1,wall2,wall3;

    Wall(Resources res,float screenX){

        wall1= BitmapFactory.decodeResource(res,R.drawable.wall1);
        wall2= BitmapFactory.decodeResource(res, R.drawable.wall2);
        wall3= BitmapFactory.decodeResource(res,R.drawable.wall3);

        width=wall1.getWidth();
        height=wall1.getHeight();

        width/=3;
        height/=2;

        width*=screenRatioX;
        height*=screenRatioY;

        wall1=Bitmap.createScaledBitmap(wall1,(int)width,(int)height,false);
        wall2=Bitmap.createScaledBitmap(wall2,(int)width,(int)height,false);
        wall3=Bitmap.createScaledBitmap(wall3,(int)width,(int)height,false);

        y=base-height;
    }

    Bitmap getWall(int wallType){

        if(wallType==1){
             return wall1;

        }
        else if(wallType==2){
            return wall2;
        }
        else {
            return wall3;
        }

    }

    RectF getCollisionShape(){
        return new RectF(x,y,x+width,y+height);
    }

}
