package com.lynchsoftwareengineering.drivespectrum;

import java.util.ArrayList;
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
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	LocationManager locationManager;
	ArrayList<View> viewArrayList;
	DriveSectrumLocationListener driveSectrumLocationListener;
	Context context;
	int widthInt; 
	int heightInt;
	final String DS_DB_NAME = "DS_DB_NAME";
	final String DS_KEY = "DS_KEY";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context = this;
		getScreenSize();
		super.onCreate(savedInstanceState);
		buildLayout();
		driveSectrumLocationListener = new DriveSectrumLocationListener();
		setContentView(buildLayout());
		setUpGPS();
		checkDB();
	}
	private void checkDB() {
		
		SharedPreferences sharedPreferences =  context.getSharedPreferences(DS_DB_NAME, Context.MODE_PRIVATE);
		SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
		sharedPreferencesEditor.putString("Key?", "Data?");
		//sharedPreferencesEditor.putInt("Test Data", "Key?");
		sharedPreferencesEditor.commit();
		String bufferString = "";
		
		Log.d("DB_TEST", sharedPreferences.getString("Key?", bufferString));
		Toast.makeText(context, "DB Was checked", Toast.LENGTH_SHORT);// Would need a DB read Write + index file at "Ke_0" 
		
		
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
		TextView textView = new TextView(context);
		textView.setText("This is boss!!!");
		
		viewArrayList = new ArrayList<View>();
		viewArrayList.add(textView);
		
		ImageButton imageButton = new ImageButton(context);
		imageButton.setBackgroundColor(Color.YELLOW);// this is really ugly but it is for testing.
		viewArrayList.add(imageButton);
		
		ImageButton imageButtonTwo = new ImageButton(context);
		imageButtonTwo.setBackgroundColor(Color.RED);
		viewArrayList.add(imageButtonTwo);
		
		//relativeLayout.setBackgroundDrawable(R.drawable.ic_launcher);
		
		for(int i = 0; i < viewArrayList.size(); i++){ 
			RelativeLayout.LayoutParams relativeLayoutLayoutParams = new RelativeLayout.LayoutParams(widthInt/6, heightInt/9);
			relativeLayoutLayoutParams.setMargins((widthInt/6)+((widthInt/4)*i), (heightInt/9)/*+((heightInt/9)*i)*/, 0, 0);
			relativeLayout.addView(viewArrayList.get(i), relativeLayoutLayoutParams);
		}
		
		DriveSpectrumOnClickListener driveSpectrumOnClickListener = new DriveSpectrumOnClickListener();
		// would be nice if I would use for loop here...
		imageButton.setOnClickListener(driveSpectrumOnClickListener);
		imageButtonTwo.setOnClickListener(driveSpectrumOnClickListener);
		
		
		relativeLayout.setBackgroundColor(Color.argb(255, 0, 150, 255));
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
			Toast.makeText(context, "lat "+ locatoin.getLatitude() + " : long "+ locatoin.getLongitude(), Toast.LENGTH_SHORT).show();
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

			if(view.equals(viewArrayList.get(1))){
				tagString = "Button 1 was pressed GPS is on.";
			} else {
				context.startActivity(new Intent(context, SpectrumView.class));
			}
			
			Toast.makeText(context, tagString, Toast.LENGTH_SHORT).show();
		}
		
	}

}
