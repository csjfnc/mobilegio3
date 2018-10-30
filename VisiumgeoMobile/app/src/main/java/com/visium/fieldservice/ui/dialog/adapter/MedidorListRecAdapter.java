package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.response.MedidorResponse;
import com.visium.fieldservice.entity.equipament.Medidor;
import com.visium.fieldservice.ui.common.EquipmentListDialogListener;

import java.util.List;

/**
 * Created by fjesus on 19/05/2017.
 */

public class MedidorListRecAdapter extends RecyclerView.Adapter<MedidorListRecAdapter.ViewHolder> {

    private List<MedidorResponse> medidorList;
    private EquipmentListDialogListener mEquipmentListDialogListener;
    private Context context;

    public MedidorListRecAdapter(List<MedidorResponse> medidorList, Context context){
        this.medidorList = medidorList;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView medidor_numero_text, medidor_complemento_text;

        public ViewHolder(View itemView) {
            super(itemView);

            medidor_numero_text = (TextView) itemView.findViewById(R.id.medidor_numero_text);
            medidor_complemento_text = (TextView) itemView.findViewById(R.id.medidor_complemento_text);
        }
    }

    @Override
    public MedidorListRecAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if(medidorList != null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.medidor_linha_list, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedidorListRecAdapter.ViewHolder holder, int position) {

        holder.medidor_numero_text.setText(medidorList.get(position).getNumeroMedidor());
        holder.medidor_complemento_text.setText(medidorList.get(position).getComplementoResidencial());
    }

    @Override
    public int getItemCount() {
        return medidorList.size();
    }

    public void setEquipmentListDialogListener(EquipmentListDialogListener listener){
        this.mEquipmentListDialogListener = listener;
    }

    public static class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private MedidorListRecAdapter.RecyclerItemClickListener.OnItemClickListener mListener;

        public interface OnItemClickListener {
            public void onItemClick(View view, int position);
        }

        GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context, MedidorListRecAdapter.RecyclerItemClickListener.OnItemClickListener listener) {
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
