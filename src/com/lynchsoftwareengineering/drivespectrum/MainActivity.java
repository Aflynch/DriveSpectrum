package com.lynchsoftwareengineering.drivespectrum;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.media.Image;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
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
	LocationManager locationManager;
	DBSingleton dbSingleton;
	Calendar calendar;
	WifiInfo wifiInfo;
	File file;
	ArrayList<View> viewArrayList;
	Context context;
	
	public static final String DB_NAME = "DB_NAME";
	int widthInt; 
	int heightInt;
	
	@Override
	/* 
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		getScreenSize();
		setContentView(buildLayout());
		startGPSIntentService();
		manageStateOfDatabase();
	//	DBSingleton dbSingleton = DBSingleton.getInstanceOfDataBaseSingleton(file.getAbsolutePath(), wInfo.getMacAddress());
	//	ArrayList<String> stringArrayList= dbSingleton.listAllFromDB();

		//test
	}


	private void manageStateOfDatabase() {
		DBSingleton bdSingleton = DBSingleton.getInstanceOfDataBaseSingleton(file.getAbsolutePath(), wifiInfo.getMacAddress());
		int caseInt = bdSingleton.checkDatabaseState();
		switch(caseInt){
			case(DBSingleton.ALL_NEW_DBS): Log.d("Table",""+caseInt+" no acction was taken.");// no case needed GPSSingleton will data and up data tables
				break;
			case(DBSingleton.MADE_PATH_AND_AVG_TABLES):// this is not yet tested! 
				Intent intent = new Intent(this, BuildPathAndAVGTableIntentService.class);
				intent.putExtra(GPSIntentService.MAC_ADDRESS_KEY+"", wifiInfo.getMacAddress());
				intent.putExtra(GPSIntentService.FILE_PATH_KEY+"", file.getAbsolutePath());
				Log.d("Table","BuildPathAndAVGTableIntentServic is about to be started.");
				startService(intent);
				break;
			case(DBSingleton.MADE_AVG_TABLE): Log.d("Table",""+caseInt+" no acction was taken.");
				break; 
			case(DBSingleton.ALL_TABLES_FOUND):Log.d("Table", "All tables found.");
			dbSingleton = DBSingleton.getInstanceOfDataBaseSingletion();
			Log.d("Table", "MA: mainTable ="+dbSingleton.getRowCountOfTable(DBContractClass.GPSEntry.TABLE_NAME));
			Log.d("Table", "MA: PathTable ="+dbSingleton.getRowCountOfTable(DBContractClass.PathGPSEntry.TABLE_NAME));
			Log.d("Table", "MA: AVGTable ="+dbSingleton.getRowCountOfTable(DBContractClass.AVGGPSEntry.TABLE_NAME));
				break;
			default:Log.d("Table","WARNING CASE "+ caseInt+" NOT COVED: THIS IS DEFAULT CASE!!"); 
		}
	}


	private void startGPSIntentService() {
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		wifiInfo = wifiManager.getConnectionInfo();
		String macAddress = wifiInfo.getMacAddress();
		file = getDir(DB_NAME,Activity.MODE_PRIVATE);
		Intent intent = new Intent(this, GPSIntentService.class);
		intent.putExtra(GPSIntentService.MAC_ADDRESS_KEY+"", wifiInfo.getMacAddress());
		intent.putExtra(GPSIntentService.FILE_PATH_KEY+"", file.getAbsolutePath());
		startService(intent);
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
		startButton.setBackgroundResource(R.drawable.light_stats_button);
		//startButton.setBackgroundColor(Color.argb(255, 255, 165, 0));// this is really ugly but it is for testing.
		//startButton.setTextColor(Color.WHITE);
		//startButton.setText("Start");
		viewArrayList.add(startButton);
		
		Button mapButton = new Button(context);
		//mapButton.setBackgroundColor(Color.argb(255, 255, 165, 0));
		//mapButton.setText("Map");
		mapButton.setBackgroundResource(R.drawable.light_map_button);
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
		bannerRelativeLayout.setBackgroundColor(Color.argb(0, 50, 50, 50));
		
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
		relativeLayout.setBackgroundResource(R.drawable.back_ground);
		/*
		LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View mainView = inflater.inflate(R.layout.banner_view, null, true);
		relativeLayout.addView(mainView, layoutParams);
		*/
		//relativeLayout.setBackgroundColor(Color.YELLOW);
		//bannerRelativeLayout.invalidate();
		return relativeLayout;
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

	private class DriveSpectrumOnClickListener implements OnClickListener{
		String tagString  = "tag"; 
		@Override
		public void onClick(View view) {
			Log.d("DB_TEST","onClick was called");

			if(view.equals(viewArrayList.get(0))){
				Toast.makeText(context, "Some day when you are good I will make this work.", Toast.LENGTH_LONG).show();
			} else {
				context.startActivity(new Intent(context, SpectrumActivity.class));
			}
			//Toast.makeText(context, tagString, Toast.LENGTH_SHORT).show();
		}
		
	}

}
