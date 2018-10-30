package com.visium.fieldservice.entity.equipament;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.api.visium.bean.response.BancoCapacitorResponse;

import java.io.Serializable;

public class BancoCapacitor implements Serializable {
    private long id;
    private long postId;
    private int status;
    private int proprietario;
    private String numeroDeCampo;
    private int estadoNormal;
    private String fase;
    private String capacidadeEloFusivel;
    private int tipoControle;
    private boolean chaveOleo;
    private boolean controladorExterno;
    private int quantidadeChavesMontagem;
    private double totalKVar;
    private int quantidadeElementos;
    private String dadosControlador;
    private int numeroEquipamento;

    private double latAtualizacao, lonAtualizacao;

    public BancoCapacitor(){}

    public BancoCapacitor(BancoCapacitorResponse response) {
        this.id = response.getId();
        this.postId = response.getPostId();
        this.status = response.getStatus();
        this.proprietario = response.getProprietario();
        this.numeroDeCampo = response.getNumeroDeCampo();
        this.fase = response.getFase();
        this.capacidadeEloFusivel = response.getCapacidadeElofusivel();
        this.tipoControle = response.getTipoControle();
        this.chaveOleo = response.getChaveOleo();
        this.controladorExterno = response.getControladorExterno();
        this.quantidadeChavesMontagem = response.getQuantidadeChavesMontagem();
        this.totalKVar = response.getTotalKVar();
        this.quantidadeElementos = response.getQuantidadeElementos();
        this.dadosControlador = response.getDadosControlador();
        this.numeroEquipamento = response.getNumeroEquipamento();
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof BancoCapacitor
                && ((BancoCapacitor) o).getId() == this.id;
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

    public int getProprietario() {
        return proprietario;
    }

    public void setProprietario(int proprietario) {
        this.proprietario = proprietario;
    }

    public String getNumeroDeCampo() {
        return numeroDeCampo;
    }

    public void setNumeroDeCampo(String numeroDeCampo) {
        this.numeroDeCampo = numeroDeCampo;
    }

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getLatAtualizacao() {
        return latAtualizacao;
    }

    public void setLatAtualizacao(double latAtualizacao) {
        this.latAtualizacao = latAtualizacao;
    }

    public double getLonAtualizacao() {
        return lonAtualizacao;
    }

    public void setLonAtualizacao(double lonAtualizacao) {
        this.lonAtualizacao = lonAtualizacao;
    }

    public LatLng toLatLngAtualizacao() {
        return new LatLng(latAtualizacao, lonAtualizacao);
    }

    public int getEstadoNormal() {
        return estadoNormal;
    }

    public void setEstadoNormal(int estadoNormal) {
        this.estadoNormal = estadoNormal;
    }

    public String getCapacidadeEloFusivel() {
        return capacidadeEloFusivel;
    }

    public void setCapacidadeEloFusivel(String capacidadeEloFusivel) {
        this.capacidadeEloFusivel = capacidadeEloFusivel;
    }

    public int getTipoControle() {
        return tipoControle;
    }

    public void setTipoControle(int tipoControle) {
        this.tipoControle = tipoControle;
    }

    public boolean isChaveOleo() {
        return chaveOleo;
    }

    public void setChaveOleo(boolean chaveOleo) {
        this.chaveOleo = chaveOleo;
    }

    public boolean isControladorExterno() {
        return controladorExterno;
    }

    public void setControladorExterno(boolean controladorExterno) {
        this.controladorExterno = controladorExterno;
    }

    public int getQuantidadeChavesMontagem() {
        return quantidadeChavesMontagem;
    }

    public void setQuantidadeChavesMontagem(int quantidadeChavesMontagem) {
        this.quantidadeChavesMontagem = quantidadeChavesMontagem;
    }

    public double getTotalKVar() {
        return totalKVar;
    }

    public void setTotalKVar(double totalKVar) {
        this.totalKVar = totalKVar;
    }

    public int getQuantidadeElementos() {
        return quantidadeElementos;
    }

    public void setQuantidadeElementos(int quantidadeElementos) {
        this.quantidadeElementos = quantidadeElementos;
    }

    public String getDadosControlador() {
        return dadosControlador;
    }

    public void setDadosControlador(String dadosControlador) {
        this.dadosControlador = dadosControlador;
    }

    public int getNumeroEquipamento() {
        return numeroEquipamento;
    }

    public void setNumeroEquipamento(int numeroEquipamento) {
        this.numeroEquipamento = numeroEquipamento;
    }
}
