package com.visium.fieldservice.ui.dialog.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.Lighting;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.ui.dialog.LightingListDialogListener;

import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class LightingAdapter extends BaseAdapter {

    private List<Lighting> mLightingList;
    private Context mContext;
    private LightingListDialogListener mListener;
    private Post mPost;

    public LightingAdapter(Post post, List<Lighting> lightingList, Context context,
                           LightingListDialogListener listener) {
        this.mPost = post;
        this.mLightingList = lightingList;
        this.mContext = context;
        this.mListener = listener;
    }

    @Override
    public int getCount() {
        return mLightingList.size();
    }

    @Override
    public Object getItem(int i) {
        return mLightingList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mLightingList.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.lighting_list_adapter, null);
        }

        final RelativeLayout cab_ip = (RelativeLayout) view.findViewById(R.id.cab_ip);
        final GridLayout gridViewIp = (GridLayout) view.findViewById(R.id.gridViewIp);
        final RelativeLayout options_ip = (RelativeLayout) view.findViewById(R.id.options_ip);
        final ImageView img_expande = (ImageView) view.findViewById(R.id.img_expande);


        final Lighting lighting = mLightingList.get(i);
        ((TextView) view.findViewById(R.id.lighting_id))
                .setText(String.valueOf(lighting.getId()));
        ((TextView) view.findViewById(R.id.lighting_arm_type))
                .setText(lighting.getArmType().getName());
        ((TextView) view.findViewById(R.id.lighting_fixture_type))
                .setText(lighting.getLightFixtureType().getName());
        ((TextView) view.findViewById(R.id.lighting_fixture_amount))
                .setText(String.valueOf(lighting.getLightFixtureAmount()));
        ((TextView) view.findViewById(R.id.lighting_lamp_type))
                .setText(lighting.getLampType().getName());
        ((TextView) view.findViewById(R.id.lighting_power_rating))
                .setText(String.valueOf(lighting.getPowerRating().getRating()));
        ((TextView) view.findViewById(R.id.lighting_power_phase))
                .setText(lighting.getPowerPhase());
        ((TextView) view.findViewById(R.id.lighting_trigger))
                .setText(lighting.getTrigger().getName());
        ((TextView) view.findViewById(R.id.lighting_status))
                .setText(lighting.getStatus().getName());
        ((TextView) view.findViewById(R.id.lighting_lamp_amount))
                .setText(String.valueOf(lighting.getLampAmount()));
        view.findViewById(R.id.lighting_edit_icon)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onEditLightingItemClicked(lighting);
                    }
                });

        view.findViewById(R.id.delete_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mListener.onDeleteItemClicked(mPost, lighting);
                    }
                });

        final int[] pode = {0};

        cab_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pode[0] == 0){
                    gridViewIp.setVisibility(View.VISIBLE);
                    options_ip.setVisibility(View.VISIBLE);
                    img_expande.setImageResource(R.drawable.ic_expand_less_black_24dp);
                    cab_ip.setBackgroundColor(Color.parseColor("#e5e5e5"));
                    pode[0] = 1;
                }else if (pode[0] == 1){
                    gridViewIp.setVisibility(View.GONE);
                    options_ip.setVisibility(View.GONE);
                    img_expande.setImageResource(R.drawable.ic_expand_more_black_24dp);
                    cab_ip.setBackgroundColor(Color.WHITE);
                    pode[0] = 0;
                }
            }
        });
        return view;
    }
}
