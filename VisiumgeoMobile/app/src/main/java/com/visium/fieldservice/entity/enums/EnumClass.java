package com.visium.fieldservice.entity.enums;


import com.visium.fieldservice.api.visium.bean.response.EnumClassResponse;
import com.visium.fieldservice.api.visium.bean.response.EnumItemResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class EnumClass implements Serializable {
    private String enumName;
    private List<EnumItem> enums;

    public EnumClass(){}

    public EnumClass(EnumClassResponse response) {
        enumName = response.getEnumName();
        if(response.getListEnumItems()!=null) {
            enums = new ArrayList<>(response.getListEnumItems().size());
            for(EnumItemResponse r : response.getListEnumItems()) {
                enums.add(new EnumItem( r.getKey(), r.getValue()));
            }
        }
    }

    public String getEnumName() {
        return enumName;
    }

    public void setEnumName(String enumName) {
        this.enumName = enumName;
    }

    public List<EnumItem> getEnums() {
        return enums;
    }

    public void setEnums(List<EnumItem> enums) {
        this.enums = enums;
    }

    public String getValueByKey(int key) {
        String value = "";
        for(EnumItem se : enums) {
            if(se.getKey() == key) {
                value = se.getValue();
            }
        }
        return value;
    }


}
