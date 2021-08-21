package com.example.spider_hackathon;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BackGround {

    float x=0,y=0;
    Bitmap background;

    BackGround(float screenX, float screenY, Resources res){

        background= BitmapFactory.decodeResource(res,R.drawable.background);
        background=Bitmap.createScaledBitmap(background,(int)screenX,(int)screenY,false);

    }

}
