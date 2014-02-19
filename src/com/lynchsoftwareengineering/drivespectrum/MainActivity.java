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
	DBSingleton dbSingleton;
	Calendar calendar;
	ArrayList<View> viewArrayList;
	Context context;
	int widthInt; 
	int heightInt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = this;
		getScreenSize();
		super.onCreate(savedInstanceState);
		buildLayout();
		driveSectrumLocationListener = new DriveSectrumLocationListener();
		calendar = Calendar.getInstance();
		setContentView(buildLayout());
		initCheckDB();
		//testNulldata();
	}
	
	
	

	private void initCheckDB() {
		dbSingleton = DBSingleton.getDBSingletion();
		dbSingleton.checkDB(context);
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
		final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
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
			// need to have instace of DB
			dbSingleton.writeToDB(locatoin.getLatitude()+"#"+locatoin.getLongitude()+"#"+locatoin.getSpeed()+"#"+ date.toString());
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
				dbSingleton.dbToLogD();
				
			} else {
				context.startActivity(new Intent(context, SpectrumActivity.class));
			}
			//Toast.makeText(context, tagString, Toast.LENGTH_SHORT).show();
		}
		
	}

}
