package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 12/05/2017.
 */

public enum PontoEntregaClasseAtendimento {

    SEM_INFORMACAO("SEM INFORMACAO"),
    NORMAL("NORMAL"),
    MEDIA("MEDIA");

    private String classeAtendimento;

    PontoEntregaClasseAtendimento(String classeAtendimento) {
        this.classeAtendimento = classeAtendimento;
    }

    public String getClasseAtendimento() {
        return classeAtendimento;
    }

    public static PontoEntregaClasseAtendimento parse(String classeAtendimento) {
        for (PontoEntregaClasseAtendimento type : PontoEntregaClasseAtendimento.values()) {
            if (StringUtils.equals(type.getClasseAtendimento(), classeAtendimento)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getClasseAtendimentos() {
        List<CharSequence> classeAtendimentos = new ArrayList<>();
        for (PontoEntregaClasseAtendimento type : PontoEntregaClasseAtendimento.values()) {
            classeAtendimentos.add(type.getClasseAtendimento());
        }
        return classeAtendimentos;
    }
}
