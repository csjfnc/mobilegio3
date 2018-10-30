package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public enum Mufla {

    SEM_INFORMACAO("SEM INFORMAÇÃO"),
    SIM("SIM"),
    NAO("NÃO");

    private String name;

    Mufla(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Mufla parse(String name) {
        for (Mufla type : Mufla.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (Mufla type : Mufla.values()) {
            names.add(type.getName());
        }
        return names;
    }

}