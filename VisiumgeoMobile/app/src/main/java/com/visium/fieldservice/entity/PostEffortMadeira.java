package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public enum PostEffortMadeira {

    SEM_INFORMACAO("SEM"),
    LEV("LEV"),
    MED("MED"),
    PES("PES");
    private String name;


    PostEffortMadeira(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PostEffortMadeira parse(String name) {
        for (PostEffortMadeira type : PostEffortMadeira.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (PostEffortMadeira type : PostEffortMadeira.values()) {
            names.add(type.getName());
        }
        return names;
    }

    public static String[] getValuesArray() {
        PostEffortMadeira[] v = PostEffortMadeira.values();
        String[] values = new String[v.length];
        for(int i = 0; i < v.length; i++) {
            values[i] = v[i].getName();
        }
        return values;
    }

}