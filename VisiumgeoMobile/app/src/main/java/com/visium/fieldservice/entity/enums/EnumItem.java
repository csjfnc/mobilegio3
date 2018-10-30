package com.visium.fieldservice.entity.enums;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.api.visium.bean.response.EnumClassResponse;
import com.visium.fieldservice.api.visium.bean.response.EnumItemResponse;

public class EnumItem {

    @SerializedName("Key")
    private int key;

    @SerializedName("Value")
    private String value;

    public EnumItem() {}

    public EnumItem(int key, String value) {
        this.key = key;
        this.value = value;
    }

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
