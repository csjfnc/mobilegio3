package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 12/05/2017.
 */

public enum DemandaTipoImovel {

    _0("0"),
    _18("18"),
    _46("46"),
    _48("48"),
    _66("66"),
    _72 ("72"),
    _73 ("73"),
    _74 ("74"),
    _76 ("76"),
    _77 ("77"),
    _78 ("78"),
    _79 ("79"),
    _80 ("80"),
    _81 ("81"),
    _83 ("83"),
    _85 ("85"),
    _87 ("87"),
    _88 ("88"),
    _89 ("89"),
    _90 ("90"),
    _91 ("91"),
    _92 ("92"),
    _93 ("93"),
    _94 ("94"),
    _95 ("95"),
    _96 ("96"),
    _97 ("97"),
    _117 ("117"),
    _118 ("118"),
    _119 ("119"),
    _120 ("120"),
    _121 ("121"),
    _122 ("122"),
    _123 ("123"),
    _126 ("126"),
    _127 ("127"),
    _128 ("128"),
    _129 ("129"),
    _130 ("130"),
    _131 ("131"),
    _132 ("132"),
    _135 ("135"),
    _136 ("136"),
    _138 ("138"),
    _140 ("140"),
    _141 ("141");

    private String tipo;

    DemandaTipoImovel(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public static DemandaTipoImovel parse(String status) {
        for (DemandaTipoImovel type : DemandaTipoImovel.values()) {
            if (StringUtils.equals(type.getTipo(), status)) {
                return type;
            }
        }
        return _0;
    }

    public static List<CharSequence> getNames() {
        List<CharSequence> names = new ArrayList<>();
        for (DemandaTipoImovel type : DemandaTipoImovel.values()) {
            names.add(type.getTipo());
        }
        return names;
    }
}
