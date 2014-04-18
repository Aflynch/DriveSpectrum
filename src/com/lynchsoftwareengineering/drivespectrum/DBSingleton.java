package com.lynchsoftwareengineering.drivespectrum;

import java.io.File;
import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import com.lynchsoftwareengineering.drivespectrum.DBContractClass.AVGGPSEntry;
import com.lynchsoftwareengineering.drivespectrum.DBContractClass.GPSEntry;

import dalvik.system.BaseDexClassLoader;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursorDriver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteDatabaseCorruptException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;
import android.graphics.Color;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;
/*
 * This class is a wrapper class for SQLightDatabase
 * it manages the opening and editing of the database, and 
 * provides for a singleton access. Though it can not restrict access 
 * as such.  
 */ 
public class DBSingleton {
	private  final String DB_FILE_NAME  = "thebests";
	private  final String TEXT_TYPE = " TEXT";
	private  final String REAL_TYPE = " REAL";
	private  final String INTEGER_TYPE = " INTEGER ";
	private  final String BIGINT_TYPE ="BIGINT";
	private  final String COMMA_SEP = " , ";
	private  final String SELECT_START = "SELECT * FROM "+DBContractClass.GPSEntry.TABLE_NAME + " WHERE "+DBContractClass.GPSEntry._ID + " < 100";// Testing
	private  final String COUNT_ROWS_IN_PGS_TBABLE = "SELECT "+DBContractClass.GPSEntry.COLUMN_NAME_BEARING +" "+ "FROM "+DBContractClass.GPSEntry.TABLE_NAME;
	private  final String  CHECK_IT_TABLE_NAME_EXISTS = "SELECT DISTINCT  tbl_name FROM sqlite_master WHERE tbl_name = '"+DBContractClass.GPSEntry.TABLE_NAME +"'"; 	 
	private  final String SQL_CREATE_GPS_TABLE =
	    "CREATE TABLE " + DBContractClass.GPSEntry.TABLE_NAME + " (" +
	    DBContractClass.GPSEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
	    DBContractClass.GPSEntry.COLUMN_NAME_BEARING + REAL_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_LAT + REAL_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_LON + REAL_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_MAC_ADDRESS + TEXT_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_SPEED + REAL_TYPE + COMMA_SEP +
	    DBContractClass.GPSEntry.COLUMN_NAME_TIME + TEXT_TYPE+ COMMA_SEP+
	    DBContractClass.GPSEntry.C0LUMN_NAME_TIME_OUT_BOOL+ INTEGER_TYPE+ COMMA_SEP+
	    DBContractClass.GPSEntry.COLUMN_VERSION_INT + INTEGER_TYPE +")";
	private  final String SQL_CREATE_AVGGPS_TABLE =
		    "CREATE TABLE " + DBContractClass.AVGGPSEntry.TABLE_NAME + " (" +
		    DBContractClass.AVGGPSEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
		    DBContractClass.AVGGPSEntry.COLUMN_NAME_BEARING + REAL_TYPE + COMMA_SEP +
		    DBContractClass.AVGGPSEntry.COLUMN_NAME_LAT + REAL_TYPE + COMMA_SEP +
		    DBContractClass.AVGGPSEntry.COLUMN_NAME_LON + REAL_TYPE + COMMA_SEP +
		    DBContractClass.AVGGPSEntry.COLUMN_NAME_MAC_ADDRESS + TEXT_TYPE + COMMA_SEP +
		    DBContractClass.AVGGPSEntry.COLUMN_NAME_SPEED + REAL_TYPE + COMMA_SEP +
		    DBContractClass.AVGGPSEntry.COLUMN_NAME_TIME + TEXT_TYPE+ COMMA_SEP+
		    DBContractClass.AVGGPSEntry.COLUMN_NAME_NUMBER_OF_AVG + REAL_TYPE+COMMA_SEP+
		    DBContractClass.AVGGPSEntry.COLUMN_NAME_ROUTE_NAME+TEXT_TYPE+COMMA_SEP+
		    DBContractClass.AVGGPSEntry.COLUME_NAME_ROUTE_SEQUENCE+ INTEGER_TYPE+COMMA_SEP+
		  //  DBContractClass.GPSEntry.C0LUMN_NAME_TIME_OUT_BOOL+ INTEGER_TYPE+ COMMA_SEP+
		    DBContractClass.GPSEntry.COLUMN_VERSION_INT + INTEGER_TYPE +")";
	
