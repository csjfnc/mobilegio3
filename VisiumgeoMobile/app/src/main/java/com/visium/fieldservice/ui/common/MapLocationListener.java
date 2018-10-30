package com.visium.fieldservice.ui.common;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.equipament.EquipmentType;

import java.util.List;

/**
 * Created by ltonon on 20/10/2016.
 */
public interface MapLocationListener {

    Location getLastLocation();
}
