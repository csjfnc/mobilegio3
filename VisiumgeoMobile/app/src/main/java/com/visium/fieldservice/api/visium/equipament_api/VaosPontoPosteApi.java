package com.visium.fieldservice.api.visium.equipament_api;

import com.visium.fieldservice.api.DefaultCallback;
import com.visium.fieldservice.api.visium.FieldServiceApi;
import com.visium.fieldservice.api.visium.bean.request.VaosPontoPosteRequest;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.VaosPontoPosteResponse;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.util.LogUtils;

/**
 * Created by fjesus on 02/01/2018.
 */

public class VaosPontoPosteApi extends FieldServiceApi<VaosPontoPosteService>{

    private static final VaosPontoPosteApi INSTANCE = new VaosPontoPosteApi();

    public VaosPontoPosteApi() {
        super(VaosPontoPosteService.class);
    }

    public static void create(VaosPontoPosteRequest request, final VisiumApiCallback<GenericResponse<VaosPontoPosteResponse>> callback) {
        try {
            INSTANCE.service.create(request, new DefaultCallback<GenericResponse<VaosPontoPosteResponse>>(callback));
        } catch (Exception e) {
            LogUtils.apiError(AfloramentoApi.class, e);
        }
    }
}
