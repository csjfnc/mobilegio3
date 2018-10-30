package com.visium.fieldservice.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.SwitchCompat;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.maps.model.LatLng;
import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.memoria.DownloadOrderOffline;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.controller.PostController;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.OcupantesD;
import com.visium.fieldservice.entity.OcupantesS;
import com.visium.fieldservice.entity.PontoAtualizacao;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.PostEffort;
import com.visium.fieldservice.entity.PostEffortMadeira;
import com.visium.fieldservice.entity.PostEquipament;
import com.visium.fieldservice.entity.PostHeight;
import com.visium.fieldservice.entity.PostHeightGrid;
import com.visium.fieldservice.entity.PostLocation;
import com.visium.fieldservice.entity.PostPhotos;
import com.visium.fieldservice.entity.PostRedePrimaria;
import com.visium.fieldservice.entity.PostType;
import com.visium.fieldservice.ui.dialog.adapter.AlturaAdapter;
import com.visium.fieldservice.ui.dialog.adapter.EsforcoAdapter;
import com.visium.fieldservice.ui.dialog.adapter.EsforcoMadeiraAdapter;
import com.visium.fieldservice.ui.dialog.adapter.OcupanteDAdapter;
import com.visium.fieldservice.ui.dialog.adapter.OcupanteSAdapter;
import com.visium.fieldservice.ui.dialog.adapter.PostEquipamentAdapter;
import com.visium.fieldservice.ui.dialog.adapter.PostRedeprimariaAdapter;
import com.visium.fieldservice.ui.dialog.adapter.TipoAdapter;
import com.visium.fieldservice.ui.maps.MapsPickLocationActivity;
import com.visium.fieldservice.ui.maps.MapsPostsActivity;
import com.visium.fieldservice.ui.util.EquipmentUpdateUtils;
import com.visium.fieldservice.ui.util.ViewUtils;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.LogUtils;
import com.visium.fieldservice.verifier.Verifier;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import me.originqiu.library.EditTag;

/**
 * @author Andrew Willard (andrewillard@gmail.com)
 */

public class PostEditDialogFragment extends AppCompatDialogFragment {

    private static final int PICKUP_LOCATION = 1;

    private static Post mPost;
    private static PostEditDialogListener mListener;
    private static Button mImageAdd, mSetStart;
    private static LatLng mUserLatLng;
    private static boolean mCreating;
    private LinearLayout mLayoutImages;
    private ScrollView mScrollView;
    private static Spinner mType, mEffort, mHeight, ocupante_s, ocupante_d;
    private static EditText mObservations;
    private static List<PostPhotos> photosList;
    private static Activity activity;
    private AlertDialog alertDialog;
    private GridView gridAltura, grid_esforco,grid_post_tipo, grid_ocupante_s, grid_ocupante_d, grid_equipamento, grid_redeprimaria;
    private AlturaAdapter alturaAdapter;
    private EsforcoAdapter esforcoAdapter;
    private EsforcoMadeiraAdapter esforcoMadeiraAdapter;
    private TipoAdapter tipoAdapter;
    private OcupanteSAdapter ocupanteSAdapter;
    private OcupanteDAdapter ocupanteDAdapter;
    private PostEquipamentAdapter postEquipamentAdapter;
    private PostRedeprimariaAdapter postRedeprimariaAdapter;
    private boolean podeEquipament;
    private LinearLayout lay_seq2;

    private static TextView post_seq_edit, post_seq_effort, post_seq_tipo, post_seq_ocupantes, post_seq_ocupanted,
            post_seq_rede_primaria, post_seq_equipament;
    private int esforco, tipo_visible, equipamentSelect, redePrimariaSelect;
    private int altura;
    private int ocupantes, ocupanted, tipo;

    public static AppCompatDialogFragment newInstance(Activity activity, PostEditDialogListener listener, Post post) {
        return PostEditDialogFragment.newInstance(activity, listener, post, null);
    }

