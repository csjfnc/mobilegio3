package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 12/05/2017.
 */

public enum DemandaComplemento2 {

    A("A"),
    B("B"),
    C("C"),
    D("D");

    private String complemento;

    DemandaComplemento2(String complemento) {
        this.complemento = complemento;
    }

    public String getComplemento() {
        return complemento;
    }

    public static DemandaComplemento2 parse(String status) {
        for (DemandaComplemento2 type : DemandaComplemento2.values()) {
            if (StringUtils.equals(type.getComplemento(), status)) {
                return type;
            }
        }
        return A;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (DemandaComplemento2 type : DemandaComplemento2.values()) {
            names.add(type.getComplemento());
        }
        return names;
    }
}
