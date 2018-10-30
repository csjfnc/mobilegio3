package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class ServiceOrderResponse implements Serializable {

    private static final long serialVersionUID = -4391494410229866024L;

    @SerializedName("IdOrdemDeServico")
    private long id;

    @SerializedName("NumeroOS")
    private String number;

    @SerializedName("PoligonosOS")
    private List<ServiceOrderPolygonResponse> polygons;

    @SerializedName("Status")
    private int status;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<ServiceOrderPolygonResponse> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<ServiceOrderPolygonResponse> polygons) {
        this.polygons = polygons;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
