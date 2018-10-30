package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 12/05/2017.
 */

public enum PontoEntregaClasseSocial {

    SEM_INFORMACAO("SEM INFORMACAO"),
    RESIDENCIAL("RESIDENCIAL"),
    COMERCIAL("COMERCIAL"),
    EDIFICIO("EDIFÍCIO");

    private String classeSocial;

    PontoEntregaClasseSocial(String classeSocial) {
        this.classeSocial = classeSocial;
    }

    public String getClasseSocial() {
        return classeSocial;
    }

    public static PontoEntregaClasseSocial parse(String classeSocial) {
        for (PontoEntregaClasseSocial type : PontoEntregaClasseSocial.values()) {
            if (StringUtils.equals(type.getClasseSocial(), classeSocial)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getClasseSocials() {
        List<CharSequence> classeSocial = new ArrayList<>();
        for (PontoEntregaClasseSocial type : PontoEntregaClasseSocial.values()) {
            classeSocial.add(type.getClasseSocial());
        }
        return classeSocial;
    }
}