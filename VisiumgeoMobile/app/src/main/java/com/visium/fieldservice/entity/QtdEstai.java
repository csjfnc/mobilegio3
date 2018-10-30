package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public enum QtdEstai {

    _0("0"),
    _1("1"),
    _2("2"),
    _3("3"),
    _4("4"),
    _5("5"),
    _6("6"),
    _7("7"),
    _8("8");

    private String name;

    QtdEstai(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static QtdEstai parse(String name) {
        for (QtdEstai type : QtdEstai.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return _0;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (QtdEstai type : QtdEstai.values()) {
            names.add(type.getName());
        }
        return names;
    }

}