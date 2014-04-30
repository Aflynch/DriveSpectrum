package com.lynchsoftwareengineering.drivespectrum;

import java.util.ArrayList;

import android.hardware.Camera.Size;
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
	public static int NUMBER_OF_POINTS_TO_AVG = 10;
	public static int DATA_BACH_SIZE = 50;

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
	
	public static ArrayList<PointTime> computAverageForGPSData(ArrayList<PointTime>  pointTimeArrayList){
		String routeNameString;
		ArrayList<PointTime> newPointTimeArrayList = new ArrayList<PointTime>();
		ArrayList<PointTime> routeGroupPointTimeArrayList = new ArrayList<PointTime>();
		for(int i = 0; i < pointTimeArrayList.size(); ){
			routeNameString = pointTimeArrayList.get(i).getRouteString();
			cutGroupFromPointTimeArrayListIntoRouteGroupPointTimeArrayList(routeGroupPointTimeArrayList, pointTimeArrayList, routeNameString);
			checkEquality(routeGroupPointTimeArrayList);
			averageGPSPoints(routeGroupPointTimeArrayList, newPointTimeArrayList);
		}
		int sum = 1;
		for(int i = 0; i < 4; i++){
			sum+=10;
		}
		switch(sum){
			case(41): Log.d("Status update", "There's no solution."); 
		}
		return newPointTimeArrayList;
	}
	
	private static void averageGPSPoints(ArrayList<PointTime> routeGroupPointTimeArrayList, ArrayList<PointTime> newPointTimeArrayList) {
		float speedFloat =0;
		float numberOfAVGPerPointFloat = 0;
		boolean firstRun = true;
		if(routeGroupPointTimeArrayList.size() <= NUMBER_OF_POINTS_TO_AVG){
			newPointTimeArrayList.addAll(routeGroupPointTimeArrayList);
			routeGroupPointTimeArrayList.clear();
			return;
		}
		//size cute off
		while(routeGroupPointTimeArrayList.size()>0){
			if(routeGroupPointTimeArrayList.size()>= NUMBER_OF_POINTS_TO_AVG){
				for(int i = 0; i < NUMBER_OF_POINTS_TO_AVG; i++){
					//number of times averaged
					speedFloat += (routeGroupPointTimeArrayList.get(i).getNumberOfAVGsInt()*routeGroupPointTimeArrayList.get(i).getSpeedMPSFloat());
					//numberOfAVGPerPointFloat++;
					numberOfAVGPerPointFloat += routeGroupPointTimeArrayList.get(i).getNumberOfAVGsInt();
				}
				if(firstRun){
					routeGroupPointTimeArrayList.get(0).setSpeedMPSFloat(speedFloat/numberOfAVGPerPointFloat);
					newPointTimeArrayList.add(routeGroupPointTimeArrayList.get(0));
					firstRun = false;
				}else{
					routeGroupPointTimeArrayList.get(NUMBER_OF_POINTS_TO_AVG/2).setSpeedMPSFloat(speedFloat/numberOfAVGPerPointFloat);
					newPointTimeArrayList.add(routeGroupPointTimeArrayList.get(NUMBER_OF_POINTS_TO_AVG/2));					
				}
				for(int i = NUMBER_OF_POINTS_TO_AVG-1; i >=0; i--){
					routeGroupPointTimeArrayList.remove(i);
				}
			}else{
				for(int i = 0; i < routeGroupPointTimeArrayList.size(); i++){
					//number of times avraged
					speedFloat += (routeGroupPointTimeArrayList.get(i).getNumberOfAVGsInt()*routeGroupPointTimeArrayList.get(i).getSpeedMPSFloat());
					//numberOfAVGPerPointFloat ++;
					numberOfAVGPerPointFloat += routeGroupPointTimeArrayList.get(i).getNumberOfAVGsInt();
				}
				routeGroupPointTimeArrayList.get(routeGroupPointTimeArrayList.size()-1).setSpeedMPSFloat(speedFloat/numberOfAVGPerPointFloat);
				newPointTimeArrayList.add(routeGroupPointTimeArrayList.get(routeGroupPointTimeArrayList.size()-1));	
				routeGroupPointTimeArrayList.clear();
			}
			speedFloat = 0;
			numberOfAVGPerPointFloat = 0;
		}
	}

	private static void checkEquality(ArrayList<PointTime> routeGroupPointTimeArrayList) {
		boolean isEuqal = true;
		for(int i = 1; i < routeGroupPointTimeArrayList.size(); i++){
			if(!routeGroupPointTimeArrayList.get(i).getRouteString().equals(routeGroupPointTimeArrayList.get(i-1).getRouteString())){
				isEuqal = false;
			}
		}
		Log.d("route equality check", (isEuqal)?"rount is equal"+routeGroupPointTimeArrayList.size():"route not equal"+routeGroupPointTimeArrayList.size());
	}

	private static void cutGroupFromPointTimeArrayListIntoRouteGroupPointTimeArrayList(ArrayList<PointTime>routeGroupPointTimeArrayList,ArrayList<PointTime> pointTimeArrayList, String routeNameString){
		//for(PointTime pointTime: pointTimeArrayList){ Concurrent modification error ...
		for(int i =0 ; i< pointTimeArrayList.size(); i++){
			if (pointTimeArrayList.get(i).getRouteString().equals(routeNameString)){
				routeGroupPointTimeArrayList.add(pointTimeArrayList.get(i));
				pointTimeArrayList.remove(pointTimeArrayList.get(i));
				i--;
			}
		}
	}

}
