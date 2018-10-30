package com.visium.fieldservice.ui.dialog;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.ui.common.MapLocationListener;

import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public interface PostEditDialogListener extends MapLocationListener {

    void onPostChanged(Post post, boolean creating, List<PontoEntrega> pontosNovos);
    void onPostDelete(Post post);
    List<Post> getPostsList();
    List<LatLng> getOrderPoints();
    int getNextPostNumber();
    int getHighestPostNumber();

}
