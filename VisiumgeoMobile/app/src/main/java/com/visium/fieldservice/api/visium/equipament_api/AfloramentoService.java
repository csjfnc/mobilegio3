package com.visium.fieldservice.api.visium.equipament_api;

import com.visium.fieldservice.api.visium.bean.request.AfloramentoRequest;
import com.visium.fieldservice.api.visium.bean.response.AfloramentoResponse;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;


interface AfloramentoService {

    @GET( "/afloramento/getbyposte")
    public void getAfloramentoList(
            @Query("idposte") long postId,
            Callback<GenericResponse<List<AfloramentoResponse>>> callback);

    @GET( "/afloramento/get")
    public void getAfloramento(
            @Query("id") long id,
            Callback<GenericResponse<AfloramentoResponse>> callback);

    @PUT( "/afloramento/edit")
    public void save(@Body AfloramentoRequest request,
                     Callback<GenericResponse<AfloramentoResponse>> callback);

    @POST( "/afloramento/add")
    public void create(@Body AfloramentoRequest request,
                       Callback<GenericResponse<AfloramentoResponse>> callback);

    /*@DELETE( "/afloramento/{id}")
    public void delete(@Path("id") long id,
                       Callback<GenericResponse<AfloramentoResponse>> callback);
                       */

    @POST( "/afloramento/deletar")
    public void delete(@Body AfloramentoRequest request,
                       Callback<GenericResponse<AfloramentoResponse>> callback);
}