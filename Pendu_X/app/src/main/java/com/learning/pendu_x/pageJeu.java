package com.learning.pendu_x;
//liste_lettres_du_mot
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.learning.pendu_x.R;

import java.util.ArrayList;
import java.util.HashMap;

public class pageJeu extends AppCompatActivity {
    static Button[] clavier = new Button[26];

    // création d'un dictionnaire pour avoir le button sur le clavier et la définition de sa valeur.
    HashMap<Button, Character> lettres_btn = new HashMap<Button, Character>();
    TextView txt_pageJeu_mot, txt_pageJeu_essaie,txt_pageJeu_XCoins,txt_pageJeu_themeNiveau;
    ImageButton imgb_pageJeu_back;

    private ArrayList<Character> characters=new ArrayList<Character>();
    private ArrayList<Integer> numbers=new ArrayList<Integer>();

//    private HashMap<String,String> history=new HashMap<String,String>();


    // initlialisation du résultat de la méthode " Mot_a_deviner_tires " dans la variable mot_a_afficher... comme ca, chaque fois qu'on appuie sur un button, c'est plus facile de changer des lettre du mot à deviner.
    private String mot_choisi,niveau,theme,theme_niveau,mot_a_afficher,username;
    private int nombre_essaie,XCoins_recompense,XCoins;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // supprimer ActionBar et TitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_page_jeu);
        GetInfo();
        SetupView();
        Button_lettre();

        txt_pageJeu_essaie.setText("Il vous reste " + nombre_essaie + " essaies!");

        if (mot_choisi.length() < 4) {
            txt_pageJeu_mot.setTextSize(60);
            txt_pageJeu_mot.setText(mot_a_afficher);
        } else if (mot_choisi.length() > 4 && mot_choisi.length() < 6) {
            txt_pageJeu_mot.setTextSize(55);
            txt_pageJeu_mot.setText(mot_a_afficher);
        } else {
            txt_pageJeu_mot.setTextSize(45);
            txt_pageJeu_mot.setText(mot_a_afficher);
        }





