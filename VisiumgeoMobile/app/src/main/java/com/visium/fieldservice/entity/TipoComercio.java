package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 11/05/2018.
 */

public enum TipoComercio {

    OUTROS("OUTROS"),
    AG_AUTOMOVEIS("AG. AUTOMOVEIS"),
    BANCO("BANCO"),
    BANCA_JORNAL("BANCA JORNAL"),
    BAR("BAR"),
    BOMBEIRO("BOMBEIRO"),
    CASA_NOTURNA("CASA NOTURNA"),
    CLINICA("CLINICA"),
    CLUBE("CLUBE"),
    CRECHE("CRECHE"),
    ESC_PARTICULAR("ESC PARTICULAR"),
    ESC_PUBLICA("ESC PUBLICA"),
    ESCOLA("ESCOLA"),
    ESCRITORIO("ESCRITORIO"),
    ESTACIONAMENTO("ESTACIONAMENTO"),
    FLAT("FLAT"),
    GALERIA("GALERIA"),
    GINASIO_ESPORTE("GINASIO_ESPORTE"),
    HOSPITAL("HOSPITAL"),
    HOTEL("HOTEL"),
    IGREJA("IGREJA"),
    IMOBILIARIA("IMOBILIARIA"),
    INDUSTRIA("INDUSTRIA"),
    LANCHONETE("LANCHONETE"),
    MOTEL("MOTEL"),
    MEDIDOR_VAZIO("MEDIDOR VAZIO"),
    OFICINA("OFICINA"),
    ORGAO_PUBLICO("ORGAO PUBLICO"),
    PADARIA("PADARIA"),
    PENSAO("PENSAO"),
    POLICIA("POLICIA"),
    PONTO_TAXI("PONTO TAXI"),
    POSTO("POSTO"),
    POSTO_SAUDE("POSTO SAUDE"),
    PRACA("PRACA"),
    RESTAURANTE("RESTAURANTE"),
    SABESP("SABESP"),
    SHOPPING("SHOPPING"),
    SUPERMERCADO("SUPERMERCADO"),
    UNIVERSIDADE("UNIVERSIDADE");

    private String nome;

    TipoComercio(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static TipoComercio parse(String nome){
        for(TipoComercio comercio : TipoComercio.values()){
            if(StringUtils.equals(comercio.getNome(), nome)){
                return comercio;
            }
        }
        return OUTROS;
    }

    public static List<CharSequence> getNomes(){
        List<CharSequence> sequences = new ArrayList<>();
        for(TipoComercio comercio : TipoComercio.values()){
            sequences.add(comercio.getNome());
        }
        return sequences;
    }
}
