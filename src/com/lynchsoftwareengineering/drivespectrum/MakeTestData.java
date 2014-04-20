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
		String routeNameString;

		PointTime zeroPointTime = new PointTime(33.97941432, -84.01260397, 4.f, 90.f, (long) 10000000, "mac1", "");

	 	routeNameString = zeroPointTime.getTimeInMillsLong()+zeroPointTime.getMacAddressString();		
		for(int i = 0; i < 4; i++){
			testPointTimeArraylist.add(new PointTime(zeroPointTime.getLatDouble(), zeroPointTime.getLonDouble()+(.0001*(double)i), zeroPointTime.getSpeedMPS(), zeroPointTime.getBearingFloat(), zeroPointTime.getTimeInMillsLong()+i, zeroPointTime.getMacAddressString(), routeNameString));
		}
		
		PointTime onePointTime = new PointTime(33.97941432, -84.01265397, 8.f, 90.f, (long) 20000000, "mac2", "");

		routeNameString = onePointTime.getTimeInMillsLong()+onePointTime.getMacAddressString();		
		for(int i = 0; i < 4; i++){
			testPointTimeArraylist.add(new PointTime(onePointTime.getLatDouble(), onePointTime.getLonDouble()+(.0001*(double)i), onePointTime.getSpeedMPS(), onePointTime.getBearingFloat(), onePointTime.getTimeInMillsLong()+i, onePointTime.getMacAddressString(), routeNameString));
		}
		
		PointTime twoPointTime = new PointTime(33.97941432, -84.01260397, 6.f, 180.f, (long) 30000000, "mac3", "");

		routeNameString = twoPointTime.getTimeInMillsLong()+twoPointTime.getMacAddressString();		
		for(int i = 0; i < 4; i++){
			testPointTimeArraylist.add(new PointTime(twoPointTime.getLatDouble()+(.0001*(double)i), twoPointTime.getLonDouble(), twoPointTime.getSpeedMPS(), twoPointTime.getBearingFloat(), twoPointTime.getTimeInMillsLong()+i, twoPointTime.getMacAddressString(), routeNameString));
		}

		PointTime threePointTime = new PointTime(33.97946432, -84.01260397, 10.f, 180.f, (long) 40000000, "mac4", "");

		routeNameString = threePointTime.getTimeInMillsLong()+threePointTime.getMacAddressString();		
		for(int i = 0; i < 4; i++){
			testPointTimeArraylist.add(new PointTime(threePointTime.getLatDouble()+(.0001*(double)i), threePointTime.getLonDouble(), threePointTime.getSpeedMPS(), threePointTime.getBearingFloat(), threePointTime.getTimeInMillsLong()+i, threePointTime.getMacAddressString(), routeNameString));
		}
		
		PointTime fourPointTime = new PointTime(33.97945432, -84.01261397, 2.f, 180.f, (long) 50000000, "mac5", "");

		routeNameString = fourPointTime.getTimeInMillsLong()+fourPointTime.getMacAddressString();		
		for(int i = 0; i < 4; i++){
			testPointTimeArraylist.add(new PointTime(fourPointTime.getLatDouble()+(.0001*(double)i), fourPointTime.getLonDouble(), fourPointTime.getSpeedMPS(), fourPointTime.getBearingFloat(), fourPointTime.getTimeInMillsLong()+i, fourPointTime.getMacAddressString(), routeNameString));
		}
		return testPointTimeArraylist;
	}
}
