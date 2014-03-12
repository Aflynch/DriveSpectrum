package com.lynchsoftwareengineering.drivespectrum;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DBSingleton {

	private static final String TEXT_TYPE = " TEXT";
	private static final String REAL_TYPE = " REAL";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = ",";
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + DBContractClass.GPSEntry.TABLE_NAME + " (" +
	    DBContractClass.GPSEntry._ID + " INTEGER PRIMARY KEY," +
	    DBContractClass.GPSEntry.COLUMN_NAME_BEARING + REAL_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_LAT + REAL_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_LON + REAL_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_MAC_ADDRESS + TEXT_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_SPEED + REAL_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_TIME + INTEGER_TYPE + COMMA_SEP +
	    " )";
	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + DBContractClass.GPSEntry.TABLE_NAME;
	private static DBSingleton dBSingleton;

	
	private DBSingleton(){
		// "Haha, You will ever get this"
	}

	public static DBSingleton getInstanceOfDataBaseSingleton(){
		if(dBSingleton == null){
			dBSingleton = new DBSingleton();
			return dBSingleton;
		}else{
			return dBSingleton;
		}
	}
	
	public class GPSReaderDbHelper extends SQLiteOpenHelper {
	    // If you change the database schema, you must increment the database version.
	    public static final int DATABASE_VERSION = 1;
	    public static final String DATABASE_NAME = "GPSData.db";

	    public GPSReaderDbHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(SQL_CREATE_ENTRIES);
	    }
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // This database is only a cache for online data, so its upgrade policy is
	        // to simply to discard the data and start over
	    	
	    	Log.d("database test", "onUpgrade was called, but no acction was taken.");
	        //db.execSQL(SQL_DELETE_ENTRIES);
	        //onCreate(db);
	    }
	    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        onUpgrade(db, oldVersion, newVersion);
	    }
	}
	
}

