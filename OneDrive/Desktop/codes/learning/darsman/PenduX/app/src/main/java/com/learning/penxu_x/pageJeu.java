package com.learning.penxu_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.learning.penxu_x.pageNiveaux.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;



public class pageJeu extends AppCompatActivity {

    static Button[] clavier=new Button[26];

    // création d'un dictionnaire pour avoir le button sur le clavier et la définition de sa valeur.
    HashMap<Button,Character> lettres_btn=new HashMap<Button, Character>();
    TextView txt_pageJeu_mot,txt_pageJeu_essaie;
    ImageButton imgb_pageJeu_back;

    ArrayList<Character> liste_lettres_du_mot=new ArrayList<Character>();
                                                                                                //    Character[] tires_lettres_du_mot=new Character[pageNiveaux.mot.length()];

    // initlialisation du résultat de la méthode " Mot_a_deviner_tires " dans la variable mot_a_afficher... comme ca, chaque fois qu'on appuie sur un button, c'est plus facile de changer des lettre du mot à deviner.
    String mot_a_afficher=Mot_a_deviner_tires(pageNiveaux.mot);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String mot;
        setContentView(R.layout.activity_page_jeu);
        SetupView();
        Button_lettre();


        txt_pageJeu_essaie.setText("Il vous reste "+pageNiveaux.nombre_essaie+" essaies!");

        if(pageNiveaux.mot.length() <4) {
            txt_pageJeu_mot.setTextSize(60);
            txt_pageJeu_mot.setText(mot_a_afficher);
        }else if(pageNiveaux.mot.length() >4 && pageNiveaux.mot.length()<6){
            txt_pageJeu_mot.setTextSize(55);
            txt_pageJeu_mot.setText(mot_a_afficher);
        }else{
            txt_pageJeu_mot.setTextSize(45);
            txt_pageJeu_mot.setText(mot_a_afficher);
        }



        imgb_pageJeu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(pageJeu.this,MainActivity.class);
                startActivity(intent);
            }
        });


            for (Button btn : lettres_btn.keySet()) {
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (char lettre : liste_lettres_du_mot) {
                            if (lettres_btn.get(btn) != lettre) {
                                pageNiveaux.nombre_essaie-=1;
                                txt_pageJeu_essaie.setText("Il vous reste "+pageNiveaux.nombre_essaie+" essaies!");
                                String message = "La lettre " + lettres_btn.get(btn) + " n'existe pas dans ce mot !";
                                Toast.makeText(pageJeu.this, message, Toast.LENGTH_LONG).show();
                                btn.setVisibility(View.INVISIBLE);
                            } else {
                                int place_in_liste = liste_lettres_du_mot.indexOf(lettre);
                                String message = "Damn ! vous avez bien deviné";
                                if (place_in_liste == 0) {
                                    char[] mot_a_afficher_temp = mot_a_afficher.toCharArray();
                                    mot_a_afficher_temp[place_in_liste] = lettre;
                                    mot_a_afficher = new String(mot_a_afficher_temp);
                                    txt_pageJeu_mot.setText(mot_a_afficher);
                                    Toast.makeText(pageJeu.this, message, Toast.LENGTH_LONG).show();
                                    btn.setVisibility(View.INVISIBLE);
                                } else {
                                    char[] mot_a_afficher_temp = mot_a_afficher.toCharArray();
                                    mot_a_afficher_temp[place_in_liste * 2] = lettre;
                                    mot_a_afficher = new String(mot_a_afficher_temp);
                                    txt_pageJeu_mot.setText(mot_a_afficher);
                                    Toast.makeText(pageJeu.this, message, Toast.LENGTH_LONG).show();
                                    btn.setVisibility(View.INVISIBLE);
                                }
                            }
                        }
                    }
                });
            }


        Verification_du_tires();

    }
    public void SetupView(){
        // création des buttons du clavier en les ajoutant dans une liste
        clavier[0]=findViewById(R.id.btn_pageJeu_A);
        clavier[1]=findViewById(R.id.btn_pageJeu_B);
        clavier[2]=findViewById(R.id.btn_pageJeu_C);
        clavier[3]=findViewById(R.id.btn_pageJeu_D);
        clavier[4]=findViewById(R.id.btn_pageJeu_E);
        clavier[5]=findViewById(R.id.btn_pageJeu_F);
        clavier[6]=findViewById(R.id.btn_pageJeu_G);
        clavier[7]=findViewById(R.id.btn_pageJeu_H);
        clavier[8]=findViewById(R.id.btn_pageJeu_I);
        clavier[9]=findViewById(R.id.btn_pageJeu_J);
        clavier[10]=findViewById(R.id.btn_pageJeu_K);
        clavier[11]=findViewById(R.id.btn_pageJeu_L);
        clavier[12]=findViewById(R.id.btn_pageJeu_M);
        clavier[13]=findViewById(R.id.btn_pageJeu_N);
        clavier[14]=findViewById(R.id.btn_pageJeu_O);
        clavier[15]=findViewById(R.id.btn_pageJeu_P);
        clavier[16]=findViewById(R.id.btn_pageJeu_Q);
        clavier[17]=findViewById(R.id.btn_pageJeu_R);
        clavier[18]=findViewById(R.id.btn_pageJeu_S);
        clavier[19]=findViewById(R.id.btn_pageJeu_T);
        clavier[20]=findViewById(R.id.btn_pageJeu_U);
        clavier[21]=findViewById(R.id.btn_pageJeu_V);
        clavier[22]=findViewById(R.id.btn_pageJeu_W);
        clavier[23]=findViewById(R.id.btn_pageJeu_X);
        clavier[24]=findViewById(R.id.btn_pageJeu_Y);
        clavier[25]=findViewById(R.id.btn_pageJeu_Z);

        for (int i=0;i<pageNiveaux.mot.length();i++){
            liste_lettres_du_mot.add(pageNiveaux.mot.charAt(i));
        }

        //création du texte du mot à deviner en "_"
        txt_pageJeu_mot=findViewById(R.id.txt_pageJeu_mot);

        //création du texte du nombre d'essaies qui reste
        txt_pageJeu_essaie=findViewById(R.id.txt_pageJeu_essaie);

        //création du ImageButton pour revenir à la page précedente
        imgb_pageJeu_back=findViewById(R.id.imgb_pageJeu_back);
    }

    // une méthode qui nous permet d'ajouter les buttons et leurs valeurs dans le dictionnaire.
    public void Button_lettre(){
        // Ajouter les buttons et leurs valeurs dans le dictionnaire.
        for (int i=0 ; i <26;i++){
            int temp=i+65;
            char character=(char) temp;
            lettres_btn.put(clavier[i],character);
        }
    }
    public String Mot_a_deviner_tires(String mot){
        String tires="";
        for(int i=1;i<=mot.length();i++){
            if(i==mot.length())
            {tires+="_";
        }else{tires+="_ ";}
        }
            return tires;
    }

    public void Verification_du_tires(){
        // Transfomer la variable "mot_a_afficher" en liste afin de vérifier si les tirès toujours existent.
        //  1. création d'une ArrayList 'verification_de_tires'
        ArrayList<Character> verification_de_tires=new ArrayList<Character>();
        //  2. mettre des lettres dans la liste 'verification_de_tires'
        for(int i=0;i<mot_a_afficher.length();i++){
            if (mot_a_afficher.charAt(i)=='_') {
                verification_de_tires.add(mot_a_afficher.charAt(i));
            }else{
                continue;
            }
        }
        //  3. Faire la vérification
            //si '_' existe, calcule le nombre de '_' restant et affiche dans un Toast
        if(verification_de_tires.contains('_')) {
            int nombre_de_tires=0;
            for(int i=0;i<verification_de_tires.size();i++){
                nombre_de_tires+=1;
                Toast.makeText(pageJeu.this,"Il vous reste "+nombre_de_tires+" lettre à deviner!",Toast.LENGTH_LONG).show();
                continue;
            }
            //si '_' N'existe PAS, calcule le nombre de '_' restant et affiche dans un Toast
        }else{
            String message_gagne="Bravo! Vous avez gagné!";
            Toast.makeText(pageJeu.this,message_gagne,Toast.LENGTH_LONG).show();
            Intent intent=new Intent(pageJeu.this,MainActivity.class);
            startActivity(intent);
        }
    }


}

