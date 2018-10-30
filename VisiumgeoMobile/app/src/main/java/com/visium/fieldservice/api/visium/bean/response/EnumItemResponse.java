package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EnumItemResponse implements Serializable {

    @SerializedName("Key")
    private int key;

    @SerializedName("Value")
    private String value;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}