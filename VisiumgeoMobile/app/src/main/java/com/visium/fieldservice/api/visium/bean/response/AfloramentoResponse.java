package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.equipament.Afloramento;

import java.io.Serializable;

public class AfloramentoResponse implements Serializable {

    @SerializedName("IdAfloramento")
    private long id;

    @SerializedName("IdPoste")
    private long postId;

    @SerializedName("Status")
    private int status;

    @SerializedName("Proprietario")
    private int proprietario;


    public AfloramentoResponse(Afloramento afloramento){
        this.id = afloramento.getId();
        this.postId = afloramento.getPostId();
        this.status = afloramento.getStatus();
        this.proprietario = afloramento.getProprietario();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPostId() {
        return postId;
    }

    public void setPostId(long postId) {
        this.postId = postId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getProprietario() {
        return proprietario;
    }

    public void setProprietario(int proprietario) {
        this.proprietario = proprietario;
    }
}