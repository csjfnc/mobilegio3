package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.PontoEntregaStatus;

import java.util.List;

/**
 * Created by fjesus on 30/08/2017.
 */

public class PontoEntregaStatusAdapter extends BaseAdapter {

    private Context context;
    private List<PontoEntregaStatus> pontoEntregaStatuses;

    public PontoEntregaStatusAdapter(Context context, List<PontoEntregaStatus> pontoEntregaStatuses){
        this.context = context;
        this.pontoEntregaStatuses = pontoEntregaStatuses;
    }


    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<PontoEntregaStatus> getPontoEntregaStatuses() {
        return pontoEntregaStatuses;
    }

    public void setPontoEntregaStatuses(List<PontoEntregaStatus> pontoEntregaStatuses) {
        this.pontoEntregaStatuses = pontoEntregaStatuses;
    }

    @Override
    public int getCount() {
        return pontoEntregaStatuses.size();
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

        button.setText(pontoEntregaStatuses.get(i).getStatus()+"");

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, postHeightGrids.get(i).getHeight()+"", Toast.LENGTH_LONG).show();
//            }
//        });

        return view1;
    }
}
