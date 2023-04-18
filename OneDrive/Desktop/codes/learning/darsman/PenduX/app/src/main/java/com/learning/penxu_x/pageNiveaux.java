package com.learning.penxu_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

    Random random=new Random();

    private Context mContext;
    BD_actions bdActions;

    Button btn_pageNiveau_easy,btn_pageNiveau_medium,btn_pageNiveau_brutal;
    ImageButton imgb_pageNiveaux_back;

    static String niveau;


    static String mot;

    static int nombre_essaie;


    ArrayList<String> easyMedium=new ArrayList<String>(Arrays.asList("Toyota","Mercedes"));
    ArrayList<String> easyBrutal=new ArrayList<String>(Arrays.asList("Renault","peugeot"));

    public pageNiveaux(Context context) {
        mContext = context;
    }

    public pageNiveaux() {

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_niveaux);
        SetupView();
        pageNiveaux pageNiveauxObj = new pageNiveaux(this);
        mContext = this;
        bdActions = new BD_actions(mContext);


        btn_pageNiveau_easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                niveau="Easy";
                ArrayList<String>easyMot = bdActions.getVoitureEasy();
                if(easyMot.size()>140){
                    int ramdomIndex = random.nextInt(easyMot.size() -140)+140;
                    mot= easyMot.get(ramdomIndex).toUpperCase(Locale.ROOT);
                }else if (easyMot.size()>0){
                    mot=easyMot.get(random.nextInt(easyMot.size())).toUpperCase(Locale.ROOT);
                }else{
                    System.out.println("ERROR");
                }
                //mot=easyMot.get(random.nextInt(easyMot.size() -140 )).toUpperCase(Locale.ROOT);
                nombre_essaie=6;
                Intent intent=new Intent(pageNiveaux.this,pageJeu.class);
                startActivity(intent);

            }

        });

      /*  btn_pageNiveau_medium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                niveau="Medium";
                ArrayList<String>mediumMot = bdActions.getVoitureMedium();
                mot=mediumMot.get(random.nextInt(mediumMot.size() - 1)).toUpperCase(Locale.ROOT);
                nombre_essaie=4;
                Intent intent=new Intent(pageNiveaux.this,pageJeu.class);
                startActivity(intent);
            }
        });

        btn_pageNiveau_brutal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                niveau="Brutal";
                ArrayList<String>brutalMot = bdActions.getVoitureBrutal();
                mot=brutalMot.get(random.nextInt(easyBrutal.size()- 1)).toUpperCase(Locale.ROOT);
                nombre_essaie=3;
                Intent intent=new Intent(pageNiveaux.this,pageJeu.class);
                startActivity(intent);
            }
        });*/

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