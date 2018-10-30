package com.visium.fieldservice.entity;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.api.visium.bean.response.PostLocationResponse;

import java.io.Serializable;

public class PontoAtualizacao implements Serializable {

    private static final long serialVersionUID = 6721620434520908307L;

    @SerializedName("Lat")
    private Double latitude;

    @SerializedName("Lon")
    private Double longitude;

    @SerializedName("DataAtualizacao")
    private long date;

    public PontoAtualizacao(){}


    public PontoAtualizacao(Double latitude, Double longitude, long date) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.date = date;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
