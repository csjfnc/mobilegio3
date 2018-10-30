package com.visium.fieldservice.entity;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.api.visium.bean.response.AnotacaoResponse;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fjesus on 23/01/2018.
 */

public class Anotacao implements Serializable {

    private long id;
    private String anotacao;
    private double x;
    private double y;
    private long orderId;
    private boolean update;
    private boolean excluido;
    private Date dataExclusao;

    public Anotacao(){

    }

    public Anotacao(AnotacaoResponse anotacaoResponse){
        this.id = anotacaoResponse.getId();
        this.anotacao = anotacaoResponse.getAnotacao();
        this.x = anotacaoResponse.getX();
        this.y = anotacaoResponse.getY();
        this.orderId = anotacaoResponse.getOrderId();
        this.update = anotacaoResponse.isUpdate();
        this.excluido = anotacaoResponse.isExcluido();
        this.dataExclusao = anotacaoResponse.getDataExclusao();
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

    public Date getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(Date dataExclusao) {
        this.dataExclusao = dataExclusao;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAnotacao() {
        return anotacao;
    }

    public void setAnotacao(String anotacao) {
        this.anotacao = anotacao;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
