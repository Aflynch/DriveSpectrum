package com.lynchsoftwareengineering.drivespectrum;

import android.provider.BaseColumns;

public final class DBContractClass {
	private final static String DB_FILE_NAME  = "thebests";
	private final static String TEXT_TYPE = " TEXT";
	private final static String REAL_TYPE = " REAL";
	private final static String INTEGER_TYPE = " INTEGER ";
	private final static String BIGINT_TYPE ="BIGINT";
	private final static String COMMA_SEP = " , ";
		public DBContractClass() {
		}
		public static abstract class GlobalGPSEntry implements BaseColumns{
			public static final int VERSION_INT = 0;
			public static final String DATABASE_NAME = "drivespectrum_avg";//drivespectrumdb //runningdb
			public static final String TABLE_NAME = "globalgps";//gps //run
			public static final String COLUMN_NAME_LAT = "lat";
			public static final String COLUMN_NAME_LON = "lon";
			public static final String COLUMN_NAME_TIME = "time";
			public static final String COLUMN_NAME_BEARING = "bearing";
			public static final String COLUMN_NAME_SPEED = "speed";
			public static final String COLUMN_NAME_MAC_ADDRESS = "mac_address";
			public static final String COLUMN_NAME_NUMBER_OF_AVG = "num_agv";
			public static final String COLUMN_NAME_ROUTE_NAME= "route_name";
			public static final String COLUME_NAME_ROUTE_SEQUENCE = "route_sequance";
			public static final String C0LUMN_NAME_TIME_OUT_BOOL = "TIME_OUT_BOOL";
			public static final String COLUMN_VERSION_INT = "vertion";
			public static final String SQL_CREATE_GLOBALGPS_TABLE =
				    "CREATE TABLE " +TABLE_NAME + " (" +
				    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				    COLUMN_NAME_BEARING + REAL_TYPE + COMMA_SEP +
				    COLUMN_NAME_LAT + REAL_TYPE + COMMA_SEP +
				    COLUMN_NAME_LON + REAL_TYPE + COMMA_SEP +
				    COLUMN_NAME_MAC_ADDRESS + TEXT_TYPE + COMMA_SEP +
				    COLUMN_NAME_SPEED + REAL_TYPE + COMMA_SEP +
				    COLUMN_NAME_TIME + TEXT_TYPE+ COMMA_SEP+
				    COLUMN_NAME_NUMBER_OF_AVG + REAL_TYPE+COMMA_SEP+
				    COLUMN_NAME_ROUTE_NAME+TEXT_TYPE+COMMA_SEP+
				    COLUME_NAME_ROUTE_SEQUENCE+ INTEGER_TYPE+COMMA_SEP+
				    COLUMN_VERSION_INT + INTEGER_TYPE +")";
			public final static String CHECK_IF_GLOBAL_TABLE_EXISTS = "SELECT DISTINCT  tbl_name FROM sqlite_master WHERE tbl_name = '"+TABLE_NAME +"'"; 	 

		}
		
