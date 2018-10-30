package com.visium.fieldservice.ui.dialog;

import com.visium.fieldservice.entity.Post;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public interface PostActionsDialogListener {

    void onEditPostButtonClicked(Post post);
    void onEditLocalButtonClicked(Post post);

    void onListLightingPostButtonClicked(Post post);
    void onToggleDeletePostButtonClicked(Post post);
    void onListPontoEntregaButtonClicked(Post post);
    void onListAfloramentosButtonClicked(Post post);
    void onListBancoCapacitadorButtonClicked(Post post);

}