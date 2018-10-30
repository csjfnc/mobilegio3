package com.visium.fieldservice.api.visium;

import android.content.Context;
import android.database.Cursor;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.visium.fieldservice.FieldService;
import com.visium.fieldservice.api.Api;
import com.visium.fieldservice.entity.UserProfile;
import com.visium.fieldservice.preference.PreferenceHelper;
import org.apache.commons.lang3.StringUtils;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import com.squareup.okhttp.OkHttpClient;
import retrofit.client.OkClient;
import java.util.concurrent.TimeUnit;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public abstract class FieldServiceApi<T> extends Api {

    private static String serverUrl;
    private static int defaultTimeout;

    protected T service;

    protected FieldServiceApi(Class<T> clazz) {
        service = getNewRestBuilder().build().create(clazz);
    }

    @Override
    public RestAdapter.Builder getNewRestBuilder() {

        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(defaultTimeout, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(defaultTimeout, TimeUnit.SECONDS);

        return new RestAdapter.Builder()
                .setLogLevel(Api.LOG_LEVEL)
                .setConverter(getGsonConverter())
                .setClient(new OkClient(okHttpClient))
                .setEndpoint(serverUrl)
                .setRequestInterceptor(getNewRequestInterceptor());
    }

    @Override
    public RequestInterceptor getNewRequestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void intercept(RequestFacade request) {
                String imei;
                //;String imeiTest = "358182070324359";
                String imeiTest = "358502078953166";
                if (StringUtils.isNotBlank(PreferenceHelper.getImei())) {
                    imei = PreferenceHelper.getImei();
                } else {
                    imei = ((TelephonyManager) FieldService.get().getSystemService(
                            Context.TELEPHONY_SERVICE)).getDeviceId();
                }
                request.addHeader("IMEI", imeiTest);

                UserProfile userProfile = PreferenceHelper.getUserProfile();
                if (userProfile != null && userProfile.getAuthToken() != null
                        && userProfile.getAuthToken().exists() && !userProfile.getAuthToken().isExpired()) {
                    request.addHeader("Token", userProfile.getAuthToken().get());
                    Log.e("TOKEN", userProfile.getAuthToken().get());
                    Log.e("IMEI", imeiTest);
                }
            }
        };
    }

    public static void configureAPI(String serverUrl, int defaultTimeout) {
        FieldServiceApi.serverUrl = serverUrl;
        FieldServiceApi.defaultTimeout = defaultTimeout;
    }

}
