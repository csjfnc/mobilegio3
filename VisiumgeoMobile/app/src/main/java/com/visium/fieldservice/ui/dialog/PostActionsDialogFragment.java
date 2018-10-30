package com.visium.fieldservice.ui.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import android.widget.ViewSwitcher;

import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.controller.PostController;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.PontoAtualizacao;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.ui.maps.MapsPostsActivity;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.LogUtils;
import com.visium.fieldservice.verifier.Verifier;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */
public class PostActionsDialogFragment extends AppCompatDialogFragment {

    private static Post mPost;
    private static PostActionsDialogListener mListener;
    private Button toggleDelete;

    public static AppCompatDialogFragment newInstance(PostActionsDialogListener listener, Post post) {
        PostActionsDialogFragment.mPost = post;
        PostActionsDialogFragment.mListener = listener;
        return new PostActionsDialogFragment();
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
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_post_actions, container, false);
        toggleDelete = (Button) view.findViewById(R.id.toggle_delete);
        toggleDelete.setText(mPost.isExcluido() ? "Restaurar poste" : "Apagar poste");
        ((TextView) view.findViewById(R.id.post_actions_title))
                .setText(getString(R.string.dialog_post_actions_title, mPost.getGeoCode() + " / " + mPost.getPostNumber()));

        view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismissAllowingStateLoss();
            }
        });

        view.findViewById(R.id.local_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEditLocalButtonClicked(mPost);
                dismissAllowingStateLoss();
            }
        });

        /*view.findViewById(R.id.edit_list_lighting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onListLightingPostButtonClicked(mPost);
            }
        });*/

        /*//view.findViewById(R.id.edit_pontoentrega).setClickable(false);
        view.findViewById(R.id.edit_pontoentrega).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onListPontoEntregaButtonClicked(mPost);
            }
        });*/


        view.findViewById(R.id.edit_post).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            mListener.onEditPostButtonClicked(mPost);
            }
        });

        toggleDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onToggleDeletePostButtonClicked(mPost);
            }
        });

       /* view.findViewById(R.id.edit_afloramento).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onListAfloramentosButtonClicked(mPost);
            }
        });*/

       /* view.findViewById(R.id.edit_banco_capacitador).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onListBancoCapacitadorButtonClicked(mPost);
            }
        });*/

        view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if (!mPost.isClosed()) {
                    mPost.setClosed(true);
                    mPost.setUpdate(true);
                    Location l = ((MapsPostsActivity) mListener).getLastLocation();
                    mPost.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis() / 1000));
                    mPost.setUpdate(true);
                    final ProgressDialog alertDialog = new ProgressDialog(getActivity(), R.style.AlertDialogTheme);
                    alertDialog.setTitle(getString(R.string.dialog_post_edit_saving_title, mPost.getGeoCode()));
                    alertDialog.setMessage(getString(R.string.dialog_post_edit_saving_message));
                    alertDialog.setCancelable(false);
                    alertDialog.show();
                    boolean workingOffline = FileUtils.serviceOrderFileExists(mPost.getOrderId());
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
                                alertDialog.dismiss();
                                ((PostEditDialogListener) mListener).onPostChanged(mPost, false);
                                dismissAllowingStateLoss();

                            } else {
                                mPost.setClosed(false);
                                alertDialog.dismiss();
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

                    } else {
                        PostController.get().save(getContext(), mPost, new VisiumApiCallback<Post>() {
                            @Override
                            public void callback(Post post, ErrorResponse e) {
                                alertDialog.dismiss();

                                if (e == null && post != null) {
                                    Verifier.addPostCount(mPost.getGeoCode());
                                    Toast.makeText(getActivity(),
                                            R.string.dialog_post_edit_saving_success,
                                            Toast.LENGTH_LONG).show();
                                    ((PostEditDialogListener) mListener).onPostChanged(mPost, false);
                                    dismissAllowingStateLoss();
                                } else if (e != null && e.isCustomized()) {
                                    mPost.setClosed(false);
                                    Toast.makeText(getActivity(),
                                            e.getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    mPost.setClosed(false);
                                    Toast.makeText(getActivity(),
                                            R.string.dialog_post_edit_saving_error,
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                } else*/
                    dismissAllowingStateLoss();
            }
        });

        return view;
    }

}