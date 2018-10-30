package com.visium.fieldservice.controller;

import android.content.Context;

import com.visium.fieldservice.api.visium.bean.request.LightingRequest;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.LightingResponse;
import com.visium.fieldservice.api.visium.equipament_api.LightingApi;
import com.visium.fieldservice.entity.Lighting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Andrew Willard
 */
public class LightingController {

    private static final LightingController INSTANCE = new LightingController();

    private LightingController() {}

    public static LightingController get() {
        return INSTANCE;
    }

    public void getLightingList(long postId, final VisiumApiCallback<List<Lighting>> callback) {

        LightingApi.getLightingList(postId, new VisiumApiCallback<GenericResponse<List<LightingResponse>>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<List<LightingResponse>> response, ErrorResponse e) {

                List<Lighting> lightingList;

                if (e == null && response != null && response.getData() != null) {
                    lightingList = new ArrayList<>(response.getData().size());
                    for (LightingResponse resp : response.getData()) {
                        lightingList.add(new Lighting(resp));
                    }
                } else {
                    lightingList = Collections.<Lighting>emptyList();
                }

                callback.callback(lightingList, e);
            }
        });
    }

    public void save(Context ctx, Lighting lighting, final VisiumApiCallback<Lighting> callback) {
        LightingApi.save(new LightingRequest(lighting), new VisiumApiCallback<GenericResponse<LightingResponse>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<LightingResponse> response, ErrorResponse e) {

                if (e == null && response != null && response.getData() != null) {
                    callback.callback(new Lighting(response.getData()), e);
                } else {
                    callback.callback(null, e);
                }
            }
        });
    }

    public void create(Context ctx, Lighting lighting, final VisiumApiCallback<Lighting> callback) {
        LightingApi.create(new LightingRequest(lighting), new VisiumApiCallback<GenericResponse<LightingResponse>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<LightingResponse> response, ErrorResponse e) {

                if (e == null && response != null && response.getData() != null) {
                    callback.callback(new Lighting(response.getData()), e);
                } else {
                    callback.callback(null, e);
                }
            }
        });
    }

    public void delete(Context ctx, Lighting lighting, final VisiumApiCallback<Lighting> callback) {
        LightingApi.delete(new LightingRequest(lighting), new VisiumApiCallback<GenericResponse<LightingResponse>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<LightingResponse> response, ErrorResponse e) {

                if (e == null && response != null && response.getData() != null) {
                    callback.callback(new Lighting(response.getData()), e);
                } else {
                    callback.callback(null, e);
                }
            }
        });
    }

}