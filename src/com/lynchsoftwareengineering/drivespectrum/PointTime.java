package com.lynchsoftwareengineering.drivespectrum;

import android.graphics.Point;
import android.util.Log;

public class PointTime extends Point implements Comparable<PointTime > {// lenght -15,+5 , 
	public String routeString;
	public Double latDouble, lonDouble;
	public long timeInMillsLong;
	public String  macAddressString;
	public float speedMPSFloat;
	public float bearingFloat;
	public PointTime(){
		
	}
	public PointTime(int xInt,int yInt,float speedMPSFloat, float bearingFloat, long  timeInMillsLong, String macAddressString, String routeString){
		super.set(xInt, yInt);
		this.speedMPSFloat = speedMPSFloat;
		this.bearingFloat = bearingFloat;
		this.timeInMillsLong = timeInMillsLong;
		this.macAddressString = macAddressString;
		this.routeString = routeString;
	}	
	public PointTime(double latDouble,double lonDouble,float speedMPSFloat, float bearingFloat, long  timeInMillsLong, String macAddressString, String routeString){
		this.latDouble = latDouble;
		this.lonDouble = lonDouble;
		this.speedMPSFloat = speedMPSFloat;
		this.bearingFloat = bearingFloat;
		this.timeInMillsLong = timeInMillsLong;
		this.macAddressString = macAddressString;
		this.routeString = routeString;
	}
	
	@Override
		public boolean equals(Object o) {
			PointTime pointTime = (PointTime) o;
//			Log.d("equals", (pointTime.getTimeInMillsLong() == getTimeInMillsLong())? "true": "false");
//			Log.d("equals", ""+timeInMillsLong +"  :  "+pointTime.getTimeInMillsLong());
			return (pointTime.getTimeInMillsLong() == getTimeInMillsLong())? true: false;
		}	
	
	public long getTimeInMillsLong() {
		return timeInMillsLong;
	}
	public void setTimeInMillsLong(Long timeInMillsLong) {
		this.timeInMillsLong = timeInMillsLong;
	}
	public float getSpeedMPS() {
		return speedMPSFloat;
	}
	public void setSpeedMPS(float speedMPS) {
		this.speedMPSFloat = speedMPS;
	}
	public String getMacAddressString() {
		return macAddressString;
	}
	public void setTimeInMillsLong(long timeInMillsLong) {
		this.timeInMillsLong = timeInMillsLong;
	}
	public String getRouteString() {
		return routeString;
	}
	public void setRouteString(String routeString) {
		this.routeString = routeString;
	}
	
	public Double getLatDouble() {
		return latDouble;
	}
	public void setLatDouble(Double latDouble) {
		this.latDouble = latDouble;
	}
	public Double getLonDouble() {
		return lonDouble;
	}
	public void setLonDouble(Double lonDouble) {
		this.lonDouble = lonDouble;
	}
	public float getBearingFloat() {
		return bearingFloat;
	}
	public void setBearingFloat(float bearingFloat) {
		this.bearingFloat = bearingFloat;
	}
	public void setMacAddressString(String macAddressString) {
		this.macAddressString = macAddressString;
	}
	@Override
	public int compareTo(PointTime another) {
		if (getTimeInMillsLong() <  another.getTimeInMillsLong()){
			return 1;
		}else if (getTimeInMillsLong() > another.getTimeInMillsLong()){
			return -1;
		}else{
			return 0;
		}
	}
	
}
