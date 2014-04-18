package com.lynchsoftwareengineering.drivespectrum;

import java.util.ArrayList;

public class MakeTestData {

	public MakeTestData(){
		
	}
	
	public static ArrayList<PointTime> getTestPointTimeArrayList(int numberOfPointTimesInt, int numberOfRountesInt, int numberOfOverLappingRountesInt){
//		stringArraylist.add("33.97941432#-84.01260397#30.1#1396312154436#178.0");
//		stringArraylist.add("33.97941432#-80.01260397#30.1#1396312154436#178.0");
//		stringArraylist.add("35.97941432#-82.01260397#30.1#1396312154436#178.0");
		ArrayList<PointTime> testPointTimeArraylist = new ArrayList<PointTime>();
		PointTime zeroPointTime = new PointTime(33.97941432, -84.01260397, 4.f, 90.f, (long) 1000000000, "mac1", "");

		String routeNameString = zeroPointTime.getTimeInMillsLong()+zeroPointTime.getMacAddressString();		
		for(int i = 0; i < 4; i++){
			testPointTimeArraylist.add(new PointTime(zeroPointTime.getLatDouble(), zeroPointTime.getLonDouble()+(.0001*(double)i), zeroPointTime.getSpeedMPS(), 90.f, zeroPointTime.getTimeInMillsLong()+i, zeroPointTime.getMacAddressString(), routeNameString));
		}
		
		PointTime onePointTime = new PointTime(33.97941432, -84.01265397, 8.f, 90.f, (long) 2000000000, "mac2", "");

		routeNameString = onePointTime.getTimeInMillsLong()+onePointTime.getMacAddressString();		
		for(int i = 0; i < 4; i++){
			testPointTimeArraylist.add(new PointTime(onePointTime.getLatDouble(), onePointTime.getLonDouble()+(.0001*(double)i), onePointTime.getSpeedMPS(), 90.f, onePointTime.getTimeInMillsLong()+i, onePointTime.getMacAddressString(), routeNameString));
		}
		return testPointTimeArraylist;
	}
}
