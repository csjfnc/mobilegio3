package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public enum LightingLampType {

    VAPOR_SODIO("VAPOR SODIO"),
    VAPOR_METALICO("VAPOR METALICO"),
    INCANDESCENTE("INCANDESCENTE"),
    VAPOR_MERCURIO("VAPOR MERCURIO"),
    VAPOR_MISTO("VAPOR MISTO"),
    FLUORESCENTE("FLUORESCENTE"),
    LED("LED"),
    SEM_IP("SEM IP"),
    SEM_INFORMACAO("SEM INFORMACAO");

    private String name;

    LightingLampType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static LightingLampType parse(String name) {
        for (LightingLampType type : LightingLampType.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (LightingLampType type : LightingLampType.values()) {
            names.add(type.getName());
        }
        return names;
    }
}