	private static final String SQL_DELETE_ENTRIES =
	    "DROP TABLE IF EXISTS " ;
	private static DBSingleton dBSingleton;
	private String macAddressString;
	private String filePathString;

	//lilyphon@yahoo.com	
	static DBSingleton dbSingleton;
	SharedPreferences sharedPreferences;
	SharedPreferences.Editor sharedPerferencesEditor;
	final String OFF_SET_KEY = "OFF_SET_KEY";
	final String DS_DB_NAME = "DS_DB_NAME";
	final String DS_KEY = "DS_KEY";
	int dbOffSetInt;
	public int getNumberOfRowsFromGPSTable(){
		SQLiteDatabase db = SQLiteDatabase.openDatabase(filePathString+DB_FILE_NAME,null, SQLiteDatabase.CREATE_IF_NECESSARY);
		Cursor  cursor = db.rawQuery(SELECT_START,null);
		int countInt = cursor.getColumnCount();
		cursor.close();
		db.close();
		return countInt;
	}
	public ArrayList<PointTime> listAllFromDB(){
		SQLiteDatabase db = SQLiteDatabase.openDatabase(filePathString+DB_FILE_NAME,null, SQLiteDatabase.CREATE_IF_NECESSARY);
		Cursor  cursor = db.rawQuery(SELECT_START,null);
		Log.d("running", "Number of columns in DB = " + cursor.getColumnCount() + " Number of rows " +  getNumberOfRowsFromGPSTable());
		ArrayList<PointTime> arrayListPointTime = new ArrayList<PointTime>();
		 if (cursor.moveToFirst()){
			 // I really hate sqlight in Android. Ok so after you run you select statement you get back a cursor object
			 // that you need to move the row you want then use the index of the column and the right datatype to pull out 
			 // the information. 
			 int  baringInt = cursor.getColumnIndex(DBContractClass.GPSEntry.COLUMN_NAME_BEARING);
			 int  latitudeInt = cursor.getColumnIndex(DBContractClass.GPSEntry.COLUMN_NAME_LAT);
			 int  longitudeInt = cursor.getColumnIndex(DBContractClass.GPSEntry.COLUMN_NAME_LON);
			 int  macAddressInt = cursor.getColumnIndex(DBContractClass.GPSEntry.COLUMN_NAME_MAC_ADDRESS);
			 int  speedInt = cursor.getColumnIndex(DBContractClass.GPSEntry.COLUMN_NAME_SPEED);
			 int  timeInt = cursor.getColumnIndex(DBContractClass.GPSEntry.COLUMN_NAME_TIME);
			 
			 do{/// CHANGING over to PointTome
				 
				 	PointTime pointTime = new PointTime();
				 	pointTime.setLatDouble( cursor.getDouble(latitudeInt));
				 	pointTime.setLonDouble( cursor.getDouble(longitudeInt));
				 	pointTime.setSpeedMPS((float)cursor.getDouble(speedInt));
				 	pointTime.setTimeInMillsLong(Long.parseLong(cursor.getString(timeInt)));
				 	pointTime.setBearingFloat(cursor.getFloat(baringInt));
				 	
				 	arrayListPointTime.add(pointTime);// I needed to know that I was not passing just the pointer because I will be reusing this object.
			 }while(cursor.moveToNext());
		 }
		 return arrayListPointTime;
	}

