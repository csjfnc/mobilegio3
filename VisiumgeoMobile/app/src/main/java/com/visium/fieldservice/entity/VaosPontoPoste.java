package com.visium.fieldservice.entity;

import com.visium.fieldservice.api.visium.bean.response.VaosPontoPosteResponse;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fjesus on 02/01/2018.
 */

public class VaosPontoPoste implements Serializable {

    private long id;
    private long id_ponto_entrega;
    private long id_poste;
    private long id_ordem_servico;
    private Double x1;
    private Double x2;
    private Double y1;
    private Double y2;
    private Date data_exclusao;
    private boolean update;


    public VaosPontoPoste(){}

    public VaosPontoPoste(VaosPontoPosteResponse vaosPontoPosteResponse){
        this.id = vaosPontoPosteResponse.getId();
        this.id_ponto_entrega = vaosPontoPosteResponse.getId_ponto_entrega();
        this.id_poste = vaosPontoPosteResponse.getId_poste();
        this.x1 = vaosPontoPosteResponse.getX1();
        this.x2 = vaosPontoPosteResponse.getX2();
        this.y1 = vaosPontoPosteResponse.getY1();
        this.y2 = vaosPontoPosteResponse.getY2();
        this.id_ordem_servico = vaosPontoPosteResponse.getId_ordem_servico();
        this.data_exclusao = vaosPontoPosteResponse.getData_exclusao();
        this.update = vaosPontoPosteResponse.isUpdate();
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
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
