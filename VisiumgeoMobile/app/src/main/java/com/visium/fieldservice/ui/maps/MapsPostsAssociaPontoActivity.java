package com.visium.fieldservice.ui.maps;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.controller.AfloramentoController;
import com.visium.fieldservice.controller.LightingController;
import com.visium.fieldservice.controller.PontoEntregaController;
import com.visium.fieldservice.controller.PostController;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.Lighting;
import com.visium.fieldservice.entity.PontoAtualizacao;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.PontoEntregaLocation;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.PostLocation;
import com.visium.fieldservice.entity.PostMedidoresPosicao;
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
import com.visium.fieldservice.ui.dialog.LightingEditDialogFragment;
import com.visium.fieldservice.ui.dialog.LightingEditDialogListener;
import com.visium.fieldservice.ui.dialog.LightingListDialogFragment;
import com.visium.fieldservice.ui.dialog.LightingListDialogListener;
import com.visium.fieldservice.ui.dialog.MedidorListDialogFragment;
import com.visium.fieldservice.ui.dialog.PontoEntregaEditDialogFragment;
import com.visium.fieldservice.ui.dialog.PontoEntregaEditDialogListener;
import com.visium.fieldservice.ui.dialog.PontoEntregaListDialogFragment;
import com.visium.fieldservice.ui.dialog.PontoEntregaMedidorEditDialogFragment;
import com.visium.fieldservice.ui.dialog.PostActionsDialogFragment;
import com.visium.fieldservice.ui.dialog.PostActionsDialogListener;
import com.visium.fieldservice.ui.dialog.PostEditDialogFragment;
import com.visium.fieldservice.ui.dialog.PostEditDialogListener;
import com.visium.fieldservice.ui.dialog.PostPontoEditDialogFragment;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.GeoUtils;
import com.visium.fieldservice.util.LogUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapsPostsAssociaPontoActivity extends MapsAccuracy
        implements PostActionsDialogListener, PostEditDialogListener, MapLocationListener {

    public static final int PICKUP_LOCATION = 2;
    public static final int PONTO_ENTREGA_PICKUP_LOCATION = 300;
    public static final String SERVICE_ORDER_POINTS = "SERVICE_ORDER_POINTS";
    public static final String SERVICE_ORDER_DETAILS = "SERVICE_ORDER_DETAILS";
    public static final String POST_LIST = "POST_LIST";
    public static final String VAO_PRIMARIO_LIST = "VAO_PRIMARIO_LIST";
    public static final String PONTO_ENTREGA = "PONTO_ENTREGA";

    private PostActionsDialogFragment padf;

    private List<LatLng> mOrderPoints;
    private ServiceOrderDetails mOrderDetails;
    private List<Post> mPosts;
    private List<VaoPrimario> mVaosPrimarios;
    private List<PontoEntrega> mPontoEntregas;
    private FloatingActionButton floatingActionButton;
    private Map<String, Post> postsMap = new HashMap<>();
    private Map<String, PontoEntrega> pontosMap = new HashMap<>();
    private Map<Long, Marker> markersMap = new HashMap<>();
    private Map<Long, Marker> markersMapPontoEntrega = new HashMap<>();
    private Map<Long, Polyline> polylineMapEntreg = new HashMap<>();
    private LatLng creatingPostLatLng;
    private List<LatLng> points;
    private LightingListDialogFragment lightingListDialogFragment;
    private AfloramentoListDialogFragment afloramentoListDialogFragment;
    private List<Lighting> lightingList;
    private List<Afloramento> afloramentoList;
    private List<PontoEntrega> pontoEntregaList;
    private PontoEntregaListDialogFragment pontoEntregaListDialogFragment;
    private HashMap<Polyline, Integer> polylineList;
    private FloatingActionButton emenda;
    private List<VaosPontoPoste> mVaosPontoPostes;
    private LatLng latLngPonto;
    private String nomeDemanda;
    private String numeroDemanda;

    private String tipo;

  //  HashMap<Polyline, PontoEntrega> polylinePontoEntregaHashMap;

    private int highestPostNumber = 0;

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
        setContentView(R.layout.activity_map_posts_associa_ponto);

      //  polylinePontoEntregaHashMap = new HashMap<>();

        Intent intent = getIntent();
        mOrderPoints = (List<LatLng>) intent.getSerializableExtra(SERVICE_ORDER_POINTS);
        mOrderDetails = (ServiceOrderDetails) intent.getSerializableExtra(SERVICE_ORDER_DETAILS);

        nomeDemanda = intent.getStringExtra("tipoDemanda");
        numeroDemanda = intent.getStringExtra("numeroDemanda");

        latLngPonto = (LatLng) intent.getExtras().get("latLongPontoEntrega");

        try {
            OfflineDownloadResponse downloaded = FileUtils.retrieve(mOrderDetails.getId());
            mPosts = downloaded.getPosts();
          //  mVaosPrimarios = downloaded.getVaoPrimarioList();
            mPontoEntregas = downloaded.getPontoEntregaList();
            mVaosPontoPostes = downloaded.getVaosPontoPosteList();
        } catch (IOException e) {
            e.printStackTrace();
        }


        /*mPosts = (List<Post>) intent.getSerializableExtra(POST_LIST);
        mVaosPrimarios = (List<VaoPrimario>) intent.getSerializableExtra(VAO_PRIMARIO_LIST);
        mPontoEntregas = (List<PontoEntrega>) intent.getSerializableExtra(PONTO_ENTREGA);
           */
        setupToolbar(R.drawable.logo, mOrderDetails.getNumber());
        forceStatusBarColor();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Bundle bundle = data.getExtras();
        tipo = bundle.getString("tipo");

        if (requestCode == PICKUP_LOCATION) {

            if (data != null && data.hasExtra(MapsPickLocationActivity.PICK_LOCATION)) {
                creatingPostLatLng = data.getParcelableExtra(MapsPickLocationActivity.PICK_LOCATION);
            } else {
                Toast.makeText(MapsPostsAssociaPontoActivity.this,
                        R.string.maps_posts_new_location_error,
                        Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == PONTO_ENTREGA_PICKUP_LOCATION) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }

        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

   /* @Override
    protected void onPostResume() {
        super.onPostResume();

        if (creatingPostLatLng != null) {

            if(tipo.equals("poste")){
                Post post = new Post();
                post.setOrderId(mOrderDetails.getId());
                post.setLocation(new PostLocation(creatingPostLatLng.latitude,
                        creatingPostLatLng.longitude));

                PostEditDialogFragment.newInstance(MapsPostsAssociaPontoActivity.this, this, post,
                        new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                        .show(getSupportFragmentManager(), "postEdit");
            }
            if(tipo.equals("emenda")){

                PontoEntrega pontoEntrega = new PontoEntrega();
                pontoEntrega.setOrderId(mOrderDetails.getId());
                pontoEntrega.setLocation(new PontoEntregaLocation(creatingPostLatLng.latitude,
                        creatingPostLatLng.longitude));

                PostPontoEditDialogFragment.newInstance(MapsPostsAssociaPontoActivity.this, this, pontoEntrega,
                        new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()))
                        .show(getSupportFragmentManager(), "PontoEntregaEdit");
            }
            creatingPostLatLng = null;
        }
    }*/

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

        moveToCurrentLocation(latLngPonto);

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
//Todo
       /* mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                mMap.animateCamera(CameraUpdateFactory
                        .newLatLngBounds(GeoUtils.createLatLngBounds(marker.getPosition()), 5));
                return false;
            }
        });*/

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                Post post = postsMap.get(marker.getId());
                if(post ==null){
                    return;
                }else {
                    SharedPreferences.Editor preferences = getSharedPreferences("poste", MODE_PRIVATE).edit();
                    preferences.putLong("id", post.getId());
                    preferences.putInt("tipo", post.getPostType());

                    double height = post.getHeight();
                    String altura = height+"";

                    preferences.putString("altura", altura);
                    preferences.putString("obs", post.getObservations());

                    preferences.putString("lat", post.getLocation().getLat()+"");
                    preferences.putString("lon", post.getLocation().getLon()+"");

                    preferences.commit();


                    finish();

                    padf = (PostActionsDialogFragment) PostActionsDialogFragment.newInstance(MapsPostsAssociaPontoActivity.this, post);
                    padf.show(getSupportFragmentManager(), "postActions");
                }
            }
        });

        points = new ArrayList<>(mPosts.size());

        Polygon polygon = mMap.addPolygon(
                getPolygonOptions(mOrderPoints));
        polygon.setClickable(false);
        //List<Post> listDelete = new ArrayList<>();
        for (Post post : mPosts) {
            int postN = post.getPostNumber();
            LogUtils.log("Loading Post Geocode / Number = " + String.valueOf(post.getGeoCode() + " / " + post.getPostNumber()));
            if(postN > highestPostNumber) {
                highestPostNumber = postN;
            }

            LatLng latLng = new LatLng(post.getLocation().getLat(), post.getLocation().getLon());
            points.add(latLng);
            Marker marker = mMap.addMarker(
                    new MarkerOptions()
                            .icon(getPin(post))
                            .position(latLng)
                            .title(String.valueOf("Selecionar "+post.getGeoCode() + " / " + post.getPostNumber())));

            markersMap.put(post.getId(), marker);
            postsMap.put(marker.getId(), post);

            List<PostMedidoresPosicao> peList = post.getPosicoesMedidores();
            if(peList !=null) {
                for(PostMedidoresPosicao pea : peList) {
                    List<LatLng> lineList = new ArrayList<LatLng>();
                    lineList.add(post.getLocation().toLatLng());
                    LatLng peaLatLng = new LatLng(pea.getX(), pea.getY());
                    lineList.add(peaLatLng);
                    Polyline line = mMap.addPolyline(getPontoEntregaPolylineOptions(lineList));
                }
            }

        }

        //  List<CharSequence> tipoDemanda = TipoDemanda.getNames();
        //  CharSequence nomeDemanda = tipoDemanda.get(mPontoEntregas)

        MarkerOptions options = new MarkerOptions();
        options.position(latLngPonto).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ponto_location))
                .title(numeroDemanda).snippet(nomeDemanda);

        mMap.addMarker(options).showInfoWindow();

               /* mMap.addMarker(new MarkerOptions().position(latLngPonto)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ponto_location))
                        .title(numeroDemanda).snippet(nomeDemanda)).showInfoWindow();*/

      /*  for(VaoPrimario vao : mVaosPrimarios) {
            if(vao.getX1() < 0) {
                LogUtils.log("Vao x1: "+vao.getX1()+"\nx2: "+vao.getX2());
                List<LatLng> lineList = new ArrayList<LatLng>();
                lineList.add(new LatLng(vao.getX1(), vao.getY1()));
                lineList.add(new LatLng(vao.getX2(), vao.getY2()));
                Polyline line = mMap.addPolyline(getVaoPolylineOptions(lineList));
                line.setClickable(true);
            }
        }*/

        if(!mVaosPontoPostes.isEmpty()){
            for(VaosPontoPoste vaosPontoPoste : mVaosPontoPostes){
                if(vaosPontoPoste.getX1() != null){

                    List<LatLng> lineList = new ArrayList<LatLng>();
                    lineList.add(new LatLng(vaosPontoPoste.getX1(), vaosPontoPoste.getY1()));
                    lineList.add(new LatLng(vaosPontoPoste.getX2(), vaosPontoPoste.getY2()));
                    Polyline line = mMap.addPolyline(getVaoPolylineOptions(lineList));
                    line.setWidth(4);
                    line.setColor((Color.parseColor("#f0ff23")));

                    line.setClickable(true);
                }
            }
        }

        for(PontoEntrega pontoEntrega : mPontoEntregas){

            //LatLng peaLatLng = new LatLng(pontoEntrega.getX(), pontoEntrega.getY());
            LatLng peaLatLng = new LatLng(pontoEntrega.getX(), pontoEntrega.getY());

            List<CharSequence> tipoDemanda = TipoDemanda.getNames();
            String tipoDemandasss = (String) tipoDemanda.get(pontoEntrega.getTipoDemanda());

            if(pontoEntrega.isExcluido()){
                mMap.addMarker(new MarkerOptions().position(peaLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.ponto_location_delete)).title("Nº"+pontoEntrega.getNumero_local()+" / "+tipoDemanda.toString()));
            }else {
                if (pontoEntrega.getTipoDemanda() == 0) {
                    mMap.addMarker(new MarkerOptions().position(peaLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.duvida)).title("Nº" + pontoEntrega.getNumero_local() + " / " + tipoDemanda.toString()).snippet(tipoDemanda.toString()));
                }
                if (pontoEntrega.getTipoDemanda() == 1) {
                    mMap.addMarker(new MarkerOptions().position(peaLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.predio)).title("Nº" + pontoEntrega.getNumero_local() + " / " + tipoDemanda.toString()).snippet(tipoDemanda.toString()));
                }
                if (pontoEntrega.getTipoDemanda() == 2) {
                    mMap.addMarker(new MarkerOptions().position(peaLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.comercio)).title("Nº" + pontoEntrega.getNumero_local() + " / " + tipoDemanda.toString()).snippet(tipoDemanda.toString()));
                }
                if (pontoEntrega.getTipoDemanda() == 3) {
                    mMap.addMarker(new MarkerOptions().position(peaLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.terreno)).title("Nº" + pontoEntrega.getNumero_local() + " / " + tipoDemanda.toString()).snippet(tipoDemanda.toString()));
                }
                if (pontoEntrega.getTipoDemanda() == 4) {
                    mMap.addMarker(new MarkerOptions().position(peaLatLng).icon(BitmapDescriptorFactory.fromResource(R.mipmap.casa)).title("Nº" + pontoEntrega.getNumero_local() + " / " + tipoDemanda.toString()).snippet(tipoDemanda.toString()));
                }
            }
        }
                /*mMap.addCircle(new CircleOptions().)
                new MarkerOptions().
                */
        //mPosts.removeAll(listDelete);
