package com.lynchsoftwareengineering.drivespectrum;

import java.io.File;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;

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
import android.view.View;
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
	int widthInt;
	int heightInt;
	int viewWidthInt;
	int viewHightInt;
	private final int NORTH_INT = 0, SOUTH_INT = 1, EAST_INT = 2 , WEST_INT = 3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		getScreenSize();
		initCheckDB();
		prepData();
		setContentView(buildLayout()); // Array problems here
	}
	
	private void prepData() {/* Really much of this needs to be changed. This is just the first build. This will be on the the first system I will come back to look over after my first demo.*/
		
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo wInfo = wifiManager.getConnectionInfo();
		String macAddress = wInfo.getMacAddress();
		File file = getDir(MainActivity.DB_NAME,Activity.MODE_PRIVATE);
		macAddressString = wInfo.getMacAddress();
		filePathString = file.getAbsolutePath();
		
		
		ArrayList<String> stringArrayList = dbSingleton.listAllFromDB();
		if (stringArrayList.size() == 0){
			return;// need to back at this latter and see if the return null still work
		} /* I could wright this in a way that would be more ready able, but I would lose speed. I think that 
		That will larger date set speed could be an issue.*/
		
		/*
		String[] gpsRowDateStringArray = stringArrayList.get(0).split("#");
		for(String string: gpsRowDateStringArray){
			Log.d("Date Test", string);
		}
		/*
		Location location = new Location(LOCATION_SERVICE);
		location.setLatitude(Double.parseDouble(gpsRowDateStringArray[0]));
		location.setLongitude(Double.parseDouble(gpsRowDateStringArray[1]));
		location.setSpeed(Float.parseFloat(gpsRowDateStringArray[2]));
		*/
		// Get first index values.
		
		/*
		indexValue2DArrayDouble[NORTH_INT][0] = 0;
		indexValue2DArrayDouble[NORTH_INT][1] = Double.parseDouble(gpsRowDateStringArray[1]);

		indexValue2DArrayDouble[SOUTH_INT][0] = 0;
		indexValue2DArrayDouble[SOUTH_INT][1] = Double.parseDouble(gpsRowDateStringArray[1]);
		
		indexValue2DArrayDouble[EAST_INT][0] = 0;
		indexValue2DArrayDouble[EAST_INT][0] = Double.parseDouble(gpsRowDateStringArray[0]);
		
		indexValue2DArrayDouble[WEST_INT][0] = 0;
		indexValue2DArrayDouble[WEST_INT][0] = Double.parseDouble(gpsRowDateStringArray[0]);
		*/
		
		String[] baseGPSRowStringArray = stringArrayList.get(0).split("#");
		latitudeMaxDouble = Double.parseDouble(baseGPSRowStringArray[0]);
		latitudeMinDouble = latitudeMaxDouble;
		longitudeMaxDouble = Double.parseDouble(baseGPSRowStringArray[1]);
		longitudeMinDouble = longitudeMaxDouble;
		maxSpeedDouble = Double.parseDouble(baseGPSRowStringArray[2]);

		
		//latitude N > 0
		//latitude S < 0		
		//longitude W is < 0
		//longitude E is > 0
		
		// reread first index. I know it was just easier for testing. 
		for(String gpsRowDateString :stringArrayList){
			String[] gpsRowDateStringArray = gpsRowDateString.split("#");
			double latitudeDouble = Double.parseDouble(gpsRowDateStringArray[0]);
			double longitudeDouble = Double.parseDouble(gpsRowDateStringArray[1]);
			double speedDouble = Double.parseDouble(gpsRowDateStringArray[2]);
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

		}
		Log.d("Date Test", ""+latitudeMinDouble);
		Log.d("Date Test", ""+latitudeMaxDouble);
		Log.d("Date Test", ""+longitudeMinDouble);
		Log.d("Date Test", ""+longitudeMaxDouble);
		Log.d("Date Test", ""+((maxSpeedDouble*60)*60)/1000);

		pointTimeArrayList = getRelivePointArrayList(stringArrayList);
	}

	private ArrayList<PointTime> getRelivePointArrayList(ArrayList<String> stringArrayList) {
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
		
		minLocation = null;
		Location location = maxLocation; // Recycle XD
		maxLocation = null;
		ArrayList<PointTime> pointTimeArrayList = new ArrayList<PointTime>();
		for(String string: stringArrayList){
			String[] gpsRowStringArray = string.split("#");
			double latitudeDouble = Double.parseDouble(gpsRowStringArray[0]);
			double longitudeDouble = Double.parseDouble(gpsRowStringArray[1]);// might be a problem here
			location.setLatitude(latitudeDouble);
			location.setLongitude(middleLongitudeDouble);
			float distanceYInMetersFloat = middleLocation.distanceTo(location);
			/*if (latitudeDouble < middleLatitudeDouble){
				distanceYInMetersFloat *= -1;
			}*/
			
			location.setLatitude(middleLatitudeDouble);
			location.setLongitude(longitudeDouble);
			float distanceXInMetersFloat = middleLocation.distanceTo(location);
			/*if(longitudeDouble < middleLongitudeDouble){
				distanceXInMetersFloat *= -1;
			}*/
			
			//if (lengthInMetersLongitudeDouble > lenghtInMetersLatitudeDouble)
			//new PointTime(xInt, yInt, speedMPS, timeString)
			
			//viewWidthInt is the use for both.
			pointTimeArrayList.add(new PointTime(convetDistcanceToPixels(distanceXInMetersFloat, viewWidthInt)+50/*demo offset*/, convetDistcanceToPixels(distanceYInMetersFloat, viewWidthInt), Float.parseFloat(gpsRowStringArray[2]),Long.parseLong( gpsRowStringArray[3])));
		}
		
		return pointTimeArrayList;
	}
	// The is something really wrong here. 
	private int convetDistcanceToPixels(double distanceInMetersDouble, int viewWidthHightInt) {
		double maxDistacneDouble = (lenghtInMetersLatitudeDouble > lengthInMetersLongitudeDouble )? lenghtInMetersLatitudeDouble : lengthInMetersLongitudeDouble;
		return (int)((distanceInMetersDouble/maxDistacneDouble)*viewWidthHightInt);
	}

	private double findDifference(double maxDouble, double minDouble) {// 5/(5-5) == 5/0 XD
		if (maxDouble >= 0 && minDouble >= 0){
			return (minDouble - maxDouble);
		} else if (maxDouble < 0 && minDouble < 0){
			return (minDouble - maxDouble);
		} else if (maxDouble >= 0 && minDouble < 0){
			return (maxDouble + minDouble);
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

	private double findMiddle(double maxDouble, double minDouble) {// 5/(5-5) == 5/0 XD
		if (maxDouble >= 0 && minDouble >= 0){
			return minDouble +((minDouble - maxDouble)/2.0);
		} else if (maxDouble < 0 && minDouble < 0){
			return minDouble-(((minDouble - maxDouble))/2.0);
		} else if (maxDouble >= 0 && minDouble < 0){
			return (((maxDouble + minDouble)/2.0)>= 0)? maxDouble - ((maxDouble + minDouble)/2.0) : ((maxDouble + minDouble)/2)- minDouble;
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
		dbSingleton = DBSingleton.getInstanceOfDataBaseSingleton(filePathString, macAddressString);// No check need new version :)
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
		relativeLayoutLayoutParams.setMargins(widthInt/10, heightInt/10, 10,10);
		relativeLayout.addView(drawView, relativeLayoutLayoutParams);
		
		relativeLayout.setBackgroundColor(Color.YELLOW);
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
			setBackgroundColor(Color.argb(255, 255, 165, 0));
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
			paint.setStrokeWidth(3);
			for (int i = 0; i < arraySizeInt ; i++){
				pointTime = pointTimeArrayList.get(i);
				pointTime2 = pointTimeArrayList.get(i+1);
				long timeLong = pointTime.getTimeInMillsLong();
				long timeLong2 = pointTime.getTimeInMillsLong();
		//		if ( (timeLong2 -timeLong)< 30000 && pointTime.speedMPS >3 ){
					int colorInt = (int)((pointTime.speedMPS*60)*60)*2;
					paint.setColor(Color.argb(255,colorInt,colorInt,colorInt));
					canvas.drawLine((float)pointTime.x,(float)pointTime.y, (float)pointTime2.x, (float)pointTime2.y, paint);
					pointTime = pointTimeArrayList.get(i);
					pointTime2 = pointTimeArrayList.get(i);	
			//	}
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

}
