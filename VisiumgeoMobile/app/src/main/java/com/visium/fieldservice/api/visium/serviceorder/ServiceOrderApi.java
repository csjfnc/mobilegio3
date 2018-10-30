package com.visium.fieldservice.api.visium.serviceorder;

import com.visium.fieldservice.api.DefaultCallback;
import com.visium.fieldservice.api.visium.FieldServiceApi;
import com.visium.fieldservice.api.visium.bean.response.CityResponse;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.ServiceOrderDetailsResponse;
import com.visium.fieldservice.api.visium.bean.response.ServiceOrderResponse;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.util.LogUtils;

import java.io.File;
import java.util.List;

import retrofit.mime.TypedFile;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
@SuppressWarnings("unchecked")
public class ServiceOrderApi extends FieldServiceApi<ServiceOrderService> {

    private static final ServiceOrderApi INSTANCE = new ServiceOrderApi();

    protected ServiceOrderApi() {
        super(ServiceOrderService.class);
    }

    public static void getCities(final VisiumApiCallback<GenericResponse<List<CityResponse>>> callback) {

        try {

            INSTANCE.service.getList(new DefaultCallback<GenericResponse<List<CityResponse>>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(ServiceOrderApi.class, e);
        }
    }

    public static void getOrders(int cityId, final VisiumApiCallback<GenericResponse<List<ServiceOrderResponse>>> callback) {

        try {

            INSTANCE.service.getOrders(cityId, new DefaultCallback<GenericResponse<List<ServiceOrderResponse>>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(ServiceOrderApi.class, e);
        }
    }

    public static void getOrder(long id, final VisiumApiCallback<GenericResponse<ServiceOrderDetailsResponse>> callback) {

        try {

            INSTANCE.service.getOrder(id, new DefaultCallback<GenericResponse<ServiceOrderDetailsResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(ServiceOrderApi.class, e);
        }
    }

    public static void openOrder(long id, final VisiumApiCallback<GenericResponse<ServiceOrderDetailsResponse>> callback) {

        try {

            INSTANCE.service.openOrder(id, new DefaultCallback<GenericResponse<ServiceOrderDetailsResponse>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(ServiceOrderApi.class, e);
        }
    }

    public static void publishOrder(File file, final VisiumApiCallback<GenericResponse<Object>> callback) {

        try {

            INSTANCE.service.publishOrder(new TypedFile("multipart/form-data", file), file.getName(),
                    new DefaultCallback<GenericResponse<Object>>(callback));

        } catch (Exception e) {
            LogUtils.apiError(ServiceOrderApi.class, e);
        }
    }


    public static void closeOrder(long id, String finishDate,
                                   final VisiumApiCallback<GenericResponse<ServiceOrderDetailsResponse>> callback) {

        try {

            INSTANCE.service.closeOrder(id, finishDate,
                    new DefaultCallback<GenericResponse<ServiceOrderDetailsResponse>> (callback));

        } catch (Exception e) {
            LogUtils.apiError(ServiceOrderApi.class, e);
        }
    }

}
