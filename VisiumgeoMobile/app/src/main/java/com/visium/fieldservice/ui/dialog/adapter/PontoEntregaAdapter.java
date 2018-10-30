package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.equipament.EquipmentType;
import com.visium.fieldservice.ui.common.EquipmentListDialogListener;

import java.util.List;


public class PontoEntregaAdapter extends BaseAdapter {

    private List<PontoEntrega> mPontoEntregaList;
    private Context mContext;
    private EquipmentListDialogListener mListener;
    private EquipmentType EQUIPMENT_TYPE = EquipmentType.PONTO_DE_ENTREGA;
    private int LAYOUT = R.layout.pontoentrega_list_adapter;

    public PontoEntregaAdapter(List<PontoEntrega> pontoEntregaList, Context context,
                               EquipmentListDialogListener listener) {
        this.mPontoEntregaList = pontoEntregaList;
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return mPontoEntregaList.size();
    }

    @Override
    public Object getItem(int i) {
        return mPontoEntregaList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mPontoEntregaList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(LAYOUT, null);
        }

        final PontoEntrega obj = mPontoEntregaList.get(i);

       /*((TextView) view.findViewById(R.id.equipment_id))
                .setText(String.valueOf(obj.getId()));
        ((TextView) view.findViewById(R.id.txtStatus))
                .setText("Status");
        ((TextView) view.findViewById(R.id.txtClasseAtendimento))
                .setText("Classe Atendimento");
        ((TextView) view.findViewById(R.id.txtTipoConstrucao))
                .setText("Tipo Construção");
        ((TextView) view.findViewById(R.id.txtClasseSocial))
                .setText(String.valueOf(obj.getClasseSocial()));
        ((TextView) view.findViewById(R.id.txtLogradouro))
                .setText(String.valueOf(obj.getLogradouro()));
        ((TextView) view.findViewById(R.id.txtNumero))
                .setText(String.valueOf(obj.getNumero()));
        ((TextView) view.findViewById(R.id.txtFase))
                .setText(String.valueOf(obj.getFase()));
        ((TextView) view.findViewById(R.id.txtETLigacao))
                .setText(String.valueOf(obj.getEtLigacao()));
        ((TextView) view.findViewById(R.id.txtObservacao))
                .setText(String.valueOf(obj.getObservacao()));
        /*view.findViewById(R.id.edit_icon)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onEditItemClicked(EQUIPMENT_TYPE, obj);
                    }
                });

        view.findViewById(R.id.delete_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onDeleteItemClicked(EQUIPMENT_TYPE, obj);
                    }
                });*/
        return view;
    }
}
