package com.lynchsoftwareengineering.drivespectrum;

import java.util.Comparator;

import android.location.Location;

public class ComparatorPointTimeByLocation implements Comparator<PointTime> {
	private  PointTime pointTime;
	private double latDouble, lonDouble;
	
	 public ComparatorPointTimeByLocation(PointTime pointTime) {
		 this.latDouble  = pointTime.getLatDouble();
		 this.lonDouble = pointTime.getLonDouble();
		 this.pointTime = pointTime;
	}

	@Override
	public  int compare(PointTime pointTime, PointTime onePointTime) {
		float[] floatArray = new float[3];
		float[] oneFloatArray = new float[3];
		Location.distanceBetween(latDouble, lonDouble, pointTime.getLatDouble(), pointTime.getLonDouble(), floatArray);//distance(latDouble,  lonDouble ,pointTime.getLonDouble(), pointTime.getLatDouble());
		double distanceDouble = floatArray[0];
		Location.distanceBetween(latDouble,  lonDouble ,onePointTime.getLatDouble(), onePointTime.getLonDouble(), oneFloatArray);
		double oneDistanceDouble = oneFloatArray[0];
		if (oneDistanceDouble == distanceDouble){
			return 0;
		}else if (distanceDouble  <oneDistanceDouble) {
			return -1 ;
		}else{
			return 1;
		}
	}

	public PointTime getPointTime() {
		return pointTime;
	}
	
	
	public void setPointTime(PointTime pointTime) {
		this.pointTime = pointTime;
	}

}


