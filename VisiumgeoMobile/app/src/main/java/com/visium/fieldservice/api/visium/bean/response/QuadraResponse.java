package com.visium.fieldservice.api.visium.bean.response;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.Quadra;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by fjesus on 16/01/2018.
 */

public class QuadraResponse implements Serializable {

    @SerializedName("ID")
    private long id;
    @SerializedName("OrderID")
    private long idOrder;
    @SerializedName("LatLonQuadras")
    private List<LatLonQuadraResponse> latLngs;
    @SerializedName("Update")
    private boolean update;
    @SerializedName("Excluido")
    private boolean excluido;
    @SerializedName("DataExclusao")
    private Date dateExcluido;

    public QuadraResponse(Quadra quadra){
        this.idOrder = quadra.getIdOrder();
        this.id = quadra.getId();
        this.dateExcluido = quadra.getDateExcluido();
        this.excluido = quadra.isExcluido();
        this.update = quadra.isUpdate();
        this.latLngs = quadra.getLatLngs();
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
