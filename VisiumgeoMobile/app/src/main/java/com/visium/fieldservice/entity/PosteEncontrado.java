package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public enum PosteEncontrado {

    SIM("SIM"),
    NAO("NÃO"),
    SEM_ACESSO("SEM ACESSO"),
    AREA_PARTICULAR("AREA PARTICULAR"),
    OUTROS("OUTROS");

    private String name;

    PosteEncontrado(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PosteEncontrado parse(String name) {
        for (PosteEncontrado type : PosteEncontrado.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SIM;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (PosteEncontrado type : PosteEncontrado.values()) {
            names.add(type.getName());
        }
        return names;
    }

    public static String getUnico(int valor){
        String encontrado = "";
        int cont = 0;
        for(PosteEncontrado posteEncontrado : PosteEncontrado.values()){

            if(cont == valor){
                encontrado = posteEncontrado.getName();
            }
        }
        return  encontrado;
    }

}