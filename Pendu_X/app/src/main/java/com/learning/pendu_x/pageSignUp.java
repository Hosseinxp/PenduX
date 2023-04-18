package com.learning.pendu_x;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.learning.pendu_x.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class pageSignUp extends AppCompatActivity {
    EditText edt_pageSignUp_username,edt_pageSignUp_password,edt_pageSignUp_email,edt_pageSignUp_confirmPassword;//edt_pageSignUp_phoneNumber;
    Button btn_pageSignUp_createAccount;
    private String password,confirmPassword,email,username;
    private int XCoins,LOST,WIN;
    ImageButton imgb_pageSignUp_back;


    FirebaseDatabase dataBase =FirebaseDatabase.getInstance("https://pendu-x-4466b-default-rtdb.europe-west1.firebasedatabase.app");


//    long phoneNumber;
//    ProgressBar progressBar_pageSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // supprimer ActionBar et TitleBar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_page_sign_up);
        if (isConnected()==false){
            BD_actions database=new BD_actions(getApplicationContext());
            database.setOn_Off(false);
            Toast.makeText(this, "Sorry!you're not connected to Internet", Toast.LENGTH_SHORT).show();
            Intent signUp_main=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(signUp_main);

        }

        SetupView();
        GetInfo();
        setInfo();
    }

    public void SetupView(){
        edt_pageSignUp_username=findViewById(R.id.edt_pageSignUp_username);
        edt_pageSignUp_email=findViewById(R.id.edt_pageSignUp_email);
        edt_pageSignUp_password=findViewById(R.id.edt_pageSignUp_password);
        edt_pageSignUp_confirmPassword=findViewById(R.id.edt_pageSignUp_confirmPassword);
        //edt_pageSignUp_phoneNumber=findViewById(R.id.edt_pageSignUp_phoneNumber);
        btn_pageSignUp_createAccount=findViewById(R.id.btn_pageSignUp_createAccount);
        imgb_pageSignUp_back=findViewById(R.id.imgb_pageSignUp_back);
//        progressBar_pageSignUp=findViewById(R.id.progressBar_pageSignUp);


        imgb_pageSignUp_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUp_connexion=new Intent(pageSignUp.this,pageConnexion.class);
                signUp_connexion.putExtra("XCoins",XCoins);
                signUp_connexion.putExtra("LOST",LOST);
                signUp_connexion.putExtra("WIN",WIN);
                startActivity(signUp_connexion);
                finish();
            }
        });

    }


    /* On réccuper les informations saisies par l'utilisateur par des EditText et on les initialise dans les variables username,email,password,confirmPassword
       Ensuite, nous mettons des conditions pour vérifier si les infos saisies sont correct ou pas!
       Vérificaton est faite par quelles condions?
            *username_verification : si username est déjà saisie ou pas (TextView est vide ou pas )
            *email_verification : si email est déjà saisie ou pas (TextView est vide ou pas )
            *passwrod_verification : si password est déjà saisie ou pas (TextView est vide ou pas )
            *confirmPasswrod_verification : si confirmPassword est déjà saisie ou pas (TextView est vide ou pas )
            *password_length_verification : si la longeur du password est plus de 8 caracters ou pas
            *digit_verification ( resultat at FALSE ): si le password comprend les chiffres ou pas
            *special_character_verification: si le password contient les caracters specials ou pas
            *emailPattern_verification : si le mail saisie correspond au pattern d'un mail ( EX: hello@yahoo.com)
     */
    public void setInfo(){
        btn_pageSignUp_createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Helper_EmailVerification helper_emailVerification=new Helper_EmailVerification();
                Helper_UsernameVerification helper_usernameVerification=new Helper_UsernameVerification();

                username= edt_pageSignUp_username.getText().toString().trim();
                email=edt_pageSignUp_email.getText().toString().trim().toLowerCase();
                password=edt_pageSignUp_password.getText().toString().trim();
                confirmPassword=edt_pageSignUp_confirmPassword.getText().toString().trim();




                // Vérification de 'username'
                boolean username_verification=true;
                if(TextUtils.isEmpty(username)){
                    Toast.makeText(pageSignUp.this, "Please enter your Username", Toast.LENGTH_SHORT).show();
                    edt_pageSignUp_username.setError("Username is empty");
                    edt_pageSignUp_username.requestFocus();
                    username_verification=false;
                }else{
                    username_verification=true;
                }



                // Vérification de 'email'
                boolean email_verification=true;
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(pageSignUp.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    edt_pageSignUp_email.setError("Username is empty !");
                    edt_pageSignUp_email.requestFocus();
                    email_verification=false;
                }else{
                    email_verification=true;
                }



                // Vérification de 'password'
                boolean passwrod_verification=true;
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(pageSignUp.this, "Please enter your Password", Toast.LENGTH_SHORT).show();
                    edt_pageSignUp_password.setError("Password is empty");
                    edt_pageSignUp_password.requestFocus();
                    passwrod_verification=false;
                }else{
                    passwrod_verification=true;
                }

                // Vérification de 'confirmPassword'
                boolean confirmPasswrod_verification=true;
                if (TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(pageSignUp.this, "Please Confirm your password", Toast.LENGTH_SHORT).show();
                    edt_pageSignUp_confirmPassword.setError("Confirm your password please");
                    edt_pageSignUp_confirmPassword.requestFocus();
                    confirmPasswrod_verification=false;
                }else{
                    confirmPasswrod_verification=true;
                }

                // Vérification d'égalisation du password et confirmPassword
                boolean password_confirmPassword=true;
                if(!confirmPassword.equals(password)){
                    Toast.makeText(pageSignUp.this, "Your password doesn't match !", Toast.LENGTH_SHORT).show();
                    edt_pageSignUp_confirmPassword.setError("Confirm your password please");
                    edt_pageSignUp_confirmPassword.requestFocus();
                    password_confirmPassword=false;
                }else{
                    password_confirmPassword=true;
                }


                // Vérification dela taille 'password'
                boolean password_length_verification=true;
                if(password.length() <8 ){
                    Toast.makeText(pageSignUp.this, "Your password is fewer than 8 digits.", Toast.LENGTH_SHORT).show();
                    edt_pageSignUp_password.setError("Enter a longer Password");
                    edt_pageSignUp_password.requestFocus();
                    password_length_verification=false;
                }else{
                    password_length_verification=true;
                }



