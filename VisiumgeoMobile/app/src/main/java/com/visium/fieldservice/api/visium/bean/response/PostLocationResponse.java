package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.PostLocation;

import java.io.Serializable;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class PostLocationResponse implements Serializable {

    private static final long serialVersionUID = 6721620434520908307L;

    @SerializedName("Lat")
    private Double lat;

    @SerializedName("Lon")
    private Double lon;

    public PostLocationResponse() {}

    public PostLocationResponse(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public PostLocationResponse(PostLocation postLocation) {
        this.lat = postLocation.getLat();
        this.lon = postLocation.getLon();
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
