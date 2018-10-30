package com.visium.fieldservice.ui.dialog;

import android.location.Location;

import com.visium.fieldservice.entity.Lighting;
import com.visium.fieldservice.entity.Post;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public interface MedidorListDialogListener {

    void onAddMedidorButtonClicked(Post post);

    void onEditMedidorItemClicked(Lighting lighting);
    void onMedidorDeleteItemClicked(Post post, Lighting lighting);
    Location getLastLocation();

}
