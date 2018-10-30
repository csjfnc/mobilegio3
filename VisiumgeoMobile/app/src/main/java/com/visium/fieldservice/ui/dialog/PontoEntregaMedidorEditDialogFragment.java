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
import android.widget.EditText;
import android.widget.Toast;

import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.MedidorResponse;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.controller.PontoEntregaController;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.equipament.EquipmentType;
import com.visium.fieldservice.ui.common.EquipmentEditDialogListener;
import com.visium.fieldservice.ui.maps.MapsPostsActivity;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.LogUtils;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PontoEntregaMedidorEditDialogFragment extends AppCompatDialogFragment {

    private static PontoEntrega mPontoEntrega;
    private List<MedidorResponse> medidorList;
    private MedidorResponse medidor;
    private static EquipmentEditDialogListener mListener;
    private static Post mPost;
    private static long mOrderId;

    private EditText mNumero, mComplemento;
    
    public static AppCompatDialogFragment newInstance(EquipmentEditDialogListener listener,
                                                      PontoEntrega pontoEntrega, Post post, long mOrderId) {
        PontoEntregaMedidorEditDialogFragment.mPontoEntrega = pontoEntrega;
        PontoEntregaMedidorEditDialogFragment.mListener = listener;
        PontoEntregaMedidorEditDialogFragment.mPost = post;
        PontoEntregaMedidorEditDialogFragment.mOrderId = mOrderId;
        return new PontoEntregaMedidorEditDialogFragment();
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

        View view = inflater.inflate(R.layout.fragment_pontoentrega_medidor_edit, container, false);


        mNumero = (EditText)view.findViewById(R.id.edit_numero);
        mComplemento = (EditText)view.findViewById(R.id.edit_complemento);
     //   medidorList = new ArrayList<>();


        //medidorList.add(medidor);


     /*   if (mPontoEntrega == null) {
            mPontoEntrega = new PontoEntrega();
            mPontoEntrega.setPostId(mPost.getId());
            mPontoEntrega.setUpdate(true);
            ((TextView) view.findViewById(R.id.title))
                    .setText("Criar");
        } else {
            ((TextView) view.findViewById(R.id.title))
                    .setText("Editar " + mPontoEntrega.getId());
//
            mStatus.setSelection(
                    PontoEntregaConfiguration.getEnum(PontoEntregaConfiguration.Names.STATUS).getEnums()
                            .get(mPontoEntrega.getStatus()).getKey());
            mClasseAtendimento.setSelection(
                    PontoEntregaConfiguration.getEnum(PontoEntregaConfiguration.Names.CLASSE_DE_ATENDIMENTO).getEnums()
                            .get(mPontoEntrega.getClasseAtendimento()).getKey());
            mTipoConstrucao.setSelection(
                    PontoEntregaConfiguration.getEnum(PontoEntregaConfiguration.Names.TIPO_DE_CONSTRUCAO).getEnums()
                            .get(mPontoEntrega.getTipoConstrucao()).getKey());

            //mClasseSocial.setText(String.valueOf(mPontoEntrega.getClasseSocial()));//

        }*/

        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

        view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mNumero.getText().toString().equals("")){
                    Toast.makeText(getContext(), "Digite pelo menos um numero", Toast.LENGTH_LONG).show();
                    return;
                }

                LogUtils.log("Post Id : "+mPost.getId() + " PontoEntregaPostId : "+mPontoEntrega.getPostId());


             //   mPontoEntrega.setClasseSocial(ViewUtils.getTextViewValue(mClasseSocial));

                medidor = new MedidorResponse();
                medidor.setComplementoResidencial(mComplemento.getText().toString());
                medidor.setNumeroMedidor(mNumero.getText().toString());
                medidor.setData_cadastro(new Date());
                mPontoEntrega.setUpdate(true);
                medidor.setIdMedidor(-1 * System.currentTimeMillis() / 1000);
                medidor.setIdPontoEntrega(mPontoEntrega.getId());
                medidor.setUpdate(true);
           //     mPontoEntrega.getMedidor().add(medidor);
                Location l = ((MapsPostsActivity)mListener).getLastLocation();

                final ProgressDialog alertDialog = new ProgressDialog(getActivity(), R.style.AlertDialogTheme);
                alertDialog.setTitle("Salvando");
                alertDialog.setMessage("Por favor aguarde");
                alertDialog.setCancelable(false);
                alertDialog.show();

                VisiumApiCallback<PontoEntrega> callback =
                        new VisiumApiCallback<PontoEntrega>() {
                            private static final long serialVersionUID = 3656333712718352037L;

                            @Override
                            public void callback(PontoEntrega pontoEntrega, ErrorResponse e) {

                                alertDialog.dismiss();

                                if (pontoEntrega != null && e == null) {
                                    Toast.makeText(getContext(), "Equipamento salvo com sucesso",
                                            Toast.LENGTH_LONG).show();

                                    mListener.onEquipmentItemChanged(EquipmentType.PONTO_DE_ENTREGA, pontoEntrega);
                                    dismissAllowingStateLoss();
                                } else if (e != null && e.isCustomized()) {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getContext(), "Erro salvando equipamento",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        };

                /*if (mPontoEntrega.getId() > 0) {
                    PontoEntregaController.get().save(getContext(),mPontoEntrega, callback);
                } else {
                    PontoEntregaController.get().create(getContext(),mPontoEntrega, callback);
                }*/

                /*Versão 3 */

                if (FileUtils.serviceOrderFileExists(mOrderId)) {
                    if (mPontoEntrega.getId() > 0) {
                        try {
                            OfflineDownloadResponse downloaded = FileUtils.retrieve(mOrderId);

                            List<PontoEntrega> pontoEntregaList = downloaded.getPontoEntregaList();

                            Iterator<PontoEntrega> iterator = pontoEntregaList.iterator();
/*
                            while (iterator.hasNext()) {
                                PontoEntrega pontoEntrega = iterator.next();

                                if (pontoEntrega.getId() == mPontoEntrega.getId()) {
                                    iterator.remove();
                                    break;
                                }
                            }*/

                            int i = 0;
                            PontoEntrega pontoEntrega = null;

                            while (iterator.hasNext()) {
                                pontoEntrega = iterator.next();

                                if (pontoEntrega.getId() == mPontoEntrega.getId()) {
                                    // pontoEntrega.getMedidor().add(medidor);
                                    pontoEntregaList.get(i).setUpdate(true);
                                  //  pontoEntregaList.get(i).getMedidor().add(medidor);
                                    break;
                                }
                                //// TODO: 22/05/2017
                                ++i;
                            }

                       //     pontoEntregaList.add(mPontoEntrega);
                            downloaded.setPontoEntregaList(pontoEntregaList);

                            if (FileUtils.saveOfflineDownload(downloaded)) {
                                callback.callback(mPontoEntrega, null);
                            } else {
                                callback.callback(null, null);
                            }

                        } catch (IOException e) {
                            LogUtils.error(this, e);
                            callback.callback(null, null);
                        }

                    } else {
                        try {

                            OfflineDownloadResponse downloaded = FileUtils.retrieve(mOrderId);
                            List<PontoEntrega> pontoEntregaList = downloaded.getPontoEntregaList();

                            /*for(int a = -1; a <= pontoEntregaList.size(); a++){
                                if(mPontoEntrega.getPostId() == mPost.getId()){
                                    //mPontoEntrega.getMedidor().add(medidor);
                                    downloaded.getPontoEntregaList().get(a).getMedidor().add(medidor);
                                }
                            }*/

                            int i = 0;
                          //  for(PontoEntrega pontoEntrega : pontoEntregaList){

                                    for(PontoEntrega pontoEntrega : pontoEntregaList){
                                        if(pontoEntrega.getId() == mPontoEntrega.getId()){
                                  //          downloaded.getPontoEntregaList().get(i).getMedidor().add(medidor);
                                        }
                                        ++i;
                                    }

                          //  }
                 //           downloaded.getPontoEntregaList().get(i).getMedidor().add(medidor);


                            mPontoEntrega.setId(-1 * System.currentTimeMillis() / 1000);
                         //   pontoEntregaList.add(mPontoEntrega);


                            if (FileUtils.saveOfflineDownload(downloaded)) {
                                callback.callback(mPontoEntrega, null);
                            } else {
                                callback.callback(null, null);
                            }

                        } catch (IOException e) {
                            LogUtils.error(this, e);
                            callback.callback(null, null);
                        }
                    }
                }
                else {
                    if (mPontoEntrega.getId() > 0) {
                        PontoEntregaController.get().save(getContext(), mPontoEntrega, callback);
                    } else {
                        PontoEntregaController.get().create(getContext(), mPontoEntrega, callback);
                    }
                }
                /*Fim Versão 3 */
            }

        });

        return view;
    }
}