package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.equipament.Medidor;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fjesus on 16/05/2017.
 */

public class MedidorResponse implements Serializable {

    @SerializedName("IdMedidor")
    private long idMedidor;
    @SerializedName("IdPontoEntrega")
    private long idPontoEntrega;
    @SerializedName("NumeroMedidor")
    private String numeroMedidor;
    @SerializedName("ComplementoResidencial")
    private String complementoResidencial;
    @SerializedName("DataCadastro")
    private Date data_cadastro;
    @SerializedName("DataExclusao")
    private Date data_exclusao;
    @SerializedName("Update")
    private boolean update;
    @SerializedName("Excluido")
    private boolean excluido;


    public MedidorResponse(){}

    public MedidorResponse(Medidor pontoEntrega){
        this.idMedidor = pontoEntrega.getIdMedidor();
        this.idPontoEntrega = pontoEntrega.getIdPontoEntrega();
        this.numeroMedidor = pontoEntrega.getNumeroMedidor();
        this.complementoResidencial = pontoEntrega.getComplementoResidencial();
        this.data_cadastro = pontoEntrega.getData_cadastro();
        this.data_exclusao = pontoEntrega.getData_exclusao();
        this.update = pontoEntrega.isUpdate();
        this.excluido = pontoEntrega.isExcluido();
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
