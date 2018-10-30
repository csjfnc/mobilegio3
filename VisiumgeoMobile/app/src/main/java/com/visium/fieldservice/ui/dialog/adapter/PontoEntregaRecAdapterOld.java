package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.ui.common.EquipmentListDialogListener;

import java.util.List;

/**
 * Created by fjesus on 11/05/2017.
 */

public class PontoEntregaRecAdapterOld extends RecyclerView.Adapter<PontoEntregaRecAdapterOld.ViewHolder> {

    private List<PontoEntrega> pontoEntregaList;
    private Context context;
    private EquipmentListDialogListener mEquipmentListDialogListener;
    private Post post;

    public PontoEntregaRecAdapterOld(List<PontoEntrega> pontoEntregaList, Context context, Post post){
        this.pontoEntregaList = pontoEntregaList;
        this.context = context;
        this.post = post;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout linha_apdapter_ponto;
        TextView nomePontoEntrega;

        public ViewHolder(View itemView) {
            super(itemView);
            linha_apdapter_ponto = (RelativeLayout) itemView.findViewById(R.id.linha_apdapter_ponto);
            nomePontoEntrega = (TextView) itemView.findViewById(R.id.nomePontoEntrega);
        }
    }

    @Override
    public PontoEntregaRecAdapterOld.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if(pontoEntregaList != null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.pontoentrega_lista_adapter2, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PontoEntregaRecAdapterOld.ViewHolder holder, final int position) {

       /* String classeSocial = "";
        if(pontoEntregaList.get(position).getClasseSocial() == 0){
            classeSocial = "Sem Informação";
        }
        if(pontoEntregaList.get(position).getClasseSocial() == 1){
            classeSocial = "Residencial";
        }
        if(pontoEntregaList.get(position).getClasseSocial() == 2) {
            classeSocial = "Comercial";
        }
            if(pontoEntregaList.get(position).getClasseSocial() == 3){
                classeSocial = "Edifício";
            }
*/

      /*  holder.nomePontoEntrega.setText(pontoEntregaList.get(position).getLogradouro()+", " +
                ""+pontoEntregaList.get(position).getNumero()+", "+ classeSocial);
        holder.linha_apdapter_ponto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              //  AlertDialog.Builder builder = new AlertDialog.Builder(context);


            }
        });*/

    }

    @Override
    public int getItemCount() {
        return pontoEntregaList.size();
    }

    public void setEquipmentListDialogListener(EquipmentListDialogListener listener){
        this.mEquipmentListDialogListener = listener;
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, OnItemClickListener listener) {
            mListener = listener;
            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildAdapterPosition(childView));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }
}


