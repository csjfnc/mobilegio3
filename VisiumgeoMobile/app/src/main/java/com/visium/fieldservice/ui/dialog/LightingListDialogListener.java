package com.visium.fieldservice.ui.dialog;

import android.location.Location;

import com.visium.fieldservice.entity.Lighting;
import com.visium.fieldservice.entity.Post;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public interface LightingListDialogListener {

    void onAddLightingButtonClicked(Post post);

    void onEditLightingItemClicked(Lighting lighting);
    void onDeleteItemClicked(Post post, Lighting lighting);
    Location getLastLocation();

}
