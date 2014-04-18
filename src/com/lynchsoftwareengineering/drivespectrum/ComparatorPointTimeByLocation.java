package com.lynchsoftwareengineering.drivespectrum;

import java.util.Comparator;

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
		double distanceDouble = distance(latDouble,  lonDouble ,pointTime.getLonDouble(), pointTime.getLatDouble());
		double oneDistanceDouble = distance(latDouble,  lonDouble ,onePointTime.getLonDouble(), onePointTime.getLatDouble());
		if (distanceDouble  <oneDistanceDouble) {
			return 1 ;
		}else if (distanceDouble  >oneDistanceDouble){
			return -1;
		}
		return 0; 
	}
	
	/** distance in Km
	 *  From http://www.geodatasource.com/developers/java
	 */
	public static double distance(double lat1, double lon1, double lat2, double lon2) {
		  double theta = lon1 - lon2;
		  double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
		  dist = Math.acos(dist);
		  dist = rad2deg(dist);
		  dist = dist * 60 * 1.1515;
		  dist = dist * 1.609344;
		  return (dist);
	}
 /**
  *  deg2red 
  *  From http://www.geodatasource.com/developers/java
  */
	private static double deg2rad(double deg) {
	  return (deg * Math.PI / 180.0);
	}
/**
 *  rad2deg
 *  From http://www.geodatasource.com/developers/java
 */
	private static double rad2deg(double rad) {
	  return (rad * 180 / Math.PI);
	}

	public PointTime getPointTime() {
		return pointTime;
	}
	
	
	public void setPointTime(PointTime pointTime) {
		this.pointTime = pointTime;
	}

}


