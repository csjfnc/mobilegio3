package com.visium.fieldservice.api.visium.bean.request;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.LocalUsuario;

/**
 * Created by fjesus on 13/06/2018.
 */

public class LocalUsuarioRequest {

    @SerializedName("NomeUser")
    private String NomeUser;
    @SerializedName("X")
    private double x;
    @SerializedName("Y")
    private double y;
    @SerializedName("Panico")
    private int panico;
    @SerializedName("Mensagem")
    private String mensagem;


    public LocalUsuarioRequest(String NomeUser, double x, double y, int panico, String mensagem ){
        this.NomeUser = NomeUser;
        this.x = x;
        this.y = y;
        this.panico = panico;
        this.mensagem = mensagem;
    }

    public int getPanico() {
        return panico;
    }

    public void setPanico(int panico) {
        this.panico = panico;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getNomeUser() {
        return NomeUser;
    }

    public void setNomeUser(String nomeUser) {
        NomeUser = nomeUser;
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
