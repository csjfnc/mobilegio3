package com.visium.fieldservice.api.visium.bean.response;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.entity.LocalUsuario;

/**
 * Created by fjesus on 13/06/2018.
 */

public class LocalUsuarioResponse {

    private String idUser;
    private LatLng latlon;
    private static int panico;
    private static String mensagem;


    public LocalUsuarioResponse(LocalUsuario localUsuario){
        this.idUser = localUsuario.getIdUser();
        this.latlon = localUsuario.getLatlon();
        this.panico = localUsuario.getPanico();
        this.mensagem = localUsuario.getMensagem();
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public LatLng getLatlon() {
        return latlon;
    }

    public void setLatlon(LatLng latlon) {
        this.latlon = latlon;
    }

    public static int getPanico() {
        return panico;
    }

    public static void setPanico(int panico) {
        LocalUsuarioResponse.panico = panico;
    }

    public static String getMensagem() {
        return mensagem;
    }

    public static void setMensagem(String mensagem) {
        LocalUsuarioResponse.mensagem = mensagem;
    }
}
