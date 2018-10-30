package com.visium.fieldservice.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.memoria.DownloadOrderOffline;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.entity.Aterramento;
import com.visium.fieldservice.entity.Defeito;
import com.visium.fieldservice.entity.Equipment;
import com.visium.fieldservice.entity.Mufla;
import com.visium.fieldservice.entity.OcupantesD;
import com.visium.fieldservice.entity.OcupantesS;
import com.visium.fieldservice.entity.ParaRaio;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.PostEffort;
import com.visium.fieldservice.entity.PostEquipament;
import com.visium.fieldservice.entity.PostHeight;
import com.visium.fieldservice.entity.PostRedePrimaria;
import com.visium.fieldservice.entity.PostType;
import com.visium.fieldservice.entity.PosteEncontrado;
import com.visium.fieldservice.entity.QtdEstai;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.LogUtils;
import com.visium.fieldservice.verifier.Verifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fjesus on 08/05/2018.
 */

public class PostCompletoEditFragment extends AppCompatDialogFragment {

    private static Post mPost;
    private static Activity activity;
    private static PostEditDialogListener mListener;

    private Spinner encontrado_spn, tipo_spn, altura_spn, esforco_spn, pararaio_spn, aterramento_spn, qtd_estai_spn, equipamento_spn,
            mufla_spn, redeprimaria_spn, defeito_spn, ocupantes_spn, ocupanted_spn;
    private Button save_button, cancel_button, btn_foto;
    private EditText barramento_edt, estrutura_primaria_edt, estrutura_secundaria_edt, ano_edt, situacao_edt, obs_poste_completo;

