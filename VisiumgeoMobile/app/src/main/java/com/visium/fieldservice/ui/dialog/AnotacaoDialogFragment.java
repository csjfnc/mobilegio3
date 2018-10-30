package com.visium.fieldservice.ui.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.memoria.DownloadOrderOffline;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.entity.Anotacao;
import com.visium.fieldservice.entity.Lagradouro;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.LogUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fjesus on 24/01/2018.
 */

public class AnotacaoDialogFragment extends AppCompatDialogFragment {

    private static Anotacao mAnotacao;
    private static AnotacaoDialogListener mListener;
    private static LatLng mUserLatLng;
    private static boolean mCreating;
    private static Activity activity;
    private EditText edt_anotacao;
    private Button save_button, cancel_button;


    public static AppCompatDialogFragment newInstance(AnotacaoDialogListener listener, Anotacao anotacao) {
        AnotacaoDialogFragment.mAnotacao = anotacao;
        AnotacaoDialogFragment.mListener = listener;
        return new AnotacaoDialogFragment();
    }

    public static AppCompatDialogFragment newInstance(Activity activity, AnotacaoDialogListener listener, Anotacao anotacao, LatLng mUserLatLng) {
        AnotacaoDialogFragment.mAnotacao = anotacao;
        AnotacaoDialogFragment.mListener = listener;
        AnotacaoDialogFragment.mCreating = mUserLatLng != null;
        AnotacaoDialogFragment.activity = activity;
        AnotacaoDialogFragment.mUserLatLng = mUserLatLng;
        if (AnotacaoDialogFragment.mCreating) {
            AnotacaoDialogFragment.mUserLatLng = mUserLatLng;
        }
        return new AnotacaoDialogFragment();
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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.anotacao_layout_fragment, container);
        edt_anotacao = (EditText) view.findViewById(R.id.edt_anotacao);
        save_button = (Button) view.findViewById(R.id.save_button);
        cancel_button = (Button) view.findViewById(R.id.cancel_button);

        if(!mCreating){
            edt_anotacao.setText(mAnotacao.getAnotacao());
        }

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String anotaocao = edt_anotacao.getText().toString().toUpperCase();
                if(anotaocao.equals("") || anotaocao == null){
                    edt_anotacao.setError("Campo Obrigatório");
                }else{
                    mAnotacao.setAnotacao(anotaocao);
                    boolean workingOffline = FileUtils.serviceOrderFileExists(mAnotacao.getOrderId());
                    if (mCreating) {

                        if (workingOffline) {

                            try {
                              //  OfflineDownloadResponse download = FileUtils.retrieve(mAnotacao.getOrderId());
                                OfflineDownloadResponse download = null;
                                if(DownloadOrderOffline.getResponse() == null){
                                    DownloadOrderOffline.setResponse(FileUtils.retrieve(mAnotacao.getOrderId()));
                                    download = DownloadOrderOffline.getResponse();
                                }
                                else{
                                    download = DownloadOrderOffline.getResponse();
                                }
                                List<Anotacao> anotacaos = download.getAnotacaoList();

                                mAnotacao.setId(-1 * System.currentTimeMillis());

                                anotacaos.add(mAnotacao);

                                download.setAnotacaoList(anotacaos);

                                if (FileUtils.saveOfflineDownload(download)) {

                                    Toast.makeText(getActivity(),
                                            R.string.dialog_post_edit_saving_success,
                                            Toast.LENGTH_LONG).show();
                                    dismissAllowingStateLoss();
                                    mListener.onAnotacaoChanged(mAnotacao, true, false);

                                } else {
                                    Toast.makeText(getActivity(),"Não foi possivel Salvar a Anotação",
                                            Toast.LENGTH_LONG).show();
                                }

                            } catch (IOException e) {
                                //mPontoEntrega.setClosed(false);
                                //alertDialog.dismiss();
                                Toast.makeText(getActivity(),
                                        getString(R.string.dialog_post_edit_saving_error,
                                                mAnotacao.getId()),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }else{
                        if (workingOffline) {

                            try {
                               // OfflineDownloadResponse download = FileUtils.retrieve(mAnotacao.getOrderId());
                                OfflineDownloadResponse download = null;
                                if(DownloadOrderOffline.getResponse() == null){
                                    DownloadOrderOffline.setResponse(FileUtils.retrieve(mAnotacao.getOrderId()));
                                    download = DownloadOrderOffline.getResponse();
                                }
                                else{
                                    download = DownloadOrderOffline.getResponse();
                                }
                                List<Anotacao> anotacaoList = download.getAnotacaoList();

                                Iterator<Anotacao> iterator = anotacaoList.iterator();

                                while (iterator.hasNext()) {
                                    Anotacao anotacao = iterator.next();
                                    if (anotacao.getId() == mAnotacao.getId()) {
                                        iterator.remove();
                                        break;
                                    }
                                }
                                mAnotacao.setUpdate(true);
                                anotacaoList.add(mAnotacao);
                                download.setAnotacaoList(anotacaoList);

                                if (FileUtils.saveOfflineDownload(download)) {
                                    Toast.makeText(getActivity(),"Anotação Salva",
                                            Toast.LENGTH_LONG).show();
                                    dismissAllowingStateLoss();
                                    mListener.onAnotacaoChanged(mAnotacao, false, false);

                                } else {
                                    Toast.makeText(getActivity(),"Erro ao salvar a anotação",
                                            Toast.LENGTH_LONG).show();
                                }
                            //    alertDialog.dismiss();

                            } catch (IOException e) {
                                LogUtils.error(this, e);
                             //   alertDialog.dismiss();

                                Toast.makeText(getActivity(),"Erro ao salvar a anotação",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                }
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
}
