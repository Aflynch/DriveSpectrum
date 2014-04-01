package com.lynchsoftwareengineering.drivespectrum;

import com.lynchsoftwareengineering.drivespectrum.DBContractClass.GPSEntry;
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
	LocationManager locationManager;
	DBSingleton dbSingleton ;
	CustomLocationListioner customLocationListioner;
	String macAddressString;
	String filePathString;
	public GPSIntentService() {
		super("GPDIntrentService");
	}

	@Override 
	public void onStart(Intent intent, int startInt){
		super.onStart(intent, startInt);
		macAddressString = intent.getStringExtra(""+MAC_ADDRESS_KEY);
		filePathString = intent.getStringExtra(""+FILE_PATH_KEY);
		
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
			dbSingleton.writeGPSDataToDB(location.getBearing(),location.getLatitude(),location.getLongitude(),location.getSpeed(),System.currentTimeMillis());
			Log.d("Service", "GPS data send to  database."+location.toString());
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
