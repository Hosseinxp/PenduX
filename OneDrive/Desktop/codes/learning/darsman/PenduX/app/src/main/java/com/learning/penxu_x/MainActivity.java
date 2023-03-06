package com.learning.penxu_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btn_pageMain_Jouer,btn_pageMain_Setting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BtnSetup();

        btn_pageMain_Jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(pageJeu.clavier[0]);
                Intent intent=new Intent(MainActivity.this,pageCategorie.class);
                startActivity(intent);
            }
        });
        btn_pageMain_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,pageSetting.class);
                startActivity(intent);
            }
        });
    }

    // Création des buttons 'Jouer' et 'paramétrage'
    public void BtnSetup(){
        btn_pageMain_Jouer=findViewById(R.id.btn_pageMain_Jouer);
        btn_pageMain_Setting=findViewById(R.id.btn_pageMain_Setting);
    }
}