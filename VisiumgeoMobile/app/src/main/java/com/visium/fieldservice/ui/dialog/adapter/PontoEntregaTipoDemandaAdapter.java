package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.PontoEntregaStatus;
import com.visium.fieldservice.entity.PostHeight;
import com.visium.fieldservice.entity.TipoDemanda;

import java.util.List;

/**
 * Created by fjesus on 30/08/2017.
 */

public class PontoEntregaTipoDemandaAdapter extends BaseAdapter {

    private Context context;
    private List<TipoDemanda> tipoDemandas;

    public PontoEntregaTipoDemandaAdapter(Context context, List<TipoDemanda> tipoDemandas){
        this.context = context;
        this.tipoDemandas = tipoDemandas;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<TipoDemanda> getTipoDemandas() {
        return tipoDemandas;
    }

    public void setTipoDemandas(List<TipoDemanda> tipoDemandas) {
        this.tipoDemandas = tipoDemandas;
    }

    @Override
    public int getCount() {
        return tipoDemandas.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {

        View view1 = view;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view1 = layoutInflater.inflate(R.layout.linha_grid_item, null);

        TextView button = (TextView) view1.findViewById(R.id.bt);

        button.setText(tipoDemandas.get(i).getTipo()+"");
        button.setHeight(120);
        button.setTextSize(18);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, postHeightGrids.get(i).getHeight()+"", Toast.LENGTH_LONG).show();
//            }
//        });

        return view1;
    }
}
