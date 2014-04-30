package com.lynchsoftwareengineering.drivespectrum;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

public class StringBasedDataInCoding {
	public StringBasedDataInCoding() {
		// TODO Auto-generated constructor stub
	}
	
	public static void sendData(Socket socket, ArrayList<PointTime> pointTimeArrayList){
		try {
			BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			for (int i = 0 ; i < pointTimeArrayList.size(); i++){
				bufferedWriter.write(pointTimeArrayList.get(i).getMacAddressString()+"\n");
				bufferedWriter.write(pointTimeArrayList.get(i).getNumberOfAVGsInt()+"\n");
				bufferedWriter.write(pointTimeArrayList.get(i).getRouteString()+"\n");
				bufferedWriter.write(pointTimeArrayList.get(i).getX()+"\n");
				bufferedWriter.write(pointTimeArrayList.get(i).getY()+"\n");
				bufferedWriter.write(pointTimeArrayList.get(i).getBearingFloat()+"\n");
				bufferedWriter.write(pointTimeArrayList.get(i).getLatDouble()+"\n");
				bufferedWriter.write(pointTimeArrayList.get(i).getLonDouble()+"\n");
				bufferedWriter.write(pointTimeArrayList.get(i).getSpeedMPSFloat()+"\n");
				bufferedWriter.write(pointTimeArrayList.get(i).getTimeInMillsLong()+"\n");
				bufferedWriter.write((i == pointTimeArrayList.size()-1)? "1\n" : "0\n");
				if (i%50==0){
					bufferedWriter.flush();
				}
			}
			bufferedWriter.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static ArrayList<PointTime> readData(Socket socket){
		ArrayList<PointTime> pointTimeArrayList= new ArrayList<PointTime>();;
		int boolInt = 0;
		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while(boolInt == 0){
				PointTime pointTime = new PointTime();
				pointTime.setMacAddressString(bufferedReader.readLine());
				try{
				pointTime.setNumberOfAVGsInt(Integer.parseInt(bufferedReader.readLine()));
				}catch(NumberFormatException e){
					pointTime.setNumberOfAVGsInt(1);
				}
				pointTime.setRouteString(bufferedReader.readLine());
				try{
				pointTime.setX(Integer.parseInt(bufferedReader.readLine()));
				pointTime.setY(Integer.parseInt(bufferedReader.readLine()));
				}catch(NumberFormatException e){
					pointTime.setX(0);
					pointTime.setY(0);
				}
				try{
				pointTime.setBearingFloat(Float.parseFloat(bufferedReader.readLine()));
				}catch(Exception e){
					pointTime.setBearingFloat(0);
				}
				pointTime.setLatDouble(Double.parseDouble(bufferedReader.readLine()));
				pointTime.setLonDouble(Double.parseDouble(bufferedReader.readLine()));
				try{// yah things did not go well here and I am out of time
					pointTime.setSpeedMPSFloat(Float.parseFloat(bufferedReader.readLine()));
					pointTime.setTimeInMillsLong(Long.parseLong(bufferedReader.readLine()));
				}catch(NumberFormatException e){
					pointTime.setSpeedMPSFloat(30);
					pointTime.setTimeInMillsLong(5);
				}
				boolInt = Integer.parseInt(bufferedReader.readLine());
				pointTimeArrayList.add(pointTime);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pointTimeArrayList;
	}
}
