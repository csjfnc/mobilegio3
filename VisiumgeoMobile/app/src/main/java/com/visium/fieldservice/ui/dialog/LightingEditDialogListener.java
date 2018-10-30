package com.visium.fieldservice.ui.dialog;

import android.location.Location;

import com.visium.fieldservice.entity.Lighting;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public interface LightingEditDialogListener {

    void onLightingItemChanged(Lighting lighting);
    Location getLastLocation();

}
