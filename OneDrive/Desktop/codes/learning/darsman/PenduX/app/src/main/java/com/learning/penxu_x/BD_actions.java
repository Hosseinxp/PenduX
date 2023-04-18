package com.learning.penxu_x;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.learning.penxu_x.pageNiveaux;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Locale;

public class BD_actions extends SQLiteOpenHelper {

    private Context context;

    private com.learning.penxu_x.pageNiveaux pageNiveaux;
    private static String BD_NAME = "BD_PenduX";


    private static String BD_PATH = "/data/data/com.learning.penxu_x/Databases/";

    public static final String COLUMN_1 = "ID";
    public static final String COLUMN_2 = "Theme";
    public static final String COLUMN_3 = "Niveau";
    public static final String COLUMN_4 = "Mot";

    public BD_actions(Context context) {
        super(context,BD_NAME,null,1);

        this.context = context;

/*
        boolean BD_EXISTANCE = CheckDatabase();
        if (BD_EXISTANCE) {
            OpenDatabase();
        } else {
            //this.getReadableDatabase(); nous permet de lire la base de données étrangère
            this.getReadableDatabase();
            CreateDatabase();
        }
*/
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + BD_NAME +"(ID INTEGER PRIMARY KEY ,Theme VARCHR(20), Niveau VARCHAR(140))" );
        // Définition de la requête SQL de création de la table TB_mot
        /*String createTableQuery = "CREATE TABLE IF NOT EXISTS TB_mot (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "mot TEXT NOT NULL)";

        // Exécution de la requête SQL de création de la table TB_mot
        sqLiteDatabase.execSQL(createTableQuery);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

/*
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }*/

    private void insertData(SQLiteDatabase db, int id, String theme, String niveau, String mot)
    {
        ContentValues values = new ContentValues();
        values.put(COLUMN_1, id);
        values.put(COLUMN_2, theme);
        values.put(COLUMN_3,niveau);
        values.put(COLUMN_4,mot);
        db.insert(BD_NAME, null, values);

    }

    public void insertMot()
    {
        String[] mots = {"Audi","BMW","Dacia", "Fiat","Ford","Opel","Seat","Asia","DS","Skoda"};
        SQLiteDatabase db = this.getWritableDatabase();
        for (String mot : mots){
            insertData(db,1,"Voiture","Easy", mot);
        }
    }

    public ArrayList<String> getVoitureEasy(){
        ArrayList<String> list=new ArrayList<>();
        String query="SELECT mot FROM TB_mot Where ID=1";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        int row=0;
        if(cursor.moveToFirst()) {
            do {
                String voiture = cursor.getString(row);
                list.add(voiture);
                row++;
            } while (cursor.moveToNext());
        }
            cursor.close();

        return list;
    }

    public ArrayList<String> getVoitureMedium(){
        ArrayList<String> list=new ArrayList<>();
        String query="SELECT mot FROM TB_mot Where ID=2";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        int row=0;
        if(cursor.moveToFirst()) {
            do {
                String voiture = cursor.getString(row);
                list.add(voiture);
                row++;
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    public ArrayList<String> getVoitureBrutal(){
        ArrayList<String> list=new ArrayList<>();
        String query="SELECT mot FROM TB_mot Where ID=3";
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        int row=0;
        if(cursor.moveToFirst()) {
            do {
                String voiture = cursor.getString(row);
                list.add(voiture);
                row++;
            } while (cursor.moveToNext());
        }
        cursor.close();

        return list;
    }

    @SuppressLint("Range")
    private String getMotEasyAleatoire(){

        SQLiteDatabase db = this.getReadableDatabase();
        String mot = null;
        String selectQuery = "SELECT mot FROM TB_mot WHERE Niveau =  'Easy' ORDER BY RANDOM() LIMIT 1  ";
        Cursor cursor = db.rawQuery(selectQuery,null);

        if(cursor.moveToFirst()){
            mot = cursor.getString(cursor.getColumnIndex("mot"));
        }
        cursor.close();
        db.close();

        return mot;
    }

    /*private boolean CheckDatabase(){
        boolean flag=false;
        try{
            String path=BD_PATH+BD_NAME;
            File file=new File(path);
            flag=file.exists();
        }catch (SQLException e){
            throw new Error("DATA BASE DOESN'T exist!");
        }
        return flag;
    }



    public void OpenDatabase() throws SQLException{
        String path=BD_PATH+BD_NAME;
        SQLiteDatabase.openDatabase(path,null,SQLiteDatabase.OPEN_READWRITE);
    }


    private void CreateDatabase(){
        try{
            CopyDatabase();
        }catch (IOException e){
            throw new Error("ERROR COPYING");
        }
    }

    public void CopyDatabase() throws IOException {


        // InputStream nous permet de lire et écrire des données
        InputStream inputStream=context.getAssets().open(BD_NAME);

        // OutputStream nous permet d'écrire des données
        OutputStream outputStream =new FileOutputStream(BD_PATH+BD_NAME);

        byte[] buffer =new byte[1024];
        int len;
        // mettre les infos de valeur d'un mb(megabyte) dans buffer
        while ((len=inputStream.read(buffer))>0){
            outputStream.write(buffer,0,len);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }
*/

}