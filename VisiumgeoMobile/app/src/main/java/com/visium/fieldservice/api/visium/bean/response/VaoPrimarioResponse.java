package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.VaoPrimario;

import java.io.Serializable;


public class VaoPrimarioResponse implements Serializable {

    private static final long serialVersionUID = 6721620434520908307L;

    @SerializedName("x1")
    private Double x1;

    @SerializedName("x2")
    private Double x2;

    @SerializedName("y1")
    private Double y1;

    @SerializedName("y2")
    private Double y2;

    public VaoPrimarioResponse() {}

    public VaoPrimarioResponse(VaoPrimario vaoPrimario) {
        this.x1 = vaoPrimario.getX1();
        this.x2 = vaoPrimario.getX2();
        this.y1 = vaoPrimario.getY1();
        this.y2 = vaoPrimario.getY2();
    }

    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }

    public Double getX2() {
        return x2;
    }

    public void setX2(Double x2) {
        this.x2 = x2;
    }

    public Double getY1() {
        return y1;
    }

    public void setY1(Double y1) {
        this.y1 = y1;
    }

    public Double getY2() {
        return y2;
    }

    public void setY2(Double y2) {
        this.y2 = y2;
    }
}