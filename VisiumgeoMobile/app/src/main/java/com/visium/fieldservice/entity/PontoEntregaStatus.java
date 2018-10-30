package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 12/05/2017.
 */

public enum PontoEntregaStatus {

    SEM_INFORMACAO("SEM INFORMACAO"),
    EXISTENTE("EXISTENTE");

    private String status;

    PontoEntregaStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static PontoEntregaStatus parse(String status) {
        for (PontoEntregaStatus type : PontoEntregaStatus.values()) {
            if (StringUtils.equals(type.getStatus(), status)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (PontoEntregaStatus type : PontoEntregaStatus.values()) {
            names.add(type.getStatus());
        }
        return names;
    }
}
