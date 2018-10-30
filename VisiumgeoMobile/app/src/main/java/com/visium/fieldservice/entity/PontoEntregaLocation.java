package com.visium.fieldservice.entity;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.api.visium.bean.response.PontoEntregaLocationResponse;
import com.visium.fieldservice.api.visium.bean.response.PontoEntregaResponse;
import com.visium.fieldservice.api.visium.bean.response.PostLocationResponse;

import java.io.Serializable;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class PontoEntregaLocation implements Serializable {

    private static final long serialVersionUID = 6721620434520908307L;

    private Double lat;
    private Double lon;

    public PontoEntregaLocation(){}

    public PontoEntregaLocation(PontoEntregaLocationResponse resp){
        this.lat = resp.getLat();
        this.lon = resp.getLon();
        //this.latAtualizacao = resp.getLatAtualizacao();
        //this.lonAtualizacao = resp.getLonAtualizacao();
    }

    public PontoEntregaLocation(Double lat, Double lon, Double latAtualizacao, Double lonAtualizacao) {
        this.lat = lat;
        this.lon = lon;
        /*if(latAtualizacao == null || lonAtualizacao == null) {
            this.latAtualizacao = (double)0;
            this.lonAtualizacao = (double)0;
        } else {
            this.latAtualizacao = latAtualizacao;
            this.lonAtualizacao = lonAtualizacao;
        }*/
    }

    public PontoEntregaLocation(Double lat, Double lon) {
        this.lat = lat;
        this.lon = lon;
        //this.latAtualizacao = (double)0;
        //this.lonAtualizacao = (double)0;
    }


    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public LatLng toLatLng() {
        return new LatLng(lat, lon);
    }


    /*public Double getLatAtualizacao() {
        return latAtualizacao;
    }

    public void setLatAtualizacao(Double latAtualizacao) {
        this.latAtualizacao = latAtualizacao;
    }

    public Double getLonAtualizacao() {
        return lonAtualizacao;
    }

    public void setLonAtualizacao(Double lonAtualizacao) {
        this.lonAtualizacao = lonAtualizacao;
    }

    public LatLng toLatLngAtualizacao() {
        return new LatLng(latAtualizacao, lonAtualizacao);
    }*/
}
