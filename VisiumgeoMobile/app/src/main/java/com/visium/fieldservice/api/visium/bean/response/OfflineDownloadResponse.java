package com.visium.fieldservice.api.visium.bean.response;

import com.google.gson.annotations.SerializedName;
import com.visium.fieldservice.entity.Anotacao;
import com.visium.fieldservice.entity.DemandaStrand;
import com.visium.fieldservice.entity.Lagradouro;
import com.visium.fieldservice.entity.Lighting;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.PontoEntregaLocation;
import com.visium.fieldservice.entity.PontoEntregaPhotos;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.Quadra;
import com.visium.fieldservice.entity.ServiceOrder;
import com.visium.fieldservice.entity.ServiceOrderDetails;
import com.visium.fieldservice.entity.VaoPrimario;
import com.visium.fieldservice.entity.VaosPontoPoste;
import com.visium.fieldservice.entity.equipament.Afloramento;
import com.visium.fieldservice.entity.equipament.Medidor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class OfflineDownloadResponse implements Serializable {

    private static final long serialVersionUID = -2639082179861442012L;

    @SerializedName("OrdemDeServico")
    private OfflineServiceOrderResponse serviceOrder;

    @SerializedName("Postes")
    private List<PostResponse> posts;

    @SerializedName("IPS")
    private List<LightingResponse> lightingList;

    @SerializedName("PontoEntrega")
    private List<PontoEntregaResponse> pontoEntregaList = new ArrayList<>();

    @SerializedName("Medidor")
    private List<MedidorResponse> medidorResponseList;

    @SerializedName("VaosPrimarios")
    private List<VaoPrimarioResponse> vaoPrimarioList;

    @SerializedName("Afloramentos")
    private List<AfloramentoResponse> afloramentoList = new ArrayList<>();

    @SerializedName("VaosPontoPoste")
    private List<VaosPontoPosteResponse> vaosPontoPosteList = new ArrayList<>();

    @SerializedName("Quadras")
    private List<QuadraResponse> quadraList = new ArrayList<>();

    @SerializedName(("Anotacao"))
    private  List<AnotacaoResponse> anotacaoList = new ArrayList<>();

    @SerializedName(("Strand"))
    private  List<DemandaStrandResponse> demandaStrandList = new ArrayList<>();

    @SerializedName("FinalizadoColaborador")
    private boolean ColaboradorFinalizou;

    public OfflineServiceOrderResponse getOfflineServiceOrder() {
        return serviceOrder;
    }

    public ServiceOrder getServiceOrder() {
        ServiceOrderResponse order = new ServiceOrderResponse();
        order.setId(serviceOrder.getId());
        order.setNumber(serviceOrder.getNumber());
        order.setPolygons(serviceOrder.getPolygons());
        order.setStatus(serviceOrder.getStatus());

        return new ServiceOrder(order);
    }

    public boolean isColaboradorFinalizou() {
        return ColaboradorFinalizou;
    }

    public void setColaboradorFinalizou(boolean colaboradorFinalizou) {
        ColaboradorFinalizou = colaboradorFinalizou;
    }

    public ServiceOrderDetails getServiceOrderDetails() {
        ServiceOrderDetailsResponse order = new ServiceOrderDetailsResponse();
        order.setId(serviceOrder.getId());
        order.setNumber(serviceOrder.getNumber());
        order.setFinishDateTime(serviceOrder.getFinishDateTime());
        order.setStartDateTime(serviceOrder.getStartDateTime());
        order.setTotalPosts(serviceOrder.getTotalPosts());
        order.setUserName(serviceOrder.getUserName());

        return new ServiceOrderDetails(order);
    }

    public List<DemandaStrand> getDemandaStrandList() {
        List<DemandaStrand> result = new ArrayList<>(demandaStrandList.size());
        for(DemandaStrandResponse response : demandaStrandList){
            result.add(new DemandaStrand(response));
        }
        return result;
    }

    public void setDemandaStrandList(List<DemandaStrand> demandaStrandList) {

        List<DemandaStrandResponse> list = new ArrayList<>(demandaStrandList.size());
        for(DemandaStrand strand : demandaStrandList){
            list.add(new DemandaStrandResponse(strand));
        }
        this.demandaStrandList = list;
    }

    public List<Anotacao> getAnotacaoList() {

        List<Anotacao> result = new ArrayList<>(anotacaoList.size());
        for(AnotacaoResponse response : anotacaoList){
            result.add(new Anotacao(response));
        }
        return result;
    }

    public void setAnotacaoList(List<Anotacao> anotacaoList) {
        List<AnotacaoResponse> list = new ArrayList<>(anotacaoList.size());
        for(Anotacao anotacao :  anotacaoList){
            list.add(new AnotacaoResponse(anotacao));
        }
        this.anotacaoList = list;
    }

    public List<Quadra> getQuadraList() {
        List<Quadra> result = new ArrayList<>(quadraList.size());
        for(QuadraResponse response : quadraList){
            result.add(new Quadra(response));
        }
        return result;
    }

    public void setQuadraList(List<Quadra> quadraList) {
        List<QuadraResponse> list = new ArrayList<>(quadraList.size());
        for(Quadra quadra :  quadraList){
            list.add(new QuadraResponse(quadra));
        }
        this.quadraList = list;
    }

    public List<Post> getPosts() {
        List<Post> result = new ArrayList<>(posts.size());
        for (PostResponse resp : posts) {
            result.add(new Post(resp));
        }

        return result;
    }

    public List<Afloramento> getAfloramentoList() {

        List<Afloramento> result = new ArrayList<>(afloramentoList.size());

        for (AfloramentoResponse resp : afloramentoList) {
            result.add(new Afloramento(resp));
        }

        return result;
    }


    public void setAfloramentoList(List<Afloramento> afloramentoList) {

        List<AfloramentoResponse> list = new ArrayList<>(afloramentoList.size());

        for (Afloramento afloramento : afloramentoList) {
            list.add(new AfloramentoResponse(afloramento));
        }

        this.afloramentoList = list;
    }

    public List<VaosPontoPoste> getVaosPontoPosteList() {

        if (vaoPrimarioList == null) {
            vaoPrimarioList = new ArrayList<>();
        }
        List<VaosPontoPoste> result = new ArrayList<>(vaosPontoPosteList.size());

        for (VaosPontoPosteResponse resp : vaosPontoPosteList) {
            result.add(new VaosPontoPoste(resp));
        }

        return result;
    }

    public void setVaosPontoPosteList(List<VaosPontoPoste> vaosPontoPosteList) {

        List<VaosPontoPosteResponse> list = new ArrayList<>(vaosPontoPosteList.size());

        for (VaosPontoPoste vaosPontoPoste : vaosPontoPosteList) {
            list.add(new VaosPontoPosteResponse(vaosPontoPoste));
        }

        this.vaosPontoPosteList = list;
    }

    public List<Lighting> getLightingList() {

        List<Lighting> result = new ArrayList<>(lightingList.size());

        for (LightingResponse resp : lightingList) {
            result.add(new Lighting(resp));
        }

        return result;
    }

    public List<PontoEntrega> getPontoEntregaList() {

        List<PontoEntrega> result = new ArrayList<>(pontoEntregaList.size());

        for (PontoEntregaResponse resp : pontoEntregaList) {

            PontoEntrega entrega = new PontoEntrega();
            List<PontoEntregaPhotos> listFotos = new ArrayList<>();

            for(PontoEntregaPhotosResponse photos : resp.getPhotos()){
                listFotos.add(new PontoEntregaPhotos(photos));
            }
            entrega.setPhotos(listFotos);

            entrega.setNumero_andares_edificio(resp.getNumero_andares_edificio());
            entrega.setNome_edificio(resp.getNome_edificio());
            entrega.setNumero_total_apartamentos(resp.getNumero_total_apartamentos());
            entrega.setNumero_local(resp.getNumero_local());
            entrega.setPostId(resp.getPostId());
            entrega.setComplemento2(resp.getComplemento2());
            entrega.setTipoDemanda(resp.getTipoDemanda());
            entrega.setClassificacao(resp.getClassificacao());
            entrega.setCodigo_bd_geo(resp.getCodigo_bd_geo());
            entrega.setComplemento1(resp.getComplemento1());
            entrega.setExcluido(resp.isExcluido());
            entrega.setGeoCode(resp.getCodigo_bd_geo());
            /*PontoEntregaLocation pontoEntregaLocation = new PontoEntregaLocation();
            pontoEntregaLocation.setLat(resp.getX());
            pontoEntregaLocation.setLon(resp.getY());
            entrega.setLocation(pontoEntregaLocation);*/
            entrega.setX(resp.getX());
            entrega.setY(resp.getY());
            entrega.setY_atualizacao(resp.getY_atualizacao());
            entrega.setX_atualizacao(resp.getX_atualizacao());
            entrega.setId(resp.getId());
            entrega.setOrderId(resp.getOrderId());
            entrega.setPostNumber(resp.getPostNumber());
            entrega.setTipoImovel(resp.getTipoImovel());
            entrega.setStatus(resp.getStatus());
            entrega.setUpdate(resp.isUpdate());
            entrega.setQtd_domicilio(resp.getQtd_domicilio());
            entrega.setTipo_comercio(resp.getTipo_comercio());
            entrega.setQtd_salas(resp.getQtd_salas());
            entrega.setQta_blocos(resp.getQtd_blocos());
            entrega.setOcorrencia(resp.getOcorrencia());
            result.add(entrega);
        }

        return result;
    }

    public List<Medidor> getMedidorList() {

        List<Medidor> result = new ArrayList<>(medidorResponseList.size());

        for (MedidorResponse resp : medidorResponseList) {
            result.add(new Medidor(resp));
        }
        return result;
    }

    public List<VaoPrimario> getVaoPrimarioList() {

        List<VaoPrimario> result = new ArrayList<>(vaoPrimarioList.size());

        for (VaoPrimarioResponse resp : vaoPrimarioList) {
            result.add(new VaoPrimario(resp));
        }

        return result;
    }

    public void setServiceOrder(OfflineServiceOrderResponse serviceOrder) {
        this.serviceOrder = serviceOrder;
    }

    public void setPostResponseList(List<PostResponse> posts) {
        this.posts = posts;
    }

    public void setPostList(List<Post> posts) {

        List<PostResponse> list = new ArrayList<>(posts.size());

        for (Post post : posts) {
            list.add(new PostResponse(post));
        }

        this.posts = list;
    }

    public void setLightingList(List<Lighting> lightingList) {

        List<LightingResponse> list = new ArrayList<>(lightingList.size());

        for (Lighting lighting : lightingList) {
            list.add(new LightingResponse(lighting));
        }

        this.lightingList = list;
    }

    public void setPontoEntregaList(List<PontoEntrega> pontoEntregaList) {

        List<PontoEntregaResponse> list = new ArrayList<>(pontoEntregaList.size());

        for (PontoEntrega pontoEntrega : pontoEntregaList) {
            list.add(new PontoEntregaResponse(pontoEntrega));
        }

        this.pontoEntregaList = list;
    }

    public void setVaoPrimarioList(List<VaoPrimario> vaoPrimarioList) {

        List<VaoPrimarioResponse> list = new ArrayList<>(vaoPrimarioList.size());

        for (VaoPrimario vaoPrimario : vaoPrimarioList) {
            list.add(new VaoPrimarioResponse(vaoPrimario));
        }

        this.vaoPrimarioList = list;
    }
}