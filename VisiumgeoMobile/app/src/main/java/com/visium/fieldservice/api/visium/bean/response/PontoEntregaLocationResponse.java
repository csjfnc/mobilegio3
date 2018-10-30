package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.PontoEntregaLocation;
import com.visium.fieldservice.entity.PostLocation;

import java.io.Serializable;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class PontoEntregaLocationResponse implements Serializable {

    private static final long serialVersionUID = 6721620434520908307L;

    @SerializedName("Lat")
    private Double lat;

    @SerializedName("Lon")
    private Double lon;

    public PontoEntregaLocationResponse() {}

    public PontoEntregaLocationResponse(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public PontoEntregaLocationResponse(PontoEntregaLocation entregaLocation) {
        this.lat = entregaLocation.getLat();
        this.lon = entregaLocation.getLon();
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }
}
