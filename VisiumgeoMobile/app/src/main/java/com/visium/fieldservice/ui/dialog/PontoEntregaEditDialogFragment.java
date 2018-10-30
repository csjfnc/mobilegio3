package com.visium.fieldservice.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.controller.PostController;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.Fase;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.PontoEntregaClasseAtendimento;
import com.visium.fieldservice.entity.PontoEntregaClasseSocial;
import com.visium.fieldservice.entity.PontoEntregaPhotos;
import com.visium.fieldservice.entity.PontoEntregaStatus;
import com.visium.fieldservice.entity.PontoEntregaTipodeConstrução;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.ui.common.EquipmentEditDialogListener;
import com.visium.fieldservice.ui.dialog.adapter.FaseAdapter;
import com.visium.fieldservice.ui.dialog.adapter.PontoEntregaClasseAtendimentoAdapter;
import com.visium.fieldservice.ui.dialog.adapter.PontoEntregaClasseSocialAdapter;
import com.visium.fieldservice.ui.dialog.adapter.PontoEntregaStatusAdapter;
import com.visium.fieldservice.ui.dialog.adapter.PontoEntregaTipoConstrucaoAdapter;
import com.visium.fieldservice.ui.maps.MapsPostsActivity;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.LogUtils;
import com.visium.fieldservice.verifier.Verifier;
import com.visium.fieldservice.verifier.VerifierPontoEntregaFotos;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

public class PontoEntregaEditDialogFragment extends AppCompatDialogFragment {

    private static PontoEntrega mPontoEntrega;
    private static EquipmentEditDialogListener mListener2;
    private static boolean isCreating = false;
    private static boolean mCreating;
    private static LatLng mLatLng;
    private static MapsPostsActivity mListener;
    private static Post mPost;
    private static long mOrderId;
    private static Activity activity;
    private static LatLng mUserLatLng;
    private static Button mSetStart, mImageAdd;
    private Spinner mStatus, mClasseAtendimento, mTipoConstrucao, mClasseSocial;
    private EditText mLogradouro, mNumero, mMedidor, mFase, mETLigacao, mObservacao;
    private static List<PontoEntregaPhotos> pontoEntregaPhotosList;
    private LinearLayout layoutImages_ponto_entrega;
    private ScrollView mScrollView;
    private GridView gridStatus, grid_classe_atendimento, grid_tipo_construcao, grid_ponto_classe_social,gridFase;
    private PontoEntregaStatusAdapter pontoEntregaStatusAdapter;
    private PontoEntregaClasseAtendimentoAdapter pontoEntregaClasseAtendimentoAdapter;
    private PontoEntregaTipoConstrucaoAdapter pontoEntregaTipoConstrucaoAdapter;
    private PontoEntregaClasseSocialAdapter pontoEntregaClasseSocialAdapter;
    private ViewFlipper viewFlipper;
    private static TextView post_seq_status, post_seq_classe_atendimento, post_seq_tipo_construcao, post_seq_classe_social,
            post_seq_lagradouro, post_seq_numero, post_seq_fase, post_seq_etligacao, post_seq_observacao;

    private static String seq_lagradouro,  seq_numero,  seq_fase,  seq_etligacao, seq_observacao;
    private static int seq_status,  seq_classe_atendimento,  seq_tipo_construcao,  seq_classe_social;

    public static AppCompatDialogFragment newInstance(Activity activity, EquipmentEditDialogListener listener, PontoEntrega pontoEntrega, LatLng mUserLatLng) {
        PontoEntregaEditDialogFragment.mPontoEntrega = pontoEntrega;
        PontoEntregaEditDialogFragment.mListener2 = listener;
        PontoEntregaEditDialogFragment.mCreating = mUserLatLng != null;
        PontoEntregaEditDialogFragment.activity = activity;
       /* if (PontoEntregaEditDialogFragment.mCreating) {
            PontoEntregaEditDialogFragment.mUserLatLng = mUserLatLng;
        }*/
        isCreating = true;
        return new PontoEntregaEditDialogFragment();
    }

    public static AppCompatDialogFragment newInstance(MapsPostsActivity listener,
                                                      PontoEntrega pontoEntrega, Post post, long mOrderId) {
        PontoEntregaEditDialogFragment.mPontoEntrega = pontoEntrega;
        PontoEntregaEditDialogFragment.mListener = listener;
        PontoEntregaEditDialogFragment.mPost = post;
        PontoEntregaEditDialogFragment.mOrderId = mOrderId;

        return new PontoEntregaEditDialogFragment();
    }

