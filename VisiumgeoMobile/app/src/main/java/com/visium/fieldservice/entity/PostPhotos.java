package com.visium.fieldservice.entity;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.api.visium.bean.response.PostPhotosResponse;

import java.io.Serializable;


public class PostPhotos implements Serializable {

    private String number;
    private String date;

    public PostPhotos(){}

    public PostPhotos(PostPhotosResponse resp){
        this.number = resp.getNumber();
        this.date = resp.getDate();
    }

    public PostPhotos(String number, String date) {
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
