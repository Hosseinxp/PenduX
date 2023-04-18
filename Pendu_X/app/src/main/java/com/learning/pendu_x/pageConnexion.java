package com.learning.pendu_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.learning.pendu_x.R;

public class pageConnexion extends AppCompatActivity {
    Button btn_pageConnexion_login,btn_pageConnexion_signUp;
    ImageButton imgb_pageConnexion_back;

//    View.OnClickListener login=new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            startActivity(new Intent(pageConnexion.this, pageLogin.class));
//        }
//    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // supprimer ActionBar et TitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_page_connexion);


        if(isConnected()==false){
            BD_actions database=new BD_actions(getApplicationContext());
            database.setOn_Off(false);
            Toast.makeText(this, "Sorry! you're not connected to internet", Toast.LENGTH_SHORT).show();
            Intent pageConnection_main=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(pageConnection_main);
        }

        SetupView();

    }
    public void SetupView(){
        btn_pageConnexion_login=findViewById(R.id.btn_pageConnexion_login);
        btn_pageConnexion_signUp=findViewById(R.id.btn_pageConnexion_signUp);
        imgb_pageConnexion_back=findViewById(R.id.imgb_pageConnexion_back);

        btn_pageConnexion_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent connexion_signUp =new Intent(pageConnexion.this,pageSignUp.class);
                startActivity(connexion_signUp);
                finish();
            }
        });

        imgb_pageConnexion_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent connexion_main =new Intent(pageConnexion.this,MainActivity.class);
                startActivity(connexion_main);
                finish();
            }
        });
        btn_pageConnexion_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent connexion_login =new Intent(pageConnexion.this,pageLogin.class);
                startActivity(connexion_login);
                finish();
            }
        });
    }

    public boolean isConnected(){
        ConnectivityManager manager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }


}