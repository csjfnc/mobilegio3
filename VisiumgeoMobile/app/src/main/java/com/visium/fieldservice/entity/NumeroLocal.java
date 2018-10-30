package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 22/01/2018.
 */

public enum NumeroLocal {

    _0("0"),
    _01("1"),
    _02("2"),
    _03("3"),
    _04("4"),
    _05("5"),
    _06("6"),
    _07("7"),
    _08("8"),
    _09("9"),
    _10("APAGAR"),
    _11("OK");

    private String numero;

    NumeroLocal(String numero){
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public static NumeroLocal parse(String status) {
        for (NumeroLocal type : NumeroLocal.values()) {
            if (StringUtils.equals(type.getNumero(), status)) {
                return type;
            }
        }
        return _0;
    }

    public List<CharSequence> getNumerosLocais(){
        List<CharSequence> sequences = new ArrayList<>();
        for(NumeroLocal local : NumeroLocal.values()){
            sequences.add(local.getNumero());
        }
        return sequences;
    }
}
