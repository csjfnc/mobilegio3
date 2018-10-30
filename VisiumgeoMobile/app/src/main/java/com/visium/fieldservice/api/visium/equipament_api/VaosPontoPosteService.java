package com.visium.fieldservice.api.visium.equipament_api;

import com.visium.fieldservice.api.visium.bean.request.LightingRequest;
import com.visium.fieldservice.api.visium.bean.request.VaosPontoPosteRequest;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.LightingResponse;
import com.visium.fieldservice.api.visium.bean.response.VaoPrimarioResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by fjesus on 02/01/2018.
 */

public interface VaosPontoPosteService {

    @GET("/vaosposteponto/get")
    public void getVaosPontoPoste();

    @POST( "/vaosposteponto/add")
    public void create(@Body VaosPontoPosteRequest request,
                       Callback<GenericResponse<VaoPrimarioResponse>> callback);
}
