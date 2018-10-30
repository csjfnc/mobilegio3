package com.visium.fieldservice.entity.equipament;

import com.visium.fieldservice.api.visium.bean.response.AfloramentoResponse;

import java.io.Serializable;

public class Afloramento implements Serializable {
    private long id;
    private long postId;
    private int status;
    private int proprietario;

    private double latAtualizacao, lonAtualizacao;

    public Afloramento(){}

    public Afloramento(AfloramentoResponse response) {
        this.id = response.getId();
        this.postId = response.getPostId();
        this.status = response.getStatus();
        this.proprietario = response.getProprietario();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Afloramento
                && ((Afloramento) o).getId() == this.id;
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

    public double getLatAtualizacao() {
        return latAtualizacao;
    }

    public void setLatAtualizacao(double latAtualizacao) {
        this.latAtualizacao = latAtualizacao;
    }

    public double getLonAtualizacao() {
        return lonAtualizacao;
    }

    public void setLonAtualizacao(double lonAtualizacao) {
        this.lonAtualizacao = lonAtualizacao;
    }
}
