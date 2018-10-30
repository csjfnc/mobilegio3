package com.visium.fieldservice.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.DemandaStrand;
import com.visium.fieldservice.entity.Post;

/**
 * Created by fjesus on 30/01/2018.
 */

public class DemandaStrandActionsDialogFragment extends AppCompatDialogFragment {


    private static DemandaStrand mDemandaStrand;
    private static DemandaStrandEditDialogListener mListener;
    private static Activity activity;
    private static boolean mCreating;
    private static LatLng mUserLatLng;
    private static Polyline mPolyline;
    private Button save_button, toggle_delete, edit_demanda_strand;

    public static AppCompatDialogFragment newInstance(Activity activity, DemandaStrandEditDialogListener listener, DemandaStrand mDemandaStrand, LatLng mUserLatLng, Polyline polyline) {
        DemandaStrandActionsDialogFragment.mDemandaStrand = mDemandaStrand;
        DemandaStrandActionsDialogFragment.mListener = listener;
        DemandaStrandActionsDialogFragment.mCreating = mUserLatLng != null;
        DemandaStrandActionsDialogFragment.activity = activity;
        DemandaStrandActionsDialogFragment.mPolyline = polyline;
        if (DemandaStrandActionsDialogFragment.mCreating) {
            DemandaStrandActionsDialogFragment.mUserLatLng = mUserLatLng;
        }
        return new DemandaStrandActionsDialogFragment();
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_strand_actions, container, false);

        save_button = (Button) view.findViewById(R.id.save_button);
        toggle_delete = (Button) view.findViewById(R.id.toggle_delete);
        edit_demanda_strand = (Button) view.findViewById(R.id.edit_demanda_strand);

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPolyline.setColor(Color.parseColor("#000000"));
                dismissAllowingStateLoss();
            }
        });

        toggle_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onDeletarDemandaStrand(mDemandaStrand, mPolyline);
            }
        });

        edit_demanda_strand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPolyline.setColor(Color.parseColor("#ff9900"));
                mListener.onEditDemandaStrand(mDemandaStrand, mPolyline);
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        super.onStop();
        mPolyline.setColor(Color.parseColor("#ff9900"));
    }
}
