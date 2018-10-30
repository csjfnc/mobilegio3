package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.DemandaClassficacao;
import com.visium.fieldservice.entity.DemandaComplemento1;

import java.util.List;

/**
 * Created by fjesus on 30/08/2017.
 */

public class DemandaComplemento1Adapter extends BaseAdapter {

    private Context context;
    private List<DemandaComplemento1> demandaComplemento1s;

    public DemandaComplemento1Adapter(Context context, List<DemandaComplemento1> demandaComplemento1s){
        this.context = context;
        this.demandaComplemento1s = demandaComplemento1s;
    }


    @Override
    public int getCount() {
        return demandaComplemento1s.size();
    }

    @Override
    public Object getItem(int i) {
        return demandaComplemento1s.get(i);
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

        button.setText(demandaComplemento1s.get(i).getComplemento()+"");

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, postHeightGrids.get(i).getHeight()+"", Toast.LENGTH_LONG).show();
//            }
//        });

        return view1;
    }
}
