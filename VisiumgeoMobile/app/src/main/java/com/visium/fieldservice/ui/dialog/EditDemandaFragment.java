package com.visium.fieldservice.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.memoria.DownloadOrderOffline;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.entity.DemandaClassficacao;
import com.visium.fieldservice.entity.DemandaDomicilio;
import com.visium.fieldservice.entity.Ocorrencia;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.QuatidadeSalas;
import com.visium.fieldservice.entity.TipoComercio;
import com.visium.fieldservice.entity.TipoDemanda;
import com.visium.fieldservice.entity.VaosPontoPoste;
import com.visium.fieldservice.ui.dialog.adapter.DemandaClassificacaoAdapter;
import com.visium.fieldservice.ui.dialog.adapter.DomicilioAdapter;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.LogUtils;
import com.visium.fieldservice.verifier.Verifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fjesus on 15/05/2018.
 */

public class EditDemandaFragment extends AppCompatDialogFragment {

    private static PontoEntrega mPontoEntrega;
    private static Activity mActivity;
    private static Post mPost;

    private EditText numero_edt, complemento_edt, nome_edificop_edt, quantidade_andares_edt, quantidade_apartamentos_edt,
            quantidade_blocos_edt;
    private Spinner tipo_spn, classificacao_spn, domicilios_spn, classificacao_comercio_spn,
            domicilios_comercio_spn, ocorrencia_spn;

    private Button save_button, cancel_button;

    public static EditDemandaFragment newInstance(Activity activity, PontoEntrega pontoEntrega) {

        EditDemandaFragment.mPontoEntrega = pontoEntrega;
        EditDemandaFragment.mActivity = activity;

        return new EditDemandaFragment();
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_pontoetrega_completoedit_fragment, container, false);

        numero_edt = (EditText) view.findViewById(R.id.numero_edt);
        complemento_edt = (EditText) view.findViewById(R.id.complemento_edt);
        nome_edificop_edt = (EditText) view.findViewById(R.id.nome_edificop_edt);
        tipo_spn = (Spinner) view.findViewById(R.id.tipo_spn);
        classificacao_spn = (Spinner) view.findViewById(R.id.classificacao_spn);
        domicilios_spn = (Spinner) view.findViewById(R.id.domicilios_spn);
        classificacao_comercio_spn = (Spinner) view.findViewById(R.id.classificacao_comercio_spn);
        domicilios_comercio_spn = (Spinner) view.findViewById(R.id.domicilios_comercio_spn);
        quantidade_andares_edt = (EditText) view.findViewById(R.id.quantidade_andares_edt);
        quantidade_apartamentos_edt = (EditText) view.findViewById(R.id.quantidade_apartamentos_edt);
        ocorrencia_spn = (Spinner) view.findViewById(R.id.ocorrencia_spn);
        quantidade_blocos_edt = (EditText) view.findViewById(R.id.quantidade_blocos_edt);

        save_button = (Button) view.findViewById(R.id.save_button);
        cancel_button = (Button) view.findViewById(R.id.cancel_button);

        numero_edt.setText(mPontoEntrega.getNumero_local()+"");
        complemento_edt.setText(mPontoEntrega.getComplemento1());
        nome_edificop_edt.setText(mPontoEntrega.getNome_edificio());
        tipo_spn.setAdapter(new ArrayAdapter<TipoDemanda>(mActivity, android.R.layout.simple_list_item_1, TipoDemanda.values()));
        tipo_spn.setSelection(mPontoEntrega.getTipoDemanda());
        classificacao_spn.setAdapter(new ArrayAdapter<DemandaClassficacao>(mActivity, android.R.layout.simple_list_item_1, DemandaClassficacao.values()));
        classificacao_spn.setSelection(mPontoEntrega.getClassificacao());
        domicilios_spn.setAdapter(new ArrayAdapter<DemandaDomicilio>(mActivity, android.R.layout.simple_list_item_1, DemandaDomicilio.values()));
        domicilios_spn.setSelection(mPontoEntrega.getQtd_domicilio());
        classificacao_comercio_spn.setAdapter(new ArrayAdapter<TipoComercio>(mActivity, android.R.layout.simple_list_item_1, TipoComercio.values()));
        classificacao_comercio_spn.setSelection(mPontoEntrega.getTipo_comercio());
        domicilios_comercio_spn.setAdapter(new ArrayAdapter<QuatidadeSalas>(mActivity, android.R.layout.simple_list_item_1, QuatidadeSalas.values()));
        domicilios_comercio_spn.setSelection(mPontoEntrega.getQtd_salas());
        quantidade_andares_edt.setText(mPontoEntrega.getNumero_andares_edificio()+"");
        quantidade_apartamentos_edt.setText(mPontoEntrega.getNumero_total_apartamentos()+"");
        ocorrencia_spn.setAdapter(new ArrayAdapter<Ocorrencia>(mActivity, android.R.layout.simple_list_item_1, Ocorrencia.values()));
        ocorrencia_spn.setSelection(mPontoEntrega.getOcorrencia());

