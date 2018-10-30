package com.visium.fieldservice.api.visium.bean.request;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.equipament.Afloramento;

import java.io.Serializable;

public class AfloramentoRequest implements Serializable {

    @SerializedName("IdAfloramento")
    private long id;

    @SerializedName("IdPoste")
    private long postId;

    @SerializedName("Status")
    private int status;

    @SerializedName("Proprietario")
    private int proprietario;

    @SerializedName("LatAtualizacao")
    private Double latAtualizacao;

    @SerializedName("LonAtualizacao")
    private Double lonAtualizacao;

    public AfloramentoRequest(Afloramento obj) {

        this.id = obj.getId();
        this.postId = obj.getPostId();
        this.status = obj.getStatus();
        this.proprietario = obj.getProprietario();

        this.latAtualizacao = obj.getLatAtualizacao();
        this.lonAtualizacao = obj.getLonAtualizacao();
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

    public int getProprietario() {
        return proprietario;
    }

    public void setProprietario(int proprietario) {
        this.proprietario = proprietario;
    }

    public Double getLatAtualizacao() {
        return latAtualizacao;
    }

    public void setLatAtualizacao(Double latAtualizacao) {
        this.latAtualizacao = latAtualizacao;
    }

    public Double getLonAtualizacao() {
        return lonAtualizacao;
    }

    public void setLonAtualizacao(Double lonAtualizacao) {
        this.lonAtualizacao = lonAtualizacao;
    }
}