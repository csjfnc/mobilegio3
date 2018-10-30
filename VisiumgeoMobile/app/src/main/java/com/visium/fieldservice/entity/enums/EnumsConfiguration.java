package com.visium.fieldservice.entity.enums;


import com.visium.fieldservice.api.visium.bean.response.EnumClassResponse;
import com.visium.fieldservice.api.visium.bean.response.EnumItemResponse;
import com.visium.fieldservice.api.visium.bean.response.EquipmentConfigurationResponse;
import com.visium.fieldservice.util.LogUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

public class EnumsConfiguration implements Serializable {
    private String name;
    private HashMap<String, EnumClass> enumMap;

    public EnumsConfiguration(){}

    public EnumsConfiguration(EquipmentConfigurationResponse response) {
        name = response.getEquipmentName();
        List<EnumClassResponse> listEnums = response.getListEnums();
        if(listEnums!=null) {
            enumMap = new HashMap<>(listEnums.size());
            for(EnumClassResponse r : listEnums) {
                EnumClass ec = new EnumClass(r);
                LogUtils.log("enum name: " + ec.getEnumName());
                enumMap.put(name, ec);
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, EnumClass> getEnumMap() {
        return enumMap;
    }

    public void setEnumMap(HashMap<String, EnumClass> enumMap) {
        this.enumMap = enumMap;
    }
}
