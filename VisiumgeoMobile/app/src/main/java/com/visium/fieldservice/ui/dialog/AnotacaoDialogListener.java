package com.visium.fieldservice.ui.dialog;

import com.visium.fieldservice.entity.Anotacao;

/**
 * Created by fjesus on 24/01/2018.
 */

public interface AnotacaoDialogListener {

    void onAnotacaoChanged(Anotacao anotacao, boolean creating, boolean deletar);
    void onAnotacaoUpdateChaged(Anotacao anotacao);
    void onAnotacaoDelete(Anotacao anotacao);
}
