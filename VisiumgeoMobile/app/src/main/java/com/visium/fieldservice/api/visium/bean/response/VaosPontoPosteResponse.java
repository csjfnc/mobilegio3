package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.PontoEntregaLocation;
import com.visium.fieldservice.entity.PostLocation;
import com.visium.fieldservice.entity.VaosPontoPoste;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fjesus on 02/01/2018.
 */

public class VaosPontoPosteResponse implements Serializable {

    @SerializedName("Id")
    private long id;
    @SerializedName("IdPontoEntrega")
    private long id_ponto_entrega;
    @SerializedName("IdPoste")
    private long id_poste;
    @SerializedName("IdOrdemDeServico")
    private long id_ordem_servico;

    @SerializedName("X1")
    private Double x1;

    @SerializedName("X2")
    private Double x2;

    @SerializedName("Y1")
    private Double y1;

    @SerializedName("Y2")
    private Double y2;

    @SerializedName("DataExclusao")
    private Date data_exclusao;

    @SerializedName("Update")
    private boolean update;

    public VaosPontoPosteResponse(VaosPontoPoste vaosPontoPoste){
        this.id = vaosPontoPoste.getId();
        this.id_ponto_entrega = vaosPontoPoste.getId_ponto_entrega();
        this.id_poste = vaosPontoPoste.getId_poste();
        this.x1 = vaosPontoPoste.getX1();
        this.x2 = vaosPontoPoste.getX2();
        this.y1 = vaosPontoPoste.getY1();
        this.y2 = vaosPontoPoste.getY2();
        this.id_ordem_servico = vaosPontoPoste.getId_ordem_servico();
        this.data_exclusao = vaosPontoPoste.getData_exclusao();
        this.update = vaosPontoPoste.isUpdate();
    }

    public Date getData_exclusao() {
        return data_exclusao;
    }

    public void setData_exclusao(Date data_exclusao) {
        this.data_exclusao = data_exclusao;
    }

    public long getId_ordem_servico() {
        return id_ordem_servico;
    }

    public void setId_ordem_servico(long id_ordem_servico) {
        this.id_ordem_servico = id_ordem_servico;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_ponto_entrega() {
        return id_ponto_entrega;
    }

    public void setId_ponto_entrega(long id_ponto_entrega) {
        this.id_ponto_entrega = id_ponto_entrega;
    }

    public long getId_poste() {
        return id_poste;
    }

    public void setId_poste(long id_poste) {
        this.id_poste = id_poste;
    }

    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }

    public Double getX2() {
        return x2;
    }

    public void setX2(Double x2) {
        this.x2 = x2;
    }

    public Double getY1() {
        return y1;
    }

    public void setY1(Double y1) {
        this.y1 = y1;
    }

    public Double getY2() {
        return y2;
    }

    public void setY2(Double y2) {
        this.y2 = y2;
    }
}
