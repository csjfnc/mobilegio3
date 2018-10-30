package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EnumClassResponse implements Serializable {

    @SerializedName("Name")
    private String enumName;

    @SerializedName("Enums")
    private List<EnumItemResponse> listEnumItems;

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public List<EnumItemResponse> getListEnumItems() {
        return listEnumItems;
    }

    public void setListEnumItems(List<EnumItemResponse> listEnumItems) {
        this.listEnumItems = listEnumItems;
    }
}