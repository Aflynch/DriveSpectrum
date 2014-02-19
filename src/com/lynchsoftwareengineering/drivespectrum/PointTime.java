package com.lynchsoftwareengineering.drivespectrum;

import android.graphics.Point;

public class PointTime extends Point {// lenght -15,+5 , 
	public String timeString;
	public float speedMPS;
	public PointTime(){
		
	}
	public PointTime(int xInt,int yInt,float speedMPS, String timeString){
		super.set(xInt, yInt);
		this.speedMPS = speedMPS;
		this.timeString = timeString;
	}
	public String getTimeString() {
		return timeString;
	}
	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}
	public float getSpeedMPS() {
		return speedMPS;
	}
	public void setSpeedMPS(float speedMPS) {
		this.speedMPS = speedMPS;
	}
	
}
