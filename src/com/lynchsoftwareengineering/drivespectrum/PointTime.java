package com.lynchsoftwareengineering.drivespectrum;

import android.graphics.Point;

public class PointTime extends Point {// lenght -15,+5 , 
	public long timeInMillsLong;
	public float speedMPS;
	public PointTime(){
		
	}
	public PointTime(int xInt,int yInt,float speedMPS, long  timeInMillsLong){
		super.set(xInt, yInt);
		this.speedMPS = speedMPS;
		this.timeInMillsLong = timeInMillsLong;
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
	
}