    public static AppCompatDialogFragment newInstance(Activity activity, PostEditDialogListener listener, Post post, LatLng mUserLatLng) {
        PostEditDialogFragment.mPost = post;
        PostEditDialogFragment.mListener = listener;
        PostEditDialogFragment.mCreating = mUserLatLng != null;
        PostEditDialogFragment.activity = activity;
        if (PostEditDialogFragment.mCreating) {
            PostEditDialogFragment.mUserLatLng = mUserLatLng;
        }
        return new PostEditDialogFragment();
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

    public void addImage(Context c, final String value, boolean isNew) {
        final LinearLayout l = new LinearLayout(c);
        final EditText lEdit = new EditText(c);
        //ViewUtils.setViewMargins(c, new LinearLayout.LayoutParams(), 0, 0, 30, 10, lEdit);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 60, 20);
        lEdit.setLayoutParams(layoutParams);
        lEdit.setText(value);
        Button b = new Button(c);
        b.setText("Apagar");
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       // criaForm();

      //  AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        photosList = new ArrayList<>();
        //View view = inflater.inflate(R.layout.fragment_post_edit, container, false);
        View view = inflater.inflate(R.layout.layout_edit_post_next, container, false);
        mImageAdd = (Button) view.findViewById(R.id.button_add);
        mSetStart = (Button) view.findViewById(R.id.button_set_start);
        mScrollView = (ScrollView) view.findViewById(R.id.scroll);
        mLayoutImages = (LinearLayout) view.findViewById(R.id.layoutImages);
        mType = (Spinner) view.findViewById(R.id.post_type);
        //mHeight = (Spinner) view.findViewById(R.id.post_height);
        mEffort = (Spinner) view.findViewById(R.id.post_effort);
        mObservations = (EditText) view.findViewById(R.id.edit_observation);
        ocupante_s = (Spinner) view.findViewById(R.id.ocupante_s);
        ocupante_d = (Spinner) view.findViewById(R.id.ocupante_d);
        gridAltura = (GridView) view.findViewById(R.id.gridAltura);
        grid_esforco = (GridView) view.findViewById(R.id.grid_esforco);
        grid_post_tipo = (GridView) view.findViewById(R.id.grid_post_tipo);
        grid_ocupante_s = (GridView) view.findViewById(R.id.grid_ocupante_s);
        grid_ocupante_d = (GridView) view.findViewById(R.id.grid_ocupante_d);
        grid_equipamento = (GridView) view.findViewById(R.id.grid_equipamento);
        grid_redeprimaria = (GridView) view.findViewById(R.id.grid_redeprimaria);

        post_seq_edit = (TextView) view.findViewById(R.id.post_seq_edit);
        post_seq_effort = (TextView) view.findViewById(R.id.post_seq_effort);
        post_seq_tipo = (TextView) view.findViewById(R.id.post_seq_tipo);
        post_seq_ocupantes = (TextView) view.findViewById(R.id.post_seq_ocupantes);
        post_seq_ocupanted = (TextView) view.findViewById(R.id.post_seq_ocupanted);
        post_seq_equipament = (TextView) view.findViewById(R.id.post_seq_equipament);
        post_seq_rede_primaria = (TextView) view.findViewById(R.id.post_seq_rede_primaria);

        lay_seq2 = (LinearLayout) view.findViewById(R.id.lay_seq2);


        final ViewFlipper viewFlipper = (ViewFlipper) view.findViewById(R.id.vs);
        final Button next_button = (Button) view.findViewById(R.id.next_button);
        Button back_button = (Button) view.findViewById(R.id.back_button);

        /*Animation in = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_in_left);
        Animation out = AnimationUtils.loadAnimation(getActivity(), android.R.anim.slide_out_right);*/

        viewFlipper.setInAnimation(null);
        viewFlipper.setOutAnimation(null);

        final int fim = 7;
        final int[] count = {0};

      //  builder.setView(view);

        //List<PostHeightGrid> postHeightGrids = Arrays.asList(PostHeight.values());

        final List<PostHeight> list = Arrays.asList(PostHeight.values());
        final List<PostEffort> list_effort = Arrays.asList(PostEffort.values());
        final List<PostEffortMadeira> list_effort_madeira = Arrays.asList(PostEffortMadeira.values());
        final List<PostType> list_type = Arrays.asList(PostType.values());
        final List<OcupantesS> list_Ocupantes = Arrays.asList(OcupantesS.values());
        final List<OcupantesD> list_OcupanteD = Arrays.asList(OcupantesD.values());
        final List<PostEquipament> list_PostEquipaments = Arrays.asList(PostEquipament.values());
        final List<PostRedePrimaria> list_RedePrimarias = Arrays.asList(PostRedePrimaria.values());

        alturaAdapter = new AlturaAdapter(getActivity(), list);
        gridAltura.setAdapter(alturaAdapter);



        tipoAdapter = new TipoAdapter(getActivity(), list_type);
        grid_post_tipo.setAdapter(tipoAdapter);

        ocupanteSAdapter = new OcupanteSAdapter(getActivity(), list_Ocupantes);
        grid_ocupante_s.setAdapter(ocupanteSAdapter);

        ocupanteDAdapter = new OcupanteDAdapter(getActivity(), list_OcupanteD);
        grid_ocupante_d.setAdapter(ocupanteDAdapter);

        postEquipamentAdapter = new PostEquipamentAdapter(getActivity(), list_PostEquipaments);
        grid_equipamento.setAdapter(postEquipamentAdapter);

        postRedeprimariaAdapter = new PostRedeprimariaAdapter(getActivity(), list_RedePrimarias);
        grid_redeprimaria.setAdapter(postRedeprimariaAdapter);



        grid_post_tipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PostType type = list_type.get(i);
                tipo = i;
                post_seq_tipo.setText(type.getName()+"");
                viewFlipper.showNext();
                count[0]++;

                if(i == 3){
                    esforcoMadeiraAdapter = new EsforcoMadeiraAdapter (getActivity(), list_effort_madeira);
                    grid_esforco.setAdapter(esforcoMadeiraAdapter);
                }else{
                    esforcoAdapter = new EsforcoAdapter(getActivity(), list_effort);
                    grid_esforco.setAdapter(esforcoAdapter);
                }

            }
        });



        gridAltura.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PostHeight postHeight = list.get(i);
                altura = i;
                post_seq_edit.setText(" / "+altura+"");
                viewFlipper.showNext();
                count[0]++;

                if(i > 2){
                    podeEquipament = true;
                    post_seq_equipament.setVisibility(View.VISIBLE);
                }else{
                    podeEquipament = false;
                    post_seq_equipament.setText("");
                    post_seq_equipament.setVisibility(View.GONE);
                }

            }
        });

        grid_esforco.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PostEffort postEffort = list_effort.get(i);
                esforco = i;
                post_seq_effort.setText(" / "+postEffort.getName()+"");
                if(podeEquipament){
                    viewFlipper.showNext();
                    count[0]++;
                }else{
                    viewFlipper.showNext();
                    viewFlipper.showNext();
                    count[0]++;
                    count[0]++;
                }
            }
        });

        grid_equipamento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PostEquipament equipament = list_PostEquipaments.get(position);
                equipamentSelect = position;
                post_seq_equipament.setText(" / "+equipament.getNome());
                viewFlipper.showNext();
                count[0]++;
            }
        });

        grid_redeprimaria.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PostRedePrimaria postRedePrimaria = list_RedePrimarias.get(position);
                redePrimariaSelect =position;

                    post_seq_rede_primaria.setText(postRedePrimaria.getName());

                viewFlipper.showNext();
                count[0]++;
            }
        });

        grid_ocupante_s.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OcupantesS ocupantesS = list_Ocupantes.get(i);
                ocupantes = ocupantesS.getOcupantes();
                post_seq_ocupantes.setText(" / "+ocupantesS.getOcupantes()+"");
                viewFlipper.showNext();
                count[0]++;
            }
        });


        grid_ocupante_d.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                OcupantesD ocupantesD = list_OcupanteD.get(i);
                ocupanted = ocupantesD.getOcupantesD();
                post_seq_ocupanted.setText(" / "+ocupantesD.getOcupantesD()+"");
                viewFlipper.showNext();
                count[0]++;
            }
        });



