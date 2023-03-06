package com.learning.penxu_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ToggleButton;

public class pageSetting extends AppCompatActivity {
    ToggleButton tglb_pageSetting_sound;
    ImageButton imgb_pageMain_back;
    View.OnClickListener on_off=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ToggleButton tglb=(ToggleButton) view;
            if(tglb.isChecked()){
                tglb.setBackgroundColor(Color.GREEN);
                tglb.setTextColor(Color.BLACK);
            }else{
                tglb.setBackgroundColor(Color.RED);
                tglb.setTextColor(Color.BLACK);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_setting);
        SetupView();

        tglb_pageSetting_sound.setOnClickListener(on_off);
        imgb_pageMain_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(pageSetting.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void SetupView(){
        tglb_pageSetting_sound=findViewById(R.id.tglb_pageSetting_sound);
        imgb_pageMain_back=findViewById(R.id.imgb_pageMain_back);
    }
}