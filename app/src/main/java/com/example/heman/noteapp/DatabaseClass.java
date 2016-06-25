package com.example.heman.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.sql.Blob;

/**
 * Created by Heman on 05/06/2016.
 */

public class DatabaseClass {

	private static final String TAG = "DatabaseClass";
	public static final String Note_id= "_id";
	public static final String Note_data = "note";
	public static final String Note_Image ="img";

	
	public static final String[] ALL_KEYS = new String[] {Note_id, Note_data, Note_Image};


	public static final String DATABASE_NAME = "myDataBase";
	public static final String DATABASE_TABLE = "Notedb";
	public static final int DATABASE_VERSION = 4;

	private static final String DATABASE_CREATE_SQL = 
			"CREATE TABLE " + DATABASE_TABLE 
			+ " (" + Note_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Note_data + " TEXT NOT NUll, "
			+ Note_Image + " BLOB );";
	
	private final Context context;
	private DatabaseHelper myDBH;
	private SQLiteDatabase db;


	public DatabaseClass(Context ctx) {
		this.context = ctx;
		myDBH = new DatabaseHelper(context);
	}
	

	public DatabaseClass open() {
		db = myDBH.getWritableDatabase();
		return this;
	}
	

	public void close() {
		myDBH.close();
	}

	public long insertRow(String task, byte[] img) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(Note_data, task);initialValues.put(Note_Image, img);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}


	public boolean InsertImage(Long cRow, byte[] img) throws SQLiteException {
	String where = Note_id + "=" +cRow;
	ContentValues newValues = new ContentValues();
		newValues.put(Note_Image, img);
		return db.update(DATABASE_TABLE, newValues, where, null)!=0;
	}


	public boolean EditRow(Long cRow, String cData) {
        String where = Note_id + "=" + cRow;
        ContentValues newValues = new ContentValues();
        newValues.put(Note_data,cData);
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;

	}
	


	public Cursor getRows() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	public boolean delRow(Long cRow) {
		String where = Note_id + "=" + cRow;
		return db.delete(DATABASE_TABLE, where, null) != 0;
	}




	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE_SQL);			
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(_db);
		}
	}


}

