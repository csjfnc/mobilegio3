package com.visium.fieldservice.api.visium.bean.request;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.VaosPontoPoste;

import java.io.Serializable;

/**
 * Created by fjesus on 02/01/2018.
 */

public class VaosPontoPosteRequest implements Serializable {

    @SerializedName("Id")
    private long id;
    @SerializedName("IdPontoEntrega")
    private long id_ponto_entrega;
    @SerializedName("IdPoste")
    private long id_poste;

    @SerializedName("x1")
    private Double x1;

    @SerializedName("x2")
    private Double x2;

    @SerializedName("y1")
    private Double y1;

    @SerializedName("y2")
    private Double y2;

    public VaosPontoPosteRequest(VaosPontoPoste vaosPontoPoste){
        this.id = vaosPontoPoste.getId();
        this.id_ponto_entrega = vaosPontoPoste.getId_ponto_entrega();
        this.id_poste = vaosPontoPoste.getId_poste();
        this.x1 = vaosPontoPoste.getX1();
        this.x2 = vaosPontoPoste.getX2();
        this.y1 = vaosPontoPoste.getY1();
        this.y2 = vaosPontoPoste.getY2();
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
