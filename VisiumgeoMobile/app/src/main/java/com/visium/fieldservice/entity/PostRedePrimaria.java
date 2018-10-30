package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public enum PostRedePrimaria {

    SEM_INFORMACAO("SEM_INFORMACAO"),
    FIM_DE_LICINHA_SEC("FIM DE LINHA SEC."),
    SOMENTE_PRIMARIA("SOMENTE PRIMARIA"),
    FIM_STRAND_SEC("FIM STRAND SEC"),
    FIM_STRAND("FIM STRAND");

    private String name;


    PostRedePrimaria(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PostRedePrimaria parse(String name) {
        for (PostRedePrimaria type : PostRedePrimaria.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (PostRedePrimaria type : PostRedePrimaria.values()) {
            names.add(type.getName());
        }
        return names;
    }

    public static String[] getValuesArray() {
        PostRedePrimaria[] v = PostRedePrimaria.values();
        String[] values = new String[v.length];
        for(int i = 0; i < v.length; i++) {
            values[i] = v[i].getName();
        }
        return values;
    }

}