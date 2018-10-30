package com.visium.fieldservice.ui.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.configuration.AfloramentoConfiguration;
import com.visium.fieldservice.controller.AfloramentoController;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.equipament.Afloramento;
import com.visium.fieldservice.entity.equipament.EquipmentType;
import com.visium.fieldservice.ui.common.EquipmentEditDialogListener;
import com.visium.fieldservice.ui.dialog.adapter.EnumsAdapter;
import com.visium.fieldservice.ui.maps.MapsPostsActivity;
import com.visium.fieldservice.util.LogUtils;

import java.util.List;

public class AfloramentoEditDialogFragment extends AppCompatDialogFragment {

    private static Afloramento mAfloramento;
    private static EquipmentEditDialogListener mListener;
    private static Post mPost;
    private static boolean isCreating = false;
    private Spinner mStatus, mProprietario;
    private static LatLng mLatLng;
    private Afloramento afloramento;
    private static long mOrderId;
    
    public static AppCompatDialogFragment newInstance(MapsPostsActivity listener,
                                                      Afloramento afloramento, Post post) {
        AfloramentoEditDialogFragment.mAfloramento = afloramento;
        AfloramentoEditDialogFragment.mListener = listener;
        AfloramentoEditDialogFragment.mPost = post;
        return new AfloramentoEditDialogFragment();
    }

    public static AppCompatDialogFragment newInstance(MapsPostsActivity listener,
                                                      LatLng latLng, Post post, long mOrderId) {
        LogUtils.log("Ponto Entrega is creating true");
        isCreating = true;
        AfloramentoEditDialogFragment.mLatLng = latLng;
        AfloramentoEditDialogFragment.mListener = listener;
        AfloramentoEditDialogFragment.mPost = post;
        AfloramentoEditDialogFragment.mOrderId = mOrderId;
        mAfloramento = null; //NAO ESQUECEREI DESSA LINHA
        return new AfloramentoEditDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, 0);
    }


    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_afloramento_edit, container, false);

        mStatus = (Spinner)view.findViewById(R.id.spinner_status);
        mProprietario = (Spinner)view.findViewById(R.id.spinner_proprietario);



        mStatus.setAdapter(
                new EnumsAdapter(getContext(), android.R.layout.simple_spinner_item,
                        AfloramentoConfiguration.getEnum(AfloramentoConfiguration.Names.STATUS)));
        mStatus.setSelection(0);
/*
        mProprietario.setAdapter(
                new EnumsAdapter(getContext(), android.R.layout.simple_spinner_item,
                        AfloramentoConfiguration.getEnum(AfloramentoConfiguration.Names.PROPRIETARIO)));
        mProprietario.setSelection(0);*/

        if (mAfloramento == null) {
            mAfloramento = new Afloramento();
            mAfloramento.setPostId(mPost.getId());
            ((TextView) view.findViewById(R.id.title))
                    .setText("Criar");
        } else {
            ((TextView) view.findViewById(R.id.title))
                    .setText("Editar " + mAfloramento.getId());

            mStatus.setSelection(
                    AfloramentoConfiguration.getEnum(AfloramentoConfiguration.Names.STATUS).getEnums()
                            .get(mAfloramento.getStatus()).getKey());
            mProprietario.setSelection(
                    AfloramentoConfiguration.getEnum(AfloramentoConfiguration.Names.PROPRIETARIO).getEnums()
                            .get(mAfloramento.getProprietario()).getKey());
        }

        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

        view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mAfloramento.setStatus(((EnumsAdapter)mStatus.getAdapter()).getKey(mStatus.getSelectedItemPosition()));
                mAfloramento.setProprietario(((EnumsAdapter)mProprietario.getAdapter()).getKey(mProprietario.getSelectedItemPosition()));

                final ProgressDialog alertDialog = new ProgressDialog(getActivity(), R.style.AlertDialogTheme);
                alertDialog.setTitle("Salvando");
                alertDialog.setMessage("Por favor aguarde");
                alertDialog.setCancelable(false);
                alertDialog.show();

                VisiumApiCallback<Afloramento> callback =
                        new VisiumApiCallback<Afloramento>() {
                            private static final long serialVersionUID = 3656333712718352037L;

                            @Override
                            public void callback(Afloramento afloramento, ErrorResponse e) {

                                alertDialog.dismiss();

                                if (afloramento != null && e == null) {
                                    Toast.makeText(getContext(), "Equipamento salvo com sucesso",
                                            Toast.LENGTH_LONG).show();

                                    mListener.onEquipmentItemChanged(EquipmentType.AFLORAMENTO, afloramento);
                                    dismissAllowingStateLoss();
                                } else if (e != null && e.isCustomized()) {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), "Erro salvando equipamento",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        };

                if (mAfloramento.getId() > 0) {
                    AfloramentoController.get().save(getContext(),mAfloramento, callback);
                } else {
                    AfloramentoController.get().create(getContext(),mAfloramento, callback);
                }
            }

        });

        return view;
    }
}