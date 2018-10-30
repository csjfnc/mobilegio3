package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public enum LightingTrigger {

    INDIVIDUAL("INDIVIDUAL"),
    GRUPO("GRUPO"),
    SEM_INFORMACAO("SEM INFORMACAO");

    private String name;

    LightingTrigger(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static LightingTrigger parse(String name) {
        for (LightingTrigger trigger : LightingTrigger.values()) {
            if (StringUtils.equals(trigger.getName(), name)) {
                return trigger;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (LightingTrigger type : LightingTrigger.values()) {
            names.add(type.getName());
        }
        return names;
    }
}
