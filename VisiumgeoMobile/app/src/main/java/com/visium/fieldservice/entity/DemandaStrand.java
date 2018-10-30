package com.visium.fieldservice.entity;

import com.visium.fieldservice.api.visium.bean.response.DemandaStrandResponse;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by fjesus on 30/01/2018.
 */

public class DemandaStrand implements Serializable{

    private long id;
    private long orderId;
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private long posteId1;
    private long posteId2;
    private Date dataExclusao;
    private boolean update;
    private boolean excluido;
    private long codigoBdGeo;

    public DemandaStrand(DemandaStrandResponse response){
        this.id = response.getId();
        this.orderId = response.getOrderId();
        this.x1 = response.getX1();
        this.x2 = response.getX2();
        this.y1 = response.getY1();
        this.y2 = response.getY2();
        this.excluido = response.isExcluido();
        this.update = response.isUpdate();
        this.dataExclusao = response.getDataExclusao();
        this.codigoBdGeo = response.getCodigoBdGeo();
        this.posteId1 = response.getPosteId1();
        this.posteId2 = response.getPosteId2();
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

    public DemandaStrand(){}

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
