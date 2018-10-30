package com.visium.fieldservice.entity;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.api.visium.bean.response.LightingResponse;

import java.io.Serializable;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class Lighting extends Equipment  implements Serializable {

    private long id;
    private long postId;
    private LightingArmType armType;
    private LightingLightFixtureType lightFixtureType;
    private int lightFixtureAmount;
    private LightingLampType lampType;
    private LightingPowerRating powerRating;
    private String powerPhase;
    private LightingTrigger trigger;
    private LightingStatus status;
    private int lampAmount;
    private boolean excluido;
    private boolean update;
    private PontoAtualizacao pontoAtualizacao;

    public Lighting(){}

    public Lighting(LightingResponse response) {
        this.id = response.getId();
        this.postId = response.getPostId();
        this.armType = response.getArmType();
        this.lightFixtureType = response.getLightFixtureType();
        this.lightFixtureAmount = response.getLightFixtureAmount();
        this.lampType = response.getLampType();
        this.powerRating = response.getPowerRating();
        this.powerPhase = response.getPowerPhase();
        this.trigger = response.getTrigger();
        this.status = response.getStatus();
        this.lampAmount = response.getLampAmount();
        this.excluido = response.isExcluido();
        this.update = response.isUpdate();
        this.pontoAtualizacao = response.getPontoAtualizacao();
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

    public PontoAtualizacao getPontoAtualizacao() {
        return pontoAtualizacao;
    }

    public void setPontoAtualizacao(PontoAtualizacao pontoAtualizacao) {
        this.pontoAtualizacao = pontoAtualizacao;
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

    public LightingArmType getArmType() {
        return armType;
    }

    public void setArmType(LightingArmType armType) {
        this.armType = armType;
    }

    public LightingLightFixtureType getLightFixtureType() {
        return lightFixtureType;
    }

    public void setLightFixtureType(LightingLightFixtureType lightFixtureType) {
        this.lightFixtureType = lightFixtureType;
    }

    public int getLightFixtureAmount() {
        return lightFixtureAmount;
    }

    public void setLightFixtureAmount(int lightFixtureAmount) {
        this.lightFixtureAmount = lightFixtureAmount;
    }

    public LightingLampType getLampType() {
        return lampType;
    }

    public void setLampType(LightingLampType lampType) {
        this.lampType = lampType;
    }

    public LightingPowerRating getPowerRating() {
        return powerRating;
    }

    public void setPowerRating(LightingPowerRating powerRating) {
        this.powerRating = powerRating;
    }

    public String getPowerPhase() {
        return powerPhase;
    }

    public void setPowerPhase(String powerPhase) {
        this.powerPhase = powerPhase;
    }

    public LightingTrigger getTrigger() {
        return trigger;
    }

    public void setTrigger(LightingTrigger trigger) {
        this.trigger = trigger;
    }

    public LightingStatus getStatus() {
        return status;
    }

    public void setStatus(LightingStatus status) {
        this.status = status;
    }

    public int getLampAmount() {
        return lampAmount;
    }

    public void setLampAmount(int lampAmount) {
        this.lampAmount = lampAmount;
    }



    @Override
    public boolean equals(Object o) {
        return o instanceof Lighting
                && ((Lighting) o).getId() == this.id;
    }

}
