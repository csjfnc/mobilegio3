package com.visium.fieldservice.ui.dialog;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.VaosPontoPoste;
import com.visium.fieldservice.ui.common.MapLocationListener;

import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public interface PontoEntregaEditDialogListener extends MapLocationListener {

    void onPontoEntegasChanged(PontoEntrega pontoEntrega, boolean creating, Post post, boolean deletarPlyline, boolean podeTracar);
    void onPostDelete(Post post);
    List<PontoEntrega> getPontoEntregasList();
    List<LatLng> getOrderPoints();
    int getNextPostNumber();
    int getHighestPostNumber();

}