/*        ocupante_s.setAdapter(new ArrayAdapter<>(getContext(),
                R.layout.custom_spinner_item,
                OcupantesS.getOcupantes_s()));*/

     /*   ocupante_d.setAdapter(new ArrayAdapter<>(getContext(),
                R.layout.custom_spinner_item,
                OcupantesD.getOcupantesd()));
*/

/*        mType.setAdapter(
                new ArrayAdapter<>(getContext(),
                        R.layout.custom_spinner_item,
                        PostType.getNames()));*/

/*        mHeight.setAdapter(
                new ArrayAdapter<>(getContext(),
                        R.layout.custom_spinner_item,
                        PostHeight.getHeights()));*/

/*        mEffort.setAdapter(
                new ArrayAdapter<>(getContext(),
                        R.layout.custom_spinner_item,
                        PostEffort.getNames()));*/




        /*mEffort.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    PostEffort.getValuesArray();
                    ChooserPopupFragment.newInstance(0, PostEffort.getValuesArray());
                }
            }
        });*/
        /*mType.setAdapter(
                new EnumsAdapter(getContext(), android.R.layout.simple_spinner_item,
                        PostConfiguration.getEnum(PostConfiguration.NAMES.POST_TYPE)));
        mType.setSelection(0);*/

        final PostEditDialogFragment c = this;
        mObservations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                ChooserPopupFragment.newInstance(getContext(), 0, PostEffort.getValuesArray())
                        .show(getChildFragmentManager(), "ChooserPopup");*/
                AlertDialog.Builder alert = new AlertDialog.Builder(c.getActivity());

                alert.setTitle("Observações");
                //alert.setMessage("Message");
                final InputMethodManager imm = (InputMethodManager) c.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

                final EditText input = new EditText(c.getActivity());
                //input.setCursorVisible(true);
                input.setText(mObservations.getText());
                input.setFocusable(true);
                input.setRawInputType(InputType.TYPE_CLASS_TEXT);
                input.setTextIsSelectable(true);
                input.setTextColor(getResources().getColor(R.color.black));
                alert.setView(input);
                input.post(new Runnable() {
                    @Override
                    public void run() {
                        input.setSelection(mObservations.getText().toString().length());
                    }
                });
                imm.showSoftInput(v, InputMethodManager.SHOW_FORCED);

                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                        mObservations.setText(ViewUtils.getTextViewValue(input));
                    //    viewFlipper.showNext();
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        imm.hideSoftInputFromWindow(input.getWindowToken(), 0);
                        // Canceled.
                    }
                });

                alert.show();
                input.requestFocus();
            }
        });

        mSetStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Verifier.prompt(c.getContext());
            }
        });

        mImageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int n = Verifier.getNextNumber(mPost.getGeoCode(), c);
                if(n>0) {
                    addImage(c.getContext(), String.format("%04d", n), true);
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

//        view.findViewById(R.id.location_button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), MapsPickLocationActivity.class);
//               // intent.putExtra(MapsPostsActivity.POST_LIST, new ArrayList<>(mListener.getPostsList()));
//                intent.putExtra(MapsPostsActivity.SERVICE_ORDER_POINTS, new ArrayList<>(mListener.getOrderPoints()));
//
//                intent.putExtra("orderId", mPost.getOrderId());
//
//                if (PostEditDialogFragment.mCreating) {
//                    intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, PostEditDialogFragment.mUserLatLng);
//                    //intent.putExtra(MapsPickLocationActivity.METERS_RESTRICTION, 10f);
//                } else {
//                    intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, mPost.getLocation().toLatLng());
//                }
//                startActivityForResult(intent, PICKUP_LOCATION);
//            }
//        });


        //mTransaction = (SwitchCompat) view.findViewById(R.id.transaction_switch);
        if (mCreating) {
            //mTransaction.setVisibility(View.GONE);
            ((TextView) view.findViewById(R.id.post_edit_title))
                    .setText(R.string.dialog_post_edit_creating_title);
           // mType.setSelection(PostType.SEM_INFORMACAO.ordinal());
            //mTransaction.setChecked(mPost.isClosed());
            mObservations.setText(mPost.getObservations());
            // mHeight.setSelection(0);
            // mEffort.setSelection(0);
           // mType.setSelection(0);
          //  ocupante_s.setSelection(0);
         //   ocupante_d.setSelection(0);
            Location l = mListener.getLastLocation();
            mPost.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis()/1000));
            mPost.setUpdate(true);
        } else {
            ((TextView) view.findViewById(R.id.post_edit_title))
                    .setText(getString(R.string.dialog_post_edit_title, mPost.getGeoCode()));
            //mTransaction.setChecked(mPost.isClosed());
            mObservations.setText(mPost.getObservations());
            altura = mPost.getHeight();
            esforco = mPost.getPostEffort();
            ocupantes = OcupantesS.parse(mPost.getOcupante_s()).ordinal();
            ocupanted = OcupantesD.parse(mPost.getOcupante_d()).ordinal();
            tipo =  mPost.getPostType();


            PostType type = list_type.get(tipo);

            post_seq_tipo.setText(""+type.getName());
            post_seq_edit.setText(" / "+altura);
            post_seq_effort.setText(" / "+esforco);
            post_seq_ocupantes.setText(" / "+ocupantes);
            post_seq_ocupanted.setText(" / "+ocupanted);


            //gridAltura.setSelection(PostHeight.parse(mPost.getHeight()).ordinal());
//            mHeight.setSelection(PostHeight.parse(mPost.getHeight()).ordinal());
      //      mEffort.setSelection(PostEffort.parse(mPost.getPostEffort()).ordinal());


          /*  ocupante_s.setSelection(OcupantesS.parse(mPost.getOcupante_s()).ordinal());
            ocupante_d.setSelection(OcupantesD.parse(mPost.getOcupante_d()).ordinal());*/

         //   mType.setSelection(mPost.getPostType());
        }



       /* view.findViewById(R.id.cancel_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Verifier.rollBack(mPost.getGeoCode());
                dismissAllowingStateLoss();
            }
        });*/

        /*view.findViewById(R.id.location_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapsPickLocationActivity.class);
                if (PostEditDialogFragment.mCreating) {
                    intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, PostEditDialogFragment.mUserLatLng);
                    intent.putExtra(MapsPickLocationActivity.METERS_RESTRICTION, 10f);
                } else {
                    intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, mPost.getLocation().toLatLng());
                }
                startActivityForResult(intent, PICKUP_LOCATION);
            }
        });*/

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count[0] > fim ) {

                                    //EquipmentUpdateUtils.setPontoAtualizacao(getContext(), mPost);
                                    Location l = mListener.getLastLocation();
                                    mPost.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis() / 1000));
                                    mPost.setUpdate(true);
                                    mPost.setData_finalizado(new Date());
                                    mPost.setClosed(true);

                                    final ProgressDialog alertDialog = new ProgressDialog(getActivity(), R.style.AlertDialogTheme);
                                    alertDialog.setTitle(getString(R.string.dialog_post_edit_saving_title, mPost.getGeoCode()));
                                    alertDialog.setMessage(getString(R.string.dialog_post_edit_saving_message));
                                    alertDialog.setCancelable(false);
                                    alertDialog.show();

                                    mPost.setObservations(ViewUtils.getTextViewValue(mObservations));
                                    mPost.setHeight(altura);

                                    /*mPost.setOcupante_s(OcupantesS.getOcupantes_s().get(ocupante_s.getSelectedItemPosition()).intValue());
                                    mPost.setOcupante_d(OcupantesD.getOcupantesd().get(ocupante_d.getSelectedItemPosition()).intValue());*/

                                    mPost.setOcupante_s(ocupantes);
                                    mPost.setOcupante_d(ocupanted);

                                    mPost.setPostEffort(esforco);
                                    mPost.setPostType(tipo);

                                    if(podeEquipament) {
                                        mPost.setEquipamento(equipamentSelect);
                                    }
                                    mPost.setRedePrimaria(redePrimariaSelect);

                                 /*   mPost.setPostEffort(PostEffort.getNames().get(mEffort.getSelectedItemPosition()).toString());
                                    mPost.setPostType(mType.getSelectedItemPosition());
                                    mPost.setCreateDate(new Date());*/

                                    /*List<String> imagesTags = new ArrayList<String>();
                                    int count = mLayoutImages.getChildCount();
                                    for(int i = 0; i<count; i++) {
                                        LinearLayout l = (LinearLayout) mLayoutImages.getChildAt(i);
                                        EditText e = (EditText) l.getChildAt(0);
                                        imagesTags.add(String.valueOf(e.getText()));
                                    }*/
                                    mPost.setPhotos(photosList);

                                    boolean workingOffline = FileUtils.serviceOrderFileExists(mPost.getOrderId());
                                    if (mCreating) {
                                        mPost.setClosed(true);
                                        mPost.setUpdate(true);
                                        mPost.setPostNumber(mListener.getNextPostNumber());
                                        LogUtils.log("highestPostNumber = " + mListener.getHighestPostNumber());

                                        if (workingOffline) {

                                            try {
                                           //     OfflineDownloadResponse download = FileUtils.retrieve(mPost.getOrderId());
                                                OfflineDownloadResponse download = null;
                                                if(DownloadOrderOffline.getResponse() == null){
                                                    DownloadOrderOffline.setResponse(FileUtils.retrieve(mPost.getOrderId()));
                                                    download = DownloadOrderOffline.getResponse();
                                                }
                                                else{
                                                    download = DownloadOrderOffline.getResponse();
                                                }

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
                                                    mListener.onPostChanged(mPost, true, null);

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

                                        } else {

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
                                                        mListener.onPostChanged(post, true, null);
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
                                                //OfflineDownloadResponse download = FileUtils.retrieve(mPost.getOrderId());
                                                OfflineDownloadResponse download = null;
                                                if(DownloadOrderOffline.getResponse() == null){
                                                    DownloadOrderOffline.setResponse(FileUtils.retrieve(mPost.getOrderId()));
                                                    download = DownloadOrderOffline.getResponse();
                                                }
                                                else{
                                                    download = DownloadOrderOffline.getResponse();
                                                }
                                                List<Post> posts = download.getPosts();

                                                List<PontoEntrega> pontoEntregasLines = new ArrayList<>();
                                                List<PontoEntrega> pontosNovos = new ArrayList<>();
                                                pontoEntregasLines = download.getPontoEntregaList();
                                                for (PontoEntrega pontoEntrega : pontoEntregasLines){
                                                    if(pontoEntrega.getPostId() == mPost.getId()){
                                                        pontosNovos.add(pontoEntrega);
                                                    }
                                                }

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
                                                    mListener.onPostChanged(mPost, false, pontosNovos);

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
                                                        dismissAllowingStateLoss();
                                                        mListener.onPostChanged(mPost, false, null);
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


                }else{
                    viewFlipper.showNext();
                    count[0]++;
                    if(count[0] == 3){
                        if(!podeEquipament){
                            viewFlipper.showNext();
                            count[0]++;
                        }
                    }
                    if(count[0] > fim){
                        next_button.setText("Salvar");
                        Drawable image = ContextCompat.getDrawable(getContext(),R.drawable.ic_check_black_24dp);
                        image.setBounds( 0, 0, 60, 60 );
                        next_button.setCompoundDrawables(null, null, image, null );
                    }else{

                    }
                }
            }
        });

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count[0] > 0) {
                    viewFlipper.showPrevious();
                    --count[0];
                    next_button.setText("Próximo");
                    Drawable image = ContextCompat.getDrawable(getContext(),R.drawable.ic_navigate_next_black_24dp);
                    image.setBounds( 0, 0, 60, 60 );
                    next_button.setCompoundDrawables(null, null, image, null );

                    if(count[0] == 3){
                        if(!podeEquipament){
                            viewFlipper.showPrevious();
                            --count[0];
                        }
                    }

                } else {
                    Verifier.rollBack(mPost.getGeoCode());
                    dismissAllowingStateLoss();
                }
            }
        });



     /*   view.findViewById(R.id.save_button).setOnClickListener(new View.OnClickListener() {
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

                mPost.setOcupante_s(OcupantesS.getOcupantes_s().get(ocupante_s.getSelectedItemPosition()).intValue());
                mPost.setOcupante_d(OcupantesD.getOcupantesd().get(ocupante_d.getSelectedItemPosition()).intValue());

                mPost.setPostEffort(PostEffort.getNames().get(mEffort.getSelectedItemPosition()).toString());
                mPost.setPostType(mType.getSelectedItemPosition());
                mPost.setCreateDate(new Date());

               // List<String> imagesTags = new ArrayList<String>();
                //int count = mLayoutImages.getChildCount();
                //for(int i = 0; i<count; i++) {
                  //  LinearLayout l = (LinearLayout) mLayoutImages.getChildAt(i);
                   // EditText e = (EditText) l.getChildAt(0);
                    //imagesTags.add(String.valueOf(e.getText()));
               // }

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
        });*/
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICKUP_LOCATION) {

            if (data != null && data.hasExtra(MapsPickLocationActivity.PICK_LOCATION)) {
                LatLng latLng = data.getParcelableExtra(MapsPickLocationActivity.PICK_LOCATION);
                LogUtils.log("onMapPickLocationActivityResult");
                mPost.setLocation(new PostLocation(latLng.latitude, latLng.longitude));
                //mLat.setText(String.valueOf(latLng.latitude));
                //mLon.setText(String.valueOf(latLng.longitude));
            } else {
                Toast.makeText(getActivity(),
                        R.string.dialog_post_edit_pickup_location_error,
                        Toast.LENGTH_LONG).show();
            }

        }
    }
}