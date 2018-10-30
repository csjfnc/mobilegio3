package com.visium.fieldservice.ui.maps;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telecom.TelecomManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
//import com.tooltip.Tooltip;
import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.memoria.DownloadOrderOffline;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.LatLonQuadraResponse;
import com.visium.fieldservice.api.visium.bean.response.LocalUsuarioResponse;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.controller.AfloramentoController;
import com.visium.fieldservice.controller.LightingController;
import com.visium.fieldservice.controller.LocalizacaoUsuario;
import com.visium.fieldservice.controller.PontoEntregaController;
import com.visium.fieldservice.controller.PostController;
import com.visium.fieldservice.controller.UserController;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.Anotacao;
import com.visium.fieldservice.entity.DemandaStrand;
import com.visium.fieldservice.entity.Lagradouro;
import com.visium.fieldservice.entity.LatLonQuadra;
import com.visium.fieldservice.entity.Lighting;
import com.visium.fieldservice.entity.LocalUsuario;
import com.visium.fieldservice.entity.PontoAtualizacao;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.PontoEntregaLocation;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.PostLocation;
import com.visium.fieldservice.entity.PostMedidoresPosicao;
import com.visium.fieldservice.entity.Quadra;
import com.visium.fieldservice.entity.ServiceOrderDetails;
import com.visium.fieldservice.entity.TipoDemanda;
import com.visium.fieldservice.entity.VaoPrimario;
import com.visium.fieldservice.entity.VaosPontoPoste;
import com.visium.fieldservice.entity.equipament.Afloramento;
import com.visium.fieldservice.entity.equipament.EquipmentType;
import com.visium.fieldservice.ui.common.EquipmentEditDialogListener;
import com.visium.fieldservice.ui.common.EquipmentListDialogListener;
import com.visium.fieldservice.ui.common.MapLocationListener;
import com.visium.fieldservice.ui.dialog.AfloramentoEditDialogFragment;
import com.visium.fieldservice.ui.dialog.AfloramentoListDialogFragment;
import com.visium.fieldservice.ui.dialog.AnotacaoActionDialogFrament;
import com.visium.fieldservice.ui.dialog.AnotacaoDialogFragment;
import com.visium.fieldservice.ui.dialog.AnotacaoDialogListener;
import com.visium.fieldservice.ui.dialog.DemandaStrandActionsDialogFragment;
import com.visium.fieldservice.ui.dialog.DemandaStrandEditDialogListener;
import com.visium.fieldservice.ui.dialog.DemandatActionsDialogListener;
import com.visium.fieldservice.ui.dialog.EditDemandaFragment;
import com.visium.fieldservice.ui.dialog.LightingEditDialogFragment;
import com.visium.fieldservice.ui.dialog.LightingEditDialogListener;
import com.visium.fieldservice.ui.dialog.LightingListDialogFragment;
import com.visium.fieldservice.ui.dialog.LightingListDialogListener;
import com.visium.fieldservice.ui.dialog.MedidorListDialogFragment;
import com.visium.fieldservice.ui.dialog.PhtotosFragment;
import com.visium.fieldservice.ui.dialog.PontoActionsDialogFragment;
import com.visium.fieldservice.ui.dialog.PontoEntregaEditDialogFragment;
import com.visium.fieldservice.ui.dialog.PontoEntregaEditDialogListener;
import com.visium.fieldservice.ui.dialog.PontoEntregaListDialogFragment;
import com.visium.fieldservice.ui.dialog.PontoEntregaMedidorEditDialogFragment;
import com.visium.fieldservice.ui.dialog.PostActionsDialogFragment;
import com.visium.fieldservice.ui.dialog.PostActionsDialogListener;
import com.visium.fieldservice.ui.dialog.PostCompletoEditFragment;
import com.visium.fieldservice.ui.dialog.PostEditDialogFragment;
import com.visium.fieldservice.ui.dialog.PostEditDialogListener;
import com.visium.fieldservice.ui.dialog.PostPontoEditDialogFragment;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.GeoUtils;
import com.visium.fieldservice.util.LogUtils;
import com.visium.fieldservice.verifier.Verifier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MapsPostsActivity extends MapsAccuracy
        implements PostActionsDialogListener, PostEditDialogListener, PontoEntregaEditDialogListener, DemandatActionsDialogListener,
        LightingListDialogListener, LightingEditDialogListener,
        EquipmentEditDialogListener, EquipmentListDialogListener, MapLocationListener, AnotacaoDialogListener, DemandaStrandEditDialogListener{


    public static final int RESULT_CAMERA = 575;
    private static final int TOAST_DURACAO = 4;
    public static final int PICKUP_LOCATION = 2;
    public static final int PICKUP_LOCATION_STRAND = 400;
    public static final int PONTO_ENTREGA_PICKUP_LOCATION = 300;
    public static final String SERVICE_ORDER_PONTES_ENTREGA = "SERVICE_ORDER_PONTES_ENTREGA";
    public static final String SERVICE_ORDER_POINTS = "SERVICE_ORDER_POINTS";
    public static final String SERVICE_ORDER_DETAILS = "SERVICE_ORDER_DETAILS";
    public static final String POST_LIST = "POST_LIST";
    public static final String VAO_PRIMARIO_LIST = "VAO_PRIMARIO_LIST";
    public static final String PONTO_ENTREGA = "PONTO_ENTREGA";
    private PostActionsDialogFragment padf;
    private PontoActionsDialogFragment padfPponto;
    private AnotacaoActionDialogFrament padfAnotacao;
    private DemandaStrandActionsDialogFragment padfStrand;
    private List<Quadra> mQuadras;
    private List<LatLng> mOrderPoints;
    private ServiceOrderDetails mOrderDetails;
    private List<Post> mPosts;
    private List<VaoPrimario> mVaosPrimarios;
    private List<PontoEntrega> mPontoEntregas;
    private List<VaosPontoPoste> mVaosPontoPostes;
    private List<DemandaStrand> mDemandaStrands;
    private Map<String, Post> postsMap = new HashMap<>();
    private Map<String, PontoEntrega> pontosMap = new HashMap<>();
    private Map<String, Anotacao> anotacaoMap = new HashMap<>();
    private Map<Long, Marker> markersMap = new HashMap<>();
    private Map<Long, Marker> markersMapPontoEntrega = new HashMap<>();
    private Map<Long, Marker> markersMapAnatocao = new HashMap<>();
    private Map<Long, Polyline> polylineMapEntreg = new HashMap<>();
    private Map<Long, Polyline> polylineMapPoste = new HashMap<>();
    private Map<Long, Polyline> polylineStrandId = new HashMap<>();
    private Map<Polyline, DemandaStrand> polylineStrandMapa = new HashMap<>();
    private View SnakemapFragment;
    private int countStrand = 0;
    //private Map<Long, Polyline> polylineMapStrand = new HashMap<>();
    //private Map<String, DemandaStrand> demandaStrandMap = new HashMap<>();

    private LatLng creatingPostLatLng, creatingPostLatLng2;
    private List<LatLng> points;
    private LightingListDialogFragment lightingListDialogFragment;
    private AfloramentoListDialogFragment afloramentoListDialogFragment;
    private List<Lighting> lightingList;
    private List<Afloramento> afloramentoList;
    private List<PontoEntrega> pontoEntregaList;
    private PontoEntregaListDialogFragment pontoEntregaListDialogFragment;
    private HashMap<Polyline, Integer> polylineList;
    private FloatingActionButton emenda, anotacao, strand, btnfoto;
    private ImageButton panico;
    private String tipo, editarStrand;
    private List<Address> addresses;
    private boolean digitar = false;
    private Lagradouro lagradouro;
    private boolean modoLagradouro = false;
    private List<Anotacao> anotacaoList;
    private DemandaStrand mDemandaStrand;
    private Polyline mPolyline;
    private boolean posteTime, demandaTime, strandTime, anotacaoTime, demandaPosteTime, postTimeEdit, demandaTimeEdit, fotoTime;
    private RelativeLayout layoutMapsPostsActivity;
    private Post posteTimeObjeto, postTimeEditObj, postTimeStrand, postTimeFoto;
    private DemandaStrand demandaStrandTime;
    private Marker markerStrand1;
    private PontoEntrega pontoEntregaObj;
    private String caminhoFoto;
    private Button btn_cancelar_opcao;
    private Marker markerPoste;
    private TextView textoAux;
    HashMap<Polyline, PontoEntrega> polylinePontoEntregaHashMap;
    private int highestPostNumber = 0;
    private Location lastLocaltion;
    private boolean taEmPanico;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    //private Tooltip tooltip;

    public int getHighestPostNumber() {
        return highestPostNumber;
    }

    @Override
    public int getNextPostNumber() {
        return ++highestPostNumber;
    }


    @Override
    @SuppressWarnings("ResourceType")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_posts);

        callConnection();

        posteTime = true;
        demandaTime = false;
        strandTime = false;
        anotacaoTime = false;
        demandaPosteTime = false;
        postTimeEdit = false;
        demandaTimeEdit = false;
        fotoTime = false;

        SnakemapFragment = findViewById(R.id.map);
        textoAux = (TextView) findViewById(R.id.textoAux);

        polylinePontoEntregaHashMap = new HashMap<>();

        final Intent intent = getIntent();
        mOrderPoints = (List<LatLng>) intent.getSerializableExtra(SERVICE_ORDER_POINTS);
        mOrderDetails = (ServiceOrderDetails) intent.getSerializableExtra(SERVICE_ORDER_DETAILS);

        taEmPanico = true;
        try {
            OfflineDownloadResponse downloaded = null;
            if (DownloadOrderOffline.getResponse() == null) {
                DownloadOrderOffline.setResponse(FileUtils.retrieve(mOrderDetails.getId()));
                downloaded = DownloadOrderOffline.getResponse();
            } else {
                downloaded = DownloadOrderOffline.getResponse();
                if (mOrderDetails.getId() != downloaded.getServiceOrderDetails().getId()) {
                    DownloadOrderOffline.setResponse(FileUtils.retrieve(mOrderDetails.getId()));
                    downloaded = DownloadOrderOffline.getResponse();
                }
            }
            //OfflineDownloadResponse downloaded = FileUtils.retrieve(mOrderDetails.getId());
            mPosts = downloaded.getPosts();
            mVaosPrimarios = downloaded.getVaoPrimarioList();
            mPontoEntregas = downloaded.getPontoEntregaList();
            mVaosPontoPostes = downloaded.getVaosPontoPosteList();
            mQuadras = downloaded.getQuadraList();
            mDemandaStrands = downloaded.getDemandaStrandList();
            anotacaoList = downloaded.getAnotacaoList();

        } catch (IOException e) {
            e.printStackTrace();
        }

//        try {
//            OfflineDownloadResponse downloaded = FileUtils.retrieve(mOrderDetails.getId());
//            mPosts = downloaded.getPosts();
//            mVaosPrimarios = downloaded.getVaoPrimarioList();
//            mPontoEntregas = downloaded.getPontoEntregaList();
//            mVaosPontoPostes = downloaded.getVaosPontoPosteList();
//            mQuadras = downloaded.getQuadraList();
//            mDemandaStrands = downloaded.getDemandaStrandList();
//            anotacaoList = downloaded.getAnotacaoList();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        /*mPosts = (List<Post>) intent.getSerializableExtra(POST_LIST);
        mVaosPrimarios = (List<VaoPrimario>) intent.getSerializableExtra(VAO_PRIMARIO_LIST);
        mPontoEntregas = (List<PontoEntrega>) intent.getSerializableExtra(PONTO_ENTREGA);
           */
        setupToolbar(R.drawable.logo, mOrderDetails.getNumber());
        forceStatusBarColor();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btn_cancelar_opcao = (Button) findViewById(R.id.btn_cancelar_opcao);
        btn_cancelar_opcao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                demandaTime = false;
                strandTime = false;
                anotacaoTime = false;
                postTimeEdit = false;
                demandaTimeEdit = false;
                demandaPosteTime = false;
                fotoTime = false;
                posteTime = true;
                btn_cancelar_opcao.setVisibility(View.GONE);

                if (markerPoste != null) {
                    markerPoste.setIcon(getPin(posteTimeObjeto));
                    markerPoste = null;
                }
                //setupToolbar(R.drawable.logo, mOrderDetails.getNumber() + "- Cadastro de Poste");
                textoAux.setVisibility(View.GONE);

            }
        });

        emenda = (FloatingActionButton) findViewById(R.id.emenda);
        anotacao = (FloatingActionButton) findViewById(R.id.anotacao);
        strand = (FloatingActionButton) findViewById(R.id.strand);
        btnfoto = (FloatingActionButton) findViewById(R.id.btnfoto);
        panico = (ImageButton) findViewById(R.id.panico);

      //  tooltip = new Tooltip.Builder(panico, R.style.simpletooltip_default).setText("Ajuda Ativado!").build();