        /* Comment le code fonctionne ?
            Pour chaque button qui existent dans le ArrayListe 'lettres_btn', nous mettons une plusieurs conditions et en fonction de la validité des conditions, le button marche différement.
            C'est quoi la condition? Si la lettre du clavier existe dans le mot, affiche un message 'Bravo' et met le button en invisible et met la lettre à la place de tiré dans le mot qui est affiché.
            Si la lettre du clavier n'est pas dans le mot, met le button en invisible et affiche un message 'La lettre n'existe pas'.
            Après avoir gagné le jeu, nous affiche un message ' bravo vous avez gagné!' et nous retourne un message.
            Comment?
                1. Nous définissons une variable booléan qui s'appelle 'lettre_trouvee' et nous le mettons 'false' en défault.
                2. Dans la fonctionnalité de chaque button, nous metton une bouvle for pour parcourir dans la liste ''liste_lettres_du_mot'' qui contient les lettres du mot aléatoirement choisi.
                        Si la lettre du clavier existe dans le mot:
                            on trouve la place de la lettre du clavier dans le mot et affiche un message 'Damn ! vous avez bien deviné'. Ensuite, pour bien placer la lattre dans le mot qui est affiché, nous mettons une condition(car il y a un espace entre
                            chaque tiré, donc la longeur du mot_a_afficher est plus grande que la longeur du mot aléatoirement choisi dans la pageNiveaux) :
                                Si la lettre devinée par le client est placée en première:
                                    transformer le mot_a_afficher en une liste du type 'char' qui s'appelle 'mot_a_afficher_temp'.Donc là, on met la lettre à la place du tiré ciblé.Après, nous retransformons 'mot_a_afficher_temp' en String
                                    et nous la remettons dans mot_a_afficher et nous l'affiche.
                                Si la lettre devinée par le client n'est pas en première:
                                    on suit la structure precedente, mais pour la facon de mise en place de la lettre et differente (place du mot dans la liste fois 2 et mettre le tiré dans cette place)
                        Si la lettre du clavier n'existe pas dans le mot, on met 'lettre_trouvee' comme condition( car c'est déja faux):
                             diminue le nombre d'essaie par 1 et affiche le nouveau nombre d'essaie.Après mettre me button en invisible.
                        Si il n'exite pas de tirès dans mot_a_afficher, ca veut dire l'utilisateur a gagné! donc:
                             Affiche un message 'Bravo vous avez gagné' et retourne à la page Main(principale).

              ****  C'est quoi l'histoire de la variabel 'lettre_trouvee' ?
                        Exactement après avoir définit la méthode onClicke, nous créons une variable boolean 'lettre_trouvee' qui est false par defaut.
                        dans la première boucle dans la condition if(pageNiveaux.nombre_essaie > 1), si l'utilisateur arrive à trouver la lettre, cette variable va être 'true' et comme ca, quand on est sortie de
                        la boucle, pour la boucle if (!lettre_trouvee), la condition est false et la boucle ne se démarre plus!
                        en résumé, si l'utilisateur a trouvé la lettre, 'lettre_trouvee' va être 'true' et la boucle qui diminue le nombre d'essaie ne fonctionne pas, sinon, si la lettre n'a pas été trouvé, la boucle se demarre automatiquement.

              ****  Pourquoiif (pageNiveaux.nombre_essaie > 1), mais pas if (pageNiveaux.nombre_essaie > 0) ?
                        Car quand nombre_essaie arrive à 0, nous sommes toujours dans la première condition! ca veut dire la condition ' if (pageNiveaux.nombre_essaie > ...) ' vérifie le nomnbre_essaie avant la diminuation du nomnbre_essaie !
                        donc ca veut dire quand nomnbre_essaie est 0, elle n'arrive pas à vérifier, mais c'est après !
         */