// TODO
                /*if (!points.isEmpty()) {
                    mMap.animateCamera(CameraUpdateFactory
                            .newLatLngBounds(GeoUtils.createLatLngBounds(points), 50));
                }*/

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

        /*mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {

            }
        });*/

        mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
            @Override
            public void onPolylineClick(Polyline polyline) {
                LogUtils.log("polyline clicked id: " + polyline.getId());
            }
        });
    }
    private void moveToCurrentLocation(LatLng currentLocation)
    {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,45));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);

    }
    public static PolygonOptions getPolygonOptions(List<LatLng> orderPoints) {
        int POLYGON_ALPHA = 70;
        PolygonOptions options = new PolygonOptions().addAll(orderPoints);
        options.strokeColor(Color.rgb(56, 83, 135));
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
        PostEditDialogFragment.newInstance(MapsPostsAssociaPontoActivity.this, this, post)
                .show(getSupportFragmentManager(), "postEdit");
    }

    @Override
    public void onEditLocalButtonClicked(Post post) {

    }

    @Override
    public void onListLightingPostButtonClicked(Post post) {

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
                post.setPontoAtualizacao(new PontoAtualizacao(l.getLatitude(), l.getLongitude(), System.currentTimeMillis()/1000));
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
                        OfflineDownloadResponse download = FileUtils.retrieve(post.getOrderId());
                        List<Post> posts = download.getPosts();

                        Iterator<Post> iterator = posts.iterator();

                        while (iterator.hasNext()) {
                            Post p = iterator.next();
                            //if(p.equals(post)) {
                            if (p.getId() == post.getId()) {
                                LogUtils.log("FOUND "+p.getId());
                                iterator.remove();
                                break;
                            }
                        }
                        posts.add(post);
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

                }

                else {
                    //FIXME: não foi implementado restauração de postes ONLINE
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
                                        onPostChanged(post, false, null);
                                    } else {
                                        Toast.makeText(getApplicationContext(),
                                                "Houve algum problema salvando as alterações, por favor tente novamente ou informe o administrador.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            };

                    PostController.get().delete(getApplicationContext(), post, callback);
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
    public void onListPontoEntregaButtonClicked(Post post) {

    }

    @Override
    public void onListAfloramentosButtonClicked(Post post) {

    }

    @Override
    public void onListBancoCapacitadorButtonClicked(Post post) {

    }




    public void deletePolylineAndMakersNegativos(PontoEntrega entrega, Post post){
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
                        .icon(getPinPontoEntrega(entrega))
                        .position(new LatLng(entrega.getX(), entrega.getY()))
                        .title(tipo.toString()));

        markersMapPontoEntrega.put(entrega.getId(), marker);
        pontosMap.put(marker.getId(), entrega);

        Polyline line = mMap.addPolyline(new PolylineOptions()
                .add(new LatLng(entrega.getX(), entrega.getY()), post.getLocation().toLatLng())
                .width(10)
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
    public List<Post> getPostsList() {
        return mPosts;
    }

    @Override
    public List<LatLng> getOrderPoints()  {
        return mOrderPoints;
    }

    @NonNull
    public static BitmapDescriptor getPin(Post post) {
        int drawable = post.isExcluido() ? R.drawable.pin_red :
                post.getPostType() > 7 &&  post.getPostType() < 11 ? R.drawable.pin_yellow :
                        post.getPostType() > 14 ? R.drawable.pin_roxo :
                post.isClosed() ? R.drawable.pin_green :
                        R.drawable.pin_cyan;

        return BitmapDescriptorFactory.fromResource(drawable);
    }

    @NonNull
    public static BitmapDescriptor getPinPonto() {
        int drawable = R.drawable.pin_red ;
        return BitmapDescriptorFactory.fromResource(drawable);
    }

    public static BitmapDescriptor getPinPontoEntrega(PontoEntrega pontoEntrega) {
        int drawable =R.drawable.pin_red;

        if(pontoEntrega.getTipoDemanda() == 0){
            drawable =R.mipmap.duvida;
        }
        if(pontoEntrega.getTipoDemanda() == 1){
            drawable =R.mipmap.predio;
        }
        if(pontoEntrega.getTipoDemanda() == 2){
            drawable =R.mipmap.comercio;
        }
        if(pontoEntrega.getTipoDemanda() == 3){
            drawable =R.mipmap.terreno;
        }
        if(pontoEntrega.getTipoDemanda() == 4){
            drawable =R.mipmap.casa;
        }
                /*post.getClasseAtendimento()  7 &&  post.getPostType() < 11 ? R.drawable.pin_yellow :
                        post.getPostType() > 14 ? R.drawable.pin_roxo :
                                post.isClosed() ? R.drawable.pin_green :
                                        R.drawable.pin_cyan;*/

        return BitmapDescriptorFactory.fromResource(drawable);
    }
}