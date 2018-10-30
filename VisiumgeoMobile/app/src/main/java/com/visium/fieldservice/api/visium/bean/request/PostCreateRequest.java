package com.visium.fieldservice.api.visium.bean.request;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.PontoAtualizacao;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.PostPhotos;

import java.io.Serializable;
import java.util.List;

public class PostCreateRequest implements Serializable {

    @SerializedName("NumeroPosteNaOs")
    private int postNumber;

    @SerializedName("Lat")
    private Double lat;

    @SerializedName("Lon")
    private Double lon;

    @SerializedName("IdOrdemDeServico")
    private long orderId;

    @SerializedName("Fotos")
    private List<PostPhotos> photos;

    @SerializedName("Altura")
    private double height;

    @SerializedName("Esforco")
    private int postEffort;

    @SerializedName("TipoPoste")
    private int postType;

    @SerializedName("Descricao")
    private String observations;

    @SerializedName("Excluido")
    private boolean excluido;

    @SerializedName("Update")
    private boolean update;

    @SerializedName("PontoAtualizacao")
    private PontoAtualizacao pontoAtualizacao;

    public PostCreateRequest() {}

    public PostCreateRequest(Post post) {
        this.lat = post.getLocation().getLat();
        this.lon = post.getLocation().getLon();
        this.update = post.isUpdate();
        this.pontoAtualizacao = post.getPontoAtualizacao();
        this.orderId = post.getOrderId();
        this.photos = post.getPhotos();
        this.height = post.getHeight();
        this.postEffort = post.getPostEffort();
        this.postType = post.getPostType();
        this.observations = post.getObservations();
        this.excluido = post.isExcluido();
        this.update = post.isUpdate();
        this.pontoAtualizacao = post.getPontoAtualizacao();
    }


    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
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

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public PontoAtualizacao getPontoAtualizacao() {
        return pontoAtualizacao;
    }

    public void setPontoAtualizacao(PontoAtualizacao pontoAtualizacao) {
        this.pontoAtualizacao = pontoAtualizacao;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public List<PostPhotos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PostPhotos> photos) {
        this.photos = photos;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public int getPostEffort() {
        return postEffort;
    }

    public void setPostEffort(int postEffort) {
        this.postEffort = postEffort;
    }

    public int getPostType() {
        return postType;
    }

    public void setPostType(int postType) {
        this.postType = postType;
    }

    public String getObservations() {
        return observations;
    }

    public void setObservations(String observations) {
        this.observations = observations;
    }
}