package com.lynchsoftwareengineering.drivespectrum;

import java.io.File;
import java.io.ObjectInputStream.GetField;

import dalvik.system.BaseDexClassLoader;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.util.Log;
/*
 * This class is a wrapper class for SQLightDatabase
 * it manages the opening and editing of the database, and 
 * provides for a singleton access. Though it can not restrict access 
 * as such.  
 */ 
public class DBSingleton {
	private static final String DB_FILE_NAME  = "thebests";
	private static final String TEXT_TYPE = " TEXT";
	private static final String REAL_TYPE = " REAL";
	private static final String INTEGER_TYPE = " INTEGER";
	private static final String COMMA_SEP = ",";
	private static final String  CHECK_IT_TABLE_NAME_EXISTS = "SELECT DISTINCT  tbl_name FROM sqlite_master WHERE tbl_name = '"+DBContractClass.GPSEntry.TABLE_NAME +"'"; 	 
	private static final String SQL_CREATE_ENTRIES =
	    "CREATE TABLE " + DBContractClass.GPSEntry.TABLE_NAME + " (" +
	    DBContractClass.GPSEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
	    DBContractClass.GPSEntry.COLUMN_NAME_BEARING + REAL_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_LAT + REAL_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_LON + REAL_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_MAC_ADDRESS + TEXT_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_SPEED + REAL_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_TIME + INTEGER_TYPE +
	    " )";
	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " + DBContractClass.GPSEntry.TABLE_NAME;
	private static DBSingleton dBSingleton;
	private String macAddressString;
	private static boolean fileIsLoadedBoolean;


	//lilyphon@yahoo.com	
	static DBSingleton dbSingleton;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor sharedPerferencesEditor;
	final String OFF_SET_KEY = "OFF_SET_KEY";
	final String DS_DB_NAME = "DS_DB_NAME";
	final String DS_KEY = "DS_KEY";
	int dbOffSetInt;
	private String filePathString;
	private DBSingleton() {
		
	}
	
	public static DBSingleton getDBSingletion(){
		if (dbSingleton == null){
			dbSingleton = new DBSingleton();
		}
		return dbSingleton;
	}
	
	public void writeMapDB(String dataString){
		dbOffSetInt++;
		sharedPerferencesEditor.putString("Key"+dbOffSetInt, dataString);
		sharedPerferencesEditor.putString(OFF_SET_KEY, ""+dbOffSetInt);
		sharedPerferencesEditor.commit();
	}
	public synchronized void writeGPSDataToDB(float bearingFloat, double latitudeDouble, double longitudeDouble, double speedDouble, long timeLong){
		SQLiteDatabase db = SQLiteDatabase.openDatabase(filePathString+DB_FILE_NAME,null, SQLiteDatabase.CREATE_IF_NECESSARY);
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBContractClass.GPSEntry.COLUMN_NAME_BEARING, bearingFloat);
		contentValues.put(DBContractClass.GPSEntry.COLUMN_NAME_LAT, latitudeDouble);
		contentValues.put(DBContractClass.GPSEntry.COLUMN_NAME_LON, longitudeDouble);
		contentValues.put(DBContractClass.GPSEntry.COLUMN_NAME_MAC_ADDRESS, macAddressString);
		contentValues.put(DBContractClass.GPSEntry.COLUMN_NAME_SPEED, speedDouble);
		contentValues.put(DBContractClass.GPSEntry.COLUMN_NAME_TIME, timeLong);// CHECK FOR MAX MIN LAT LON SPEED!!!! 
		db.insert(DBContractClass.GPSEntry.TABLE_NAME, null, contentValues);
	}
	
	private DBSingleton(String filePathString, String macAddressString){
		this.filePathString = filePathString;
		this.macAddressString = macAddressString;
		// "Haha, You will ever get this"

		SQLiteDatabase db = SQLiteDatabase.openDatabase(filePathString+DB_FILE_NAME,null, SQLiteDatabase.CREATE_IF_NECESSARY);
		if (db !=null){
			Cursor cursor = db.rawQuery(CHECK_IT_TABLE_NAME_EXISTS, null);
			if(cursor.getCount() ==  0){
				db.execSQL(SQL_CREATE_ENTRIES);
			}else{
				Log.d("running" , "SQLightDataBase and table  "+DBContractClass.GPSEntry.TABLE_NAME +" loaded.");
			}
		}
		db.close();
	}

	public static DBSingleton getInstanceOfDataBaseSingletion(){
		if (dBSingleton != null){
			return dBSingleton;
		}else{
			return null; // I know it would be null either way but it make me fee better. 
		}
	}
	public static DBSingleton getInstanceOfDataBaseSingleton(String filePathString, String macAddressString){
		//Then one day he got this
		if(dBSingleton == null){
			dBSingleton = new DBSingleton(filePathString, macAddressString);
			fileIsLoadedBoolean = true;
			return dBSingleton;
		}else{
			return dBSingleton;
		}
	}
	/*
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
	*/
}

