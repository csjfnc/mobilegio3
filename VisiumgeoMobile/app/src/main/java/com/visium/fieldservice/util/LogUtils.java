package com.visium.fieldservice.util;

import android.os.Build;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit.RetrofitError;

/**
 * @author Andrew Willard
 */
public class LogUtils {

    private static final AtomicBoolean TRACE_ON = new AtomicBoolean(true);
    private static final String TAG = "[FS]";

    private LogUtils() {}

    public static void turnOffTrace() {
        TRACE_ON.set(false);
    }

    private static String getMessage(String message, boolean withZg) {
        return !withZg ? StringUtils.EMPTY : String.format ("%s %s", TAG, ((message == null) ? StringUtils.EMPTY : message));
    }

    private static String getName(Object origin) {
        if (origin != null) {
            return origin instanceof String ? origin.toString()
                    : origin instanceof Class ? ((Class) origin).getSimpleName()
                    : origin.getClass().getSimpleName();
        } else {
            return StringUtils.EMPTY;
        }
    }

    public static void trace(Object origin, String message, Throwable... e) {
        if (TRACE_ON.get()) {
            Log.v(getName(origin), getMessage(message, true), (e.length == 0) ? null : e[0]);
        }
    }

    public static void debug(Object origin, String message, Throwable... e) {
        Log.d(getName(origin), getMessage(message, true), (e.length == 0) ? null : e[0]);
    }

    public static void error(Object origin, String message, Throwable... e) {
        Log.e(getName(origin), getMessage(message, true), (e.length == 0) ? null : e[0]);
    }

    public static void apiError(Object origin, Throwable e) {

        RetrofitError error;

        if (e instanceof RetrofitError
                && (error = (RetrofitError) e) != null
                && error.getResponse() != null
                && error.getResponse().getBody() != null) {

            try {

                Log.e(getName(origin), getMessage(String.format("RetrofitError: %s",
                        StreamUtils.read(error.getResponse().getBody().in())), true), e);

            } catch (IOException e1) {
                Log.e(getName(origin), getMessage(String.valueOf(e), true), e);
            }

        } else {
            Log.e(getName(origin), getMessage(String.valueOf(e), true), e);
        }

    }

    public static void error(Object origin, Throwable e) {
        Log.e(getName(origin), getMessage(String.valueOf(e), true), e);
    }

    public static void wtf(Object origin, String message, Throwable e) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
            Log.e(getName(origin), getMessage(message, true), e);
        } else {
            Log.wtf(getName(origin), getMessage(message, true), e);
        }
    }

    public static void wtf(Object origin, Throwable e) {
        wtf(origin, String.valueOf(e), e);
    }

    public static void warn(Object origin, String message, Throwable... e) {
        Log.w(getName(origin), getMessage(message, true), (e.length == 0) ? null : e[0]);
    }

    public static void info(Object origin, String message, Throwable... e) {
        Log.i(getName(origin), getMessage(message, true), (e.length == 0) ? null : e[0]);
    }

    public static void log(String message) {
        Log.d("DEBUG VISUM", message);
    }

}