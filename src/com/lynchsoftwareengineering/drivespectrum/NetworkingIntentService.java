package com.lynchsoftwareengineering.drivespectrum;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import dalvik.system.BaseDexClassLoader;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

public class NetworkingIntentService extends IntentService{
	public static int MAC_ADDRESS_KEY = 0;
	public static int FILE_PATH_KEY = 1;
	public static String FILE_SHAREDPREFERENCES = "shared";
	public static String STATE_KEY = "state";
	SharedPreferences sharedPreferences;
	String macAddressString;
	String filePathString;
	
	public NetworkingIntentService(){
		super("NetworkingIntentService");
	}

	private void sendAVGTable() {
		Log.d("Threading","NetworkingIntentService is running XD");	
//		int globalTableStateInt = sharedPreferences.getInt(STATE_KEY, -1);
//		if(globalTableStateInt == -1){
//			SharedPreferences.Editor editor = sharedPreferences.edit();
//			editor.putInt(STATE_KEY, 1);
//			editor.commit();
//			DBSingleton dbSingleton = DBSingleton.getInstanceOfDataBaseSingleton(filePathString, macAddressString);
//			dbSingleton.makeGlobaleTable();
//		}

//		editor.putInt(getString(R.string.saved_high_score), newHighScore);
//		editor.commit()
		DBSingleton dbSingleton = DBSingleton.getInstanceOfDataBaseSingleton(filePathString, macAddressString);
		dbSingleton.deteleTable(DBContractClass.GlobalGPSEntry.TABLE_NAME);
		dbSingleton.makeGlobaleTable();
		try {
			Socket socket = new Socket(NetworkingSettings.IP_ADDRESS, NetworkingSettings.SOCKET_NUMBER);
			StringBasedDataInCoding.sendData(socket, dbSingleton.getDataInSegmentsFromMainTable(1000, "SELECT * FROM ", DBContractClass.GPSEntry.TABLE_NAME));
			Log.d("Threading","Data may have been sent. ");
			ArrayList<PointTime> pointTimeArrayList = StringBasedDataInCoding.readData(socket);
			dbSingleton.writeToTable(DBContractClass.GlobalGPSEntry.TABLE_NAME, pointTimeArrayList, 1000);
			Log.d("Threading", "Size of echoed ArrayList<PointTime> = "+pointTimeArrayList.size());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("Threading"," Huston we have a problem "+e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.d("Threading"," Huston we have a problem "+e.toString());
		}
		dbSingleton.deteleTable(DBContractClass.NEWGPSEntry.TABLE_NAME);
		Log.d("Table", "Global Table count = "+dbSingleton.getRowCountOfTable(DBContractClass.GlobalGPSEntry.TABLE_NAME));
		onDestroy();
//            BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));

		
	}
	@Override
	protected void onHandleIntent(Intent arg0) {
		macAddressString = arg0.getStringExtra(""+MAC_ADDRESS_KEY);
		filePathString = arg0.getStringExtra(""+FILE_PATH_KEY);
		sharedPreferences = getBaseContext().getSharedPreferences(FILE_SHAREDPREFERENCES, Context.MODE_PRIVATE);
		sendAVGTable();// rename		
	}

}
