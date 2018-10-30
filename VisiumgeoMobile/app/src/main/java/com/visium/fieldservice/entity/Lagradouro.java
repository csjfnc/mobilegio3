package com.visium.fieldservice.entity;

import com.visium.fieldservice.api.visium.bean.response.LagradouroResponse;

import java.io.Serializable;

/**
 * Created by fjesus on 09/01/2018.
 */

public class Lagradouro implements Serializable {

    private long id;
    private long chaveLog;
    private String nomeLog;
    private String compleLog;
    private String abrevTipo;
    private String extensoBairro;
    private String cep;
    private String titulo;
    private String preposicao;
    private String municipo;
    private String classificacao;
    private String regional;
    private String uf;

    public Lagradouro(){

    }

    public Lagradouro(LagradouroResponse response){
        this.id = response.getId();
        this.chaveLog = response.getChaveLog();
        this.compleLog = response.getCompleLog();
        this.abrevTipo = response.getAbrevTipo();
        this.extensoBairro = response.getExtensoBairro();
        this.cep = response.getCep();
        this.titulo = response.getTitulo();
        this.preposicao = response.getPreposicao();
        this.municipo = response.getMunicipo();
        this.classificacao = response.getClassificacao();
        this.regional = response.getRegional();
        this.nomeLog = response.getNomeLog();
        this.uf = response.getUf();
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getNomeLog() {
        return nomeLog;
    }

    public void setNomeLog(String nomeLog) {
        this.nomeLog = nomeLog;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChaveLog() {
        return chaveLog;
    }

    public void setChaveLog(long chaveLog) {
        this.chaveLog = chaveLog;
    }


    public String getCompleLog() {
        return compleLog;
    }

    public void setCompleLog(String compleLog) {
        this.compleLog = compleLog;
    }

    public String getAbrevTipo() {
        return abrevTipo;
    }

    public void setAbrevTipo(String abrevTipo) {
        this.abrevTipo = abrevTipo;
    }

    public String getExtensoBairro() {
        return extensoBairro;
    }

    public void setExtensoBairro(String extensoBairro) {
        this.extensoBairro = extensoBairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getPreposicao() {
        return preposicao;
    }

    public void setPreposicao(String preposicao) {
        this.preposicao = preposicao;
    }

    public String getMunicipo() {
        return municipo;
    }

    public void setMunicipo(String municipo) {
        this.municipo = municipo;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public String getRegional() {
        return regional;
    }

    public void setRegional(String regional) {
        this.regional = regional;
    }
}
