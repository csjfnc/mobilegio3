package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.DemandaStrand;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fjesus on 30/01/2018.
 */

public class DemandaStrandResponse implements Serializable {

    @SerializedName("ID")
    private long id;
    @SerializedName("OrderID")
    private long orderId;
    @SerializedName("X1")
    private double x1;
    @SerializedName("X2")
    private double x2;
    @SerializedName("Y1")
    private double y1;
    @SerializedName("Y2")
    private double y2;
    @SerializedName("DataExclusao")
    private Date dataExclusao;
    @SerializedName("Update")
    private boolean update;
    @SerializedName("Excluido")
    private boolean excluido;
    @SerializedName("CodigoBdGeo")
    private long codigoBdGeo;
    @SerializedName("PosteId1")
    private long posteId1;
    @SerializedName("PosteId2")
    private long posteId2;

    public DemandaStrandResponse(DemandaStrand strand){
        this.id = strand.getId();
        this.orderId = strand.getOrderId();
        this.x1 = strand.getX1();
        this.x2 = strand.getX2();
        this.y1 = strand.getY1();
        this.y2 = strand.getY2();
        this.excluido = strand.isExcluido();
        this.update = strand.isUpdate();
        this.dataExclusao = strand.getDataExclusao();
        this.codigoBdGeo = strand.getCodigoBdGeo();
        this.posteId1 = strand.getPosteId1();
        this.posteId2 = strand.getPosteId2();
    }

    public long getPosteId1() {
        return posteId1;
    }

    public void setPosteId1(long posteId1) {
        this.posteId1 = posteId1;
    }

    public long getPosteId2() {
        return posteId2;
    }

    public void setPosteId2(long posteId2) {
        this.posteId2 = posteId2;
    }

    public long getCodigoBdGeo() {
        return codigoBdGeo;
    }

    public void setCodigoBdGeo(long codigoBdGeo) {
        this.codigoBdGeo = codigoBdGeo;
    }

    public Date getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(Date dataExclusao) {
        this.dataExclusao = dataExclusao;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public DemandaStrandResponse(){

    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }
}
