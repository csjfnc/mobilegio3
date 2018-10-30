package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.PostHeight;
import com.visium.fieldservice.entity.PostHeightGrid;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fjesus on 30/08/2017.
 */

public class AlturaAdapter extends BaseAdapter {

    private Context context;
    private List<PostHeight> postHeightGrids;

    public AlturaAdapter(Context context, List<PostHeight> postHeightGrids){
        this.context = context;
        this.postHeightGrids = postHeightGrids;
    }

    @Override
    public int getCount() {
        return postHeightGrids.size();
    }

    @Override
    public PostHeight getItem(int i) {
        return postHeightGrids.get(i);
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

        button.setText(postHeightGrids.get(i).getHeight()+"");

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(context, postHeightGrids.get(i).getHeight()+"", Toast.LENGTH_LONG).show();
//            }
//        });

        return view1;
    }
}
