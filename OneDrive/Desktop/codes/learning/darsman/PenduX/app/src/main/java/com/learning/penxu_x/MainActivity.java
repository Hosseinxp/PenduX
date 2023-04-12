package com.learning.penxu_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.media.MediaPlayer;

public class MainActivity extends AppCompatActivity {
    Button btn_pageMain_Jouer,btn_pageMain_Setting;
    private MediaPlayer mediaPlayer;
    private static boolean run_music;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SetupView();

//        setRun_music(true);
        if(getRun_music()) {
            mediaPlayer.start();
        }else{
            mediaPlayer.stop();
        }

        SettingButton();

    }

    // Création des buttons 'Jouer' et 'paramétrage'
    public void SetupView(){
        this.mediaPlayer = MediaPlayer.create(getApplicationContext(),R.raw.smoke);
        btn_pageMain_Jouer=findViewById(R.id.btn_pageMain_Jouer);
        btn_pageMain_Setting=findViewById(R.id.btn_pageMain_Setting);
        btn_pageMain_Jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(pageJeu.clavier[0]);
                Intent intent=new Intent(MainActivity.this,pageCategorie.class);
                startActivity(intent);
            }
        });
    }

    public void SettingButton(){
        btn_pageMain_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,pageSetting.class);
                startActivity(intent);
            }
        });
    }


    public boolean getRun_music() {
        return run_music;
    }

    public void setRun_music(boolean run_music) {
        this.run_music = run_music;
    }
}