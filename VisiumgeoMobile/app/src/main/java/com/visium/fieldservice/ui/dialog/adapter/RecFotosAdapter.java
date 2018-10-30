package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.PostPhotos;

import java.util.List;

/**
 * Created by fjesus on 03/05/2018.
 */

public class RecFotosAdapter extends RecyclerView.Adapter<RecFotosAdapter.ViewHolder> {

    private List<PostPhotos> postPhotos;
    private Context context;

    public RecFotosAdapter(List<PostPhotos> postPhotos, Context context ){
        this.postPhotos = postPhotos;
        this.context = context;
    }

    public static  class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textoFotos;

        public ViewHolder(View itemView) {
            super(itemView);
            textoFotos = (TextView) itemView.findViewById(R.id.textFotos);
        }
    }

    @Override
    public RecFotosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.linha_fotos_rec, parent, false);

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecFotosAdapter.ViewHolder holder, int position) {
        PostPhotos postPhoto = postPhotos.get(position);
        holder.textoFotos.setText(postPhoto.getNumber());
    }

    @Override
    public int getItemCount() {
        return postPhotos.size();
    }


}
