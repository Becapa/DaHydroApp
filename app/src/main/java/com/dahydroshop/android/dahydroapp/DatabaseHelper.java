package com.dahydroshop.android.dahydroapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Mike on 11/4/2015.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE = "hydroapp.db";
    private static final int VERSION = 1;
    private static final String ITEMTABLE = "item";
    private static final String OWNERTABLE = "owner";

    private AssetManager mManager;

    public DatabaseHelper(Context context){
        super(context, DATABASE, null, VERSION);
        mManager = context.getAssets();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE " + ITEMTABLE + "(" +
                "id INTEGER PRIMARY KEY, " +
                "department TEXT NOT NULL, " +
                "itemName TEXT NOT NULL, " +
                "attribute TEXT, " +
                "size TEXT, " +
                "quantity INTEGER, " +
                "margin REAL, " +
                "cost REAL, " +
                "price REAL, " +
                "extCost REAL, " +
                "extPrice REAL)");

        db.execSQL("CREATE TABLE " + OWNERTABLE + "(" +
                "id INTEGER PRIMARY KEY, " +
                "username TEXT NOT NULL, " +
                "passwd TEXT NOT NULL)");

        setOwnerTableDataFromCSV(db);
        setItemTableDataFromCSV(db);
    }

    private void setOwnerTableDataFromCSV(SQLiteDatabase db){
        String csvFile = "CSVOwner.csv";
        InputStream inStream = null;
        try {
            inStream = mManager.open(csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(inStream));
        String line;
        db.beginTransaction();

        String dbCol0 = "username";
        String dbCol1 = "passwd";

        try {
            while ((line = buffer.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length != 2) {
                    Log.d("CSVParser", "Skipping Bad CSV Row");
                    continue;
                }
                ContentValues cv = new ContentValues();
                cv.put(dbCol0, columns[0].trim());
                cv.put(dbCol1, columns[1].trim());
                db.insert(OWNERTABLE, null, cv);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    private void setItemTableDataFromCSV(SQLiteDatabase db){
        String csvFile = "CSVPOS.csv";
        InputStream inStream = null;
        try {
            inStream = mManager.open(csvFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader buffer = null;
        if (inStream != null) {
            buffer = new BufferedReader(new InputStreamReader(inStream));
        }
        String line;
        db.beginTransaction();

        String dbCol0 = "department";
        String dbCol1 = "itemName";
        String dbCol2 = "attribute";
        String dbCol3 = "size";
        String dbCol4 = "quantity";
        String dbCol5 = "margin";
        String dbCol6 = "cost";
        String dbCol7 = "price";
        String dbCol8 = "extCost";
        String dbCol9 = "extPrice";

        try {
            if (buffer != null) {
                while ((line = buffer.readLine()) != null) {
                    String[] columns = line.split(",");
                    if (columns.length != 10) {
                        Log.d("CSVParser", "Bad row...");
                        continue;
                    }
                    ContentValues cv = new ContentValues();
                    cv.put(dbCol0, columns[0].trim());
                    cv.put(dbCol1, columns[1].trim());
                    cv.put(dbCol2, columns[2].trim());
                    cv.put(dbCol3, columns[3].trim());
                    cv.put(dbCol4, columns[4].trim());
                    cv.put(dbCol5, columns[5].trim());
                    cv.put(dbCol6, columns[6].trim());
                    cv.put(dbCol7, columns[7].trim());
                    cv.put(dbCol8, columns[8].trim());
                    cv.put(dbCol9, columns[9].trim());
                    db.insert(ITEMTABLE, null, cv);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + ITEMTABLE);
        db.execSQL("DROP TABLE IF EXISTS " + OWNERTABLE);

        onCreate(db);
    }
}