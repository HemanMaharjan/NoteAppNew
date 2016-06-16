package com.example.heman.noteapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Heman on 05/06/2016.
 */

public class DatabaseClass {

	private static final String TAG = "DatabaseClass";
	public static final String Note_id= "_id";
	public static final String Note_data = "note";

	
	public static final String[] ALL_KEYS = new String[] {Note_id, Note_data};


	public static final String DATABASE_NAME = "myDataBase";
	public static final String DATABASE_TABLE = "Notedb";
	public static final int DATABASE_VERSION = 2;

	private static final String DATABASE_CREATE_SQL = 
			"CREATE TABLE " + DATABASE_TABLE 
			+ " (" + Note_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ Note_data + " TEXT NOT NUll"
			+ ");";
	
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

	public long insertRow(String task) {
		ContentValues initialValues = new ContentValues();
		initialValues.put(Note_data, task);
				

		return db.insert(DATABASE_TABLE, null, initialValues);
	}
	


	public Cursor getRows() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS, where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
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