//                else if(!(String.valueOf(phoneNumber).length() ==9)){
//                    Toast.makeText(pageSignUp.this, "your phone number is not correct", Toast.LENGTH_SHORT).show();
//                    edt_pageSignUp_phoneNumber.setError("check you phone number");
//                    edt_pageSignUp_phoneNumber.requestFocus();
//                }


                        // Vérification du password --> digit_verification
                boolean digit_verification=false;
                for(char element: password.toCharArray()){
                    if(Character.isDigit(element)){
                        digit_verification=true;
                        break;
                    }
                }
                if(!digit_verification){
                    Toast.makeText(pageSignUp.this, "your Password doesn't contain any number digits.", Toast.LENGTH_SHORT).show();
                    edt_pageSignUp_password.setError("Add digits to your password");
                    edt_pageSignUp_password.requestFocus();
                }


                            // Vérification du password --> special_character
                boolean special_character_verification=false;
                String specialChars = "!@#$%^&*()_+-=[]{};':\",./<>?";
                for(char element: password.toCharArray()){
                    if(specialChars.contains(Character.toString(element))){
                        special_character_verification=true;
                        break;
                    }
                }

                if (!special_character_verification){
                    Toast.makeText(pageSignUp.this, "your Password doesn't contain any #@-+&", Toast.LENGTH_SHORT).show();
                    edt_pageSignUp_password.setError("Add special characters to your password");
                    edt_pageSignUp_password.requestFocus();
                }



                            // Vérification de Pattern du mail
                boolean emailPattern_verification=true;
                if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    Toast.makeText(pageSignUp.this, "Your email is not valid", Toast.LENGTH_SHORT).show();
                    edt_pageSignUp_email.setError("Check you email address");
                    edt_pageSignUp_email.requestFocus();
                    emailPattern_verification=false;
                }else{
                    emailPattern_verification=true;
                }

                Log.i("EMAIL",email);



                boolean finalEmail_verification = email_verification;
                boolean finalEmailPattern_verification = emailPattern_verification;
                boolean finalPassword_length_verification = password_length_verification;
                boolean finalSpecial_character_verification = special_character_verification;
                boolean finalDigit_verification = digit_verification;
                boolean finalPassword_confirmPassword = password_confirmPassword;
                boolean finalConfirmPasswrod_verification = confirmPasswrod_verification;
                boolean finalPasswrod_verification = passwrod_verification;
                boolean finalUsername_verification = username_verification;



                    DatabaseReference ref_username_emailSearch_inUsersBranch_register = dataBase.getReference();
                    ref_username_emailSearch_inUsersBranch_register.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // Helper_UsernameVerification helper_usernameVerification2=new Helper_UsernameVerification();
                            if (snapshot.child("users").exists()) {
                                Log.i("SNAPSHOT1", snapshot + "");
                                helper_usernameVerification.setUser_status(true);
                                for (DataSnapshot everyusername : snapshot.child("users").getChildren()) {
                                    if (everyusername.getKey().toLowerCase().equals(username.toLowerCase())) {
                                        helper_usernameVerification.setUser_status(false);
                                        break;
                                    }
                                }
                                Log.i("INSIDE", helper_usernameVerification.getUser_status() + "");
                                if (helper_usernameVerification.getUser_status() == false) {
                                    edt_pageSignUp_username.setError("Username already exists !");
                                    edt_pageSignUp_username.requestFocus();
                                }


                                helper_emailVerification.setEmail_status(true);
                                for (DataSnapshot everyUserDetail : snapshot.child("users").getChildren()) {
                                    if (everyUserDetail.child("email").getValue().equals(email)) {
                                        Log.i("EMAIL_INSIDE", everyUserDetail.child("email").getValue().equals(email) + "");
                                        helper_emailVerification.setEmail_status(false);
                                        break;
                                    }
                                }
                                if (helper_emailVerification.getEmail_status() == false) {
                                    edt_pageSignUp_email.setError("Email already exists!");
                                    edt_pageSignUp_email.requestFocus();
                                }

//                            Log.i("USERRNAMEVerification",helper_usernameVerification.getUser_status()+"");
//                            Log.i("EMAILVerification",helper_emailVerification.getEmail_status()+"");
                                if (finalEmail_verification && finalEmailPattern_verification && finalPassword_length_verification && finalSpecial_character_verification
                                        && finalDigit_verification && finalPassword_confirmPassword && finalConfirmPasswrod_verification && finalPasswrod_verification && finalUsername_verification
                                        && helper_emailVerification.getEmail_status() && helper_emailVerification.getEmail_status()) {
                                    try {
                                        RegisterUser();
                                    } catch (Exception e) {
                                        Log.i("Algorithm", e.getMessage() + "");
                                    }
                                }
                            } else {
                                Log.i("SNAPSHOT2", snapshot + "");
                                if (finalEmail_verification && finalEmailPattern_verification && finalPassword_length_verification && finalSpecial_character_verification
                                        && finalDigit_verification && finalPassword_confirmPassword && finalConfirmPasswrod_verification && finalPasswrod_verification && finalUsername_verification) {
                                    try {
                                        RegisterUser();
                                    } catch (Exception e) {
                                        Log.i("Algorithm", e.getMessage() + "");
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Log.i("ERROR", error.toString() + "");
                        }

                    });







                Log.i("email_verification",email_verification+"");
                Log.i("emailPattern",emailPattern_verification+"");
                Log.i("password length",password_length_verification+"");
                Log.i("special character",special_character_verification+"");
                Log.i("digit_verification",digit_verification+"");
                Log.i("password confirm",password_confirmPassword+"");

            }});


    }

    public void RegisterUser() throws NoSuchAlgorithmException {

        DatabaseReference reference=dataBase.getReference("users");
        // SHA -> Secure Hash Algorithm
        // MD5 -> Message Digest
        /* Ici, nous voulons faire le HASHing afin de hasher le mot de pass et l'envoyer à la base de données
            Firebase realtime database

           MessageDigest est l'objet contenant les methode qui nous permet de faire le hashing.
          Donc on cré un objet du type "MessageDigest" et on réccupère une instance du type de sécurité demandé

          Ensuite, nous envyons les bytes de notre mot de passe à l'objet "messageDigest" que avons créé
          afin de faire le hashing à l'aide de la méthode " .digest()". les résultats obtenus par la methode ".digest()"
          sont du type byte[] (byte array ! ), donc on initialise les résultats obtenus dans une variable
          du type byte[] qui s'appelle "resultByteArray".

          Maintenant, nous voulons transfomer le résultat du type byte[] ( byte array ) en String, mais ce
          n'est pas possbile! car le format des bytes dans "resultByteArray" sont differents ( en hexadecimale ou ...)
          donc il nous faut un objet 'StringBuilder" et comme ca, nous pouvons créer une varable String !


         */
        MessageDigest messageDigest =MessageDigest.getInstance("SHA");
        messageDigest.update(password.getBytes());
        byte[] resultByteArray =messageDigest.digest();
        StringBuilder sb= new StringBuilder();
        for(byte byte_in_array:resultByteArray){
            sb.append(String.format("%2x",byte_in_array));      // "%2x" est pour transfomer le format du byte en format du String.
        }

        password=sb.toString();


        Log.i("PASSWORDHASHED",password+"");
        UserDetails userDetails=new UserDetails(username.toLowerCase(),password,email.toLowerCase(),XCoins,WIN,LOST,false);
        reference.child(username).setValue(userDetails);


        Toast.makeText(this, "SignUp is done successfully !", Toast.LENGTH_SHORT).show();
        Intent signUp_login=new Intent(pageSignUp.this,pageLogin.class);
        startActivity(signUp_login);
    }

    public void GetInfo(){
//        Intent getInfo=getIntent();
//        XCoins=getInfo.getIntExtra("XCoins",XCoins);
//        LOST=getInfo.getIntExtra("LOST",LOST);
//        WIN=getInfo.getIntExtra("WIN",WIN);

        BD_actions database=new BD_actions(getApplicationContext());
        XCoins=database.getXCoins();
        WIN=database.getWIN();
        LOST=database.getLOST();

    }


    public boolean isConnected(){
        ConnectivityManager manager=(ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return manager.getActiveNetworkInfo() != null && manager.getActiveNetworkInfo().isConnectedOrConnecting();
    }


}
















