package com.visium.fieldservice.entity.equipament;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.api.visium.bean.response.MedidorResponse;

import java.util.Date;

/**
 * Created by fjesus on 16/05/2017.
 */

public class Medidor {

        private long idMedidor;
        private long idPontoEntrega;
        private String numeroMedidor;
        private String complementoResidencial;
        private Date data_cadastro;
        private Date data_exclusao;
        private boolean update;
        private boolean excluido;



    public Medidor(MedidorResponse response){

        this.idMedidor = response.getIdMedidor();
        this.idPontoEntrega = response.getIdPontoEntrega();
        this.numeroMedidor = response.getNumeroMedidor();
        this.complementoResidencial = response.getComplementoResidencial();
        this.data_cadastro = response.getData_cadastro();
        this.data_exclusao = response.getData_exclusao();
        this.update = response.isUpdate();
        this.excluido = response.isExcluido();
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

    public Date getData_cadastro() {
        return data_cadastro;
    }

    public void setData_cadastro(Date data_cadastro) {
        this.data_cadastro = data_cadastro;
    }

    public Date getData_exclusao() {
        return data_exclusao;
    }

    public void setData_exclusao(Date data_exclusao) {
        this.data_exclusao = data_exclusao;
    }

    public long getIdMedidor() {
            return idMedidor;
        }

        public void setIdMedidor(long idMedidor) {
            this.idMedidor = idMedidor;
        }

        public long getIdPontoEntrega() {
            return idPontoEntrega;
        }

        public void setIdPontoEntrega(long idPontoEntrega) {
            this.idPontoEntrega = idPontoEntrega;
        }

        public String getNumeroMedidor() {
            return numeroMedidor;
        }

        public void setNumeroMedidor(String numeroMedidor) {
            this.numeroMedidor = numeroMedidor;
        }

        public String getComplementoResidencial() {
            return complementoResidencial;
        }

        public void setComplementoResidencial(String complementoResidencial) {
            this.complementoResidencial = complementoResidencial;
        }
    }
