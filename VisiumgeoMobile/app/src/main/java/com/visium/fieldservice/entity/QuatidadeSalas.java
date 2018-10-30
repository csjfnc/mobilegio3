package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 14/05/2018.
 */

public enum QuatidadeSalas {

    _1("1"),
    _2("2"),
    _3("3"),
    _4("4"),
    _5("5"),
    _6("6"),
    _7("7"),
    _8("8"),
    _9("9"),
    _10("10"),
    _11("11"),
    _12("12"),
    _13("13"),
    _14("14"),
    _15("15"),
    _16("16"),
    _17("17"),
    _18("18"),
    _19("19"),
    _20("20"),
    _21("21"),
    _22("22"),
    _23("23"),
    _24("24"),
    _25("25"),
    _30("30"),
    _35("35"),
    _40("40"),
    _45("45"),
    _50("50"),
    _55("55"),
    _60("60"),
    _65("65"),
    _70("70"),
    _75("75"),
    _80("80"),
    _85("85"),
    _90("90"),
    _95("95"),
    _100("100"),
    _150("150"),
    _200("200"),
    _250("250"),
    _300("300"),
    _350("350"),
    _400("400"),
    _450("450"),
    _500("500");

    public String numero;

    QuatidadeSalas (String numero){
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public static QuatidadeSalas parse(String valor){
        for(QuatidadeSalas salas : QuatidadeSalas.values()){
            if(StringUtils.equals(salas.getNumero(), valor)){
                return salas;
            }
        }
        return _1;
    }

    public static List<CharSequence> getNumeros(){
        List<CharSequence> sequences = new ArrayList<>();
        for (QuatidadeSalas salas : QuatidadeSalas.values()){
            sequences.add(salas.getNumero());
        }
        return sequences;
    }
}
