package com.learning.pendu_x;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.learning.pendu_x.R;

public class pageChangePassword extends AppCompatActivity {
    Button btn_pageChangeModification_confirm;
    ImageButton imgb_pageChangePassword_back;
    EditText edt_pageChangePassword_confirmPassword,edt_pageChangePassword_newPassword,edt_pageChangePassword_oldPassword;
    private String oldPassword,newPassword,confirmPassword,username;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_page_change_password);

        if(isConnected()==false){
            BD_actions database=new BD_actions(getApplicationContext());
            database.setOn_Off(false);
            Toast.makeText(this, "Sorry! you're not connected to internet", Toast.LENGTH_SHORT).show();
            Intent changePassword_main=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(changePassword_main);
        }
        GetInfo();
        SetupView();
        ChangePassword();
    }


    public void SetupView(){
        edt_pageChangePassword_confirmPassword=findViewById(R.id.edt_pageChangePassword_confirmPassword);
        edt_pageChangePassword_newPassword=findViewById(R.id.edt_pageChangePassword_newPassword);
        edt_pageChangePassword_oldPassword=findViewById(R.id.edt_pageChangePassword_oldPassword);
        imgb_pageChangePassword_back=findViewById(R.id.imgb_pageModification_back);
        btn_pageChangeModification_confirm=findViewById(R.id.btn_pageChangeModification_confirm);
        btn_pageChangeModification_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent changePassword_userAccount=new Intent(getApplicationContext(),pageUserAccount.class);
//                Intent getIntent =getIntent();
//                setResult(RESULT_CANCELED,getIntent);
                startActivity(changePassword_userAccount);
                finish();
            }
        });
    }





    public void ChangePassword(){
        btn_pageChangeModification_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {






                //oldPassword_empty= oldPassword vide ?
                boolean oldPassword_empty_verification;
                if(TextUtils.isEmpty(edt_pageChangePassword_oldPassword.getText().toString())){
                    oldPassword_empty_verification=false;
                    edt_pageChangePassword_oldPassword.setError("Enter your old password !");
                    edt_pageChangePassword_oldPassword.requestFocus();
                }else{
                    oldPassword_empty_verification=true;
                    oldPassword=edt_pageChangePassword_oldPassword.getText().toString();
                }

                //newPassword_empty= newPassword vide ?
                boolean newPassword_empty_verification;
                if(TextUtils.isEmpty(edt_pageChangePassword_newPassword.getText().toString())){
                    newPassword_empty_verification=false;
                    edt_pageChangePassword_oldPassword.setError("Enter your new password !");
                    edt_pageChangePassword_oldPassword.requestFocus();
                }else{
                    newPassword_empty_verification=true;
                    newPassword=edt_pageChangePassword_newPassword.getText().toString();
                }


                //confirmPassword_empty= confirmPassword vide ?
                boolean confirmPassword_empty_verification;
                if(TextUtils.isEmpty(edt_pageChangePassword_confirmPassword.getText())){
                    confirmPassword_empty_verification=false;
                    edt_pageChangePassword_oldPassword.setError("Enter your new password !");
                    edt_pageChangePassword_oldPassword.requestFocus();
                }else{
                    confirmPassword_empty_verification=true;
                    confirmPassword=edt_pageChangePassword_confirmPassword.getText().toString();
                }



                // same_oldPassword = pour vérifier si l'ancien password de login et l'ancien password entré sont parreils.
                boolean different_oldPassword_newPassword_verification;
                if(oldPassword.equals(newPassword)){
                    different_oldPassword_newPassword_verification=false;
                    edt_pageChangePassword_newPassword.setError("Your password shouldn't match the old one");
                    edt_pageChangePassword_newPassword.requestFocus();

                }else{
                    different_oldPassword_newPassword_verification=true;
                }

                Log.i("same_oldPassword",different_oldPassword_newPassword_verification+"");



                // confirm_password= si le nouveau et l'ancien mot de passe sont parreil

                boolean same_new_confirm_Password_verification = false;
                if(different_oldPassword_newPassword_verification) {
                    if (!(newPassword.equals(confirmPassword))) {
                        same_new_confirm_Password_verification = false;
                        edt_pageChangePassword_confirmPassword.setError("Your password doesn't match");
                        edt_pageChangePassword_confirmPassword.requestFocus();
                    } else {
                        same_new_confirm_Password_verification = true;
                    }
                }


                Log.i("same_new_confirm_Pa",same_new_confirm_Password_verification+"");

                if(oldPassword_empty_verification && newPassword_empty_verification && confirmPassword_empty_verification && different_oldPassword_newPassword_verification && same_new_confirm_Password_verification){
                    DatabaseReference reference= FirebaseDatabase.getInstance("https://pendu-x-4466b-default-rtdb.europe-west1.firebasedatabase.app").getReference("users/"+username);
                    BD_actions database=new BD_actions(getApplicationContext());

                    database.setPasword(newPassword);
                    reference.child(username).child("password").setValue(newPassword);
                    Intent changePaswword_userDetail=new Intent(getApplicationContext(),pageUserAccount.class);
//                    setResult(RESULT_OK,setIntent);
                    startActivity(changePaswword_userDetail);
                    finish();
                }

            }
        });

    }
    public void GetInfo(){
        BD_actions database=new BD_actions(getApplicationContext());
        username=database.getUsername();
        oldPassword=database.getPassword();
    }

    public boolean isConnected(){
        ConnectivityManager manager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }



}