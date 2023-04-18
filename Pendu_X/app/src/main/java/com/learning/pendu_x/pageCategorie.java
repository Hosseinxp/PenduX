package com.learning.pendu_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.learning.pendu_x.R;

public class pageCategorie extends AppCompatActivity {
    ImageButton imgb_pageCateg_voiture,imgb_pageCateg_back;
    TextView txt_pageCateg_XCoins;
    private String theme;
    private int XCoins;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // supprimer ActionBar et TitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_page_categorie);
        SetupView();
        GetInfo();

    }

    public void SetupView(){
        imgb_pageCateg_voiture=findViewById(R.id.imgb_pageCateg_voiture);
        imgb_pageCateg_back=findViewById(R.id.imgb_pageCateg_back);
        txt_pageCateg_XCoins=findViewById(R.id.txt_pageCateg_XCoins);

        imgb_pageCateg_voiture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme="Voiture";
                Intent intent_niveaux=new Intent(pageCategorie.this,pageNiveaux.class);
                intent_niveaux.putExtra("Theme",theme);
//                intent_niveaux.putExtra("XCoins",XCoins);
//                intent_niveaux.putExtra("username",username);
                startActivity(intent_niveaux);
            }
        });
        imgb_pageCateg_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_connexion_back=new Intent(pageCategorie.this,MainActivity.class);
//                main_connexion_back.setAction("MAIN_CONNECTION_BACK");
//                main_connexion_back.putExtra("XCoins",XCoins);
//                main_connexion_back.putExtra("username",username);
                startActivity(main_connexion_back);
                finish();
            }
        });
    }

    public void GetInfo(){
//        Intent main_categ=getIntent();
//        XCoins=main_categ.getIntExtra("XCoins",XCoins);
//        username=main_categ.getStringExtra("username");

        BD_actions database=new BD_actions(getApplicationContext());
        XCoins=database.getXCoins();

        if(String.valueOf(XCoins).length()< 3){
            txt_pageCateg_XCoins.setTextSize(34);
            txt_pageCateg_XCoins.setText(XCoins+"");
        }
        else if(String.valueOf(XCoins).length()> 4){
            txt_pageCateg_XCoins.setTextSize(30);
            txt_pageCateg_XCoins.setText(XCoins+"");
        }
        else if(String.valueOf(XCoins).length()> 5){
            txt_pageCateg_XCoins.setTextSize(28);
            txt_pageCateg_XCoins.setText(XCoins+"");
        }else{
            txt_pageCateg_XCoins.setTextSize(22);
            txt_pageCateg_XCoins.setText(XCoins+"");
        }

        if(isConnected()==false){
            Toast.makeText(this, "You're not connected to Internet", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean isConnected(){
        ConnectivityManager manager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}