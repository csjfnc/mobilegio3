package com.visium.fieldservice.api.visium.bean.request;

import android.net.http.HttpResponseCache;

import com.visium.fieldservice.BuildConfig;
import com.visium.fieldservice.util.LogUtils;

import io.fabric.sdk.android.services.network.HttpRequest;

/**
 * Created by andrew on 1/20/16.
 */
public class LoginRequest {

    private String userName;
    private String password;
    private int version = BuildConfig.VERSION_CODE;

    public LoginRequest() {}

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getAuthorization() {
        String authorization = String.format("Basic %s", HttpRequest.Base64
                .encode(String.format("%s:%s:%s", userName, password, version)));
        LogUtils.log(authorization);
        return authorization;
    }
}
