package com.visium.fieldservice.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.visium.fieldservice.api.visium.bean.memoria.DownloadOrderOffline;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.controller.PontoEntregaController;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.DemandaClassficacao;
import com.visium.fieldservice.entity.DemandaComplemento1;
import com.visium.fieldservice.entity.DemandaComplemento2;
import com.visium.fieldservice.entity.DemandaDomicilio;
import com.visium.fieldservice.entity.DemandaTipoImovel;
import com.visium.fieldservice.entity.Lagradouro;
import com.visium.fieldservice.entity.NumeroLocal;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.PontoEntregaPhotos;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.QuatidadeSalas;
import com.visium.fieldservice.entity.ServiceOrderDetails;
import com.visium.fieldservice.entity.TipoComercio;
import com.visium.fieldservice.entity.TipoDemanda;
import com.visium.fieldservice.entity.VaosPontoPoste;
import com.visium.fieldservice.ui.dialog.adapter.DemandaClassificacaoAdapter;
import com.visium.fieldservice.ui.dialog.adapter.DemandaComplemento1Adapter;
import com.visium.fieldservice.ui.dialog.adapter.DemandaComplemento2Adapter;
import com.visium.fieldservice.ui.dialog.adapter.DemandaTipoImovelAdapter;
import com.visium.fieldservice.ui.dialog.adapter.DomicilioAdapter;
import com.visium.fieldservice.ui.dialog.adapter.NumeroLocalAdapter;
import com.visium.fieldservice.ui.dialog.adapter.PontoEntregaTipoDemandaAdapter;
import com.visium.fieldservice.ui.dialog.adapter.QuatidadeSalasAdapter;
import com.visium.fieldservice.ui.dialog.adapter.TipoComercioAdapter;
import com.visium.fieldservice.ui.maps.MapsPickLocationActivity;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.LogUtils;
import com.visium.fieldservice.verifier.Verifier;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */

public class PostPontoEditDialogFragment extends AppCompatDialogFragment {

    private static final int PICKUP_LOCATION = 1;

    private static PontoEntrega mPontoEntrega;
    private static VaosPontoPoste mVaosPontoPoste;
    private static PontoEntregaEditDialogListener mListener;
    private static Button mImageAdd, mSetStart, btn_mudar_posicao;
    private static LatLng mUserLatLng;
    private static boolean mCreating;
    private LinearLayout mLayoutImages;
    private ScrollView mScrollView;
    private static Spinner mType, mEffort, mHeight, ocupante_s, ocupante_d;
    private static EditText mObservations;
    private static List<PontoEntregaPhotos> photosList;
    private static Activity activity;
    private AlertDialog alertDialog;
    private GridView  gridNumeroLocal, gridNumeroAndarLocal, gridNumeroTotalApartamentos;
    private static Post mPost;
    private VaosPontoPoste vaosDeletar;
    private boolean trocaPositionVaoPOnto = false;
    private boolean podePassar = false;
    private ViewFlipper viewFlipper;
    private static String seq_lagradouro, seq_numero, seq_fase, seq_etligacao;
    private static int seq_tipo_demanda, seq_tipo_comercio_position;


    private String set_tipo_complemento1, set_tipo_complemento2, seq_tipo_imovel, seq_numero_local, seq_numero_andares_edificio,
            seq_numeto_total_apartamentos, seq_nome_edifio;

    private int seq_domicilio, seq_tipoComercio, seq_quantidade_salas, seq_classificacao;

    private static TextView post_seq_tipoDemanda, post_seq_classficacao, post_seq_complemento1, post_seq_tipo_imovel, post_seq_complemento2,
            post_seq_nunero_local, post_seq_numero_total_apartamentos, post_seq_numero_andares_edificio, post_seq_nome_edificio, post_seq_domicilio, post_seq_tipoComercio, post_seq_quantidade_salas;


    private LinearLayout layoutImages_ponto_entrega;
    private EditText mLogradouro, mNumero, mMedidor, mFase, mETLigacao, mObservacao, edt_nome_edificio, edt_numero_local,
            edt_numero_total_apartamentos, edt_numero_andar_edificio;
    private GridView gridTipoDemanda, grid_classificacao, grid_complemento1, grid_tipo_imovel, grid_complemento2, grid_domicilio, grid_tipo_comercio, grid_qtd_salas;
    private PontoEntregaTipoDemandaAdapter pontoEntregaTipoDemandaAdapter;
    private DemandaClassificacaoAdapter demandaClassificacaoAdapter;
    private DemandaComplemento1Adapter demandaComplemento1Adapter;
    private DemandaComplemento2Adapter demandaComplemento2Adapter;
    private DemandaTipoImovelAdapter demandaTipoImovelAdapter;
    private NumeroLocalAdapter numeroLocalAdapter;
    private NumeroLocalAdapter numeroAndarLocalAdapter, numeroTotalApartamentosAdapter;
    private static List<PontoEntregaPhotos> pontoEntregaPhotosList;
    private static Lagradouro lagradouro;
    private boolean deletarPlyline;
    private EditText edt_complemento1, edt_complemento2;
    private LinearLayout linear_numero_andar_edificio;
    String numeroLocal = "";
    String numeroAndar = "";
    String numeroAparatamentos = "";

    private DomicilioAdapter domicilioAdapter;
    private TipoComercioAdapter tipoComercioAdapter;
    private QuatidadeSalasAdapter quatidadeSalasAdapter;

    private List<LatLng> mOrderPoints;
    private ServiceOrderDetails mOrderDetails;

    public static AppCompatDialogFragment newInstance(Activity activity, PontoEntregaEditDialogListener listener, PontoEntrega post) {
        return PostPontoEditDialogFragment.newInstance(activity, listener, post, null);
    }

    public static AppCompatDialogFragment newInstance(Activity activity, PontoEntregaEditDialogListener listener, PontoEntrega pontoEntrega, LatLng mUserLatLng) {
        PostPontoEditDialogFragment.mPontoEntrega = pontoEntrega;
        PostPontoEditDialogFragment.mListener = listener;
        PostPontoEditDialogFragment.mCreating = mUserLatLng != null;
        PostPontoEditDialogFragment.activity = activity;
        PostPontoEditDialogFragment.mUserLatLng = mUserLatLng;
        if (PostPontoEditDialogFragment.mCreating) {
            PostPontoEditDialogFragment.mUserLatLng = mUserLatLng;
        }
        return new PostPontoEditDialogFragment();
    }

