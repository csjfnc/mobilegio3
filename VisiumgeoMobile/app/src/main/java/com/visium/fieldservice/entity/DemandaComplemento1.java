package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 12/05/2017.
 */

public enum DemandaComplemento1 {

    SN("SN"),
    EC("EC"),
    FD("FD"),
    CASA("CASA"),
    BLOCO("BLOCO"),
    SALA("SALA"),
    LOJA("LOJA"),
    F1("F1"),
    F2("F2"),
    F3("F3"),
    F4("F4"),
    F5("F5"),
    F6("F6"),
    F7("F7"),
    F8("F8"),
    F9("F9");

    private String complemento;

    DemandaComplemento1(String complemento) {
        this.complemento = complemento;
    }

    public String getComplemento() {
        return complemento;
    }

    public static DemandaComplemento1 parse(String status) {
        for (DemandaComplemento1 type : DemandaComplemento1.values()) {
            if (StringUtils.equals(type.getComplemento(), status)) {
                return type;
            }
        }
        return SN;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (DemandaComplemento1 type : DemandaComplemento1.values()) {
            names.add(type.getComplemento());
        }
        return names;
    }
}
