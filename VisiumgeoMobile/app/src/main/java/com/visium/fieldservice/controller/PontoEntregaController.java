package com.visium.fieldservice.controller;

import android.content.Context;

import com.visium.fieldservice.api.visium.bean.equipament_api.PontoEntregaApi;
import com.visium.fieldservice.api.visium.bean.request.PontoEntregaRequest;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.PontoEntregaResponse;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.util.GeoUtils;
import com.visium.fieldservice.util.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PontoEntregaController {

    private static final PontoEntregaController INSTANCE = new PontoEntregaController();

    private PontoEntregaController() {}

    public static PontoEntregaController get() {
        return INSTANCE;
    }

    public void getPontoEntregaList(long postId, final VisiumApiCallback<List<PontoEntrega>> callback) {

        PontoEntregaApi.getPontoEntregaList(postId, new VisiumApiCallback<GenericResponse<List<PontoEntregaResponse>>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<List<PontoEntregaResponse>> response, ErrorResponse e) {

                List<PontoEntrega> pontoEntregaList;

                if (e == null && response != null && response.getData() != null) {
                    pontoEntregaList = new ArrayList<>(response.getData().size());
                    for (PontoEntregaResponse resp : response.getData()) {
                        pontoEntregaList.add(new PontoEntrega(resp));
                    }
                } else {
                    pontoEntregaList = Collections.<PontoEntrega>emptyList();
                }

                callback.callback(pontoEntregaList, e);
            }
        });
    }

    public void save(Context ctx, PontoEntrega pontoEntrega, final VisiumApiCallback<PontoEntrega> callback) {
        PontoEntregaApi.save(new PontoEntregaRequest(pontoEntrega), new VisiumApiCallback<GenericResponse<PontoEntregaResponse>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<PontoEntregaResponse> response, ErrorResponse e) {

                if (e == null && response != null && response.getData() != null) {
                    callback.callback(new PontoEntrega(response.getData()), e);
                } else {
                    callback.callback(null, e);
                }
            }
        });
    }

    public void create(Context ctx, PontoEntrega pontoEntrega, final VisiumApiCallback<PontoEntrega> callback) {
        PontoEntregaApi.create(new PontoEntregaRequest(pontoEntrega), new VisiumApiCallback<GenericResponse<PontoEntregaResponse>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<PontoEntregaResponse> response, ErrorResponse e) {

                if (e == null && response != null && response.getData() != null) {
                    callback.callback(new PontoEntrega(response.getData()), e);
                } else {
                    callback.callback(null, e);
                }
            }
        });
    }
    
    public void delete(Context ctx, PontoEntrega pontoEntrega, final VisiumApiCallback<PontoEntrega> callback) {
        PontoEntregaApi.delete(new PontoEntregaRequest(pontoEntrega), new VisiumApiCallback<GenericResponse<PontoEntregaResponse>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<PontoEntregaResponse> response, ErrorResponse e) {

                if (e == null && response != null && response.getData() != null) {
                    callback.callback(new PontoEntrega(response.getData()), e);
                } else {
                    callback.callback(null, e);
                }
            }
        });
    }


}