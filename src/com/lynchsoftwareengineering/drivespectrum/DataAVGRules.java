package com.lynchsoftwareengineering.drivespectrum;

import android.location.Location;
import android.util.Log;

/**
 * 
 * @author Aflynch
 *  This class has static variables to hold the magic numbers that govern the AVG functions.
 */
public class DataAVGRules {
	
	public static int DIEMETER_IN_METERS = 159;//15900 // this should not be a constant. MPS*15
	public static int RADIUS_IN_METERS = 79;//7950
	public static int MIN_ROUTE_NUM_IN_DIEMETER  =2;
	public static int BEARING_RANGE_DEGREES_TOLERANCE = 15;// this is plus minus

	public static float[] findLatLonRange(double rangeLatMinDouble,double rangeLonMinDouble,double rangeLatMaxDouble,double rangeLonMaxDouble){
		float[] latFloatArray = new float[3];
		float[] lonFloatArray = new float[3];
		float[] answerFloatArray = new float[2];
		Location.distanceBetween(rangeLatMinDouble, rangeLonMinDouble, rangeLatMaxDouble, rangeLonMinDouble, latFloatArray);//lat
		Location.distanceBetween(rangeLatMinDouble, rangeLonMinDouble, rangeLatMinDouble, rangeLonMaxDouble, lonFloatArray);//lon
		Log.d("distance", "lat in metters: "+latFloatArray[0]+"  lon in metters : "+lonFloatArray[0]);
		answerFloatArray[0] = latFloatArray[0];
		answerFloatArray[1]= lonFloatArray[0];
		return answerFloatArray;
	}
}
