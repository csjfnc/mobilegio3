package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public enum Fase {

    SEM_INFORMACAO("SEM INFORMACAO"),
    A("A"),
    B("B"),
    C("C"),
    AB("AB"),
    AC("AC"),
    BC("BC"),
    ABC("ABC");

    private String name;

    Fase(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Fase parse(String name) {
        for (Fase type : Fase.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (Fase type : Fase.values()) {
            names.add(type.getName());
        }
        return names;
    }

}