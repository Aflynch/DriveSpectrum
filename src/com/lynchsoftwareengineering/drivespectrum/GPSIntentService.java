package com.lynchsoftwareengineering.drivespectrum;

import com.lynchsoftwareengineering.drivespectrum.DBContractClass.GPSEntry;
import com.lynchsoftwareengineering.drivespectrum.DBSingleton.GPSReaderDbHelper;

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
	public GPSIntentService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}


	//Context context;
	LocationManager locationManager;
	CustomLocationListioner customLocationListioner;
	String macAddressString;
	
	/*
	public GPSIntentService(Context context) {
		super("GPSIntentService");
		this.context = context;
	}
*/
	@Override 
	public void onStart(Intent intent, int startInt){
		intent.get
		setMacAddress();
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		customLocationListioner  = new CustomLocationListioner();
		final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!gpsEnabled) {
			enableLocationSettings();//
		}
		LocationProvider provider = locationManager.getProvider(LocationManager.GPS_PROVIDER);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 10, customLocationListioner);
	}

	private void setMacAddress() {
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo wInfo = wifiManager.getConnectionInfo();
		macAddressString = wInfo.getMacAddress(); 
	}

	private SQLiteDatabase getWriteableSQLightDatabase() {
		DBSingleton dbSingleton = DBSingleton.getInstanceOfDataBaseSingleton();
		
		GPSReaderDbHelper gpsReaderDbHelper = dbSingleton.new GPSReaderDbHelper();
		SQLiteDatabase sqLiteDatabase = gpsReaderDbHelper.getWritableDatabase();
		return sqLiteDatabase;
	}
	
	@Override
	protected void onHandleIntent(Intent intent) {
		for(int i = 0; i < 100; i++){
			Log.d("Service", "Test'n bitch!!!"+intent.toString());
		}
		//customLocationListioner = new CustomLocationListioner();
	}
	
	protected void setUpGPS() {
		Log.d("log bitch", "line of code");
	//	locationManager = (LocationManager) Context.getSystemService(LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!gpsEnabled) {
			enableLocationSettings();//
		}
		LocationProvider provider = locationManager
				.getProvider(LocationManager.GPS_PROVIDER);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 10, customLocationListioner);
	}
	private void enableLocationSettings() {
		Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(settingsIntent);
	}
	

	private class CustomLocationListioner implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			SQLiteDatabase sqLiteDatabaseWriteable = getWriteableSQLightDatabase(); // Need to come back and make readable database as well.
			ContentValues contentValues = new ContentValues();
			contentValues.put(GPSEntry.COLUMN_NAME_BEARING, ""+location.getBearing());
			contentValues.put( GPSEntry.COLUMN_NAME_LAT, ""+location.getLatitude());
			contentValues.put( GPSEntry.COLUMN_NAME_LON, ""+location.getLongitude());
			contentValues.put( GPSEntry.COLUMN_NAME_MAC_ADDRESS ,macAddressString);
			contentValues.put( GPSEntry.COLUMN_NAME_SPEED , ""+location.getSpeed());
			contentValues.put( GPSEntry.COLUMN_NAME_TIME , ""+location.getTime());

			sqLiteDatabaseWriteable.insert(GPSEntry.TABLE_NAME, null, contentValues);

			for(int i = 0; i < 100; i++){
				Log.d("Service", "Test'n bitch!!!"+location.toString());
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
}
