package com.lynchsoftwareengineering.drivespectrum;

import android.provider.BaseColumns;

public final class DBContractClass {

		public DBContractClass() {
		}
		
		public static abstract class GPSEntry implements BaseColumns{
			public static final String DATABASE_NAME = "drivespectrumdb";
			public static final String TABLE_NAME = "gps";
			public static final String COLUMN_NAME_LAT = "lat";
			public static final String COLUMN_NAME_LON = "lon";
			public static final String COLUMN_NAME_TIME = "time";
			public static final String COLUMN_NAME_BEARING = "bearing";
			public static final String COLUMN_NAME_SPEED = "speed";
			public static final String COLUMN_NAME_MAC_ADDRESS = "mac_address";
			public static final String VERSION_INT = "vertion";
		}
}
