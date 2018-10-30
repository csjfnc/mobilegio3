package com.visium.fieldservice.entity;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.api.visium.bean.response.LocalUsuarioResponse;

/**
 * Created by fjesus on 13/06/2018.
 */

public class LocalUsuario {

    private static String idUser;
    private static LatLng latlon;
    private static int panico;
    private static String mensagem;
    public static int tempoToast = 3000;

    public LocalUsuario(){}
    public LocalUsuario(LocalUsuarioResponse response){
        this.idUser = response.getIdUser();
        this.latlon = response.getLatlon();
    }

    public static String getIdUser() {
        return idUser;
    }

    public static void setIdUser(String idUser) {
        LocalUsuario.idUser = idUser;
    }

    public static LatLng getLatlon() {
        return latlon;
    }

    public static void setLatlon(LatLng latlon) {
        LocalUsuario.latlon = latlon;
    }

    public static int getPanico() {
        return panico;
    }

    public static void setPanico(int panico) {
        LocalUsuario.panico = panico;
    }

    public static String getMensagem() {
        return mensagem;
    }

    public static void setMensagem(String mensagem) {
        LocalUsuario.mensagem = mensagem;
    }
}
