package com.learning.pendu_x;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learning.pendu_x.R;

import java.security.MessageDigest;

public class pageLogin extends AppCompatActivity {
    Button btn_pageLogin_login;
    EditText edt_pageLogin_username,edt_pageLogin_password;
    TextView txt_pageLogin_username,txt_pageLogin_password,txt_pageLogin_forgotPassword;

    ImageButton imgb_pageLogin_back;

    private int firebase_XCoins,WIN,LOST;
    private String username,password,firebase_password,firebase_email;


//    View.OnClickListener getPassword=new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            password=txt_pageLogin_password.getText().toString();
//        }
//    };
//    View.OnClickListener getUsername=new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            password=txt_pageLogin_username.getText().toString();
//        }
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_page_login);
        if(isConnected()==false){
            BD_actions database=new BD_actions(getApplicationContext());
            database.setOn_Off(false);
            Toast.makeText(pageLogin.this, "You're not connected to Internet!", Toast.LENGTH_SHORT).show();
            Intent login_main=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(login_main);

        }
        SetupView();
        SetInfo();
    }

    public void SetupView(){
        btn_pageLogin_login=findViewById(R.id.btn_pageLogin_login);
        edt_pageLogin_username=findViewById(R.id.edt_pageLogin_email);
        edt_pageLogin_password=findViewById(R.id.edt_pageLogin_password);
        txt_pageLogin_username=findViewById(R.id.txt_pageLogin_email);
        txt_pageLogin_password=findViewById(R.id.txt_pageLogin_password);
        imgb_pageLogin_back=findViewById(R.id.imgb_pageLogin_back);
        txt_pageLogin_forgotPassword=findViewById(R.id.txt_pageLogin_forgotPassword);
        imgb_pageLogin_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp_connexion=new Intent(pageLogin.this,pageConnexion.class);
                startActivity(signUp_connexion);
                finish();
            }
        });

    }
    public void SetInfo(){

        btn_pageLogin_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username=edt_pageLogin_username.getText().toString().trim();
                password=edt_pageLogin_password.getText().toString().trim();

                boolean username_check=true;
                if(TextUtils.isEmpty(username)){
                    username_check=false;
                    edt_pageLogin_username.setError("Username is empty !");
                    edt_pageLogin_username.requestFocus();
                }else{
                    username_check=true;
                }


                boolean password_check=true;
                if(TextUtils.isEmpty(password)){
                    password_check=false;
                    edt_pageLogin_password.setError("Password is empty !");
                    edt_pageLogin_password.requestFocus();
                }else{
                    password_check=true;
                }


                // Password Hashing!
                MessageDigest messageDigest = null;
                try {
                    messageDigest = MessageDigest.getInstance("SHA");
                } catch (Exception e) {
                    Log.i("EXCEPTON",e.getMessage()+"");
                }
                messageDigest.update(password.getBytes());
                byte[] resultByteArray =messageDigest.digest();
                StringBuilder sb= new StringBuilder();
                for(byte byte_in_array:resultByteArray){
                    sb.append(String.format("%2x",byte_in_array));      // "%2x" est pour transfomer le format du byte en format du String.
                }

                password=sb.toString();



                    if (password_check == true && username_check == true) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance("https://pendu-x-4466b-default-rtdb.europe-west1.firebasedatabase.app");
                        DatabaseReference reference = database.getReference("users");

                        reference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    Log.i("SNAPSHOT", snapshot + "");
                                    boolean userExists = false;
                                    String firebase_username = null;
                                    for (DataSnapshot everyUser : snapshot.getChildren()) {
                                        Log.i("EVERYUSER", everyUser.getKey() + "");
                                        if (everyUser.getKey().toLowerCase().equals(username.toLowerCase())) {
                                            userExists = true;
                                            firebase_username = everyUser.getKey();
                                            break;
                                        }
                                        Log.i("USERNAMEDATABASE", firebase_username + "");
                                    }
                                    if (userExists == true && firebase_username != null) {
                                        firebase_password = snapshot.child(firebase_username).child("password").getValue().toString();
                                        if (firebase_password.equals(password)) {
                                            firebase_XCoins = Integer.parseInt(snapshot.child(firebase_username).child("xcoins").getValue().toString());
                                            firebase_email = snapshot.child(firebase_username).child("email").getValue().toString();
                                            WIN = Integer.parseInt(snapshot.child(firebase_username).child("WIN").getValue().toString());
                                            LOST = Integer.parseInt(snapshot.child(firebase_username).child("LOST").getValue().toString());
                                            reference.child(firebase_username).child("on_off").setValue(true);


                                            BD_actions database = new BD_actions(getApplicationContext());

                                            database.setUsername(username);
                                            database.setPasword(password);

                                            database.setWIN_firebase(WIN + database.getWIN());
                                            reference.child(firebase_username).child("WIN").setValue(WIN + database.getWIN());

                                            database.setLOST_firebase(LOST + database.getLOST());
                                            reference.child(firebase_username).child("LOST").setValue(LOST + database.getLOST());

                                            database.setXCoins(firebase_XCoins + database.getXCoins());
                                            database.setOn_Off(true);

                                            database.setEmail(firebase_email);


                                            Intent intent = new Intent(pageLogin.this, pageUserAccount.class);
//                                        intent.putExtra("password", database_password);
//                                        intent.putExtra("username", database_username);
//                                        intent.putExtra("XCoins",XCoins);
//                                        intent.putExtra("email", database_email);
//                                        intent.putExtra("LOST", LOST);
//                                        intent.putExtra("WIN", WIN);


                                            Log.i("password1", database.getPassword() + "");
                                            Log.i("username1", database.getUsername() + "");
                                            Log.i("XCoins1", database.getXCoins() + "");
                                            Log.i("LOST1", database.getLOST() + "");
                                            Log.i("WIN1", database.getWIN() + "");

                                            Toast.makeText(pageLogin.this, "Login successfully !", Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                            finish();
                                        } else {
                                            edt_pageLogin_password.setError("Password is wrong !");
                                            edt_pageLogin_password.requestFocus();
                                        }
                                    } else {
                                        edt_pageLogin_username.setError("Username doesn't exist !");
                                        edt_pageLogin_username.requestFocus();
                                    }
                                } else {
                                    edt_pageLogin_username.setError("Username doesn't exist !");
                                    edt_pageLogin_username.requestFocus();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }







//                if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
//                    checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if(snapshot.exists()){
//                                String usernameFromDB=snapshot.child(username).getValue(String.class);
//                                String passwordFromDB=snapshot.child(username).child(password).getValue(String.class);
//                                if(usernameFromDB.equals(username) && passwordFromDB.equals(password)){
//                                    startActivity(new Intent(pageLogin.this,MainActivity.class));
//                                }else{
//                                    edt_pageLogin_password.setError("Please verify your information");
//                                    edt_pageLogin_username.requestFocus();
//                                }
//                            }else{
//                                edt_pageLogin_username.setError("User doesn't exist !");
//                                edt_pageLogin_username.requestFocus();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//                }else{
//                    Toast.makeText(pageLogin.this, "Check your email", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }
    public boolean isConnected(){
        ConnectivityManager manager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }


}