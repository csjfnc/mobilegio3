package com.visium.fieldservice.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.Anotacao;

/**
 * Created by fjesus on 26/01/2018.
 */

public class AnotacaoActionDialogFrament extends AppCompatDialogFragment {

    private static Anotacao mAnotacao;
    private static AnotacaoDialogListener mListener;
    private Button edit_anotacao, delete_anotacao, save_button, cancel_button;

    public static AppCompatDialogFragment newInstance(AnotacaoDialogListener listener, Anotacao anotacao){
        AnotacaoActionDialogFrament.mAnotacao = anotacao;
        AnotacaoActionDialogFrament.mListener = listener;

        return new AnotacaoActionDialogFrament();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_anotacao_actions, container, false);

        edit_anotacao = (Button) view.findViewById(R.id.edit_anotacao);
        delete_anotacao = (Button) view.findViewById(R.id.delete_anotacao);
        save_button = (Button) view.findViewById(R.id.save_button);
        cancel_button = (Button) view.findViewById(R.id.cancel_button);

        edit_anotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAnotacaoUpdateChaged(mAnotacao);
            }
        });

        delete_anotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAnotacaoDelete(mAnotacao);
            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
            }
        });

        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
            }
        });

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
