package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.Lagradouro;

import java.io.Serializable;

/**
 * Created by fjesus on 09/01/2018.
 */

public class LagradouroResponse implements Serializable {

    @SerializedName("IdLgradouro")
    private long id;
    @SerializedName("Chave")
    private long chaveLog;
    @SerializedName("Complemento")
    private String compleLog;
    @SerializedName("Abreviacao")
    private String abrevTipo;
    @SerializedName("Bairro")
    private String extensoBairro;
    @SerializedName("IdIp")
    private String cep;
    @SerializedName("Titulo")
    private String titulo;
    @SerializedName("Preposicao")
    private String preposicao;
    @SerializedName("Municipio")
    private String municipo;
    @SerializedName("Classificacao")
    private String classificacao;
    @SerializedName("Regional")
    private String regional;
    @SerializedName("NomeRua")
    private String nomeLog;
    @SerializedName("UF")
    private String uf;

    public LagradouroResponse(){

    }

    public LagradouroResponse(Lagradouro lagradouro){

        this.id = lagradouro.getId();
        this.chaveLog = lagradouro.getChaveLog();
        this.compleLog = lagradouro.getCompleLog();
        this.abrevTipo = lagradouro.getAbrevTipo();
        this.extensoBairro = lagradouro.getExtensoBairro();
        this.cep = lagradouro.getCep();
        this.titulo = lagradouro.getTitulo();
        this.preposicao = lagradouro.getPreposicao();
        this.municipo = lagradouro.getMunicipo();
        this.classificacao = lagradouro.getClassificacao();
        this.regional = lagradouro.getRegional();
        this.nomeLog = lagradouro.getNomeLog();
        this.uf = lagradouro.getUf();
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
