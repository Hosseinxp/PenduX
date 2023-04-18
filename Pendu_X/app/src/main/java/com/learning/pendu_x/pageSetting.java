package com.learning.pendu_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import com.learning.pendu_x.R;

public class pageSetting extends AppCompatActivity {
    ToggleButton tglb_pageSetting_sound;
    ImageButton imgb_pageMain_back;
//    private int XCoins;
//    private String username;
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
        // supprimer ActionBar et TitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_page_setting);
        SetupView();

        tglb_pageSetting_sound.setOnClickListener(on_off);
        imgb_pageMain_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent setting_main=new Intent(pageSetting.this,MainActivity.class);
//                setting_main.setAction("SETTING_MAIN_BACK");
//                setting_main.putExtra("username",username);
//                setting_main.putExtra("XCoins",XCoins);
                startActivity(setting_main);
            }
        });
    }

    public void SetupView(){
        tglb_pageSetting_sound=findViewById(R.id.tglb_pageSetting_sound);
        imgb_pageMain_back=findViewById(R.id.imgb_pageMain_back);
    }

//    public void GetInfo(){
//        Intent getInfo=getIntent();
//        XCoins=getInfo.getIntExtra("XCoins",XCoins);
//        username=getInfo.getStringExtra("username");
//    }
}