// la difference entre la variable 'mot_a_afficher' et 'pageNiveaux.mot' est que 'mot_a_afficher' possède des '_', mais ce n'est pas le cas pour 'pageNiveaux.mot'

//        char[] mot_a_deviner_length = new char[pageNiveaux.mot.length()];
//        for (int i = 0; i < mot_a_deviner_length.length; i++) {
//            mot_a_deviner_length[i] = '_';
//        }
//        mot_a_deviner=new String(mot_a_deviner_length);
//        return mot_a_deviner;
//    }


//        while(pageNiveaux.nombre_essaie>0) {
//            for (Button btn:lettres_btn.keySet()) {
//                btn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        for(char lettre:liste_lettres_du_mot) {
//                            if (lettres_btn.get(btn) != lettre){
//                                pageNiveaux.nombre_essaie-=1;
//                                String message="La lettre "+lettre+" n'existe pas dans ce mot !";
//                                Toast.makeText(pageJeu.this,message,Toast.LENGTH_LONG);
//                                btn.setVisibility(View.INVISIBLE);
//                            }else{
//                                int place_in_liste=liste_lettres_du_mot[lettre];
//                                String message="Damn ! vous avez bien deviné";
//                                if(place_in_liste==0){
//                                    char[] mot_a_afficher_temp=mot_a_afficher.toCharArray();
//                                    mot_a_afficher_temp[place_in_liste]=lettre;
//                                    mot_a_afficher=new String(mot_a_afficher_temp);
//                                    txt_pageJeu_mot.setText(mot_a_afficher);
//                                    Toast.makeText(pageJeu.this,message,Toast.LENGTH_LONG);
//                                    btn.setVisibility(View.INVISIBLE);
//                                }else{
//                                    char[] mot_a_afficher_temp=mot_a_afficher.toCharArray();
//                                    mot_a_afficher_temp[(place_in_liste*2)+1]=lettre;
//                                    mot_a_afficher=new String(mot_a_afficher_temp);
//                                    txt_pageJeu_mot.setText(mot_a_afficher);
//                                    Toast.makeText(pageJeu.this,message,Toast.LENGTH_LONG);
//                                    btn.setVisibility(View.INVISIBLE);
//                                }
//                            }
//                        }
//                    }
//                });
//            }
//        }



//Une méthode qui nous permet de couper des lettres dans un mot et les ajouter dans la liste 'liste_lettres_du_mot'
//    public void Lettres_du_mot(String mot){
//        for (int i=0;i<mot.length();i++){
//            liste_lettres_du_mot.add(mot.charAt(i));
//        }
//    }