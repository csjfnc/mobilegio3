package com.visium.fieldservice.api.visium.bean.equipament_api;

import com.visium.fieldservice.api.visium.bean.request.PontoEntregaRequest;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.PontoEntregaResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;


interface PontoEntregaService {

    @GET( "/pontoentrega/getbyposte")
    public void getPontoEntregaList(
            @Query("idposte") long postId,
            Callback<GenericResponse<List<PontoEntregaResponse>>> callback);

    @GET( "/pontoentrega/get")
    public void getPontoEntrega(
            @Query("id") long id,
            Callback<GenericResponse<PontoEntregaResponse>> callback);

    @PUT( "/pontoentrega/edit")
    public void save(@Body PontoEntregaRequest request,
                     Callback<GenericResponse<PontoEntregaResponse>> callback);

    @POST( "/pontoentrega/add")
    public void create(@Body PontoEntregaRequest request,
                       Callback<GenericResponse<PontoEntregaResponse>> callback);

    /*@DELETE( "/pontoentrega/{id}")
    public void delete(@Path("id") long id,
                       Callback<GenericResponse<PontoEntregaResponse>> callback);
                       */

    @POST( "/pontoentrega/deletar")
    public void delete(@Body PontoEntregaRequest request,
                       Callback<GenericResponse<PontoEntregaResponse>> callback);
}