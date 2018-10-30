package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public enum Defeito {

    SEM_INFORMACAO("SEM INFORMAÇÃO"),
    SEM_AVARIA("SEM AVARIA"),
    VEGETAÇÃO("VEGETAÇÃO"),
    PODRE("PODRE"),
    QUEIMADO("QUEIMADO"),
    FERRAGEM_EXPOSTA("FERRAGEM EXPOSTA"),
    FORA_DE_PRUMO("FORA DE PRUMO"),
    CRUZADA_QUEIMADA("CRUZADA QUEIMADA"),
    CRUZADA_PODRE("CRUZADA PODRE"),
    PARA_RAIO_ESTOURADO("PARA RAIO ESTOURADO");

    private String name;

    Defeito(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Defeito parse(String name) {
        for (Defeito type : Defeito.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (Defeito type : Defeito.values()) {
            names.add(type.getName());
        }
        return names;
    }
}