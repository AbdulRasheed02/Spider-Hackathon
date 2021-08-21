package com.example.spider_hackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv_play,tv_gameName,tv_HighScore;
    String highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        tv_play=findViewById(R.id.tv_play);
        tv_gameName=findViewById(R.id.tv_gameName);
        tv_HighScore=findViewById(R.id.tv_highScore);

        tv_gameName.setText("BOUNCE");

        tv_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,GameActivity.class);
                startActivity(intent);
            }
        });

        SharedPreferences prefs=getSharedPreferences("game",MODE_PRIVATE);

        highscore="";

        if(prefs.getInt("highscore1",0)>0){
            highscore+=("1st : "+String.valueOf(prefs.getInt("highscore1",0))+"\n");
        }
        else{
            highscore+=("1st : "+"\n");
        }
        if(prefs.getInt("highscore2",0)>0){
            highscore+=("2nd : "+String.valueOf(prefs.getInt("highscore2",0))+"\n");
        }
        else{
            highscore+=("2nd : "+"\n");
        }
        if(prefs.getInt("highscore3",0)>0){
            highscore+=("3rd : "+String.valueOf(prefs.getInt("highscore3",0))+"\n");
        }
        else{
            highscore+=("3rd : "+"\n");
        }

        tv_HighScore.setText("Leaderboard"+"\n"+highscore);

    }

}