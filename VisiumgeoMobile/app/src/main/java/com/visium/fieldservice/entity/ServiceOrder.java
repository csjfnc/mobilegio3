package com.visium.fieldservice.entity;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.api.visium.bean.response.ServiceOrderPolygonResponse;
import com.visium.fieldservice.api.visium.bean.response.ServiceOrderResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class ServiceOrder implements Serializable {

    private static final long serialVersionUID = 8256422150496407344L;

    private long id;
    private String number;
    private List<ServiceOrderPolygon> polygons;
    private List<LatLng> points = new ArrayList<>();
    private int status;
    private long totalPostes;
    private long totalDemandas;
    private long totalStrands;
    private long totalAnotacao;

    public long getTotalStrands() {
        return totalStrands;
    }

    public void setTotalStrands(long totalStrands) {
        this.totalStrands = totalStrands;
    }

    public long getTotalAnotacao() {
        return totalAnotacao;
    }

    public void setTotalAnotacao(long totalAnotacao) {
        this.totalAnotacao = totalAnotacao;
    }

    public ServiceOrder() {}

    public ServiceOrder(ServiceOrderResponse serviceOrderResponse) {
        this.id = serviceOrderResponse.getId();
        this.number = serviceOrderResponse.getNumber();
        this.status = serviceOrderResponse.getStatus();

        List<ServiceOrderPolygon> polygons = new ArrayList<>(serviceOrderResponse.getPolygons().size());
        for (ServiceOrderPolygonResponse resp : serviceOrderResponse.getPolygons()) {
            ServiceOrderPolygon serviceOrderPolygon = new ServiceOrderPolygon(resp);
            if (!polygons.contains(serviceOrderPolygon)) {
                polygons.add(serviceOrderPolygon);
                points.add(new LatLng(serviceOrderPolygon.getLat(), serviceOrderPolygon.getLon()));
            }
        }

        this.polygons = polygons;
    }

    public long getTotalPostes() {
        return totalPostes;
    }

    public void setTotalPostes(long totalPostes) {
        this.totalPostes = totalPostes;
    }

    public long getTotalDemandas() {
        return totalDemandas;
    }

    public void setTotalDemandas(long totalDemandas) {
        this.totalDemandas = totalDemandas;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public List<ServiceOrderPolygon> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<ServiceOrderPolygon> polygons) {
        this.polygons = polygons;
    }

    public List<LatLng> getPoints() {
        return points;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
