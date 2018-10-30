package com.visium.fieldservice.api.visium.bean.request;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.api.visium.bean.response.MedidorResponse;
import com.visium.fieldservice.entity.Equipment;
import com.visium.fieldservice.entity.PontoEntrega;

import java.io.Serializable;
import java.util.List;

public class PontoEntregaRequest implements Serializable {

    @SerializedName("IdPontoEntrega")
    private long id;
    @SerializedName("IdPoste")
    private long postId;
    @SerializedName("Status")
    private int status;
    @SerializedName("ClasseAtendimento")
    private int classeAtendimento;
    @SerializedName("TipoConstrucao")
    private int tipoConstrucao;
    @SerializedName("Numero")
    private String numero;
    @SerializedName("ClasseSocial")
    private int classeSocial;
    @SerializedName("Logradouro")
    private String logradouro;
    @SerializedName("Medidor")
    private List<MedidorResponse> medidor;
    @SerializedName("Fase")
    private String fase;
    @SerializedName("ETLigacao")
    private String etLigacao;
    @SerializedName("Observacao")
    private String observacao;
    @SerializedName("Y")
    private double y;
    @SerializedName("X")
    private double x;

    public PontoEntregaRequest(PontoEntrega obj) {
        this.id = obj.getId();
        this.postId = obj.getPostId();
        this.status = obj.getStatus();
        this.x = obj.getX();
        this.y = obj.getY();
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getClasseAtendimento() {
        return classeAtendimento;
    }

    public void setClasseAtendimento(int classeAtendimento) {
        this.classeAtendimento = classeAtendimento;
    }

    public int getTipoConstrucao() {
        return tipoConstrucao;
    }

    public void setTipoConstrucao(int tipoConstrucao) {
        this.tipoConstrucao = tipoConstrucao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public int getClasseSocial() {
        return classeSocial;
    }

    public void setClasseSocial(int classeSocial) {
        this.classeSocial = classeSocial;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public List<MedidorResponse> getMedidor() {
        return medidor;
    }

    public void setMedidor(List<MedidorResponse> medidor) {
        this.medidor = medidor;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public String getEtLigacao() {
        return etLigacao;
    }

    public void setEtLigacao(String etLigacao) {
        this.etLigacao = etLigacao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}