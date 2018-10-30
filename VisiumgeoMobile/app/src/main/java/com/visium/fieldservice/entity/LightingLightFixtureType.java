package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public enum LightingLightFixtureType {

    ABERTA("ABERTA"),
    FECHADA("FECHADA"),
    GRADE("GRADE"),
    REFLETOR("REFLETOR"),
    PRATO("PRATO"),
    INTEGRADA("INTEGRADA"),
    GLOBO("GLOBO"),
    DECORATIVA("DECORATIVA"),
    ORNAMENTAL("ORNAMENTAL"),
    OUTROS("OUTROS"),
    SEM_INFORMACAO("SEM INFORMACAO");

    private String name;

    LightingLightFixtureType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static LightingLightFixtureType parse(String name) {
        for (LightingLightFixtureType type : LightingLightFixtureType.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (LightingLightFixtureType type : LightingLightFixtureType.values()) {
            names.add(type.getName());
        }
        return names;
    }
}
