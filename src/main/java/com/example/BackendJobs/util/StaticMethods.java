package com.example.BackendJobs.util;

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
}
