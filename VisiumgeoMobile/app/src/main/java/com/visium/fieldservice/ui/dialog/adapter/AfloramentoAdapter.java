package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.visium.fieldservice.R;
import com.visium.fieldservice.configuration.AfloramentoConfiguration;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.equipament.Afloramento;
import com.visium.fieldservice.entity.equipament.EquipmentType;
import com.visium.fieldservice.ui.common.EquipmentListDialogListener;

import java.util.List;


public class AfloramentoAdapter extends RecyclerView.Adapter<AfloramentoAdapter.ViewHolder>  {

    private List<Afloramento> mAfloramentoList;
    private Context mContext;
    private EquipmentListDialogListener mListener;
    private EquipmentType EQUIPMENT_TYPE = EquipmentType.AFLORAMENTO;
    private int LAYOUT = R.layout.afloramento_list_adapter;
    private Post post;

    public AfloramentoAdapter(List<Afloramento> afloramentoList, Context context,
                              EquipmentListDialogListener listener, Post post) {
        this.mAfloramentoList = afloramentoList;
        this.mContext = context;
        this.mListener = listener;
        this.post = post;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView equipment_id, txtStatus, txtProprietario;
        ImageView edit_icon, delete_button;

        public ViewHolder(View itemView) {
            super(itemView);

            equipment_id = (TextView) itemView.findViewById(R.id.equipment_id);
            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            txtProprietario = (TextView) itemView.findViewById(R.id.txtProprietario);

            edit_icon = (ImageView) itemView.findViewById(R.id.edit_icon);
            delete_button = (ImageView) itemView.findViewById(R.id.delete_button);

        }
    }

    @Override
    public AfloramentoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(LAYOUT, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        final Afloramento obj = mAfloramentoList.get(position);

        holder.equipment_id.setText(String.valueOf(obj.getId()));
        holder.equipment_id.setText(AfloramentoConfiguration.getEnum(AfloramentoConfiguration.Names.STATUS).getValueByKey(obj.getStatus())); //FIXME: usar um estático AfloramentoEnums.getStatusNameByKey
        holder.txtProprietario.setText(AfloramentoConfiguration.getEnum(AfloramentoConfiguration.Names.PROPRIETARIO).getValueByKey(obj.getProprietario())); //FIXME: usar um estático AfloramentoEnums.getStatusNameByKey

        holder.edit_icon
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onEditItemClicked(EQUIPMENT_TYPE, post, obj);
                    }
                });

        holder.delete_button
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onDeleteItemClicked(EQUIPMENT_TYPE, post, obj);
                    }
                });
    }

    @Override
    public long getItemId(int i) {
        return mAfloramentoList.get(i).getId();
    }

    @Override
    public int getItemCount() {
        return mAfloramentoList.size();
    }

}
