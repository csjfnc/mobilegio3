package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.DemandaClassficacao;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.PontoEntregaClasseSocial;
import com.visium.fieldservice.entity.PontoEntregaStatus;
import com.visium.fieldservice.entity.PontoEntregaTipodeConstrução;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.equipament.EquipmentType;
import com.visium.fieldservice.ui.common.EquipmentListDialogListener;

import java.util.Arrays;
import java.util.List;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

/**
 * Created by fjesus on 11/05/2017.
 */

public class PontoEntregaRecAdapter extends RecyclerView.Adapter<PontoEntregaRecAdapter.ViewHolder> {

    private List<PontoEntrega> pontoEntregaList;
    private Context context;
    private EquipmentListDialogListener mEquipmentListDialogListener;
    private Post post;
    private EquipmentListDialogListener mListener;

    public PontoEntregaRecAdapter(List<PontoEntrega> pontoEntregaList, Context context, Post post, EquipmentListDialogListener mListener) {
        this.mListener = mListener;
        this.pontoEntregaList = pontoEntregaList;
        this.context = context;
        this.post = post;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout linha_apdapter_ponto;
        TextView equipment_id, txtClasseAtendimento, txtTipoConstrucao, txtClasseSocial, txtLogradouro,
                txtNumero, txtComplemento, txtMedidor, txtFase, txtETLigacao, txtObservacao, txtStatus;
        private RelativeLayout cab_ponto_entrega;
        private GridLayout gridLayPontoEntrega;
        RelativeLayout options;
        private ImageButton edit_icon, delete_button, ver_button, add_button;
        private ImageView img_expande;
        private View divider;

        public ViewHolder(View itemView) {
            super(itemView);

            txtStatus = (TextView) itemView.findViewById(R.id.txtStatus);
            linha_apdapter_ponto = (RelativeLayout) itemView.findViewById(R.id.linha_apdapter_ponto);
            equipment_id = (TextView) itemView.findViewById(R.id.equipment_id);
            txtClasseAtendimento = (TextView) itemView.findViewById(R.id.txtClasseAtendimento);
            txtTipoConstrucao = (TextView) itemView.findViewById(R.id.txtTipoConstrucao);
            txtClasseSocial = (TextView) itemView.findViewById(R.id.txtClasseSocial);
            txtLogradouro = (TextView) itemView.findViewById(R.id.txtLogradouro);
            txtNumero = (TextView) itemView.findViewById(R.id.txtNumero);
            txtFase = (TextView) itemView.findViewById(R.id.txtFase);
            txtETLigacao = (TextView) itemView.findViewById(R.id.txtETLigacao);
            txtObservacao = (TextView) itemView.findViewById(R.id.txtObservacao);
            cab_ponto_entrega = (RelativeLayout) itemView.findViewById(R.id.cab_ponto_entrega);
            gridLayPontoEntrega = (GridLayout) itemView.findViewById(R.id.gridLayPontoEntrega);
            options = (RelativeLayout) itemView.findViewById(R.id.options);

            edit_icon = (ImageButton) itemView.findViewById(R.id.edit_icon);
            delete_button = (ImageButton) itemView.findViewById(R.id.delete_button);
            ver_button = (ImageButton) itemView.findViewById(R.id.ver_button);
            add_button = (ImageButton) itemView.findViewById(R.id.add_button);
            img_expande = (ImageView) itemView.findViewById(R.id.img_expande);
            divider = itemView.findViewById(R.id.divider);
        }
    }

