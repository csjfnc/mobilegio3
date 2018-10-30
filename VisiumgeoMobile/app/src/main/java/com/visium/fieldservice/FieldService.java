package com.visium.fieldservice;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import com.crashlytics.android.Crashlytics;
import com.visium.fieldservice.api.visium.FieldServiceApi;
import com.visium.fieldservice.preference.LocalCache;
import com.visium.fieldservice.preference.PreferenceHelper;
import com.visium.fieldservice.preference.PreferenceKey;
import com.visium.fieldservice.ui.login.LoginActivity;
import com.visium.fieldservice.ui.maps.MapsServiceOrderActivity;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.LogUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.fabric.sdk.android.Fabric;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class FieldService extends MultiDexApplication {

    private LocalCache localCache;
    private static FieldService instance;

    @Override
    public void onCreate() {
        super.onCreate();
        Calendar cc = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String date = df.format(cc.getTime());
        LogUtils.log("Date = "+date);
        Fabric.with(this, new Crashlytics());
        instance = this;
        localCache = LocalCache.init(this);
        FieldServiceApi.configureAPI(getString(R.string.fieldservice_server_api),
                Integer.valueOf(getString(R.string.fieldservice_timeout_api)));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int versionCode = sharedPreferences.getInt(PreferenceKey.VERSION_CODE, BuildConfig.VERSION_CODE);
        LogUtils.log("PREF VERSION CODE = "+versionCode + " BUILDCONFIG = "+BuildConfig.VERSION_CODE);
        if(versionCode != BuildConfig.VERSION_CODE) {
            FileUtils.deleteAll();
            PreferenceHelper.setImei(null);
            PreferenceHelper.setUserProfile(null);
            PreferenceHelper.setGcmToken(null);
        }

        sharedPreferences.edit().putInt(PreferenceKey.VERSION_CODE, BuildConfig.VERSION_CODE).apply();
    }

    public static FieldService get() {
        return instance;
    }

}
