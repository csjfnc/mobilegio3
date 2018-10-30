package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public enum ParaRaio {

    SEM_INFORMACAO("SEM INFORMACAO"),
    SIM("SIM"),
    NAO("NÃO");

    private String name;

    ParaRaio(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static ParaRaio parse(String name) {
        for (ParaRaio type : ParaRaio.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (ParaRaio type : ParaRaio.values()) {
            names.add(type.getName());
        }
        return names;
    }

}