		public static abstract class NEWGPSEntry implements BaseColumns{
			public static final int VERSION_INT = 0;
			public static final String DATABASE_NAME = "newspectrum_avg";//drivespectrumdb //runningdb
			public static final String TABLE_NAME = "newgps";//gps //run
			public static final String COLUMN_NAME_LAT = "lat";
			public static final String COLUMN_NAME_LON = "lon";
			public static final String COLUMN_NAME_TIME = "time";
			public static final String COLUMN_NAME_BEARING = "bearing";
			public static final String COLUMN_NAME_SPEED = "speed";
			public static final String COLUMN_NAME_MAC_ADDRESS = "mac_address";
			public static final String COLUMN_NAME_NUMBER_OF_AVG = "num_agv";
			public static final String COLUMN_NAME_ROUTE_NAME= "route_name";
			public static final String COLUME_NAME_ROUTE_SEQUENCE = "route_sequance";
			public static final String C0LUMN_NAME_TIME_OUT_BOOL = "TIME_OUT_BOOL";
			public static final String COLUMN_VERSION_INT = "vertion";
			public static final String SQL_CREATE_NEWGPS_TABLE =
				    "CREATE TABLE " +TABLE_NAME + " (" +
				    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				    COLUMN_NAME_BEARING + REAL_TYPE + COMMA_SEP +
				    COLUMN_NAME_LAT + REAL_TYPE + COMMA_SEP +
				    COLUMN_NAME_LON + REAL_TYPE + COMMA_SEP +
				    COLUMN_NAME_MAC_ADDRESS + TEXT_TYPE + COMMA_SEP +
				    COLUMN_NAME_SPEED + REAL_TYPE + COMMA_SEP +
				    COLUMN_NAME_TIME + TEXT_TYPE+ COMMA_SEP+
				    COLUMN_NAME_NUMBER_OF_AVG + REAL_TYPE+COMMA_SEP+
				    COLUMN_NAME_ROUTE_NAME+TEXT_TYPE+COMMA_SEP+
				    COLUME_NAME_ROUTE_SEQUENCE+ INTEGER_TYPE+COMMA_SEP+
				    COLUMN_VERSION_INT + INTEGER_TYPE +")";
			public final static String CHECK_IF_NEW_TABLE_EXISTS = "SELECT DISTINCT  tbl_name FROM sqlite_master WHERE tbl_name = '"+TABLE_NAME +"'"; 	 

		}
		public static abstract class AVGGPSEntry implements BaseColumns{
			public static final int VERSION_INT = 0;
			public static final String DATABASE_NAME = "drivespectrum_avg";//drivespectrumdb //runningdb
			public static final String TABLE_NAME = "agvgps";//gps //run
			public static final String COLUMN_NAME_LAT = "lat";
			public static final String COLUMN_NAME_LON = "lon";
			public static final String COLUMN_NAME_TIME = "time";
			public static final String COLUMN_NAME_BEARING = "bearing";
			public static final String COLUMN_NAME_SPEED = "speed";
			public static final String COLUMN_NAME_MAC_ADDRESS = "mac_address";
			public static final String COLUMN_NAME_NUMBER_OF_AVG = "num_agv";
			public static final String COLUMN_NAME_ROUTE_NAME= "route_name";
			public static final String COLUME_NAME_ROUTE_SEQUENCE = "route_sequance";
			public static final String C0LUMN_NAME_TIME_OUT_BOOL = "TIME_OUT_BOOL";
			public static final String COLUMN_VERSION_INT = "vertion";
		}
		public static abstract class PathGPSEntry implements BaseColumns{
			public static final int VERSION_INT = 0;
			public static final String DATABASE_NAME = "drivespectrum_path";//drivespectrumdb //runningdb
			public static final String TABLE_NAME = "pathgps";//gps //run
			public static final String COLUMN_NAME_LAT = "lat";
			public static final String COLUMN_NAME_LON = "lon";
			public static final String COLUMN_NAME_TIME = "time";
			public static final String COLUMN_NAME_BEARING = "bearing";
			public static final String COLUMN_NAME_SPEED = "speed";
			public static final String COLUMN_NAME_MAC_ADDRESS = "mac_address";
			public static final String COLUMN_NAME_NUMBER_OF_AVG = "num_agv";
			public static final String COLUMN_NAME_ROUTE_NAME= "route_name";
			public static final String COLUME_NAME_ROUTE_SEQUENCE = "route_sequance";
			public static final String C0LUMN_NAME_TIME_OUT_BOOL = "TIME_OUT_BOOL";
			public static final String COLUMN_VERSION_INT = "vertion";
		}
		
		public static abstract class GPSEntry implements BaseColumns{
			public static final int VERSION_INT = 0;
			public static final String DATABASE_NAME = "drivespectrumdb";//drivespectrumdb //runningdb
			public static final String TABLE_NAME = "gps";//gps //run
			public static final String COLUMN_NAME_LAT = "lat";
			public static final String COLUMN_NAME_LON = "lon";
			public static final String COLUMN_NAME_TIME = "time";
			public static final String COLUMN_NAME_BEARING = "bearing";
			public static final String COLUMN_NAME_SPEED = "speed";
			public static final String COLUMN_NAME_MAC_ADDRESS = "mac_address";
///			public static final Srting COLUMN_NAME_TIME_OUT_BOOL = "TIME _OUT_BOOL";
			public static final String C0LUMN_NAME_TIME_OUT_BOOL = "time_out_bool";
			public static final String COLUMN_VERSION_INT = "vertion";
		}
}
