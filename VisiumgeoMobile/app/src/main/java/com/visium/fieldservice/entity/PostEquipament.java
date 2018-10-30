package com.visium.fieldservice.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 07/05/2018.
 */

public enum  PostEquipament {

    SEM_INFORMACAO("SEM"),
    TRAFO("TRAFO"),
    BANCO_CAPACITOR("BANCO CAPACITOR"),
    CHAVE_FACA("CHAVE FACA"),
    CHAVE_VISIVEL("CHAVE FUSIVEL"),
    ENTRADA_PRIMARIA("ENTRADA PRIMARIA"),
    CHAVE_OLEO("CHAVE OLEO"),
    SECCIONALIZADOR("SECCIONALIZADOR"),
    RELIGADORA("RELIGADORA"),
    REGULADORA("REGULADORA");

    private String nome;



    PostEquipament(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public static PostEquipament parse(String nome){
        for(PostEquipament post : PostEquipament.values()){
            if(StringUtils.equals(post.getNome(), nome)){
                return post;
            }
        }
        return SEM_INFORMACAO;
    }

    public static List<CharSequence> getNomes(){
        List<CharSequence> sequences = new ArrayList<>();
        for (PostEquipament equipament: PostEquipament.values()){
            sequences.add(equipament.getNome());
        }
        return sequences;
    }
}
