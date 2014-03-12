package com.lynchsoftwareengineering.drivespectrum;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.ContactsContract.Data;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	DriveSectrumLocationListener driveSectrumLocationListener;
	LocationManager locationManager;
	DatabaseSingleton dbSingleton;
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
		Intent intent = new Intent(this, GPSIntentService.class);
		startService(intent);
		buildLayout();
		driveSectrumLocationListener = new DriveSectrumLocationListener();
		calendar = Calendar.getInstance();
		setContentView(buildLayout());
		initCheckDB();
		//testNulldata();
	}
	
	
	

	private void initCheckDB() {
		dbSingleton = DatabaseSingleton.getDBSingletion();
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
	
	private View buildLayout() {
		
		RelativeLayout relativeLayout = new RelativeLayout(context);
		/*
		TextView textView = new TextView(context);
		textView.setText("This is boss!!!");
		*/
		viewArrayList = new ArrayList<View>();
		//viewArrayList.add(textView);
		
		ImageButton startButton = new ImageButton(context);
		startButton.setBackgroundResource(R.drawable.button_start_1);
		//startButton.setBackgroundColor(Color.argb(255, 255, 165, 0));// this is really ugly but it is for testing.
		//startButton.setTextColor(Color.WHITE);
		//startButton.setText("Start");
		viewArrayList.add(startButton);
		
		Button mapButton = new Button(context);
		//mapButton.setBackgroundColor(Color.argb(255, 255, 165, 0));
		//mapButton.setText("Map");
		mapButton.setBackgroundResource(R.drawable.button_map_1);
		viewArrayList.add(mapButton);
		
		//relativeLayout.setBackgroundDrawable(R.drawable.ic_launcher);
		
		//View view = new View(context);
		//view.setBackgroundResource(R.drawable.brown);
		//view.addChildrenForAccessibility(viewArrayList);
		///bannerView.setScaleType(ImageView.ScaleType.FIT_XY);
		//imageView.
		
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(widthInt, heightInt/7);
		
		layoutParams.setMargins(0, (heightInt/9), 0, 0);
		//relativeLayout.addView(view, layoutParams);
		//view.buildGraphices(viewArrayList);
		int buttonWidthInt = widthInt/3;
		int buttonHeightInt = heightInt/9;		
		
		RelativeLayout bannerRelativeLayout = new RelativeLayout(context);
		//bannerRelativeLayout.setBackgroundColor(Color.BLACK);
		bannerRelativeLayout.setBackgroundColor(Color.argb(200, 50, 50, 50));
		
		for(int i = 0; i < viewArrayList.size(); i++){ 
			RelativeLayout.LayoutParams relativeLayoutLayoutParams = new RelativeLayout.LayoutParams(buttonWidthInt, buttonHeightInt);
			relativeLayoutLayoutParams.setMargins((widthInt/12)+((widthInt/3)*i)+((widthInt/6)*i), ((heightInt/7)-(buttonHeightInt))/2, 0, 0);
			//relativeLayoutLayoutParams.setMargins((widthInt/12)+((widthInt/3)*i)+((widthInt/6)*i), (int)((heightInt/(1.0/63.0)))/*+((heightInt/9)*i)*/, 0, 0);
			bannerRelativeLayout.addView(viewArrayList.get(i), relativeLayoutLayoutParams);
		}
		relativeLayout.addView(bannerRelativeLayout, layoutParams);

		DriveSpectrumOnClickListener driveSpectrumOnClickListener = new DriveSpectrumOnClickListener();
		// would be nice if I would use for loop here...
		startButton.setOnClickListener(driveSpectrumOnClickListener);
		mapButton.setOnClickListener(driveSpectrumOnClickListener);
		relativeLayout.setBackgroundResource(R.drawable.greek_pattern_2);
		/*
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mainView = inflater.inflate(R.layout.banner_view, null, true);
		relativeLayout.addView(mainView, layoutParams);
		*/
		//relativeLayout.setBackgroundColor(Color.YELLOW);
		//bannerRelativeLayout.invalidate();
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
