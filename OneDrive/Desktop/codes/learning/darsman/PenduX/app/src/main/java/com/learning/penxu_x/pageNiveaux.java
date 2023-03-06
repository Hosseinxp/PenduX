package com.learning.penxu_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class pageNiveaux extends AppCompatActivity {
    //Création de l'objet Random
    Random random=new Random();
    //Création de 3 button de niveaux
    Button btn_pageNiveau_easy,btn_pageNiveau_medium,btn_pageNiveau_brutal;
    //Création de l'ImageButton
    ImageButton imgb_pageNiveaux_back;
    //Création de la variable static 'niveau' afin de l'utiliser dans la pageJeu
    static String niveau;

    //Création de la variable static 'mot' qui contient juste les tirés avec un espace entre chaque tiré, afin de l'utiliser dans la pageJeu
    static String mot;
    //Création de la variable static 'nombre_essaie' qui contient le nombre d'essaies selon le niveau choisi, afin de l'utiliser dans la pageJeu
    static int nombre_essaie;
    // exemple des mots pour chaque niveau choisi:
    ArrayList<String> easyMot=new ArrayList<String>(Arrays.asList("BMW","Ford"));
    ArrayList<String> easyMedium=new ArrayList<String>(Arrays.asList("Toyota","Mercedes"));
    ArrayList<String> easyBrutal=new ArrayList<String>(Arrays.asList("Renault","peugeot"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_niveaux);
        SetupView();
        // En clickant sur chaque niveau, nous initialisons le niveau choisi dans la variable 'niveau', un mot random de la liste correposdante pour le niveau choisie, le nombre d'essaie selon le niveau choisi et intent pour
        // aller à la page suivante après avoir clické sur chaque button.
        btn_pageNiveau_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                niveau="Easy";
                mot=easyMot.get(random.nextInt(easyMot.size() - 1)).toUpperCase(Locale.ROOT);
                nombre_essaie=6;
                Intent intent=new Intent(pageNiveaux.this,pageJeu.class);
                startActivity(intent);
            }
        });

        btn_pageNiveau_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                niveau="Medium";
                mot=easyMedium.get(random.nextInt(easyMedium.size() - 1)).toUpperCase(Locale.ROOT);
                nombre_essaie=4;
                Intent intent=new Intent(pageNiveaux.this,pageJeu.class);
                startActivity(intent);
            }
        });

        btn_pageNiveau_brutal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                niveau="Brutal";
                mot=easyBrutal.get(random.nextInt(easyBrutal.size()- 1)).toUpperCase(Locale.ROOT);
                nombre_essaie=3;
                Intent intent=new Intent(pageNiveaux.this,pageJeu.class);
                startActivity(intent);
            }
        });

        // Création du button 'imgb_pageNiveaux_back' pour revenir à la page précedente
        imgb_pageNiveaux_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(pageNiveaux.this,pageCategorie.class);
                startActivity(intent);
            }
        });
    }

    public void SetupView(){
        btn_pageNiveau_easy=findViewById(R.id.btn_pageNiveau_easy);
        btn_pageNiveau_medium=findViewById(R.id.btn_pageNiveau_medium);
        btn_pageNiveau_brutal=findViewById(R.id.btn_pageNiveau_brutal);
        imgb_pageNiveaux_back=findViewById(R.id.imgb_pageNiveaux_back);
    }



}