package com.lynchsoftwareengineering.drivespectrum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.ContactsContract.Data;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	DriveSectrumLocationListener driveSectrumLocationListener;
	LocationManager locationManager;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor sharedPerferencesEditor;
	Calendar calendar;
	ArrayList<View> viewArrayList;
	Context context;
	int dbOffSetInt;
	int widthInt; 
	int heightInt;
	final String OFF_SET_KEY = "OFF_SET_KEY";
	final String DS_DB_NAME = "DS_DB_NAME";
	final String DS_KEY = "DS_KEY";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = this;
		getScreenSize();
		super.onCreate(savedInstanceState);
		buildLayout();
		driveSectrumLocationListener = new DriveSectrumLocationListener();
		calendar = Calendar.getInstance();
		setContentView(buildLayout());
		checkDB();
		testNulldata();
	}
	
	
	private void writeToDB(String dataString){
		dbOffSetInt++;
		sharedPerferencesEditor.putString("Key"+dbOffSetInt, dataString);
		sharedPerferencesEditor.putString(OFF_SET_KEY, ""+dbOffSetInt);
		sharedPerferencesEditor.commit();
	}
	
	private ArrayList<String> readAllDB(){ // db needs to be an object like for real. 
		ArrayList<String> stringArrayList = new ArrayList<String>();
		for(int i = 0; i < dbOffSetInt; i++ ){
			stringArrayList.add(sharedPreferences.getString("Key"+i, "")); 
		}
		return stringArrayList;
	}
	
	private String readDB(int keyIndexInt){
		
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
	
	private void checkDB() {// Need to check it database as been used befor. 
		
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
	private void getScreenSize(){
		WindowManager windowManager = (WindowManager) context.getSystemService(context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		Point pointScreenSize = new Point();
		if(Build.VERSION.SDK_INT  >=  13){ 
			display.getSize(pointScreenSize);
		}else{
			DisplayMetrics displayMetrics = new DisplayMetrics();
			display.getMetrics(displayMetrics);
			pointScreenSize.set(displayMetrics.widthPixels, displayMetrics.heightPixels);
		}
		widthInt = pointScreenSize.x;
		heightInt = pointScreenSize.y;		
	}
	
	private RelativeLayout buildLayout() {
		
		RelativeLayout relativeLayout = new RelativeLayout(context);
		/*
		TextView textView = new TextView(context);
		textView.setText("This is boss!!!");
		*/
		viewArrayList = new ArrayList<View>();
		//viewArrayList.add(textView);
		
		Button startButton = new Button(context);
		startButton.setBackgroundColor(Color.argb(255, 255, 165, 0));// this is really ugly but it is for testing.
		startButton.setTextColor(Color.WHITE);
		startButton.setText("Start");
		viewArrayList.add(startButton);
		
		Button mapButton = new Button(context);
		mapButton.setBackgroundColor(Color.argb(255, 255, 165, 0));
		mapButton.setText("Map");
		viewArrayList.add(mapButton);
		
		//relativeLayout.setBackgroundDrawable(R.drawable.ic_launcher);
		
		for(int i = 0; i < viewArrayList.size(); i++){ 
			RelativeLayout.LayoutParams relativeLayoutLayoutParams = new RelativeLayout.LayoutParams(widthInt/3, heightInt/9);
			relativeLayoutLayoutParams.setMargins((widthInt/12)+((widthInt/3)*i)+((widthInt/6)*i), (heightInt/9)/*+((heightInt/9)*i)*/, 0, 0);
			relativeLayout.addView(viewArrayList.get(i), relativeLayoutLayoutParams);
		}
		
		DriveSpectrumOnClickListener driveSpectrumOnClickListener = new DriveSpectrumOnClickListener();
		// would be nice if I would use for loop here...
		startButton.setOnClickListener(driveSpectrumOnClickListener);
		mapButton.setOnClickListener(driveSpectrumOnClickListener);
		
		
		relativeLayout.setBackgroundColor(Color.YELLOW);
		return relativeLayout;
	}

	
	protected void setUpGPS() {
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		final boolean gpsEnabled = locationManager
				.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if (!gpsEnabled) {
			enableLocationSettings();//
		}
		LocationProvider provider = locationManager
				.getProvider(LocationManager.GPS_PROVIDER);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				10000, 10, driveSectrumLocationListener);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void enableLocationSettings() {
		Intent settingsIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(settingsIntent);
	}
	
	public class DriveSectrumLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location locatoin) {
			Date date = calendar.getTime();
			Toast.makeText(context, "lat "+ locatoin.getLatitude() + " : long "+ locatoin.getLongitude(), Toast.LENGTH_SHORT).show();
			writeToDB(locatoin.getLatitude()+"#"+locatoin.getLongitude()+"#"+locatoin.getSpeed()+"#"+ date.toString());
			// Need to log this data
		}

		@Override
		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
		}
		
	}

	
	private class DriveSpectrumOnClickListener implements OnClickListener{
		String tagString  = "tag"; 
		@Override
		public void onClick(View view) {
			Log.d("DB_TEST","onClick was called");

			if(view.equals(viewArrayList.get(0))){
				tagString = "GPS is on.";
				setUpGPS();
				checkDB();
				dbToLogD();
				
			} else {
				context.startActivity(new Intent(context, SpectrumView.class));
			}
			
			//Toast.makeText(context, tagString, Toast.LENGTH_SHORT).show();
		}
		
	}

}
