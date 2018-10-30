package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.Anotacao;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fjesus on 23/01/2018.
 */

public class AnotacaoResponse implements Serializable {

    @SerializedName("IdAnotacao")
    private long id;
    @SerializedName("Descricao")
    private String anotacao;
    @SerializedName("X")
    private double x;
    @SerializedName("Y")
    private double y;
    @SerializedName("IdOrdemServico")
    private long orderId;
    @SerializedName("Update")
    private boolean update;
    @SerializedName("Excluido")
    private boolean excluido;
    @SerializedName("DataExclusao")
    private Date dataExclusao;

    public AnotacaoResponse(Anotacao anotacao){
        this.id = anotacao.getId();
        this.anotacao = anotacao.getAnotacao();
        this.x = anotacao.getX();
        this.y = anotacao.getY();
        this.orderId = anotacao.getOrderId();
        this.excluido = anotacao.isExcluido();
        this.update = anotacao.isUpdate();
        this.dataExclusao = anotacao.getDataExclusao();
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
