package com.example.BackendJobs.Util;

import java.sql.Timestamp;

public final class StaticMethods {

	public static <E extends Enum<E>> boolean isValidEnum(final Class<E> enumClass, final String enumName) {
        if (enumName == null) {
            return false;
        }
        try {
            Enum.valueOf(enumClass, enumName);
            return true;
        } catch (final IllegalArgumentException ex) {
            return false;
        }
    }
	
	public static int intValue(Boolean b) {
	    int intValue = 0;
	    if (b) {
	    	intValue = 1;
	    }
	    return intValue;
	}
	
	public static String getDate(Timestamp time) {
		String timeString = time.toString();
		String output = timeString.substring(0, 10);
		return output;
	}
}
