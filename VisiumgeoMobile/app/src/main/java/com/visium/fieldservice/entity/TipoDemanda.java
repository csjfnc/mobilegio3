package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 12/05/2017.
 */

public enum TipoDemanda {

    RESIDENCIAL("RESIDENCIAL"),
    COMERCIAL_P("COMERCIAL P"),
    COMERCIAL_M("COMERCIAL M"),
    COMERCIAL_G("COMERCIAL G"),
    VILA("VILA"),
    COMERCIO_RESIDENCIA("COMERCIO/RESIDENCIA"),
    CONSTRUCAO("CONSTRUCAO"),
    TERRENO("TERRENO"),
    EDIFÍCIO_RES("EDIFÍCIO RES"),
    EDIFICIO_COM("EDIFÍCIO COM"),
    EDIFICIO_EMPRESA("EDIFÍCIO EMPRESA"),
    EDIFÍCIO_CONSTRUCAO("EDIFÍCIO CONSTRUCAO");


    private String tipo;

    TipoDemanda(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public static TipoDemanda parse(String status) {
        for (TipoDemanda type : TipoDemanda.values()) {
            if (StringUtils.equals(type.getTipo(), status)) {
                return type;
            }
        }
        return RESIDENCIAL;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (TipoDemanda type : TipoDemanda.values()) {
            names.add(type.getTipo());
        }
        return names;
    }
}