        quantidade_blocos_edt.setText(mPontoEntrega.getQta_blocos() != null ? mPontoEntrega.getQta_blocos() : "");


        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    mPontoEntrega.setNumero_local(numero_edt.getText().toString() != null ? Integer.parseInt(numero_edt.getText().toString()) : 0);
                    mPontoEntrega.setComplemento1(complemento_edt.getText().toString());
                    mPontoEntrega.setNome_edificio(complemento_edt.getText().toString());
                    mPontoEntrega.setTipoDemanda(tipo_spn.getSelectedItemPosition());

                    mPontoEntrega.setClassificacao(classificacao_spn.getSelectedItemPosition());
                    mPontoEntrega.setQtd_domicilio(domicilios_spn.getSelectedItemPosition());
                    mPontoEntrega.setTipo_comercio(classificacao_comercio_spn.getSelectedItemPosition());
                    mPontoEntrega.setQtd_salas(domicilios_comercio_spn.getSelectedItemPosition());
                    mPontoEntrega.setNumero_andares_edificio(!quantidade_andares_edt.getText().toString().equals("") ? Integer.parseInt(quantidade_andares_edt.getText().toString()) : 0);
                    mPontoEntrega.setNumero_total_apartamentos(!quantidade_apartamentos_edt.getText().toString().equals("") ? Integer.parseInt(quantidade_apartamentos_edt.getText().toString()) : 0);
                    mPontoEntrega.setOcorrencia(ocorrencia_spn.getSelectedItemPosition());
                    mPontoEntrega.setQta_blocos(quantidade_blocos_edt.getText().toString());



                    //OfflineDownloadResponse download = FileUtils.retrieve(mPontoEntrega.getOrderId());
                    OfflineDownloadResponse download = null;
                    if(DownloadOrderOffline.getResponse() == null){
                        DownloadOrderOffline.setResponse(FileUtils.retrieve(mPontoEntrega.getOrderId()));
                        download = DownloadOrderOffline.getResponse();
                    }
                    else{
                        download = DownloadOrderOffline.getResponse();
                    }
                    List<PontoEntrega> pontoEntregas = download.getPontoEntregaList();
                 //   List<Post> postes = download.getPosts();
                  //  List<VaosPontoPoste> vaosPontoPostes = download.getVaosPontoPosteList() == null ? new ArrayList<VaosPontoPoste>() : download.getVaosPontoPosteList();
                    Iterator<PontoEntrega> iterator = pontoEntregas.iterator();

                    while (iterator.hasNext()) {
                        PontoEntrega post = iterator.next();
                        if (post.getId() == mPontoEntrega.getId()) {
                            iterator.remove();
                            break;
                        }
                    }

                    /*for (Post post : postes){
                        if(post.getId() == mPontoEntrega.getPostId()){
                            mPost = post;
                        }
                    }*/
                  /*  if (mPost != null) {
                        if (mPontoEntrega.getId() != 0) {
                            long id_ponto = mPontoEntrega.getId();
                            for (VaosPontoPoste vaosPontoPoste : vaosPontoPostes) {
                                if (vaosPontoPoste.getId_ponto_entrega() == id_ponto) {
                                    vaosDeletar = vaosPontoPoste;
                                    vaosPontoPostes.remove(vaosPontoPoste);
                                    break;
                                }
                            }
                        }
                    }*/

                    pontoEntregas.add(mPontoEntrega);
                    download.setPontoEntregaList(pontoEntregas);
                    //   download.setVaosPontoPosteList(vaosPontoPostes);

                    if (FileUtils.saveOfflineDownload(download)) {
                        Verifier.addPostCount(mPontoEntrega.getGeoCode());
                        Toast.makeText(getActivity(),
                                R.string.dialog_post_edit_saving_success,
                                Toast.LENGTH_LONG).show();
                        dismissAllowingStateLoss();
                      //  mListener.onPontoEntegasChanged(mPontoEntrega, false, mPost, deletarPlyline, true);

                    } else {
                        Toast.makeText(getActivity(),
                                getString(R.string.dialog_post_edit_saving_error,
                                        mPontoEntrega.getId()),
                                Toast.LENGTH_LONG).show();
                    }
                    //alertDialog.dismiss();

                } catch (IOException e) {
                    LogUtils.error(this, e);
                    //alertDialog.dismiss();

                    Toast.makeText(getActivity(),
                            getString(R.string.dialog_post_edit_saving_error,
                                    mPontoEntrega.getId()),
                            Toast.LENGTH_LONG).show();
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
