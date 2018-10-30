package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.equipament.BancoCapacitor;

import java.io.Serializable;

public class BancoCapacitorResponse implements Serializable {

    @SerializedName("IdBancoCapacitor")
    private long id;

    @SerializedName("IdPoste")
    private long postId;

    @SerializedName("Status")
    private int status;

    @SerializedName("Proprietario")
    private int proprietario;

    @SerializedName("NumeroCampo")
    private String numeroDeCampo;

    @SerializedName("EstadoNormal")
    private int estadoNormal;

    @SerializedName("Fase")
    private String fase;

    @SerializedName("CapacidadeEloFusivel")
    private String capacidadeElofusivel;

    @SerializedName("TipoControle")
    private int tipoControle;

    @SerializedName("PossuiChaveOleo")
    private boolean chaveOleo;

    @SerializedName("ControladorExterno")
    private boolean controladorExterno;

    @SerializedName("QtdChavesMontagem")
    private int quantidadeChavesMontagem;

    @SerializedName("TotalKvar")
    private double totalKVar;

    @SerializedName("QtdElementos")
    private int quantidadeElementos;

    @SerializedName("DadosControlador")
    private String dadosControlador;

    @SerializedName("NumeroEquipamento")
    private int numeroEquipamento;

    public BancoCapacitorResponse(BancoCapacitor bancoCapacitor){
        this.id = bancoCapacitor.getId();
        this.postId = bancoCapacitor.getPostId();
        this.status = bancoCapacitor.getStatus();
        this.proprietario = bancoCapacitor.getProprietario();
        this.numeroDeCampo = bancoCapacitor.getNumeroDeCampo();
        this.estadoNormal = bancoCapacitor.getEstadoNormal();
        this.fase = bancoCapacitor.getFase();
        this.capacidadeElofusivel = bancoCapacitor.getCapacidadeEloFusivel();
        this.tipoControle = bancoCapacitor.getTipoControle();
        this.chaveOleo  = bancoCapacitor.isChaveOleo();
        this.controladorExterno = bancoCapacitor.isControladorExterno();
        this.quantidadeChavesMontagem = bancoCapacitor.getQuantidadeChavesMontagem();
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

    public String getFase() {
        return fase;
    }

    public void setFase(String fase) {
        this.fase = fase;
    }

    public int getEstadoNormal() {
        return estadoNormal;
    }

    public void setEstadoNormal(int estadoNormal) {
        this.estadoNormal = estadoNormal;
    }

    public String getCapacidadeElofusivel() {
        return capacidadeElofusivel;
    }

    public void setCapacidadeElofusivel(String capacidadeElofusivel) {
        this.capacidadeElofusivel = capacidadeElofusivel;
    }

    public int getTipoControle() {
        return tipoControle;
    }

    public void setTipoControle(int tipoControle) {
        this.tipoControle = tipoControle;
    }

    public boolean getChaveOleo() {
        return chaveOleo;
    }

    public void setChaveOleo(boolean chaveOleo) {
        this.chaveOleo = chaveOleo;
    }

    public boolean getControladorExterno() {
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

    public String getNumeroDeCampo() {
        return numeroDeCampo;
    }

    public void setNumeroDeCampo(String numeroDeCampo) {
        this.numeroDeCampo = numeroDeCampo;
    }

    public boolean isChaveOleo() {
        return chaveOleo;
    }

    public boolean isControladorExterno() {
        return controladorExterno;
    }
}