    public static AppCompatDialogFragment newInstance(Activity activity, PontoEntregaEditDialogListener listener, PontoEntrega pontoEntrega, LatLng mUserLatLng, Lagradouro lagradouro, Post post) {
        PostPontoEditDialogFragment.mPontoEntrega = pontoEntrega;
        PostPontoEditDialogFragment.mListener = listener;
        PostPontoEditDialogFragment.mCreating = mUserLatLng != null;
        PostPontoEditDialogFragment.activity = activity;
        PostPontoEditDialogFragment.mUserLatLng = mUserLatLng;
        PostPontoEditDialogFragment.lagradouro = lagradouro;
        PostPontoEditDialogFragment.mPost = post;
        if (PostPontoEditDialogFragment.mCreating) {
            PostPontoEditDialogFragment.mUserLatLng = mUserLatLng;
        }
        return new PostPontoEditDialogFragment();
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
                for (int i = 0; i < photosList.size(); i++) {
                    PontoEntregaPhotos p = photosList.get(i);
                    if (p.getNumber().equals(value)) {
                        toRemove = i;
                        break;
                    }
                }
                photosList.remove(toRemove);
            }
        });
        l.addView(lEdit);
        l.addView(b);
        layoutImages_ponto_entrega.addView(l);
        if (isNew) {
            Calendar cc = Calendar.getInstance();
            //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(cc.getTime());
            LogUtils.log("Date = " + date);
            photosList.add(new PontoEntregaPhotos(value, date));
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

        //   post = new Post();
        // criaForm();
        photosList = new ArrayList<>();
        View view = inflater.inflate(R.layout.fragment_pontoentrega_edit, container, false);
        mSetStart = (Button) view.findViewById(R.id.button_set_start);
        mImageAdd = (Button) view.findViewById(R.id.button_add);
        layoutImages_ponto_entrega = (LinearLayout) view.findViewById(R.id.layoutImages_ponto_entrega);
        mScrollView = (ScrollView) view.findViewById(R.id.scroll_ponto_entrega);
        mLayoutImages = (LinearLayout) view.findViewById(R.id.layoutImages);
        post_seq_tipoDemanda = (TextView) view.findViewById(R.id.post_seq_tipoDemanda);
        post_seq_classficacao = (TextView) view.findViewById(R.id.post_seq_classficacao);
        final Button back_button_ponto_entrega, next_button_ponto_entrega;
        back_button_ponto_entrega = (Button) view.findViewById(R.id.back_button_ponto_entrega);
        next_button_ponto_entrega = (Button) view.findViewById(R.id.next_button_ponto_entrega);
        gridTipoDemanda = (GridView) view.findViewById(R.id.gridTipoDemanda);
        grid_tipo_comercio = (GridView) view.findViewById(R.id.grid_tipo_comercio);
        viewFlipper = (ViewFlipper) view.findViewById(R.id.vs_ponto_entrega);
        grid_classificacao = (GridView) view.findViewById(R.id.grid_classificacao);
        post_seq_nunero_local = (TextView) view.findViewById(R.id.post_seq_nunero_local);
        post_seq_numero_total_apartamentos = (TextView) view.findViewById(R.id.post_seq_numero_total_apartamentos);
        post_seq_numero_andares_edificio = (TextView) view.findViewById(R.id.post_seq_numero_andares_edificio);
        post_seq_nome_edificio = (TextView) view.findViewById(R.id.post_seq_nome_edificio);
        edt_nome_edificio = (EditText) view.findViewById(R.id.edt_nome_edificio);
        edt_numero_andar_edificio = (EditText) view.findViewById(R.id.edt_numero_andar_edificio);
        edt_numero_total_apartamentos = (EditText) view.findViewById(R.id.edt_numero_total_apartamentos);
        edt_numero_local = (EditText) view.findViewById(R.id.edt_numero_local);
       // btn_mudar_posicao = (Button) view.findViewById(R.id.btn_mudar_posicao);
        linear_numero_andar_edificio = (LinearLayout) view.findViewById(R.id.linear_numero_andar_edificio);
        gridNumeroLocal = (GridView) view.findViewById(R.id.gridNumeroLocal);
        gridNumeroAndarLocal = (GridView) view.findViewById(R.id.gridNumeroAndarLocal);
        gridNumeroTotalApartamentos = (GridView) view.findViewById(R.id.gridNumeroTotalApartamentos);
        grid_domicilio = (GridView) view.findViewById(R.id.grid_domicilio);
        post_seq_domicilio = (TextView) view.findViewById(R.id.post_seq_domicilio);
        post_seq_tipoComercio = (TextView) view.findViewById(R.id.post_seq_tipoComercio);
        post_seq_quantidade_salas = (TextView) view.findViewById(R.id.post_seq_quantidade_salas);
        grid_qtd_salas = (GridView) view.findViewById(R.id.grid_qtd_salas);

        next_button_ponto_entrega.setText("Próximo");

        /*final Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);*/

        viewFlipper.setInAnimation(null);
        viewFlipper.setOutAnimation(null);

        final int fim = 8;
        final int[] count = {0};

        seq_numero_andares_edificio="";
        seq_numeto_total_apartamentos = "";
        seq_nome_edifio = "";

        //  builder.setView(view);

        //List<PostHeightGrid> postHeightGrids = Arrays.asList(PostHeight.values());

        final List<TipoDemanda> tipoDemandas = Arrays.asList(TipoDemanda.values());
        final List<DemandaClassficacao> demandaClassficacaos = Arrays.asList(DemandaClassficacao.values());
        final List<DemandaComplemento1> demandaComplemento1s = Arrays.asList(DemandaComplemento1.values());
        final List<DemandaComplemento2> demandaComplemento2s = Arrays.asList(DemandaComplemento2.values());
        final List<DemandaTipoImovel> demandaTipoImovels = Arrays.asList(DemandaTipoImovel.values());
        final List<NumeroLocal> numeroLocals = Arrays.asList(NumeroLocal.values());
        final List<NumeroLocal> numeroAndarLocal = Arrays.asList(NumeroLocal.values());
        final List<NumeroLocal> numeroTotalApartamentos = Arrays.asList(NumeroLocal.values());
        final List<DemandaDomicilio> demandaDomicilios = Arrays.asList(DemandaDomicilio.values());
        final List<TipoComercio> tipoComercios = Arrays.asList(TipoComercio.values());
        final List<QuatidadeSalas> quatidadeSalas = Arrays.asList(QuatidadeSalas.values());

        pontoEntregaTipoDemandaAdapter = new PontoEntregaTipoDemandaAdapter(getContext(), tipoDemandas);
        gridTipoDemanda.setAdapter(pontoEntregaTipoDemandaAdapter);

        demandaClassificacaoAdapter = new DemandaClassificacaoAdapter(getContext(), demandaClassficacaos);
        grid_classificacao.setAdapter(demandaClassificacaoAdapter);


        numeroLocalAdapter  = new NumeroLocalAdapter(getContext(), numeroLocals);
        gridNumeroLocal.setAdapter(numeroLocalAdapter);

        numeroAndarLocalAdapter = new NumeroLocalAdapter(getContext(), numeroAndarLocal);
        gridNumeroAndarLocal.setAdapter(numeroAndarLocalAdapter);

        numeroTotalApartamentosAdapter = new NumeroLocalAdapter(getContext(), numeroTotalApartamentos);
        gridNumeroTotalApartamentos.setAdapter(numeroTotalApartamentosAdapter);

        domicilioAdapter = new DomicilioAdapter(getContext(), demandaDomicilios);
        grid_domicilio.setAdapter(domicilioAdapter);

        tipoComercioAdapter = new TipoComercioAdapter(getContext(), tipoComercios);
        grid_tipo_comercio.setAdapter(tipoComercioAdapter);

        quatidadeSalasAdapter = new QuatidadeSalasAdapter(getContext(), quatidadeSalas);
        grid_qtd_salas.setAdapter(quatidadeSalasAdapter);

        seq_fase = "";


        gridNumeroLocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 10){
                    numeroLocal = "";

                }else if(position == 11) {
                    ++count[0];
                    viewFlipper.showNext();
                }else{
                    numeroLocal += numeroLocals.get(position).getNumero().toString();
                    seq_numero_local = numeroLocal;
                }

                edt_numero_local.setText(numeroLocal);
                post_seq_nunero_local.setText(numeroLocal+"");
            }
        });

        gridTipoDemanda.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                post_seq_tipoDemanda.setText("");
                post_seq_numero_total_apartamentos.setText("");
                post_seq_nome_edificio.setText("");
                post_seq_classficacao.setText("");
                post_seq_domicilio.setText("");
                post_seq_numero_andares_edificio.setText("");
                post_seq_tipoComercio.setText("");
                post_seq_quantidade_salas.setText("");

                seq_tipo_demanda = i;
                if (tipoDemandas.get(i).getTipo().equals("SEM INFORMACAO") || tipoDemandas.get(i).getTipo().equals("SEM_INFORMACAO")) {
                    post_seq_tipoDemanda.setText("...");
                } else {
                    post_seq_tipoDemanda.setText("/"+tipoDemandas.get(i).getTipo());
                }
                if(seq_tipo_demanda == 0){
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];

                }

                if(seq_tipo_demanda >= 1 && seq_tipo_demanda <=3){
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                }

                if(seq_tipo_demanda == 4){
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                }

                if(seq_tipo_demanda == 5){
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                }

                if(seq_tipo_demanda == 6){
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                }

                if(seq_tipo_demanda == 7){
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                }

                if(seq_tipo_demanda == 8){
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                }

                if (seq_tipo_demanda == 9){
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                }

                if (seq_tipo_demanda == 10){
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                }

                if (seq_tipo_demanda == 11){
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    ++count[0];
                }
            }
        });
        grid_classificacao.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                DemandaClassficacao demandaClassficacao = demandaClassficacaos.get(i);
                seq_classificacao = i;
                if (demandaClassficacaos.get(i).getClassificacao().equals("SEM INFORMACAO") || demandaClassficacaos.get(i).getClassificacao().equals("SEM_INFORMACAO")) {
                    post_seq_classficacao.setText("/...");
                } else {
                    post_seq_classficacao.setText("/" + demandaClassficacaos.get(i).getClassificacao());
                }

                if(seq_tipo_demanda == 0){
                    viewFlipper.showNext();
                    ++count[0];
                    return;
                }

                if(seq_tipo_demanda == 5){
                    if(count[0] == 5) {
                        viewFlipper.showNext();
                        ++count[0];
                        return;
                    }
                }

                if(seq_tipo_demanda == 8){
                    if(count[0] == 5) {
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        return;
                    }
                    if(count[0] == 2){
                        viewFlipper.showNext();
                        ++count[0];
                        return;
                    }
                    if(count[0] == 3){
                        viewFlipper.showNext();
                        viewFlipper.showNext();
                        viewFlipper.showNext();
                        viewFlipper.showNext();
                        viewFlipper.showNext();
                        ++count[0];
                        ++count[0];
                        ++count[0];
                        ++count[0];
                        ++count[0];
                        return;
                    }
                }

                if (seq_tipo_demanda == 11){
                    viewFlipper.showPrevious();
                    viewFlipper.showPrevious();
                    viewFlipper.showPrevious();
                    --count[0];
                    --count[0];
                    --count[0];
                    return;
                }
            }
        });

        grid_domicilio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DemandaDomicilio demandaDomicilio = demandaDomicilios.get(position);

                post_seq_domicilio.setText("/" +demandaDomicilio.getQtd());
                seq_domicilio = position;

                if(seq_tipo_demanda == 0){
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    next_button_ponto_entrega.setText("Salvar");

                }

                if (seq_tipo_demanda == 4){
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    next_button_ponto_entrega.setText("Salvar");
                }

                if (seq_tipo_demanda == 5){
                    viewFlipper.showNext();
                    ++count[0];
                }

                if (seq_tipo_demanda == 6){
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    next_button_ponto_entrega.setText("Salvar");
                }

                if (seq_tipo_demanda == 7){
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    ++count[0];
                    ++count[0];
                    ++count[0];
                    next_button_ponto_entrega.setText("Salvar");
                }

                if (seq_tipo_demanda == 10){
                    viewFlipper.showPrevious();
                    viewFlipper.showPrevious();
                    --count[0];
                    --count[0];
                }
            }
        });