	public synchronized void writeGPSDataToDB(float bearingFloat, double latitudeDouble, double longitudeDouble, double speedDouble, long timeLong, int timeOutBoolInt){
		SQLiteDatabase db = SQLiteDatabase.openDatabase(filePathString+DB_FILE_NAME,null, SQLiteDatabase.CREATE_IF_NECESSARY);
		ContentValues contentValues = new ContentValues();
		contentValues.put(DBContractClass.GPSEntry.COLUMN_NAME_BEARING, bearingFloat);
		contentValues.put(DBContractClass.GPSEntry.COLUMN_NAME_LAT, latitudeDouble);
		contentValues.put(DBContractClass.GPSEntry.COLUMN_NAME_LON, longitudeDouble);
		contentValues.put(DBContractClass.GPSEntry.COLUMN_NAME_MAC_ADDRESS, macAddressString);
		contentValues.put(DBContractClass.GPSEntry.COLUMN_NAME_SPEED, speedDouble);
		contentValues.put(DBContractClass.GPSEntry.COLUMN_NAME_TIME, ""+timeLong);// CHECK FOR MAX MIN LAT LON SPEED!!!! 
		contentValues.put(DBContractClass.GPSEntry.C0LUMN_NAME_TIME_OUT_BOOL, timeOutBoolInt);
		contentValues.put(DBContractClass.GPSEntry.COLUMN_VERSION_INT, DBContractClass.GPSEntry.VERSION_INT);
		
		db.insert(DBContractClass.GPSEntry.TABLE_NAME, null, contentValues);
		db.close();
		
		//if ((Long.parseLong(oneGPSRowDataStringArray[3]) -Long.parseLong( gpsRowDateStringArray[3]))<GPSIntentService.MAX_TIME_OUT_TIME){
		// need to find a way to get a good for max and min value
//		
//			if( latitudeDouble> latitudeMaxDouble){
//				latitudeMaxDouble = latitudeDouble;	
//			}else if(latitudeDouble < latitudeMinDouble){
//				latitudeMinDouble = latitudeDouble;
//			}// one location could need to set both Lat and Long
//			if(longitudeDouble > longitudeMaxDouble){
//				longitudeMaxDouble = longitudeDouble;
//			}else if(longitudeDouble < longitudeMinDouble){
//				longitudeMinDouble = longitudeDouble;
//			}
//			if(speedDouble > maxSpeedDouble){
//				maxSpeedDouble = speedDouble;
//			}
	//	}
	//	}
		
		//listAllFromDB();
	}
	private DBSingleton(String filePathString, String macAddressString){
		this.filePathString = filePathString;
		this.macAddressString = macAddressString;
		// "Haha, You will ever get this"

		SQLiteDatabase db = SQLiteDatabase.openDatabase(filePathString+DB_FILE_NAME,null, SQLiteDatabase.CREATE_IF_NECESSARY);
		if (db !=null){
			Cursor cursor = db.rawQuery(CHECK_IT_TABLE_NAME_EXISTS, null); 
			if(cursor.getCount() ==  0){
			//	set up max min
				db.execSQL(SQL_CREATE_GPS_TABLE);
				db.execSQL(SQL_CREATE_AVGGPS_TABLE);
				Log.d("running","NEW DB WAS MADE!!! ");
			}else{
			//	get max min
			//	db.execSQL(SQL_DELETE_ENTRIES+);
			//	db.execSQL(SQL_DELETE_ENTRIES+DBContractClass.AVGGPSEntry.TABLE_NAME);
				Log.d("running" , "SQLightDataBase and table! "+DBContractClass.GPSEntry.TABLE_NAME +" loaded.");
			}
		}
		db.close();
	}
	/*
	 * Stress ANS  Automatic 
	 * disorders
	 * why do 
	 * visible ways
	 * most common
	 * common stress management
	 * -------
	 * Micor and micor
	 * what amonts of calories
	 * what determaines caloro in take
	 * what is DRI
	 * hight vs lower 
	 * nutriet dincity
	 * where does glocus come from 
	 * caristis of complex carbohidrates
	 * salubla fiber 
	 * complet and incomplete proteins 
	 * good fat vs bad
	 * fat vs water soluble vitamins
	 * 
	 * ------
	 * one more thing 
	 */
	
	private void setUpMaxMinLatLonSpeed(){
//		PreferenceManager.getDefaultSharedPreferences(context)
//		 sharedPreferences = get;
//		 sharedPerferencesEditor;
	}

	public static DBSingleton getInstanceOfDataBaseSingletion(){

		if (dBSingleton != null){
			return dBSingleton;
		}else{
			return null; // I know it would be null either way but it make me fee better. 
		}
	}
	public static DBSingleton getInstanceOfDataBaseSingleton(String filePathString, String macAddressString){
		//Then one day he got this
		if(dBSingleton == null){
			dBSingleton = new DBSingleton(filePathString, macAddressString);
			return dBSingleton;
		}else{
			return dBSingleton;
		}
	}
	/*
	public class GPSReaderDbHelper extends SQLiteOpenHelper {
	    // If you change the database schema, you must increment the database version.
	    public static final int DATABASE_VERSION = 1;
	    public static final String DATABASE_NAME = "GPSData.db";

	    public GPSReaderDbHelper(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	    public void onCreate(SQLiteDatabase db) {
	        db.execSQL(SQL_CREATE_GPS_TABLE);
	    }
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // This database is only a cache for online data, so its upgrade policy is
	        // to simply to discard the data and start over
	    	
	    	Log.d("database test", "onUpgrade was called, but no acction was taken.");
	        //db.execSQL(SQL_DELETE_ENTRIES);
	        //onCreate(db);
	    }
	    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        onUpgrade(db, oldVersion, newVersion);
	    }
	}
	*/
	
