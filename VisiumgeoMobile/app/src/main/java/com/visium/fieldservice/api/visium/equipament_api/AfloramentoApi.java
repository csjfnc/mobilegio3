package com.visium.fieldservice.api.visium.equipament_api;

import com.visium.fieldservice.api.DefaultCallback;
import com.visium.fieldservice.api.visium.FieldServiceApi;
import com.visium.fieldservice.api.visium.bean.request.AfloramentoRequest;
import com.visium.fieldservice.api.visium.bean.response.AfloramentoResponse;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.util.LogUtils;

import java.util.List;


@SuppressWarnings("unchecked")
public class AfloramentoApi extends FieldServiceApi<AfloramentoService> {

    private static final AfloramentoApi INSTANCE = new AfloramentoApi();

    protected AfloramentoApi() {
        super(AfloramentoService.class);
    }

    public static void getAfloramentoList(long postId, final VisiumApiCallback<GenericResponse<List<AfloramentoResponse>>> callback) {
        try {
            INSTANCE.service.getAfloramentoList(postId, new DefaultCallback<GenericResponse<List<AfloramentoResponse>>>(callback));
        } catch (Exception e) {
            LogUtils.apiError(AfloramentoApi.class, e);
        }
    }

    public static void getAfloramento(long id, final VisiumApiCallback<GenericResponse<AfloramentoResponse>> callback) {

        try {

            INSTANCE.service.getAfloramento(id, new DefaultCallback<GenericResponse<AfloramentoResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(AfloramentoApi.class, e);
        }
    }

    public static void save(AfloramentoRequest request, final VisiumApiCallback<GenericResponse<AfloramentoResponse>> callback) {

        try {

            INSTANCE.service.save(request, new DefaultCallback<GenericResponse<AfloramentoResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(AfloramentoApi.class, e);
        }
    }

    public static void create(AfloramentoRequest request, final VisiumApiCallback<GenericResponse<AfloramentoResponse>> callback) {
        try {
            INSTANCE.service.create(request, new DefaultCallback<GenericResponse<AfloramentoResponse>>(callback));
        } catch (Exception e) {
            LogUtils.apiError(AfloramentoApi.class, e);
        }
    }

    public static void delete(AfloramentoRequest request, final VisiumApiCallback<GenericResponse<AfloramentoResponse>> callback) {

        try {

            INSTANCE.service.delete(request, new DefaultCallback<GenericResponse<AfloramentoResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(AfloramentoApi.class, e);
        }
    }
}
