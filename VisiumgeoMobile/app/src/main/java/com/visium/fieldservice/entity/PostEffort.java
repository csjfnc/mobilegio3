package com.visium.fieldservice.entity;

import com.visium.fieldservice.base.MyEnum;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public enum PostEffort {

    SEM_INFORMACAO("SEM"),
    _200("200"),
    _300("300"),
    _400("400"),
    _450("450"),
    _500("500"),
    _600("600"),
    _700("700"),
    _800("800"),
    _900("900"),
    _1000("1000"),
    _1100("1200"),
    _1200("1500"),
    _1300("1600");
    private String name;


    PostEffort(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PostEffort parse(String name) {
        for (PostEffort type : PostEffort.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (PostEffort type : PostEffort.values()) {
            names.add(type.getName());
        }
        return names;
    }

    public static String[] getValuesArray() {
        PostEffort[] v = PostEffort.values();
        String[] values = new String[v.length];
        for(int i = 0; i < v.length; i++) {
            values[i] = v[i].getName();
        }
        return values;
    }

}