        for (Button btn : lettres_btn.keySet()) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean lettre_trouvee = false;
                    if (nombre_essaie > 1) {
                        for (char lettre : characters) {
                            if (lettres_btn.get(btn) == lettre) {
                                lettre_trouvee=true;

                                //La raison pour laquelle nous avons créé une Arrayliste de Integer est parce que après avoir vérifié si la lettre d'un button appuyé correspond à la lettre
                                // dans le mot, nous avons besoin de toutes les places de LA LETTRE CHOISIE afin de remplacer la lettre à la place de tirè dans l'affichage.
                                // Comment?
                                //      si par exemple la lettre est 'T' et qu'elle est répetée 3 fois dans la lettre, nous mettons les chiffres de toutes ses places de ce mot dans
                                //       une ArrayList qui s'appelle " temp_places_in_liste " . Donc après, selon le chiffre de la lettre, nous remplacons le tirès par la lettre.
                                // *** Par exemple, pour un mot comme " TOYOTA ", la lettre 'O' est répetée 2 fois, donc avec cette méthode, nous pouvons remplacer tous les tirés qui correspondent
                                //      à la lettre 'O';


                                Log.i("CHARACTERS", characters +"");
                                Log.i("NUMBERS", numbers +"");


                                String message = "Damn ! vous avez bien deviné";
                                ArrayList<Integer> places_lettre =new ArrayList<Integer>();
                                for(int counter=0;counter<characters.size();counter++){
                                    if(lettres_btn.get(btn)==characters.get(counter)){
                                        places_lettre.add(numbers.get(counter));
                                    }
                                }

                                char[] mot_a_afficher_temp = mot_a_afficher.toCharArray();
                                for(int counter=0;counter<places_lettre.size();counter++) {
                                    int place=places_lettre.get(counter);
                                    if (place == 0) {
                                        mot_a_afficher_temp[place]=lettre;
                                    }
                                    else {
                                        mot_a_afficher_temp[place * 2] = lettre;
                                    }
                                }
                                mot_a_afficher = new String(mot_a_afficher_temp);
                                txt_pageJeu_mot.setText(mot_a_afficher);
                                Toast.makeText(pageJeu.this, message, Toast.LENGTH_LONG).show();
                                btn.setVisibility(View.INVISIBLE);
                            }
                        }

                        // la difference entre les 2 conditions dedans est juste quand 'nombre_essaie' arrive à 1, il n'y pas de 's' à la fin de 'essaie' !!!
                        if (!lettre_trouvee) {
                            nombre_essaie--;
                            if(nombre_essaie>1) {
                                txt_pageJeu_essaie.setText("Il vous reste " + nombre_essaie + " essaies!");
                                String message = "La lettre " + lettres_btn.get(btn) + " n'existe pas dans ce mot !";
                                Toast.makeText(pageJeu.this, message, Toast.LENGTH_LONG).show();
                                btn.setVisibility(View.INVISIBLE);
                            }else {
                                txt_pageJeu_essaie.setText("Il vous reste " + nombre_essaie + " essaie !");
                                String message = "La lettre " + lettres_btn.get(btn) + " n'existe pas dans ce mot !";
                                Toast.makeText(pageJeu.this, message, Toast.LENGTH_LONG).show();
                                btn.setVisibility(View.INVISIBLE);
                            }
                        }

                        if (mot_a_afficher.replaceAll("\\s+", "").equals(mot_choisi)) {
                            Toast.makeText(pageJeu.this, "Bravo! Vous avez gagné!", Toast.LENGTH_LONG).show();

                            XCoins+=XCoins_recompense;

//                            Date date = new Date();
//                            history.put("Result","WIN");
//                            history.put("Level",niveau+"");
//                            history.put("Theme",theme+"");
//                            history.put("Date_Time",date+"");
//
//                            Log.i("TIME_WIN",date+"");


                            BD_actions database=new BD_actions(getApplicationContext());
                            database.setXCoins(XCoins);
                            database.setWIN_gameDone();

                            Intent jeu_main_WIN = new Intent(pageJeu.this, MainActivity.class);
//                            jeu_main_WIN.setAction("JEU_MAIN_WIN");
//                            jeu_main_WIN.putExtra("XCoins",XCoins);
//                            jeu_main_WIN.putExtra("WIN",1);
                            startActivity(jeu_main_WIN);
                            finish();
                        }
                    }

                    else {
                        Toast.makeText(pageJeu.this, "Vous avez perdu !", Toast.LENGTH_LONG).show();

//                        Date date = new Date();
//                        history.put("Result","LOST");
//                        history.put("Level",niveau+"");
//                        history.put("Theme",theme+"");
//                        history.put("Date_Time",date+"");
//                        Log.i("TIME_WIN",com.google.firebase.database.ServerValue.TIMESTAMP+"");

                        BD_actions database=new BD_actions(getApplicationContext());
                        database.setLOST_gameDone();
                        Intent jeu_main_LOST = new Intent(pageJeu.this, MainActivity.class);
//                        jeu_main_LOST.setAction("JEU_MAIN_LOST");
//                        jeu_main_LOST.putExtra("XCoins",XCoins);
//                        jeu_main_LOST.putExtra("LOST",1);
                        startActivity(jeu_main_LOST);
                        finish();
                    }
                }
            });
        }
