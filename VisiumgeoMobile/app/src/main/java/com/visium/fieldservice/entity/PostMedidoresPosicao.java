package com.visium.fieldservice.entity;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.api.visium.bean.response.PostPhotosResponse;

import java.io.Serializable;


public class PostMedidoresPosicao implements Serializable {

    @SerializedName("x_pe")
    private double x;

    @SerializedName("y_pe")
    private double y;


    public PostMedidoresPosicao() {}

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
