package com.visium.fieldservice.ui.dialog.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.ServiceOrder;

import java.util.List;

/**
 * Created by fjesus on 02/05/2018.
 */

public class ListaOrdensAdapters extends BaseAdapter {

    private List<ServiceOrder> serviceOrders;
    private Activity activity;


    public ListaOrdensAdapters(List<ServiceOrder> serviceOrders, Activity activity){
        this.serviceOrders = serviceOrders;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return serviceOrders.size();
    }

    @Override
    public Object getItem(int position) {
        return serviceOrders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return serviceOrders.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = activity.getLayoutInflater().inflate(R.layout.list_ordem_linha, parent, false);
        ServiceOrder serviceOrder = serviceOrders.get(position);
        TextView textTituloOrdem = (TextView) view.findViewById(R.id.textTituloOrdem);
        //TextView cityOrdem = (TextView) view.findViewById(R.id.cityOrdem);
        TextView totalPostes = (TextView) view.findViewById(R.id.totalPostes);
        TextView totalDemandas = (TextView) view.findViewById(R.id.totalDemandas);
        TextView totalStrands = (TextView) view.findViewById(R.id.totalStrands);
        TextView totalAnotacao = (TextView) view.findViewById(R.id.totalAnotacao);

        textTituloOrdem.setText(serviceOrder.getNumber());
        totalPostes.setText("Postes: "+serviceOrder.getTotalPostes());
        totalDemandas.setText("Demandas: "+serviceOrder.getTotalDemandas());
        totalStrands.setText("Strands: "+serviceOrder.getTotalStrands());
        totalAnotacao.setText("Anotações: "+serviceOrder.getTotalAnotacao());


        return view;
    }
}
