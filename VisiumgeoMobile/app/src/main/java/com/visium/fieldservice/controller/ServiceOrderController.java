package com.visium.fieldservice.controller;

import com.visium.fieldservice.api.visium.bean.response.CityResponse;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.GenericResponse;
import com.visium.fieldservice.api.visium.bean.response.ServiceOrderDetailsResponse;
import com.visium.fieldservice.api.visium.bean.response.ServiceOrderResponse;
import com.visium.fieldservice.api.visium.serviceorder.ServiceOrderApi;
import com.visium.fieldservice.entity.City;
import com.visium.fieldservice.entity.ServiceOrder;
import com.visium.fieldservice.entity.ServiceOrderDetails;
import com.visium.fieldservice.util.FileUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Andrew Willard
 */
public class ServiceOrderController {

    private static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_PATTERN);
    private static final ServiceOrderController INSTANCE = new ServiceOrderController();

    private ServiceOrderController() {}

    public static ServiceOrderController get() {
        return INSTANCE;
    }

    public void getCities(final VisiumApiCallback<List<City>> callback) {

        ServiceOrderApi.getCities(new VisiumApiCallback<GenericResponse<List<CityResponse>>>() {

            private static final long serialVersionUID = -7401289448345173931L;

            @Override
            public void callback(GenericResponse<List<CityResponse>> response, ErrorResponse e) {

                List<City> cities;

                if (e == null && response.getData() != null) {
                    cities = new ArrayList<>(response.getData().size());
                    for (CityResponse resp : response.getData()) {
                        cities.add(new City(resp.getId(), resp.getName()));
                    }
                } else {
                    cities = Collections.<City>emptyList();
                }

                callback.callback(cities, e);
            }
        });
    }

    public void getServiceOrders(City city, final VisiumApiCallback<List<ServiceOrder>> callback) {

        ServiceOrderApi.getOrders(city.getId(), new VisiumApiCallback<GenericResponse<List<ServiceOrderResponse>>>() {

            private static final long serialVersionUID = 7468650977202068869L;

            @Override
            public void callback(GenericResponse<List<ServiceOrderResponse>> response, ErrorResponse e) {

                List<ServiceOrder> orders;

                if (e != null || response == null || response.getData() == null) {
                    orders = null;
                } else {
                    orders = new ArrayList<ServiceOrder>(response.getData().size());
                    for (ServiceOrderResponse resp : response.getData()) {
                        orders.add(new ServiceOrder(resp));
                    }
                }

                callback.callback(orders, e);
            }
        });
    }

    public void getServiceOrder(ServiceOrder order, final VisiumApiCallback<ServiceOrderDetails> callback) {

        ServiceOrderApi.getOrder(order.getId(), new VisiumApiCallback<GenericResponse<ServiceOrderDetailsResponse>>() {

            private static final long serialVersionUID = 7468650977202068869L;

            @Override
            public void callback(GenericResponse<ServiceOrderDetailsResponse> response, ErrorResponse e) {

                ServiceOrderDetails order;

                if (e != null || response == null || response.getData() == null) {
                    order = null;
                } else {
                    order = new ServiceOrderDetails(response.getData());
                }

                callback.callback(order, e);
            }
        });
    }

    public void updateOrder(ServiceOrderDetails order, final VisiumApiCallback<Boolean> callback) {

        if (order.getFinishDateTime() != null) {
            ServiceOrderApi.closeOrder(order.getId(), DATE_FORMAT.format(order.getFinishDateTime()),
                    new VisiumApiCallback<GenericResponse<ServiceOrderDetailsResponse>> () {

                    private static final long serialVersionUID = 7468650977202068869L;

                    @Override
                    public void callback (GenericResponse <ServiceOrderDetailsResponse> response, ErrorResponse e){
                    callback.callback(e == null, e);
                }
            });

        } else {

            ServiceOrderApi.openOrder(order.getId(), new VisiumApiCallback<GenericResponse<ServiceOrderDetailsResponse>>() {

                private static final long serialVersionUID = 7468650977202068869L;

                @Override
                public void callback(GenericResponse<ServiceOrderDetailsResponse> response, ErrorResponse e) {
                    callback.callback(e == null, e);
                }
            });
        }
    }

    public void publishOfflineOrder(long orderId,
                                    final VisiumApiCallback<Object> callback) {

        ServiceOrderApi.publishOrder(FileUtils.getFile(orderId), new VisiumApiCallback<GenericResponse<Object>>() {

            private static final long serialVersionUID = 7468650977202068869L;

            @Override
            public void callback(GenericResponse<Object> response, ErrorResponse e) {
                callback.callback(e == null, e);
            }
        });

    }

}