	public  class DataFilter{
		ArrayList<PointTime>  pointTimeArraylist;
		ArrayList<PointTime>  newPointTimeArraylist;
		PointTime referencePointTime;
		String routeNameString;
		double latConvertDegreeInToMetersDouble;
		double logConvetDegreeInToMetersDouble ;
		double rangeLatMaxDouble, rangeLatMinDouble, rangeLonMaxDouble, rangeLonMinDouble ;
		double latToMetterConvertion, lonToMettersConvertion;
		boolean isInSamePathBool;
		int indexInt;
		//public DataFilter(){
		public ArrayList<PointTime> processData(ArrayList<PointTime> pointTimeArrayListToBeSorted){ // need to return the process data. Get the right arraylist!!! 
			
			// read form DBOne then after processing is loaded into the next DBTwo ready for drawing.
			/*
			 * Order all points from DBOne by time. 
			 * 
			 * check for points in side min distance && bearing. if (are bellow < point magic number) {Need to max min lat lon area}
			 * then add the them to the DBTwo. 
			 * If number of points is grater the max  send to AVG function.
			 * 
			 * AVG function
			 * Starting from  minTime where bearing is in +- the same with magic number for error.
			 * AVG(with weight of number of time AVG) and make max points per Km.
			 * 
			 * move forward diameter of to range
			 * 
			 */
			
			// pointTimeArraylist = listAllFromDB();// needs to be class varyable so I can finde it later 
			pointTimeArraylist = pointTimeArrayListToBeSorted;
			newPointTimeArraylist = new ArrayList<PointTime>();
			// set lon convetions 
			setConvertionForLonAndLat(pointTimeArraylist.get(0).getLatDouble(), pointTimeArraylist.get(0).getLonDouble());
			// filter Data
			setReferencePointTime(pointTimeArraylist.get(0));
			indexInt = 0;
			while(0 <pointTimeArraylist.size()){// ref
				//inner search to matches inside area 
				if(indexInt >= pointTimeArraylist.size()){
					indexInt = 0;
				}
				if(referencePointTime == null && pointTimeArraylist.size() > 0){
					setReferencePointTime(pointTimeArraylist.get(0));
				}
					innerForLoopFinePointTimeInsideArea(pointTimeArraylist,pointTimeArraylist.get(0));
			}
			return newPointTimeArraylist;
		}
		private void innerForLoopFinePointTimeInsideArea(ArrayList<PointTime> pointTimeArrayList, PointTime outterPointTime) { //Important, this could done much faster  
			setMinMaxLatLon(outterPointTime.getLatDouble(), outterPointTime.getLonDouble());
			ArrayList<PointTime> groupPointTimeArrayList = new ArrayList<PointTime>();
			boolean firstRunBool = true;
			//while()
			for(; indexInt < pointTimeArrayList.size(); indexInt++){// this will not work. reference has already been removed
				// need change i for the PointTime object that have been removed. Also need to look at the order that they are taken out of the array...s	
				double latDouble = pointTimeArrayList.get(indexInt).getLatDouble();
				double lonDouble = pointTimeArrayList.get(indexInt).getLonDouble();
				if(rangeLatMinDouble< latDouble && rangeLatMaxDouble> latDouble){
				//	Log.d("Range", "lat = "+ latDouble + " range = min: "+ rangeLatMinDouble + " range = max : "+ rangeLatMaxDouble);
				//	Log.d("Range","lon = "+ lonDouble + " range = min: "+rangeLonMinDouble+" range = max: "+ rangeLonMaxDouble );
					if(rangeLonMinDouble< lonDouble && rangeLonMaxDouble > lonDouble){ // need test cases for lon conversion.
						Log.d("Running", "is inside range ");
						groupPointTimeArrayList.add(pointTimeArrayList.get(indexInt));
					}
				}
			}
			if (groupPointTimeArrayList.size() > 1){
				processInGroup(groupPointTimeArrayList);
			}else if (groupPointTimeArrayList.size() == 1){
				pointTimeArrayList.remove(referencePointTime);
				referencePointTime = null;
				indexInt--;
			}
		}
		
		private void setReferencePointTime(PointTime pointTime){
			 referencePointTime = pointTime;
			 routeNameString = "";
			 routeNameString += referencePointTime.getTimeInMillsLong();
			 routeNameString += referencePointTime.getMacAddressString();
			 referencePointTime.setRouteString(routeNameString);
		}
		
