package com.lynchsoftwareengineering.drivespectrum;

import java.util.ArrayList;

import com.lynchsoftwareengineering.drivespectrum.DBContractClass.GPSEntry;
import com.lynchsoftwareengineering.drivespectrum.DBSingleton.DataFilter;
import android.app.IntentService;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.ClipData.Item;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.UrlQuerySanitizer.ValueSanitizer;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

public class GPSIntentService extends IntentService {
	public static int MAC_ADDRESS_KEY = 0;
	public static int FILE_PATH_KEY=1;
	public static int MIN_SPEED_MPS = 2;//3
	public static int MAX_TIME_OUT_TIME = 30000;
	LocationManager locationManager;
	DBSingleton dbSingleton ;
	CustomLocationListioner customLocationListioner;
	String macAddressString;
	String filePathString;
	Long timeLong;
	int countInt;
	public GPSIntentService() {
		super("GPDIntrentService");
	}

	@Override 
	public void onStart(Intent intent, int startInt){
		super.onStart(intent, startInt);
		macAddressString = intent.getStringExtra(""+MAC_ADDRESS_KEY);
		filePathString = intent.getStringExtra(""+FILE_PATH_KEY);
		countInt = 0;
		setUpGPS();
	}
	
	protected void setUpGPS() {
		Log.d("log bitch", "line of code");
	    locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!gpsEnabled) {
			enableLocationSettings();//
		}
		LocationProvider provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
		CustomLocationListioner customLocationListioner = new CustomLocationListioner();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 10, customLocationListioner);
	}
	private void enableLocationSettings() {
		Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		settingsIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(settingsIntent);
	}
	
	private void getDBSingleton(){
		dbSingleton = DBSingleton.getInstanceOfDataBaseSingletion();
		
	}

	private class CustomLocationListioner implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			if (dbSingleton == null){
				getDBSingleton();
			}
			if (location.getSpeed()>MIN_SPEED_MPS   ){
					int boolInt = (timeLong== null || ((System.currentTimeMillis() - timeLong))>GPSIntentService.MAX_TIME_OUT_TIME )? 0 : 1;// sqlight has no boolean type. Docs say to use Integer 1 or 0
					dbSingleton.writeGPSDataToDB(location.getBearing(),location.getLatitude(),location.getLongitude(),location.getSpeed(),System.currentTimeMillis(), boolInt); // 0 true 1 false  
					dbSingleton.writeToNewGPSTable(location.getBearing(),location.getLatitude(),location.getLongitude(),location.getSpeed(),System.currentTimeMillis(), boolInt);
					Log.d("Service", "GPS data send to  database."+location.toString());
					timeLong = System.currentTimeMillis();
					// this is so wrong but I am out of time and it will get the job done for now.
					countInt++;
					if (countInt%DataAVGRules.NUMBER_OF_POINTS_TO_AVG == 0){
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
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}	
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		
	}
}
