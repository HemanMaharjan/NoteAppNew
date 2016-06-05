package com.example.heman.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Heman on 05/06/2016.
 */
public class DatabaseClass extends SQLiteOpenHelper {
    public static final String DATABASE_NAME ="Note.db";
    public static final String TABLE_NAME = "note_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "DATA";

    public DatabaseClass(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,DATA TEXT )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

}