//        grid_tipo_imovel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                DemandaTipoImovel demandaTipoImovel = demandaTipoImovels.get(i);
//
//                seq_tipo_imovel = demandaTipoImovel.getTipo();
//                post_seq_tipo_imovel.setText("/" + demandaTipoImovels.get(i).getTipo());
//                viewFlipper.showNext();
//                ++count[0];
//            }
//        });
//        grid_complemento1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                edt_complemento1.setText("");
//                DemandaComplemento1 demandaComplemento1 = demandaComplemento1s.get(i);
//
//                set_tipo_complemento1 = demandaComplemento1.getComplemento();
//                if (demandaComplemento1s.get(i).getComplemento().equals("SEM INFORMACAO") || demandaComplemento1s.get(i).getComplemento().equals("SEM_INFORMACAO")) {
//                    post_seq_complemento1.setText("/...");
//                } else {
//                    post_seq_complemento1.setText("/" + demandaComplemento1s.get(i).getComplemento());
//                }
//                viewFlipper.showNext();
//                ++count[0];
//            }
//        });
//
//        grid_complemento2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                edt_complemento2.setText("");
//                DemandaComplemento2 demandaComplemento2 = demandaComplemento2s.get(i);
//                set_tipo_complemento2 = demandaComplemento2.getComplemento();
//                post_seq_complemento2.setText("/" + demandaComplemento2s.get(i).getComplemento());
//                viewFlipper.showNext();
//                ++count[0];
//            }
//        });

        gridNumeroAndarLocal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 10){
                    numeroAndar = "";
                }else if(position == 11){
                        viewFlipper.showNext();
                        ++count[0];
                }
                else{
                    numeroAndar += numeroAndarLocal.get(position).getNumero().toString();
                    post_seq_numero_andares_edificio.setText("/"+numeroAndar);
                }

                edt_numero_andar_edificio.setText(numeroAndar);

            }
        });

        gridNumeroTotalApartamentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 10){
                    numeroAparatamentos = "";
                }else if(position == 11){
                        viewFlipper.showNext();
                        ++count[0];
                }
                else{
                    numeroAparatamentos += numeroTotalApartamentos.get(position).getNumero().toString();
                    post_seq_numero_total_apartamentos.setText("/"+numeroAparatamentos);
                }
                edt_numero_total_apartamentos.setText(numeroAparatamentos);
            }
        });

        grid_tipo_comercio.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                seq_tipo_comercio_position = position;
                TipoComercio tipoComercio = tipoComercios.get(position);
                post_seq_tipoComercio.setText("/" +tipoComercio.getNome());
                seq_tipoComercio = position;
                viewFlipper.showNext();
                ++count[0];
            }
        });

        grid_qtd_salas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QuatidadeSalas salas = quatidadeSalas.get(position);
                post_seq_quantidade_salas.setText("/" + salas.getNumero());
                seq_quantidade_salas = position;
                viewFlipper.showNext();
                next_button_ponto_entrega.setText("Salvar");
                ++count[0];
            }
        });


        final PostPontoEditDialogFragment c = this;

        mSetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Verifier.prompt(c.getContext());
            }
        });

        mImageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Verifier.getPontoNextNumber(mPontoEntrega.getGeoCode(), c);
                if (n > 0)
                    addImage(c.getContext(), String.format("%04d", n), true);
            }
        });

        if (mPontoEntrega.getPhotos() != null) {
            List<PontoEntregaPhotos> imagesList = mPontoEntrega.getPhotos();
            for (PontoEntregaPhotos pp : imagesList) {
                addImage(c.getContext(), pp.getNumber(), false);
                photosList.add(pp);
            }
        }


        if (mCreating) {
            ((TextView) view.findViewById(R.id.title))
                    .setText(R.string.dialog_ponto_entrega_edit_creating_title);

            Location l = mListener.getLastLocation();
            //mPontoEntrega.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis() / 1000));
            mPontoEntrega.setX_atualizacao(l.getLatitude());
            mPontoEntrega.setY_atualizacao(l.getLongitude());
            mPontoEntrega.setUpdate(true);
        } else {
            ((TextView) view.findViewById(R.id.title))
                    .setText(getString(R.string.dialog_post_edit_title, mPontoEntrega.getGeoCode()));
            //mTransaction.setChecked(mPost.isClosed());

            seq_tipo_demanda = mPontoEntrega.getTipoDemanda();
            seq_classificacao = mPontoEntrega.getClassificacao();
            seq_tipo_imovel = mPontoEntrega.getTipoImovel();
            set_tipo_complemento1 = mPontoEntrega.getComplemento1();
            set_tipo_complemento2 = mPontoEntrega.getComplemento2();

            List<CharSequence> tipoDemanda = TipoDemanda.getNames();
            CharSequence tipoDemandaEdit = tipoDemanda.get(mPontoEntrega.getTipoDemanda());

            post_seq_tipoDemanda.setText("/"+tipoDemandaEdit.toString());
            post_seq_classficacao.setText("/" + mPontoEntrega.getClassificacao());
            post_seq_tipo_imovel.setText("/" + mPontoEntrega.getTipoImovel());
            post_seq_complemento1.setText("/" + mPontoEntrega.getComplemento1());
            post_seq_complemento2.setText("/" + mPontoEntrega.getComplemento2());

            post_seq_nunero_local.setText(mPontoEntrega.getNumero_local());
            post_seq_numero_andares_edificio.setText("/"+mPontoEntrega.getNumero_andares_edificio());
            post_seq_numero_total_apartamentos.setText("/"+mPontoEntrega.getNumero_total_apartamentos());
            post_seq_nome_edificio.setText("/"+mPontoEntrega.getNome_edificio());
            /*edt_complemento1.setText(mPontoEntrega.getComplemento1());
            edt_complemento2.setText(mPontoEntrega.getComplemento2());*/

            edt_numero_local.setText(mPontoEntrega.getNumero_local()+"");
            edt_numero_andar_edificio.setText(mPontoEntrega.getNumero_andares_edificio()+"");
            edt_numero_total_apartamentos.setText(mPontoEntrega.getNumero_total_apartamentos()+"");
            edt_nome_edificio.setText(mPontoEntrega.getNome_edificio()+"");

            if(mPontoEntrega.getNumero_total_apartamentos() == 0){
                post_seq_numero_total_apartamentos.setVisibility(View.GONE);
            }
            if(mPontoEntrega.getNumero_andares_edificio() == 0){
                post_seq_numero_andares_edificio.setVisibility(View.GONE);
            }
            if(mPontoEntrega.getNome_edificio().equals("")){
                post_seq_nome_edificio.setVisibility(View.GONE);
            }

        }



        next_button_ponto_entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count[0] > fim) {
                                    mPontoEntrega.setTipoDemanda(seq_tipo_demanda);
                                    mPontoEntrega.setClassificacao(seq_classificacao);
                                    //mPontoEntrega.setTipoImovel(seq_tipo_imovel);
                                    mPontoEntrega.setNumero_local(seq_numero_local != null ? Integer.parseInt(seq_numero_local) : 0);
                                    mPontoEntrega.setNumero_andares_edificio(!seq_numero_andares_edificio.equals("")  ? Integer.parseInt(seq_numero_andares_edificio) : 0);
                                    mPontoEntrega.setNumero_total_apartamentos(!seq_numeto_total_apartamentos.equals("") ? Integer.parseInt(seq_numeto_total_apartamentos) : 0);
                                    mPontoEntrega.setNome_edificio(seq_nome_edifio);
                                    mPontoEntrega.setQtd_domicilio(seq_domicilio != 0 ? seq_domicilio : 0);
                                    mPontoEntrega.setQtd_salas(seq_quantidade_salas != 0 ? seq_quantidade_salas : 0);
                                    mPontoEntrega.setTipo_comercio(seq_tipoComercio);

//                                    if (!edt_complemento1.getText().toString().equals("")) {
//                                        mPontoEntrega.setComplemento1(edt_complemento1.getText().toString());
//                                    } else {
//                                        mPontoEntrega.setComplemento1(set_tipo_complemento1);
//                                    }
//                                    if (!edt_complemento2.getText().toString().equals("")) {
//                                        mPontoEntrega.setComplemento2(edt_complemento2.getText().toString());
//                                    } else {
//                                        mPontoEntrega.setComplemento2(set_tipo_complemento2);
//                                    }

                                    mPontoEntrega.setUpdate(true);
                                    mPontoEntrega.setPhotos(photosList);
                                    if (mPost != null) {
                                        mPontoEntrega.setPostId(mPost.getId());
                                    }


                                    //EquipmentUpdateUtils.setPontoAtualizacao(getContext(), mPost);
                                    Location l = mListener.getLastLocation();
                                    //mPontoEntrega.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis() / 1000));
                                    mPontoEntrega.setX_atualizacao(l.getLatitude());
                                    mPontoEntrega.setY_atualizacao(l.getLongitude());
                                    mPontoEntrega.setUpdate(true);

                                    final ProgressDialog alertDialog = new ProgressDialog(getActivity(), R.style.AlertDialogTheme);
                                    alertDialog.setTitle(getString(R.string.dialog_post_edit_saving_title, mPontoEntrega.getGeoCode()));
                                    alertDialog.setMessage(getString(R.string.dialog_post_edit_saving_message));
                                    alertDialog.setCancelable(false);
                                    alertDialog.show();


                                    mPontoEntrega.setPhotos(photosList);

                                    boolean workingOffline = FileUtils.serviceOrderFileExists(mPontoEntrega.getOrderId());
                                    if (mCreating) {
                                        mPontoEntrega.setClosed(true);
                                        mPontoEntrega.setUpdate(false);
                                        mPontoEntrega.setPostNumber(mListener.getNextPostNumber());

                                        LogUtils.log("highestPostNumber = " + mListener.getHighestPostNumber());
                                        mPontoEntrega.setLagradouro(lagradouro);

                                        if (workingOffline) {

                                            try {
                                                //OfflineDownloadResponse download = FileUtils.retrieve(mPontoEntrega.getOrderId());
                                                OfflineDownloadResponse download = null;
                                                if(DownloadOrderOffline.getResponse() == null){
                                                    DownloadOrderOffline.setResponse(FileUtils.retrieve(mPontoEntrega.getOrderId()));
                                                    download = DownloadOrderOffline.getResponse();
                                                }
                                                else{
                                                    download = DownloadOrderOffline.getResponse();
                                                }

                                                List<PontoEntrega> posts = download.getPontoEntregaList();
                                                List<VaosPontoPoste> vaosPontoPostes = download.getVaosPontoPosteList() == null ? new ArrayList<VaosPontoPoste>() : download.getVaosPontoPosteList();
                                                if (vaosPontoPostes == null) {
                                                    vaosPontoPostes = new ArrayList<>();
                                                }

                                                // vaosDeletar = new ArrayList<>();
                                               /* if(mPontoEntrega.getId() != 0){
                                                    long id_ponto = mPontoEntrega.getId();
                                                    for(VaosPontoPoste vaosPontoPoste : vaosPontoPostes){
                                                        if(vaosPontoPoste.getId_ponto_entrega() == id_ponto){
                                                            vaosDeletar = vaosPontoPoste;
                                                            return;
                                                        }
                                                    }
                                                }*/

                                                mPontoEntrega.setId(-1 * System.currentTimeMillis());
                                                mVaosPontoPoste = new VaosPontoPoste();
                                                mVaosPontoPoste.setId(-1 * System.currentTimeMillis());
                                                mVaosPontoPoste.setId_ponto_entrega(mPontoEntrega.getId());
                                                if(mPost != null) {
                                                    mVaosPontoPoste.setId_poste(mPost.getId());
                                                }
                                                mVaosPontoPoste.setId_ordem_servico(mPontoEntrega.getOrderId());



                                               /* SharedPreferences preferences = getActivity().getSharedPreferences("poste", Context.MODE_PRIVATE);
                                                String lat = preferences.getString("lat", null);
                                                String lon = preferences.getString("lon", null);*/


                                                if (mPost != null) {
                                                    mVaosPontoPoste.setX1(mPost.getLocation().getLat());
                                                    mVaosPontoPoste.setY1(mPost.getLocation().getLon());
                                                    mVaosPontoPoste.setX2(mPontoEntrega.getX());
                                                    mVaosPontoPoste.setY2(mPontoEntrega.getY());
                                                }
                                                vaosPontoPostes.add(mVaosPontoPoste);
                                                posts.add(mPontoEntrega);
                                                download.setVaosPontoPosteList(vaosPontoPostes);
                                                deletarPlyline = false;
                                                download.setPontoEntregaList(posts);

                                                if (FileUtils.saveOfflineDownload(download)) {
                                                    Verifier.clearRollBackStack();
                                                    Verifier.addPostCount(mPontoEntrega.getGeoCode());
                                                    Toast.makeText(getActivity(),
                                                            R.string.dialog_post_edit_saving_success,
                                                            Toast.LENGTH_LONG).show();
                                                    dismissAllowingStateLoss();
                                                    mListener.onPontoEntegasChanged(mPontoEntrega, true, mPost, deletarPlyline, true);

                                                } else {
                                                    mPontoEntrega.setClosed(false);
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

                                            PontoEntregaController.get().create(getContext(), mPontoEntrega, new VisiumApiCallback<PontoEntrega>() {
                                                @Override
                                                public void callback(PontoEntrega pontoEntrega, ErrorResponse e) {
                                                    alertDialog.dismiss();

                                                    if (e == null && pontoEntrega != null) {
                                                        Verifier.clearRollBackStack();
                                                        Verifier.addPostCount(mPontoEntrega.getGeoCode());
                                                        Toast.makeText(getActivity(),
                                                                R.string.dialog_post_edit_saving_success,
                                                                Toast.LENGTH_LONG).show();
                                                        dismissAllowingStateLoss();
                                                        mListener.onPontoEntegasChanged(pontoEntrega, true, mPost, deletarPlyline, true);
                                                    } else if (e != null && e.isCustomized()) {
                                                        mPontoEntrega.setClosed(false);
                                                        Toast.makeText(getActivity(),
                                                                e.getMessage(), Toast.LENGTH_LONG).show();
                                                    } else {
                                                        mPontoEntrega.setClosed(false);
                                                        Toast.makeText(getActivity(),
                                                                getString(R.string.dialog_post_edit_saving_error,
                                                                        mPontoEntrega.getId()),
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                        }

                                    } else {
                                        //mPost.setClosed(mTransaction.isChecked());

                                        if (workingOffline) {

                                            try {
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
                                                List<Post> postes = download.getPosts();
                                                List<VaosPontoPoste> vaosPontoPostes = download.getVaosPontoPosteList() == null ? new ArrayList<VaosPontoPoste>() : download.getVaosPontoPosteList();
                                                Iterator<PontoEntrega> iterator = pontoEntregas.iterator();

                                                while (iterator.hasNext()) {
                                                    PontoEntrega post = iterator.next();
                                                    if (post.getId() == mPontoEntrega.getId()) {
                                                        iterator.remove();
                                                        break;
                                                    }
                                                }

                                                for (Post post : postes){
                                                    if(post.getId() == mPontoEntrega.getPostId()){
                                                        mPost = post;
                                                    }
                                                }
                                                if (mPost != null) {
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
                                                }



                                                pontoEntregas.add(mPontoEntrega);
                                                download.setPontoEntregaList(pontoEntregas);
                                             //   download.setVaosPontoPosteList(vaosPontoPostes);

                                                if (FileUtils.saveOfflineDownload(download)) {
                                                    Verifier.addPostCount(mPontoEntrega.getGeoCode());
                                                    Toast.makeText(getActivity(),
                                                            R.string.dialog_post_edit_saving_success,
                                                            Toast.LENGTH_LONG).show();
                                                    dismissAllowingStateLoss();
                                                    mListener.onPontoEntegasChanged(mPontoEntrega, false, mPost, deletarPlyline, true);

                                                } else {
                                                    Toast.makeText(getActivity(),
                                                            getString(R.string.dialog_post_edit_saving_error,
                                                                    mPontoEntrega.getId()),
                                                            Toast.LENGTH_LONG).show();
                                                }
                                                alertDialog.dismiss();

                                            } catch (IOException e) {
                                                LogUtils.error(this, e);
                                                alertDialog.dismiss();

                                                Toast.makeText(getActivity(),
                                                        getString(R.string.dialog_post_edit_saving_error,
                                                                mPontoEntrega.getId()),
                                                        Toast.LENGTH_LONG).show();
                                            }

                                        } else {
                                            PontoEntregaController.get().save(getContext(), mPontoEntrega, new VisiumApiCallback<PontoEntrega>() {
                                                @Override
                                                public void callback(PontoEntrega pontoEntrega, ErrorResponse e) {
                                                    alertDialog.dismiss();

                                                    if (e == null && pontoEntrega != null) {
                                                        Verifier.addPostCount(mPontoEntrega.getGeoCode());
                                                        Toast.makeText(getActivity(),
                                                                R.string.dialog_post_edit_saving_success,
                                                                Toast.LENGTH_LONG).show();
                                                        dismissAllowingStateLoss();
                                                        mListener.onPontoEntegasChanged
                                                                (mPontoEntrega, false, mPost, deletarPlyline, true);
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
                } else {

                    if (count[0] == 0){
                        viewFlipper.showNext();
                        ++count[0];
                    }

                    if (seq_tipo_demanda == 0) {
                        if(count[0] == 5){
                            viewFlipper.showNext();
                            ++count[0];
                            return;
                        }
                        if(count[0] == 6){
                            viewFlipper.showNext();
                            ++count[0];
                            viewFlipper.showNext();
                            ++count[0];
                            viewFlipper.showNext();
                            ++count[0];
                            next_button_ponto_entrega.setText("Salvar");
                            return;
                        }

                    }

                    if(seq_tipo_demanda == 1 || seq_tipo_demanda == 2 || seq_tipo_demanda == 3){
                        if(count[0] == 7){
                            viewFlipper.showNext();
                            ++count[0];
                            return;
                        }
                        if(count[0] == 8){
                            viewFlipper.showNext();
                            ++count[0];
                            next_button_ponto_entrega.setText("Salvar");
                            return;
                        }
                    }

//                    if(post_seq_tipoDemanda.getText().toString().equals("EDIFÍCIO")){
//                        podePassar = false;
//                    }else{
//                        podePassar = true;
//                    }

                    if (seq_tipo_demanda == 4){
                        if (count[0] == 6) {
                            viewFlipper.showNext();
                            viewFlipper.showNext();
                            viewFlipper.showNext();
                            ++count[0];
                            ++count[0];
                            ++count[0];
                            next_button_ponto_entrega.setText("Salvar");
                            return;
                        }
                    }

                        if (seq_tipo_demanda == 10) {
                            if (count[0] == 4) {
                                viewFlipper.showNext();
                                viewFlipper.showNext();
                                viewFlipper.showNext();
                                viewFlipper.showNext();
                                viewFlipper.showNext();
                                ++count[0];
                                ++count[0];
                                ++count[0];
                                ++count[0];
                                ++count[0];
                                next_button_ponto_entrega.setText("Salvar");
                                return;
                            }
                            if(count[0] == 6){
                                viewFlipper.showPrevious();
                                viewFlipper.showPrevious();
                                --count[0];
                                --count[0];
                                return;
                            }

                        }

                        if (seq_tipo_demanda == 11){
                                if(count[0] == 5) {
                                    viewFlipper.showPrevious();
                                    viewFlipper.showPrevious();
                                    viewFlipper.showPrevious();
                                    --count[0];
                                    --count[0];
                                    --count[0];
                                    return;
                                }
                            if(count[0] == 2) {
                                viewFlipper.showNext();
                                ++count[0];
                                return;
                            }
                            if(count[0] == 3) {
                                viewFlipper.showNext();
                                ++count[0];
                                return;
                            }
                            if(count[0] == 4) {
                                viewFlipper.showNext();
                                viewFlipper.showNext();
                                viewFlipper.showNext();
                                viewFlipper.showNext();
                                viewFlipper.showNext();
                                ++count[0];
                                ++count[0];
                                ++count[0];
                                ++count[0];
                                ++count[0];
                                next_button_ponto_entrega.setText("Salvar");
                                return;
                            }
                        }

                    if (seq_tipo_demanda == 5) {
                        if(count[0] == 5){
                            viewFlipper.showNext();
                            ++count[0];
                            return;
                        }
                        if(count[0] == 6){
                            viewFlipper.showNext();
                            ++count[0];
                            return;
                        }
                        if(count[0] == 7){
                            viewFlipper.showNext();
                            ++count[0];
                            return;
                        }
                        if(count[0] == 8){
                            viewFlipper.showNext();
                            ++count[0];
                            next_button_ponto_entrega.setText("Salvar");
                            return;
                        }
                    }

                    if (seq_tipo_demanda == 6) {
                        if(count[0] == 6) {
                            viewFlipper.showNext();
                            viewFlipper.showNext();
                            viewFlipper.showNext();
                            ++count[0];
                            ++count[0];
                            ++count[0];
                            next_button_ponto_entrega.setText("Salvar");
                            return;
                        }
                    }

                    if(seq_tipo_demanda == 7){
                        if(count[0] == 6){
                            viewFlipper.showPrevious();
                            viewFlipper.showPrevious();
                            viewFlipper.showPrevious();
                            ++count[0];
                            ++count[0];
                            ++count[0];
                            next_button_ponto_entrega.setText("Salvar");
                            return;
                        }
                    }

                    if(seq_tipo_demanda == 8){
                        if(count[0] == 5) {
                            viewFlipper.showPrevious();
                            viewFlipper.showPrevious();
                            viewFlipper.showPrevious();
                            --count[0];
                            --count[0];
                            --count[0];
                            return;
                        }
                        if(count[0] == 2){
                            viewFlipper.showNext();
                            ++count[0];
                            return;
                        }
                        if(count[0] == 3){
                            viewFlipper.showNext();
                            ++count[0];
                            return;
                        }
                        if(count[0] == 4){
                            viewFlipper.showPrevious();
                            viewFlipper.showPrevious();
                            viewFlipper.showPrevious();
                            viewFlipper.showPrevious();
                            viewFlipper.showPrevious();
                            ++count[0];
                            ++count[0];
                            ++count[0];
                            ++count[0];
                            ++count[0];
                            next_button_ponto_entrega.setText("Salvar");
                            return;
                        }
                    }

                    if (seq_tipo_demanda == 9){
                        if(count[0] == 7) {
                            viewFlipper.showNext();
                            ++count[0];
                            return;
                        }
                        if(count[0] == 8) {
                            viewFlipper.showNext();
                            ++count[0];
                            next_button_ponto_entrega.setText("Salvar");
                            return;
                        }
                    }
                }
//                    if (count[0] == 1) {
//                            seq_numero = edt_numero_local.getText().toString().toUpperCase();
//                            seq_numero_local = edt_numero_local.getText().toString().toUpperCase();
//                            post_seq_nunero_local.setText("/" + seq_numero_local);
//                        if(podePassar){
//                            viewFlipper.showNext();
//                            viewFlipper.showNext();
//                            viewFlipper.showNext();
//                            ++count[0];
//                            ++count[0];
//                            ++count[0];
//                        }
//                    }
//                    if(!podePassar){
//                        if (count[0] == 2) {
//                            seq_numero_andares_edificio = edt_numero_andar_edificio.getText().toString().toUpperCase();
//                            post_seq_numero_andares_edificio.setText("/" + seq_numero_andares_edificio);
//                        }
//                        if (count[0] == 3) {
//                            seq_numeto_total_apartamentos = edt_numero_total_apartamentos.getText().toString().toUpperCase();
//                            post_seq_numero_total_apartamentos.setText("/" + seq_numeto_total_apartamentos);
//                        }
//                        if (count[0] == 4) {
//                            seq_nome_edifio = edt_nome_edificio.getText().toString().toUpperCase();
//                            post_seq_nome_edificio.setText("/" + seq_nome_edifio);
//                        }
//                    }
//
//                    if(count[0] == 7) {
//                        if (seq_tipo_demanda >= 2 && seq_tipo_demanda <= 4) {
//                            viewFlipper.showNext();
//                            viewFlipper.showNext();
//                            ++count[0];
//                            ++count[0];
//                        }
//                    }

//                    if (count[0] == 7) {
//                        if (!edt_complemento1.getText().toString().equals("")) {
//                            post_seq_complemento1.setText("/" + edt_complemento1.getText().toString().toUpperCase());
//                        }
//                    }
//                    if (count[0] == 8) {
//                        if (!edt_complemento2.getText().toString().equals("")) {
//                            post_seq_complemento2.setText("/" + edt_complemento2.getText().toString().toUpperCase());
//                        }
//                    }

//                    count[0]++;
//                    if (count[0] > fim) {
//                        next_button_ponto_entrega.setText("Salvar");
//                        Drawable image = ContextCompat.getDrawable(getContext(), R.drawable.ic_check_black_24dp);
//                        image.setBounds(0, 0, 60, 60);
//                        next_button_ponto_entrega.setCompoundDrawables(null, null, image, null);
//                    } else {
//
//                    }

            }
        });

        back_button_ponto_entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count[0] == 1){
                    viewFlipper.showPrevious();
                    --count[0];
                }

                if(seq_tipo_demanda == 8){

                    if(count[0] == 5){
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        return;
                    }

                    if(count[0] == 4){
                        viewFlipper.showPrevious();
                        --count[0];
                        return;
                    }
                    if(count[0] == 3){
                        viewFlipper.showPrevious();
                        --count[0];
                        return;
                    }
                    if(count[0] == 2){
                        viewFlipper.showNext();
                        viewFlipper.showNext();
                        viewFlipper.showNext();
                        ++count[0];
                        ++count[0];
                        ++count[0];
                        return;
                    }

                    if(count[0] == 9){
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        next_button_ponto_entrega.setText("Próximo");
                        return;
                    }
                }

                if (seq_tipo_demanda == 0) {
                    if (count[0] == 5) {
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        return;
                    }
                    if(count[0] == 6){
                        viewFlipper.showPrevious();
                        --count[0];
                        return;
                    }
                    if(count[0] == 9){
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        next_button_ponto_entrega.setText("Próximo");
                        return;
                    }
                }

                if(seq_tipo_demanda == 1 || seq_tipo_demanda == 2 || seq_tipo_demanda == 3){
                    if(count[0] == 7){
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        return;
                    }
                    if(count[0] == 8){
                        viewFlipper.showPrevious();
                        --count[0];
                        return;
                    }
                    if(count[0] == 9){
                        viewFlipper.showPrevious();
                        --count[0];
                        next_button_ponto_entrega.setText("Próximo");
                        return;
                    }
                }

                if (seq_tipo_demanda == 4){
                    if (count[0] == 6) {
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        return;
                    }
                    if(count[0] == 9){
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        next_button_ponto_entrega.setText("Próximo");
                        return;
                    }
                }

                if (seq_tipo_demanda == 5) {
                    if(count[0] == 5){
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        return;
                    }
                    if(count[0] == 6){
                        viewFlipper.showPrevious();
                        --count[0];
                        return;
                    }
                    if(count[0] == 7){
                        viewFlipper.showPrevious();
                        --count[0];
                        return;
                    }
                    if(count[0] == 8){
                        viewFlipper.showPrevious();
                        --count[0];
                        return;
                    }
                    if(count[0] == 9){
                        viewFlipper.showPrevious();
                        --count[0];
                        next_button_ponto_entrega.setText("Próximo");
                        return;
                    }
                }
                if(seq_tipo_demanda == 6){
                    if(count[0] == 6){
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        return;
                    }
                    if(count[0] == 9){
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        next_button_ponto_entrega.setText("Próximo");
                        return;
                    }
                }

                if(seq_tipo_demanda == 7){
                    if(count[0] == 6) {
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        return;
                    }
                    if(count[0] == 9) {
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        next_button_ponto_entrega.setText("Próximo");
                        return;
                    }
                }

                if (seq_tipo_demanda == 9){
                    if (count[0] == 7) {
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        next_button_ponto_entrega.setText("Próximo");
                        return;
                    }

                    if(count[0] == 8) {
                        viewFlipper.showPrevious();
                        --count[0];
                        return;
                    }
                    if(count[0] == 9) {
                        viewFlipper.showPrevious();
                        --count[0];
                        next_button_ponto_entrega.setText("Próximo");
                        return;
                    }
                }

                if (seq_tipo_demanda == 10){
                    if(count[0] == 6) {
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        return;
                    }
                }

                if (seq_tipo_demanda == 11){
                    if(count[0] == 5) {
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        return;
                    }
                    if(count[0] == 2) {
                        viewFlipper.showNext();
                        viewFlipper.showNext();
                        viewFlipper.showNext();
                        ++count[0];
                        ++count[0];
                        ++count[0];
                        return;
                    }

                    if(count[0] == 3) {
                        viewFlipper.showPrevious();
                        --count[0];
                        return;
                    }
                    if(count[0] == 4) {
                        viewFlipper.showPrevious();
                        --count[0];
                        return;
                    }
                    if(count[0] == 9){
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        viewFlipper.showPrevious();
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        --count[0];
                        next_button_ponto_entrega.setText("Próximo");
                        return;
                    }
                }
//                // Volta para o tipo demanda.
//                if(count[0] == 7) {
//                    if (seq_tipo_demanda >= 2 && seq_tipo_demanda <= 4) {
//                        viewFlipper.showPrevious();
//                        viewFlipper.showPrevious();
//                        viewFlipper.showPrevious();
//                        viewFlipper.showPrevious();
//                        viewFlipper.showPrevious();
//                        --count[0];
//                        --count[0];
//                        --count[0];
//                        --count[0];
//                        --count[0];
//                    }
//                }
//
//                if(count[0] == 8) {
//                    if (seq_tipo_demanda >= 2 && seq_tipo_demanda <= 4) {
//                    }else{
//                        viewFlipper.showPrevious();
//                        --count[0];
//                    }
//                }
//
//                if (count[0] == 5) {
//                    if(seq_tipo_demanda == 1){
//                        viewFlipper.showPrevious();
//                        viewFlipper.showPrevious();
//                        viewFlipper.showPrevious();
//                        --count[0];
//                        --count[0];
//                        --count[0];
//                    }
//                }
//
//                if (count[0] > 0) {
//                    if(count[0] == 5){
//                        if(podePassar) {
//                            viewFlipper.showPrevious();
//                            viewFlipper.showPrevious();
//                            viewFlipper.showPrevious();
//                            --count[0];
//                            --count[0];
//                            --count[0];
//                        }
//                    }
//                    viewFlipper.showPrevious();
//                    --count[0];
//                    next_button_ponto_entrega.setText("Próximo");
//                    Drawable image = ContextCompat.getDrawable(getContext(), R.drawable.ic_navigate_next_black_24dp);
//                    image.setBounds(0, 0, 60, 60);
//                    next_button_ponto_entrega.setCompoundDrawables(null, null, image, null);
//                } else {
//                    Verifier.rollBack(mPontoEntrega.getGeoCode());
//                    dismissAllowingStateLoss();
//                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICKUP_LOCATION) {

            if (data != null && data.hasExtra(MapsPickLocationActivity.PICK_LOCATION)) {
                LatLng latLng = data.getParcelableExtra(MapsPickLocationActivity.PICK_LOCATION);
                LogUtils.log("onMapPickLocationActivityResult");
                mPontoEntrega.setX(latLng.latitude);
                mPontoEntrega.setY(latLng.longitude);
                //mPontoEntrega.setLocation(new PontoEntregaLocation(latLng.latitude, latLng.longitude));
                //mLat.setText(String.valueOf(latLng.latitude));
                //mLon.setText(String.valueOf(latLng.longitude));
            } else {
                Toast.makeText(getActivity(),
                        R.string.dialog_post_edit_pickup_location_error,
                        Toast.LENGTH_LONG).show();
            }

        }
    }
}