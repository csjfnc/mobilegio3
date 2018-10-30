package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.DemandaDomicilio;

import java.util.List;

/**
 * Created by fjesus on 11/05/2018.
 */

public class DomicilioAdapter extends BaseAdapter {

    private List<DemandaDomicilio> domicilios;
    private Context context;

    public DomicilioAdapter(Context context, List<DemandaDomicilio> domicilios){
        this.domicilios = domicilios;
        this.context = context;
    }

    @Override
    public int getCount() {
        return domicilios.size();
    }

    @Override
    public Object getItem(int position) {
        return domicilios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.linha_grid_item, null);

        TextView textView = (TextView) view.findViewById(R.id.bt);
        textView.setHeight(120);
        textView.setTextSize(19);

        textView.setText(domicilios.get(position).getQtd());

        return view;

    }
}
