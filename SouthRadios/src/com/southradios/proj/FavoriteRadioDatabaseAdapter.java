package com.southradios.proj;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavoriteRadioDatabaseAdapter {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "radiodb.db";
	private static final String TABLE_NAME_FAV = "fav_radio_table";
	
	
	

	public static final String FAV_RADIO_TABLE_COLUMN_ID = "Id";
	public static final String FAV_RADIO_TABLE_COLUMN_RADIO_NAME = "RadioName";
	public static final String FAV_RADIO_TABLE_COLUMN_RADIO_CAPTION = "RadioCaption";
	public static final String FAV_RADIO_TABLE_COLUMN_RADIO_URL= "RadioUrl";
	public static final String FAV_RADIO_TABLE_COLUMN_RADIO_ICON_NAME = "RadioIconName";
	
	

	private DatabaseOpenHelper openHelper;
	private SQLiteDatabase db;

	public FavoriteRadioDatabaseAdapter(Context context) {

		openHelper = new DatabaseOpenHelper(context);
		db = openHelper.getWritableDatabase();

	}
	
	public void saveRecords(int id, String radioName, String radioCaption, String radioUrl, String radioIconName){
		ContentValues cv = new ContentValues();
		cv.put(FAV_RADIO_TABLE_COLUMN_ID, id);
		cv.put(FAV_RADIO_TABLE_COLUMN_RADIO_NAME, radioName);
		cv.put(FAV_RADIO_TABLE_COLUMN_RADIO_CAPTION, radioCaption);
		cv.put(FAV_RADIO_TABLE_COLUMN_RADIO_URL, radioUrl);
		cv.put(FAV_RADIO_TABLE_COLUMN_RADIO_ICON_NAME, radioIconName);
		db.insert(TABLE_NAME_FAV, null, cv);
	}
	

	
	public void RemoveRecords(int id){

		db.delete(TABLE_NAME_FAV, FAV_RADIO_TABLE_COLUMN_ID + " = " + id, null);
	}

	
	//For a single radio
	public Cursor getRecords(int radioId){
		return db.rawQuery("select * from " + TABLE_NAME_FAV + " where " +FAV_RADIO_TABLE_COLUMN_ID + " = "+radioId , null);
	}
	
	//For all radio favorite page
	public Cursor getRecords(){
		return db.rawQuery("select * from " + TABLE_NAME_FAV , null);
	}
	

	
	public void close(){
		db.close();
	}
	
	private class DatabaseOpenHelper extends SQLiteOpenHelper {

		public DatabaseOpenHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			System.out.println("SQL : "+"CREATE TABLE " + TABLE_NAME_FAV + " ( "
					+ FAV_RADIO_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY, "
					+ FAV_RADIO_TABLE_COLUMN_RADIO_NAME + " VARCHAR, "
					+ FAV_RADIO_TABLE_COLUMN_RADIO_CAPTION + " VARCHAR, "
					+ FAV_RADIO_TABLE_COLUMN_RADIO_URL + " VARCHAR, "
					+ FAV_RADIO_TABLE_COLUMN_RADIO_ICON_NAME + " VARCHAR )");
			
			
			db.execSQL("CREATE TABLE " + TABLE_NAME_FAV + " ( "
					+ FAV_RADIO_TABLE_COLUMN_ID + " INTEGER PRIMARY KEY, "
					+ FAV_RADIO_TABLE_COLUMN_RADIO_NAME + " VARCHAR, "
					+ FAV_RADIO_TABLE_COLUMN_RADIO_CAPTION + " VARCHAR, "
					+ FAV_RADIO_TABLE_COLUMN_RADIO_URL + " VARCHAR, "
					+ FAV_RADIO_TABLE_COLUMN_RADIO_ICON_NAME + " VARCHAR )");
			

			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
			// TODO Auto-generated method stub

			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_FAV);
			onCreate(db);

		}
		


	}

}
