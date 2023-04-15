package com.learning.penxu_x;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class pageCategorie extends AppCompatActivity {
    ImageButton imgb_pageCateg_voiture,imgb_pageCateg_back,imgb_pageCateg_anime,imgb_pageCateg_film,imgb_pageCateg_sport ;

    ConstraintLayout layout_pageCateg;
    String Theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_categorie);
        SetupView();
        imgb_pageCateg_voiture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Theme="Voiture";
                Intent intent=new Intent(pageCategorie.this,pageNiveaux.class);
                startActivity(intent);
            }
        });

        imgb_pageCateg_anime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Theme = "Anime";
                Intent intent=new Intent(pageCategorie.this,pageNiveaux.class);
                startActivity(intent);
            }
        });

        imgb_pageCateg_film.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Theme = "Film";
                Intent intent=new Intent(pageCategorie.this,pageNiveaux.class);
                startActivity(intent);
            }
        });

        imgb_pageCateg_sport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Theme = "Sport";
                Intent intent=new Intent(pageCategorie.this,pageNiveaux.class);
                startActivity(intent);
            }
        });

        imgb_pageCateg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(pageCategorie.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void SetupView(){
        imgb_pageCateg_anime = findViewById(R.id.imgb_pageCateg_anime);
        imgb_pageCateg_voiture =findViewById(R.id.imgb_pageCateg_voiture);
        imgb_pageCateg_film = findViewById(R.id.imgb_pageCateg_film);
        imgb_pageCateg_sport = findViewById(R.id.imgb_pageCateg_sport);
        imgb_pageCateg_back=findViewById(R.id.imgb_pageCateg_back);
    }
}