package com.visium.fieldservice.api.visium.equipament_api;

import com.visium.fieldservice.api.DefaultCallback;
import com.visium.fieldservice.api.visium.FieldServiceApi;
import com.visium.fieldservice.api.visium.bean.request.LightingRequest;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.LightingResponse;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.util.LogUtils;

import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
@SuppressWarnings("unchecked")
public class LightingApi extends FieldServiceApi<LightingService> {

    private static final LightingApi INSTANCE = new LightingApi();

    protected LightingApi() {
        super(LightingService.class);
    }

    public static void getLightingList(long postId, final VisiumApiCallback<GenericResponse<List<LightingResponse>>> callback) {

        try {

            INSTANCE.service.getLightingList(postId, new DefaultCallback<GenericResponse<List<LightingResponse>>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(LightingApi.class, e);
        }
    }

    public static void getLighting(long id, final VisiumApiCallback<GenericResponse<LightingResponse>> callback) {

        try {

            INSTANCE.service.getLighting(id, new DefaultCallback<GenericResponse<LightingResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(LightingApi.class, e);
        }
    }

    public static void save(LightingRequest request, final VisiumApiCallback<GenericResponse<LightingResponse>> callback) {

        try {

            INSTANCE.service.save(request, new DefaultCallback<GenericResponse<LightingResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(LightingApi.class, e);
        }
    }

    public static void create(LightingRequest request, final VisiumApiCallback<GenericResponse<LightingResponse>> callback) {

        try {

            INSTANCE.service.create(request, new DefaultCallback<GenericResponse<LightingResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(LightingApi.class, e);
        }
    }

    public static void delete(LightingRequest request, final VisiumApiCallback<GenericResponse<LightingResponse>> callback) {

        try {

            INSTANCE.service.delete(request, new DefaultCallback<GenericResponse<LightingResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(LightingApi.class, e);
        }
    }
}
