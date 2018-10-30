package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public enum LightingArmType {

    SEM_INFORMACAO("SEM INFORMACAO"),
    CURTO("CURTO"),
    MEDIO("MEDIO"),
    LONGO("LONGO"),
    FORA_DE_PADRAO("FORA DE PADRAO"),
    SEM_BRACO("SEM BRACO"),
    FAVELA("FAVELA"),
    ECONOMIA("ECONOMIA"),
    PETALAS_01("PETALAS 01"),
    PETALAS_02("PETALAS 02"),
    PETALAS_03("PETALAS 03"),
    PETALAS_04("PETALAS 04"),
    PETALAS_05("PETALAS 05"),
    PETALAS_06("PETALAS 06"),
    PETALAS_07("PETALAS 07"),
    PETALAS_08("PETALAS 08"),
    PETALAS_09("PETALAS 09"),
    PETALAS_10("PETALAS 10"),
    ESPECIAL("ESPECIAL");

    private String name;

    LightingArmType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static LightingArmType parse(String name) {
        for (LightingArmType type : LightingArmType.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (LightingArmType type : LightingArmType.values()) {
            names.add(type.getName());
        }
        return names;
    }

}