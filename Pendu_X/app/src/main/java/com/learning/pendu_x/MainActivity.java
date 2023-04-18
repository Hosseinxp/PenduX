package com.learning.pendu_x;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//import com.learning.pendu_x.R;

public class MainActivity extends AppCompatActivity {
    Button btn_pageMain_Jouer, btn_pageMain_Setting;
    ImageButton imgb_pageMain_account;
    TextView txt_pageMain_XCoins;

    private String username, password;
    private int XCoins, WIN = 0, LOST = 0;
    private boolean on_off;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // supprimer ActionBar et TitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_main);
        SetupView();
        GetInfo();

    }

    // Création des buttons 'Jouer' et 'paramétrage'
    public void SetupView() {
        btn_pageMain_Jouer = findViewById(R.id.btn_pageMain_Jouer);
        btn_pageMain_Setting = findViewById(R.id.btn_pageMain_Setting);
        imgb_pageMain_account = findViewById(R.id.imgb_pageMain_account);
        txt_pageMain_XCoins = findViewById(R.id.txt_pageMain_XCoins);

        btn_pageMain_Jouer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_categorie = new Intent(MainActivity.this, pageCategorie.class);
//                main_categorie.putExtra("XCoins", XCoins);
//                main_categorie.putExtra("Username", username);
                startActivity(main_categorie);
            }
        });
        btn_pageMain_Setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent main_setting = new Intent(MainActivity.this, pageSetting.class);
