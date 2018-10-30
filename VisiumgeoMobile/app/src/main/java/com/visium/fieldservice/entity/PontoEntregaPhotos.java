package com.visium.fieldservice.entity;

import com.visium.fieldservice.api.visium.bean.response.PontoEntregaPhotosResponse;
import com.visium.fieldservice.api.visium.bean.response.PostPhotosResponse;

import java.io.Serializable;


public class PontoEntregaPhotos implements Serializable {

    private String number;
    private String date;

    public PontoEntregaPhotos(){}

    public PontoEntregaPhotos(PontoEntregaPhotosResponse resp){
        this.number = resp.getNumber();
        this.date = resp.getDate();
    }

    public PontoEntregaPhotos(String number, String date) {
        this.number = number;
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