//                if(usernameExistance_verification==true){
//                        edt_pageSignUp_username.setError("User already exists !");
//                        edt_pageSignUp_username.requestFocus();
//                }else {
//                    usernameExistance_verification=true;
//                    if(emailExistance_verification==true){
//                        edt_pageSignUp_email.setError("User already exists !");
//                        edt_pageSignUp_email.requestFocus();
//                    }else {
//                        emailExistance_verification=true;
//                    }
//                }




    //                AtomicBoolean usernameExistance_verification = new AtomicBoolean(false);
////                String tempUsernameLowerCase,tempEmailLowerCase;
////                tempUsernameLowerCase=username.toLowerCase();
//
//                Query checkUsername=reference.orderByChild("username").equalTo(username);
//
//                boolean finalEmail_verification = email_verification;
//                boolean finalEmailPattern_verification = emailPattern_verification;
//                boolean finalPassword_length_verification = password_length_verification;
//                boolean finalSpecial_character_verification = special_character_verification;
//                boolean finalDigit_verification = digit_verification;
//                boolean finalPassword_confirmPassword = password_confirmPassword;
//                boolean finalConfirmPasswrod_verification = confirmPasswrod_verification;
//                boolean finalPasswrod_verification = passwrod_verification;
//                boolean finalUsername_verification = username_verification;

