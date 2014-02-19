package com.lynchsoftwareengineering.drivespectrum;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

public class DBSingleton {
	static DBSingleton dbSingleton;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor sharedPerferencesEditor;
	final String OFF_SET_KEY = "OFF_SET_KEY";
	final String DS_DB_NAME = "DS_DB_NAME";
	final String DS_KEY = "DS_KEY";
	int dbOffSetInt;
	private DBSingleton() {
		
	}
	
	public static DBSingleton getDBSingletion(){
		if (dbSingleton == null){
			dbSingleton = new DBSingleton();
		}
		return dbSingleton;
	}
	public void writeToDB(String dataString){
		dbOffSetInt++;
		sharedPerferencesEditor.putString("Key"+dbOffSetInt, dataString);
		sharedPerferencesEditor.putString(OFF_SET_KEY, ""+dbOffSetInt);
		sharedPerferencesEditor.commit();
	}
	
	public ArrayList<String> readAllDB(){ // db needs to be an object like for real. 
		ArrayList<String> stringArrayList = new ArrayList<String>();
		for(int i = 0; i < dbOffSetInt; i++ ){
			stringArrayList.add(sharedPreferences.getString("Key"+i, "")); 
			//Log.d("Date Test", sharedPreferences.getString("Key"+i, ""));
		}
		return stringArrayList;
	}
	
	public String readDB(int keyIndexInt){
		
		String bufferString = "";
		bufferString = sharedPreferences.getString("Key"+keyIndexInt, bufferString);
		return bufferString;
	}
	
	private void testNulldata(){
		String bufferString = "";
		sharedPreferences.getString("Null Key", bufferString);
		Log.d("DB_TEST", "data  = " + bufferString);
	}
	
	public void dbToLogD(){
		for (int i = 0; i <= dbOffSetInt ; i++){
			Log.d("DB_TEST", readDB(i));
		}
	}
	
	public void checkDB(Context context ) {// Need to check it database as been used befor. 
		// needs to be able to run with out context. latter going to use service. 
		this.sharedPreferences = context.getSharedPreferences(DS_DB_NAME, Context.MODE_PRIVATE);
		this.sharedPerferencesEditor = sharedPreferences.edit();
		sharedPerferencesEditor.putString("Key?", "Data??");
		
		//sharedPerferencesEditor.putInt("Test Data", "Key?");
		sharedPerferencesEditor.commit();
		String testData = "";
		testData = sharedPreferences.getString("Key?", testData);
		String bufferString = "";
		bufferString = sharedPreferences.getString(OFF_SET_KEY, bufferString);
		Log.d("DB_TEST", bufferString);
		if (bufferString.equals("")){
			dbOffSetInt = -1;// write method ++ before updating dbOFFSetInt in db.
		}else{
			dbOffSetInt = Integer.parseInt(bufferString);
		}
		Toast.makeText(context, "DB Was checked. dbOffSett = "+ dbOffSetInt +" : "+testData, Toast.LENGTH_SHORT).show();// Would need a DB read Write + index file at "Ke_0" 
		
		
		/*
		SharedPreferences shardPreferences = getSharedPreferences(DS_DB_NAME, 0);
		Editor editor =  shardPreferences.edit();
		editor.putString(DS_KEY, "this is some awesome data!");
		editor.commit();
		// check data read. 
		Log.d("BD_TEST", ""+shardPreferences.getLong(DS_KEY, 0));
		long fileWasFound = shardPreferences.getLong(DS_KEY, (long) -1);	
		*/	
	}

	public int getDbOffSetInt() {
		return dbOffSetInt;
	}
	
	
}
