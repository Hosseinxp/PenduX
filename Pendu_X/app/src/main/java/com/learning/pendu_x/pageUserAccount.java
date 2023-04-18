package com.learning.pendu_x;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learning.pendu_x.R;

public class pageUserAccount extends AppCompatActivity {
    ImageView img_pageUserAccount_userPic;
    ImageButton imgb_pageUserAccount_back;
    TextView txt_pageUserAccount_username,txt_pageUserAccount_wins,txt_pageUserAccount_losts,txt_pageUserAccount_emailTitle,txt_pageUserAccount_email,
            txt_pageUserAccount_phoneNumber,txt_pageUserAccount_phoneNumberTitle,txt_pageUserAccount_winsCounter,txt_pageUserAccount_lostsCounter,txt_pageUserAcoount_XCoins;
    Button btn_pageUserAccount_changeInfo,btn_pageUserAccount_changePassword,btn_pageUserAccount_signOut;
    private String username,email,password;
//    private int phoneNumber;
    private int XCoins,LOST,WIN;
    private boolean on_off;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // supprimer ActionBar et TitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_page_user_account);
        if(isConnected()==false){
            BD_actions database=new BD_actions(getApplicationContext());
            database.setOn_Off(false);
            Toast.makeText(this, "Sorry! you're not connected to Internet", Toast.LENGTH_SHORT).show();
            Intent userAccount_main=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(userAccount_main);
        }
        GetInfo();
        SetupView();
        ChangeInfo();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==1){
//            if(resultCode==RESULT_OK){
//                String newUsername=data.getStringExtra("newUsername");
//                String newEmail=data.getStringExtra("newEmail");
//                txt_pageUserAccount_username.setText(newUsername);
//                txt_pageUserAccount_email.setText(newEmail);
//                Toast.makeText(this, "Information has been modified", Toast.LENGTH_SHORT).show();
//            }else{
//                Toast.makeText(this, "An error has occurred", Toast.LENGTH_SHORT).show();
//            }
//
//            if(requestCode==2){
//                if(resultCode==RESULT_OK){
//                    String newPaswword=data.getStringExtra("newPassword");
//                    Toast.makeText(this, "Password successfully changed ", Toast.LENGTH_SHORT).show();
//                }else{
//                    Toast.makeText(this, "An error has occurred", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        }
//    }

    public void SetupView(){
        img_pageUserAccount_userPic=findViewById(R.id.img_pageUserAccount_userPic);
        imgb_pageUserAccount_back=findViewById(R.id.imgb_pageUserAccount_back);
        txt_pageUserAccount_username=findViewById(R.id.txt_pageUserAccount_username);
        txt_pageUserAccount_wins=findViewById(R.id.txt_pageUserAccount_wins);
        txt_pageUserAccount_losts=findViewById(R.id.txt_pageUserAccount_losts);
        txt_pageUserAccount_emailTitle=findViewById(R.id.txt_pageUserAccount_emailTitle);
        txt_pageUserAccount_email=findViewById(R.id.txt_pageUserAccount_email);
        txt_pageUserAccount_phoneNumber=findViewById(R.id.txt_pageUserAccount_phoneNumber);
        txt_pageUserAccount_phoneNumberTitle=findViewById(R.id.txt_pageUserAccount_phoneNumberTitle);
        txt_pageUserAccount_winsCounter=findViewById(R.id.txt_pageUserAccount_winsCounter);
        txt_pageUserAccount_lostsCounter=findViewById(R.id.txt_pageUserAccount_lostsCounter);
        txt_pageUserAcoount_XCoins=findViewById(R.id.txt_pageUserAcoount_XCoins);

        btn_pageUserAccount_changeInfo=findViewById(R.id.btn_pageUserAccount_changeInfo);
        btn_pageUserAccount_changePassword=findViewById(R.id.btn_pageUserAccount_changePassword);
        btn_pageUserAccount_signOut=findViewById(R.id.btn_pageUserAccount_signOut);
        imgb_pageUserAccount_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ON_OFF_BACK",on_off+"");
                Intent account_main_back=new Intent(pageUserAccount.this,MainActivity.class);
//                account_main_back.setAction("ACCOUNT_MAIN_BACK");
//                account_main_back.putExtra("on_off",on_off);
//                account_main_back.putExtra("username",username);
//                account_main_back.putExtra("XCoins",XCoins);
//                account_main_back.putExtra("LOST",LOST);
//                account_main_back.putExtra("WIN",WIN);
                startActivity(account_main_back);
            }
        });


        txt_pageUserAccount_username.setText(username);
        txt_pageUserAccount_email.setText(email);
        txt_pageUserAccount_lostsCounter.setText(LOST+"");
        txt_pageUserAccount_winsCounter.setText(WIN+"");

        if(String.valueOf(XCoins).length()< 3){
            txt_pageUserAcoount_XCoins.setTextSize(34);
            txt_pageUserAcoount_XCoins.setText(XCoins+"");
        }
        else if(String.valueOf(XCoins).length()> 4){
            txt_pageUserAcoount_XCoins.setTextSize(30);
            txt_pageUserAcoount_XCoins.setText(XCoins+"");
        }
        else if(String.valueOf(XCoins).length()> 5){
            txt_pageUserAcoount_XCoins.setTextSize(28);
            txt_pageUserAcoount_XCoins.setText(XCoins+"");
        }else{
            txt_pageUserAcoount_XCoins.setTextSize(22);
            txt_pageUserAcoount_XCoins.setText(XCoins+"");
        }

    }



    public void ChangeInfo(){
        btn_pageUserAccount_changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userAccount_changePassword =new Intent(pageUserAccount.this,pageModification.class);
//                userAccount_changePassword.putExtra("username",username);
//                userAccount_changePassword.putExtra("email",email);
//                startActivityForResult(userAccount_changePassword,1);
                startActivity(userAccount_changePassword);
            }
        });

        btn_pageUserAccount_changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userAccount_changePassword =new Intent(pageUserAccount.this,pageChangePassword.class);
