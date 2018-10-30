package com.visium.fieldservice.ui.dialog;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.visium.fieldservice.entity.DemandaStrand;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.ui.common.MapLocationListener;

import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public interface DemandaStrandEditDialogListener extends MapLocationListener {

    void onDemandaStradChanged(DemandaStrand strand, boolean mCreating, Polyline polyline);
    void onDeletarDemandaStrand(DemandaStrand strand, Polyline polyline);
    void onEditDemandaStrand(DemandaStrand strand, Polyline polyline);
}
