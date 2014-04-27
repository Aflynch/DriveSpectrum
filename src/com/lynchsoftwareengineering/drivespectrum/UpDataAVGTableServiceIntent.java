package com.lynchsoftwareengineering.drivespectrum;

import android.app.IntentService;
import android.content.Intent;

public class UpDataAVGTableServiceIntent extends IntentService {
	public static final int ROUTE_NAME_TO_UPDATE_KEY = 0;
	/*TODO: 
	 * need to take name of routeString and find all matches and update delete from AVG table then 
	 * add new updated AVG path data. 
	 * 
	 * need to come back and check.
	 */

	public UpDataAVGTableServiceIntent(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onHandleIntent(Intent arg0) {
		// TODO Auto-generated method stub
		
	}



}
