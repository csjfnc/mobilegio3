package com.visium.fieldservice.ui.util;

import android.content.Context;

import com.visium.fieldservice.entity.Equipment;
import com.visium.fieldservice.entity.PontoAtualizacao;
import com.visium.fieldservice.entity.PostLocation;
import com.visium.fieldservice.util.GeoUtils;

/**
 * Created by ltonon on 13/01/2017.
 */

public class EquipmentUpdateUtils {

    public static void setPontoAtualizacao(Context ctx, Equipment equipment) {
        double[] t = GeoUtils.getCurrentPosition(ctx);
        long date = System.currentTimeMillis()/1000;
        equipment.setPontoAtualizacao(new PontoAtualizacao(t[0], t[1], date));
    }
}
