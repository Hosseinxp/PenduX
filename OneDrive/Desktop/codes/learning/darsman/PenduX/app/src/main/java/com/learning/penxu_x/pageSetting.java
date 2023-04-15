package com.learning.penxu_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import android.widget.ImageButton;

import android.widget.ToggleButton;

public class pageSetting extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    ToggleButton tglb_pageSetting_sound;
    ToggleButton Tglb1_pageSetting_music;
    ImageButton imgb_pageMain_back;

    public pageSetting() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_setting);

        SetupView();
        this.mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.smoke);


    }

    public void SetupView() {
        tglb_pageSetting_sound = findViewById(R.id.tglb_pageSetting_sound);
        imgb_pageMain_back = findViewById(R.id.Imgb_pageMain_back);
        Tglb1_pageSetting_music = findViewById(R.id.Tglb1_pageSetting_music);

        imgb_pageMain_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(pageSetting.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void playSound(View view) {

        if (Tglb1_pageSetting_music.isChecked()) {

            mediaPlayer.stop();
        } else {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();

        }
    }
}



