package com.learning.pendu_x;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BD_actions extends SQLiteOpenHelper {

    private Context context;
    private static String BD_NAME= "BD_PenduX.db";
//    private static String packageName="com.learning.pendu_x";
//    private static String BD_PATH="/data/data/"+packageName+"/databases/";

    private static final int BD_VERSION=1;


    public BD_actions(@Nullable Context context) {
        super(context, BD_NAME, null, BD_VERSION);
        this.context=context;
//        packageName=context.getPackageName();


//        boolean BD_EXISTANCE=CheckDatabase();
//        try {
//            if(BD_EXISTANCE){
//                OpenDatabase();
//            }else{
//                //this.getReadableDatabase(); nous donne la permission de juste lire la base de données étrangère
//                this.getReadableDatabase();
//                onCreate(context);
//            }
//        }catch (Exception e){
//            throw new Error(e+"");
//        }


    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable="CREATE TABLE TB_Info("+
                " ID INTEGER PRIMARY KEY NOT NULL DEFAULT(1),"+
                " Username VARCHAR(20)," +
                " Password VARCHAR(255)," +
                " XCoins INTEGER NOT NULL DEFAULT(0),"+
                " WIN INTEGER NOT NULL DEFAULT(0)," +
                " LOST INTEGER NOT NULL DEFAULT(0)," +
                " on_off INTEGER NOT NULL DEFAULT(0)," +
                " Email VARCHAR(255)" +
                ");";
        sqLiteDatabase.execSQL(createTable);

        try {
            String insertBasics="INSERT INTO TB_Info (ID,Username,Password,XCoins,WIN,LOST,on_off,Email) VALUES ('1', null, null,'0','0','0','0' , null);";
            sqLiteDatabase.execSQL(insertBasics);
        }catch (Exception e){
            throw new Error(e+"");
        }


    }

    public void setBasics(){
        SQLiteDatabase sqLiteDatabase= this.getWritableDatabase();
        String insertBasics="INSERT INTO Info (Username,Password,XCoins,WIN,LOST,on_off,Email) VALUES (null, null,'0','0','0','0' , null);";
        sqLiteDatabase.execSQL(insertBasics);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
//        if(newVersion<oldVersion){
//            try {
//                CopyDatabase();
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }



//    private boolean CheckDatabase(){
//        boolean flag=false;
//        try{
////            String path=BD_PATH+BD_NAME;
//            File file=new File(BD_PATH);
//            flag=file.exists();
//        }catch (SQLException e){
//            throw new Error("DATA BASE DOESN'T exist!");
//        }
//        return flag;
//    }



//    public void OpenDatabase() throws SQLException{
//        try {
//            String path = BD_PATH + BD_NAME;
//            SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
//        }catch (Exception e){
//            throw new Error(e+"");
//        }
//    }



//    private void CreateDatabase(){
//        try{
//            CopyDatabase();
//        }catch (Exception e){
//            throw new Error(e+"");
//        }
//    }


//    public void CopyDatabase() throws IOException {
//        try {
//            // InputStream nous permet de lire et écrire des données
//            InputStream inputStream = context.getAssets().open(BD_NAME);
//
//            Log.i("inputStream", inputStream + "");
//
//            // OutputStream nous permet d'écrire des données
//            OutputStream outputStream = new FileOutputStream(BD_PATH);
//            Log.i("outputStream", outputStream + "");
//
//
//            byte[] buffer = new byte[1024];
//            int len;
//            // mettre les infos de valeur d'un mb(megabyte) dans le buffer
//            while ((len = inputStream.read(buffer)) > 0) {
//                outputStream.write(buffer, 0, len);
//            }
//            outputStream.flush();
//            outputStream.close();
//            inputStream.close();
//        }catch(Exception e){
//            throw new Error(e+"");
//        }
//    }






    public String getUsername(){
        String username=null;
        try {
            String querry = "Select Username From TB_Info";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(querry, null);
            Log.i("cursor",cursor.getCount()+"");
            if (cursor.moveToFirst()) {
                username = cursor.getString(0);  // ca commence par 0 et le premier (1) est pour username
            }
            db.close();

        }catch (Exception e){
            throw new Error(e);
        }
        return username;
    }

    public boolean setUsername(String Username){
        String newUsername=Username;
        try{
            String querry="Update TB_Info set Username='"+newUsername+"'";
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL(querry);
            db.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }



    public String getPassword(){
        String password=null;
        String querry="Select password From TB_Info";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(querry,null);
        if (cursor.moveToFirst()) {
            password = cursor.getString(0);
        }
        db.close();
        return password;
    }

    public boolean setPasword(String password){
        String newPassword=password;
        try{
            String querry="Update TB_Info set password='"+newPassword+"'";
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL(querry);
            db.close();
            return true;

        }catch (Exception e){
            return false;
        }
    }




    public int getXCoins(){
        int XCoins=0;
        String querry="Select XCoins From TB_Info";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(querry,null);
        if (cursor.moveToFirst()) {
            XCoins = cursor.getInt(0);
        }
        db.close();
        return XCoins;
    }


    public boolean setXCoins(int XCoins){
        int newXCoins=XCoins;
        try{
            String querry="Update TB_Info set XCoins='"+newXCoins+"'";
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL(querry);
            db.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }







    /* WIN  -> getWin() : réccuperation du nombre de gagne de la base de données
               setWIN_firebase() : initialisation le nombre de gagne auprès de firebase, dans la base de données (c'est après Login)
               setWIN_gameDone()  : Incrementation et initialisation de nouveau nombre de gagne dans la base de données

     */

    public int getWIN(){
        int win=0;
        String querry="Select WIN From TB_Info";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(querry,null);
        if (cursor.moveToFirst()) {
            win = cursor.getInt(0);
        }
        db.close();
        return win;
    }

    public boolean setWIN_firebase(int win){
        int newWIN=win;
        newWIN+=getWIN();
        try{
            String querry="Update TB_Info set WIN='"+newWIN+"'";
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL(querry);
            db.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean setWIN_gameDone(){
        int newWIN=getWIN()+1;
        try{
            String querry="Update TB_Info set WIN='"+newWIN+"'";
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL(querry);
            db.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }






    public int getLOST(){
        int lost=0;
        String querry="Select LOST From TB_Info";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(querry,null);
        if (cursor.moveToFirst()) {
            lost = cursor.getInt(0);
        }
        db.close();
        return lost;
    }

    /* lost: le montant du perdre enregistré dans le firebase
        newLOST=une varibale qui garde l'addition du montant du perdre enregistré dans le firebase et la base de données et réinitialiser ce montant dans la base de données.
     */
    public boolean setLOST_firebase(int lost){
        int newLOST=lost;
        newLOST+=getLOST();
        try{
            String querry="Update TB_Info set LOST='"+newLOST+"'";
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL(querry);
            db.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }


    public boolean setLOST_gameDone(){
        int newLOST=getLOST()+1;
        try{
            String querry="Update TB_Info set LOST='"+newLOST+"'";
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL(querry);
            db.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }








    public boolean getOn_Off(){
        boolean on_off=false;
        String querry="Select on_off From TB_Info";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(querry,null);
        if (cursor.moveToFirst()) {
            if(cursor.getInt(0)==1){
                on_off=true;
            }else{
                on_off=false;
            }
        }
        db.close();
        return on_off;
    }

    public boolean setOn_Off(boolean on_off){
        int newOn_Off_int=0;
        if(on_off==true){
            newOn_Off_int=1;
        }else{
            newOn_Off_int=0;
        }
        try{
            String querry="Update TB_Info set on_off='"+newOn_Off_int+"'";
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL(querry);
            db.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }


    public String getEmail(){
        String email=null;
        String querry="Select Email From TB_Info";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(querry,null);
        if (cursor.moveToFirst()) {
            email = cursor.getString(0);  // ca commence par 0 et le premier (1) est pour username
        }
        db.close();
        return email;
    }

    public boolean setEmail(String email){
        String newEmail=email;
        try{
            String querry="Update TB_Info set Email='"+newEmail+"'";
            SQLiteDatabase db=this.getWritableDatabase();
            db.execSQL(querry);
            db.close();
            return true;
        }catch (Exception e){
            return false;
        }
    }










}

/* on réccupère les infos de la base de données que nous avons créé d'hors de l'application et
    nous l'avons importé dedans.L'importation est faite en créant une nouvelle base de données.
    comment acceder à la nouvelle base de données?
    création d'une variable BD_EXISTANCE nous permet de vérifier si la base de données existe ou pas...
    comment?
    tout d'abords, à l'aide de la méthode CheckDatabase(), nous essayons de lire le fichier existant dans l'adresse
    où nous voulons avons créé la nouvelle base de données.

     si la base de données existe:
        à l'aide de méthode "OpenDatabase()", nous définissons le "path" de la base de données que nous avons créé
        et avec SQLiteDatabase.openDatabase(path,null, SQLiteDatabase.LE_NIVEAU_D'ACCES)" nous lison BD!


    si la base de données n'existe pas:
        nous allons créer une nouvelle base de données à l'aide de la méthode "CreateDatabase()"en recopiant les données dans
         la base de données que nous avons importé!
         dans la méthode CopyDatabase(), nous avons une autre méthode "CopyDatabase()"

         ** pour quoi la méthode "CopyDatabase()" est dans la méthode "CreateDatabase()" ? parce que c'est plus propre!

         dans la méthode "CopyDatabase()" nous récuperons la base de données dans le dossier assets nous l'ouvrons.
          après, "BD_PATH+BD_NAME", avec OutputStream nous pouvons sortir et enregistrer les résultats.
         avec "inputStream.read(buffer)" nous récuperrons les données 1mb par 1mb et nous initialisons dans "buffer". ensuite,
         comme le résultat est en int, nous le mettons dans "len". ensuite, nous sortons et enregistrons les résultats
         avec "outputStream.write(buffer,0,len)" dans l'adresse qu'on a données à "outputStream".



 */
