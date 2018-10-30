package com.visium.fieldservice.api.visium.serviceorder;

import com.visium.fieldservice.api.visium.bean.response.CityResponse;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.ServiceOrderDetailsResponse;
import com.visium.fieldservice.api.visium.bean.response.ServiceOrderResponse;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

import java.util.List;

/**
 * @author Andrew Willard
 */
interface ServiceOrderService {

    @GET( "/cidade/getlist")
    public void getList(Callback<GenericResponse<List<CityResponse>>> callback);

    @GET( "/os/getbycidade")
    public void getOrders(@Query("idcidade") int cityId, Callback<GenericResponse<List<ServiceOrderResponse>>> callback);

    @GET( "/os/get")
    public void getOrder(@Query("idordemdeservico") long id, Callback<GenericResponse<ServiceOrderDetailsResponse>> callback);

    @POST( "/os/reabrir")
    public void openOrder(@Query("idordemdeservico") long id, Callback<GenericResponse<ServiceOrderDetailsResponse>> callback);

    @POST( "/os/encerramento")
    public void closeOrder(@Query("idordemdeservico") long id,
                           @Query("data") String finishDate,
                           Callback<GenericResponse<ServiceOrderDetailsResponse>> callback);

    @Multipart
    @POST( "/offlinemode/savestructureos")
    public void publishOrder(@Part("filename") TypedFile file, @Part("description") String description,
                             Callback<GenericResponse<Object>> callback);
}
