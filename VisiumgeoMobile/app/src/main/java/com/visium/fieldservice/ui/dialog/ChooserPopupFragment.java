package com.visium.fieldservice.ui.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.base.MyEnum;
import com.visium.fieldservice.controller.PostController;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.PontoAtualizacao;
import com.visium.fieldservice.entity.PopupType;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.PostEffort;
import com.visium.fieldservice.entity.PostHeight;
import com.visium.fieldservice.entity.PostLocation;
import com.visium.fieldservice.entity.PostPhotos;
import com.visium.fieldservice.entity.PostType;
import com.visium.fieldservice.ui.maps.MapsPickLocationActivity;
import com.visium.fieldservice.ui.maps.MapsPostsActivity;
import com.visium.fieldservice.ui.util.ViewUtils;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.LogUtils;
import com.visium.fieldservice.verifier.Verifier;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */

public class ChooserPopupFragment extends AppCompatDialogFragment {

    private static final int PICKUP_LOCATION = 1;

    private static PostEditDialogListener mListener;
    private static boolean mCreating;
    private ScrollView mScrollView;
    private static EditText mValue;
    private GridView mGridView;
    private static String[] mItemList;
    private ArrayAdapter<String> mAdapter;
    private static Context mCtx;

    public static AppCompatDialogFragment newInstance(Context ctx, int popupType, String[] itemList) {
        mItemList = itemList;
        mCtx = ctx;
        LogUtils.log("ChooserPopupFragment: newInstance");
        /*
        if (ChooserPopupFragment.mCreating) {
            ChooserPopupFragment.mUserLatLng = mUserLatLng;
        }*/
        return new ChooserPopupFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, 0);
        //setStyle(AppCompatDialogFragment.STYLE_NO_FRAME, 0);
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popup_chooser, container, false);

        mGridView = (GridView) view.findViewById(R.id.gridView);
        mValue = (EditText) view.findViewById(R.id.editValue);
        mScrollView = (ScrollView) view.findViewById(R.id.scroll);

        mAdapter = new ArrayAdapter<String>(mCtx,
                android.R.layout.simple_list_item_1, mItemList);

        mGridView.setAdapter(mAdapter);

        view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });


        /*
        view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //EquipmentUpdateUtils.setPontoAtualizacao(getContext(), mPost);
                Location l = mListener.getLastLocation();
                mPost.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis()/1000));
                mPost.setUpdate(true);

                final ProgressDialog alertDialog = new ProgressDialog(getActivity(), R.style.AlertDialogTheme);
                alertDialog.setTitle(getString(R.string.dialog_post_edit_saving_title, mPost.getGeoCode()));
                alertDialog.setMessage(getString(R.string.dialog_post_edit_saving_message));
                alertDialog.setCancelable(false);
                alertDialog.show();

                mPost.setObservations(ViewUtils.getTextViewValue(mObservations));
                mPost.setHeight(PostHeight.getHeights().get(mHeight.getSelectedItemPosition()).doubleValue());
                mPost.setPostEffort(PostEffort.getNames().get(mEffort.getSelectedItemPosition()).toString());
                mPost.setPostType(mType.getSelectedItemPosition());
                mPost.setCreateDate(new Date());


                mPost.setPhotos(photosList);

                boolean workingOffline = FileUtils.serviceOrderFileExists(mPost.getOrderId());
                if (mCreating) {
                    mPost.setClosed(true);
                    mPost.setUpdate(true);
                    mPost.setPostNumber(mListener.getNextPostNumber());
                    LogUtils.log("highestPostNumber = " + mListener.getHighestPostNumber());

                    if (workingOffline) {

                        try {
                            OfflineDownloadResponse download = FileUtils.retrieve(mPost.getOrderId());
                            List<Post> posts = download.getPosts();
                            mPost.setId(-1 * System.currentTimeMillis());
                            posts.add(mPost);
                            download.setPostList(posts);

                            if (FileUtils.saveOfflineDownload(download)) {
                                Verifier.clearRollBackStack();
                                Verifier.addPostCount(mPost.getGeoCode());
                                Toast.makeText(getActivity(),
                                        R.string.dialog_post_edit_saving_success,
                                        Toast.LENGTH_LONG).show();
                                dismissAllowingStateLoss();
                                mListener.onPostChanged(mPost, true);

                            } else {
                                mPost.setClosed(false);
                                Toast.makeText(getActivity(),
                                        getString(R.string.dialog_post_edit_saving_error,
                                                mPost.getId()),
                                        Toast.LENGTH_LONG).show();
                            }

                            alertDialog.dismiss();

                        } catch (IOException e) {
                            mPost.setClosed(false);
                            LogUtils.error(this, e);
                            alertDialog.dismiss();
                            Toast.makeText(getActivity(),
                                    getString(R.string.dialog_post_edit_saving_error,
                                            mPost.getId()),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                    else {

                        PostController.get().create(getContext(), mPost, new VisiumApiCallback<Post>() {
                            @Override
                            public void callback(Post post, ErrorResponse e) {
                                alertDialog.dismiss();

                                if (e == null && post != null) {
                                    Verifier.clearRollBackStack();
                                    Verifier.addPostCount(mPost.getGeoCode());
                                    Toast.makeText(getActivity(),
                                            R.string.dialog_post_edit_saving_success,
                                            Toast.LENGTH_LONG).show();
                                    dismissAllowingStateLoss();
                                    mListener.onPostChanged(post, true);
                                } else if (e != null && e.isCustomized()) {
                                    mPost.setClosed(false);
                                    Toast.makeText(getActivity(),
                                            e.getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    mPost.setClosed(false);
                                    Toast.makeText(getActivity(),
                                            getString(R.string.dialog_post_edit_saving_error,
                                                    mPost.getId()),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                } else {
                    //mPost.setClosed(mTransaction.isChecked());

                    if (workingOffline) {

                        try {
                            OfflineDownloadResponse download = FileUtils.retrieve(mPost.getOrderId());
                            List<Post> posts = download.getPosts();

                            Iterator<Post> iterator = posts.iterator();

                            while (iterator.hasNext()) {
                                Post post = iterator.next();
                                if (post.getId() == mPost.getId()) {
                                    iterator.remove();
                                    break;
                                }
                            }

                            posts.add(mPost);
                            download.setPostList(posts);

                            if (FileUtils.saveOfflineDownload(download)) {
                                Verifier.addPostCount(mPost.getGeoCode());
                                Toast.makeText(getActivity(),
                                        R.string.dialog_post_edit_saving_success,
                                        Toast.LENGTH_LONG).show();
                                dismissAllowingStateLoss();
                                mListener.onPostChanged(mPost, false);

                            } else {

                                Toast.makeText(getActivity(),
                                        getString(R.string.dialog_post_edit_saving_error,
                                                mPost.getId()),
                                        Toast.LENGTH_LONG).show();
                            }

                            alertDialog.dismiss();

                        } catch (IOException e) {
                            LogUtils.error(this, e);
                            alertDialog.dismiss();

                            Toast.makeText(getActivity(),
                                    getString(R.string.dialog_post_edit_saving_error,
                                            mPost.getId()),
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                    else {
                        PostController.get().save(getContext(), mPost, new VisiumApiCallback<Post>() {
                            @Override
                            public void callback(Post post, ErrorResponse e) {
                                alertDialog.dismiss();

                                if (e == null && post != null) {
                                    Verifier.addPostCount(mPost.getGeoCode());
                                    Toast.makeText(getActivity(),
                                            R.string.dialog_post_edit_saving_success,
                                            Toast.LENGTH_LONG).show();
                                    dismissAllowingStateLoss();
                                    mListener.onPostChanged(mPost, false);
                                } else if (e != null && e.isCustomized()) {
                                    Toast.makeText(getActivity(),
                                            e.getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(getActivity(),
                                            R.string.dialog_post_edit_saving_error,
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }
            }
        });
        */


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }
}