package com.visium.fieldservice.api.visium.equipament_api;

import com.visium.fieldservice.api.visium.bean.request.LightingRequest;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.LightingResponse;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Query;

import java.util.List;

/**
 * @author Andrew Willard
 */
interface LightingService {

    @GET( "/ip/getbyposte")
    public void getLightingList(
            @Query("idposte") long postId,
            Callback<GenericResponse<List<LightingResponse>>> callback);

    @GET( "/ip/get")
    public void getLighting(
            @Query("idip") long id,
            Callback<GenericResponse<LightingResponse>> callback);

    @PUT( "/ip/edit")
    public void save(@Body LightingRequest request,
                     Callback<GenericResponse<LightingResponse>> callback);

    @POST( "/ip/add")
    public void create(@Body LightingRequest request,
                       Callback<GenericResponse<LightingResponse>> callback);

    @POST( "/ip/deletar")
    public void delete(@Body LightingRequest request,
                       Callback<GenericResponse<LightingResponse>> callback);
}