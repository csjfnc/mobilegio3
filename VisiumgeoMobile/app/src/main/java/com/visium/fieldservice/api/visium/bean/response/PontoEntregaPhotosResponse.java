package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.PontoEntregaPhotos;
import com.visium.fieldservice.entity.PostPhotos;

import java.io.Serializable;

public class PontoEntregaPhotosResponse implements Serializable {

    @SerializedName("NumeroFoto")
    private String number;

    @SerializedName("DataFoto")
    private String date;

    public PontoEntregaPhotosResponse() {}

    public PontoEntregaPhotosResponse(String number, String date) {
        this.number = number;
        this.date = date;
    }

    public PontoEntregaPhotosResponse(PontoEntregaPhotos pontoEntregaPhotos) {
        this.number = pontoEntregaPhotos.getNumber();
        this.date = pontoEntregaPhotos.getDate();
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