    public static AppCompatDialogFragment newInstance(Activity activity, PostEditDialogListener listener, Post post) {
        PostCompletoEditFragment.mPost = post;
        PostCompletoEditFragment.activity = activity;
        PostCompletoEditFragment.mListener = listener;

        return new PostCompletoEditFragment();
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

        View view = inflater.inflate(R.layout.layout_post_completoedit_fragment, container, false);

        encontrado_spn = (Spinner) view.findViewById(R.id.encontrado_spn);
        tipo_spn =(Spinner) view.findViewById(R.id.tipo_spn);
        altura_spn = (Spinner) view.findViewById(R.id.altura_spn);
        esforco_spn = (Spinner) view.findViewById(R.id.esforco_spn);
        pararaio_spn = (Spinner) view.findViewById(R.id.pararaio_spn);
        aterramento_spn = (Spinner) view.findViewById(R.id.aterramento_spn);
        qtd_estai_spn = (Spinner) view.findViewById(R.id.qtd_estai);
        equipamento_spn = (Spinner) view.findViewById(R.id.equipamento_spn);
        mufla_spn = (Spinner) view.findViewById(R.id.mufla_spn);
        redeprimaria_spn = (Spinner) view.findViewById(R.id.remeprimaria_spn);
        defeito_spn = (Spinner) view.findViewById(R.id.defeito_spn);
        save_button = (Button) view.findViewById(R.id.save_button);
        cancel_button = (Button) view.findViewById(R.id.cancel_button);
        barramento_edt = (EditText) view.findViewById(R.id.barramento_edt);
        estrutura_primaria_edt = (EditText) view.findViewById(R.id.estrutura_primaria_edt);
        estrutura_secundaria_edt = (EditText) view.findViewById(R.id.estrutura_secundaria_edt);
        ano_edt = (EditText) view.findViewById(R.id.ano_edt);
        situacao_edt = (EditText) view.findViewById(R.id.situacao_edt);
        btn_foto = (Button) view.findViewById(R.id.btn_foto);
        obs_poste_completo = (EditText) view.findViewById(R.id.obs_poste_completo);
        ocupantes_spn = (Spinner) view.findViewById(R.id.ocupantes_spn);
        ocupanted_spn = (Spinner) view.findViewById(R.id.ocupanted_spn);

        //ArrayAdapter<PosteEncontrado> encontradoAdapter = new ArrayAdapter<PosteEncontrado>(activity, android.R.layout.simple_list_item_2, PosteEncontrado.values());

        encontrado_spn.setAdapter(new ArrayAdapter<PosteEncontrado>(activity, android.R.layout.simple_list_item_1, PosteEncontrado.values()));
        encontrado_spn.setSelection(mPost.getEncontrado());

        tipo_spn.setAdapter(new ArrayAdapter<PostType>(activity, android.R.layout.simple_list_item_1, PostType.values()));
        tipo_spn.setSelection(mPost.getPostType());

        altura_spn.setAdapter(new ArrayAdapter<PostHeight>(activity, android.R.layout.simple_list_item_1, PostHeight.values()));
        altura_spn.setSelection(mPost.getHeight());

        esforco_spn.setAdapter(new ArrayAdapter<PostEffort>(activity, android.R.layout.simple_list_item_1, PostEffort.values()));
        esforco_spn.setSelection(mPost.getPostEffort());

        pararaio_spn.setAdapter(new ArrayAdapter<ParaRaio>(activity, android.R.layout.simple_list_item_1, ParaRaio.values()));
        pararaio_spn.setSelection(mPost.getParaRario());

        aterramento_spn.setAdapter(new ArrayAdapter<Aterramento>(activity, android.R.layout.simple_list_item_1, Aterramento.values()));
        aterramento_spn.setSelection(mPost.getAterramento());

        qtd_estai_spn.setAdapter(new ArrayAdapter<QtdEstai>(activity, android.R.layout.simple_list_item_1, QtdEstai.values()));
        qtd_estai_spn.setSelection(mPost.getQtdEstai());

        equipamento_spn.setAdapter(new ArrayAdapter<PostEquipament>(activity, android.R.layout.simple_list_item_1, PostEquipament.values()));
        equipamento_spn.setSelection(mPost.getEquipamento());

        mufla_spn.setAdapter(new ArrayAdapter<Mufla>(activity, android.R.layout.simple_list_item_1, Mufla.values()));
        mufla_spn.setSelection(mPost.getMufla());

        redeprimaria_spn.setAdapter(new ArrayAdapter<PostRedePrimaria>(activity, android.R.layout.simple_list_item_1, PostRedePrimaria.values()));
        redeprimaria_spn.setSelection(mPost.getRedePrimaria());

        defeito_spn.setAdapter(new ArrayAdapter<Defeito>(activity, android.R.layout.simple_list_item_1, Defeito.values()));
        defeito_spn.setSelection(mPost.getDefeito());

        ocupantes_spn.setAdapter(new ArrayAdapter<OcupantesS>(activity, android.R.layout.simple_list_item_1, OcupantesS.values()));
        ocupantes_spn.setSelection(mPost.getOcupante_s());

        ocupanted_spn.setAdapter(new ArrayAdapter<OcupantesD>(activity, android.R.layout.simple_list_item_1, OcupantesD.values()));
        ocupanted_spn.setSelection(mPost.getOcupante_d());


        btn_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentPhotosMaquina.newInstance(mPost).show(getFragmentManager(), "fotosEdit");
            }
        });

        if(mPost.getBarramento() != null)
        barramento_edt.setText(mPost.getBarramento());

        if(mPost.getEstruturaPrimaria() != null)
            estrutura_primaria_edt.setText(mPost.getEstruturaPrimaria());

        if(mPost.getEstruturaSecundaria() != null)
            estrutura_secundaria_edt.setText(mPost.getEstruturaSecundaria());

        if(mPost.getAno() != null)
            ano_edt.setText(mPost.getAno());

        if(mPost.getSituacao() != null)
            situacao_edt.setText(mPost.getSituacao());

        if(mPost.getObservations() != null)
            obs_poste_completo.setText(mPost.getObservations());

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mPost.setUpdate(true);
                    mPost.setEncontrado(encontrado_spn.getSelectedItemPosition());
                    mPost.setPostType(tipo_spn.getSelectedItemPosition());
                    mPost.setHeight(altura_spn.getSelectedItemPosition());
                    mPost.setPostEffort(esforco_spn.getSelectedItemPosition());
                    mPost.setParaRario(pararaio_spn.getSelectedItemPosition());
                    mPost.setAterramento(aterramento_spn.getSelectedItemPosition());
                    mPost.setQtdEstai(qtd_estai_spn.getSelectedItemPosition());
                    mPost.setEquipamento(equipamento_spn.getSelectedItemPosition());
                    mPost.setMufla(mufla_spn.getSelectedItemPosition());
                    mPost.setRedePrimaria(redeprimaria_spn.getSelectedItemPosition());
                    mPost.setDefeito(defeito_spn.getSelectedItemPosition());
                    mPost.setOcupante_s(ocupantes_spn.getSelectedItemPosition());
                    mPost.setOcupante_d(ocupanted_spn.getSelectedItemPosition());
                    mPost.setObservations(obs_poste_completo.getText().toString());

                    if(barramento_edt.getText().toString() != null ){
                        mPost.setBarramento(barramento_edt.getText().toString());
                    }

                    if(estrutura_primaria_edt.getText().toString() != null ){
                        mPost.setEstruturaPrimaria(estrutura_primaria_edt.getText().toString());
                    }

                    if(estrutura_secundaria_edt.getText().toString() != null ){
                        mPost.setEstruturaSecundaria(estrutura_secundaria_edt.getText().toString());
                    }

                    if(ano_edt.getText().toString() != null ){
                        mPost.setAno(ano_edt.getText().toString());
                    }

                    if(situacao_edt.getText().toString() != null ){
                        mPost.setSituacao(situacao_edt.getText().toString());
                    }

                    //OfflineDownloadResponse download = FileUtils.retrieve(mPost.getOrderId());
                    OfflineDownloadResponse download = null;
                    if(DownloadOrderOffline.getResponse() == null){
                        DownloadOrderOffline.setResponse(FileUtils.retrieve(mPost.getOrderId()));
                        download = DownloadOrderOffline.getResponse();
                    }
                    else{
                        download = DownloadOrderOffline.getResponse();
                    }
                    List<Post> posts = download.getPosts();

                    List<PontoEntrega> pontoEntregasLines = new ArrayList<>();
                    List<PontoEntrega> pontosNovos = new ArrayList<>();
                    pontoEntregasLines = download.getPontoEntregaList();

                    for (PontoEntrega pontoEntrega : pontoEntregasLines){
                        if(pontoEntrega.getPostId() == mPost.getId()){
                            pontosNovos.add(pontoEntrega);
                        }
                    }

                    Iterator<Post> iterator = posts.iterator();

                    while (iterator.hasNext()) {
                        Post post = iterator.next();
                        if (post.getId() == mPost.getId()) {
                            iterator.remove();
                            break;
                        }
                    }

                    posts.add(mPost);
                    download.setPostList(posts);

                    if (FileUtils.saveOfflineDownload(download)) {
                        Verifier.addPostCount(mPost.getGeoCode());
                        Toast.makeText(getActivity(),
                                R.string.dialog_post_edit_saving_success,
                                Toast.LENGTH_LONG).show();
                        dismissAllowingStateLoss();
                        mListener.onPostChanged(mPost, false, pontosNovos);

                    } else {

                        Toast.makeText(getActivity(),
                                getString(R.string.dialog_post_edit_saving_error,
                                        mPost.getId()),
                                Toast.LENGTH_LONG).show();
                    }

                   // alertDialog.dismiss();

                } catch (IOException e) {
                    LogUtils.error(this, e);
                    //alertDialog.dismiss();

                    Toast.makeText(getActivity(),
                            getString(R.string.dialog_post_edit_saving_error,
                                    mPost.getId()),
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
