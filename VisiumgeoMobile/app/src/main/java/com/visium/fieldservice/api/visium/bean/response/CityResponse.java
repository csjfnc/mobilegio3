package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class CityResponse implements Serializable {

    private static final long serialVersionUID = 4735035703876826962L;

    @SerializedName("IdCidade")
    private int id;

    @SerializedName("Nome")
    private String name;

    public CityResponse() {}

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}