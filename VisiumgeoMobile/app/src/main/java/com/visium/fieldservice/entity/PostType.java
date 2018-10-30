package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;


public enum PostType {

    SEM_INFORMACAO("SEM INFO..."),
    CIRCULAR("CIRCULAR"),
    DUPLOT("DUPLOT"),
    MADEIRA("MADEIRA"),
    FERRO("FERRO"),
    TELEFONICO("TELEFONICO"),
    IP("IP"),
    DUPLO("DUPLO"),
    FLUTAP("FLUTAP"),
    CONTRAPOSTE("CONTRAPOSTE"),
    FOTO("FOTO"),
    FIBRA("FIBRA"),
    TUBULAR("TUBULAR"),
    QUADRADO("QUADRADO"),
    ARVORE_P("ÁRVORE-P"),
    ARVORE_M("ÁRVORE-M"),
    ARVORE_G("ÁRVORE-G"),
    PM_METALICO("PM-METÁLICO"),
    PM_CONCRETO("PM-CONCRETO"),
    PM_DUPLO("PM-DUPLO"),
    PM_MADEIRA("PM-MADEIRA"),
    INVASAO_RISCO_01 ("INVASAO RISCO 01"),
    INVASAO_RISCO_02 ("INVASAO RISCO 02");

    private String name;

    PostType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static PostType parse(String name) {
        for (PostType type : PostType.values()) {
            if (StringUtils.equals(type.getName(), name)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (PostType type : PostType.values()) {
            names.add(type.getName());
        }
        return names;
    }

}