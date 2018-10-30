package com.visium.fieldservice.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.equipament.Afloramento;
import com.visium.fieldservice.entity.equipament.EquipmentType;
import com.visium.fieldservice.ui.common.EquipmentListDialogListener;
import com.visium.fieldservice.ui.dialog.adapter.AfloramentoAdapter;

import java.util.List;

public class AfloramentoListDialogFragment extends AppCompatDialogFragment {

    private static List<Afloramento> mAfloramentoList;
    private static Post mPost;
    private static EquipmentListDialogListener mListener;
    private static AfloramentoAdapter mAdapter;
    private static RecyclerView mAfloramentoListView;
   // private static View mAfloramentoListEmptyView;
    private TextView lista_vazia;

    public static AfloramentoListDialogFragment newInstance(EquipmentListDialogListener listener,
                                                            List<Afloramento> equipmentList,
                                                            Post post) {
        AfloramentoListDialogFragment.mAfloramentoList = equipmentList;
        AfloramentoListDialogFragment.mPost = post;
        AfloramentoListDialogFragment.mListener = listener;
        return new AfloramentoListDialogFragment();
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

        View view = inflater.inflate(R.layout.fragment_equipment_list, container, false);

        lista_vazia = (TextView) view.findViewById(R.id.lista_vazia);
        lista_vazia.setText("Não Afloramentos Cadastrados");

         ((TextView) view.findViewById(R.id.title))
                 .setText("Lista de afloramentos do Poste " + mPost.getGeoCode());

        mAfloramentoListView = (RecyclerView) view.findViewById(R.id.list);
        //mAfloramentoListEmptyView = view.findViewById(R.id.list_empty);
        mAdapter = new AfloramentoAdapter(mAfloramentoList, getContext(), mListener, mPost);
        mAfloramentoListView.setAdapter(mAdapter);
        switchLayout();

        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

        view.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAddItemButtonClicked(EquipmentType.AFLORAMENTO, mPost, null);
            }
        });

        return view;
    }

    public void switchLayout() {
        if (mAfloramentoList.size() > 0) {
            mAfloramentoListView.setVisibility(View.VISIBLE);
            lista_vazia.setVisibility(View.GONE);
        } else {
            mAfloramentoListView.setVisibility(View.GONE);
            lista_vazia.setVisibility(View.VISIBLE);
        }
    }

}