//        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
//        floatingActionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                posteTime = true;
//                Snackbar.make(SnakemapFragment, "Escolha um local", Snackbar.LENGTH_LONG).show();
//                demandaTime = false;
//                strandTime = false;
//                anotacaoTime = false;
//                postTimeEdit = false;
//                demandaTimeEdit = false;
//                demandaPosteTime = false;
//                fotoTime = false;
//                btn_cancelar_opcao.setVisibility(View.VISIBLE);
//
//
//               /* if(false){
//                    if (mLastLocation == null) {
//                        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                        Criteria criteria = new Criteria();
//                        mLastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
//                    }
//
//                    Intent intent = new Intent(MapsPostsActivity.this,
//                            MapsPickLocationActivity.class);
//                    //intent.putExtra(MapsPostsActivity.POST_LIST, new ArrayList<>(mPosts));
//
//                    intent.putExtra("orderId", mOrderDetails.getId());
//                    intent.putExtra("tipo", "poste");
//
//                    //       intent.putExtra(MapsPostsActivity.POST_LIST, (Serializable) posts);
//                    intent.putExtra(MapsPostsActivity.SERVICE_ORDER_POINTS, new ArrayList<>(mOrderPoints));
//                    //intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
//                    //intent.putExtra(MapsPickLocationActivity.METERS_RESTRICTION, 10f);
//                    startActivityForResult(intent, PICKUP_LOCATION);
//                }*/
//            }
//        });

        emenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                demandaPosteTime = true;
                //Snackbar.make(SnakemapFragment, "Escolha um poste", Snackbar.LENGTH_LONG).show();
                posteTime = false;
                demandaTime = true;
                strandTime = false;
                anotacaoTime = false;
                postTimeEdit = false;
                demandaTimeEdit = false;
                fotoTime = false;
                btn_cancelar_opcao.setVisibility(View.VISIBLE);
                textoAux.setVisibility(View.VISIBLE);
                //setupToolbar(R.drawable.logo, mOrderDetails.getNumber() +"- Cadastro de Demanda");
                textoAux.setText("Escolha o poste de origem");
                /*if(tooltip.isShowing()) {
                    tooltip.dismiss();
                }*/

               /* if (mLastLocation == null) {
                    LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    Criteria criteria = new Criteria();
                    mLastLocation = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
                }

                if (modoLagradouro) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsPostsActivity.this);
                    View view1 = LayoutInflater.from(MapsPostsActivity.this).inflate(R.layout.layout_address_location, null);
                    builder.setView(view1);
                    Button btn_cancelar = (Button) view1.findViewById(R.id.btn_cancelar);
                    TextView addressLong = (TextView) view1.findViewById(R.id.addressLong);
                    Button address_digitar = (Button) view1.findViewById(R.id.address_digitar);
                    ProgressBar progress = (ProgressBar) view1.findViewById(R.id.progress);
                    final AlertDialog alertDialog = builder.create();
                    Geocoder geocoder = new Geocoder(MapsPostsActivity.this, Locale.getDefault());
                    addresses = null;
                    try {
                        addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);
                        addressLong.setText(addresses.get(0).getThoroughfare() + ", " + addresses.get(0).getSubLocality() + ", "
                                + addresses.get(0).getSubAdminArea() + ", " + addresses.get(0).getPostalCode());
                        progress.setVisibility(View.GONE);
                        addressLong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                alertDialog.dismiss();
                                Intent intent = new Intent(MapsPostsActivity.this,
                                        MapsPickLocationActivity.class);
                                //intent.putExtra(MapsPostsActivity.POST_LIST, new ArrayList<>(mPosts));

                                intent.putExtra("orderId", mOrderDetails.getId());
                                intent.putExtra("tipo", "emenda");

                                //       intent.putExtra(MapsPostsActivity.POST_LIST, (Serializable) posts);
                                intent.putExtra(MapsPostsActivity.SERVICE_ORDER_POINTS, new ArrayList<>(mOrderPoints));
                                //intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                                //intent.putExtra(MapsPickLocationActivity.METERS_RESTRICTION, 10f);
                                startActivityForResult(intent, PICKUP_LOCATION);
                            }
                        });

                    } catch (IOException e) {
                        addressLong.setText("Não foi possivel Localizar o endereço do seu local atual");
                        progress.setVisibility(View.GONE);
                        e.printStackTrace();
                    }

                    address_digitar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AlertDialog.Builder builder1CadastroAddress = new AlertDialog.Builder(MapsPostsActivity.this);
                            View view2 = LayoutInflater.from(MapsPostsActivity.this).inflate(R.layout.layout_digitar_address, null);
                            digitar = true;
                            Button btn_salvar_address = (Button) view2.findViewById(R.id.btn_salvar_address);
                            final EditText rua = (EditText) view2.findViewById(R.id.rua);
                            final EditText complemento = (EditText) view2.findViewById(R.id.complemento);
                            final EditText tipoRua = (EditText) view2.findViewById(R.id.tipoRua);
                            final EditText nomeBairro = (EditText) view2.findViewById(R.id.nomeBairro);
                            final EditText uf = (EditText) view2.findViewById(R.id.uf);
                            final EditText municipio = (EditText) view2.findViewById(R.id.municipio);
                            builder1CadastroAddress.setView(view2);
                            final AlertDialog cadAddress = builder1CadastroAddress.create();

                            btn_salvar_address.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    lagradouro = new Lagradouro();
                                    lagradouro.setNomeLog(rua.getText().toString());
                                    lagradouro.setCompleLog(complemento.getText().toString());
                                    lagradouro.setAbrevTipo(tipoRua.getText().toString());
                                    lagradouro.setExtensoBairro(nomeBairro.getText().toString());
                                    lagradouro.setUf(uf.getText().toString());
                                    lagradouro.setMunicipo(municipio.getText().toString());

                                    Intent intent = new Intent(MapsPostsActivity.this,
                                            MapsPickLocationActivity.class);
                                    //intent.putExtra(MapsPostsActivity.POST_LIST, new ArrayList<>(mPosts));

                                    intent.putExtra("orderId", mOrderDetails.getId());
                                    intent.putExtra("tipo", "emenda");

                                    //       intent.putExtra(MapsPostsActivity.POST_LIST, (Serializable) posts);
                                    intent.putExtra(MapsPostsActivity.SERVICE_ORDER_POINTS, new ArrayList<>(mOrderPoints));
                                    //intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                                    //intent.putExtra(MapsPickLocationActivity.METERS_RESTRICTION, 10f);
                                    startActivityForResult(intent, PICKUP_LOCATION);
                                    cadAddress.dismiss();
                                }
                            });

                            cadAddress.show();
                        }
                    });


                    btn_cancelar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertDialog.dismiss();
                        }
                    });


                    alertDialog.show();
                } else {
                    Intent intent = new Intent(MapsPostsActivity.this,
                            MapsPickLocationActivity.class);
                    //intent.putExtra(MapsPostsActivity.POST_LIST, new ArrayList<>(mPosts));

                    intent.putExtra("orderId", mOrderDetails.getId());
                    intent.putExtra("tipo", "emenda");

                    //       intent.putExtra(MapsPostsActivity.POST_LIST, (Serializable) posts);
                    intent.putExtra(MapsPostsActivity.SERVICE_ORDER_POINTS, new ArrayList<>(mOrderPoints));
                    //intent.putExtra(MapsPickLocationActivity.PICK_LOCATION, new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
                    //intent.putExtra(MapsPickLocationActivity.METERS_RESTRICTION, 10f);
                    startActivityForResult(intent, PICKUP_LOCATION);
                }
*/
            }
        });

        anotacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent anotacao = new Intent(MapsPostsActivity.this, MapsPickLocationActivity.class);
                anotacao.putExtra("orderId", mOrderDetails.getId());
                anotacao.putExtra("tipo", "anotacao");

                anotacao.putExtra(MapsPostsActivity.SERVICE_ORDER_POINTS, new ArrayList<>(mOrderPoints));
                startActivityForResult(anotacao, PICKUP_LOCATION);*/

                anotacaoTime = true;
                // Snackbar.make(SnakemapFragment, "Escolha o local", Snackbar.LENGTH_LONG).show();
                posteTime = false;
                demandaTime = false;
                strandTime = false;
                demandaPosteTime = false;
                postTimeEdit = false;
                demandaTimeEdit = false;
                fotoTime = false;
                btn_cancelar_opcao.setVisibility(View.VISIBLE);
                textoAux.setVisibility(View.VISIBLE);
                /// setupToolbar(R.drawable.logo, mOrderDetails.getNumber() +"- Cadastro de Anotação");
                textoAux.setText("Escolha o local para anotação");

            }
        });

        strand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                strandTime = true;
                //  Snackbar.make(SnakemapFragment, "Escolha o inicio", Snackbar.LENGTH_LONG).show();
                demandaStrandTime = new DemandaStrand();

                posteTime = false;
                demandaTime = false;
                demandaPosteTime = false;
                postTimeEdit = false;
                demandaTimeEdit = false;
                anotacaoTime = false;
                fotoTime = false;
                btn_cancelar_opcao.setVisibility(View.VISIBLE);
                textoAux.setVisibility(View.VISIBLE);
                //setupToolbar(R.drawable.logo, mOrderDetails.getNumber() +"- Cadastro de Strand");
                textoAux.setText("Escolha o local para Strand");
               /* Intent strand = new Intent(MapsPostsActivity.this, MapsStrandPickLocationActivity.class);
                strand.putExtra("orderId", mOrderDetails.getId());
                strand.putExtra("tipo", "strand");

                strand.putExtra(MapsPostsActivity.SERVICE_ORDER_POINTS, new ArrayList<>(mOrderPoints));
                startActivityForResult(strand, PICKUP_LOCATION_STRAND);*/
            }
        });

        btnfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fotoTime = true;
                strandTime = false;
                posteTime = false;
                demandaTime = false;
                demandaPosteTime = false;
                postTimeEdit = false;
                demandaTimeEdit = false;
                anotacaoTime = false;
                // Snackbar.make(SnakemapFragment, "Escolha o poste para tirar foto", Snackbar.LENGTH_LONG).show();
                btn_cancelar_opcao.setVisibility(View.VISIBLE);
                textoAux.setVisibility(View.VISIBLE);
                //setupToolbar(R.drawable.logo, mOrderDetails.getNumber() +"- Tirar Foto");
                textoAux.setText("Escolha o poste para a foto");
            }
        });
        panico.setMinimumWidth(90);




        panico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastLocaltion != null) {

                    if(LocalUsuario.getPanico() == 0){
                        if(LocalUsuario.getIdUser() != null){
                            //   Toast.makeText(this, "Lat: "+ location.getLatitude()+" Lon: "+ location.getLongitude(), Toast.LENGTH_SHORT).show();
                            LocalUsuario.setPanico(1);
                            LocalUsuario.setMensagem("");

                            UserController.get().LocalAtualPanico(LocalUsuario.getIdUser(), lastLocaltion.getLatitude(), lastLocaltion.getLongitude(), LocalUsuario.getPanico(), LocalUsuario.getMensagem(), new VisiumApiCallback<LocalUsuario>() {
                                @Override
                                public void callback(LocalUsuario localUsuario, ErrorResponse e) {
                                    //  Toast.makeText(MapsServiceOrderActivity.this, "olaa", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        //taEmPanico = false;
                        //Toast.makeText(MapsPostsActivity.this, "Pedido de ajuda enviado!", Toast.LENGTH_LONG).show();
                        panico.setBackgroundColor(Color.parseColor("#efc300"));
                     //   tooltip.show();

                        SharedPreferences.Editor preferences = getSharedPreferences("LocalUsuario", Context.MODE_PRIVATE).edit();
                        preferences.putInt("panico", 1);
                        preferences.commit();


                    }else{
                        if(LocalUsuario.getIdUser() != null){
                            //   Toast.makeText(this, "Lat: "+ location.getLatitude()+" Lon: "+ location.getLongitude(), Toast.LENGTH_SHORT).show();
                            LocalUsuario.setPanico(0);
                            LocalUsuario.setMensagem("");

                            UserController.get().LocalAtualPanico(LocalUsuario.getIdUser(), lastLocaltion.getLatitude(), lastLocaltion.getLongitude(), LocalUsuario.getPanico(), LocalUsuario.getMensagem(), new VisiumApiCallback<LocalUsuario>() {
                                @Override
                                public void callback(LocalUsuario localUsuario, ErrorResponse e) {
                                    //  Toast.makeText(MapsServiceOrderActivity.this, "olaa", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        //taEmPanico = true;
                        panico.setBackgroundColor(Color.WHITE);
                       // tooltip.dismiss();
                        SharedPreferences.Editor preferences = getSharedPreferences("LocalUsuario", Context.MODE_PRIVATE).edit();
                        preferences.putInt("panico", 1);
                        preferences.commit();
                    }
                }
            }
        });
    }

   /* public void setPosteTime(){
        if(posteTime){
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            posteTime = false;
            setupToolbar(R.drawable.logo, mOrderDetails.getNumber());
        }else{
            posteTime = true;
            floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff9999")));
            setupToolbar(R.drawable.logo, mOrderDetails.getNumber()+" Escolha o Local do Poste");
            demandaTime = false;
            strandTime = false;
            anotacaoTime = false;
        }
    }
    public void setDemandaTime(){
        if(posteTime){
            emenda.setBackgroundTintList(ColorStateList.valueOf(Color.WHITE));
            demandaTime = false;
            setupToolbar(R.drawable.logo, mOrderDetails.getNumber());
        }else{
            demandaTime = true;
            emenda.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff9999")));
            setupToolbar(R.drawable.logo, mOrderDetails.getNumber()+" Escolha o Local da Demanda");
            posteTime = false;
            strandTime = false;
            anotacaoTime = false;
        }
    }*/




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == RESULT_CAMERA) {

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Deseja Tirar outra Foto?");
                builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tirarFotoPoste(postTimeFoto);
                        btn_cancelar_opcao.setVisibility(View.GONE);
                    }
                }).setNegativeButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        btn_cancelar_opcao.setVisibility(View.GONE);
                    }
                });

                builder.show();
            }
        }