//                userAccount_changePassword.putExtra("password",password);
//                userAccount_changePassword.putExtra("username",username);
//                startActivityForResult(userAccount_changePassword,2);
                startActivity(userAccount_changePassword);
            }
        });

        btn_pageUserAccount_signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase database=FirebaseDatabase.getInstance("https://pendu-x-4466b-default-rtdb.europe-west1.firebasedatabase.app/");
                DatabaseReference reference_onOff=database.getReference("users").child(username);
                reference_onOff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            reference_onOff.child("on_off").setValue(false);
                            reference_onOff.child("xcoins").setValue(XCoins);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.i("ERROR_onOff",error.toString()+"");
                    }
                });

                Intent userAccount_main_signOut=new Intent(pageUserAccount.this,MainActivity.class);
//                userAccount_main_signOut.setAction("SIGN_OUT");
//                userAccount_main_signOut.putExtra("XCoins",0);
//                userAccount_main_signOut.putExtra("on_off",false);
//                userAccount_main_signOut.putExtra("LOST",0);
//                userAccount_main_signOut.putExtra("WIN",0);
                startActivity(userAccount_main_signOut);
                finish();
            }
        });
    }

    public void GetInfo(){
//        Intent getLoginData =getIntent();
//        username=getLoginData.getStringExtra("username");
//        password=getLoginData.getStringExtra("password");
//        email=getLoginData.getStringExtra("email");
//        XCoins=getLoginData.getIntExtra("XCoins",XCoins);
//        LOST=getLoginData.getIntExtra("LOST",LOST);
//        WIN=getLoginData.getIntExtra("WIN",WIN);

        BD_actions database=new BD_actions(getApplicationContext());
        username=database.getUsername();
        email=database.getEmail();
        WIN=database.getWIN();
        LOST=database.getLOST();
        XCoins=database.getXCoins();


        Log.i("USERNAME_GET",username);
        Log.i("EMAIL_GET",email);
        Log.i("XCoins_GET",XCoins+"");
//        Log.i("PASSWROD_GET",password+"");
//        Log.i("ON_OFF_GET",on_off+"");
        Log.i("LOST",LOST+"");
        Log.i("WIN",WIN+"");
    }

    public boolean isConnected(){
        ConnectivityManager manager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }
}