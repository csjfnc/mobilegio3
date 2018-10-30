package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public enum Ocorrencia {

    SEM_INFORMACAO("SEM INFORMACAO"),
    CACHORRO_BRAVO("CACHORRO BRAVO"),
    CASA_FECHADA("CASA FECHADA"),
    CASA_VAZIA("CASA VAZIA"),
    DIFICIL_ACESSO("DIFICIL_ACESSO");

    private String name;

    Ocorrencia(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Ocorrencia parse(String name) {
        for (Ocorrencia type : Ocorrencia.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (Ocorrencia type : Ocorrencia.values()) {
            names.add(type.getName());
        }
        return names;
    }

}