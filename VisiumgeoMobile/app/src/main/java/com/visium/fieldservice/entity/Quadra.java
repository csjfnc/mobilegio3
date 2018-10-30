package com.visium.fieldservice.entity;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.api.visium.bean.response.LatLonQuadraResponse;
import com.visium.fieldservice.api.visium.bean.response.QuadraResponse;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by fjesus on 16/01/2018.
 */

public class Quadra implements Serializable {

    private long id;
    private long idOrder;
    private List<LatLonQuadraResponse> latLngs;
    private boolean update;
    private boolean excluido;
    private Date dateExcluido;

    public Quadra(QuadraResponse response){
        this.idOrder = response.getIdOrder();
        this.id = response.getId();
        this.latLngs = response.getLatLngs();
        this.update = response.isUpdate();
        this.excluido = response.isExcluido();
        this.dateExcluido = response.getDateExcluido();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Date getDateExcluido() {
        return dateExcluido;
    }

    public void setDateExcluido(Date dateExcluido) {
        this.dateExcluido = dateExcluido;
    }

    public long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(long idOrder) {
        this.idOrder = idOrder;
    }

    public List<LatLonQuadraResponse> getLatLngs() {
        return latLngs;
    }

    public void setLatLngs(List<LatLonQuadraResponse> latLngs) {
        this.latLngs = latLngs;
    }
}
