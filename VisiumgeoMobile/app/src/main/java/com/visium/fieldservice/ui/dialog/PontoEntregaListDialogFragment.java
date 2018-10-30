package com.visium.fieldservice.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.equipament.EquipmentType;
import com.visium.fieldservice.ui.common.EquipmentListDialogListener;
import com.visium.fieldservice.ui.dialog.adapter.PontoEntregaAdapter;
import com.visium.fieldservice.ui.dialog.adapter.PontoEntregaRecAdapter;
import com.visium.fieldservice.ui.maps.MapsPickLocationActivity;
import com.visium.fieldservice.ui.maps.MapsPostsActivity;
import com.visium.fieldservice.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class PontoEntregaListDialogFragment extends AppCompatDialogFragment {

    private static List<PontoEntrega> mPontoEntregaList;
    private static Post mPost;
    private static EquipmentListDialogListener mListener;
    private static PontoEntregaAdapter mAdapter;
    private static PontoEntregaRecAdapter mAdapter2;
    private static ListView mPontoEntregaListView;
    private static RecyclerView mPontoEntregaListView2;
    private static View mPontoEntregaListEmptyView;
    private static TextView list_empty;


    public static PontoEntregaListDialogFragment newInstance(EquipmentListDialogListener listener,
                                                             List<PontoEntrega> equipmentList,
                                                             Post post) {
        PontoEntregaListDialogFragment.mPontoEntregaList = equipmentList;
        PontoEntregaListDialogFragment.mPost = post;
        PontoEntregaListDialogFragment.mListener = listener;
        return new PontoEntregaListDialogFragment();
    }

    public void notifyDataSetChanged() {
        if (mAdapter2 != null) {
            switchLayout();
            mAdapter2.notifyDataSetChanged();
            list_empty.setVisibility(View.GONE);
            if(mPontoEntregaList.isEmpty()){
                list_empty.setVisibility(View.VISIBLE);
            }
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

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAdapter2 != null) {
            switchLayout();
            mAdapter2.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_equipment_list, container, false);
         ((TextView) view.findViewById(R.id.title))
                 .setText("Lista de Pontos de Entrega do Poste " + mPost.getGeoCode());

        mPontoEntregaListView2 = (RecyclerView) view.findViewById(R.id.list);
        list_empty = (TextView) view.findViewById(R.id.lista_vazia);
        list_empty.setText("Não Pontos de Entrega Cadastrados");

        mPontoEntregaListView2.setLayoutManager(new LinearLayoutManager(getContext()));

      //  mPontoEntregaListEmptyView = view.findViewById(R.id.list_empty);
        mAdapter2 = new PontoEntregaRecAdapter(mPontoEntregaList, getContext(), mPost, mListener);
      //  mAdapter2.setEquipmentListDialogListener(mListener);
        mPontoEntregaListView2.setAdapter(mAdapter2);



    /*    mPontoEntregaListView2.addOnItemTouchListener(new PontoEntregaRecAdapter.RecyclerItemClickListener(getContext(), new PontoEntregaRecAdapter.RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, final int position) {

                final PontoEntrega pontoEntrega = mPontoEntregaList.get(position);

                Button add_medidor, ver_ponto_entrega, ver_medidores, excluir_ponto_entrega;
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View view1 = layoutInflater.inflate(R.layout.pontoentrega_options_select, null);
                //final AlertDialog alertDialog = builder.create();
                add_medidor = (Button) view1.findViewById(R.id.add_medidor);
                ver_medidores = (Button) view1.findViewById(R.id.ver_medidores);
                ver_ponto_entrega = (Button) view1.findViewById(R.id.ver_ponto_entrega);
                excluir_ponto_entrega = (Button) view1.findViewById(R.id.excluir_ponto_entrega);

                TextView pontoentrega_title = (TextView) view1.findViewById(R.id.pontoentrega_title);

                add_medidor.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onEditItemClicked(EquipmentType.MEDIDOR, mPost, pontoEntrega);
                    }
                });

                ver_medidores.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onListItemEquipament(EquipmentType.MEDIDOR, mPost, mPontoEntregaList.get(position));
                    }
                });

                ver_ponto_entrega.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onEditItemClicked(EquipmentType.PONTO_DE_ENTREGA, mPost, mPontoEntregaList.get(position));
                    }
                });

                excluir_ponto_entrega.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onDeleteItemClicked(EquipmentType.PONTO_DE_ENTREGA, mPost, mPontoEntregaList.get(position));
                    }
                });

                pontoentrega_title.setText(mPontoEntregaList.get(position).getLogradouro());


                builder.setView(view1);
                AlertDialog alertDialog = builder.create();

                alertDialog.show();

            }
        }));
*/
        //switchLayout();
        LogUtils.log("PontoEntregaList ");
        mPontoEntregaListView2.setVisibility(View.VISIBLE);
//        mPontoEntregaListEmptyView.setVisibility(View.GONE);

        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

        view.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsPickLocationActivity.class);
            //    intent.putExtra(MapsPostsActivity.POST_LIST, new ArrayList<>(((MapsPostsActivity)mListener).getPostsList()));
                intent.putExtra(MapsPostsActivity.SERVICE_ORDER_POINTS, new ArrayList<>(((MapsPostsActivity)mListener).getOrderPoints()));
                //intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                //intent.putExtra(MapsPickLocationActivity.METERS_RESTRICTION, 10f);
                intent.putExtra("orderId", mPost.getOrderId());
                startActivityForResult(intent, MapsPostsActivity.PONTO_ENTREGA_PICKUP_LOCATION);
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == MapsPostsActivity.PONTO_ENTREGA_PICKUP_LOCATION) {
            if (data != null && data.hasExtra(MapsPickLocationActivity.PICK_LOCATION)) {
                LatLng latLng = data.getParcelableExtra(MapsPickLocationActivity.PICK_LOCATION);
                LogUtils.log("Latitude: "+latLng.latitude+" Longitude: "+latLng.longitude );
                mListener.onAddItemButtonClicked(EquipmentType.PONTO_DE_ENTREGA, mPost, latLng);
            } else {
                Toast.makeText(getActivity(),
                        R.string.maps_posts_new_location_error,
                        Toast.LENGTH_LONG).show();
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }

    public void switchLayout() {
        if (mPontoEntregaList.size() > 0) {
            mPontoEntregaListView2.setVisibility(View.VISIBLE);
            list_empty.setVisibility(View.GONE);
        } else {
            mPontoEntregaListView2.setVisibility(View.GONE);
            list_empty.setVisibility(View.VISIBLE);
        }
    }

}