//                main_setting.putExtra("XCoins", XCoins);
//                main_setting.putExtra("username", username);
                startActivity(main_setting);
            }
        });

        imgb_pageMain_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()) {
                    if (on_off) {
                        Intent main_connexion = new Intent(MainActivity.this, pageUserAccount.class);
//                    main_connexion.putExtra("XCoins", XCoins);
//                    main_connexion.putExtra("WIN", WIN);
//                    main_connexion.putExtra("LOST", LOST);
                        startActivity(main_connexion);
                        finish();
                    } else {
                        Intent main_connexion = new Intent(MainActivity.this, pageConnexion.class);
//                    main_connexion.putExtra("XCoins", XCoins);
//                    main_connexion.putExtra("WIN", WIN);
//                    main_connexion.putExtra("LOST", LOST);
                        startActivity(main_connexion);
                        finish();
//                    Toast.makeText(MainActivity.this, "No internet access", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainActivity.this, "You're not connected to internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void GetInfo() {
//        Intent getInfoIntent = getIntent();

//        if (getInfoIntent.getAction().equals("ACCOUNT_MAIN_BACK")) {
//            XCoins = getInfoIntent.getIntExtra("XCoins", XCoins);
//            username=getInfoIntent.getStringExtra("username");
//        }

//        else if (getInfoIntent.getAction().equals("SIGN_OUT")) {
//            XCoins = getInfoIntent.getIntExtra("XCoins", XCoins);
//            on_off=getInfoIntent.getBooleanExtra("on_off",on_off);
//            LOST=getInfoIntent.getIntExtra("LOST",LOST);
//            WIN=getInfoIntent.getIntExtra("LOST",WIN);
//            username=null;
//        }
//        else if(getInfoIntent.getAction().equals("MAIN_CONNECTION_BACK")){
//            XCoins = getInfoIntent.getIntExtra("XCoins", XCoins);
//            username=getInfoIntent.getStringExtra("username");
//        }

//        else if(getInfoIntent.getAction().equals("JEU_MAIN_BACK")){
//            XCoins = getInfoIntent.getIntExtra("XCoins", XCoins);
//            username=getInfoIntent.getStringExtra("username");
//        }
//        else if(getInfoIntent.getAction().equals("SETTING_MAIN_BACK")){
//            XCoins = getInfoIntent.getIntExtra("XCoins", XCoins);
//            username=getInfoIntent.getStringExtra("username");
//        }

        //****************

//        if (getInfoIntent.getAction().equals("JEU_MAIN_WIN")) {
//            XCoins+= getInfoIntent.getIntExtra("XCoins", XCoins);
//            username=getInfoIntent.getStringExtra("username");
//            WIN+=getInfoIntent.getIntExtra("WIN",1);
//
//
//            if(!username.equals(null)){
//                FirebaseDatabase database=FirebaseDatabase.getInstance();
//                DatabaseReference update_WIN=database.getReference("users");
//                update_WIN.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.exists()){
//                            String database_username=null;
//
//                            for (DataSnapshot everyUser:snapshot.getChildren()){
//                                if(everyUser.getKey().toString().toLowerCase()==username.toLowerCase()){
//                                    database_username=everyUser.getKey().toString();
//                                    break;
//                                }
//                            }
//                            update_WIN.child(database_username).child("xcoins").setValue(XCoins);
//                            update_WIN.child(database_username).child("WIN").setValue(WIN);
//
////                            Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {}.getType();
////                            database_hostory=snapshot.child(database_username).child("history").getValue(type);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        }

//        else if (getInfoIntent.getAction().equals("JEU_MAIN_LOST")) {
//            XCoins = getInfoIntent.getIntExtra("XCoins", XCoins);
//            username=getInfoIntent.getStringExtra("username");
//            LOST+=getInfoIntent.getIntExtra("LOST",1);
//
//            if(!username.equals(null)){
//                FirebaseDatabase database=FirebaseDatabase.getInstance();
//                DatabaseReference update_LOST=database.getReference("users");
//                update_LOST.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.exists()){
//                            String database_username=null;
//
//                            for (DataSnapshot everyUser:snapshot.getChildren()){
//                                if(everyUser.getKey().toString().toLowerCase()==username.toLowerCase()){
//                                    database_username=everyUser.getKey().toString();
//                                    break;
//                                }
//                            }
//                            update_LOST.child(database_username).child("xcoins").setValue(XCoins);
//                            update_LOST.child(database_username).child("WIN").setValue(WIN);
//
////                            Type type = new TypeToken<ArrayList<HashMap<String, String>>>() {}.getType();
////                            database_hiastory=snapshot.child(database_username).child("history").getValue(type);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        }

        if(isConnected()){
            BD_actions database=new BD_actions(getApplicationContext());
            username=database.getUsername();
            password=database.getPassword();
            XCoins= database.getXCoins();
            WIN= database.getWIN();
            LOST= database.getLOST();


            if(username!=null && password!=null) {

            FirebaseDatabase firebase_database =FirebaseDatabase.getInstance();

            DatabaseReference reference=firebase_database.getReference("users").child(username);
            DatabaseReference infoConnected=firebase_database.getReference(".info/connected");


            infoConnected.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    boolean connected = snapshot.getValue(Boolean.class);

                    if (connected) {
                        reference.child("on_off").setValue(true);
                        reference.child("on_off").onDisconnect().setValue(false);

                        reference.child("on_off").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){
                                    BD_actions database=new BD_actions(getApplicationContext());
                                    if(Boolean.parseBoolean(snapshot.getValue().toString())){
                                        database.setOn_Off(false);
                                    }else{
                                        database.setOn_Off(true);
                                    }
                                    if (Integer.parseInt(snapshot.child("xcoins").getValue().toString()) != XCoins) {
                                        reference.child("xcoins").setValue(XCoins + Integer.parseInt(snapshot.child("xcoins").getValue().toString()));
                                        database.setXCoins(XCoins + Integer.parseInt(snapshot.child("xcoins").getValue().toString()));

                                    } else if (Integer.parseInt(snapshot.child("WIN").getValue().toString()) != WIN) {
                                        reference.child("WIN").setValue(WIN + Integer.parseInt(snapshot.child("WIN").getValue().toString()));
                                        database.setWIN_firebase(Integer.parseInt(snapshot.child("WIN").getValue().toString()));

                                    } else if (Integer.parseInt(snapshot.child("LOST").getValue().toString()) != LOST) {
                                        reference.child("LOST").setValue(LOST + Integer.parseInt(snapshot.child("LOST").getValue().toString()));
                                        database.setLOST_firebase(Integer.parseInt(snapshot.child("LOST").getValue().toString()));
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });


                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            }

            XCoins= database.getXCoins();
            WIN= database.getWIN();
            LOST= database.getLOST();
            on_off=database.getOn_Off();

        }else{
            Toast.makeText(this, "You're not connected to Internet", Toast.LENGTH_SHORT).show();
            BD_actions database=new BD_actions(getApplicationContext());
            username=database.getUsername();
            password=database.getPassword();
            XCoins= database.getXCoins();
            WIN= database.getWIN();
            LOST= database.getLOST();
            on_off=database.getOn_Off();
        }


//        BD_actions database=new BD_actions(getApplicationContext());
//        username=database.getUsername();
//        password=database.getPassword();
//        XCoins= database.getXCoins();
//        WIN= database.getWIN();
//        LOST= database.getLOST();
//        on_off=database.getOn_Off();

        Log.i("USERNAME",username+"");
        Log.i("PASSWORD",password+"");
        Log.i("XCoins",XCoins+"");
        Log.i("WIN",WIN+"");
        Log.i("LOST",LOST+"");
        Log.i("on_off",on_off+"");


        if (String.valueOf(XCoins).length() < 3) {
            txt_pageMain_XCoins.setTextSize(34);
            txt_pageMain_XCoins.setText(XCoins + "");
        } else if (String.valueOf(XCoins).length() > 4) {
            txt_pageMain_XCoins.setTextSize(30);
            txt_pageMain_XCoins.setText(XCoins + "");
        } else if (String.valueOf(XCoins).length() > 5) {
            txt_pageMain_XCoins.setTextSize(28);
            txt_pageMain_XCoins.setText(XCoins + "");
        } else {
            txt_pageMain_XCoins.setTextSize(22);
            txt_pageMain_XCoins.setText(XCoins + "");
        }


    }

    public boolean isConnected(){
        ConnectivityManager manager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}

//    public boolean inConnected(){
//
//    }