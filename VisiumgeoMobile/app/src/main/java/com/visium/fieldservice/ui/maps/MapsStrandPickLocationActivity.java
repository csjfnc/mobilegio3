package com.visium.fieldservice.ui.maps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.maps.android.SphericalUtil;
import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.entity.Anotacao;
import com.visium.fieldservice.entity.DemandaStrand;
import com.visium.fieldservice.entity.PontoEntrega;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.VaoPrimario;
import com.visium.fieldservice.entity.VaosPontoPoste;
import com.visium.fieldservice.ui.common.CommonAppCompatActivity;
import com.visium.fieldservice.util.FileUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsStrandPickLocationActivity extends CommonAppCompatActivity implements OnMapReadyCallback {

    public static final String PICK_LOCATION = "PICK_LOCATION";
    public static final String PICK_LOCATIONDOIS = "PICK_LOCATIONDOIS";
    public static final String METERS_RESTRICTION = "METERS_RESTRICTION";

    private GoogleMap mMap;
    private Marker mMarker;
    private LatLng editLocation;
    private Float metersRestriction;

    private List<LatLng> points;
    private Map<String, Post> postsMap = new HashMap<>();
    private Map<String, PontoEntrega> pontosMap = new HashMap<>();
    private Map<Long, Marker> markersMap = new HashMap<>();

    private Map<String, Anotacao> anotacaoMap = new HashMap<>();
    private Map<Long, Marker> anotacaoHashMap = new HashMap<>();
    private Map<Long, Polyline> polylineMapStrand = new HashMap<>();

    private List<Post> mPosts;
    private List<LatLng> mOrderPoints;

    private List<VaoPrimario> mVaosPrimarios;
    private List<PontoEntrega> mPontoEntregas;
    private List<VaosPontoPoste> mVaosPontoPostes;
    private List<DemandaStrand> mDemandaStrands;
    private long orderID = 0;
    private String tipo, editar;
    private List<Anotacao> anotacaoList;
    private int contaClique;
    private Intent dataStrand = new Intent();
    private DemandaStrand mDemandaStrand;
    private LatLng latLngEdit;
    private LatLng latLgn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        setupToolbar(false, R.string.pick_location);
        forceStatusBarColor();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle bundle = getIntent().getExtras();
        tipo = bundle.getString("tipo");
        mDemandaStrand = (DemandaStrand) bundle.getSerializable("strand");

        contaClique = 0;
        findViewById(R.id.button_pick_confirmation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!tipo.equals("strand")) {
                    setResult();
                } else {
                    if (contaClique == 0) {
                        dataStrand.putExtra(PICK_LOCATION, mMarker != null ? mMarker.getPosition() : null);
                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin1))
                                .position(mMarker.getPosition()));
                    }
                    if (contaClique == 1) {
                        mMarker.getPosition();
                        dataStrand.putExtra(PICK_LOCATIONDOIS, mMarker != null ? mMarker.getPosition() : null);
                        dataStrand.putExtra("tipo", tipo);
                        /*if(mDemandaStrand != null){
                            dataStrand.putExtra("strand", mDemandaStrand);
                        }*/
                        setResult(RESULT_OK, dataStrand);
                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin2))
                                .position(mMarker.getPosition()));
                        finish();
                    }
                    contaClique++;
                }
            }
        });

        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(PICK_LOCATION)) {
                editLocation = intent.getParcelableExtra(PICK_LOCATION);
            }
            if (intent.hasExtra(METERS_RESTRICTION)) {
                metersRestriction = intent.getFloatExtra(METERS_RESTRICTION, 0);
            }
            if (intent.hasExtra(MapsPostsActivity.SERVICE_ORDER_POINTS)) {
                mOrderPoints = (List<LatLng>) intent.getSerializableExtra(MapsPostsActivity.SERVICE_ORDER_POINTS);
            }
            if (intent.hasExtra("orderId")) {
                orderID = intent.getLongExtra("orderId", 0);
            }
           /* if (intent.hasExtra(MapsPostsActivity.POST_LIST)) {
                mPosts = (List<Post>) intent.getSerializableExtra(MapsPostsActivity.POST_LIST);
            }*/
        }

        try {
            OfflineDownloadResponse downloaded = FileUtils.retrieve(orderID);
            mPosts = downloaded.getPosts();
            mVaosPrimarios = downloaded.getVaoPrimarioList();
            mPontoEntregas = downloaded.getPontoEntregaList();
            mVaosPontoPostes = downloaded.getVaosPontoPosteList();
            anotacaoList = downloaded.getAnotacaoList();
            mDemandaStrands = downloaded.getDemandaStrandList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (mDemandaStrand != null) {
            latLngEdit = new LatLng(mDemandaStrand.getX1(), mDemandaStrand.getY1());
            //moveToCurrentLocation(latLngEdit);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult();
                return true;
            default:
                //NO-OP
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setResult() {
        Intent data = new Intent();
        data.putExtra(PICK_LOCATION, mMarker != null ? mMarker.getPosition() : null);
        data.putExtra("tipo", tipo);
        setResult(RESULT_OK, data);
        finish();
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
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        View mapTypeActionButton = findViewById(R.id.map_type);
        if (mapTypeActionButton != null) {
            mapTypeActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mMap != null) {
                        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL) {
                            mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        } else {
                            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        }
                    }
                }
            });
        }

        if (mDemandaStrand != null) {
            //latLgn = latLngEdit;
            moveToCurrentLocation(latLngEdit);
        } else {
            latLgn = getAndDefineMyLocation();
        }


        points = new ArrayList<>(mPosts.size());
        Polygon polygon = mMap.addPolygon(
                MapsPostsActivity.getPolygonOptions(mOrderPoints));
        polygon.setClickable(false);

        for (Post post : mPosts) {
            LatLng latLng = new LatLng(post.getLocation().getLat(), post.getLocation().getLon());
            points.add(latLng);
            Marker marker = mMap.addMarker(
                    new MarkerOptions()
                            .icon(MapsPostsActivity.getPin(post))
                            .position(latLng)
                            .title(String.valueOf(post.getGeoCode())));

            markersMap.put(post.getId(), marker);
            postsMap.put(marker.getId(), post);

        }

        for (PontoEntrega pontoEntrega : mPontoEntregas) {
            LatLng latLng = new LatLng(pontoEntrega.getX(), pontoEntrega.getY());
            points.add(latLng);
            Marker marker = mMap.addMarker(
                    new MarkerOptions()
                            .icon(MapsPostsActivity.getPinPonto(pontoEntrega))
                            .position(latLng)
                            .title(String.valueOf(pontoEntrega.getGeoCode())));

            markersMap.put(pontoEntrega.getId(), marker);
            pontosMap.put(marker.getId(), pontoEntrega);
        }
        for (Anotacao anotacao : anotacaoList) {

            //LatLng peaLatLng = new LatLng(pontoEntrega.getX(), pontoEntrega.getY());
            LatLng peaLatLng = new LatLng(anotacao.getX(), anotacao.getY());

            Marker marker = null;
            marker = mMap.addMarker(new MarkerOptions().position(peaLatLng)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.noteicon3))
                    .title(anotacao.getAnotacao()));
            if (marker != null) {
                anotacaoMap.put(marker.getId(), anotacao);
                anotacaoHashMap.put(anotacao.getId(), marker);
            }
        }

        for (final DemandaStrand demandaStrand : mDemandaStrands) {
            LatLng latLng = new LatLng(demandaStrand.getX2(), demandaStrand.getY2());

            Polyline addPolyline = mMap.addPolyline(new PolylineOptions()
                    .add(new LatLng(demandaStrand.getX1(), demandaStrand.getY1()), latLng)
                    .width(10)
                    .color(Color.parseColor("#ff9900")));
            addPolyline.setClickable(true);
            //polylineMapStrand.put(demandaStrand.getId(), addPolyline);
        }

        if (!mVaosPontoPostes.isEmpty()) {
            for (VaosPontoPoste vaosPontoPoste : mVaosPontoPostes) {
                if (vaosPontoPoste.getX1() != null) {
                    List<LatLng> lineList = new ArrayList<LatLng>();

                    lineList.add(new LatLng(vaosPontoPoste.getX1(), vaosPontoPoste.getY1()));
                    lineList.add(new LatLng(vaosPontoPoste.getX2(), vaosPontoPoste.getY2()));
                       /* Polyline line = mMap.addPolyline(getVaoPolylineOptions(lineList));
                        line.setWidth(6);
                        line.setColor((Color.parseColor("#f0ff23")));*/

                    LatLng latLng = new LatLng(vaosPontoPoste.getX2(), vaosPontoPoste.getY2());

                    Polyline line2 = mMap.addPolyline(new PolylineOptions()
                            .add(new LatLng(vaosPontoPoste.getX1(), vaosPontoPoste.getY1()), latLng)
                            .width(4)
                            .color(Color.parseColor("#ff3a3a")));

                    line2.setClickable(true);
                }
            }
        }

        if(latLngEdit != null){
            MarkerOptions markerOptions = new MarkerOptions().
                    position(latLngEdit).title(getString(R.string.pick_location_marker_drag))
                    .draggable(true);
            mMarker = mMap.addMarker(markerOptions);
        }else{
            MarkerOptions markerOptions = new MarkerOptions().
                    position(latLgn).title(getString(R.string.pick_location_marker_drag))
                    .draggable(true);
            mMarker = mMap.addMarker(markerOptions);
        }



        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

            @Override
            public void onMarkerDragStart(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                marker.setSnippet(getString(R.string.pick_location_marker_drop));
                marker.setPosition(checkRestriction(marker, marker.getPosition()));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(marker.getPosition()));
            }

            @Override
            public void onMarkerDrag(Marker marker) {
            }

        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                latLng = checkRestriction(mMarker, latLng);
                mMarker.setPosition(latLng);
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });

        if (metersRestriction != null && metersRestriction > 0) {
            mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
                @Override
                public void onMapLoaded() {
                    mMap.addCircle(new CircleOptions()
                            .strokeColor(Color.WHITE)
                            .center(editLocation)
                            .radius(metersRestriction));
                }
            });
        }

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker marker) {
                Post post = postsMap.get(marker.getId());
                if (post != null) {
                    if (contaClique == 0) {
                        LatLng latLng = new LatLng(post.getLocation().getLat(), post.getLocation().getLon());
                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin1))
                                .position(latLng));
                        dataStrand.putExtra(PICK_LOCATION, latLng);
                    }
                    if (contaClique == 1) {
                        LatLng latLng = new LatLng(post.getLocation().getLat(), post.getLocation().getLon());
                        mMap.addMarker(new MarkerOptions()
                                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.pin2))
                                .position(latLng));
                        dataStrand.putExtra(PICK_LOCATIONDOIS, latLng);
                        dataStrand.putExtra("tipo", tipo);
                        setResult(RESULT_OK, dataStrand);

                        finish();
                    }
                    contaClique++;
                } else {
                    Toast.makeText(MapsStrandPickLocationActivity.this, "Anotação não é permitida", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private LatLng checkRestriction(Marker marker, LatLng latLng) {
        if (metersRestriction != null && metersRestriction > 0) {
            if (SphericalUtil.computeDistanceBetween(editLocation, latLng) > metersRestriction) {
                marker.setPosition(editLocation);
                return editLocation;
            }
        }
        return latLng;
    }


    private LatLng getAndDefineMyLocation() {

        LatLng latLgn = editLocation != null ? editLocation : null;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION) && (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION))) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
            }

        } else {
            mMap.setMyLocationEnabled(true);

            if (latLgn == null) {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                Criteria criteria = new Criteria();

                Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

                if (location != null) {
                    latLgn = new LatLng(location.getLatitude(), location.getLongitude());
                } else {
                    latLgn = new LatLng(-22.8947469, -47.1706916);
                }
            }
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLgn)              // Sets the center of the map to location user
                .zoom(30)                   // Sets the zoom
                //.bearing(90)                // Sets the orientation of the camera to east
                //.tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        return latLgn;

    }

    private void moveToCurrentLocation(LatLng currentLocation) {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 45));
        // Zoom in, animating the camera.
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        // Zoom out to zoom level 10, animating with a duration of 2 seconds.
        mMap.animateCamera(CameraUpdateFactory.zoomTo(18), 2000, null);

    }
}