//        if (data != null) {
//            Bundle bundle = data.getExtras();
//            tipo = bundle.getString("tipo");
//            //  mDemandaStrand = (DemandaStrand) bundle.getSerializable("mDemandaStrand");
//        }
//        if (requestCode == PICKUP_LOCATION) {
//            if (data != null && data.hasExtra(MapsPickLocationActivity.PICK_LOCATION)) {
//                creatingPostLatLng = data.getParcelableExtra(MapsPickLocationActivity.PICK_LOCATION);
//            } else {
//                Toast.makeText(MapsPostsActivity.this,
//                        R.string.maps_posts_new_location_error,
//                        Toast.LENGTH_LONG).show();
//            }
//        } else if (requestCode == PONTO_ENTREGA_PICKUP_LOCATION) {
//            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//                fragment.onActivityResult(requestCode, resultCode, data);
//            }
//        } else if (requestCode == PICKUP_LOCATION_STRAND) {
//            if (data != null) {
//                creatingPostLatLng = data.getParcelableExtra(MapsPickLocationActivity.PICK_LOCATION);
//                creatingPostLatLng2 = data.getParcelableExtra(MapsPickLocationActivity.PICK_LOCATIONDOIS);
//            } else {
//                Toast.makeText(MapsPostsActivity.this,
//                        "Falha ao obter localização",
//                        Toast.LENGTH_LONG).show();
//            }
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (creatingPostLatLng != null) {

            /*if (tipo.equals("poste")) {
                Post post = new Post();
                post.setOrderId(mOrderDetails.getId());
                post.setLocation(new PostLocation(creatingPostLatLng.latitude,
                        creatingPostLatLng.longitude));

                PostEditDialogFragment.newInstance(MapsPostsActivity.this, this, post,
                        new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                        .show(getSupportFragmentManager(), "postEdit");
            }*/
//            if (tipo.equals("emenda")) {
//                PontoEntrega pontoEntrega = new PontoEntrega();
//                pontoEntrega.setOrderId(mOrderDetails.getId());
//               /* pontoEntrega.setLocation(new PontoEntregaLocation(creatingPostLatLng.latitude,
//                        creatingPostLatLng.longitude));*/
//                pontoEntrega.setX(creatingPostLatLng.latitude);
//                pontoEntrega.setY(creatingPostLatLng.longitude);
//
//                if (modoLagradouro) {
//                    if (digitar) {
//                        PostPontoEditDialogFragment.newInstance(MapsPostsActivity.this, this, pontoEntrega,
//                                new LatLng(creatingPostLatLng.latitude, creatingPostLatLng.longitude), lagradouro)
//                                .show(getSupportFragmentManager(), "PontoEntregaEdit");
//                    } else {
//                        Lagradouro lagradouro1 = new Lagradouro();
//
//                        Address address = addresses.get(0);
//                        lagradouro1.setNomeLog(address.getThoroughfare());
//                        lagradouro1.setExtensoBairro(address.getSubLocality());
//                        lagradouro1.setCep(address.getPostalCode());
//                        lagradouro1.setUf(address.getAdminArea());
//                        lagradouro1.setMunicipo(address.getSubAdminArea());
//
//                        PostPontoEditDialogFragment.newInstance(MapsPostsActivity.this, this, pontoEntrega,
//                                new LatLng(creatingPostLatLng.latitude, creatingPostLatLng.longitude), lagradouro1)
//                                .show(getSupportFragmentManager(), "PontoEntregaEdit");
//
//                    }
//                } else {
//                    PostPontoEditDialogFragment.newInstance(MapsPostsActivity.this, this, pontoEntrega,
//                            new LatLng(creatingPostLatLng.latitude, creatingPostLatLng.longitude), new Lagradouro())
//                            .show(getSupportFragmentManager(), "PontoEntregaEdit");
//                }
//            }

          /*  if (tipo.equals("anotacao")) {
                Anotacao anotacao = new Anotacao();
                anotacao.setOrderId(mOrderDetails.getId());
                anotacao.setX(creatingPostLatLng.latitude);
                anotacao.setY(creatingPostLatLng.longitude);

                AnotacaoDialogFragment.newInstance(MapsPostsActivity.this, this, anotacao,
                        new LatLng(creatingPostLatLng.latitude, creatingPostLatLng.longitude))
                        .show(getSupportFragmentManager(), "PontoEntregaEdit");

            }*/
            if (tipo.equals("strand")) {
               /* if (creatingPostLatLng != null && creatingPostLatLng2 != null) {
                    if (mDemandaStrand != null) {
                        //mDemandaStrand.setOrderId(mOrderDetails.getId());
                        mDemandaStrand.setX1(creatingPostLatLng.latitude);
                        mDemandaStrand.setY1(creatingPostLatLng.longitude);
                        mDemandaStrand.setX2(creatingPostLatLng2.latitude);
                        mDemandaStrand.setY2(creatingPostLatLng2.longitude);
                        criaStrand(mDemandaStrand, false);
                    } else {
                        DemandaStrand demandaStrand = new DemandaStrand();
                        demandaStrand.setOrderId(mOrderDetails.getId());
                        demandaStrand.setX1(creatingPostLatLng.latitude);
                        demandaStrand.setY1(creatingPostLatLng.longitude);
                        demandaStrand.setX2(creatingPostLatLng2.latitude);
                        demandaStrand.setY2(creatingPostLatLng2.longitude);
                        criaStrand(demandaStrand, true);
                    }

                }*/


            }
            creatingPostLatLng = null;
            creatingPostLatLng2 = null;
        }
    }

    private void criaStrand(DemandaStrand demandaStrand, boolean mPode) {
        if (mPode) {
            //mPontoEntrega.setClosed(true);
            //mPontoEntrega.setUpdate(true);
            //mPontoEntrega.setPostNumber(mListener.getNextPostNumber());
            // LogUtils.log("highestPostNumber = " + mListener.getHighestPostNumber());
            //mPontoEntrega.setLagradouro(lagradouro);

            try {
                //  OfflineDownloadResponse download = FileUtils.retrieve(demandaStrand.getOrderId());

                OfflineDownloadResponse download = null;
                if (DownloadOrderOffline.getResponse() == null) {
                    DownloadOrderOffline.setResponse(FileUtils.retrieve(demandaStrand.getOrderId()));
                    download = DownloadOrderOffline.getResponse();
                } else {
                    download = DownloadOrderOffline.getResponse();
                }

                List<DemandaStrand> mDemandaStrands = download.getDemandaStrandList();

                demandaStrand.setId(-1 * System.currentTimeMillis());
                mDemandaStrands.add(demandaStrand);

                download.setDemandaStrandList(mDemandaStrands);

                if (FileUtils.saveOfflineDownload(download)) {

                    Toast.makeText(MapsPostsActivity.this,
                            R.string.dialog_post_edit_saving_success,
                            Toast.LENGTH_LONG).show();
                    onDemandaStradChanged(demandaStrand, true, null);
                    //mListener.onPontoEntegasChanged(mPontoEntrega, true, mPost, deletarPlyline);

                } else {
                    Toast.makeText(MapsPostsActivity.this, "Não foi possivel salvar o Strand",
                            Toast.LENGTH_LONG).show();
                }

            } catch (IOException e) {
                Toast.makeText(MapsPostsActivity.this, "Não foi possivel salvar o Strand",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            try {
                //OfflineDownloadResponse download = FileUtils.retrieve(demandaStrand.getOrderId());
                OfflineDownloadResponse download = null;
                if (DownloadOrderOffline.getResponse() == null) {
                    DownloadOrderOffline.setResponse(FileUtils.retrieve(demandaStrand.getOrderId()));
                    download = DownloadOrderOffline.getResponse();
                } else {
                    download = DownloadOrderOffline.getResponse();
                }
                List<DemandaStrand> demandaStrandList = download.getDemandaStrandList();
                Iterator<DemandaStrand> iterator = demandaStrandList.iterator();

                while (iterator.hasNext()) {
                    DemandaStrand strand = iterator.next();
                    if (strand.getId() == demandaStrand.getId()) {
                        iterator.remove();
                        break;
                    }
                }
                demandaStrandList.add(demandaStrand);
                download.setDemandaStrandList(demandaStrandList);

                if (FileUtils.saveOfflineDownload(download)) {

                    Toast.makeText(this,
                            "Editado com Sucesso!",
                            Toast.LENGTH_LONG).show();
                    onDemandaStradChanged(demandaStrand, false, mPolyline);

                } else {
                    Toast.makeText(this, "Erro ao Editar!",
                            Toast.LENGTH_LONG).show();
                }
            } catch (IOException e) {
                LogUtils.error(this, e);
                Toast.makeText(this, "Erro ao Editar!",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (points != null && !points.isEmpty()) {
                    mMap.animateCamera(CameraUpdateFactory
                            .newLatLngBounds(GeoUtils.createLatLngBounds(points), 50));
                }
                return true;
            default:
                //NO-OP
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        super.onMapReady(googleMap);
        defineLocation();
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View view = getLayoutInflater().inflate(R.layout.info_window_layout, null);
                ((TextView) view.findViewById(R.id.title))
                        .setText(marker.getTitle());

                return view;
            }
        });

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {

                if (posteTime) {
                    // Toast.makeText(getApplicationContext(), latLng.latitude+","+latLng.longitude, Toast.LENGTH_LONG).show();
                    Post post = new Post();
                    post.setOrderId(mOrderDetails.getId());
                    post.setLocation(new PostLocation(latLng.latitude,
                            latLng.longitude));

                    if (mLastLocation != null) {
                        PostEditDialogFragment.newInstance(MapsPostsActivity.this, MapsPostsActivity.this, post,
                                new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                                .show(getSupportFragmentManager(), "postEdit");
                    }
                }
                if (demandaTime) {
                    PontoEntrega pontoEntrega = new PontoEntrega();
                    pontoEntrega.setOrderId(mOrderDetails.getId());
               /* pontoEntrega.setLocation(new PontoEntregaLocation(creatingPostLatLng.latitude,
                        creatingPostLatLng.longitude));*/
                    pontoEntrega.setX(latLng.latitude);
                    pontoEntrega.setY(latLng.longitude);

                    PostPontoEditDialogFragment.newInstance(MapsPostsActivity.this, MapsPostsActivity.this, pontoEntrega,
                            new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()), new Lagradouro(), posteTimeObjeto)
                            .show(getSupportFragmentManager(), "PontoEntregaEdit");

//                    if (modoLagradouro) {
//                        if (digitar) {
//                            PostPontoEditDialogFragment.newInstance(MapsPostsActivity.this, this, pontoEntrega,
//                                    new LatLng(creatingPostLatLng.latitude, creatingPostLatLng.longitude), lagradouro)
//                                    .show(getSupportFragmentManager(), "PontoEntregaEdit");
//                        } else {
//                            Lagradouro lagradouro1 = new Lagradouro();
//
//                            Address address = addresses.get(0);
//                            lagradouro1.setNomeLog(address.getThoroughfare());
//                            lagradouro1.setExtensoBairro(address.getSubLocality());
//                            lagradouro1.setCep(address.getPostalCode());
//                            lagradouro1.setUf(address.getAdminArea());
//                            lagradouro1.setMunicipo(address.getSubAdminArea());
//
//                            PostPontoEditDialogFragment.newInstance(MapsPostsActivity.this, this, pontoEntrega,
//                                    new LatLng(creatingPostLatLng.latitude, creatingPostLatLng.longitude), lagradouro1)
//                                    .show(getSupportFragmentManager(), "PontoEntregaEdit");
//
//                        }
//                    } else {

                    //  }

                }
                if (anotacaoTime) {
                    Anotacao anotacao = new Anotacao();
                    anotacao.setOrderId(mOrderDetails.getId());
                    anotacao.setX(latLng.latitude);
                    anotacao.setY(latLng.longitude);

                    AnotacaoDialogFragment.newInstance(MapsPostsActivity.this, MapsPostsActivity.this, anotacao,
                            new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                            .show(getSupportFragmentManager(), "PontoEntregaEdit");
                }
                if (strandTime) {
                    countStrand++;
                    if (countStrand == 1) {
                        demandaStrandTime.setX1(latLng.latitude);
                        demandaStrandTime.setY1(latLng.longitude);
                        markerStrand1 = mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin1))
                                .position(latLng));
                        // Snackbar.make(SnakemapFragment, "Escolha O Fim", Snackbar.LENGTH_LONG).show();
                        textoAux.setText("Escolha O Fim");
                        strandTime = true;
                        btn_cancelar_opcao.setVisibility(View.GONE);

                    }
                    if (countStrand == 2) {
                        demandaStrandTime.setX2(latLng.latitude);
                        demandaStrandTime.setY2(latLng.longitude);
                        demandaStrandTime.setOrderId(mOrderDetails.getId());
                        criaStrand(demandaStrandTime, true);
                        markerStrand1.remove();
                        btn_cancelar_opcao.setVisibility(View.VISIBLE);
                        countStrand = 0;
                    }
                }

                if (postTimeEdit) {
                    PostLocation postLocation = new PostLocation();
                    postLocation.setLat(latLng.latitude);
                    postLocation.setLon(latLng.longitude);
                    postTimeEditObj.setLocation(postLocation);
                    postTimeEditObj.setUpdate(true);
                    /*PostEditDialogFragment.newInstance(MapsPostsActivity.this, MapsPostsActivity.this, postTimeEditObj)
                            .show(getSupportFragmentManager(), "postEdit");*/

                    try {
                        //OfflineDownloadResponse download = FileUtils.retrieve(mPost.getOrderId());
                        OfflineDownloadResponse download = null;
                        if (DownloadOrderOffline.getResponse() == null) {
                            DownloadOrderOffline.setResponse(FileUtils.retrieve(postTimeEditObj.getOrderId()));
                            download = DownloadOrderOffline.getResponse();
                        } else {
                            download = DownloadOrderOffline.getResponse();
                        }
                        List<Post> posts = download.getPosts();

                        List<PontoEntrega> pontoEntregasLines = new ArrayList<>();
                        List<PontoEntrega> pontosNovos = new ArrayList<>();
                        pontoEntregasLines = download.getPontoEntregaList();

                        for (PontoEntrega pontoEntrega : pontoEntregasLines) {
                            if (pontoEntrega.getPostId() == postTimeEditObj.getId()) {
                                pontosNovos.add(pontoEntrega);
                            }
                        }

                        Iterator<Post> iterator = posts.iterator();

                        while (iterator.hasNext()) {
                            Post post = iterator.next();
                            if (post.getId() == postTimeEditObj.getId()) {
                                iterator.remove();
                                break;
                            }
                        }

                        posts.add(postTimeEditObj);
                        download.setPostList(posts);

                        if (FileUtils.saveOfflineDownload(download)) {
                            Verifier.addPostCount(postTimeEditObj.getGeoCode());
                            Toast.makeText(MapsPostsActivity.this,
                                    R.string.dialog_post_edit_saving_success,
                                    Toast.LENGTH_LONG).show();
                            //dismissAllowingStateLoss();
                            onPostChanged(postTimeEditObj, false, pontosNovos);

                        } else {

                            Toast.makeText(MapsPostsActivity.this,
                                    getString(R.string.dialog_post_edit_saving_error,
                                            postTimeEditObj.getId()),
                                    Toast.LENGTH_LONG).show();
                        }


                    } catch (IOException e) {
                        LogUtils.error(this, e);


                        Toast.makeText(MapsPostsActivity.this,
                                getString(R.string.dialog_post_edit_saving_error,
                                        postTimeEditObj.getId()),
                                Toast.LENGTH_LONG).show();
                    }

                    postTimeEdit = false;
                    posteTime = true;
                    //setupToolbar(R.drawable.logo, mOrderDetails.getNumber() + "- Cadastro de Poste");
                    markerPoste = null;
                    textoAux.setVisibility(View.GONE);
                }
                if (demandaTimeEdit) {
                    pontoEntregaObj.setX(latLng.latitude);
                    pontoEntregaObj.setY(latLng.longitude);
                    pontoEntregaObj.setUpdate(true);
                    /*PostPontoEditDialogFragment.newInstance(MapsPostsActivity.this, MapsPostsActivity.this, pontoEntregaObj)
                            .show(getSupportFragmentManager(), "demandaEdit");*/

                    try {
                        //OfflineDownloadResponse download = FileUtils.retrieve(mPontoEntrega.getOrderId());
                        OfflineDownloadResponse download = null;
                        if (DownloadOrderOffline.getResponse() == null) {
                            DownloadOrderOffline.setResponse(FileUtils.retrieve(pontoEntregaObj.getOrderId()));
                            download = DownloadOrderOffline.getResponse();
                        } else {
                            download = DownloadOrderOffline.getResponse();
                        }
                        List<PontoEntrega> pontoEntregas = download.getPontoEntregaList();
                        List<Post> postes = download.getPosts();
                        List<VaosPontoPoste> vaosPontoPostes = download.getVaosPontoPosteList() == null ? new ArrayList<VaosPontoPoste>() : download.getVaosPontoPosteList();
                        Iterator<PontoEntrega> iterator = pontoEntregas.iterator();

                        while (iterator.hasNext()) {
                            PontoEntrega post = iterator.next();
                            if (post.getId() == pontoEntregaObj.getId()) {
                                iterator.remove();
                                break;
                            }
                        }

                        for (Post post : postes) {
                            if (post.getId() == pontoEntregaObj.getPostId()) {
                                postTimeEditObj = post;
                            }
                        }
//                        if (postTimeEditObj != null) {
//                            if (pontoEntregaObj.getId() != 0) {
//                                long id_ponto = pontoEntregaObj.getId();
//                                for (VaosPontoPoste vaosPontoPoste : vaosPontoPostes) {
//                                    if (vaosPontoPoste.getId_ponto_entrega() == id_ponto) {
//                                        vaosDeletar = vaosPontoPoste;
//                                        vaosPontoPostes.remove(vaosPontoPoste);
//                                        break;
//                                    }
//                                }
//                            }
//                        }

                                             /*   mVaosPontoPoste = new VaosPontoPoste();
                                                if (mPost != null) {
                                                    deletarPlyline = true;
                                                    //mVaosPontoPoste = new VaosPontoPoste();
                                                    mVaosPontoPoste.setId_ponto_entrega(mPontoEntrega.getId());
                                                    mVaosPontoPoste.setX1(mPontoEntrega.getX());
                                                    mVaosPontoPoste.setX2(mPost.getLocation().getLat());
                                                    mVaosPontoPoste.setY1(mPontoEntrega.getY());
                                                    mVaosPontoPoste.setY2(mPost.getLocation().getLon());
                                                    mVaosPontoPoste.setId_poste(mPost.getId());
                                                }*/
                                             /*   if (trocaPositionVaoPOnto) {
                                                    if (mPontoEntrega.getId() != 0) {
                                                        deletarPlyline = true;
                                                        long id_ponto = mPontoEntrega.getId();
                                                        for (VaosPontoPoste vaosPontoPoste : vaosPontoPostes) {
                                                            if (vaosPontoPoste.getId_ponto_entrega() == id_ponto) {
                                                                //vaosDeletar = vaosPontoPoste;
                                                                //vaosPontoPostes.remove(vaosPontoPoste);
                                                                // mVaosPontoPoste.setId_ponto_entrega(mPontoEntrega.getId());
                                                                vaosPontoPoste.setX1(mPontoEntrega.getX());
                                                                // vaosPontoPoste.setX2(mPost.getLocation().getLat());
                                                                mPost = new Post();
                                                                vaosPontoPoste.setY1(mPontoEntrega.getY());
                                                                PostLocation postLocation = new PostLocation();
                                                                postLocation.setLat(vaosPontoPoste.getX2());
                                                                postLocation.setLon(vaosPontoPoste.getY2());
                                                                mPost.setLocation(postLocation);
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                                vaosPontoPostes.add(mVaosPontoPoste);*/

                        pontoEntregas.add(pontoEntregaObj);
                        download.setPontoEntregaList(pontoEntregas);
                        //   download.setVaosPontoPosteList(vaosPontoPostes);

                        if (FileUtils.saveOfflineDownload(download)) {
                            Verifier.addPostCount(pontoEntregaObj.getGeoCode());
                            Toast.makeText(MapsPostsActivity.this,
                                    R.string.dialog_post_edit_saving_success,
                                    Toast.LENGTH_LONG).show();
                            onPontoEntegasChanged(pontoEntregaObj, false, postTimeEditObj, false, true);

                        } else {
                            Toast.makeText(MapsPostsActivity.this,
                                    getString(R.string.dialog_post_edit_saving_error,
                                            pontoEntregaObj.getId()),
                                    Toast.LENGTH_LONG).show();
                        }


                    } catch (IOException e) {
                        LogUtils.error(this, e);


                        Toast.makeText(MapsPostsActivity.this,
                                getString(R.string.dialog_post_edit_saving_error,
                                        pontoEntregaObj.getId()),
                                Toast.LENGTH_LONG).show();
                    }

                    demandaTimeEdit = false;
                    posteTime = true;
                    setupToolbar(R.drawable.logo, mOrderDetails.getNumber() + "- Cadastro de Poste");
                }
            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mMap.animateCamera(CameraUpdateFactory
                        .newLatLngBounds(GeoUtils.createLatLngBounds(marker.getPosition()), 5));
                return false;
            }
        });

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Post post = postsMap.get(marker.getId());
                PontoEntrega pontoEntrega = pontosMap.get(marker.getId());
                Anotacao anotacao = anotacaoMap.get(marker.getId());
                if (anotacao != null) {
                    padfAnotacao = (AnotacaoActionDialogFrament) AnotacaoActionDialogFrament.newInstance(MapsPostsActivity.this, anotacao);
                    padfAnotacao.show(getSupportFragmentManager(), "anotacaoActions");
                }
                if (pontoEntrega != null) {
                    padfPponto = (PontoActionsDialogFragment) PontoActionsDialogFragment.newInstance(MapsPostsActivity.this, pontoEntrega);
                    padfPponto.show(getSupportFragmentManager(), "pontoEntregaActions");
                }
                if (post == null) {
                    return;
                } else {
                    padf = (PostActionsDialogFragment) PostActionsDialogFragment.newInstance(MapsPostsActivity.this, post);
                    padf.show(getSupportFragmentManager(), "postActions");
                }
            }
        });

        mMap.setOnInfoWindowLongClickListener(new GoogleMap.OnInfoWindowLongClickListener() {
            @Override
            public void onInfoWindowLongClick(Marker marker) {
                if (anotacao != null) {
                    //    Toast.makeText(MapsPostsActivity.this, "FDOEU", Toast.LENGTH_LONG).show();
                }
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public boolean onMarkerClick(Marker marker) {
                if (demandaPosteTime) {
                    if (markerPoste != null) {
                        if (posteTimeObjeto != null) {
                            markerPoste.setIcon(getPin(posteTimeObjeto));
                        }
                    }
                    posteTimeObjeto = postsMap.get(marker.getId());

                    if (posteTimeObjeto != null) {
                        markerPoste = markersMap.get(posteTimeObjeto.getId());
                        markerPoste.setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.posteselect));
                    }

                    if (posteTimeObjeto != null) {
                        demandaTime = true;
                        //Snackbar.make(SnakemapFragment, "Agora escolha o local da Demanda", Snackbar.LENGTH_LONG).show();
                        textoAux.setText("Escolha o local da demanda");
                    } else {
                        Snackbar.make(SnakemapFragment, "Escolha um Poste", Snackbar.LENGTH_LONG).show();
                        demandaTime = false;
                        textoAux.setText("Escolha um poste");
                        markerPoste = null;
                    }

                }

                if (strandTime) {
                    countStrand++;
                    if (countStrand == 1) {
                        postTimeStrand = postsMap.get(marker.getId());
                        if (postTimeStrand != null) {
                            LatLng latLngPost = new LatLng(postTimeStrand.getLocation().getLat(), postTimeStrand.getLocation().getLon());
                            demandaStrandTime.setX1(latLngPost.latitude);
                            demandaStrandTime.setY1(latLngPost.longitude);
                            //Pega ID do Poste Clicado.
                            demandaStrandTime.setPosteId1(postTimeStrand.getId());

                            markerStrand1 = mMap.addMarker(new MarkerOptions()
                                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin1))
                                    .position(latLngPost));
                            Snackbar.make(SnakemapFragment, "Escolha o fim", Snackbar.LENGTH_LONG).show();
                            btn_cancelar_opcao.setVisibility(View.GONE);
                        } else {
                            Snackbar.make(SnakemapFragment, "Escolha um poste", Snackbar.LENGTH_LONG).show();
                            countStrand--;
                        }
                        postTimeStrand = null;
                    }

                    if (countStrand == 2) {
                        postTimeStrand = postsMap.get(marker.getId());
                        if (postTimeStrand != null) {
                            LatLng latLngPost = new LatLng(postTimeStrand.getLocation().getLat(), postTimeStrand.getLocation().getLon());
                            demandaStrandTime.setX2(latLngPost.latitude);
                            demandaStrandTime.setY2(latLngPost.longitude);
                            demandaStrandTime.setPosteId2(postTimeStrand.getId());
                            demandaStrandTime.setOrderId(mOrderDetails.getId());
                            criaStrand(demandaStrandTime, true);
                            markerStrand1.remove();
                            countStrand = 0;
                        } else {
                            Snackbar.make(SnakemapFragment, "Escolha um poste", Snackbar.LENGTH_LONG).show();
                            countStrand--;
                        }
                        postTimeStrand = null;
                    }
                }

                if (fotoTime) {
                    postTimeFoto = postsMap.get(marker.getId());
                    // pontoEntregaObj = pontosMap.get(marker.getId());
                    if (postTimeFoto != null) {
                        tirarFotoPoste(postTimeFoto);
                    }
//                    if(pontoEntregaObj != null){
//                        tirarFotoPoste(postTimeFoto);
//                    }
                }

                return false;
            }
        });

        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                Toast.makeText(MapsPostsActivity.this, "ola", Toast.LENGTH_LONG).show();
            }
        });

