package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.NumeroLocal;

import java.util.List;

/**
 * Created by fjesus on 22/01/2018.
 */

public class NumeroLocalAdapter extends BaseAdapter {

    private List<NumeroLocal> numeroLocals;
    private Context context;

    public NumeroLocalAdapter(Context context, List<NumeroLocal> numeroLocals){
        this.context = context;
        this.numeroLocals = numeroLocals;
    }

    @Override
    public int getCount() {
        return numeroLocals.size();
    }

    @Override
    public Object getItem(int position) {
        return numeroLocals.get(position);
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

        TextView button = (TextView) view.findViewById(R.id.bt);
        button.setHeight(120);
        button.setTextSize(19);

        if(numeroLocals.get(position).getNumero().equals("APAGAR")){
            button.setTextColor(Color.parseColor("#ff7070"));
        }
        if(numeroLocals.get(position).getNumero().equals("OK")){
            button.setTextColor(Color.parseColor("#56ff78"));
        }
        button.setText(numeroLocals.get(position).getNumero()+"");
        return view;
    }
}