    @Override
    public PontoEntregaRecAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;
        if (pontoEntregaList != null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.pontoentrega_list_adapter, parent, false);
        }

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PontoEntregaRecAdapter.ViewHolder holder, final int position) {

     /*   String classeSocial = "";
        if (pontoEntregaList.get(position).getClasseSocial() == 0) {
            classeSocial = "Sem Informação";
        }
        if (pontoEntregaList.get(position).getClasseSocial() == 1) {
            classeSocial = "Residencial";
        }
        if (pontoEntregaList.get(position).getClasseSocial() == 2) {
            classeSocial = "Comercial";
        }
        if (pontoEntregaList.get(position).getClasseSocial() == 3) {
            classeSocial = "Edifício";
        }*/

        final List<PontoEntregaStatus> listStatus = Arrays.asList(PontoEntregaStatus.values());
        final List<DemandaClassficacao> demandaClassficacaos = Arrays.asList(DemandaClassficacao.values());
        final List<PontoEntregaTipodeConstrução> listaTipoContrucao = Arrays.asList(PontoEntregaTipodeConstrução.values());
        final List<PontoEntregaClasseSocial> pontoEntregaClasseSocials = Arrays.asList(PontoEntregaClasseSocial.values());

        // holder.equipment_id.setText(pontoEntregaList.get(position).getCodigo_bd_geo()+"");

        /*holder.txtStatus.setText(listStatus.get(pontoEntregaList.get(position).getStatus()).getStatus()+"");
        holder.txtClasseAtendimento.setText(demandaClassficacaos.get(pontoEntregaList.get(position).getClasseAtendimento()).getClassificacao()+"");
        holder.txtTipoConstrucao.setText(listaTipoContrucao.get(pontoEntregaList.get(position).getTipoConstrucao()).getTipodeConstrução() + "");
        holder.txtClasseSocial.setText(pontoEntregaClasseSocials.get(pontoEntregaList.get(position).getClasseSocial()).getClasseSocial() + "");
        holder.txtLogradouro.setText(pontoEntregaList.get(position).getLogradouro() + "");
        holder.txtNumero.setText(pontoEntregaList.get(position).getNumero());
        holder.txtFase.setText(pontoEntregaList.get(position).getFase() + "");
        holder.txtETLigacao.setText(pontoEntregaList.get(position).getEtLigacao() + "");
        holder.txtObservacao.setText(pontoEntregaList.get(position).getObservacao() + "");*/


        final int[] pode = {0};

        holder.cab_ponto_entrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pode[0] == 0) {
                    holder.gridLayPontoEntrega.setVisibility(View.VISIBLE);
                    holder.options.setVisibility(View.VISIBLE);
                    holder.cab_ponto_entrega.setBackgroundColor(Color.parseColor("#e5e5e5"));
                    pode[0] = 1;
                    holder.img_expande.setImageResource(R.drawable.ic_expand_less_black_24dp);

                    SimpleTooltip.Builder ver = new SimpleTooltip.Builder(context);
                    SimpleTooltip.Builder editar = new SimpleTooltip.Builder(context);
                    SimpleTooltip.Builder excluir = new SimpleTooltip.Builder(context);
                    SimpleTooltip.Builder add = new SimpleTooltip.Builder(context);
                    holder.divider.setVisibility(View.VISIBLE);


                    /*excluir.anchorView(holder.delete_button)
                            .text("Excluir")
                            .gravity(Gravity.TOP)
                            .animated(true)
                            .transparentOverlay(false)
                            .build()
                            .show();

                    add.anchorView(holder.add_button)
                            .text("Novo Medidor")
                            .gravity(Gravity.TOP)
                            .animated(true)
                            .transparentOverlay(false)
                            .build()
                            .show();*/



                } else {
                    holder.gridLayPontoEntrega.setVisibility(View.GONE);
                    holder.options.setVisibility(View.GONE);
                    holder.cab_ponto_entrega.setBackgroundColor(Color.WHITE);
                    pode[0] = 0;
                    holder.img_expande.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    holder.divider.setVisibility(View.GONE);
                }
            }
        });

        final PontoEntrega pontoEntrega = pontoEntregaList.get(position);

     /*   holder.equipment_id.setText(pontoEntregaList.get(position).getLogradouro() + ", " +
                "" + pontoEntregaList.get(position).getNumero() + ", " + classeSocial);
        /*holder.linha_apdapter_ponto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  AlertDialog.Builder builder = new AlertDialog.Builder(context);
            }
        });*/


        holder.add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onEditItemClicked(EquipmentType.MEDIDOR, post, pontoEntrega);
            }
        });

        holder.ver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mListener.onListItemEquipament(EquipmentType.MEDIDOR, post, pontoEntregaList.get(position));
            }
        });

        holder.edit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onEditItemClicked(EquipmentType.PONTO_DE_ENTREGA, post, pontoEntregaList.get(position));
            }
        });

        holder.delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onDeleteItemClicked(EquipmentType.PONTO_DE_ENTREGA, post, pontoEntregaList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return pontoEntregaList.size();
    }

    public void setEquipmentListDialogListener(EquipmentListDialogListener listener) {
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


