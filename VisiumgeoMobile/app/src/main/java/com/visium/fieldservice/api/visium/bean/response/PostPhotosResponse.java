package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.PostLocation;
import com.visium.fieldservice.entity.PostPhotos;

import java.io.Serializable;

public class PostPhotosResponse implements Serializable {

    @SerializedName("NumeroFoto")
    private String number;

    @SerializedName("DataFoto")
    private String date;

    public PostPhotosResponse() {}

    public PostPhotosResponse(String number, String date) {
        this.number = number;
        this.date = date;
    }

    public PostPhotosResponse(PostPhotos postPhoto) {
        this.number = postPhoto.getNumber();
        this.date = postPhoto.getDate();
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
