package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.TipoComercio;

import java.util.List;

/**
 * Created by fjesus on 30/08/2017.
 */

public class TipoComercioAdapter extends BaseAdapter {

    private Context context;
    private List<TipoComercio> tipoComercios;

    public TipoComercioAdapter(Context context, List<TipoComercio> tipoComercios){
        this.context = context;
        this.tipoComercios = tipoComercios;
    }

    @Override
    public int getCount() {
        return tipoComercios.size();
    }

    @Override
    public TipoComercio getItem(int i) {
        return tipoComercios.get(i);
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

        button.setText(tipoComercios.get(i).getNome()+"");

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, postHeightGrids.get(i).getHeight()+"", Toast.LENGTH_LONG).show();
//            }
//        });

        return view1;
    }
}
