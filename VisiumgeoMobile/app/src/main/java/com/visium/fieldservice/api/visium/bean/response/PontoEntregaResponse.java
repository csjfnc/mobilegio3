package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.Lagradouro;
import com.visium.fieldservice.entity.PontoAtualizacao;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.PontoEntregaLocation;
import com.visium.fieldservice.entity.PontoEntregaPhotos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PontoEntregaResponse implements Serializable {

    @SerializedName("IdPontoEntrega")
    private long id;
    @SerializedName("IdPoste")
    private long postId;
    @SerializedName("Status")
    private int status;
    @SerializedName("Logradouro")
    private String logradouro;
    @SerializedName("Y")
    private double y;
    @SerializedName("X")
    private double x;
    @SerializedName("CodigoGeoBD")
    private int codigo_bd_geo;
    @SerializedName("Update")
    private boolean update;
    @SerializedName("Excluido")
    private boolean excluido;
    @SerializedName("Fotos")
    private List<PontoEntregaPhotosResponse> photos;
    @SerializedName("IdCidade")
    private long cityId;

    /*novo*/
    @SerializedName("IdOrdemDeServico")
    private long orderId;
    /*@SerializedName("Posicao")
    private PontoEntregaLocationResponse location;*/
    /*@SerializedName("Posicao")
    private PontoAtualizacao pontoAtualizacao;*/
    @SerializedName("Finalizado")
    private boolean closed;
    @SerializedName("NumeroPontoNaOS")
    private int postNumber;
    @SerializedName("ClasseSocial")
    private int tipoDemanda;
    @SerializedName("Classificacao")
    private int classificacao;
    @SerializedName("TipoImovel")
    private String tipoImovel;
    @SerializedName("Complemento1")
    private String complemento1;
    @SerializedName("Complemento2")
    private String complemento2;
    @SerializedName("Lagradouro")
    private Lagradouro lagradouro;
    @SerializedName("NumeroLocal")
    private int numero_local;
    @SerializedName("NumeroAndaresEdificio")
    private int numero_andares_edificio;
    @SerializedName("NumeroTotalApartamentos")
    private int numero_total_apartamentos;
    @SerializedName("NomeEdificio")
    private String nome_edificio;
    @SerializedName("X_atualizacao")
    private double x_atualizacao;
    @SerializedName("Y_atualizacao")
    private double y_atualizacao;


    @SerializedName("QtdDomicilio")
    private int qtd_domicilio;

    @SerializedName("TipoComercio")
    private int tipo_comercio;

    @SerializedName("QtdSalas")
    private int qtd_salas;

    @SerializedName("Ocorrencia")
    private int ocorrencia;

    @SerializedName("QtdBlocos")
    private String qtd_blocos;



    public PontoEntregaResponse(){}

    public PontoEntregaResponse(PontoEntrega pontoEntrega){
        this.id = pontoEntrega.getId();
        this.postId = pontoEntrega.getPostId();
        this.status = pontoEntrega.getStatus();
        this.logradouro = pontoEntrega.getLogradouro();
        this.x = pontoEntrega.getX();
        this.y = pontoEntrega.getY();
        this.codigo_bd_geo = pontoEntrega.getCodigo_bd_geo();
        this.codigo_bd_geo = pontoEntrega.getCodigo_bd_geo();
        this.update = pontoEntrega.isUpdate();
        this.excluido = pontoEntrega.isExcluido();
        this.orderId = pontoEntrega.getOrderId();

       // this.location = new PontoEntregaLocationResponse(pontoEntrega.getLocation());
        //this.pontoAtualizacao = pontoEntrega.getPontoAtualizacao();
        this.postNumber = pontoEntrega.getPostNumber();
        this.tipoDemanda = pontoEntrega.getTipoDemanda();
        this.classificacao = pontoEntrega.getClassificacao();
        this.tipoImovel = pontoEntrega.getTipoImovel();
        this.complemento1 = pontoEntrega.getComplemento1();
        this.complemento2 = pontoEntrega.getComplemento2();
        this.lagradouro = pontoEntrega.getLagradouro();
        this.numero_local = pontoEntrega.getNumero_local();
        this.numero_andares_edificio = pontoEntrega.getNumero_andares_edificio();
        this.numero_total_apartamentos = pontoEntrega.getNumero_total_apartamentos();
        this.nome_edificio = pontoEntrega.getNome_edificio();
        this.x_atualizacao = pontoEntrega.getX_atualizacao();
        this.y_atualizacao = pontoEntrega.getY_atualizacao();
        this.cityId = pontoEntrega.getCityId();
        this.qtd_domicilio = pontoEntrega.getQtd_domicilio();
        this.tipo_comercio = pontoEntrega.getTipo_comercio();
        this.qtd_salas = pontoEntrega.getQtd_salas();
        this.ocorrencia = pontoEntrega.getOcorrencia();
        this.qtd_blocos = pontoEntrega.getQta_blocos();

        List<PontoEntregaPhotos> list = pontoEntrega.getPhotos();
        photos = new ArrayList<PontoEntregaPhotosResponse>();
        for(PontoEntregaPhotos pe : list){
            photos.add(new PontoEntregaPhotosResponse(pe));
        }

        /*List<PostPhotosResponse> list = resp.getPhotos();
        photos = new ArrayList<PostPhotos>();
        for(PostPhotosResponse ppr : list) {
            photos.add(new PostPhotos(ppr));
        }*/

    }

    public int getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(int classificacao) {
        this.classificacao = classificacao;
    }

    public int getQtd_domicilio() {
        return qtd_domicilio;
    }

    public void setQtd_domicilio(int qtd_domicilio) {
        this.qtd_domicilio = qtd_domicilio;
    }

    public int getTipo_comercio() {
        return tipo_comercio;
    }

    public void setTipo_comercio(int tipo_comercio) {
        this.tipo_comercio = tipo_comercio;
    }

    public int getQtd_salas() {
        return qtd_salas;
    }

    public void setQtd_salas(int qtd_salas) {
        this.qtd_salas = qtd_salas;
    }

    public int getOcorrencia() {
        return ocorrencia;
    }

    public void setOcorrencia(int ocorrencia) {
        this.ocorrencia = ocorrencia;
    }

    public String getQtd_blocos() {
        return qtd_blocos;
    }

    public void setQtd_blocos(String qtd_blocos) {
        this.qtd_blocos = qtd_blocos;
    }

    public long getCityId() {
        return cityId;
    }

    public void setCityId(long cityId) {
        this.cityId = cityId;
    }

    public double getX_atualizacao() {
        return x_atualizacao;
    }

    public void setX_atualizacao(double x_atualizacao) {
        this.x_atualizacao = x_atualizacao;
    }

    public double getY_atualizacao() {
        return y_atualizacao;
    }

    public void setY_atualizacao(double y_atualizacao) {
        this.y_atualizacao = y_atualizacao;
    }

    public int getNumero_local() {
        return numero_local;
    }

    public void setNumero_local(int numero_local) {
        this.numero_local = numero_local;
    }

    public int getNumero_andares_edificio() {
        return numero_andares_edificio;
    }

    public void setNumero_andares_edificio(int numero_andares_edificio) {
        this.numero_andares_edificio = numero_andares_edificio;
    }

    public int getNumero_total_apartamentos() {
        return numero_total_apartamentos;
    }

    public void setNumero_total_apartamentos(int numero_total_apartamentos) {
        this.numero_total_apartamentos = numero_total_apartamentos;
    }

    public String getNome_edificio() {
        return nome_edificio;
    }

    public void setNome_edificio(String nome_edificio) {
        this.nome_edificio = nome_edificio;
    }

    public Lagradouro getLagradouro() {
        return lagradouro;
    }

    public void setLagradouro(Lagradouro lagradouro) {
        this.lagradouro = lagradouro;
    }

    public int getPostNumber() {
        return postNumber;
    }

    public void setPostNumber(int postNumber) {
        this.postNumber = postNumber;
    }

    public int getTipoDemanda() {
        return tipoDemanda;
    }

    public void setTipoDemanda(int tipoDemanda) {
        this.tipoDemanda = tipoDemanda;
    }


    public String getTipoImovel() {
        return tipoImovel;
    }

    public void setTipoImovel(String tipoImovel) {
        this.tipoImovel = tipoImovel;
    }

    public String getComplemento1() {
        return complemento1;
    }

    public void setComplemento1(String complemento1) {
        this.complemento1 = complemento1;
    }

    public String getComplemento2() {
        return complemento2;
    }

    public void setComplemento2(String complemento2) {
        this.complemento2 = complemento2;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    /*public PontoEntregaLocationResponse getLocation() {
        return location;
    }

    public void setLocation(PontoEntregaLocationResponse location) {
        this.location = location;
    }*/

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public List<PontoEntregaPhotosResponse> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PontoEntregaPhotosResponse> photos) {
        this.photos = photos;
    }

    public boolean isExcluido() {
        return excluido;
    }

    public void setExcluido(boolean excluido) {
        this.excluido = excluido;
    }

    public boolean isUpdate() {
        return update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public int getCodigo_bd_geo() {
        return codigo_bd_geo;
    }

    public void setCodigo_bd_geo(int codigo_bd_geo) {
        this.codigo_bd_geo = codigo_bd_geo;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
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

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }


}