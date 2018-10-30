package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 12/05/2017.
 */

public enum PontoEntregaTipodeConstrução {

    SEM_INFORMACAO("SEM INFORMACAO"),
    AEREO("AÉREO"),
    SUBTERRANEO("SUBTARRÂNEO");

    private String tipodeConstrução;

    PontoEntregaTipodeConstrução(String tipodeConstrução) {
        this.tipodeConstrução = tipodeConstrução;
    }

    public String getTipodeConstrução() {
        return tipodeConstrução;
    }

    public static PontoEntregaTipodeConstrução parse(String tipodeConstrução) {
        for (PontoEntregaTipodeConstrução type : PontoEntregaTipodeConstrução.values()) {
            if (StringUtils.equals(type.getTipodeConstrução(), tipodeConstrução)) {
                return type;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getTipodeConstruçãos() {
        List<CharSequence> tipodeConstrução = new ArrayList<>();
        for (PontoEntregaTipodeConstrução type : PontoEntregaTipodeConstrução.values()) {
            tipodeConstrução.add(type.getTipodeConstrução());
        }
        return tipodeConstrução;
    }
}
