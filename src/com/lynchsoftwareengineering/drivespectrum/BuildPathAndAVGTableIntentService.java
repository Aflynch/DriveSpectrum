package com.lynchsoftwareengineering.drivespectrum;

import java.util.ArrayList;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class BuildPathAndAVGTableIntentService extends IntentService{
	public static int MAC_ADDRESS_KEY = 0;
	public static int FILE_PATH_KEY=1;
	public static int MIN_SPEED_MPS = 2;//3
	public static int MAX_TIME_OUT_TIME = 30000;
	String macAddressString;
	String filePathString;
	
	public BuildPathAndAVGTableIntentService(){
		super("BuildPathAndAVGTableIntentService");
	}

	private void buildPathAndAVGTables() {
		DBSingleton dbSingleton = DBSingleton.getInstanceOfDataBaseSingleton(filePathString, macAddressString);
		DBSingleton.DataFilter dataFilter = dbSingleton.new DataFilter();
		Log.d("Table", "Path about to be sorrtd");
		ArrayList<PointTime> pointTimeArrayList = dataFilter.processData(dbSingleton.getDataInSegmentsFromMainTable(1000, "SELECT * FROM ", DBContractClass.GPSEntry.TABLE_NAME));
		Log.d("Table", "Path sorrtd:: ArrayList size = "+pointTimeArrayList.size());
		dbSingleton.writeToTable(DBContractClass.PathGPSEntry.TABLE_NAME, pointTimeArrayList, 1000);
		Log.d("Table", "Path table was writen too. size = "+ pointTimeArrayList.size() );
		pointTimeArrayList = DataAVGRules.computAverageForGPSData(pointTimeArrayList);
		Log.d("Table", "AVG was computed. size = "+pointTimeArrayList.size());
		dbSingleton.writeToTable(DBContractClass.AVGGPSEntry.TABLE_NAME, pointTimeArrayList, 1000);
		Log.d("Tables", "AVG table was writen too");
		dbSingleton.getRowCountOfAllTable();
		
	}
	@Override
	protected void onHandleIntent(Intent arg0) {
		macAddressString = arg0.getStringExtra(""+MAC_ADDRESS_KEY);
		filePathString = arg0.getStringExtra(""+FILE_PATH_KEY);
		buildPathAndAVGTables();// rename		
	}


}
