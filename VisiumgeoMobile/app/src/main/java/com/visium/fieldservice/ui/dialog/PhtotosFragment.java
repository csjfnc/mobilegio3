package com.visium.fieldservice.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.visium.fieldservice.BuildConfig;
import com.visium.fieldservice.R;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.PostPhotos;
import com.visium.fieldservice.ui.dialog.adapter.RecFotosAdapter;
import com.visium.fieldservice.util.LogUtils;
import com.visium.fieldservice.verifier.Verifier;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit.http.POST;

/**
 * Created by fjesus on 03/05/2018.
 */

public class PhtotosFragment extends AppCompatDialogFragment {

    public static final int RESULT_CAMERA = 575;
    private RecyclerView rec_fotos;
    private RecFotosAdapter recFotosAdapter;
    private static Post mPost;
    private Button adicionarFoto, definirFoto;
    private static List<PostPhotos> photosList;
    private LinearLayout mLayoutImages;
    private ScrollView mScrollView;
    private static Activity activity;
    private String caminhoFoto;
    private int n;
    private PhtotosFragment c;

    public static AppCompatDialogFragment newInstance(Post post) {

        PhtotosFragment.mPost = post;
        PhtotosFragment.activity = activity;
        return new PhtotosFragment();
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
        c = this;
        //rec_fotos = (RecyclerView) view.findViewById(R.id.rec_fotos);
//        rec_fotos.setLayoutManager(new LinearLayoutManager(getContext()));
        adicionarFoto = (Button) view.findViewById(R.id.adicionarFoto);
        definirFoto = (Button) view.findViewById(R.id.definirFoto);
        mLayoutImages = (LinearLayout) view.findViewById(R.id.layoutImagesNovo);
        mScrollView = (ScrollView) view.findViewById(R.id.scroll);
        photosList = new ArrayList<>();
        List<PostPhotos> postPhotos = mPost.getPhotos();


        definirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Verifier.prompt(c.getContext());
            }
        });

        adicionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                n = Verifier.getNextNumber(mPost.getGeoCode(), c);
                if(n>0) {
                    tirarFoto(mPost.getId()+"_"+String.format("%04d", n));
                }
            }
        });

        if (mPost.getPhotos() != null) {
            List<PostPhotos> imagesList = mPost.getPhotos();
            for(PostPhotos pp : imagesList) {
                addImage(c.getContext(), pp.getNumber(), false);
                photosList.add(pp);
            }
        }

        return view;
    }

    public void tirarFoto(String numero){
        String version = String.valueOf(BuildConfig.VERSION_CODE);
        File f = Environment.getExternalStoragePublicDirectory("/Fotos Visium Geo");
        File ver = new File(version);
        File visium = new File(f, ver.getName());

        if(!f.exists()){
            f.mkdir();
        }
        //Verifca se a pasta com o nome da versao exite, se nao ela é criada
        if(!visium.exists()){
            visium.mkdir();
        }

        File file = new File(visium.getPath()+"/"+numero+".jpg");
        addImage(c.getContext(), numero, true);

        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // caminhoFoto = getActivity().getExternalFilesDir(null)+"/"+numero+".jpg";
       /* File arquivoFoto = new File(caminhoFoto);*/
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        startActivityForResult(intentCamera, RESULT_CAMERA);
    }



    public void addImage(Context c, final String value, boolean isNew) {
        final LinearLayout l = new LinearLayout(c);
        final EditText lEdit = new EditText(c);
        lEdit.setBackgroundColor(Color.WHITE);

        //ViewUtils.setViewMargins(c, new LinearLayout.LayoutParams(), 0, 0, 30, 10, lEdit);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 60, 20);
        lEdit.setLayoutParams(layoutParams);
        lEdit.setText("   "+ value+"   ");
        Button b = new Button(c);
        b.setBackgroundResource(R.drawable.ic_clear_black_24dp);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayoutImages.removeView(l);
                int toRemove = -1;
                for(int i = 0; i<photosList.size(); i++) {
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
        if(isNew) {
            Calendar cc = Calendar.getInstance();
            //SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = df.format(cc.getTime());
            LogUtils.log("Date = "+date);
            photosList.add(new PostPhotos(value, date));
            mScrollView.post(new Runnable() {
                @Override
                public void run() {
                    mScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                }
            });
        }
    }

}