//        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
//            @Override
//            public void onMapLoaded() {
        if (mPosts != null) {
            points = new ArrayList<>(mPosts.size());
        }

        Polygon polygon = mMap.addPolygon(
                getPolygonOptions(mOrderPoints));
        polygon.setFillColor(Color.argb(20, 255, 255, 0));
        polygon.setClickable(false);

        //List<Post> listDelete = new ArrayList<>();
        if (mPosts != null) {
            if (!mPosts.isEmpty()) {
                for (Post post : mPosts) {
                    int postN = post.getPostNumber();
                    LogUtils.log("Loading Post Geocode / Number = " + String.valueOf(post.getGeoCode() + " / " + post.getPostNumber()));
                    if (postN > highestPostNumber) {
                        highestPostNumber = postN;
                    }

                    LatLng latLng = new LatLng(post.getLocation().getLat(), post.getLocation().getLon());
                    points.add(latLng);
                    Marker marker = mMap.addMarker(
                            new MarkerOptions()
                                    .icon(getPin(post))
                                    .position(latLng)
                                    .title(String.valueOf(post.getGeoCode() + " / " + post.getPostNumber())));

                    markersMap.put(post.getId(), marker);
                    postsMap.put(marker.getId(), post);

                  /*  List<PostMedidoresPosicao> peList = post.getPosicoesMedidores();
                    if (peList != null) {
                        for (PostMedidoresPosicao pea : peList) {
                            List<LatLng> lineList = new ArrayList<LatLng>();
                            lineList.add(post.getLocation().toLatLng());
                            LatLng peaLatLng = new LatLng(pea.getX(), pea.getY());
                            lineList.add(peaLatLng);
                            Polyline line = mMap.addPolyline(getPontoEntregaPolylineOptions(lineList));
                        }
                    }*/

                }
            }
        }
        if (mDemandaStrands != null) {
            if (!mDemandaStrands.isEmpty()) {
                for (final DemandaStrand demandaStrand : mDemandaStrands) {
                    if (!demandaStrand.isExcluido()) {
                        LatLng latLng = new LatLng(demandaStrand.getX2(), demandaStrand.getY2());

                        Polyline addPolyline = mMap.addPolyline(new PolylineOptions()
                                .add(new LatLng(demandaStrand.getX1(), demandaStrand.getY1()), latLng)
                                .width(10)
                                .color(Color.parseColor("#ff9900")));
                        addPolyline.setClickable(true);
                        polylineStrandId.put(demandaStrand.getId(), addPolyline);
                        polylineStrandMapa.put(addPolyline, demandaStrand);

                        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
                            @Override
                            public void onPolylineClick(Polyline polyline) {
                            /*Polyline polyline1 = polylineStrand.get(demandaStrand.getId());
                            polyline1.remove();*/
                                polyline.setColor(Color.parseColor("#000000"));

                                DemandaStrand demandaStrand1 = polylineStrandMapa.get(polyline);

                                padfStrand = (DemandaStrandActionsDialogFragment) DemandaStrandActionsDialogFragment.newInstance(MapsPostsActivity.this, MapsPostsActivity.this, demandaStrand1, null, polyline);
                                padfStrand.show(getSupportFragmentManager(), "demandaStradtEdit");

                            }
                        });
                    }
                }
            }
        }
        if (mQuadras != null) {
            if (!mQuadras.isEmpty()) {
                for (Quadra quadra : mQuadras) {
                    Iterable<LatLng> lngList = null;
                    List<LatLng> latAux = new ArrayList<>();
                    for (LatLonQuadraResponse quadra1 : quadra.getLatLngs()) {
                        latAux.add(new LatLng(quadra1.getX(), quadra1.getY()));
                    }
                    lngList = latAux;
                    if (lngList != null) {
                        Polygon polygon2 = mMap.addPolygon(new PolygonOptions()
                                .addAll(lngList)
                                .strokeColor(Color.BLACK)
                                .strokeWidth(10));
                    }
                    //LatLng latLng = new LatLng(quadra.getX2(), quadra.getY2());

                }
            }
        }
               /* for(VaoPrimario vao : mVaosPrimarios) {
                    if(vao.getX1() < 0) {
                        LogUtils.log("Vao x1: "+vao.getX1()+"\nx2: "+vao.getX2());
                        List<LatLng> lineList = new ArrayList<LatLng>();
                        lineList.add(new LatLng(vao.getX1(), vao.getY1()));
                        lineList.add(new LatLng(vao.getX2(), vao.getY2()));
                        Polyline line = mMap.addPolyline(getVaoPolylineOptions(lineList));
                        line.setClickable(false);
                    }
                }*/
        if (mVaosPontoPostes != null) {
            if (!mVaosPontoPostes.isEmpty()) {
                for (VaosPontoPoste vaosPontoPoste : mVaosPontoPostes) {
                    if (vaosPontoPoste.getX1() != null) {
                        List<LatLng> lineList = new ArrayList<LatLng>();

                        lineList.add(new LatLng(vaosPontoPoste.getX1(), vaosPontoPoste.getY1()));
                        lineList.add(new LatLng(vaosPontoPoste.getX2(), vaosPontoPoste.getY2()));
                       /* Polyline line = mMap.addPolyline(ge
                       tVaoPolylineOptions(lineList));
                        line.setWidth(6);
                        line.setColor((Color.parseColor("#f0ff23")));*/

                        LatLng latLng = new LatLng(vaosPontoPoste.getX2(), vaosPontoPoste.getY2());

                        Polyline line2 = mMap.addPolyline(new PolylineOptions()
                                .add(new LatLng(vaosPontoPoste.getX1(), vaosPontoPoste.getY1()), latLng)
                                .width(4)
                                .color(Color.parseColor("#ff3a3a")));

                        line2.setClickable(false);
                        polylineMapEntreg.put(vaosPontoPoste.getId_ponto_entrega(), line2);
                        polylineMapPoste.put(vaosPontoPoste.getId_poste(), line2);
                    }
                }
            }
        }
        if (mPontoEntregas != null) {
            if (!mPontoEntregas.isEmpty()) {
                for (PontoEntrega pontoEntrega : mPontoEntregas) {

                    //LatLng peaLatLng = new LatLng(pontoEntrega.getX(), pontoEntrega.getY());
                    LatLng peaLatLng = new LatLng(pontoEntrega.getX(), pontoEntrega.getY());

                    List<CharSequence> tipoDemandas = TipoDemanda.getNames();
                    CharSequence tipoDemanda = tipoDemandas.get(pontoEntrega.getTipoDemanda());
                    Marker marker = null;
//TODO
                    if (pontoEntrega.isExcluido()) {
                        marker = mMap.addMarker(new MarkerOptions().position(peaLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ponto_location_delete)).title("Nº" + pontoEntrega.getNumero_local() + " / " + tipoDemanda.toString()));
                    } else {
//                                if (pontoEntrega.getTipoDemanda() == 0) {
//                                    marker = mMap.addMarker(new MarkerOptions().position(peaLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.duvida)).title("Nº" + pontoEntrega.getNumero_local() + " / " + tipoDemanda.toString()).snippet(tipoDemanda.toString()));
//                                }
//                                if (pontoEntrega.getTipoDemanda() == 1) {
//                                    marker = mMap.addMarker(new MarkerOptions().position(peaLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.predio)).title("Nº" + pontoEntrega.getNumero_local() + " / " + tipoDemanda.toString()).snippet(tipoDemanda.toString()));
//                                }
//                                if (pontoEntrega.getTipoDemanda() == 2) {
//                                    marker = mMap.addMarker(new MarkerOptions().position(peaLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.comercio)).title("Nº" + pontoEntrega.getNumero_local() + " / " + tipoDemanda.toString()).snippet(tipoDemanda.toString()));
//                                }
//                                if (pontoEntrega.getTipoDemanda() == 3) {
//                                    marker = mMap.addMarker(new MarkerOptions().position(peaLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.terreno)).title("Nº" + pontoEntrega.getNumero_local() + " / " + tipoDemanda.toString()).snippet(tipoDemanda.toString()));
//                                }
//                                if (pontoEntrega.getTipoDemanda() == 4) {
//                                    marker = mMap.addMarker(new MarkerOptions().position(peaLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.casa)).title("Nº" + pontoEntrega.getNumero_local() + " / " + tipoDemanda.toString()).snippet(tipoDemanda.toString()));
//
                        marker = mMap.addMarker(new MarkerOptions().position(peaLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_dem)).title("Nº" + pontoEntrega.getNumero_local() + " / " + tipoDemanda.toString()).snippet(tipoDemanda.toString()));
                    }
                    if (marker != null) {
                        pontosMap.put(marker.getId(), pontoEntrega);
                        markersMapPontoEntrega.put(pontoEntrega.getId(), marker);
                    }
                }
            }
        }
        if (anotacaoList != null) {
            if (!anotacaoList.isEmpty()) {
                for (Anotacao anotacao : anotacaoList) {
                    if (!anotacao.isExcluido()) {
                        //LatLng peaLatLng = new LatLng(pontoEntrega.getX(), pontoEntrega.getY());
                        LatLng peaLatLng = new LatLng(anotacao.getX(), anotacao.getY());

                        Marker marker = null;
                        marker = mMap.addMarker(new MarkerOptions().position(peaLatLng)
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.noteicon3))
                                .title(anotacao.getAnotacao()));
                        if (marker != null) {
                            anotacaoMap.put(marker.getId(), anotacao);
                            markersMapAnatocao.put(anotacao.getId(), marker);
                        }
                    }
                }
            }
        }

                /*mMap.addCircle(new CircleOptions().)
                new MarkerOptions().
                */
        //mPosts.removeAll(listDelete);

        if (!points.isEmpty()) {
            mMap.animateCamera(CameraUpdateFactory
                    .newLatLngBounds(GeoUtils.createLatLngBounds(points), 50));
        }

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        /*LogUtils.log("onMapClick: starting to check for polylines");
                        for(Polyline p : polylineList) {
                            if(PolyUtil.isLocationOnPath(latLng, p.getPoints(), p.isGeodesic(), 100)) {
                                LogUtils.log("found line "+p.getId());
                            }
                        }*/
            }
        });
