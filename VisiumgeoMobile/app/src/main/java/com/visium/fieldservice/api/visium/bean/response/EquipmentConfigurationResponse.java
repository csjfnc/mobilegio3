package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EquipmentConfigurationResponse implements Serializable {

    @SerializedName("Name")
    private String equipmentName;

    @SerializedName("Enums")
    private List<EnumClassResponse> listEnums;

    public List<EnumClassResponse> getListEnums() {
        return listEnums;
    }

    public void setListEnums(List<EnumClassResponse> listEnums) {
        this.listEnums = listEnums;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }
}

