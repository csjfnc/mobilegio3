package com.visium.fieldservice.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.visium.fieldservice.controller.Callback;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class GeoUtils {

    public static void getLocationFromAddress(final Context context, final String strAddress, final Callback<LatLng> callback){

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                Geocoder coder = new Geocoder(context);
                final List<Address> address;

                try {
                    address = coder.getFromLocationName(strAddress, 5);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (address == null) {
                                callback.callback(null, null);
                            } else {

                                Address location = address.get(0);
                                location.getLatitude();
                                location.getLongitude();

                                callback.callback(new LatLng(location.getLatitude(), location.getLongitude()), null);
                            }
                        }
                    });

                } catch (final Exception e) {
                    LogUtils.error(GeoUtils.class, e);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.callback(null, e);
                        }
                    });
                }
            }
        });
    }

    public static void getAddress(final Context context, final LatLng latLng, final Callback<List<Address>> callback){

        new Handler().post(new Runnable() {
            @Override
            public void run() {

                Geocoder coder = new Geocoder(context);
                final List<Address> address;

                try {

                    address = coder.getFromLocation(latLng.latitude, latLng.longitude, 5);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            if (address == null || address.size() <= 0) {
                                callback.callback(null, null);
                            } else {
                                callback.callback(address, null);
                            }
                        }
                    });

                } catch (final Exception e) {
                    LogUtils.error(GeoUtils.class, e);

                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            callback.callback(null, e);
                        }
                    });
                }

            }
        });
    }

    public static String getAddressUTF8(String value) {
        try {
            value = value == null ? StringUtils.EMPTY : URLDecoder.decode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            value = StringUtils.EMPTY;
        }
        return value;
    }

    public static LatLngBounds createLatLngBounds(List<LatLng> points) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (LatLng latLng : points) {
            builder.include(latLng);
        }
        return builder.build();
    }

    public static LatLngBounds createLatLngBounds(LatLng point) {
        return new LatLngBounds.Builder().include(point).build();
    }

    //Tonon
    private static LocationChangeListener lcl;

    public static double[] getCurrentPosition(Context ctx) {
        double[] result = null;
        LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        Criteria cri = new Criteria();

        LogUtils.log("A");
        if (!(ActivityCompat.checkSelfPermission(ctx,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(ctx,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {

            LogUtils.log("B");
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            //if(location != null ) mudar, a que está é para testes
            if (location != null) {
                LogUtils.log("C");
                result = new double[]{location.getLatitude(), location.getLongitude()};
                LogUtils.log("D");
                LogUtils.log("lastKnown lat: " + result[0] + " lon: " + result [1]);
            } else {
                LogUtils.log("E");
                requestLocationUpdate(ctx);

                if(lcl!=null) {
                    result = lcl.getLocation();
                }
                if(result == null) {
                    result = new double[]{(double)0, (double)0};
                }
            }
        }

        return result;
    }

    public static void requestLocationUpdate(Context ctx) {
        if(lcl !=null && lcl.isFinished()) {
            lcl.unregister();
            lcl = null;
        }
        if(lcl == null) {
            LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
            lcl = new LocationChangeListener(lm);
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lcl);
            lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, lcl);
        }
    }

    private static class LocationChangeListener implements LocationListener {
        private double[] result = null;
        private boolean isFinished = false;
        private LocationManager lm;

        public LocationChangeListener(LocationManager lm) {
            this.lm = lm;
        }

        public double[] getLocation() {
            return result;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public void unregister() {
            lm.removeUpdates(this);
        }

        @Override
        public void onLocationChanged(Location location) {
            LogUtils.log("onLocChanged");
            if(location != null) {
                LogUtils.log("onLocChanged location != null");
                result = new double[]{location.getLatitude(), location.getLongitude()};
                LogUtils.log("onLocChanged lat: " + result[0] + " lon: " + result [1]);
                isFinished = true;
                unregister();
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    }


}