		private void processInGroup(ArrayList<PointTime>  groupPointTimeArrayList){
			Collections.sort(groupPointTimeArrayList, new ComparatorPointTimeByLocation(referencePointTime));// need look into selection by the bearing for one point to an other. So parallel point wish similar bearings are not grouped unless they are really on the same road.
			for(int i = 0; i < groupPointTimeArrayList.size()-1; i++){
				//Log.d("Running", " Distance in Km : "+ ComparatorPointTimeByLocation.distance(pointTime.getLatDouble(), pointTime.getLonDouble(), pointTime1.getLatDouble(), pointTime1.getLonDouble()) );
				if( isInsideBearing(groupPointTimeArrayList.get(i).getBearingFloat(), groupPointTimeArrayList.get(i+1).getBearingFloat())) {
					if(pointTimeArraylist.indexOf(groupPointTimeArrayList.get(i)) <= indexInt){
						indexInt--;
					}
					referencePointTime = groupPointTimeArrayList.get(i);// i -i
					referencePointTime.setRouteString(routeNameString);
					// also need to remove PointTime from master arraylist
					newPointTimeArraylist.add(referencePointTime);
					pointTimeArraylist.remove(referencePointTime);// need method here to
					groupPointTimeArrayList.remove(referencePointTime);		
					i--;// So the count does not move forward  while the size of the array has gotten smaller at the same time. 
					referencePointTime = groupPointTimeArrayList.get(i+1);// this i is now i++ of what it was before because for the removal of an object from the arraylist
					referencePointTime.setRouteString(routeNameString);
					

					Log.d("Range", "XXXXX is inside range and bearing!!!! XXXXXX");
				}else{
					setReferencePointTime(groupPointTimeArrayList.get(i+1));
				}
				if(i %5 == 0){// every X number of run resort the array from current inedx 
					Collections.sort(groupPointTimeArrayList, new ComparatorPointTimeByLocation(groupPointTimeArrayList.get(i)));
				}
			}
			
			//Log.d("Running", "------ this is a new line ------");
			 
		}
		
		private boolean isInsideBearing(float bearingFloat, float oneBearingFloat){ // this should be tested.... 
			float offSetFloat = oneBearingFloat - 90;
			offSetFloat *=-1;
			oneBearingFloat += offSetFloat;//
			bearingFloat +=offSetFloat;
			bearingFloat -= 180;
			bearingFloat = Math.abs(bearingFloat);
			return  (bearingFloat >(90-DataAVGRules.BEARING_RANGE_DEGREES_TOLERANCE) && bearingFloat < (90+DataAVGRules.BEARING_RANGE_DEGREES_TOLERANCE))? true: false;
		}
		private void setMinMaxLatLon(Double latDouble, Double lonDouble) {
			// find lat lon offSet
			double  latOffsetDouble  = DataAVGRules.RADIUS_IN_METERS  / latConvertDegreeInToMetersDouble; // latOffsetDouble +- lat = latMin , latMax
			double lonOffsetDouble = DataAVGRules.RADIUS_IN_METERS/ logConvetDegreeInToMetersDouble;
			 
		// Warning need to build case for place near 0... 
			if(lonDouble > 0){
				rangeLatMaxDouble = latDouble- latOffsetDouble;
				rangeLatMinDouble = latDouble+latOffsetDouble;
			}else{
				rangeLatMaxDouble = latDouble+latOffsetDouble;
				rangeLatMinDouble = latDouble-latOffsetDouble;				
			}
			Log.d("Running", "lat min: "+ rangeLatMinDouble + " lat max: "+ rangeLatMaxDouble);
		// log
				if (lonDouble > 0){
					rangeLonMaxDouble =  lonDouble + lonOffsetDouble;
					rangeLonMinDouble = lonDouble -  lonOffsetDouble;
				}else{
					rangeLonMaxDouble =  lonDouble - lonOffsetDouble;
					rangeLonMinDouble = lonDouble + lonOffsetDouble;
				}
			Log.d("Running", "lon min: "+ rangeLonMinDouble + " max: "+ rangeLonMaxDouble);
		}
		
		private void setConvertionForLonAndLat(double latDouble , double logDouble){
//		    * Mathematical expression: Length of a degree of longitude = cos
//		    		(latitude) * 111.325 kilometers
//		    		    * Example: 1° of longitude at 40° N = cos (40°) * 111.325
//		    		    * Since the cosine of 40° is 0.7660, the length of one degree is
//		    		85.28 kilometers.
			latConvertDegreeInToMetersDouble = 111034; // there are 111034 meters in one degree lat
			logConvetDegreeInToMetersDouble =  Math.cos(latDouble) *111325;
		}
	}
}