//            }
//        });

        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                LogUtils.log("polyline clicked id: " + polyline.getId());
            }
        });
    }


    public static PolygonOptions getPolygonOptions(List<LatLng> orderPoints) {
        int POLYGON_ALPHA = 70;
        PolygonOptions options = new PolygonOptions().addAll(orderPoints);
        options.strokeColor(Color.rgb(216, 255, 0));
        //.fillColor(Color.argb(POLYGON_ALPHA, 56, 153, 185));

        return options;
    }

    public static PolylineOptions getVaoPolylineOptions(List<LatLng> points) {
        int POLYGON_ALPHA = 70;
        PolylineOptions options = new PolylineOptions().addAll(points);
        options.color(Color.argb(255, 237, 59, 50));
        options.width(10);
        return options;
    }

    public static PolylineOptions getQuadrasPolylineOptions(List<LatLng> points) {
        int POLYGON_ALPHA = 70;
        PolylineOptions options = new PolylineOptions().addAll(points);
        options.color(Color.parseColor("#000000"));
        options.width(10);
        return options;
    }

    public static PolylineOptions getPontoEntregaPolylineOptions(List<LatLng> points) {
        int POLYGON_ALPHA = 70;
        PolylineOptions options = new PolylineOptions().addAll(points);
        options.color(Color.argb(90, 95, 187, 0));
        options.width(10);
        return options;
    }

    @SuppressWarnings("ResourceType")
    private void defineLocation() {
        mMap.setMyLocationEnabled(true);
        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        int padding = (int) (width * 0.12); // offset from edges of the map 12% of screen
        mMap.moveCamera(CameraUpdateFactory
                .newLatLngBounds(GeoUtils.createLatLngBounds(mOrderPoints), width, height, padding));
    }

    @Override
    public void onEditPostButtonClicked(Post post) {

        /*PostEditDialogFragment.newInstance(MapsPostsActivity.this, this, post)
                .show(getSupportFragmentManager(), "postEdit");*/

        PostCompletoEditFragment.newInstance(MapsPostsActivity.this, this, post)
                .show(getSupportFragmentManager(), "postEditCompleto");

    }

    @Override
    public void onEditLocalButtonClicked(Post post) {
        postTimeEditObj = post;
        postTimeEdit = true;
        posteTime = false;
        demandaTime = false;
        //Snackbar.make(SnakemapFragment, "Escolha o local novo", Snackbar.LENGTH_LONG).show();
        textoAux.setText("Escolha o local novo");
        btn_cancelar_opcao.setVisibility(View.VISIBLE);
        textoAux.setVisibility(View.VISIBLE);
    }

    @Override
    public void onToggleDeletePostButtonClicked(final Post post) {
        final boolean apagar = !post.isExcluido();
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        alertDialog.setTitle(apagar ? "Apagar poste" : "Restaurar poste");
        alertDialog.setMessage("Tem certeza?");
        alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Location l = getLastLocation();
                post.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis() / 1000));
                post.setUpdate(true);

                /*final ProgressDialog alertDialog2 = new ProgressDialog(getBaseContext(), R.style.AlertDialogTheme);
                alertDialog2.setTitle(getString(R.string.dialog_deleting_title));
                alertDialog2.setCancelable(false);
                alertDialog2.show();
                dialog.dismiss();*/


                boolean workingOffline = FileUtils.serviceOrderFileExists(post.getOrderId());
                if (workingOffline) {

                    try {
                        post.setExcluido(apagar);
                        //  OfflineDownloadResponse download = FileUtils.retrieve(post.getOrderId());
                        OfflineDownloadResponse download = null;
                        if (DownloadOrderOffline.getResponse() == null) {
                            DownloadOrderOffline.setResponse(FileUtils.retrieve(post.getOrderId()));
                            download = DownloadOrderOffline.getResponse();
                        } else {
                            download = DownloadOrderOffline.getResponse();
                        }
                        List<Post> posts = download.getPosts();

                        Iterator<Post> iterator = posts.iterator();

                        while (iterator.hasNext()) {
                            Post p = iterator.next();
                            //if(p.equals(post)) {
                            if (p.getId() == post.getId()) {
                                LogUtils.log("FOUND " + p.getId());
                                iterator.remove();
                                break;
                            }
                        }
                        if (post.getId() > 0) {
                            posts.add(post);
                        }
                        download.setPostList(posts);

                        if (FileUtils.saveOfflineDownload(download)) {
                            Toast.makeText(getApplicationContext(),
                                    "Operação realizada com sucesso!",
                                    Toast.LENGTH_LONG).show();
                            padf.dismiss();
                            //onPostDelete(post);
                            onPostChanged(post, false, null);

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Houve algum problema salvando as alterações, por favor tente novamente ou informe o administrador.",
                                    Toast.LENGTH_LONG).show();
                        }

                        dialog.dismiss();

                    } catch (IOException e) {
                        LogUtils.error(this, e);
                        dialog.dismiss();

                        Toast.makeText(getApplicationContext(),
                                "Houve algum problema salvando as alterações, por favor tente novamente ou informe o administrador.",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    //FIXME: não foi implementado restauração de postes ONLINE
                   /* VisiumApiCallback<Post> callback =
                            new VisiumApiCallback<Post>() {

                                @Override
                                public void callback(Post postReturn, ErrorResponse e) {

                                    //alertDialog2.dismiss();

                                    if (e == null) {
                                        Toast.makeText(getApplicationContext(),
                                                "Operação realizada com sucesso!",
                                                Toast.LENGTH_LONG).show();
                                        padf.dismiss();
                                        //onPostDelete(post);
                                        onPostChanged(post, false);
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                "Houve algum problema salvando as alterações, por favor tente novamente ou informe o administrador.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            };

                    PostController.get().delete(getApplicationContext(), post, callback);*/
                }
            }
        });

        alertDialog.setNegativeButton("NAO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alertDialog.show();

    }

    @Override
    public void onListPontoEntregaButtonClicked(final Post post) {

        final ProgressDialog alertDialog = new ProgressDialog(this, R.style.AlertDialogTheme);
        alertDialog.setTitle(getString(R.string.dialog_ponto_de_entrega_title));
        alertDialog.setMessage("Buscando Pontos de Entrega");
        alertDialog.setCancelable(false);
        alertDialog.show();

        if (FileUtils.serviceOrderFileExists(post.getOrderId())) {

            try {
                // OfflineDownloadResponse download = FileUtils.retrieve(post.getOrderId());
                OfflineDownloadResponse download = null;
                if (DownloadOrderOffline.getResponse() == null) {
                    DownloadOrderOffline.setResponse(FileUtils.retrieve(post.getOrderId()));
                    download = DownloadOrderOffline.getResponse();
                } else {
                    download = DownloadOrderOffline.getResponse();
                }

                List<PontoEntrega> pontoEntregas = new ArrayList<>();
                for (PontoEntrega pontoEntrega : download.getPontoEntregaList()) {
                    LogUtils.log("PontoEntrega pontoId = " + pontoEntrega.getId() + " pontoEntregaPostId = " + pontoEntrega.getPostId());
                    if (pontoEntrega.getPostId() == post.getId()) {
                        pontoEntregas.add(pontoEntrega);
                    }
                }

                MapsPostsActivity.this.pontoEntregaList = pontoEntregas;
                alertDialog.dismiss();
                pontoEntregaListDialogFragment = PontoEntregaListDialogFragment
                        .newInstance(MapsPostsActivity.this, pontoEntregaList, post);
                pontoEntregaListDialogFragment.show(getSupportFragmentManager(), "pontoEntregaList");
            } catch (IOException e) {
                LogUtils.error(this, e);
                alertDialog.dismiss();

                Toast.makeText(MapsPostsActivity.this,
                        getString(R.string.dialog_lighting_error, post.getGeoCode()),
                        Toast.LENGTH_LONG).show();
            }

        } else {
            PontoEntregaController.get().getPontoEntregaList(post.getId(), new VisiumApiCallback<List<PontoEntrega>>() {
                @Override
                public void callback(List<PontoEntrega> pontoEntregaList, ErrorResponse e) {
                    alertDialog.dismiss();

                    MapsPostsActivity.this.pontoEntregaList = pontoEntregaList;

                    if (pontoEntregaList != null && e == null) {

                        pontoEntregaListDialogFragment = PontoEntregaListDialogFragment
                                .newInstance(MapsPostsActivity.this, pontoEntregaList, post);
                        pontoEntregaListDialogFragment.show(getSupportFragmentManager(), "pontoEntregaList");
                    } else if (e != null && e.isCustomized()) {
                        Toast.makeText(MapsPostsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MapsPostsActivity.this,
                                getString(R.string.dialog_ponto_de_entrega_error, post.getGeoCode()),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }


        /*
        final ProgressDialog alertDialog = new ProgressDialog(this, R.style.AlertDialogTheme);
        alertDialog.setTitle(getString(R.string.dialog_ponto_de_entrega_title));
        alertDialog.setMessage(getString(R.string.dialog_ponto_de_entrega_message));
        alertDialog.setCancelable(false);
        alertDialog.show();

        /*PontoEntregaController.get().getPontoEntregaList(getApplicationContext(), post.getId(), new VisiumApiCallback<List<PontoEntrega>>() {
            @Override
            public void callback(List<PontoEntrega> pontoEntregaList, ErrorResponse e) {
                alertDialog.dismiss();

        if (FileUtils.serviceOrderFileExists(post.getOrderId())) {

            try {
                OfflineDownloadResponse download = FileUtils.retrieve(post.getOrderId());

                List<PontoEntrega> pontoEntregas = new ArrayList<>();
                for (PontoEntrega pontoEntrega : download.getPontoEntregaList()) {
                    if (pontoEntrega.getPostId() == post.getId()) {
                        pontoEntregas.add(pontoEntrega);
                    }
                }

                MapsPostsActivity.this.pontoEntregaList = pontoEntregas;
                alertDialog.dismiss();
                pontoEntregaListDialogFragment = PontoEntregaListDialogFragment
                        .newInstance(MapsPostsActivity.this, pontoEntregaList, post);
                pontoEntregaListDialogFragment.show(getSupportFragmentManager(), "pontoEntregaList");
            } catch (IOException e) {
                LogUtils.error(this, e);
                alertDialog.dismiss();

                Toast.makeText(MapsPostsActivity.this,
                        getString(R.string.dialog_ponto_de_entrega_error, post.getGeoCode()),
                        Toast.LENGTH_LONG).show();
            }

        } else {
            PontoEntregaController.get().getPontoEntregaList(MapsPostsActivity.this, post.getId(), new VisiumApiCallback<List<PontoEntrega>>() {
                @Override
                public void callback(List<PontoEntrega> pontoEntregaList, ErrorResponse e) {
                    alertDialog.dismiss();

                    MapsPostsActivity.this.pontoEntregaList = pontoEntregaList;

                    if (pontoEntregaList != null && e == null) {

                        pontoEntregaListDialogFragment = PontoEntregaListDialogFragment
                                .newInstance(MapsPostsActivity.this, pontoEntregaList, post);
                        pontoEntregaListDialogFragment.show(getSupportFragmentManager(), "pontoEntregaList");
                    } else if (e != null && e.isCustomized()) {
                        Toast.makeText(MapsPostsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MapsPostsActivity.this,
                                getString(R.string.dialog_ponto_de_entrega_error, post.getGeoCode()),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }*/
    }

    @Override
    public void onListAfloramentosButtonClicked(final Post post) {
        final ProgressDialog alertDialog = new ProgressDialog(this, R.style.AlertDialogTheme);
        alertDialog.setTitle(getString(R.string.dialog_afloramento_title));
        alertDialog.setMessage(getString(R.string.dialog_lighting_message));
        alertDialog.setCancelable(false);
        alertDialog.show();

        if (FileUtils.serviceOrderFileExists(post.getOrderId())) {

            try {
                OfflineDownloadResponse download = FileUtils.retrieve(post.getOrderId());

                List<Afloramento> afloramentos = new ArrayList<>();
                for (Afloramento afloramento : download.getAfloramentoList()) {
                    if (afloramento.getPostId() == post.getId()) {
                        afloramentos.add(afloramento);
                    }
                }

                //TODO
                MapsPostsActivity.this.afloramentoList = afloramentos;
                alertDialog.dismiss();
                afloramentoListDialogFragment = AfloramentoListDialogFragment
                        .newInstance(MapsPostsActivity.this, afloramentoList, post);
                afloramentoListDialogFragment.show(getSupportFragmentManager(), "afloramentoList");
            } catch (IOException e) {
                LogUtils.error(this, e);
                alertDialog.dismiss();

                Toast.makeText(MapsPostsActivity.this,
                        getString(R.string.dialog_afloramento_error, post.getGeoCode()),
                        Toast.LENGTH_LONG).show();
            }
        } else {
            AfloramentoController.get().getAfloramentoList(post.getId(), new VisiumApiCallback<List<Afloramento>>() {
                @Override
                public void callback(List<Afloramento> afloramentoList, ErrorResponse e) {
                    alertDialog.dismiss();

                    MapsPostsActivity.this.afloramentoList = afloramentoList;

                    if (afloramentoList != null && e == null) {

                        //TODO
                        afloramentoListDialogFragment = AfloramentoListDialogFragment
                                .newInstance(MapsPostsActivity.this, afloramentoList, post);
                        afloramentoListDialogFragment.show(getSupportFragmentManager(), "lightingList");
                    } else if (e != null && e.isCustomized()) {
                        Toast.makeText(MapsPostsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MapsPostsActivity.this,
                                getString(R.string.dialog_afloramento_error, post.getGeoCode()),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public void onListBancoCapacitadorButtonClicked(Post post) {

    }

    @Override
    public void onListLightingPostButtonClicked(final Post post) {

        final ProgressDialog alertDialog = new ProgressDialog(this, R.style.AlertDialogTheme);
        alertDialog.setTitle(getString(R.string.dialog_lighting_title));
        alertDialog.setMessage(getString(R.string.dialog_lighting_message));
        alertDialog.setCancelable(false);
        alertDialog.show();

        if (FileUtils.serviceOrderFileExists(post.getOrderId())) {

            try {
                OfflineDownloadResponse download = FileUtils.retrieve(post.getOrderId());

                List<Lighting> lightings = new ArrayList<>();
                for (Lighting lighting : download.getLightingList()) {
                    if (lighting.getPostId() == post.getId()) {
                        lightings.add(lighting);
                    }
                }

                MapsPostsActivity.this.lightingList = lightings;
                alertDialog.dismiss();
                lightingListDialogFragment = LightingListDialogFragment
                        .newInstance(MapsPostsActivity.this, lightingList, post);
                lightingListDialogFragment.show(getSupportFragmentManager(), "lightingList");
            } catch (IOException e) {
                LogUtils.error(this, e);
                alertDialog.dismiss();

                Toast.makeText(MapsPostsActivity.this,
                        getString(R.string.dialog_lighting_error, post.getGeoCode()),
                        Toast.LENGTH_LONG).show();
            }

        } else {
            LightingController.get().getLightingList(post.getId(), new VisiumApiCallback<List<Lighting>>() {
                @Override
                public void callback(List<Lighting> lightingList, ErrorResponse e) {
                    alertDialog.dismiss();

                    MapsPostsActivity.this.lightingList = lightingList;

                    if (lightingList != null && e == null) {

                        lightingListDialogFragment = LightingListDialogFragment
                                .newInstance(MapsPostsActivity.this, lightingList, post);
                        lightingListDialogFragment.show(getSupportFragmentManager(), "lightingList");
                    } else if (e != null && e.isCustomized()) {
                        Toast.makeText(MapsPostsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MapsPostsActivity.this,
                                getString(R.string.dialog_lighting_error, post.getGeoCode()),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public void onPostChanged(Post post, boolean creating, List<PontoEntrega> pontosNovos) {
        /*if(post.isExcluido()) {
            //onPostDelete(post);
            return;
        }*/

        mPosts.remove(post);
        mPosts.add(post);

        Marker marker;

        if (!creating) {
            marker = markersMap.get(post.getId());
            markersMap.remove(post.getId());
            postsMap.remove(marker.getId());
            marker.remove();
        }

        marker = mMap.addMarker(
                new MarkerOptions()
                        .icon(getPin(post))
                        .position(post.getLocation().toLatLng())
                        .title(String.valueOf(post.getGeoCode() + " / " + post.getPostNumber())));
        markersMap.put(post.getId(), marker);
        postsMap.put(marker.getId(), post);

        trocapositionVaoPontoPoste(post, pontosNovos);


    /*    Polyline polyline2 = polylineMapPoste.get(post.getId());
        if (polyline2 != null) {
            polyline2.remove();
        }

        if (pontosNovos != null) {
            for (PontoEntrega entrega : pontosNovos) {
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(entrega.getX(), entrega.getY()), post.getLocation().toLatLng())
                        .width(4)
                        .color(Color.parseColor("#ff3a3a")));
                polylineMapEntreg.put(entrega.getId(), line);
                polylineMapPoste.put(post.getId(), line);
            }
        }*/
    }

    public void trocapositionVaoPontoPoste(Post post, List<PontoEntrega> pontoEntregas) {

        //OfflineDownloadResponse download = FileUtils.retrieve(mPontoEntrega.getOrderId());
        try {
            //OfflineDownloadResponse download = FileUtils.retrieve(mPontoEntrega.getOrderId());
            OfflineDownloadResponse download = null;
            if (DownloadOrderOffline.getResponse() == null) {
                DownloadOrderOffline.setResponse(FileUtils.retrieve(post.getOrderId()));
                download = DownloadOrderOffline.getResponse();
            } else {
                download = DownloadOrderOffline.getResponse();
            }
            List<VaosPontoPoste> vaosPontoPostes = download.getVaosPontoPosteList() == null ? new ArrayList<VaosPontoPoste>() : download.getVaosPontoPosteList();

            if (post != null) {
                if (pontoEntregas != null) {
                    for (PontoEntrega entrega : pontoEntregas) {
                        long id_ponto = entrega.getId();
                        for (VaosPontoPoste vaosPontoPoste : vaosPontoPostes) {
                            if (vaosPontoPoste.getId_ponto_entrega() == id_ponto) {

                                vaosPontoPoste.setX2(entrega.getX());
                                vaosPontoPoste.setX1(post.getLocation().getLat());
                                vaosPontoPoste.setY2(entrega.getY());
                                vaosPontoPoste.setY1(post.getLocation().getLon());
                                break;
                            }
                        }
                    }
                }
            }
            download.setVaosPontoPosteList(vaosPontoPostes);

            if (FileUtils.saveOfflineDownload(download)) {
                //Verifier.addPostCount(mPontoEntrega.getGeoCode());
                Toast.makeText(this,
                        "Alterações Executadas com Sucesso!",
                        Toast.LENGTH_LONG).show();
                //dismissAllowingStateLoss();
                //  mListener.onPontoEntegasChanged(mPontoEntrega, false, mPost, deletarPlyline);

            } else {
                Toast.makeText(this,
                        "Erro ao Executar a Alteração",
                        Toast.LENGTH_LONG).show();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        if (post != null) {
            if (pontoEntregas != null) {
                for (PontoEntrega entrega : pontoEntregas) {
                    Polyline poly = polylineMapEntreg.get(entrega.getId());
                    if (poly != null) {
                        poly.remove();
                    }
                }
                for (PontoEntrega entrega : pontoEntregas) {
                    VaosPontoPoste vaosPontoPoste = new VaosPontoPoste();

                    Polyline line = mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(entrega.getX(), entrega.getY()), post.getLocation().toLatLng())
                            .width(4)
                            .color(Color.parseColor("#ff3a3a")));
                    polylineMapEntreg.put(entrega.getId(), line);
                    polylineMapPoste.put(post.getId(), line);


                }
            }
        }
    }

    public void deletePolylineAndMakersNegativos(PontoEntrega entrega, Post post) {
        Marker marker;
        Polyline polyline;
        marker = markersMapPontoEntrega.get(entrega.getId());
        markersMap.remove(entrega.getId());
        pontosMap.remove(marker.getId());
        marker.remove();

        polyline = polylineMapEntreg.get(entrega.getId());
        polyline.remove();
    }

    public void onPontoEntregaChanged(PontoEntrega entrega, Post post, boolean creating) {
        /*if(post.isExcluido()) {
            //onPostDelete(post);
            return;
        }*/

        List<CharSequence> tipoDemanda = TipoDemanda.getNames();
        CharSequence tipo = tipoDemanda.get(entrega.getTipoDemanda());

        mPontoEntregas.remove(entrega);
        mPontoEntregas.add(entrega);
        Marker marker;
        marker = mMap.addMarker(
                new MarkerOptions()
                        .icon(getPinPonto(entrega))
                        .position(new LatLng(entrega.getX(), entrega.getY()))
                        .title(tipo.toString()));

        markersMapPontoEntrega.put(entrega.getId(), marker);
        pontosMap.put(marker.getId(), entrega);

        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(entrega.getX(), entrega.getY()), post.getLocation().toLatLng())
                .width(4)
                .color(Color.argb(90, 95, 187, 0)));


        polylineMapEntreg.put(entrega.getId(), line);
    }


    public void onPontoEntregaDelete(PontoEntrega entrega) {
        Marker marker;
        marker = markersMap.get(entrega.getId());
        markersMap.remove(entrega.getId());
        postsMap.remove(marker.getId());
        mPosts.remove(entrega);
        marker.remove();
    }

    @Override
    public void onAnotacaoChanged(Anotacao anotacao, boolean creating, boolean deletar) {
        Marker marker;
        if (!creating) {
            marker = markersMapAnatocao.get(anotacao.getId());
            markersMapAnatocao.remove(anotacao.getId());
            anotacaoMap.remove(marker.getId());
            marker.remove();
        }
        if (!deletar) {
            marker = mMap.addMarker(
                    new MarkerOptions()
                            .icon(getPinAnotacao(anotacao))
                            .position(new LatLng(anotacao.getX(), anotacao.getY()))
                            .title(anotacao.getAnotacao()));
            markersMapAnatocao.put(anotacao.getId(), marker);
            anotacaoMap.put(marker.getId(), anotacao);
        }
    }

    @Override
    public void onAnotacaoUpdateChaged(Anotacao anotacao) {
        AnotacaoDialogFragment.newInstance(MapsPostsActivity.this, anotacao)
                .show(getSupportFragmentManager(), "anotacaoActions");
    }

    @Override
    public void onAnotacaoDelete(final Anotacao anotacao) {
        // final boolean apagar = !anotacao.isExcluido();
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        alertDialog.setTitle("Apagar poste");
        alertDialog.setMessage("Tem certeza?");
        alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Location l = getLastLocation();
                //anotacao.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis()/1000));
                //post.setUpdate(true);

                /*final ProgressDialog alertDialog2 = new ProgressDialog(getBaseContext(), R.style.AlertDialogTheme);
                alertDialog2.setTitle(getString(R.string.dialog_deleting_title));
                alertDialog2.setCancelable(false);
                alertDialog2.show();
                dialog.dismiss();*/


                boolean workingOffline = FileUtils.serviceOrderFileExists(anotacao.getOrderId());
                if (workingOffline) {

                    try {
                        //post.setExcluido(apagar);
                        //OfflineDownloadResponse download = FileUtils.retrieve(anotacao.getOrderId());
                        OfflineDownloadResponse download = null;
                        if (DownloadOrderOffline.getResponse() == null) {
                            DownloadOrderOffline.setResponse(FileUtils.retrieve(anotacao.getOrderId()));
                            download = DownloadOrderOffline.getResponse();
                        } else {
                            download = DownloadOrderOffline.getResponse();
                        }
                        List<Anotacao> anotacoes = download.getAnotacaoList();

                        Iterator<Anotacao> iterator = anotacoes.iterator();

                        while (iterator.hasNext()) {
                            Anotacao a = iterator.next();
                            //if(p.equals(post)) {
                            if (a.getId() == anotacao.getId()) {
                                LogUtils.log("FOUND " + a.getId());
                                //iterator.remove();
                                if (a.getId() < 0) {
                                    iterator.remove();
                                } else {
                                    a.setExcluido(true);
                                    a.setUpdate(true);
                                }

                                break;
                            }
                        }
                        //posts.add(post);

                        download.setAnotacaoList(anotacoes);

                        if (FileUtils.saveOfflineDownload(download)) {
                            Toast.makeText(getApplicationContext(),
                                    "Operação realizada com sucesso!",
                                    Toast.LENGTH_LONG).show();
                            padfAnotacao.dismiss();
                            //onPostDelete(post);
                            onAnotacaoChanged(anotacao, false, true);

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Houve algum problema salvando as alterações, por favor tente novamente ou informe o administrador.",
                                    Toast.LENGTH_LONG).show();
                        }

                        dialog.dismiss();

                    } catch (IOException e) {
                        LogUtils.error(this, e);
                        dialog.dismiss();

                        Toast.makeText(getApplicationContext(),
                                "Houve algum problema salvando as alterações, por favor tente novamente ou informe o administrador.",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        alertDialog.setNegativeButton("NAO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alertDialog.show();

    }

    @Override
    public void onPontoEntegasChanged(PontoEntrega pontoEntrega, boolean creating, Post post, boolean deletarPlyline, boolean podeTracar) {
                        /*if(post.isExcluido()) {
            //onPostDelete(post);
            return;
        }*/
     /*   mPontoEntregas.remove(pontoEntrega);
        mPontoEntregas.add(pontoEntrega);*/

        Marker marker;

        if (!creating) {
            marker = markersMapPontoEntrega.get(pontoEntrega.getId());
            markersMapPontoEntrega.remove(pontoEntrega.getId());
            pontosMap.remove(marker.getId());
            marker.remove();
        }

        List<CharSequence> tipoDemandas = TipoDemanda.getNames();
        CharSequence tipoDemanda = tipoDemandas.get(pontoEntrega.getTipoDemanda());

        /*if(pontoEntrega.isExcluido()){
            marker = markersMapPontoEntrega.get(pontoEntrega.getId());
            markersMapPontoEntrega.remove(pontoEntrega.getId());
            pontosMap.remove(marker.getId());
            marker.remove();
        }*/

        marker = mMap.addMarker(
                new MarkerOptions()
                        .icon(getPinPonto(pontoEntrega))
                        .position(new LatLng(pontoEntrega.getX(), pontoEntrega.getY()))
                        .title("N°" + pontoEntrega.getNumero_local() + " / " + tipoDemanda.toString()));
        markersMapPontoEntrega.put(pontoEntrega.getId(), marker);
        pontosMap.put(marker.getId(), pontoEntrega);

        List<PontoEntrega> pontoEntregas = new ArrayList<>();
        pontoEntregas.add(pontoEntrega);
        if (podeTracar) {
            trocapositionVaoPontoPoste(post, pontoEntregas);
        }


     /*   if (deletarPlyline) {
            Polyline polyline = polylineMapEntreg.get(pontoEntrega.getId());
            Polyline polyline2 = polylineMapPoste.get(post.getId());
            if (polyline != null) {
                polyline.remove();
            }
            if (polyline2 != null) {
                polyline.remove();
            }
        }*/

      /*  if (post != null) {
            if (post.getLocation() != null) {
                Polyline line = mMap.addPolyline(new PolylineOptions()
                        .add(new LatLng(pontoEntrega.getX(), pontoEntrega.getY()), post.getLocation().toLatLng())
                        .width(4)
                        .color(Color.parseColor("#ff3a3a")));
                polylineMapEntreg.put(pontoEntrega.getId(), line);
                polylineMapPoste.put(post.getId(), line);
            }
        }*/

       /*if(vaosDeletar != null){
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(vaosDeletar.getX1(), vaosDeletar.getY1()),new LatLng(vaosDeletar.getX2(), vaosDeletar.getX2()))
                    .width(4)
                    .color(Color.parseColor("#f0ff23")));
            line.remove();
        }9*/
    }

    @Override
    public void onPostDelete(Post post) {
        Marker marker;
        marker = markersMap.get(post.getId());
        markersMap.remove(post.getId());
        postsMap.remove(marker.getId());
        mPosts.remove(post);
        marker.remove();
    }

    @Override
    public List<PontoEntrega> getPontoEntregasList() {
        return mPontoEntregas;
    }

    @Override
    public List<Post> getPostsList() {
        return mPosts;
    }

    @Override
    public List<LatLng> getOrderPoints() {
        return mOrderPoints;
    }

    @NonNull
    public static BitmapDescriptor getPin(Post post) {
        int drawable = post.isExcluido() ? R.drawable.pin_red :
                post.getPostType() > 7 && post.getPostType() < 11 ? R.drawable.pin_yellow :
                        post.getPostType() > 14 ? R.drawable.pin_roxo :
                                post.isClosed() ? R.drawable.pin_green :
                                        R.drawable.pin_cyan;

        return BitmapDescriptorFactory.fromResource(drawable);
    }

    public static BitmapDescriptor getPinAnotacao(Anotacao anotacao) {
        int drawable = R.mipmap.noteicon3;
        return BitmapDescriptorFactory.fromResource(drawable);
    }


    @NonNull
    public static BitmapDescriptor getPinPonto(PontoEntrega pontoEntrega) {
        int drawable = R.mipmap.icon_dem;

//        if (pontoEntrega.isExcluido()) {
//            drawable = R.mipmap.ponto_location_delete;
//            return BitmapDescriptorFactory.fromResource(drawable);
//        }
//
//        if (pontoEntrega.getTipoDemanda() == 0) {
//            drawable = R.mipmap.duvida;
//        }
//        if (pontoEntrega.getTipoDemanda() == 1) {
//            drawable = R.mipmap.predio;
//        }
//        if (pontoEntrega.getTipoDemanda() == 2) {
//            drawable = R.mipmap.comercio;
//        }
//        if (pontoEntrega.getTipoDemanda() == 3) {
//            drawable = R.mipmap.terreno;
//        }
//        if (pontoEntrega.getTipoDemanda() == 4) {
//            drawable = R.mipmap.casa;
//        }
        return BitmapDescriptorFactory.fromResource(drawable);
    }

//    @NonNull
//    public static BitmapDescriptor getPinPonto(PontoEntrega pontoEntrega) {
//        int drawable = R.drawable.pin_red;
//
//        if (pontoEntrega.isExcluido()) {
//            drawable = R.mipmap.ponto_location_delete;
//            return BitmapDescriptorFactory.fromResource(drawable);
//        }
//
//        if (pontoEntrega.getTipoDemanda() == 0) {
//            drawable = R.mipmap.duvida;
//        }
//        if (pontoEntrega.getTipoDemanda() == 1) {
//            drawable = R.mipmap.predio;
//        }
//        if (pontoEntrega.getTipoDemanda() == 2) {
//            drawable = R.mipmap.comercio;
//        }
//        if (pontoEntrega.getTipoDemanda() == 3) {
//            drawable = R.mipmap.terreno;
//        }
//        if (pontoEntrega.getTipoDemanda() == 4) {
//            drawable = R.mipmap.casa;
//        }
//        return BitmapDescriptorFactory.fromResource(drawable);
//    }

    public static BitmapDescriptor getPinPontoEntrega(PontoEntrega pontoEntrega) {
        int drawable = R.drawable.pin_red;

        if (pontoEntrega.getTipoDemanda() == 0) {
            drawable = R.mipmap.duvida;
        }
        if (pontoEntrega.getTipoDemanda() == 1) {
            drawable = R.mipmap.predio;
        }
        if (pontoEntrega.getTipoDemanda() == 2) {
            drawable = R.mipmap.comercio;
        }
        if (pontoEntrega.getTipoDemanda() == 3) {
            drawable = R.mipmap.terreno;
        }
        if (pontoEntrega.getTipoDemanda() == 4) {
            drawable = R.mipmap.casa;
        }
                /*post.getClasseAtendimento()  7 &&  post.getPostType() < 11 ? R.drawable.pin_yellow :
                        post.getPostType() > 14 ? R.drawable.pin_roxo :
                                post.isClosed() ? R.drawable.pin_green :
                                        R.drawable.pin_cyan;*/

        return BitmapDescriptorFactory.fromResource(drawable);
    }

    @Override
    public void onAddLightingButtonClicked(Post post) {
        LightingEditDialogFragment.newInstance(this, null, post, mOrderDetails.getId())
                .show(getSupportFragmentManager(), "lightingEdit");
    }

    @Override
    public void onEditLightingItemClicked(Lighting lighting) {
        LightingEditDialogFragment.newInstance(this, lighting, null, mOrderDetails.getId())
                .show(getSupportFragmentManager(), "lightingEdit");
    }

    @Override
    public void onDeleteItemClicked(final Post post, final Lighting lighting) {
        Location l = getLastLocation();
        lighting.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis() / 1000));
        lighting.setUpdate(true);
        boolean answer;
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        alertDialog.setTitle("Deseja apagar o equipamento?");
        alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                boolean workingOffline = FileUtils.serviceOrderFileExists(post.getOrderId());
                if (workingOffline) {

                    try {
                        OfflineDownloadResponse download = FileUtils.retrieve(post.getOrderId());
                        List<Lighting> ips = download.getLightingList();

                        Iterator<Lighting> iterator = ips.iterator();

                        while (iterator.hasNext()) {
                            Lighting l = iterator.next();
                            if (l.getId() == lighting.getId()) {
                                iterator.remove();
                                break;
                            }
                        }
                        lighting.setExcluido(true);
                        ips.add(lighting);
                        download.setLightingList(ips);
                        lightingList.remove(lighting);
                        lightingListDialogFragment.notifyDataSetChanged();

                        if (FileUtils.saveOfflineDownload(download)) {
                            Toast.makeText(getApplicationContext(),
                                    "Equipamento apagado com sucesso",
                                    Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Erro apagando equipamentos",
                                    Toast.LENGTH_LONG).show();
                        }

                    } catch (IOException e) {
                        LogUtils.error(this, e);

                        Toast.makeText(getApplicationContext(),
                                "Erro apagando equipamentos",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    VisiumApiCallback<Lighting> callback =
                            new VisiumApiCallback<Lighting>() {

                                @Override
                                public void callback(Lighting lighting, ErrorResponse e) {
                                    //alertDialog.dismiss();
                                    if (e == null) {
                                        lightingList.remove(lighting);
                                        if (lightingListDialogFragment != null) {
                                            lightingListDialogFragment.notifyDataSetChanged();
                                        }
                                    } else if (e != null && e.isCustomized()) {
                                        Toast.makeText(MapsPostsActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(MapsPostsActivity.this,
                                                "Erro apagando equipamento",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            };

                    LightingController.get().delete(getApplicationContext(), lighting, callback);
                }
            }
        });

        alertDialog.setNegativeButton("NAO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onLightingItemChanged(Lighting lighting) {
        lightingList.remove(lighting);
        lightingList.add(0, lighting);

        if (lightingListDialogFragment != null) {
            lightingListDialogFragment.notifyDataSetChanged();
        }
    }

    @Override
    public void onEquipmentItemChanged(EquipmentType type, Object o) {
        switch (type) {
            case PONTO_DE_ENTREGA: {
                pontoEntregaList.remove((PontoEntrega) o);
                pontoEntregaList.add(0, (PontoEntrega) o);

                if (pontoEntregaListDialogFragment != null) {
                    pontoEntregaListDialogFragment.notifyDataSetChanged();
                }
                break;
            }
        }
    }

    @Override
    public void onAddItemButtonClicked(EquipmentType type, Post post, Object o) {

        switch (type) {
            case PONTO_DE_ENTREGA:
                PontoEntregaEditDialogFragment.newInstance(this, (LatLng) o, post, mOrderDetails.getId())
                        .show(getSupportFragmentManager(), "pontoEntregaEdit");
                break;

            case AFLORAMENTO:
                AfloramentoEditDialogFragment.newInstance(this, (LatLng) o, post, mOrderDetails.getId())
                        .show(getSupportFragmentManager(), "afloramentoEdit");
                break;
        }
    }

    @Override
    public void onEditItemClicked(EquipmentType type, Post post, Object o) {

        switch (type) {

            case PONTO_DE_ENTREGA:
                PontoEntregaEditDialogFragment.newInstance(this, (PontoEntrega) o, post, mOrderDetails.getId())
                        .show(getSupportFragmentManager(), "pontoEntregaEdit");
                break;

            case MEDIDOR:
                PontoEntregaMedidorEditDialogFragment.newInstance(this, (PontoEntrega) o, post, mOrderDetails.getId())
                        .show(getSupportFragmentManager(), "medidorEdit");
                break;

        }
    }

    @Override
    public void onDeleteItemClicked(EquipmentType type, final Post post, final Object o) {
        switch (type) {
            case PONTO_DE_ENTREGA:
                Location l = getLastLocation();
                ((PontoEntrega) o).setUpdate(true);
                //((PontoEntrega)o).setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis()/1000));
                boolean answer;
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
                alertDialog.setTitle("Deseja apagar o equipamento?");
                alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {
                            OfflineDownloadResponse download = FileUtils.retrieve(post.getOrderId());
                            List<PontoEntrega> pontoEntregas = download.getPontoEntregaList();

                            Iterator<PontoEntrega> iterator = pontoEntregas.iterator();

                            while (iterator.hasNext()) {
                                PontoEntrega pe = iterator.next();
                                if (pe.getId() == ((PontoEntrega) o).getId()) {
                                    iterator.remove();
                                    break;
                                }
                            }

                            if (((PontoEntrega) o).getId() < 0) {
                                pontoEntregas.remove((PontoEntrega) o);

                            } else {
                                ((PontoEntrega) o).setExcluido(true);
                                pontoEntregas.add(((PontoEntrega) o));
                            }

                            download.setPontoEntregaList(pontoEntregas);
                            pontoEntregaList.remove(((PontoEntrega) o));
                            pontoEntregaListDialogFragment.notifyDataSetChanged();

                            if (FileUtils.saveOfflineDownload(download)) {


                                /*Set<Polyline> polylineSet = polylineList.keySet();
                                for(Polyline polyline : polylineSet){
                                    polyline.getPoints();
                                }*/


                                Toast.makeText(getApplicationContext(),
                                        "Equipamento apagado com sucesso",
                                        Toast.LENGTH_LONG).show();

                                if (((PontoEntrega) o).getId() < 0) {
                                    deletePolylineAndMakersNegativos((PontoEntrega) o, post);
                                } else {
                                    onPontoEntregaChanged((PontoEntrega) o, post, false);
                                }

                            } else {
                                Toast.makeText(getApplicationContext(),
                                        "Erro apagando equipamentos",
                                        Toast.LENGTH_LONG).show();
                            }

                        } catch (IOException e) {
                            LogUtils.error(this, e);

                            Toast.makeText(getApplicationContext(),
                                    "Erro apagando equipamentos",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

                alertDialog.setNegativeButton("NAO", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                alertDialog.show();
                break;
        }
    }

    @Override
    public void onListItemEquipament(EquipmentType type, Post post, Object o) {
        switch (type) {
            case MEDIDOR: {
                MedidorListDialogFragment.newInstance(this, (PontoEntrega) o, post)
                        .show(getSupportFragmentManager(), "pontoEntregaEdit");
            }
        }
    }

    @Override
    public void onEditDemandaButtonClicked(PontoEntrega pontoEntrega) {
      /*  PostPontoEditDialogFragment.newInstance(MapsPostsActivity.this, this, pontoEntrega)
                .show(getSupportFragmentManager(), "demandaEdit");*/
        EditDemandaFragment.newInstance(this, pontoEntrega).show(getSupportFragmentManager(), "demandaEdit");

    }


    @Override
    public void onToggleDeleteDemandaButtonClicked(final PontoEntrega pontoEntrega) {
        final boolean apagar = !pontoEntrega.isExcluido();
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        alertDialog.setTitle(apagar ? "Apagar poste" : "Restaurar poste");
        alertDialog.setMessage("Tem certeza?");
        alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Location l = getLastLocation();
                //pontoEntrega.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis() / 1000));
                pontoEntrega.setX_atualizacao(l.getLatitude());
                pontoEntrega.setY_atualizacao(l.getLongitude());
                pontoEntrega.setUpdate(true);

                /*final ProgressDialog alertDialog2 = new ProgressDialog(getBaseContext(), R.style.AlertDialogTheme);
                alertDialog2.setTitle(getString(R.string.dialog_deleting_title));
                alertDialog2.setCancelable(false);
                alertDialog2.show();
                dialog.dismiss();*/


                boolean workingOffline = FileUtils.serviceOrderFileExists(pontoEntrega.getOrderId());
                if (workingOffline) {

                    try {
                        pontoEntrega.setExcluido(apagar);
                        //  OfflineDownloadResponse download = FileUtils.retrieve(pontoEntrega.getOrderId());

                        OfflineDownloadResponse download = null;
                        if (DownloadOrderOffline.getResponse() == null) {
                            DownloadOrderOffline.setResponse(FileUtils.retrieve(pontoEntrega.getOrderId()));
                            download = DownloadOrderOffline.getResponse();
                        } else {
                            download = DownloadOrderOffline.getResponse();
                        }

                        List<PontoEntrega> pontoEntregas = download.getPontoEntregaList();
                        List<VaosPontoPoste> vaosPontoPostes = download.getVaosPontoPosteList();

                        Iterator<PontoEntrega> iterator = pontoEntregas.iterator();


                        while (iterator.hasNext()) {
                            PontoEntrega p = iterator.next();
                            //if(p.equals(post)) {
                            if (p.getId() == pontoEntrega.getId()) {
                                LogUtils.log("FOUND " + p.getId());
                                iterator.remove();
                                break;
                            }
                        }
                        if (pontoEntrega.getId() > 0) {
                            pontoEntregas.add(pontoEntrega);
                        }

                        download.setPontoEntregaList(pontoEntregas);

                        if (FileUtils.saveOfflineDownload(download)) {
                            Toast.makeText(getApplicationContext(),
                                    "Operação realizada com sucesso!",
                                    Toast.LENGTH_LONG).show();
                            padfPponto.dismiss();
                            //onPostDelete(post);
                            onPontoEntegasChanged(pontoEntrega, false, null, false, false);

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Houve algum problema salvando as alterações, por favor tente novamente ou informe o administrador.",
                                    Toast.LENGTH_LONG).show();
                        }

                        dialog.dismiss();

                    } catch (IOException e) {
                        LogUtils.error(this, e);
                        dialog.dismiss();

                        Toast.makeText(getApplicationContext(),
                                "Houve algum problema salvando as alterações, por favor tente novamente ou informe o administrador.",
                                Toast.LENGTH_LONG).show();
                    }

                } else {
                   /* //FIXME: não foi implementado restauração de postes ONLINE
                    VisiumApiCallback<Post> callback =
                            new VisiumApiCallback<Post>() {

                                @Override
                                public void callback(Post postReturn, ErrorResponse e) {

                                    //alertDialog2.dismiss();

                                    if (e == null) {
                                        Toast.makeText(getApplicationContext(),
                                                "Operação realizada com sucesso!",
                                                Toast.LENGTH_LONG).show();
                                        padf.dismiss();
                                        //onPostDelete(post);
                                        onPostChanged(post, false);
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                "Houve algum problema salvando as alterações, por favor tente novamente ou informe o administrador.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            };

                    PostController.get().delete(getApplicationContext(), post, callback);*/
                }
            }
        });

        alertDialog.setNegativeButton("NAO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onEditLocalDemandaButtonClicked(PontoEntrega pontoEntrega) {
        pontoEntregaObj = pontoEntrega;
        demandaTimeEdit = true;
        posteTime = false;
        Snackbar.make(SnakemapFragment, "Escolha o Local novo", Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onDeletarDemandaStrand(final DemandaStrand strand, final Polyline polyline) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogTheme);
        alertDialog.setTitle("Apagar Strand");
        alertDialog.setMessage("Tem certeza?");
        alertDialog.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Location l = getLastLocation();
                //anotacao.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis()/1000));
                //post.setUpdate(true);

                /*final ProgressDialog alertDialog2 = new ProgressDialog(getBaseContext(), R.style.AlertDialogTheme);
                alertDialog2.setTitle(getString(R.string.dialog_deleting_title));
                alertDialog2.setCancelable(false);
                alertDialog2.show();
                dialog.dismiss();*/


                boolean workingOffline = FileUtils.serviceOrderFileExists(strand.getOrderId());
                if (workingOffline) {

                    try {
                        //post.setExcluido(apagar);
                        //OfflineDownloadResponse download = FileUtils.retrieve(strand.getOrderId());
                        OfflineDownloadResponse download = null;
                        if (DownloadOrderOffline.getResponse() == null) {
                            DownloadOrderOffline.setResponse(FileUtils.retrieve(strand.getOrderId()));
                            download = DownloadOrderOffline.getResponse();
                        } else {
                            download = DownloadOrderOffline.getResponse();
                        }
                        List<DemandaStrand> demandaStrands = download.getDemandaStrandList();

                        Iterator<DemandaStrand> iterator = demandaStrands.iterator();

                        while (iterator.hasNext()) {
                            DemandaStrand a = iterator.next();
                            //if(p.equals(post)) {
                            if (a.getId() == strand.getId()) {
                                LogUtils.log("FOUND " + a.getId());
                                if (a.getId() < 0) {
                                    iterator.remove();
                                } else {
                                    a.setExcluido(true);
                                    a.setUpdate(true);
                                }
                                break;
                            }
                        }
                        //posts.add(post);
                        download.setDemandaStrandList(demandaStrands);

                        if (FileUtils.saveOfflineDownload(download)) {
                            Toast.makeText(getApplicationContext(),
                                    "Operação realizada com sucesso!",
                                    Toast.LENGTH_LONG).show();
                            padfStrand.dismiss();
                            //onPostDelete(post);
                            onDemandaStradChanged(null, false, polyline);

                        } else {
                            Toast.makeText(getApplicationContext(),
                                    "Houve algum problema salvando as alterações, por favor tente novamente ou informe o administrador.",
                                    Toast.LENGTH_LONG).show();
                        }

                        dialog.dismiss();

                    } catch (IOException e) {
                        LogUtils.error(this, e);
                        dialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                "Houve algum problema salvando as alterações, por favor tente novamente ou informe o administrador.",
                                Toast.LENGTH_LONG).show();
                    }
                }

            }
        });

        alertDialog.setNegativeButton("NAO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onDemandaStradChanged(DemandaStrand strand, boolean mCreating, Polyline mPolyline) {
        Polyline polyline;

        if (mPolyline != null) {
            mPolyline.remove();
        }
        if (strand != null) {
            Polyline line = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(strand.getX1(), strand.getY1()), new LatLng(strand.getX2(), strand.getY2()))
                    .width(10)
                    .color(Color.parseColor("#ff9900")));
            line.setClickable(true);
            polylineStrandId.put(strand.getId(), line);
            polylineStrandMapa.put(line, strand);
        }


        //demandaStrandMap.put(polyline.getId(), strand);

        /*if(pontoEntrega.isExcluido()){
            marker = markersMapPontoEntrega.get(pontoEntrega.getId());
            markersMapPontoEntrega.remove(pontoEntrega.getId());
            pontosMap.remove(marker.getId());
            marker.remove();
        }*/
    }

    @Override
    public void onEditDemandaStrand(DemandaStrand strand, Polyline polyline) {
        Intent strandIntent = new Intent(MapsPostsActivity.this, MapsStrandPickLocationActivity.class);
        strandIntent.putExtra("orderId", mOrderDetails.getId());
        strandIntent.putExtra("tipo", "strand");
        strandIntent.putExtra("strand", strand);
        this.mDemandaStrand = strand;
        this.mPolyline = polyline;
        polyline.setColor(Color.parseColor("#000000"));

        strandIntent.putExtra(MapsPostsActivity.SERVICE_ORDER_POINTS, new ArrayList<>(mOrderPoints));
        startActivityForResult(strandIntent, PICKUP_LOCATION_STRAND);
    }

    public void tirarFotoPoste(Post post) {

        PhtotosFragment.newInstance(post)
                .show(getSupportFragmentManager(), "postEdit");
    }

    public void tirarFotoDemanda(PontoEntrega pontoEntrega) {
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        caminhoFoto = getExternalFilesDir(null) + "/" + pontoEntrega.getId() + System.currentTimeMillis() + ".jpg";
        File arquivoFoto = new File(caminhoFoto);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
        startActivityForResult(intentCamera, RESULT_CAMERA);
    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(this, R.style.AlertDialogTheme);

        alertDialog.setTitle("Deseja Sair?");
        alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }

        }).setNegativeButton("NAO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private synchronized void callConnection() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addOnConnectionFailedListener(this).addConnectionCallbacks(this).addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    private void startLocationUpdate() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        initLocationRequest();
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MapsPostsActivity.this);
    }

    private void stopLocationUpdate(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, MapsPostsActivity.this);
    }

    private void initLocationRequest(){
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(3000);
        mLocationRequest.setFastestInterval(2000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(Bundle bundle) {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(location != null){
          //  Toast.makeText(this, "Lat: "+ location.getLatitude()+" Lon: "+ location.getLongitude(), Toast.LENGTH_SHORT).show();

            SharedPreferences  preferences = getSharedPreferences("LocalUsuario", Context.MODE_PRIVATE);
            String usuario = preferences.getString("userLocal", null);
            if(usuario != null){

                UserController.get().LocalAtual(LocalUsuario.getIdUser(), location.getLatitude(), location.getLongitude(), 1, LocalUsuario.getMensagem(), new VisiumApiCallback<LocalUsuario>() {
                    @Override
                    public void callback(LocalUsuario localUsuario, ErrorResponse e) {
                        //    Toast.makeText(MapsPostsActivity.this, "olaa", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
        startLocationUpdate();
    }

    @Override
    public void onConnectionSuspended(int i) {
        super.onConnectionSuspended(i);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        super.onConnectionFailed(connectionResult);
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);
        if(location != null){

            lastLocaltion = location;
         //   Toast.makeText(this, "Lat: "+ location.getLatitude()+" Lon: "+ location.getLongitude(), Toast.LENGTH_SHORT).show();
                UserController.get().LocalAtual(LocalUsuario.getIdUser(), location.getLatitude(), location.getLongitude(), LocalUsuario.getPanico(), LocalUsuario.getMensagem(), new VisiumApiCallback<LocalUsuario>() {
                    @Override
                    public void callback(LocalUsuario localUsuario, ErrorResponse e) {
                    }
                });
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(mGoogleApiClient != null && mGoogleApiClient.isConnected()){
            startLocationUpdate();
        }
        if(panico != null){
            if(LocalUsuario.getPanico() == 0){
                panico.setBackgroundColor(Color.WHITE);
                /*if(tooltip != null) {
                    tooltip.dismiss();
                }*/
            }else{
               /* if(tooltip != null) {
                    tooltip.show();
                }*/
                panico.setBackgroundColor(Color.parseColor("#efc300"));
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        if(mGoogleApiClient != null){
            stopLocationUpdate();
        }
    }
}