package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.LatLonQuadra;

import java.io.Serializable;

/**
 * Created by fjesus on 28/02/2018.
 */

public class LatLonQuadraResponse implements Serializable{

    @SerializedName("ID")
    private long id;
    @SerializedName("QuadraID")
    private long quadraId;
    @SerializedName("X")
    private double x;
    @SerializedName("Y")
    private double y;

    public LatLonQuadraResponse(LatLonQuadra latLonQuadra){
        this.id = latLonQuadra.getId();
        this.quadraId = latLonQuadra.getQuadraId();
        this.x = latLonQuadra.getX();
        this.y = latLonQuadra.getY();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuadraId() {
        return quadraId;
    }

    public void setQuadraId(long quadraId) {
        this.quadraId = quadraId;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
