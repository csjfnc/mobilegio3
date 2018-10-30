package com.visium.fieldservice.ui.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.memoria.DownloadOrderOffline;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.PostPhotos;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.LogUtils;
import com.visium.fieldservice.verifier.Verifier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Created by fjesus on 10/05/2018.
 */

public class FragmentPhotosMaquina extends AppCompatDialogFragment {


    private LinearLayout mLayoutImages;
    private ScrollView scroll;
    private static Button definirFoto, adicionarFoto, salvarFotosPoste, canelarFotosPoste;
    private static Post mPost;
    private static List<PostPhotos> photosList;

    public static AppCompatDialogFragment newInstance(Post mPost) {

        FragmentPhotosMaquina.mPost = mPost;

        return new FragmentPhotosMaquina();
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(AppCompatDialogFragment.STYLE_NO_TITLE, 0);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fotos_poste, container, false);

        mLayoutImages = (LinearLayout) view.findViewById(R.id.layoutImagesNovo);
        scroll = (ScrollView) view.findViewById(R.id.scroll);
        adicionarFoto = (Button) view.findViewById(R.id.adicionarFoto);
        definirFoto = (Button) view.findViewById(R.id.definirFoto);
        salvarFotosPoste = (Button) view.findViewById(R.id.salvarFotosPoste);
        canelarFotosPoste = (Button) view.findViewById(R.id.canelarFotosPoste);

        photosList = new ArrayList<>();
        final FragmentPhotosMaquina c = this;

        definirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Verifier.prompt(c.getContext());
            }
        });

        adicionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Verifier.getNextNumber(mPost.getGeoCode(), c);
                if (n > 0) {
                    addImage(c.getContext(), String.format("%04d", n), true);
                }
            }
        });

        if (mPost.getPhotos() != null) {
            List<PostPhotos> imagesList = mPost.getPhotos();
            for (PostPhotos pp : imagesList) {
                addImage(c.getContext(), pp.getNumber(), false);
                photosList.add(pp);
            }
        }

        salvarFotosPoste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    mPost.setPhotos(photosList);

                    OfflineDownloadResponse download = null;
                    if (DownloadOrderOffline.getResponse() == null) {
                        DownloadOrderOffline.setResponse(FileUtils.retrieve(mPost.getOrderId()));
                        download = DownloadOrderOffline.getResponse();
                    } else {
                        download = DownloadOrderOffline.getResponse();
                    }
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

                    } else {

                        Toast.makeText(getActivity(),
                                getString(R.string.dialog_post_edit_saving_error,
                                        mPost.getId()),
                                Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {

                }
            }
        });

        canelarFotosPoste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissAllowingStateLoss();
            }
        });

        return view;
    }

    @SuppressLint("ResourceAsColor")
    public void addImage(Context c, final String value, boolean isNew) {
        final LinearLayout l = new LinearLayout(c);
        final EditText lEdit = new EditText(c);
        final View divider = new View(c);
        //ViewUtils.setViewMargins(c, new LinearLayout.LayoutParams(), 0, 0, 30, 10, lEdit);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        layoutParams.setMargins(0, 0, 60, 20);
        lEdit.setLayoutParams(layoutParams);
        lEdit.setTextSize(18);
        lEdit.setBackgroundColor(Color.parseColor("#f1f1f1"));

        lEdit.setText(Html.fromHtml("<b>" + value + "<b>"));
        Button b = new Button(c);
        b.setText("Apagar");

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutImages.removeView(l);
                int toRemove = -1;
                for (int i = 0; i < photosList.size(); i++) {
                    PostPhotos p = photosList.get(i);
                    if (p.getNumber().equals(value)) {
                        toRemove = i;
                        break;
                    }
                }
                photosList.remove(toRemove);
            }
        });


        l.addView(lEdit);
        l.addView(b);
        mLayoutImages.addView(l);
        if (isNew) {
            Calendar cc = Calendar.getInstance();
            //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(cc.getTime());
            LogUtils.log("Date = " + date);
            photosList.add(new PostPhotos(value, date));
            scroll.post(new Runnable() {
                @Override
                public void run() {
                    scroll.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }
}