    public static AppCompatDialogFragment newInstance(MapsPostsActivity listener,
                                                      LatLng latLng, Post post, long mOrderId) {
        LogUtils.log("Ponto Entrega is creating true");
        isCreating = true;
        PontoEntregaEditDialogFragment.mLatLng = latLng;
        PontoEntregaEditDialogFragment.mListener = listener;
        PontoEntregaEditDialogFragment.mPost = post;
        PontoEntregaEditDialogFragment.mOrderId = mOrderId;
        mPontoEntrega = null; //NAO ESQUECEREI DESSA LINHA
        return new PontoEntregaEditDialogFragment();
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
    public void addImage(Context c, final String value, boolean isNew) {
        final LinearLayout l = new LinearLayout(c);
        final EditText lEdit = new EditText(c);
        //ViewUtils.setViewMargins(c, new LinearLayout.LayoutParams(), 0, 0, 30, 10, lEdit);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 60, 20);
        lEdit.setLayoutParams(layoutParams);
        lEdit.setText(value);
        Button b = new Button(c);
        b.setText("Apagar");
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutImages_ponto_entrega.removeView(l);
                int toRemove = -1;
                for(int   i = 0; i<pontoEntregaPhotosList.size(); i++) {
                    PontoEntregaPhotos p = pontoEntregaPhotosList.get(i);
                    if (p.getNumber().equals(value)) {
                        toRemove = i;
                        break;
                    }
                }
                pontoEntregaPhotosList.remove(toRemove);
            }
        });
        l.addView(lEdit);
        l.addView(b);
        layoutImages_ponto_entrega.addView(l);
        if(isNew) {
            Calendar cc = Calendar.getInstance();
            //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(cc.getTime());
            LogUtils.log("Date = "+date);
            pontoEntregaPhotosList.add(new PontoEntregaPhotos(value, date));
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        pontoEntregaPhotosList = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_pontoentrega_edit, container, false);

        /*mStatus = (Spinner)view.findViewById(R.id.spinner_status);
        mClasseAtendimento = (Spinner)view.findViewById(R.id.spinner_classeatendimento);
        mTipoConstrucao = (Spinner)view.findViewById(R.id.spinner_tipoconstrucao);*/
        mSetStart = (Button) view.findViewById(R.id.button_set_start);
        mImageAdd = (Button) view.findViewById(R.id.button_add);
        layoutImages_ponto_entrega = (LinearLayout) view.findViewById(R.id.layoutImages_ponto_entrega);
        mScrollView = (ScrollView) view.findViewById(R.id.scroll_ponto_entrega);
        //mClasseSocial = (Spinner)view.findViewById(R.id.spinner_classe_social);*/
        mLogradouro = (EditText)view.findViewById(R.id.edit_lagradouro);
        mNumero = (EditText)view.findViewById(R.id.edit_numero);
       // mFase = (EditText)view.findViewById(R.id.edit_fase);

      //  post_seq_tipoDemanda = (TextView) view.findViewById(R.id.post_seq_tipoDemanda);
      //  post_seq_classe_atendimento = (TextView) view.findViewById(R.id.post_seq_classe_atendimento);
        ///post_seq_tipo_construcao = (TextView) view.findViewById(R.id.post_seq_tipo_construcao);
        //post_seq_classe_social = (TextView) view.findViewById(R.id.post_seq_tipo_imovel);
        //post_seq_lagradouro = (TextView) view.findViewById(R.id.post_seq_lagradouro);
        //post_seq_numero = (TextView) view.findViewById(R.id.post_seq_numero);
        /*post_seq_fase = (TextView) view.findViewById(R.id.post_seq_fase);
        post_seq_etligacao = (TextView) view.findViewById(R.id.post_seq_etligacao);
        post_seq_observacao = (TextView) view.findViewById(R.id.post_seq_observacao);*/


      // mETLigacao = (EditText)view.findViewById(R.id.edit_etligacao);
        //mObservacao = (EditText)view.findViewById(R.id.edit_observaocao);
        /*mStatus.setAdapter(
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
                        PontoEntregaStatus.getNames()));
        mStatus.setSelection(0);

        mClasseAtendimento.setAdapter(
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
                        PontoEntregaClasseAtendimento.getClasseAtendimentos()));
        mClasseAtendimento.setSelection(0);

        mTipoConstrucao.setAdapter(
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
                        PontoEntregaTipodeConstrução.getTipodeConstruçãos()));
        mTipoConstrucao.setSelection(0);


        mClasseSocial.setAdapter(
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,
                        PontoEntregaClasseSocial.getClasseSocials()));
        mClasseSocial.setSelection(0);*/

        //Versao 3

        final Button back_button_ponto_entrega, next_button_ponto_entrega;
        back_button_ponto_entrega = (Button) view.findViewById(R.id.back_button_ponto_entrega);
        next_button_ponto_entrega = (Button) view.findViewById(R.id.next_button_ponto_entrega);

        gridStatus = (GridView) view.findViewById(R.id.gridStatus);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.vs_ponto_entrega);
        //grid_classe_atendimento = (GridView) view.findViewById(R.id.grid_classe_atendimento);
        //grid_ponto_classe_social = (GridView) view.findViewById(R.id.grid_ponto_classe_social);
        gridFase = (GridView) view.findViewById(R.id.gridFase);

        Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);

        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);

        final int fim = 8;
        final int[] count = {0};


        final List<PontoEntregaStatus> listStatus = Arrays.asList(PontoEntregaStatus.values());
        final List<PontoEntregaClasseAtendimento> pontoEntregaClasseAtendimentos = Arrays.asList(PontoEntregaClasseAtendimento.values());
        final List<PontoEntregaTipodeConstrução> listaTipoContrucao = Arrays.asList(PontoEntregaTipodeConstrução.values());
        final List<PontoEntregaClasseSocial> pontoEntregaClasseSocials = Arrays.asList(PontoEntregaClasseSocial.values());

        pontoEntregaStatusAdapter = new PontoEntregaStatusAdapter(getContext(), listStatus);
        gridStatus.setAdapter(pontoEntregaStatusAdapter);

       /* pontoEntregaClasseAtendimentoAdapter = new PontoEntregaClasseAtendimentoAdapter(getContext(), pontoEntregaClasseAtendimentos);
        grid_classe_atendimento.setAdapter(pontoEntregaClasseAtendimentoAdapter);
*/

        pontoEntregaTipoConstrucaoAdapter = new PontoEntregaTipoConstrucaoAdapter(getContext(), listaTipoContrucao);
        grid_tipo_construcao.setAdapter(pontoEntregaTipoConstrucaoAdapter);

        /*pontoEntregaClasseSocialAdapter = new PontoEntregaClasseSocialAdapter(getContext(), pontoEntregaClasseSocials);
        grid_ponto_classe_social.setAdapter(pontoEntregaClasseSocialAdapter);*/

        seq_fase = "";

        gridStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                seq_status = i;
                if(listStatus.get(i).getStatus().equals("SEM INFORMACAO") || listStatus.get(i).getStatus().equals("SEM_INFORMACAO")){
                    post_seq_status.setText("...");
                }else{
                    post_seq_status.setText(listStatus.get(i).getStatus());
                }
                viewFlipper.showNext();
                ++count[0];

            }
        });
      /*  grid_classe_atendimento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                seq_classe_atendimento = i;
                if(pontoEntregaClasseAtendimentos.get(i).getClasseAtendimento().equals("SEM INFORMACAO") || pontoEntregaClasseAtendimentos.get(i).getClasseAtendimento().equals("SEM_INFORMACAO")){
                    post_seq_classe_atendimento.setText("/...");
                }else{
                    post_seq_classe_atendimento.setText("/"+pontoEntregaClasseAtendimentos.get(i).getClasseAtendimento());
                }
                viewFlipper.showNext();
                ++count[0];
            }
        });*/
        grid_tipo_construcao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                seq_tipo_construcao = i;
                if(listaTipoContrucao.get(i).getTipodeConstrução().equals("SEM INFORMACAO") || listaTipoContrucao.get(i).getTipodeConstrução().equals("SEM_INFORMACAO")){
                    post_seq_tipo_construcao.setText("/...");
                }else{
                    post_seq_tipo_construcao.setText("/"+listaTipoContrucao.get(i).getTipodeConstrução());
                }
                viewFlipper.showNext();
                ++count[0];
            }
        });
        /*grid_ponto_classe_social.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                seq_classe_social = i;
                if(pontoEntregaClasseSocials.get(i).getClasseSocial().equals("SEM INFORMACAO") || pontoEntregaClasseSocials.get(i).getClasseSocial().equals("SEM_INFORMACAO")){
                    post_seq_classe_social.setText("/...");
                }else{
                    post_seq_classe_social.setText("/"+pontoEntregaClasseSocials.get(i).getClasseSocial());
                }
                viewFlipper.showNext();
                ++count[0];
            }
        });
*/
        final List<Fase> faseList = Arrays.asList(Fase.values());
        FaseAdapter faseAdapter = new FaseAdapter(getContext(), faseList);
        gridFase.setAdapter(faseAdapter);
        gridFase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(faseList.get(i).getName().equals("SEM INFORMACAO")){
                    post_seq_fase.setText("/...");
                    seq_fase = "SEM INFORMACAO";
                }else{
                    seq_fase =  faseList.get(i).getName().toString();
                    post_seq_fase.setText(seq_fase);
                }

                ++count[0];
                viewFlipper.showNext();
            }
        });

        if (mPontoEntrega == null) {
            mPontoEntrega = new PontoEntrega();
            mPontoEntrega.setPostId(mPost.getId());
            mPontoEntrega.setUpdate(true);
            mPontoEntrega.setX(mLatLng.latitude);
            mPontoEntrega.setY(mLatLng.longitude);
            ((TextView) view.findViewById(R.id.title))
                    .setText("Criar");
        } else {
            ((TextView) view.findViewById(R.id.title))
                    .setText("Editar " + mPontoEntrega.getId());
/*
            mStatus.setSelection(
                    PontoEntregaConfiguration.getEnum(PontoEntregaConfiguration.Names.STATUS).getEnums()
                            .get(mPontoEntrega.getStatus()).getKey());
            mClasseAtendimento.setSelection(
                    PontoEntregaConfiguration.getEnum(PontoEntregaConfiguration.Names.CLASSE_DE_ATENDIMENTO).getEnums()
                            .get(mPontoEntrega.getClasseAtendimento()).getKey());
            mTipoConstrucao.setSelection(
                    PontoEntregaConfiguration.getEnum(PontoEntregaConfiguration.Names.TIPO_DE_CONSTRUCAO).getEnums()
                            .get(mPontoEntrega.getTipoConstrucao()).getKey());
*/

            //mClasseAtendimento.setSelection(mPontoEntrega.getClasseAtendimento());
            /*seq_classe_atendimento = mPontoEntrega.getClasseAtendimento();
            seq_tipo_construcao = (mPontoEntrega.getTipoConstrucao());
            seq_classe_social = (mPontoEntrega.getClasseSocial());
            seq_status = (mPontoEntrega.getStatus());

            String status  = listStatus.get(seq_status).getStatus();
            String construcao  = listaTipoContrucao.get(seq_tipo_construcao).getTipodeConstrução();
            String social  = pontoEntregaClasseSocials.get(seq_classe_social).getClasseSocial();
            String atendimento  = pontoEntregaClasseAtendimentos.get(seq_classe_atendimento).getClasseAtendimento();

            mLogradouro.setText(String.valueOf(mPontoEntrega.getLogradouro()));
            mNumero.setText(String.valueOf(mPontoEntrega.getNumero()));
            //mFase.setText(String.valueOf(mPontoEntrega.getFase()));
           // mETLigacao.setText(String.valueOf(mPontoEntrega.getEtLigacao()));
          //  mObservacao.setText(String.valueOf(mPontoEntrega.getObservacao()));

            seq_lagradouro = mPontoEntrega.getLogradouro() != null ? mPontoEntrega.getLogradouro() : "" ;
            seq_numero = mPontoEntrega.getNumero() != null ? mPontoEntrega.getNumero() : "" ;
            //seq_complemento = mPontoEntrega.get();
            seq_fase = mPontoEntrega.getFase() != null ? mPontoEntrega.getFase() : "" ;
            seq_etligacao = mPontoEntrega.getEtLigacao() != null ? mPontoEntrega.getEtLigacao() : "" ;
            seq_observacao = mPontoEntrega.getObservacao() != null ? mPontoEntrega.getObservacao() : "" ;

            if(!seq_lagradouro.equals("")){
                post_seq_lagradouro.setText(seq_lagradouro.toUpperCase());
            }
            if(!seq_numero.equals("")){
                post_seq_numero.setText("/"+seq_numero.toUpperCase());
            }
            /*if(!seq_complemento.equals("")){
                post_seq_complemento.setText("/"+seq_complemento.toUpperCase());
            }*/
            if(!seq_fase.equals("")){
                if(seq_fase.equals("SEM INFORMACAO")){
                    post_seq_fase.setText("/...");
                }else{
                    post_seq_fase.setText(seq_fase.toUpperCase());
                }

            }
            if(!seq_etligacao.equals("")){
                post_seq_etligacao.setText("/"+seq_etligacao.toUpperCase());
            }
            if(!seq_observacao.equals("")){
                post_seq_observacao.setText("/"+seq_observacao.toUpperCase());
            }

            if(listStatus.get(seq_status).getStatus().equals("SEM INFORMACAO")){
                post_seq_status.setText("...");
            }else{
                post_seq_status.setText(listStatus.get(seq_status).getStatus());
            }

            if(listaTipoContrucao.get(seq_tipo_construcao).getTipodeConstrução().equals("SEM INFORMACAO")){
                post_seq_tipo_construcao.setText("/...");
            }else{
                post_seq_tipo_construcao.setText("/"+listaTipoContrucao.get(seq_tipo_construcao).getTipodeConstrução());
            }

            if(pontoEntregaClasseSocials.get(seq_classe_social).getClasseSocial().equals("SEM INFORMACAO")){
                post_seq_classe_social.setText("/...");
            }else{
                post_seq_classe_social.setText("/"+pontoEntregaClasseSocials.get(seq_classe_social).getClasseSocial());
            }

            if(pontoEntregaClasseAtendimentos.get(seq_classe_atendimento).getClasseAtendimento().equals("SEM INFORMACAO")){
                post_seq_classe_atendimento.setText("/...");
            }else{
                post_seq_classe_atendimento.setText("/"+pontoEntregaClasseAtendimentos.get(seq_classe_atendimento).getClasseAtendimento());
            }

           /* post_seq_status.setText(listStatus.get(seq_status)+"");
            post_seq_tipo_construcao.setText("/"+listaTipoContrucao.get(seq_tipo_construcao)+"");
            post_seq_classe_social.setText("/"+pontoEntregaClasseSocials.get(seq_classe_social)+"");
            post_seq_classe_atendimento.setText("/"+pontoEntregaClasseAtendimentos.get(seq_classe_atendimento)+"");*/

            //mClasseSocial.setText(String.valueOf(mPontoEntrega.getClasseSocial()));

            //mMedidor.setText(String.valueOf(mPontoEntrega.getMedidor()));

        }

        final PontoEntregaEditDialogFragment c = this;


        mSetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerifierPontoEntregaFotos.prompt(getContext());
            }
        });

        mImageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = VerifierPontoEntregaFotos.getNextNumber(mPontoEntrega.getGeoCode(), c);
                if(n>0)
                    addImage(getContext(), String.format("%04d", n), true);
            }
        });
        if (mPontoEntrega.getPhotos() != null) {
            List<PontoEntregaPhotos> imagesList = mPontoEntrega.getPhotos();
            for (PontoEntregaPhotos pp : imagesList) {
                addImage(c.getContext(), pp.getNumber(), false);
                pontoEntregaPhotosList.add(pp);
            }
        }

        view.findViewById(R.id.next_button_ponto_entrega).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
               // seq_lagradouro = mLogradouro.getText().toString();
                //seq_numero = mNumero.getText().toString();

              //  seq_fase = mFase.getText().toString();
                /*seq_etligacao = mETLigacao.getText().toString();
                seq_observacao = mObservacao.getText().toString();

                if(!seq_lagradouro.equals("")){
                    post_seq_lagradouro.setText(seq_lagradouro.toUpperCase());
                }
                if(!seq_numero.equals("")){
                    post_seq_numero.setText("/"+seq_numero.toUpperCase());
                }*/
            /*if(!seq_complemento.equals("")){
                post_seq_complemento.setText("/"+seq_complemento.toUpperCase());
            }*/
                if(!seq_fase.equals("")){
                    if(seq_fase.equals("SEM INFORMACAO")){
                        post_seq_fase.setText("/...");
                    }else{
                        post_seq_fase.setText(seq_fase.toUpperCase());
                    }
                }
                if(!seq_etligacao.equals("")){
                    post_seq_etligacao.setText("/"+seq_etligacao.toUpperCase());
                }
                if(!seq_observacao.equals("")){
                    post_seq_observacao.setText("/"+seq_observacao.toUpperCase());
                }

                if (count[0] > fim) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setMessage("Deseja Salvar")
                            .setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //EquipmentUpdateUtils.setPontoAtualizacao(getContext(), mPost);
                                   /* Location l = mListener.getLastLocation();
                                    mPontoEntrega.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis() / 1000));
                                    mPontoEntrega.setUpdate(true);*/

                                    final ProgressDialog alertDialog = new ProgressDialog(getActivity(), R.style.AlertDialogTheme);
                              //      alertDialog.setTitle(getString(R.string.dialog_post_edit_saving_title, mPost.getGeoCode()));
                                    alertDialog.setMessage(getString(R.string.dialog_post_edit_saving_message));
                                    alertDialog.setCancelable(false);
                                    alertDialog.show();

                                   /* mPost.setObservations(ViewUtils.getTextViewValue(mObservations));
                                    mPost.setHeight(altura);*/

                                    /*/*mPost.setOcupante_s(OcupantesS.getOcupantes_s().get(ocupante_s.getSelectedItemPosition()).intValue());
                                    mPost.setOcupante_d(OcupantesD.getOcupantesd().get(ocupante_d.getSelectedItemPosition()).intValue());*/

                                    /*
                                    mPost.setOcupante_s(ocupantes);
                                    mPost.setOcupante_d(ocupanted);

                                    mPost.setPostEffort(esforco);
                                    mPost.setPostType(tipo);*/

                                 /*/*   mPost.setPostEffort(PostEffort.getNames().get(mEffort.getSelectedItemPosition()).toString());
                                    mPost.setPostType(mType.getSelectedItemPosition());
                                    mPost.setCreateDate(new Date());*/

                /*/*List<String> imagesTags = new ArrayList<String>();
                int count = mLayoutImages.getChildCount();
                for(int i = 0; i<count; i++) {
                    LinearLayout l = (LinearLayout) mLayoutImages.getChildAt(i);
                    EditText e = (EditText) l.getChildAt(0);
                    imagesTags.add(String.valueOf(e.getText()));
                }*/
                                 //   mPontoEntrega.setPhotos(photosList);

                                    boolean workingOffline = FileUtils.serviceOrderFileExists(mPontoEntrega.getOrderId());
                                    if (mCreating) {
                                        mPontoEntrega.setClosed(true);
                                        mPontoEntrega.setUpdate(true);
                                        mPontoEntrega.setPostNumber(mListener.getNextPostNumber());
                                  //      LogUtils.log("highestPostNumber = " + mListener.getHighestPostNumber());

                                        if (workingOffline) {

                                            try {
                                                OfflineDownloadResponse download = FileUtils.retrieve(mPontoEntrega.getOrderId());
                                                List<PontoEntrega> pontoEntregas = download.getPontoEntregaList();
                                                mPontoEntrega.setId(-1 * System.currentTimeMillis());
                                                pontoEntregas.add(mPontoEntrega);
                                                download.setPontoEntregaList(pontoEntregas);

                                                if (FileUtils.saveOfflineDownload(download)) {
                                                    Verifier.clearRollBackStack();
                                                    Verifier.addPostCount(mPontoEntrega.getGeoCode());
                                                    Toast.makeText(getActivity(),
                                                            R.string.dialog_post_edit_saving_success,
                                                            Toast.LENGTH_LONG).show();
                                                    dismissAllowingStateLoss();
                                                  //  mListener.onPontoEntregaChanged(mPontoEntrega, true);

                                                } else {
                                               //     mPost.setClosed(false);
                                                    Toast.makeText(getActivity(),
                                                            getString(R.string.dialog_post_edit_saving_error,
                                                                    mPost.getId()),
                                                            Toast.LENGTH_LONG).show();
                                                }

                                                alertDialog.dismiss();

                                            } catch (IOException e) {
                                                mPost.setClosed(false);
                                                LogUtils.error(this, e);
                                                alertDialog.dismiss();
                                                Toast.makeText(getActivity(),
                                                        getString(R.string.dialog_post_edit_saving_error,
                                                                mPost.getId()),
                                                        Toast.LENGTH_LONG).show();
                                            }

                                        } else {

                                            PostController.get().create(getContext(), mPost, new VisiumApiCallback<Post>() {
                                                @Override
                                                public void callback(Post post, ErrorResponse e) {
                                                    alertDialog.dismiss();

                                                    if (e == null && post != null) {
                                                        Verifier.clearRollBackStack();
                                                        Verifier.addPostCount(mPost.getGeoCode());
                                                        Toast.makeText(getActivity(),
                                                                R.string.dialog_post_edit_saving_success,
                                                                Toast.LENGTH_LONG).show();
                                                        dismissAllowingStateLoss();
                                                        mListener.onPostChanged(post, true, null);
                                                    } else if (e != null && e.isCustomized()) {
                                                        mPost.setClosed(false);
                                                        Toast.makeText(getActivity(),
                                                                e.getMessage(), Toast.LENGTH_LONG).show();
                                                    } else {
                                                        mPost.setClosed(false);
                                                        Toast.makeText(getActivity(),
                                                                getString(R.string.dialog_post_edit_saving_error,
                                                                        mPost.getId()),
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }

                                    } else {
                                        //mPost.setClosed(mTransaction.isChecked());

                                        if (workingOffline) {

                                            try {
                                                OfflineDownloadResponse download = FileUtils.retrieve(mPost.getOrderId());
                                                List<Post> posts = download.getPosts();

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
                                                    mListener.onPostChanged(mPost, false, null);

                                                } else {

                                                    Toast.makeText(getActivity(),
                                                            getString(R.string.dialog_post_edit_saving_error,
                                                                    mPost.getId()),
                                                            Toast.LENGTH_LONG).show();
                                                }

                                                alertDialog.dismiss();

                                            } catch (IOException e) {
                                                LogUtils.error(this, e);
                                                alertDialog.dismiss();

                                                Toast.makeText(getActivity(),
                                                        getString(R.string.dialog_post_edit_saving_error,
                                                                mPost.getId()),
                                                        Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            PostController.get().save(getContext(), mPost, new VisiumApiCallback<Post>() {
                                                @Override
                                                public void callback(Post post, ErrorResponse e) {
                                                    alertDialog.dismiss();

                                                    if (e == null && post != null) {
                                                        Verifier.addPostCount(mPost.getGeoCode());
                                                        Toast.makeText(getActivity(),
                                                                R.string.dialog_post_edit_saving_success,
                                                                Toast.LENGTH_LONG).show();
                                                        dismissAllowingStateLoss();
                                                        mListener.onPostChanged(mPost, false, null);
                                                    } else if (e != null && e.isCustomized()) {
                                                        Toast.makeText(getActivity(),
                                                                e.getMessage(), Toast.LENGTH_LONG).show();
                                                    } else {
                                                        Toast.makeText(getActivity(),
                                                                R.string.dialog_post_edit_saving_error,
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }
                                    }
                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();

                }else{
                    viewFlipper.showNext();
                    count[0]++;
                    if(count[0] > fim){
                        next_button_ponto_entrega.setText("Salvar");
                        Drawable image = ContextCompat.getDrawable(getContext(), R.drawable.ic_check_black_24dp);
                        image.setBounds( 0, 0, 60, 60 );
                        next_button_ponto_entrega.setCompoundDrawables(null, null, image, null );
                    }else{

                    }

                }
            }
        });

        view.findViewById(R.id.back_button_ponto_entrega).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count[0] > 0) {
                    viewFlipper.showPrevious();
                    --count[0];
                    next_button_ponto_entrega.setText("Próximo");
                    Drawable image = ContextCompat.getDrawable(getContext(), R.drawable.ic_navigate_next_black_24dp);
                    image.setBounds( 0, 0, 60, 60 );
                    next_button_ponto_entrega.setCompoundDrawables(null, null, image, null );
                } else {
                    Verifier.rollBack(mPost.getGeoCode());
                    dismissAllowingStateLoss();
                }
            }
        });

        return view;
    }


}