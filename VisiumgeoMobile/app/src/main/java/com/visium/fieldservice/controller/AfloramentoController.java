package com.visium.fieldservice.controller;

import android.content.Context;

import com.visium.fieldservice.api.visium.bean.request.AfloramentoRequest;
import com.visium.fieldservice.api.visium.bean.response.AfloramentoResponse;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.equipament_api.AfloramentoApi;
import com.visium.fieldservice.entity.equipament.Afloramento;
import com.visium.fieldservice.util.GeoUtils;
import com.visium.fieldservice.util.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AfloramentoController {

    private static final AfloramentoController INSTANCE = new AfloramentoController();

    private AfloramentoController() {}

    public static AfloramentoController get() {
        return INSTANCE;
    }

    public void getAfloramentoList(long postId, final VisiumApiCallback<List<Afloramento>> callback) {

        AfloramentoApi.getAfloramentoList(postId, new VisiumApiCallback<GenericResponse<List<AfloramentoResponse>>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<List<AfloramentoResponse>> response, ErrorResponse e) {

                List<Afloramento> afloramentoList;

                if (e == null && response != null && response.getData() != null) {
                    afloramentoList = new ArrayList<>(response.getData().size());
                    for (AfloramentoResponse resp : response.getData()) {
                        afloramentoList.add(new Afloramento(resp));
                    }
                } else {
                    afloramentoList = Collections.<Afloramento>emptyList();
                }

                callback.callback(afloramentoList, e);
            }
        });
    }

    public void save(Context ctx, Afloramento afloramento, final VisiumApiCallback<Afloramento> callback) {
        putCurrentPosition(ctx, afloramento);
        AfloramentoApi.save(new AfloramentoRequest(afloramento), new VisiumApiCallback<GenericResponse<AfloramentoResponse>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<AfloramentoResponse> response, ErrorResponse e) {

                if (e == null && response != null && response.getData() != null) {
                    callback.callback(new Afloramento(response.getData()), e);
                } else {
                    callback.callback(null, e);
                }
            }
        });
    }

    public void create(Context ctx, Afloramento afloramento, final VisiumApiCallback<Afloramento> callback) {
        putCurrentPosition(ctx, afloramento);
        AfloramentoApi.create(new AfloramentoRequest(afloramento), new VisiumApiCallback<GenericResponse<AfloramentoResponse>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<AfloramentoResponse> response, ErrorResponse e) {

                if (e == null && response != null && response.getData() != null) {
                    callback.callback(new Afloramento(response.getData()), e);
                } else {
                    callback.callback(null, e);
                }
            }
        });
    }
    
    public void delete(Context ctx, Afloramento afloramento, final VisiumApiCallback<Afloramento> callback) {
        putCurrentPosition(ctx, afloramento); //FIXME
        AfloramentoApi.delete(new AfloramentoRequest(afloramento), new VisiumApiCallback<GenericResponse<AfloramentoResponse>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<AfloramentoResponse> response, ErrorResponse e) {

                if (e == null && response != null && response.getData() != null) {
                    callback.callback(new Afloramento(response.getData()), e);
                } else {
                    callback.callback(null, e);
                }
            }
        });
    }

    private boolean putCurrentPosition(Context ctx, Afloramento afloramento) {
        double[] t = GeoUtils.getCurrentPosition(ctx);
        if(t!=null) {
            LogUtils.log("lat: "+ t[0] + " long: " + t[1]);
            afloramento.setLatAtualizacao(t[0]);
            afloramento.setLonAtualizacao(t[1]);
            return true;
        } else {
            afloramento.setLatAtualizacao((double)0);
            afloramento.setLonAtualizacao((double)0);
            LogUtils.log("getCurrentPosition returned null");
            return false;
        }
    }

}