//        Verification_du_tires();

    }

    public void SetupView() {
        // création des buttons du clavier en les ajoutant dans une liste
        clavier[0] = findViewById(R.id.btn_pageJeu_A);
        clavier[1] = findViewById(R.id.btn_pageJeu_B);
        clavier[2] = findViewById(R.id.btn_pageJeu_C);
        clavier[3] = findViewById(R.id.btn_pageJeu_D);
        clavier[4] = findViewById(R.id.btn_pageJeu_E);
        clavier[5] = findViewById(R.id.btn_pageJeu_F);
        clavier[6] = findViewById(R.id.btn_pageJeu_G);
        clavier[7] = findViewById(R.id.btn_pageJeu_H);
        clavier[8] = findViewById(R.id.btn_pageJeu_I);
        clavier[9] = findViewById(R.id.btn_pageJeu_J);
        clavier[10] = findViewById(R.id.btn_pageJeu_K);
        clavier[11] = findViewById(R.id.btn_pageJeu_L);
        clavier[12] = findViewById(R.id.btn_pageJeu_M);
        clavier[13] = findViewById(R.id.btn_pageJeu_N);
        clavier[14] = findViewById(R.id.btn_pageJeu_O);
        clavier[15] = findViewById(R.id.btn_pageJeu_P);
        clavier[16] = findViewById(R.id.btn_pageJeu_Q);
        clavier[17] = findViewById(R.id.btn_pageJeu_R);
        clavier[18] = findViewById(R.id.btn_pageJeu_S);
        clavier[19] = findViewById(R.id.btn_pageJeu_T);
        clavier[20] = findViewById(R.id.btn_pageJeu_U);
        clavier[21] = findViewById(R.id.btn_pageJeu_V);
        clavier[22] = findViewById(R.id.btn_pageJeu_W);
        clavier[23] = findViewById(R.id.btn_pageJeu_X);
        clavier[24] = findViewById(R.id.btn_pageJeu_Y);
        clavier[25] = findViewById(R.id.btn_pageJeu_Z);

        //création du texte du mot à deviner en "_"
        txt_pageJeu_mot = findViewById(R.id.txt_pageJeu_mot);

        //création du texte du nombre d'essaies qui reste
        txt_pageJeu_essaie = findViewById(R.id.txt_pageJeu_essaie);

        //création du ImageButton pour revenir à la page précedente
        imgb_pageJeu_back = findViewById(R.id.imgb_pageJeu_back);

        //création du texte pour afficher le montant du XCoins que l'utilisateur possède
        txt_pageJeu_XCoins=findViewById(R.id.txt_pageJeu_XCoins);

        //création du texte pour afficher le theme et le niveau choisi
        txt_pageJeu_themeNiveau=findViewById(R.id.txt_pageJeu_themeNiveau);

        imgb_pageJeu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent jeu_main_back=new Intent(pageJeu.this,MainActivity.class);
//                jeu_main_back.setAction("JEU_MAIN_BACK");
//                jeu_main_back.putExtra("XCoins",XCoins);
//                jeu_main_back.putExtra("username",username);
                startActivity(jeu_main_back);
                finish();
            }
        });




        // initiliaser le mot à afficher en utilisant la fontion Mot_a_deviner_tires()
        mot_a_afficher = Mot_a_deviner_tires(mot_choisi);
        // Changer la taille du texte du XCoins en donction du montant gagné!
        if (String.valueOf(XCoins).length() < 3) {
            txt_pageJeu_XCoins.setTextSize(34);
            txt_pageJeu_XCoins.setText(XCoins+"");
        } else if (String.valueOf(XCoins).length() > 4) {
            txt_pageJeu_XCoins.setTextSize(30);
            txt_pageJeu_XCoins.setText(XCoins+"");
        } else if (String.valueOf(XCoins).length() > 5) {
            txt_pageJeu_XCoins.setTextSize(28);
            txt_pageJeu_XCoins.setText(XCoins+"");
        } else {
            txt_pageJeu_XCoins.setTextSize(22);
            txt_pageJeu_XCoins.setText(XCoins+"");
        }
        // Affichage du theme et le niveau choisi
        theme_niveau=theme+"  --  "+niveau;
        txt_pageJeu_themeNiveau.setText(theme_niveau);


        // création d'une bouble for afin de créer une liste contenant des lettres du mot choisi.
        for (int i = 0; i < mot_choisi.length(); i++) {
            characters.add(mot_choisi.charAt(i));
            numbers.add(i);
        }
    }

    public void GetInfo(){
        //Récuperation des infos qui arrive de la page niveaux et les information dans la base de données.
        Intent niveaux_jeu=getIntent();
        mot_choisi=niveaux_jeu.getStringExtra("mot");
        nombre_essaie=niveaux_jeu.getIntExtra("nombre_essaie",nombre_essaie);
        niveau=niveaux_jeu.getStringExtra("niveau");
        XCoins_recompense=niveaux_jeu.getIntExtra("XCoins_recompense",XCoins_recompense);
        theme=niveaux_jeu.getStringExtra("theme");

        BD_actions database=new BD_actions(getApplicationContext());
        XCoins=database.getXCoins();
//        username=niveaux_jeu.getStringExtra("username");
        //        XCoins=niveaux_jeu.getIntExtra("XCoins",XCoins);

    }

    // une méthode qui nous permet d'ajouter les buttons et leurs valeurs dans le dictionnaire.
    public void Button_lettre() {
        // Ajouter les buttons et leurs valeurs dans le dictionnaire.
        for (int i = 0; i < 26; i++) {
            int temp = i + 65;
            char character = (char) temp;
            lettres_btn.put(clavier[i], character);
        }
    }

    // C'est une méthode qui nous permet de prendre le mot aléatoirement choisi et le transfomer en _ pour afficher dans le TextView (pour que l'utilisateur voit il y a combien de _ )
    public String Mot_a_deviner_tires(String mot) {
        String tires = "";
        for (int i = 1; i <= mot.length(); i++) {
            if (i == mot.length()) {
                tires += "_";
            } else {
                tires += "_ ";
            }
        }
        return tires;
    }

}



    /* Pour comprendre mieux le code:
        dans la méthode SetupView(), nous avons mit les buttons dans une liste du type button qui s'appelle 'clavier'.En plus, nous avons cuppé le mot en lettre et les avons mit dans une liste qui s'appelle 'liste_lettres_du_mot' .
        Ensuite nous avons mit tous les buttons qui existent dans la liste 'clavier', en plus de leur lettre, dans l'ArrayList  lettres_btn (en utilisant la méthode Button_lettre )
        Dans la méthode Mot_a_deviner_tires, nous mettons des tirés à la place des lettres du mot aléatoirement choisie dans la page Niveau, en plus d'un espace entre chaque lettre, et nous les mettons dans la variable mot_a_afficher.
        Comment les button et le jeu fonctionnent ?
            Pour chaque button qui existent dans le ArrayListe 'lettres_btn', nous mettons une plusieurs conditions et en fonction de la validité des conditions, le button marche différement.
            C'est quoi la condition? Si la lettre du clavier existe dans le mot, affiche un message 'Bravo' et met le button en invisible et met la lettre à la place de tiré dans le mot qui est affiché.
            Si la lettre du clavier n'est pas dans le mot, met le button en invisible et affiche un message 'La lettre n'existe pas'.
            Après avoir gagné le jeu, nous affiche un message ' bravo vous avez gagné!' et nous retourne un message.
            Comment?
                1. Nous définissons une variable booléan qui s'appelle 'lettre_trouvee' et nous le mettons 'false' en défault.
                2. Dans la fonctionnalité de chaque button, nous metton une bouvle for pour parcourir dans la liste ''liste_lettres_du_mot'' qui contient les lettres du mot aléatoirement choisi.
                        Si la lettre du clavier existe dans le mot:
                            on trouve la place de la lettre du clavier dans le mot et affiche un message 'Damn ! vous avez bien deviné'. Ensuite, pour bien placer la lattre dans le mot qui est affiché, nous mettons une condition(car il y a un espace entre
                            chaque tiré, donc la longeur du mot_a_afficher est plus grande que la longeur du mot aléatoirement choisi dans la pageNiveaux) :
                                Si la lettre devinée par le client est placée en première:
                                    transformer le mot_a_afficher en une liste du type 'char' qui s'appelle 'mot_a_afficher_temp'.Donc là, on met la lettre à la place du tiré ciblé.Après, nous retransformons 'mot_a_afficher_temp' en String
                                    et nous la remettons dans mot_a_afficher et nous l'affiche.
                                Si la lettre devinée par le client n'est pas en première:
                                    on suit la structure precedente, mais pour la facon de mise en place de la lettre et differente (place du mot dans la liste fois 2 et mettre le tiré dans cette place)
                        Si la lettre du clavier n'existe pas dans le mot, on met 'lettre_trouvee' comme condition( car c'est déja faux):
                             diminue le nombre d'essaie par 1 et affiche le nouveau nombre d'essaie.Après mettre me button en invisible.
                        Si il n'exite pas de tirès dans mot_a_afficher, ca veut dire l'utilisateur a gagné! donc:
                             Affiche un message 'Bravo vous avez gagné' et retourne à la page Main(principale).*


            ****  C'est quoi l'histoire de la variabel 'lettre_trouvee' ?
                        Exactement après avoir définit la méthode onClicke, nous créons une variable boolean 'lettre_trouvee' qui est false par defaut.
                        dans la première boucle dans la condition if(pageNiveaux.nombre_essaie > 1), si l'utilisateur arrive à trouver la lettre, cette variable va être 'true' et comme ca, quand on est sortie de
                        la boucle, pour la boucle if (!lettre_trouvee), la condition est false et la boucle ne se démarre plus!
                        en résumé, si l'utilisateur a trouvé la lettre, 'lettre_trouvee' va être 'true' et la boucle qui diminue le nombre d'essaie ne fonctionne pas, sinon, si la lettre n'a pas été trouvé, la boucle se demarre automatiquement.

            ****  Pourquoiif (pageNiveaux.nombre_essaie > 1), mais pas if (pageNiveaux.nombre_essaie > 0) ?
                        Car quand nombre_essaie arrive à 0, nous sommes toujours dans la première condition! ca veut dire la condition ' if (pageNiveaux.nombre_essaie > ...) ' vérifie le nomnbre_essaie avant la diminuation du nomnbre_essaie !
                        donc ca veut dire quand nomnbre_essaie est 0, elle n'arrive pas à vérifier, mais c'est après !
 */



    // C'est une méthode qui vérifie il reste combien de tirès et retourne le nombre.En plus, s'il n'y a pas de tirès dans la lsite, ca nous retourne vers la page d'accueil.
