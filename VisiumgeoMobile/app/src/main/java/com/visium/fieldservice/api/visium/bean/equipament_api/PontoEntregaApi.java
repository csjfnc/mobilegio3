package com.visium.fieldservice.api.visium.bean.equipament_api;

import com.visium.fieldservice.api.DefaultCallback;
import com.visium.fieldservice.api.visium.FieldServiceApi;
import com.visium.fieldservice.api.visium.bean.request.PontoEntregaRequest;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.PontoEntregaResponse;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.util.LogUtils;

import java.util.List;


@SuppressWarnings("unchecked")
public class PontoEntregaApi extends FieldServiceApi<PontoEntregaService> {

    private static final PontoEntregaApi INSTANCE = new PontoEntregaApi();

    protected PontoEntregaApi() {
        super(PontoEntregaService.class);
    }

    public static void getPontoEntregaList(long postId, final VisiumApiCallback<GenericResponse<List<PontoEntregaResponse>>> callback) {

        try {

            INSTANCE.service.getPontoEntregaList(postId, new DefaultCallback<GenericResponse<List<PontoEntregaResponse>>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(PontoEntregaApi.class, e);
        }
    }

    public static void getPontoEntrega(long id, final VisiumApiCallback<GenericResponse<PontoEntregaResponse>> callback) {

        try {

            INSTANCE.service.getPontoEntrega(id, new DefaultCallback<GenericResponse<PontoEntregaResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(PontoEntregaApi.class, e);
        }
    }

    public static void save(PontoEntregaRequest request, final VisiumApiCallback<GenericResponse<PontoEntregaResponse>> callback) {

        try {

            INSTANCE.service.save(request, new DefaultCallback<GenericResponse<PontoEntregaResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(PontoEntregaApi.class, e);
        }
    }

    public static void create(PontoEntregaRequest request, final VisiumApiCallback<GenericResponse<PontoEntregaResponse>> callback) {

        try {

            INSTANCE.service.create(request, new DefaultCallback<GenericResponse<PontoEntregaResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(PontoEntregaApi.class, e);
        }
    }

    public static void delete(PontoEntregaRequest request, final VisiumApiCallback<GenericResponse<PontoEntregaResponse>> callback) {

        try {

            INSTANCE.service.delete(request, new DefaultCallback<GenericResponse<PontoEntregaResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(PontoEntregaApi.class, e);
        }
    }
}
