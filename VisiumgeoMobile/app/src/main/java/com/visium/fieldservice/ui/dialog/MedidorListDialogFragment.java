package com.visium.fieldservice.ui.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.response.MedidorResponse;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.entity.Lighting;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.equipament.EquipmentType;
import com.visium.fieldservice.entity.equipament.Medidor;
import com.visium.fieldservice.ui.common.EquipmentListDialogListener;
import com.visium.fieldservice.ui.dialog.adapter.LightingAdapter;
import com.visium.fieldservice.ui.dialog.adapter.MedidorListRecAdapter;
import com.visium.fieldservice.util.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class MedidorListDialogFragment extends AppCompatDialogFragment {

    private static PontoEntrega mPontoEntrega;
    private List<MedidorResponse> medidorList;
    private static Post mPost;
    private static EquipmentListDialogListener mListener;
    //private static LightingAdapter mAdapter;
    private MedidorListRecAdapter mAdapter;
    //private static ListView mLightingListView;
    private RecyclerView mMedidor_list;
    private static View mLightingListEmptyView;

    public static MedidorListDialogFragment newInstance(EquipmentListDialogListener listener,
                                                        PontoEntrega mPontoEntrega,
                                                        Post post) {
        /*for(PontoEntrega l : mPontoEntrega) {
            if(l.isExcluido())
                mPontoEntrega.remove(l);
        }*/

        MedidorListDialogFragment.mPontoEntrega = mPontoEntrega;
        MedidorListDialogFragment.mPost = post;
        MedidorListDialogFragment.mListener = listener;
        return new MedidorListDialogFragment();
    }

    public void notifyDataSetChanged() {
        if (mAdapter != null) {
            switchLayout();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();
        notifyDataSetChanged();
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAdapter != null) {
            switchLayout();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_medidor_list, container, false);
         ((TextView) view.findViewById(R.id.lighting_list_title))
                 .setText(getString(R.string.dialog_lighting_list_title, mPost.getGeoCode()));

        mMedidor_list = (RecyclerView) view.findViewById(R.id.medidor_list);
        mMedidor_list.setLayoutManager(new LinearLayoutManager(getContext()));

        mLightingListEmptyView = view.findViewById(R.id.medidor_list_empty);



        medidorList = new ArrayList<>();
     /*   for (MedidorResponse medidor : mPontoEntrega.getMedidor()) {
            if (!medidor.isExcluido()) {
                medidorList.add(medidor);
            }

        }*/

        mAdapter = new MedidorListRecAdapter(medidorList, getContext());
        mMedidor_list.setAdapter(mAdapter);
        switchLayout();
        mAdapter.notifyDataSetChanged();

        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

        mMedidor_list.addOnItemTouchListener(new MedidorListRecAdapter.RecyclerItemClickListener(getContext(), new MedidorListRecAdapter.RecyclerItemClickListener.OnItemClickListener() {

            @Override
            public void onItemClick(View view, final int position) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view1 = layoutInflater.inflate(R.layout.medidor_options_select, null);

                Button editar_medidor = (Button) view1.findViewById(R.id.editar_medidor);
                Button excluir_medidor = (Button) view1.findViewById(R.id.excluir_medidor);
                builder.setView(view1);
                final AlertDialog alertDialog = builder.create();
                editar_medidor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());

                        LayoutInflater inflater1 = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                        View view1 = inflater1.inflate(R.layout.fragment_pontoentrega_medidor_edit, null);
                        final EditText edit_numero = (EditText) view1.findViewById(R.id.edit_numero);
                        final EditText edit_complemento = (EditText) view1.findViewById(R.id.edit_complemento);

                        edit_numero.setText(medidorList.get(position).getNumeroMedidor());
                        edit_complemento.setText(medidorList.get(position).getComplementoResidencial());

                        Button cancel_button = (Button) view1.findViewById(R.id.cancel_button);
                        Button save_button = (Button) view1.findViewById(R.id.save_button);

                        builder1.setView(view1);
                        final AlertDialog alertDialog1 = builder1.create();


                        cancel_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                alertDialog1.dismiss();
                                alertDialog.dismiss();
                            }
                        });

                        save_button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {


                                String numero = edit_numero.getText().toString();
                                String complemnto = edit_complemento.getText().toString();

                                if(atualizarMedidor(position, numero, complemnto)){
                                    alertDialog1.dismiss();
                                    alertDialog.dismiss();
                                    Toast.makeText(getContext(), "Medidor Atualizado!", Toast.LENGTH_LONG).show();
                                    notifyDataSetChanged();

                                }
                                //alertDialogprog.dismiss();
                            }
                        });

                        alertDialog1.show();

                    }
                });

                excluir_medidor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(excluirMedidor(position)){
                            medidorList.remove(position);
                            mMedidor_list.setAdapter(mAdapter);
                            alertDialog.dismiss();
                            Toast.makeText(getContext(), "Medidor removido!", Toast.LENGTH_LONG).show();
                        };
                        mAdapter.notifyDataSetChanged();
                    }
                });


                alertDialog.show();


            }
        }));

       /* view.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAddLightingButtonClicked(mPost);
            }
        });*/

        return view;
    }

    public void switchLayout() {
        if (medidorList.size() > 0) {
            mMedidor_list.setVisibility(View.VISIBLE);
            mLightingListEmptyView.setVisibility(View.GONE);
        } else {
            mMedidor_list.setVisibility(View.GONE);
            mLightingListEmptyView.setVisibility(View.VISIBLE);
        }
    }

    public boolean atualizarMedidor(int posistion, String numero, String complemento){

        final ProgressDialog alertDialogprog = new ProgressDialog(getActivity(), R.style.AlertDialogTheme);
        alertDialogprog.setTitle("Editando");
        alertDialogprog.setMessage("Por favor aguarde");
        alertDialogprog.setCancelable(false);
        alertDialogprog.show();

        try {
            OfflineDownloadResponse download = FileUtils.retrieve(mPost.getOrderId());

            List<PontoEntrega> pontoEntregaList = download.getPontoEntregaList();

            //Iterator<PontoEntrega> iterator = pontoEntregaList.iterator();

            MedidorResponse medidorResponse = medidorList.get(posistion);

       /*     int i = 0;
            for(PontoEntrega pontoEntrega : pontoEntregaList){
                if(pontoEntrega.getId() == mPontoEntrega.getId()){
                    mPontoEntrega.getMedidor().get(posistion).setUpdate(true);
                    mPontoEntrega.getMedidor().get(posistion).setUpdate(true);
                    mPontoEntrega.getMedidor().get(posistion).setComplementoResidencial(complemento);
                    mPontoEntrega.getMedidor().get(posistion).setNumeroMedidor(numero);
                    alertDialogprog.dismiss();
                    break;
                }
                ++i;
            }*/

        /*    pontoEntregaList.get(i).setUpdate(true);
            pontoEntregaList.get(i).getMedidor().remove(posistion);
            pontoEntregaList.get(i).getMedidor().add(medidorResponse);
*/
            /* int i = 0;
            for(PontoEntrega pontoEntrega : pontoEntregaList){
                if(pontoEntrega.getId() == mPontoEntrega.getId()){
                    mPontoEntrega.getMedidor().remove(pontoEntrega);
                    download.getPontoEntregaList().get(i).getMedidor().remove(pontoEntrega);
                }
                ++i;
            }*/

            download.setPontoEntregaList(pontoEntregaList);
            if (FileUtils.saveOfflineDownload(download)) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    //TODO
    public boolean excluirMedidor(int posistion){

        try {
            OfflineDownloadResponse download = FileUtils.retrieve(mPost.getOrderId());

            List<PontoEntrega> pontoEntregaList = download.getPontoEntregaList();

            //Iterator<PontoEntrega> iterator = pontoEntregaList.iterator();

            MedidorResponse medidorResponse = medidorList.get(posistion);

           /* int i = 0;
            for(PontoEntrega pontoEntrega : pontoEntregaList){
                if(pontoEntrega.getId() == mPontoEntrega.getId()){
                    if(mPontoEntrega.getMedidor().get(posistion).getIdMedidor() < 0){
                        mPontoEntrega.getMedidor().remove(posistion);
                        pontoEntregaList.remove(mPontoEntrega);
                        pontoEntregaList.add(mPontoEntrega);
                        break;
                    }else{
                        mPontoEntrega.getMedidor().get(posistion).setExcluido(true);
                        mPontoEntrega.getMedidor().get(posistion).setUpdate(true);
                        pontoEntregaList.get(i).setUpdate(true);
                        pontoEntregaList.get(i).getMedidor().remove(posistion);
                        pontoEntregaList.get(i).getMedidor().add(medidorResponse);
                        break;
                    }
                }
                ++i;
            }*/



            /* int i = 0;
            for(PontoEntrega pontoEntrega : pontoEntregaList){
                if(pontoEntrega.getId() == mPontoEntrega.getId()){
                    mPontoEntrega.getMedidor().remove(pontoEntrega);
                    download.getPontoEntregaList().get(i).getMedidor().remove(pontoEntrega);
                }
                ++i;
            }*/

            download.setPontoEntregaList(pontoEntregaList);
            if (FileUtils.saveOfflineDownload(download)) {
                return true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

}