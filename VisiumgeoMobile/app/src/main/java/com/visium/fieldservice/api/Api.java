package com.visium.fieldservice.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.GsonBuilder;
import retrofit.RequestInterceptor;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * @author Andrew Willard
 */
public abstract class Api {

    protected static final RestAdapter.LogLevel LOG_LEVEL = RestAdapter.LogLevel.FULL;

    private static final GsonConverter GSON_CONVERTER = new GsonConverter(
            new GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create());

    public GsonConverter getGsonConverter() {
        return GSON_CONVERTER;
    }

    public abstract RestAdapter.Builder getNewRestBuilder();
    public abstract RequestInterceptor getNewRequestInterceptor();

}