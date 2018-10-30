package com.visium.fieldservice.ui.dialog;

import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.Post;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public interface DemandatActionsDialogListener {

    void onEditDemandaButtonClicked(PontoEntrega pontoEntrega);
    void onToggleDeleteDemandaButtonClicked(PontoEntrega pontoEntrega);
    void onEditLocalDemandaButtonClicked(PontoEntrega pontoEntrega);

}