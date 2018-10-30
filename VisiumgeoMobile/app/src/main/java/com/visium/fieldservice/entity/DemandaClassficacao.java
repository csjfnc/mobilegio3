package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 12/05/2017.
 */

public enum DemandaClassficacao {

    A("A"),
    B("B"),
    C("C"),
    D("D"),
    E("E");

    private String classificacao;

    DemandaClassficacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public static DemandaClassficacao parse(String classificacao) {
        for (DemandaClassficacao type : DemandaClassficacao.values()) {
            if (StringUtils.equals(type.getClassificacao(), classificacao)) {
                return type;
            }
        }
        return A;
    }

    public static List<CharSequence> getClassificacaos() {
        List<CharSequence> classificacao = new ArrayList<>();
        for (DemandaClassficacao type : DemandaClassficacao.values()) {
            classificacao.add(type.getClassificacao());
        }
        return classificacao;
    }
}
