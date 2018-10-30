package com.visium.fieldservice.ui.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.controller.LightingController;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.Fase;
import com.visium.fieldservice.entity.Lighting;
import com.visium.fieldservice.entity.LightingArmType;
import com.visium.fieldservice.entity.LightingLampType;
import com.visium.fieldservice.entity.LightingLightFixtureType;
import com.visium.fieldservice.entity.LightingPowerRating;
import com.visium.fieldservice.entity.LightingStatus;
import com.visium.fieldservice.entity.LightingTrigger;
import com.visium.fieldservice.entity.PontoAtualizacao;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.QtdLuminarias;
import com.visium.fieldservice.ui.dialog.adapter.AcionamentoAdapter;
import com.visium.fieldservice.ui.dialog.adapter.FaseAdapter;
import com.visium.fieldservice.ui.dialog.adapter.LampadaAcessaAdapter;
import com.visium.fieldservice.ui.dialog.adapter.PotenciaAdapter;
import com.visium.fieldservice.ui.dialog.adapter.QtdLuminariaAdapter;
import com.visium.fieldservice.ui.dialog.adapter.TipoBracoAdapter;
import com.visium.fieldservice.ui.dialog.adapter.TipoLampadaAdapter;
import com.visium.fieldservice.ui.dialog.adapter.TipoLuminariaAdapter;
import com.visium.fieldservice.ui.util.EquipmentUpdateUtils;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.LogUtils;
import com.visium.fieldservice.ui.util.ViewUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class LightingEditDialogFragment extends AppCompatDialogFragment {

    private static Lighting mLighting;
    private static LightingEditDialogListener mListener;
    private static Post mPost;
    private static long mOrderId;
    private static final String REPLACE_REGEX = "[^ABCNVabcnv]+";

    private Spinner mArmType;
    private Spinner mFixtureType;
    private EditText mFixtureAmount;
    private Spinner mLampType;
    private Spinner mPowerRating;
    private EditText mPowerPhase;
    private Spinner mTrigger;
    private Spinner mStatus;
    private EditText mLampAmount;
    private ViewFlipper viewFlipper;
    private GridView gridTipoBraco, gridTipoLuminaria, gridTipoLampada, gridAcionamento, gridLamapdaAcessa, gridPotencia, gridFase
            ,gridQtdLuminaria;
    private ViewFlipper vf_ilumination;
    private Button voltar, salvar;
    private TextView ip_seq_tipo_braco, ip_seq_tipo_luminaria, ip_seq_qtd_luminaria, ip_seq_tipo_lampada, ip_seq_potencia, ip_seq_fase,
            ip_seq_acionamento, ip_seq_lampada_acessa, ip_seq_qtd_lampadas;
    private static EditText edit_qtd_luminaria, edit_potencia, edit_faze, editQtdLampadas;
    private int tipoBraco, tipoLuminaria, tipoLampada, acionamento, lampadaAcesa;
    private int potencia;
    private String seq_fase;

    private InputFilter powerPhaseFilter = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (StringUtils.isNotBlank(source)) {
                return source.toString().replaceAll(REPLACE_REGEX, StringUtils.EMPTY).toUpperCase();
            } else {
                return null;
            }
        }
    };

    public static AppCompatDialogFragment newInstance(LightingEditDialogListener listener,
                                                      Lighting lighting, Post post, long orderId) {
        LightingEditDialogFragment.mLighting = lighting;
        LightingEditDialogFragment.mListener = listener;
        LightingEditDialogFragment.mPost = post;
        LightingEditDialogFragment.mOrderId = orderId;
        return new LightingEditDialogFragment();
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

        View view = inflater.inflate(R.layout.fragment_lighting_edit, container, false);

        /*mArmType = (Spinner) view.findViewById(R.id.lighting_arm_type);
        mFixtureType = (Spinner) view.findViewById(R.id.lighting_fixture_type);
        mFixtureAmount = (EditText) view.findViewById(R.id.lighting_fixture_amount);
        mLampType = (Spinner) view.findViewById(R.id.lighting_lamp_type);
        mPowerRating = (Spinner) view.findViewById(R.id.lighting_power_rating);
        mPowerPhase = (EditText) view.findViewById(R.id.lighting_power_phase);
        mTrigger = (Spinner) view.findViewById(R.id.lighting_trigger);
        mStatus = (Spinner) view.findViewById(R.id.lighting_status);
        mLampAmount = (EditText) view.findViewById(R.id.lighting_lamp_amount);*/

        viewFlipper  = (ViewFlipper) view.findViewById(R.id.vf_ilumination);
        gridTipoBraco = (GridView) view.findViewById(R.id.gridTipoBraco);
        vf_ilumination = (ViewFlipper) view.findViewById(R.id.vf_ilumination);
        gridTipoLuminaria = (GridView) view.findViewById(R.id.gridTipoLuminaria);
        gridTipoLampada = (GridView) view.findViewById(R.id.gridTipoLampada);
        gridAcionamento = (GridView) view.findViewById(R.id.gridAcionamento);
        gridLamapdaAcessa = (GridView) view.findViewById(R.id.gridLamapdaAcessa);
        gridPotencia = (GridView) view.findViewById(R.id.gridPotencia);
        gridFase = (GridView) view.findViewById(R.id.gridFase);
        gridQtdLuminaria = (GridView) view.findViewById(R.id.gridQtdLuminaria);

        ip_seq_tipo_braco = (TextView) view.findViewById(R.id.ip_seq_tipo_braco);
        ip_seq_tipo_luminaria = (TextView) view.findViewById(R.id.ip_seq_tipo_luminaria);
        ip_seq_tipo_lampada = (TextView) view.findViewById(R.id.ip_seq_tipo_lampada);
        ip_seq_potencia = (TextView) view.findViewById(R.id.ip_seq_potencia);
        ip_seq_fase = (TextView) view.findViewById(R.id.ip_seq_fase);
        ip_seq_tipo_braco = (TextView) view.findViewById(R.id.ip_seq_tipo_braco);
        ip_seq_qtd_luminaria = (TextView) view.findViewById(R.id.ip_seq_qtd_luminaria);
        ip_seq_acionamento = (TextView) view.findViewById(R.id.ip_seq_acionamento);
        ip_seq_lampada_acessa = (TextView) view.findViewById(R.id.ip_seq_lampada_acessa);
        ip_seq_qtd_lampadas = (TextView) view.findViewById(R.id.ip_seq_qtd_lampadas);

        // edit_qtd_luminaria = (EditText) view.findViewById(R.id.edit_qtd_luminaria);
        //
        // edit_potencia = (EditText) view.findViewById(R.id.edit_potencia);
       // edit_faze = (EditText) view.findViewById(R.id.edit_faze);
        editQtdLampadas = (EditText) view.findViewById(R.id.editQtdLampadas);


        voltar = (Button) view.findViewById(R.id.cancel_button);
        salvar = (Button) view.findViewById(R.id.save_button);

        Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);

        viewFlipper.setInAnimation(in);
        viewFlipper.setOutAnimation(out);

        final int[] count = {0};
        final int fim = 8;

        final List<LightingArmType> tipobracoStatus = Arrays.asList(LightingArmType.values());
        TipoBracoAdapter tipoBracoAdapter = new TipoBracoAdapter(getContext(),tipobracoStatus);
        gridTipoBraco.setAdapter(tipoBracoAdapter);
        gridTipoBraco.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(tipobracoStatus.get(i).getName().equals("SEM INFORMACAO")){
                    ip_seq_tipo_braco.setText("...");
                }else{
                    ip_seq_tipo_braco.setText(tipobracoStatus.get(i).getName());
                }

                tipoBraco =  i;
                ++count[0];
                viewFlipper.showNext();
            }
        });

        final List<LightingLightFixtureType> lightingLightFixtureTypes = Arrays.asList(LightingLightFixtureType.values());
        TipoLuminariaAdapter tipoLuminariaAdapter = new TipoLuminariaAdapter(getContext(),lightingLightFixtureTypes);
        gridTipoLuminaria.setAdapter(tipoLuminariaAdapter);

        gridTipoLuminaria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                if(lightingLightFixtureTypes.get(i).getName().equals("SEM INFORMACAO")){
                    ip_seq_tipo_luminaria.setText("/...");
                }else{
                    ip_seq_tipo_luminaria.setText("/"+lightingLightFixtureTypes.get(i).getName());
                }

                tipoLuminaria = i;
                ++count[0];
                viewFlipper.showNext();
            }
        });

        final List<LightingLampType> lightingLampTypes = Arrays.asList(LightingLampType.values());
        TipoLampadaAdapter tipoLampadaAdapter = new TipoLampadaAdapter(getContext(),lightingLampTypes);
        gridTipoLampada.setAdapter(tipoLampadaAdapter);
        gridTipoLampada.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(lightingLampTypes.get(i).getName().equals("SEM INFORMACAO")){
                    ip_seq_tipo_lampada.setText("/...");
                }else{
                    ip_seq_tipo_lampada.setText("/"+lightingLampTypes.get(i).getName());
                }
                tipoLampada = i;
                ++count[0];
                viewFlipper.showNext();
            }
        });

        final List<LightingPowerRating> lightingPowerRatings = Arrays.asList(LightingPowerRating.values());
        PotenciaAdapter potenciaAdapter = new PotenciaAdapter(getContext(),lightingPowerRatings);
        gridPotencia.setAdapter(potenciaAdapter);

        gridPotencia.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ip_seq_potencia.setText(lightingPowerRatings.get(i).getRating()+"");
                potencia = i;
                ++count[0];
                viewFlipper.showNext();
            }
        });

        final List<LightingTrigger> lightingTriggers = Arrays.asList(LightingTrigger.values());
        AcionamentoAdapter acionamentoAdapter = new AcionamentoAdapter(getContext(),lightingTriggers);
        gridAcionamento.setAdapter(acionamentoAdapter);
        gridAcionamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(lightingTriggers.get(i).getName().equals("SEM INFORMACAO")){
                    ip_seq_acionamento.setText("/...");
                }else{
                    ip_seq_acionamento.setText("/"+lightingTriggers.get(i).getName());
                }
                acionamento = i;
                ++count[0];
                viewFlipper.showNext();
            }
        });

        final List<LightingStatus> lightingStatusList = Arrays.asList(LightingStatus.values());
        LampadaAcessaAdapter lampadaAcessaAdapter = new LampadaAcessaAdapter(getContext(),lightingStatusList);
        gridLamapdaAcessa.setAdapter(lampadaAcessaAdapter);
        gridLamapdaAcessa.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ip_seq_lampada_acessa.setText("/"+lightingStatusList.get(i).getName());
                lampadaAcesa = i;
                ++count[0];
                salvar.setText("Salvar");
            }
        });

        final List<Fase> faseList = Arrays.asList(Fase.values());
        FaseAdapter faseAdapter = new FaseAdapter(getContext(), faseList);
        gridFase.setAdapter(faseAdapter);
        gridFase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(faseList.get(i).getName().equals("SEM INFORMACAO")){
                    ip_seq_fase.setText("/...");
                }else{
                    seq_fase = faseList.get(i).getName();
                    ip_seq_fase.setText("/"+seq_fase);
                }

                ++count[0];
                viewFlipper.showNext();
            }
        });

        final List<QtdLuminarias> qtdLuminariases = Arrays.asList(QtdLuminarias.values());
        QtdLuminariaAdapter luminariaAdapter = new QtdLuminariaAdapter(getContext(), qtdLuminariases);
        gridQtdLuminaria.setAdapter(luminariaAdapter);
        gridQtdLuminaria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(qtdLuminariases.get(i).getName().equals("SEM INFORMACAO")){
                    ip_seq_qtd_luminaria.setText("/...");
                }else{
                    ip_seq_qtd_luminaria.setText("/"+qtdLuminariases.get(i).getName());
                }

                ++count[0];
                viewFlipper.showNext();
            }
        });

       /* mArmType.setAdapter(
                new ArrayAdapter<>(getContext(),
                        R.layout.custom_spinner_item,
                        LightingArmType.getNames()));
        mArmType.setSelection(LightingArmType.SEM_INFORMACAO.ordinal());

        mFixtureType.setAdapter(
                new ArrayAdapter<>(getContext(),
                        R.layout.custom_spinner_item,
                        LightingLightFixtureType.getNames()));
        mFixtureType.setSelection(LightingLightFixtureType.SEM_INFORMACAO.ordinal());

        mLampType.setAdapter(
                new ArrayAdapter<>(getContext(),
                        R.layout.custom_spinner_item,
                        LightingLampType.getNames()));
        mLampType.setSelection(LightingLampType.SEM_INFORMACAO.ordinal());

        mPowerRating.setAdapter(
                new ArrayAdapter<>(getContext(),
                        R.layout.custom_spinner_item,
                        LightingPowerRating.getRates()));
        mPowerRating.setSelection(LightingPowerRating._0.ordinal());

        mTrigger.setAdapter(
                new ArrayAdapter<>(getContext(),
                        R.layout.custom_spinner_item,
                        LightingTrigger.getNames()));
        mTrigger.setSelection(LightingTrigger.SEM_INFORMACAO.ordinal());

        mStatus.setAdapter(
                new ArrayAdapter<>(getContext(),
                        R.layout.custom_spinner_item,
                        LightingStatus.getNames()));
        mStatus.setSelection(LightingStatus.NAO.ordinal());

        mPowerPhase.setFilters(new InputFilter[]{powerPhaseFilter});
        mFixtureAmount.setText(String.valueOf(0));
        mLampAmount.setText(String.valueOf(0));*/

        if (mLighting == null) {
            mLighting = new Lighting();
            mLighting.setPostId(mPost.getId());
            mLighting.setUpdate(true);
            mPost.setUpdate(true);
            Location l = mListener.getLastLocation();
            mLighting.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis()/1000));
            mPost.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis()/1000));

            ((TextView) view.findViewById(R.id.lighting_title))
                    .setText(R.string.dialog_lighting_edit_create_title);
        } else {
            ((TextView) view.findViewById(R.id.lighting_title))
                    .setText(getString(R.string.dialog_lighting_edit_title,
                            mLighting.getId()));


            ip_seq_tipo_braco.setText(mLighting.getArmType().getName().toString());
            ip_seq_tipo_luminaria.setText("/"+mLighting.getLightFixtureType().getName());
            ip_seq_tipo_lampada.setText("/"+mLighting.getLampType().toString());
            ip_seq_potencia.setText(+mLighting.getPowerRating().getRating()+"");
            ip_seq_fase.setText("/"+mLighting.getPowerPhase());
            ip_seq_qtd_luminaria.setText("/"+mLighting.getLightFixtureAmount()+"");
            ip_seq_acionamento.setText("/"+mLighting.getTrigger().getName());
            ip_seq_lampada_acessa.setText("/"+mLighting.getStatus().getName());
            ip_seq_qtd_lampadas.setText("/"+mLighting.getLampAmount()+"");

          //  edit_qtd_luminaria.setText(+mLighting.getLightFixtureAmount()+"" == null ? 0+"" : mLighting.getLightFixtureAmount()+"");
          //  edit_faze.setText(mLighting.getPowerPhase()+"");
            editQtdLampadas.setText(mLighting.getLampAmount()+"" == null ? 0+"" : mLighting.getLampAmount()+"");

/*
         mArmType.setSelection(mLighting.getArmType().ordinal());
            mFixtureType.setSelection(mLighting.getLightFixtureType().ordinal());
            mLampType.setSelection(mLighting.getLampType().ordinal());
            mPowerRating.setSelection(mLighting.getPowerRating().ordinal());
            mTrigger.setSelection(mLighting.getTrigger().ordinal());
            mStatus.setSelection(mLighting.getStatus().ordinal());*/

/*            mFixtureAmount.setText(String.valueOf(mLighting.getLightFixtureAmount()));
            mPowerPhase.setText(mLighting.getPowerPhase());
            mLampAmount.setText(String.valueOf(mLighting.getLampAmount()));*/
        }

       voltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count[0] > 0){
                    viewFlipper.showPrevious();
                    --count[0];
                    salvar.setText("Próximo");
                }else {
                    dismissAllowingStateLoss();
                }

            }
        });
        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  if(!edit_qtd_luminaria.getText().toString().equals("")){
                    ip_seq_qtd_luminaria.setText("/"+edit_qtd_luminaria.getText().toString().toUpperCase());
                }*/

             /*   if(!edit_faze.getText().toString().equals("")){
                    ip_seq_fase.setText("/"+edit_faze.getText().toString().toUpperCase());
                }*/

                if(!editQtdLampadas.getText().toString().equals("")){
                    ip_seq_qtd_lampadas.setText("/"+editQtdLampadas.getText().toString());
                }

                /*if(!edit_tipo_lampada.getText().toString().equals("")){
                    ip_seq_qtd_luminaria.setText("/"+edit_tipo_lampada.getText().toString());
                }*/

                if(count[0] == 6){
                    if(ip_seq_fase.getText().toString().equals("")){
//                        edit_faze.setError("Campo Obrigatório");
                        Toast.makeText(getContext(), "Escolha a Fase", Toast.LENGTH_LONG).show();
                        return;
                    }
                }


                if(count[0] > fim){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Deseja Salvar").setPositiveButton("Salvar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            int lightFixtureAmount = Integer.valueOf(ViewUtils.getTextViewValue(editQtdLampadas));
                          //  String powerPhase = ViewUtils.getTextViewValue(edit_faze);
                            int lampAmount = Integer.valueOf(ViewUtils.getTextViewValue(editQtdLampadas));

                            Location l = mListener.getLastLocation();
                            mLighting.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis()/1000));
                            mLighting.setUpdate(true);

                            if (lightFixtureAmount < 0) {
                                Toast.makeText(getContext(),
                                        getString(R.string.dialog_lighting_edit_light_fixture_amount_required),
                                        Toast.LENGTH_LONG).show();
                            } else if (lampAmount < 0) {
                                Toast.makeText(getContext(),
                                        getString(R.string.dialog_lighting_edit_lamp_amount_required),
                                        Toast.LENGTH_LONG).show();
                            } else {

                                //EquipmentUpdateUtils.setPontoAtualizacao(getContext(), mPost);

                             /*   mLighting.setArmType(LightingArmType.values()[mArmType.getSelectedItemPosition()]);
                                mLighting.setLightFixtureType(LightingLightFixtureType.values()[mFixtureType.getSelectedItemPosition()]);
                                mLighting.setLampType(LightingLampType.values()[mLampType.getSelectedItemPosition()]);
                                mLighting.setPowerRating(LightingPowerRating.values()[mPowerRating.getSelectedItemPosition()]);
                                mLighting.setTrigger(LightingTrigger.values()[mTrigger.getSelectedItemPosition()]);
                                mLighting.setStatus(LightingStatus.values()[mStatus.getSelectedItemPosition()]);
                                mLighting.setLightFixtureAmount(lightFixtureAmount);
                                mLighting.setPowerPhase(powerPhase);
                                mLighting.setLampAmount(lampAmount);*/

                                mLighting.setArmType(LightingArmType.values()[tipoBraco]);
                                mLighting.setLightFixtureType(LightingLightFixtureType.values()[tipoLuminaria]);
                                mLighting.setLampType(LightingLampType.values()[tipoLampada]);
                                mLighting.setPowerRating(LightingPowerRating.values()[potencia]);
                                mLighting.setTrigger(LightingTrigger.values()[acionamento]);
                                mLighting.setStatus(LightingStatus.values()[lampadaAcesa]);
                                mLighting.setLightFixtureAmount(lightFixtureAmount);
                                mLighting.setPowerPhase(seq_fase);
                                mLighting.setLampAmount(lampAmount);

                                final ProgressDialog alertDialog = new ProgressDialog(getActivity(), R.style.AlertDialogTheme);
                                alertDialog.setTitle(getString(R.string.dialog_lighting_edit_saving_title));
                                alertDialog.setMessage(getString(R.string.dialog_lighting_edit_saving_message));
                                alertDialog.setCancelable(false);
                                alertDialog.show();

                                VisiumApiCallback<Lighting> callback =
                                        new VisiumApiCallback<Lighting>() {
                                            private static final long serialVersionUID = 3656333712718352037L;

                                            @Override
                                            public void callback(Lighting lighting, ErrorResponse e) {

                                                alertDialog.dismiss();

                                                if (lighting != null && e == null) {
                                                    Toast.makeText(getContext(),
                                                            getString(R.string.dialog_lighting_edit_saving_success),
                                                            Toast.LENGTH_LONG).show();

                                                    mListener.onLightingItemChanged(lighting);

                                                    dismissAllowingStateLoss();
                                                } else if (e != null && e.isCustomized()) {
                                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                                } else {
                                                    Toast.makeText(getContext(),
                                                            getString(R.string.dialog_lighting_edit_saving_error),
                                                            Toast.LENGTH_LONG).show();
                                                }

                                            }
                                        };

                                if (FileUtils.serviceOrderFileExists(mOrderId)) {

                                    if (mLighting.getId() > 0) {

                                        try {
                                            OfflineDownloadResponse downloaded = FileUtils.retrieve(mOrderId);
                                            List<Lighting> lightingList = downloaded.getLightingList();
                                            Iterator<Lighting> iterator = lightingList.iterator();

                                            while (iterator.hasNext()) {
                                                Lighting lighting = iterator.next();

                                                if (lighting.getId() == mLighting.getId()) {
                                                    iterator.remove();
                                                    break;
                                                }
                                            }

                                            lightingList.add(mLighting);
                                            downloaded.setLightingList(lightingList);

                                            if (FileUtils.saveOfflineDownload(downloaded)) {
                                                callback.callback(mLighting, null);
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
                                            List<Lighting> lightingList = downloaded.getLightingList();

                                            mLighting.setId(-1 * System.currentTimeMillis() / 1000);
                                            lightingList.add(mLighting);
                                            downloaded.setLightingList(lightingList);

                                            if (FileUtils.saveOfflineDownload(downloaded)) {
                                                callback.callback(mLighting, null);
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
                                    if (mLighting.getId() > 0) {
                                        LightingController.get().save(getContext(), mLighting, callback);
                                    } else {
                                        LightingController.get().create(getContext(), mLighting, callback);
                                    }
                                }
                            }
                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });

                    builder.create();
                    builder.show();


                }else{
                    ++count[0];
                    viewFlipper.showNext();
                    if(count[0] > fim){
                        salvar.setText("Salvar");
                    }
                }
            }
        });



        return view;
    }

}