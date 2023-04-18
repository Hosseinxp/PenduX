package com.learning.pendu_x;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learning.pendu_x.R;

public class pageModification extends AppCompatActivity {
    TextView txt_pageModification_emailTitle,txt_pageModification_usernameTitle;
    EditText edt_pageModification_username,edt_pageModification_email;
    ImageButton imgb_pageModification_back;
    ImageView img_pageModification_userPic;
    Button btn_pageModification_confirmUsernameChange,btn_pageModification_confirmEmailChange;

    private String username,email,password;
    private int XCoins,WIN,LOST;
    private boolean on_off;

    private DatabaseReference reference = FirebaseDatabase.getInstance("https://pendu-x-4466b-default-rtdb.europe-west1.firebasedatabase.app").getReference("users");


    View.OnClickListener back =new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent modification_userAccount=new Intent(getApplicationContext(),pageUserAccount.class);
//            Intent getIntent=getIntent();
//            setResult(RESULT_CANCELED,getIntent);
//            finish();
            startActivity(modification_userAccount);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_page_modification);
        if(isConnected()==false){
            BD_actions database=new BD_actions(getApplicationContext());
            database.setOn_Off(false);
            Toast.makeText(this, "Sorry! you're not connected to Internet", Toast.LENGTH_SHORT).show();
            Intent modification_main=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(modification_main);
        }
        GetInfo();
        SetupView();
        ChangeUsername();
        ChangeEmail();
    }

    public void SetupView(){
        txt_pageModification_emailTitle=findViewById(R.id.txt_pageModification_emailTitle);
        txt_pageModification_usernameTitle=findViewById(R.id.txt_pageModification_usernameTitle);
        edt_pageModification_username=findViewById(R.id.edt_pageModification_username);
//        img_pageModification_userPic=findViewById(R.id.img_pageModification_userPic);
        edt_pageModification_email=findViewById(R.id.edt_pageModification_email);
//        btn_pageModification_confirm=findViewById(R.id.btn_pageModification_confirm);
        imgb_pageModification_back=findViewById(R.id.imgb_pageModification_back);
        btn_pageModification_confirmUsernameChange=findViewById(R.id.btn_pageModification_confirmUsernameChange);
        btn_pageModification_confirmEmailChange=findViewById(R.id.btn_pageModification_confirmEmailChange);


        imgb_pageModification_back.setOnClickListener(back);

        edt_pageModification_username.setHint("old username :\n"+username);
        edt_pageModification_email.setHint("old email:\n"+email);
        }



        public void ChangeUsername(){

            btn_pageModification_confirmUsernameChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Helper_UsernameVerification helper_usernameVerification=new Helper_UsernameVerification();
                    String entered_username=edt_pageModification_username.getText().toString().trim();


                    boolean same_username;
                    if(entered_username.toLowerCase().equals(username.toLowerCase())){
                        same_username=false;
                        edt_pageModification_username.setError("This username is the same as your last one!");
                        edt_pageModification_username.requestFocus();
                    }else{
                        same_username=true;
                    }
                    Log.i("username_bool",same_username+"");
                    Log.i("edt_username",entered_username);
                    Log.i("username",username);



                    helper_usernameVerification.setUser_status(true);
                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for(DataSnapshot everyUser:snapshot.getChildren()) {
                                    if (everyUser.child("username").getValue().toString().toLowerCase().equals(username)){
                                        helper_usernameVerification.setUser_status(false);
                                        break;
                                    }
                                }
                                if(!helper_usernameVerification.getUser_status()){
                                    edt_pageModification_username.setError("username already exists !");
                                    edt_pageModification_username.requestFocus();
                                }else{
                                    if(same_username) {
                                        Log.i("same_username_LOG",same_username+"");
                                        BD_actions database = new BD_actions(getApplicationContext());
                                        UserDetails editedUser = new UserDetails(entered_username, database.getPassword(), database.getEmail(), database.getXCoins(), database.getWIN(), database.getLOST(), database.getOn_Off());
                                        database.setUsername(entered_username);
                                        reference.child(username).removeValue();
                                        reference.child(entered_username).setValue(editedUser);

                                        Intent modification_userAccount = new Intent(getApplicationContext(),pageUserAccount.class);
                                        startActivity(modification_userAccount);
                                    }else{
                                        edt_pageModification_username.setError("This username is the same as your last one!");
                                        edt_pageModification_username.requestFocus();
                                    }
                                }

                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

        }});
    }



        public void ChangeEmail(){
            Helper_EmailVerification helper_emailVerification =new Helper_EmailVerification();
            btn_pageModification_confirmEmailChange.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String entered_email=edt_pageModification_email.getText().toString().trim();
                    boolean same_email;
                    if(entered_email!=email){
                        same_email=true;
                    }else{
                        edt_pageModification_email.setError("This email is the same as your last one!");
                        edt_pageModification_email.requestFocus();
                        same_email=false;
                    }

                    boolean email_pattern;
                    if(Patterns.EMAIL_ADDRESS.matcher(entered_email).matches()){
                        email_pattern=true;
                    }else{
                        edt_pageModification_email.setError("Check your email pattern");
                        edt_pageModification_email.requestFocus();
                        email_pattern=false;
                    }

                    Log.i("email_bool",same_email+"");
                    Log.i("edt_email",entered_email);
                    Log.i("email",email);


                    helper_emailVerification.setEmail_status(true);

                    reference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.exists()){
                                for(DataSnapshot everyUser:snapshot.getChildren()) {
                                    if (everyUser.child(username).child("email").getValue().toString().toLowerCase().equals(email.toLowerCase())) {
                                        helper_emailVerification.setEmail_status(false);
                                        break;
                                    }
                                }
                                if(!helper_emailVerification.getEmail_status()){
                                    edt_pageModification_email.setError("email already exists!");
                                    edt_pageModification_email.requestFocus();
                                }else{
                                    if(same_email && email_pattern) {
                                        Log.i("same_email_LOG",same_email+"");
                                        Log.i("email_pattern_LOG",email_pattern+"");

                                        BD_actions database = new BD_actions(getApplicationContext());

                                        database.setEmail(entered_email);
                                        reference.child(username).child("email").setValue(entered_email);

                                        Intent modification_userAccount = new Intent(getApplicationContext(),pageUserAccount.class);
                                        startActivity(modification_userAccount);
                                    }

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }
            });



        }

    public void GetInfo(){
//        Intent getIntent=getIntent();
//        username=getIntent.getStringExtra("username");
//        email=getIntent.getStringExtra("email");
//        ID=username.toLowerCase();

        BD_actions database=new BD_actions(getApplicationContext());
        username=database.getUsername();
        email=database.getEmail();
//        password=database.getPassword();
//        XCoins=database.getXCoins();
//        WIN=database.getWIN();
//        LOST=database.getLOST();
//        on_off=database.getOn_Off();



        Log.i("USERNAME",username);
        Log.i("EMAIL",email);

    }

    public boolean isConnected(){
        ConnectivityManager manager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }




//        else if (("old username :\\n"+username).length()>18 && ("old username :\\n"+username).length()<23 ) {
//            edt_pageModification_username.setHint("@string/hint_18");
//        }


//    public void Verification(){
//        btn_pageModification_confirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String tempUsername,tempEmail,databaseUsername,databasePassword;
//
//                tempUsername=edt_pageModification_username.getText().toString();
//                tempEmail=edt_pageModification_email.getText().toString();
//
//                Query getInfo=reference.orderByChild("username").equalTo(username);
//
//
//
//                boolean userVerification=false;
//                if(getInfo.){
//
//
//                }
//                }else if (tempUsername.equals(username)){
//                    edt_pageModification_username.setError("Your new username is the same!");
//                    edt_pageModification_username.requestFocus();
//            }
//        });
//
//    }



}