//    public void Verification_du_tires(){
//        // Transfomer la variable "mot_a_afficher" en liste afin de vérifier si les tirès toujours existent.
//        //  1. création d'une ArrayList 'verification_de_tires'
//        ArrayList<Character> verification_de_tires=new ArrayList<Character>();
//        //  2. mettre des lettres dans la liste 'verification_de_tires'
//        for(int i=0;i<mot_a_afficher.length();i++){
//            if (mot_a_afficher.charAt(i)=='_') {
//                verification_de_tires.add(mot_a_afficher.charAt(i));
//            }else{
//                continue;
//            }
//        }
//        //  3. Faire la vérification
//            //si '_' existe, calcule le nombre de '_' restant et affiche dans un Toast
//        if(verification_de_tires.contains('_')) {
//            int nombre_de_tires=0;
//            for(int i=0;i<verification_de_tires.size();i++){
//                nombre_de_tires+=1;
//                Toast.makeText(pageJeu.this,"Il vous reste "+nombre_de_tires+" lettre à deviner!",Toast.LENGTH_LONG).show();
//                continue;
//            }
//            //si '_' N'existe PAS, calcule le nombre de '_' restant et affiche dans un Toast
//        }else{
//            String message_gagne="Bravo! Vous avez gagné!";
//            Toast.makeText(pageJeu.this,message_gagne,Toast.LENGTH_LONG).show();
//            Intent intent=new Intent(pageJeu.this,MainActivity.class);
//            startActivity(intent);
//        }
//    }
//
//}








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