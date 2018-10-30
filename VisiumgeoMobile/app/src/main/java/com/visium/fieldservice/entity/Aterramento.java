package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public enum Aterramento {

    SEM_INFORMACAO("SEM INFORMACAO"),
    CIRCULAR("SIM"),
    NAO("NÃO"),
    ELETRICO("ELETRICO"),
    TV("TV"),
    TELECOM("TELECOM"),
    DUPLO("DUPLO");

    private String name;

    Aterramento(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Aterramento parse(String name) {
        for (Aterramento type : Aterramento.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (Aterramento type : Aterramento.values()) {
            names.add(type.getName());
        }
        return names;
    }

}