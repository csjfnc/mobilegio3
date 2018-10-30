package com.visium.fieldservice.ui.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.controller.PostController;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.PontoAtualizacao;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.ui.maps.MapsPostsActivity;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.LogUtils;
import com.visium.fieldservice.verifier.Verifier;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class PontoActionsDialogFragment extends AppCompatDialogFragment {

    private static PontoEntrega mPontoEntrega;
    private static DemandatActionsDialogListener mListener;
    private Button delete_demanda;

    public static AppCompatDialogFragment newInstance(DemandatActionsDialogListener listener, PontoEntrega entrega) {
        PontoActionsDialogFragment.mPontoEntrega = entrega;
        PontoActionsDialogFragment.mListener = listener;
        return new PontoActionsDialogFragment();
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
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_pontoentrega_actions, container, false);
        delete_demanda = (Button) view.findViewById(R.id.delete_demanda);
        delete_demanda.setText(mPontoEntrega.isExcluido() ? "Restaurar Demanda" : "Apagar");
        ((TextView) view.findViewById(R.id.post_actions_title))
                .setText(getString(R.string.dialog_demanda_actions_title, mPontoEntrega.getGeoCode() + " / " + mPontoEntrega.getPostNumber()));

        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

        /*view.findViewById(R.id.edit_list_lighting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onListLightingPostButtonClicked(mPost);
            }
        });*/

        //view.findViewById(R.id.edit_pontoentrega).setClickable(false);
        /*view.findViewById(R.id.edit_pontoentrega).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onListPontoEntregaButtonClicked(mPontoEntrega);
            }
        });*/

        view.findViewById(R.id.edit_local_demanda).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onEditLocalDemandaButtonClicked(mPontoEntrega);
                dismissAllowingStateLoss();
            }
        });

        view.findViewById(R.id.edit_demanda).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mListener.onEditDemandaButtonClicked(mPontoEntrega);
            }
        });

        delete_demanda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onToggleDeleteDemandaButtonClicked(mPontoEntrega);
            }
        });

       /* view.findViewById(R.id.edit_afloramento).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onListAfloramentosButtonClicked(mPost);
            }
        });*/

       /* view.findViewById(R.id.edit_banco_capacitador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onListBancoCapacitadorButtonClicked(mPost);
            }
        });*/

        view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mPontoEntrega.isClosed()) {
                    mPontoEntrega.setClosed(true);
                    mPontoEntrega.setUpdate(true);
                    Location l = ((MapsPostsActivity) mListener).getLastLocation();
                 //   mPontoEntrega.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis() / 1000));
                    mPontoEntrega.setX_atualizacao(l.getLatitude());
                    mPontoEntrega.setY_atualizacao(l.getLongitude());
                    mPontoEntrega.setUpdate(true);
                    final ProgressDialog alertDialog = new ProgressDialog(getActivity(), R.style.AlertDialogTheme);
                    alertDialog.setTitle(getString(R.string.dialog_post_edit_saving_title, mPontoEntrega.getGeoCode()));
                    alertDialog.setMessage(getString(R.string.dialog_post_edit_saving_message));
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                    boolean workingOffline = FileUtils.serviceOrderFileExists(mPontoEntrega.getOrderId());
                    if (workingOffline) {

                        try {
                            OfflineDownloadResponse download = FileUtils.retrieve(mPontoEntrega.getOrderId());
                            List<PontoEntrega> pontoEntregas = download.getPontoEntregaList();

                            Iterator<PontoEntrega> iterator = pontoEntregas.iterator();

                            while (iterator.hasNext()) {
                                PontoEntrega pontoEntrega = iterator.next();
                                if (pontoEntrega.getId() == mPontoEntrega.getId()) {
                                    iterator.remove();
                                    break;
                                }
                            }

                            pontoEntregas.add(mPontoEntrega);
                            download.setPontoEntregaList(pontoEntregas);

                            if (FileUtils.saveOfflineDownload(download)) {
                                Verifier.addPostCount(mPontoEntrega.getGeoCode());
                                Toast.makeText(getActivity(),
                                        R.string.dialog_post_edit_saving_success,
                                        Toast.LENGTH_LONG).show();
                                alertDialog.dismiss();
                            //    ((PontoEntregaEditDialogListener) mListener).onPontoEntegasChanged(mPontoEntrega, false);
                                dismissAllowingStateLoss();

                            } else {
                                mPontoEntrega.setClosed(false);
                                alertDialog.dismiss();
                                Toast.makeText(getActivity(),
                                        getString(R.string.dialog_post_edit_saving_error,
                                                mPontoEntrega.getId()),
                                        Toast.LENGTH_LONG).show();
                            }

                            alertDialog.dismiss();

                        } catch (IOException e) {
                            mPontoEntrega.setClosed(false);
                            LogUtils.error(this, e);
                            alertDialog.dismiss();

                            Toast.makeText(getActivity(),
                                    getString(R.string.dialog_post_edit_saving_error,
                                            mPontoEntrega.getId()),
                                    Toast.LENGTH_LONG).show();
                        }

                    } else {
                        /*PostController.get().save(getContext(), mPost, new VisiumApiCallback<Post>() {
                            @Override
                            public void callback(Post post, ErrorResponse e) {
                                alertDialog.dismiss();

                                if (e == null && post != null) {
                                    Verifier.addPostCount(mPost.getGeoCode());
                                    Toast.makeText(getActivity(),
                                            R.string.dialog_post_edit_saving_success,
                                            Toast.LENGTH_LONG).show();
                                    ((PostEditDialogListener) mListener).onPostChanged(mPost, false);
                                    dismissAllowingStateLoss();
                                } else if (e != null && e.isCustomized()) {
                                    mPost.setClosed(false);
                                    Toast.makeText(getActivity(),
                                            e.getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    mPost.setClosed(false);
                                    Toast.makeText(getActivity(),
                                            R.string.dialog_post_edit_saving_error,
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });*/
                    }
                } else
                    dismissAllowingStateLoss();
            }
        });

        return view;
    }

}