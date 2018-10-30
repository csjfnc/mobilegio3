package com.visium.fieldservice.ui.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.Lighting;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.ui.dialog.adapter.LightingAdapter;

import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class LightingListDialogFragment extends AppCompatDialogFragment {

    private static List<Lighting> mLightingList;
    private static Post mPost;
    private static LightingListDialogListener mListener;
    private static LightingAdapter mAdapter;
    private static ListView mLightingListView;
    private static View mLightingListEmptyView;

    public static LightingListDialogFragment newInstance(LightingListDialogListener listener,
                                                      List<Lighting> lightingList,
                                                      Post post) {
        for(Lighting l : lightingList) {
            if(l.isExcluido())
                lightingList.remove(l);
        }
        LightingListDialogFragment.mLightingList = lightingList;
        LightingListDialogFragment.mPost = post;
        LightingListDialogFragment.mListener = listener;
        return new LightingListDialogFragment();
    }

    public void notifyDataSetChanged() {
        if (mAdapter != null) {
            switchLayout();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mAdapter != null) {
            switchLayout();
            mAdapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lighting_list, container, false);
         ((TextView) view.findViewById(R.id.lighting_list_title))
                 .setText(getString(R.string.dialog_lighting_list_title, mPost.getGeoCode()));

        mLightingListView = (ListView) view.findViewById(R.id.lighting_list);
        mLightingListEmptyView = view.findViewById(R.id.lighting_list_empty);
        mAdapter = new LightingAdapter(mPost, mLightingList, getContext(), mListener);
        mLightingListView.setAdapter(mAdapter);
        switchLayout();

        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

        view.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onAddLightingButtonClicked(mPost);
            }
        });

        return view;
    }

    public void switchLayout() {
        if (mLightingList.size() > 0) {
            mLightingListView.setVisibility(View.VISIBLE);
            mLightingListEmptyView.setVisibility(View.GONE);
        } else {
            mLightingListView.setVisibility(View.GONE);
            mLightingListEmptyView.setVisibility(View.VISIBLE);
        }
    }

}