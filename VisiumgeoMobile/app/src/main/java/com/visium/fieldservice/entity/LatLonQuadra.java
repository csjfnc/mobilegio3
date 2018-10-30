package com.visium.fieldservice.entity;

import com.visium.fieldservice.api.visium.bean.response.LatLonQuadraResponse;

import java.io.Serializable;

/**
 * Created by fjesus on 28/02/2018.
 */

public class LatLonQuadra implements Serializable {

    private long id;
    private long quadraId;
    private double x;
    private double y;


    public LatLonQuadra(LatLonQuadraResponse  response){
        this.id = response.getId();
        this.quadraId = response.getQuadraId();
        this.x = response.getX();
        this.y = response.getY();
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
