package com.lynchsoftwareengineering.drivespectrum;

import android.provider.BaseColumns;

public final class DBContractClass {

		public DBContractClass() {
		}
		
		public static abstract class AVGGPSEntry implements BaseColumns{
			public static final int VERSION_INT = 0;
			public static final String DATABASE_NAME = "drivespectrumdb";//drivespectrumdb //runningdb
			public static final String TABLE_NAME = "agvgps";//gps //run
			public static final String COLUMN_NAME_LAT = "lat";
			public static final String COLUMN_NAME_LON = "lon";
			public static final String COLUMN_NAME_TIME = "time";
			public static final String COLUMN_NAME_BEARING = "bearing";
			public static final String COLUMN_NAME_SPEED = "speed";
			public static final String COLUMN_NAME_MAC_ADDRESS = "mac_address";
			public static final String COLUMN_NAME_NUMBER_OF_AVG = "num_agv";
			public static final String COLUMN_NAME_ROUTE_NAME= "route_name";
///			public static final Srting COLUMN_NAME_TIME_OUT_BOOL = "TIME _OUT_BOOL";
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
			public static final String C0LUMN_NAME_TIME_OUT_BOOL = "TIME_OUT_BOOL";
			public static final String COLUMN_VERSION_INT = "vertion";
		}
}
