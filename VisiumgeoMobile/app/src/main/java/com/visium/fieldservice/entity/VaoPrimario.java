package com.visium.fieldservice.entity;

import com.visium.fieldservice.api.visium.bean.response.VaoPrimarioResponse;

import java.io.Serializable;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class VaoPrimario implements Serializable {

    private static final long serialVersionUID = 6721620434520908307L;

    private Double x1, x2, y1, y2;

    public VaoPrimario(){}

    public VaoPrimario(VaoPrimarioResponse resp){
        this.x1 = resp.getX1();
        this.x2 = resp.getX2();
        this.y1 = resp.getY1();
        this.y2 = resp.getY2();
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
