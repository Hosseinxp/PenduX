package com.learning.pendu_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.learning.pendu_x.R;

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
    TextView txt_pageNiveaux_XCoins;
    //Création de la variable static 'niveau' afin de l'utiliser dans la pageJeu
    private String niveau;
    //Theme choisi dans la page precedente
    private String theme;

    //Création de la variable static 'mot' qui contient juste les tirés avec un espace entre chaque tiré, afin de l'utiliser dans la pageJeu
    private String mot;

    private String username;
    //Création de la variable static 'nombre_essaie' qui contient le nombre d'essaies selon le niveau choisi, afin de l'utiliser dans la pageJeu
    private int nombre_essaie;
    // le nombre XCoins_recu
    private int XCoins;
    // le nombre XCoins que l'utilisateur va gagner en fonction du niveau choisi!
    private int XCoins_recompense;
    // exemple des mots pour chaque niveau choisi:
    ArrayList<String> easyMot=new ArrayList<String>(Arrays.asList("BMW","Ford"));
    ArrayList<String> easyMedium=new ArrayList<String>(Arrays.asList("Toyota","Mercedes"));
    ArrayList<String> easyBrutal=new ArrayList<String>(Arrays.asList("Renault","peugeot"));


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // supprimer ActionBar et TitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_page_niveaux);
        SetupView();
        GetInfo();
        // En clickant sur chaque niveau, nous initialisons le niveau choisi dans la variable 'niveau', un mot random de la liste correposdante pour le niveau choisie, le nombre d'essaie selon le niveau choisi et intent pour
        // aller à la page suivante après avoir clické sur chaque button.
    }

    public void SetupView(){
        btn_pageNiveau_easy=findViewById(R.id.btn_pageNiveau_easy);
        btn_pageNiveau_medium=findViewById(R.id.btn_pageNiveau_medium);
        btn_pageNiveau_brutal=findViewById(R.id.btn_pageNiveau_brutal);
        imgb_pageNiveaux_back=findViewById(R.id.imgb_pageNiveaux_back);
        txt_pageNiveaux_XCoins=findViewById(R.id.txt_pageNiveaux_XCoins);


        btn_pageNiveau_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                niveau="Easy";
                mot=easyMot.get(random.nextInt(easyMot.size()-1)).toUpperCase(Locale.ROOT);
                nombre_essaie=6;
                XCoins_recompense=30;
                Intent niveaux_jeu=new Intent(pageNiveaux.this,pageJeu.class);
                niveaux_jeu.putExtra("niveau",niveau);
                niveaux_jeu.putExtra("XCoins_recompense",XCoins_recompense);
//                niveaux_jeu.putExtra("XCoins",XCoins);
                niveaux_jeu.putExtra("nombre_essaie",nombre_essaie);
                niveaux_jeu.putExtra("mot",mot);
                niveaux_jeu.putExtra("theme",theme);
                startActivity(niveaux_jeu);
                finish();
            }
        });

        btn_pageNiveau_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                niveau="Medium";
                mot=easyMedium.get(random.nextInt(easyMedium.size() - 1)).toUpperCase(Locale.ROOT);
                nombre_essaie=4;
                XCoins_recompense=80;
                Intent niveaux_jeu=new Intent(pageNiveaux.this,pageJeu.class);
                niveaux_jeu.putExtra("niveau",niveau);
                niveaux_jeu.putExtra("XCoins_recompense",XCoins_recompense);
//                niveaux_jeu.putExtra("XCoins",XCoins);
                niveaux_jeu.putExtra("nombre_essaie",nombre_essaie);
                niveaux_jeu.putExtra("mot",mot);
                niveaux_jeu.putExtra("theme",theme);
                startActivity(niveaux_jeu);
                finish();
            }
        });

        btn_pageNiveau_brutal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                niveau="Brutal";
                mot=easyBrutal.get(random.nextInt(easyBrutal.size()- 1)).toUpperCase(Locale.ROOT);
                nombre_essaie=3;
                XCoins_recompense=170;
                Intent niveaux_jeu=new Intent(pageNiveaux.this,pageJeu.class);
                niveaux_jeu.putExtra("niveau",niveau);
                niveaux_jeu.putExtra("XCoins_recompense",XCoins_recompense);
//                niveaux_jeu.putExtra("XCoins",XCoins);
                niveaux_jeu.putExtra("nombre_essaie",nombre_essaie);
                niveaux_jeu.putExtra("mot",mot);
                niveaux_jeu.putExtra("theme",theme);
//                niveaux_jeu.putExtra("username",username);
                startActivity(niveaux_jeu);
                finish();
            }
        });

        // Création du button 'imgb_pageNiveaux_back' pour revenir à la page précedente
        imgb_pageNiveaux_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent niveau_categ=new Intent(pageNiveaux.this,pageCategorie.class);
//                niveau_categ.putExtra("XCoins",XCoins);
//                niveau_categ.putExtra("username",username);
                startActivity(niveau_categ);
                finish();
            }
        });
    }

    public void GetInfo() {
//        Intent ceteg_niveau =getIntent();
//        theme=ceteg_niveau.getStringExtra("Theme");
//        XCoins=ceteg_niveau.getIntExtra("XCoins",XCoins);
//        username=ceteg_niveau.getStringExtra("username");

        BD_actions database=new BD_actions(getApplicationContext());
        XCoins=database.getXCoins();

        if (String.valueOf(XCoins).length() < 3) {
            txt_pageNiveaux_XCoins.setTextSize(34);
            txt_pageNiveaux_XCoins.setText(XCoins+"");
        } else if (String.valueOf(XCoins).length() > 4) {
            txt_pageNiveaux_XCoins.setTextSize(30);
            txt_pageNiveaux_XCoins.setText(XCoins+"");
        } else if (String.valueOf(XCoins).length() > 5) {
            txt_pageNiveaux_XCoins.setTextSize(28);
            txt_pageNiveaux_XCoins.setText(XCoins+"");
        } else {
            txt_pageNiveaux_XCoins.setTextSize(22);
            txt_pageNiveaux_XCoins.setText(XCoins+"");
        }
    }



}