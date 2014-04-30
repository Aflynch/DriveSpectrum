package com.lynchsoftwareengineering.drivespectrum;

import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

import com.lynchsoftwareengineering.drivespectrum.DBSingleton.DataFilter;

import dalvik.system.BaseDexClassLoader;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Location;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SpectrumActivity extends Activity {
	Context context;
	DBSingleton dbSingleton;
	ArrayList<PointTime> pointTimeArrayList;
	String filePathString;
	String macAddressString;
	double latitudeMaxDouble ,latitudeMinDouble ,longitudeMaxDouble, longitudeMinDouble, maxSpeedDouble, lenghtInMetersLatitudeDouble, lengthInMetersLongitudeDouble;
	int testIndexInt;
	int widthInt;	
	int heightInt;
	int viewWidthInt;
	int viewHightInt;
	private final int LATITUDE_INT = 0, LONGITUDE_INT = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		getScreenSize();
		setUpDatabase();
		initCheckDB();
		prepData();
		setContentView(buildLayout()); // Array problems here
		//33.97941432#-84.01260397#30.1#1396312154436#178.0
		// 34.00009497#-84.16147024#19.5#1396312154436#355.1
	}
	
	private void prepData() {/* Really much of this needs to be changed. This is just the first build. This will be on the the first system I will come back to look over after my first demo.*/
		
		
		//setUpDatabase();  // Getting instance of SBSingleton.
		
		//ArrayList<String> stringArrayList = getArrayListStringGPSDataAndSetStartMaxAndMinValues();// uses String.split to pull the data out in to datatypes 
		//then sets the base max and min values (lat, lon, and speed). 
		
		//33.97941432#-84.01260397#30.1#1396312154436#178.0
		// 34.00009497#-84.16147024#19.5#1396312154436#355.1
//		ArrayList<PointTime> pointTimeArrayList = getArrayListPointTimeGPSDataAndSetStartMaxAndMinValues();
//		//pointTimeArrayList = MakeTestData.getTestPointTimeArrayList(0, 0, 0); // Testing change
//		DBSingleton.DataFilter dataFilter = sharedPerencesSingleton.new DataFilter();
//		pointTimeArrayList = dataFilter.processData(pointTimeArrayList);
//		pointTimeArrayList = DataAVGRules.computAverageForGPSData(pointTimeArrayList);
		//Testing changes
		Log.d("Running",  "SA: pointTimeArrayList count = " +pointTimeArrayList.size() );
		
		findMaxAndMinValues(pointTimeArrayList);// does what it sounds like
		Log.d("Running",  "SA: pointTimeArrayList count = " +pointTimeArrayList.size() );

		this.pointTimeArrayList = getRelivePointArrayList(pointTimeArrayList);
		Log.d("Running",  "SA: pointTimeArrayList count = " +this.pointTimeArrayList.size() );
	}

	private void findMaxAndMinValues(ArrayList<PointTime> pointTimeArrayList) {
		//latitude N > 0
		//latitude S < 0		
		//longitude W is < 0
		//longitude E is > 0
		PointTime startPointTime = pointTimeArrayList.get(0);
		latitudeMaxDouble = startPointTime.getLatDouble();
		latitudeMinDouble = latitudeMaxDouble;
		longitudeMaxDouble = startPointTime.getLonDouble();
		longitudeMinDouble = longitudeMaxDouble;
		// reread first index. I know it was just easier for testing. 
		int arraylistSizeInt = pointTimeArrayList.size()-1;
		for(int i  = 0; i <arraylistSizeInt; i++){
			
			PointTime pointTime = pointTimeArrayList.get(i);
			PointTime onePointTime = pointTimeArrayList.get(i+1);
			double latitudeDouble = pointTime.getLatDouble();
			double longitudeDouble = pointTime.getLonDouble();
			double speedDouble = pointTime.getSpeedMPS();
		//	if (!oneGPSRowDataStringArray[3].equals("0.0") || !gpsRowDateStringArray[3].equals("0.0")){
		//	if ((onePointTime.getTimeInMillsLong() -pointTime.getTimeInMillsLong())<GPSIntentService.MAX_TIME_OUT_TIME){// should not be  needed
				if( latitudeDouble> latitudeMaxDouble){
					latitudeMaxDouble = latitudeDouble;	
				}else if(latitudeDouble < latitudeMinDouble){
					latitudeMinDouble = latitudeDouble;
				}// one location could need to set both Lat and Long
				if(longitudeDouble > longitudeMaxDouble){
					longitudeMaxDouble = longitudeDouble;
				}else if(longitudeDouble < longitudeMinDouble){
					longitudeMinDouble = longitudeDouble;
				}
				if(speedDouble > maxSpeedDouble){
					maxSpeedDouble = speedDouble;
				}
		//	}
			//}
		}
		Log.d("Data Test", ""+latitudeMinDouble);
		Log.d("Data Test", ""+latitudeMaxDouble);
		Log.d("Data Test", ""+longitudeMinDouble);
		Log.d("Data Test", ""+longitudeMaxDouble);
		Log.d("Data Test", ""+((maxSpeedDouble*60)*60)/1000);
	}

	private ArrayList<PointTime> getArrayListPointTimeGPSDataAndSetStartMaxAndMinValues(ArrayList<PointTime> pointTimeArraylist) {
		if (pointTimeArraylist.size() == 0){
			return pointTimeArraylist;// need to back at this latter and see if the return null still work
		}
	//	pointTimeArraylist = MakeTestData.getTestPointTimeArrayList(0, 0, 0);// Testing change 
//		ArrayList<String> stringArraylist = new ArrayList<String>();
//		stringArraylist.add("33.97941432#-84.01260397#30.1#1396312154436#178.0");
//		stringArraylist.add("33.97941432#-80.01260397#30.1#1396312154436#178.0");
//		stringArraylist.add("35.97941432#-82.01260397#30.1#1396312154436#178.0");
//		for(String string: stringArraylist){
//			Log.d("running", string);
//		}
		PointTime pointTime = pointTimeArraylist.get(0);
		latitudeMaxDouble = pointTime.getLatDouble();
		latitudeMinDouble = latitudeMaxDouble;
		longitudeMaxDouble = pointTime.getLonDouble();
		longitudeMinDouble = longitudeMaxDouble;
		maxSpeedDouble = pointTime.getSpeedMPS();
		return pointTimeArraylist;
	}

	private void setUpDatabase() {
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo wInfo = wifiManager.getConnectionInfo();
		String macAddress = wInfo.getMacAddress();
		File file = getDir(MainActivity.DB_NAME,Activity.MODE_PRIVATE);
		macAddressString = wInfo.getMacAddress();
		filePathString = file.getAbsolutePath();
	}

	private ArrayList<PointTime> getRelivePointArrayList(ArrayList<PointTime> pointTimeArrayList) {
		Location middleLocation = new Location(LOCATION_SERVICE);
		Location minLocation = new Location(LOCATION_SERVICE);
		Location maxLocation = new Location(LOCATION_SERVICE);
		
		double middleLongitudeDouble = findMiddle(longitudeMaxDouble,longitudeMinDouble);
		double middleLatitudeDouble = findMiddle(latitudeMaxDouble, latitudeMinDouble);
		
		//double longitudeLengthDouble = findDifference(longitudeMaxDouble, longitudeMaxDouble);
		//double latitudeLengthDouble = findDifference(latitudeMaxDouble, longitudeMinDouble);
		
		middleLocation.setLatitude(middleLatitudeDouble);
		middleLocation.setLongitude(middleLongitudeDouble);
		
		minLocation.setLongitude(longitudeMinDouble);
		minLocation.setLatitude(latitudeMaxDouble);
		
		maxLocation.setLongitude(longitudeMaxDouble);
		maxLocation.setLatitude(latitudeMaxDouble);
		lengthInMetersLongitudeDouble = minLocation.distanceTo(maxLocation);
		
		minLocation.setLongitude(longitudeMaxDouble);
		minLocation.setLatitude(latitudeMinDouble);
		
		maxLocation.setLongitude(longitudeMaxDouble);
		maxLocation.setLatitude(latitudeMaxDouble);
		lenghtInMetersLatitudeDouble = minLocation.distanceTo(maxLocation);
		Log.d("lat", "lat " + lenghtInMetersLatitudeDouble + " lon"+ lengthInMetersLongitudeDouble);
		Location location = maxLocation; // Recycle XD
		minLocation.setLongitude(longitudeMinDouble);
		minLocation.setLatitude(latitudeMaxDouble);///testing!!!!!
		maxLocation = null;
		for(PointTime pointTime : pointTimeArrayList){
			double latitudeDouble = pointTime.getLatDouble();
			double longitudeDouble = pointTime.getLonDouble();// might be a problem here
			
			location.setLatitude(latitudeDouble);
			location.setLongitude(longitudeMinDouble);
			float distanceYInMetersFloat = minLocation.distanceTo(location);
			
			location.setLatitude(latitudeMaxDouble);////testing!!!!
			location.setLongitude(longitudeDouble);
			float distanceXInMetersFloat = minLocation.distanceTo(location);
			pointTime.set(convetDistcanceToPixels(distanceXInMetersFloat, viewWidthInt), convetDistcanceToPixels(distanceYInMetersFloat, viewWidthInt));
//			new PointTime(xInt, yInt, speedMPSFloat, bearingFloat, timeInMillsLong, macAddressString, routeString);// just chagned PointTime object!!! things need to be fixed. 
//			pointTimeArrayList.add(new PointTime(convetDistcanceToPixels(distanceXInMetersFloat, viewWidthInt)/*demo offset  50*/, convetDistcanceToPixels(distanceYInMetersFloat, viewWidthInt), Float.parseFloat(gpsRowStringArray[2]),Long.parseLong( gpsRowStringArray[3])));

//	  pointTimeArrayList.add(new PointTime(convetDistcanceToPixels(distanceXInMetersFloat, viewWidthInt)/*demo offset  50*/, convetDistcanceToPixels(distanceYInMetersFloat, viewWidthInt), Float.parseFloat(gpsRowStringArray[2]),Long.parseLong( gpsRowStringArray[3])));
		}
		return pointTimeArrayList;
	}
	// The is something really wrong here. 
	private int convetDistcanceToPixels(double distanceInMetersDouble, int viewWidthHightInt) {
		double maxDistacneDouble = (lenghtInMetersLatitudeDouble >lengthInMetersLongitudeDouble )? lenghtInMetersLatitudeDouble : lengthInMetersLongitudeDouble;
		return (int) ((distanceInMetersDouble / maxDistacneDouble) *( viewWidthHightInt*.96) +(viewWidthHightInt*.02));
	}

	private  double findDifference(double maxDouble, double minDouble) {// 5/(5-5) == 5/0 XD
		if (maxDouble >= 0 && minDouble >= 0){
			return (maxDouble - minDouble);
		} else if (maxDouble < 0 ){
			return Math.abs( (minDouble - maxDouble));
		} else if (maxDouble >= 0 && minDouble < 0){
			return (maxDouble + (minDouble*-1));
		} else{
			String errorString = "Case not covered in method findWidth in class SpectrumActivity";
			Log.d("Test Data", errorString );
			try {
				throw new Exception(errorString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}

	private  double findMiddle(double maxDouble, double minDouble) {// 5/(5-5) == 5/0 XD
		if (maxDouble >= 0 && minDouble >= 0){
			return (minDouble + maxDouble)/2.0;
		} else if (maxDouble < 0 ){
			return (minDouble + maxDouble)/2.0;
		} else if (maxDouble >= 0 && minDouble < 0){
			return maxDouble-((maxDouble -minDouble)/2);
		} else{
			String errorString = "Case not covered in method findWidth in class SpectrumActivity";
			Log.d("Test Data", errorString );
			try {
				throw new Exception(errorString);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 0;
	}


	private void initCheckDB() {
		dbSingleton = DBSingleton.getInstanceOfDataBaseSingleton(filePathString, macAddressString);//  No check need new version :)
		int caseInt = dbSingleton.checkDatabaseState();
		if(caseInt == DBSingleton.ALL_TABLES_FOUND){
			pointTimeArrayList = dbSingleton.getDataInSegmentsFromAVGTable(1000, "SELECT * FROM ", DBContractClass.GlobalGPSEntry.TABLE_NAME);
		}else{
			pointTimeArrayList = dbSingleton.getDataInSegmentsFromAVGTable(1000, "SELECT * FROM ", DBContractClass.GPSEntry.TABLE_NAME);
		}
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
		
		viewHightInt = (int)(heightInt*.8);
		viewWidthInt = (int)(widthInt*.8);
	}
	
	private RelativeLayout buildLayout() {
		
		RelativeLayout relativeLayout = new RelativeLayout(context);
		TextView textView = new TextView(context);
		textView.setText("This is boss!!!");
		
		DrawView drawView  = new DrawView(context);
		// this is really ugly but it is for testing.

		//relativeLayout.setBackgroundDrawable(R.drawable.ic_launcher);
		
		RelativeLayout.LayoutParams relativeLayoutLayoutParams = new RelativeLayout.LayoutParams(viewWidthInt, viewHightInt);
		relativeLayoutLayoutParams.setMargins(widthInt/10, heightInt/10,widthInt/ 10,heightInt/10);
		relativeLayout.addView(drawView, relativeLayoutLayoutParams);
		
		relativeLayout.setBackgroundResource(R.drawable.back_ground);
		return relativeLayout;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.spectrum_view, menu);
		return true;
	}
	
	private class DrawView extends View{
		ArrayList<LinePaintDataOject> linePaintDataOjectArrayList = new ArrayList<LinePaintDataOject>();
		public DrawView(Context context) {
			super(context);
			testIndexInt = 0;
			setBackgroundColor(Color.argb(50, 0, 0, 0));
			SpectrumOnTouchListeneren specturmOnTouchListener = new SpectrumOnTouchListeneren(this);
			setOnTouchListener(specturmOnTouchListener);
		//	fillFloatArray();
		}
//		
//		private void fillFloatArray() {// EDIT list??? MV???
//			
//			int arraySizeInt = pointTimeArrayList.size()-1;
//			PointTime pointTime;
//			PointTime pointTime2;
//			LinePaintDataOject linePaintDataOject = new LinePaintDataOject();
//			for (int i = 0; i < arraySizeInt ; i++){// need to build of edge cases
//				pointTime = pointTimeArrayList.get(i);
//				pointTime2 = pointTimeArrayList.get(i+1);
//				
//				long timeLong  = pointTime.getTimeInMillsLong();
//				long timeLong2 = pointTime2.getTimeInMillsLong();		
//				if ((timeLong2 -timeLong)> 30000){
//					pointTimeArrayList.remove(i);
//				}
//			}
//
	
			/*
			float x1 = 0;
			float y1 = 0;
			float x2 = 3;
			float y2 = 3;
			int alphaInt = 255;
			int redInt = 100;
			int greenInt = 100;
			int blueInt = 100;
			
			for(int i = 0; i < 100; i++){
				LinePaintDataOject linePaintDataOject = new LinePaintDataOject();
				linePaintDataOject.setXYPointFloatArray(x1, y1, x2, y2);
				linePaintDataOject.setColorForPaintIntArray(alphaInt, redInt, greenInt, blueInt);
				x1 +=3;
				y1 +=3;
				x2 +=1;
				y2 *= 2;
				redInt +=10;
				linePaintDataOjectArrayList.add(linePaintDataOject); 
			}
			*/
//		}
//
//		private int convetStringTimeToInt(String timeString) {// 00:00 == new drive 
//			int lengthInt = timeString.length()+1;
//			int hhInt = Integer.parseInt(timeString.substring(lengthInt-18, lengthInt-16));
//			int mmInt = Integer.parseInt(timeString.substring(lengthInt-15, lengthInt-13));
//			int ssInt = Integer.parseInt(timeString.substring(lengthInt-12, lengthInt -10));
//			return ((hhInt*60)*60)+(mmInt*60)+(ssInt);
//		}

		@Override
		protected void onDraw(Canvas canvas) {
			super.onDraw(canvas);
			/* test code
			float[] pointfloatArray = {10,10, 100,100};
			Paint paint  = new Paint();
			paint.setColor(Color.RED);
			testMethod(canvas);
			canvas.drawLine(10, 10, 275, 275, paint);
			canvas.drawRect((float)300.0,(float) 300.0, (float) 400.0, (float) 400.0, paint);
			canvas.drawLines(pointfloatArray, 0, 1, paint );
			*/ 
			
			
			int arraySizeInt = pointTimeArrayList.size()-1;
			PointTime pointTime;
			PointTime pointTime2;
			Paint paint= new Paint();
			//canvas.clipRect(25, 25, 50, 50);
			paint.setStrokeWidth(3);
			int routeCheckCountInt = 0;
			Log.d("Table", "bitch'n "+ testIndexInt);
			for (int i = 0; i <  arraySizeInt/*Math.min(arraySizeInt, testIndexInt)*/ ; i++){
				pointTime = pointTimeArrayList.get(i);
				pointTime2 = pointTimeArrayList.get(i+1);
				long timeLong = pointTime.getTimeInMillsLong();
				long timeLong2 = pointTime2.getTimeInMillsLong();
				//if ( (timeLong2 -timeLong)< GPSIntentService.MAX_TIME_OUT_TIME && pointTime.speedMPS >GPSIntentService.MIN_SPEED_MPS ){
				if(pointTime.getRouteString().equals(pointTime2.getRouteString())){ //Testing change
					int colorInt =( (int)(255*(pointTime.getSpeedMPSFloat()/ maxSpeedDouble)));
					paint.setColor(Color.argb(255,255-colorInt,colorInt,0));
//				canvas.drawLine(0, 0, 400, 400, paint);
					canvas.drawLine((float)pointTime.getX(),(float)pointTime.getY(), (float)pointTime2.getX(), (float)pointTime2.getY(), paint);
					pointTime = pointTimeArrayList.get(i);
					pointTime2 = pointTimeArrayList.get(i);	
//					Log.d("Route Check", " RouteCheckCoutInt = "+routeCheckCountInt);
				}else{
					routeCheckCountInt++;
					Log.d("Route Check", " RouteCheckCoutInt = "+routeCheckCountInt);
				}
			}
			/*
			for (LinePaintDataOject linePaintDataOject : linePaintDataOjectArrayList){
				Paint paint2 = new Paint();
				int[] paintColorInt = linePaintDataOject.getColorForPaintIntArray();
				paint.setColor(Color.argb( paintColorInt[0], paintColorInt[1], paintColorInt[2], paintColorInt[3]));
				canvas.drawLines(linePaintDataOject.getXyPointsFloatArray(), paint2);
			}
			Log.d("DB_TEST", "onDraw was called");
			*/
		}

		private void testMethod(Canvas canvas) {
			Paint paint = new Paint();
			paint.setColor(Color.argb(255, 0, 255, 0));
			canvas.drawLine(400, 10, 100, 400, paint);
		}
		
	}

	public class SpectrumOnTouchListeneren implements OnTouchListener{
		DrawView drawView;
		public SpectrumOnTouchListeneren(DrawView drawView) {
			this.drawView = drawView;
		}

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			testIndexInt+=50;
			drawView.invalidate();
			return false;
		}
		
	}
	
}
