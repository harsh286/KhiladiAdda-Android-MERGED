package com.khiladiadda.utility;

import android.util.Log;

public class Logger {
    private static final String TAG = Logger.class.getSimpleName();

    private final String mClassName;
    private final boolean mLoggable;
    private static boolean sIsDebug = true;

    public static Logger getLogger (Class<?> clazz){
        return new Logger(clazz.getSimpleName());
    }

    private Logger(String mClassName) {
        this.mClassName = mClassName;
        boolean androidLog;
        try {
            Class.forName("android.util.Log");
            androidLog = true;
        } catch (ClassNotFoundException e) {
            // android logger not available, probably a list_item_my_event_ticket environment.
            androidLog = false;
        }

        this.mLoggable = sIsDebug && androidLog;
    }

    /**
     * To print the formatted debug message on console
     *
     * @param format    String format
     * @param arguments Arguments
     */
    public void debug(String format, Object... arguments) {
        if (mLoggable) {
            try {
                Log.d(mClassName, String.format(format, arguments));
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * To print the formatted error on console
     *
     * @param format    String format
     * @param arguments Arguments
     */
    public void error(String format, Object... arguments) {
        if (mLoggable) {
            try {
                Log.e(mClassName, String.format(format, arguments));
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * To print the formatted info on console
     *
     * @param format    String format
     * @param arguments Arguments
     */
    public void info(String format, Object... arguments) {
        if (mLoggable) {
            try {
                Log.i(mClassName, String.format(format, arguments));
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * To print the formatted warning message on console
     *
     * @param format    String format
     * @param arguments Arguments
     */
    public void warn(String format, Object... arguments) {
        if (mLoggable) {
            try {
                Log.w(mClassName, String.format(format, arguments));
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * To print the formatted debug message on console
     *
     * @param message String message
     */
    public void debug(String message) {
        if (mLoggable) {
            try {
                Log.d(mClassName, message);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * To print the formatted error on console
     *
     * @param message String message
     */
    public void error(String message) {
        if (mLoggable) {
            try {
                Log.e(mClassName, message);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * To print the formatted info on console
     *
     * @param message String message
     */
    public void info(String message) {
        if (mLoggable) {
            try {
                Log.i(mClassName, message);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    /**
     * To print the formatted warning message on console
     *
     * @param message String message
     */
    public void warn(String message) {
        if (mLoggable) {
            try {
                Log.w(mClassName, message);
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    public static void setIsDebug(boolean isDebug) {
        sIsDebug = isDebug;
    }

    public static boolean isDebug() {
        return sIsDebug;
    }
}