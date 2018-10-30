package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public enum QtdLuminarias {

    SEM_INFORMACAO("SEM INFORMACAO"),
    _1("01"),
    _2("02"),
    _3("03"),
    _4("04"),
    _5("05"),
    _6("06"),
    _7("07"),
    _8("08"),
    _9("09"),
    _10("10"),
    _11("11"),
    _12("12");

    private String name;

    QtdLuminarias(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static QtdLuminarias parse(String name) {
        for (QtdLuminarias type : QtdLuminarias.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (QtdLuminarias type : QtdLuminarias.values()) {
            names.add(type.getName());
        }
        return names;
    }

}