package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public enum LightingStatus {

    SIM("SIM"),
    NAO("NAO");

    private String name;

    LightingStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static LightingStatus parse(String name) {
        for (LightingStatus status : LightingStatus.values()) {
            if (StringUtils.equals(status.getName(), name)) {
                return status;
            }
        }
        return NAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (LightingStatus type : LightingStatus.values()) {
            names.add(type.getName());
        }
        return names;
    }
}
