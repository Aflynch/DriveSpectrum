package com.lynchsoftwareengineering.drivespectrum;

import android.graphics.Point;
import android.util.Log;

public class PointTime extends Point implements Comparable<PointTime > {// lenght -15,+5 , 
	private String routeString;
	private Double latDouble, lonDouble;
	private long timeInMillsLong;
	private String  macAddressString;
	private float speedMPSFloat;
	private float bearingFloat;
	private int numberOfAVGsInt;
	public PointTime(){
		
	}
	public PointTime(int xInt,int yInt,float speedMPSFloat, float bearingFloat, long  timeInMillsLong, String macAddressString, String routeString){
		super.set(xInt, yInt);
		this.speedMPSFloat = speedMPSFloat;
		this.bearingFloat = bearingFloat;
		this.timeInMillsLong = timeInMillsLong;
		this.macAddressString = macAddressString;
		this.routeString = routeString;
		this.numberOfAVGsInt = 1;
	}	
	public PointTime(double latDouble,double lonDouble,float speedMPSFloat, float bearingFloat, long  timeInMillsLong, String macAddressString, String routeString){
		this.latDouble = latDouble;
		this.lonDouble = lonDouble;
		this.speedMPSFloat = speedMPSFloat;
		this.bearingFloat = bearingFloat;
		this.timeInMillsLong = timeInMillsLong;
		this.macAddressString = macAddressString;
		this.routeString = routeString;
		this.numberOfAVGsInt = 1;
	}
	public PointTime(double latDouble,double lonDouble,float speedMPSFloat, float bearingFloat, long  timeInMillsLong, String macAddressString, String routeString, int numberOfAVGsInt){
		this.latDouble = latDouble;
		this.lonDouble = lonDouble;
		this.speedMPSFloat = speedMPSFloat;
		this.bearingFloat = bearingFloat;
		this.timeInMillsLong = timeInMillsLong;
		this.macAddressString = macAddressString;
		this.routeString = routeString;
		this.numberOfAVGsInt = numberOfAVGsInt;
	}
	
	@Override
	public String toString() {
		return "Time: "+getTimeInMillsLong();//+ " Lat: "+getLatDouble()+" Lon: "+ getLonDouble();
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
	public float getSpeedMPSFloat() {
		return speedMPSFloat;
	}
	public void setSpeedMPSFloat(float speedMPSFloat) {
		this.speedMPSFloat = speedMPSFloat;
	}
	public int getNumberOfAVGsInt() {
		return numberOfAVGsInt;
	}
	public void setNumberOfAVGsInt(int numberOfAVGsInt) {
		this.numberOfAVGsInt = numberOfAVGsInt;
	}
	
}
