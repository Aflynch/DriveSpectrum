package com.lynchsoftwareengineering.drivespectrum;

import android.graphics.Point;

public class PointTime extends Point {// lenght -15,+5 , 
	public String routeString;
	public Double latDouble, lonDouble;
	public long timeInMillsLong;
	public long  macAddressLong;
	public float speedMPS;
	public float bearingFloat;
	public PointTime(){
		
	}
	public PointTime(int xInt,int yInt,float speedMPS, float bearingFloat, long  timeInMillsLong, long macAddressLong, String routeString){
		super.set(xInt, yInt);
		this.speedMPS = speedMPS;
		this.bearingFloat = bearingFloat;
		this.timeInMillsLong = timeInMillsLong;
		this.macAddressLong = macAddressLong;
		this.routeString = routeString;
	}
	public Long getTimeInMillsLong() {
		return timeInMillsLong;
	}
	public void setTimeInMillsLong(Long timeInMillsLong) {
		this.timeInMillsLong = timeInMillsLong;
	}
	public float getSpeedMPS() {
		return speedMPS;
	}
	public void setSpeedMPS(float speedMPS) {
		this.speedMPS = speedMPS;
	}
	public Long getMacAddressLong() {
		return macAddressLong;
	}
	public void setMacAddressLong(Long macAddressLong) {
		this.macAddressLong = macAddressLong;
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
	public void setMacAddressLong(long macAddressLong) {
		this.macAddressLong = macAddressLong;
	}
	
}