//                checkUsername.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.exists()) {
//                            edt_pageSignUp_username.setError("User already exists !");
//                            edt_pageSignUp_username.requestFocus();
//                            usernameExistance_verification.set(false);
//                        }
//                        else{
//                            Log.i("USERNAME",String.valueOf(usernameExistance_verification.get()));
//                            usernameExistance_verification.set(true);
////                            String tempEmailLowerCase=snapshot.child(username).child("email").getValue().toString().toLowerCase();
////                            if(tempEmailLowerCase.equals(email.toLowerCase())){
////                                edt_pageSignUp_email.setError("email already exists !");
////                                edt_pageSignUp_email.requestFocus();
////                                emailExistance_verification.set(false);
////                            }else{
////                                emailExistance_verification.set(true);
////                            }
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });


//                AtomicBoolean emailExistance_verification = new AtomicBoolean(false);
//                Query checkEmail=reference.child(username).orderByChild("email").equalTo(email);
//                checkEmail.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        if(snapshot.exists()){
//                            edt_pageSignUp_email.setError("email already exists !");
//                            edt_pageSignUp_email.requestFocus();
//                            emailExistance_verification.set(false);
//                        }else{
//                            emailExistance_verification.set(true);
//                        }
//
//                        Log.i("EMAIL",String.valueOf(usernameExistance_verification.get()));

//                    }

//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });







    // c'est une méthode qui permet de voir si le amil est valide ou pas.
//    private boolean EmailCaracterVerification(String email) {
//        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
//        return email.matches(emailPattern);
//    }





//                if(!(EmailCaracterVerification(email))){
//                        Toast.makeText(pageSignUp.this, "Your email is not correct", Toast.LENGTH_SHORT).show();
//                        edt_pageSignUp_email.setError("Enter a valid email");
//                        edt_pageSignUp_email.requestFocus();
//                        }

//    private boolean isValidEmail(String email) {
//        boolean isValid = false;
//        try {
//            Properties props = new Properties();
//            props.put("mail.smtp.host", "smtp.gmail.com");       // change to your email provider's SMTP server
//            Session session = Session.getInstance(props, null);
//            InternetAddress address = new InternetAddress(email);
//            address.validate();
//            Transport transport = session.getTransport("smtp");
//            transport.connect();
//            Transport.send(new MimeMessage(session));
//            transport.close();
//            isValid = true;
//        } catch (AddressException e) {
//            e.printStackTrace();
//        } catch (MessagingException e) {
//            e.printStackTrace();
//        }
//        return isValid;
//    }
//
    //Connexion au FireBase



/* pour vérifier les mails, nous avons ajouté un dependancy à build.gradle

        implementation 'com.sun.mail:android-mail:1.6.3'



 */

//                boolean number_verification=false;
//                for(char element:password.toCharArray()){
//                    if (Character.isDigit(element)){
//                        number_verification=true;
//                        break;
//                    }
//                }

//                boolean lowerCase_verification=false;
//                for(char element:password.toCharArray()){
//                    if(Character.isLowerCase(element)){
//                        lowerCase_verification=true;
//                        break;
//                    }
//                }


