package com.visium.fieldservice.ui.maps;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
//import com.tooltip.Tooltip;
import com.visium.fieldservice.R;
import com.visium.fieldservice.api.visium.bean.memoria.DownloadOrderOffline;
import com.visium.fieldservice.api.visium.bean.response.ErrorResponse;
import com.visium.fieldservice.api.visium.bean.response.OfflineDownloadResponse;
import com.visium.fieldservice.controller.PostController;
import com.visium.fieldservice.controller.ServiceOrderController;
import com.visium.fieldservice.controller.UserController;
import com.visium.fieldservice.controller.VisiumApiCallback;
import com.visium.fieldservice.entity.City;
import com.visium.fieldservice.entity.LocalUsuario;
import com.visium.fieldservice.entity.Post;
import com.visium.fieldservice.entity.ServiceOrder;
import com.visium.fieldservice.entity.ServiceOrderDetails;
import com.visium.fieldservice.preference.PreferenceHelper;
import com.visium.fieldservice.ui.dialog.CitiesDialogListener;
import com.visium.fieldservice.ui.dialog.CitiesListDialogFragment;
import com.visium.fieldservice.ui.dialog.ServiceOrderDialogFragment;
import com.visium.fieldservice.ui.dialog.ServiceOrderDialogListener;
import com.visium.fieldservice.ui.dialog.adapter.ListaOrdensAdapters;
import com.visium.fieldservice.ui.login.LoginActivity;
import com.visium.fieldservice.util.FileUtils;
import com.visium.fieldservice.util.GeoUtils;
import com.visium.fieldservice.util.LogUtils;
import com.visium.fieldservice.verifier.Verifier;

import org.apache.commons.lang3.BooleanUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapsServiceOrderActivity extends MapsAccuracy
        implements CitiesDialogListener,
        ServiceOrderDialogListener {

    private static final int POLYGON_ALPHA = 70;

    private FloatingActionButton floatingActionButton, listaTodosOrdensBaixadas;
    private ServiceOrder lastServiceOrderSelected;
    private Map<String, ServiceOrder> serviceOrderMap = new HashMap<>();
    private Map<Long, Polygon> polygonMap = new HashMap<>();
    private List<LatLng> points;
    private boolean mInitializedLocation;
    private ListView listOrdens;
    private boolean visibilityListaOrdens;
    private Location lastLocaltion;
    private boolean taEmPanico;
    private ImageButton panico;
   // private Tooltip tooltip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_os);

        setupToolbar(R.drawable.logo, getString(R.string.maps_service_order_title));
        forceStatusBarColor();
        visibilityListaOrdens = false;
        taEmPanico = true;

        panico = (ImageButton) findViewById(R.id.panicoIncio);

        if(LocalUsuario.getIdUser() == null){
            SharedPreferences  preferences = getSharedPreferences("LocalUsuario", Context.MODE_PRIVATE);
            String usuario = preferences.getString("userLocal", null);
            int panicoSql = preferences.getInt("panico", 0);
            LocalUsuario.setIdUser(usuario);
            LocalUsuario.setPanico(panicoSql);
            if(LocalUsuario.getPanico() == 1) {
                //tooltip = new Tooltip.Builder(panico, R.style.simpletooltip_default).setText("Ajuda Ativado!").show();
            }
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        ImageView logoutIcon = (ImageView) findViewById(R.id.logout);
        if (logoutIcon != null) {
            logoutIcon.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View view) {
                                                  new AlertDialog.Builder(MapsServiceOrderActivity.this)
                                                          .setIcon(android.R.drawable.ic_dialog_alert)
                                                          .setTitle(R.string.logout_dialog_title)
                                                          .setMessage(R.string.logout_dialog_message)
                                                          .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                                              @Override
                                                              public void onClick(DialogInterface dialog, int which) {
                                                                  PreferenceHelper.setImei(null);
                                                                  PreferenceHelper.setUserProfile(null);
                                                                  PreferenceHelper.setGcmToken(null);
                                                                  startActivity(new Intent(MapsServiceOrderActivity.this, LoginActivity.class));

                                                                  SharedPreferences.Editor preferences = getSharedPreferences("LocalUsuario", Context.MODE_PRIVATE).edit();
                                                                  preferences.remove("userLocal");
                                                                  preferences.remove("panico");
                                                                  preferences.commit();

                                                                  finish();
                                                              }
                                                          })
                                                          .setNegativeButton(R.string.cancel, null)
                                                          .show();
                                              }
                                          }
            );
        }

        listOrdens = (ListView) findViewById(R.id.listOrdens);

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog alertDialog = new ProgressDialog(MapsServiceOrderActivity.this, R.style.AlertDialogTheme);
                alertDialog.setTitle(getString(R.string.maps_service_order_dialog_title));
                alertDialog.setMessage(getString(R.string.maps_service_order_dialog_message));
                alertDialog.setCancelable(false);
                alertDialog.show();

                ServiceOrderController.get().getCities(new VisiumApiCallback<List<City>>() {
                    @Override
                    public void callback(List<City> cities, ErrorResponse e) {

                        alertDialog.dismiss();

                        if (e == null && !cities.isEmpty()) {
                            AppCompatDialogFragment citiesListDialog = CitiesListDialogFragment
                                    .newInstance(MapsServiceOrderActivity.this, cities);
                            citiesListDialog.show(getSupportFragmentManager(), "cities");
                        } else if (e != null && e.isCustomized()) {
                            Toast.makeText(MapsServiceOrderActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MapsServiceOrderActivity.this, R.string.maps_service_order_cities_error, Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        listaTodosOrdensBaixadas = (FloatingActionButton) findViewById(R.id.listaTodosOrdensBaixadas);
        listaTodosOrdensBaixadas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibilityListaOrdens){
                    listOrdens.setVisibility(View.VISIBLE);
                    visibilityListaOrdens = false;
                }else{
                    listOrdens.setVisibility(View.GONE);
                    visibilityListaOrdens = true;
                }
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
                        //Toast.makeText(MapsServiceOrderActivity.this, "Pedido de ajuda enviado!", Toast.LENGTH_LONG).show();

                     /*   if(tooltip != null){
                            tooltip.show();
                        }else{
                            tooltip = new Tooltip.Builder(panico, R.style.simpletooltip_default)
                                    .setText("Ajuda Ativado!").show();
                        }*/

                        panico.setBackgroundColor(Color.parseColor("#efc300"));

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
                        /*if(tooltip != null) {
                            tooltip.dismiss();
                        }*/
                        panico.setBackgroundColor(Color.WHITE);

                        SharedPreferences.Editor preferences = getSharedPreferences("LocalUsuario", Context.MODE_PRIVATE).edit();
                        preferences.putInt("panico", 0);
                        preferences.commit();
                    }
                }
            }
        });

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
        getAndDefineMyLocation();
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                final ServiceOrder serviceOrder = serviceOrderMap.get(polygon.getId());
                lastServiceOrderSelected = serviceOrder;

                mMap.animateCamera(CameraUpdateFactory
                        .newLatLngBounds(GeoUtils.createLatLngBounds(serviceOrder.getPoints()), 50));

                final ProgressDialog alertDialog = new ProgressDialog(MapsServiceOrderActivity.this, R.style.AlertDialogTheme);
                alertDialog.setTitle(getString(R.string.maps_service_order_details_selected_dialog_title, serviceOrder.getNumber()));
                alertDialog.setMessage(getString(R.string.maps_service_order_details_selected_dialog_message));
                alertDialog.setCancelable(false);
                alertDialog.show();

                if (FileUtils.serviceOrderFileExists(serviceOrder.getId())) {

                    try {
                      //  OfflineDownloadResponse downloaded = FileUtils.retrieve(serviceOrder.getId());

                        OfflineDownloadResponse downloaded = null;
                        if (DownloadOrderOffline.getResponse() == null) {
                            DownloadOrderOffline.setResponse(FileUtils.retrieve(serviceOrder.getId()));
                            downloaded = DownloadOrderOffline.getResponse();
                        } else {
                            downloaded = DownloadOrderOffline.getResponse();
                            if (serviceOrder.getId() != downloaded.getServiceOrderDetails().getId()) {
                                DownloadOrderOffline.setResponse(FileUtils.retrieve(serviceOrder.getId()));
                                downloaded = DownloadOrderOffline.getResponse();
                            }
                        }

                        alertDialog.dismiss();
                        ServiceOrderDialogFragment.newInstance(MapsServiceOrderActivity.this,
                                downloaded.getServiceOrderDetails(), downloaded.getPosts())
                                .show(getSupportFragmentManager(), "serviceOrderDetails");

                    } catch (IOException e) {
                        alertDialog.dismiss();
                        LogUtils.error(this, e);
                    }

                } else {

                    ServiceOrderController.get().getServiceOrder(serviceOrder, new VisiumApiCallback<ServiceOrderDetails>() {
                        @Override
                        public void callback(ServiceOrderDetails serviceOrderDetails, ErrorResponse e) {
                            alertDialog.dismiss();

                            if (serviceOrderDetails != null && e == null) {
                                //FIXME broken, we need to replace null
                                ServiceOrderDialogFragment.newInstance(MapsServiceOrderActivity.this,
                                        serviceOrderDetails, null).show(getSupportFragmentManager(), "serviceOrderDetails");
                                //~
                            } else if (e != null && e.isCustomized()) {
                                Toast.makeText(MapsServiceOrderActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(MapsServiceOrderActivity.this,
                                        getString(R.string.maps_service_order_details_error,
                                                serviceOrder.getNumber()),
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });

        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
            }
        });

        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                plotPolygons(FileUtils.retrieveAll());
            }
        });



    }

    @SuppressWarnings("ResourceType")
    private LatLng getAndDefineMyLocation() {

        LatLng latLgn;
        mMap.setMyLocationEnabled(true);


        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));

        if (location != null) {
            latLgn = new LatLng(location.getLatitude(), location.getLongitude());
        } else {
            latLgn = new LatLng(-22.8947469, -47.1706916);
        }

        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLgn)              // Sets the center of the map to location user
                .zoom(17)                   // Sets the zoom
                .bearing(90)                // Sets the orientation of the camera to east
                .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                .build();                   // Creates a CameraPosition from the builder
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        return latLgn;

    }

    @Override
    public void onCitySelected(City city) {

        final ProgressDialog alertDialog = new ProgressDialog(MapsServiceOrderActivity.this, R.style.AlertDialogTheme);
        alertDialog.setTitle(getString(R.string.maps_service_order_city_selected_dialog_title));
        alertDialog.setMessage(getString(R.string.maps_service_order_city_selected_dialog_message));
        alertDialog.setCancelable(false);
        alertDialog.show();

        for (Polygon polygon : polygonMap.values()) {
            polygon.remove();
        }

        polygonMap.clear();
        serviceOrderMap.clear();

        ServiceOrderController.get().getServiceOrders(city, new VisiumApiCallback<List<ServiceOrder>>() {
            @Override
            public void callback(List<ServiceOrder> orders, ErrorResponse e) {
                alertDialog.dismiss();

                if (orders != null && e == null) {
                    plotPolygons(orders);
                } else if (e != null && e.isCustomized()) {
                    Toast.makeText(MapsServiceOrderActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MapsServiceOrderActivity.this, R.string.maps_service_order_error, Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void plotPolygons(final List<ServiceOrder> orders) {
        points = new ArrayList<>();
        List<ServiceOrder> serviceOrdersParaoAdapter = new ArrayList<>();
        ServiceOrder serviceOrderParaAdapter;

        for (ServiceOrder order : orders) {
            Polygon polygon = mMap.addPolygon(
                    getPolygonOptions(order));

            polygon.setClickable(true);
            serviceOrderMap.put(polygon.getId(), order);
            polygonMap.put(order.getId(), polygon);
            points.addAll(order.getPoints());

            if (FileUtils.serviceOrderFileExists(order.getId())) {

                try {
                    //  OfflineDownloadResponse downloaded = FileUtils.retrieve(serviceOrder.getId());
                    OfflineDownloadResponse downloaded = null;
                    if (DownloadOrderOffline.getResponse() == null) {
                        DownloadOrderOffline.setResponse(FileUtils.retrieve(order.getId()));
                        downloaded = DownloadOrderOffline.getResponse();
                    } else {
                        downloaded = DownloadOrderOffline.getResponse();
                        if (order.getId() != downloaded.getServiceOrderDetails().getId()) {
                            DownloadOrderOffline.setResponse(FileUtils.retrieve(order.getId()));
                            downloaded = DownloadOrderOffline.getResponse();
                        }
                    }
                    order.setTotalPostes(downloaded.getPosts().size());
                    order.setTotalDemandas(downloaded.getPontoEntregaList().size());
                    order.setTotalAnotacao(downloaded.getAnotacaoList().size());
                    order.setTotalStrands(downloaded.getDemandaStrandList().size());

                } catch (IOException e) {
                    LogUtils.error(this, e);
                }

                serviceOrdersParaoAdapter.add(order);

            }


            ListaOrdensAdapters adapter = new ListaOrdensAdapters(serviceOrdersParaoAdapter, this);
            listOrdens.setAdapter(adapter);

            listOrdens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    for(ServiceOrder serviceOrder : orders){
                        if(serviceOrder.getId() == id){
                           // final ServiceOrder serviceOrderSelect = serviceOrder;
                            lastServiceOrderSelected = serviceOrder;
                        }
                    }


                    mMap.animateCamera(CameraUpdateFactory
                            .newLatLngBounds(GeoUtils.createLatLngBounds(lastServiceOrderSelected.getPoints()), 50));

                    final ProgressDialog alertDialog = new ProgressDialog(MapsServiceOrderActivity.this, R.style.AlertDialogTheme);
                    alertDialog.setTitle(getString(R.string.maps_service_order_details_selected_dialog_title, lastServiceOrderSelected.getNumber()));
                    alertDialog.setMessage(getString(R.string.maps_service_order_details_selected_dialog_message));
                    alertDialog.setCancelable(false);
                    alertDialog.show();

                    if (FileUtils.serviceOrderFileExists(lastServiceOrderSelected.getId())) {

                        try {
                            //  OfflineDownloadResponse downloaded = FileUtils.retrieve(serviceOrder.getId());

                            OfflineDownloadResponse downloaded = null;
                            if (DownloadOrderOffline.getResponse() == null) {
                                DownloadOrderOffline.setResponse(FileUtils.retrieve(lastServiceOrderSelected.getId()));
                                downloaded = DownloadOrderOffline.getResponse();
                            } else {
                                downloaded = DownloadOrderOffline.getResponse();
                                if (lastServiceOrderSelected.getId() != downloaded.getServiceOrderDetails().getId()) {
                                    DownloadOrderOffline.setResponse(FileUtils.retrieve(lastServiceOrderSelected.getId()));
                                    downloaded = DownloadOrderOffline.getResponse();
                                }
                            }

                            alertDialog.dismiss();
                            ServiceOrderDialogFragment.newInstance(MapsServiceOrderActivity.this,
                                    downloaded.getServiceOrderDetails(), downloaded.getPosts())
                                    .show(getSupportFragmentManager(), "serviceOrderDetails");

                        } catch (IOException e) {
                            alertDialog.dismiss();
                            LogUtils.error(this, e);
                        }

                    } else {

                        ServiceOrderController.get().getServiceOrder(lastServiceOrderSelected, new VisiumApiCallback<ServiceOrderDetails>() {
                            @Override
                            public void callback(ServiceOrderDetails serviceOrderDetails, ErrorResponse e) {
                                alertDialog.dismiss();

                                if (serviceOrderDetails != null && e == null) {
                                    //FIXME broken, we need to replace null
                                    ServiceOrderDialogFragment.newInstance(MapsServiceOrderActivity.this,
                                            serviceOrderDetails, null).show(getSupportFragmentManager(), "serviceOrderDetails");
                                    //~
                                } else if (e != null && e.isCustomized()) {
                                    Toast.makeText(MapsServiceOrderActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(MapsServiceOrderActivity.this,
                                            getString(R.string.maps_service_order_details_error,
                                                    lastServiceOrderSelected.getNumber()),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }

                }
            });

            if (points.size() > 0) {
                mMap.animateCamera(CameraUpdateFactory
                        .newLatLngBounds(GeoUtils.createLatLngBounds(points), 50));
            }
        }
    }

    public PolygonOptions getPolygonOptions(ServiceOrder order) {
        PolygonOptions options = new PolygonOptions().addAll(order.getPoints());

        switch (order.getStatus()) {
            case 0: {
                options.strokeColor(Color.rgb(56, 153, 185))
                        .fillColor(Color.argb(POLYGON_ALPHA, 56, 153, 185));
                break;
            }
            case 1: {
                options.strokeColor(Color.rgb(41, 252, 39))
                        .fillColor(Color.argb(POLYGON_ALPHA, 41, 252, 39));
                break;
            }
            case 2: {
                options.strokeColor(Color.rgb(85, 85, 85))
                        .fillColor(Color.argb(POLYGON_ALPHA, 85, 85, 85));
                break;
            }
        }
        return options;
    }

    @Override
    public void onServiceOrderSaveButtonClicked(final ServiceOrderDetails order, final List<Post> posts) {

        final ProgressDialog alertDialog = new ProgressDialog(MapsServiceOrderActivity.this, R.style.AlertDialogTheme);
        alertDialog.setTitle(getString(R.string.maps_service_order_update_dialog_title, order.getNumber()));
        alertDialog.setMessage(getString(R.string.maps_service_order_update_dialog_message));
        alertDialog.setCancelable(false);
        alertDialog.show();

        if (FileUtils.serviceOrderFileExists(order.getId())) {

            if (FileUtils.saveServiceOrder(order)) {
                alertDialog.dismiss();
                Toast.makeText(MapsServiceOrderActivity.this, R.string.maps_service_order_update_success,
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(MapsServiceOrderActivity.this,
                        getString(R.string.maps_service_order_update_error, order.getId()),
                        Toast.LENGTH_LONG).show();
            }
        }
        else {

            ServiceOrderController.get().updateOrder(order, new VisiumApiCallback<Boolean>() {
                @Override
                public void callback(Boolean success, ErrorResponse e) {
                    alertDialog.dismiss();

                    if (BooleanUtils.isTrue(success) && e == null) {
                        Toast.makeText(MapsServiceOrderActivity.this, R.string.maps_service_order_update_success,
                                Toast.LENGTH_LONG).show();
                    } else if (e != null && e.isCustomized()) {
                        Toast.makeText(MapsServiceOrderActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MapsServiceOrderActivity.this,
                                getString(R.string.maps_service_order_update_error, order.getId()),
                                Toast.LENGTH_LONG).show();
                    }

                    ServiceOrderDialogFragment.newInstance(MapsServiceOrderActivity.this,
                            order, posts).show(getSupportFragmentManager(), "serviceOrderDetails");

                }
            });
        }
    }

    @Override
    public void onServiceOrderPostsButtonClicked(final ServiceOrderDetails orderDetails) {

        final ProgressDialog alertDialog = new ProgressDialog(MapsServiceOrderActivity.this, R.style.AlertDialogTheme);
        alertDialog.setTitle(getString(R.string.maps_service_posts_dialog_title, orderDetails.getNumber()));
        alertDialog.setMessage(getString(R.string.maps_service_posts_dialog_message));
        alertDialog.setCancelable(false);
        alertDialog.show();

        if (FileUtils.serviceOrderFileExists(orderDetails.getId())) {

            try {
                OfflineDownloadResponse downloaded = FileUtils.retrieve(orderDetails.getId());
                alertDialog.dismiss();

                Intent intent = new Intent(MapsServiceOrderActivity.this, MapsPostsActivity.class);
                intent.putExtra(MapsPostsActivity.SERVICE_ORDER_DETAILS, orderDetails);
                intent.putExtra(MapsPostsActivity.SERVICE_ORDER_POINTS, new ArrayList<>(lastServiceOrderSelected.getPoints()));
                intent.putExtra(MapsPostsActivity.SERVICE_ORDER_POINTS, new ArrayList<>(lastServiceOrderSelected.getPoints()));
                //intent.putExtra(MapsPostsActivity.POST_LIST, new ArrayList<>(downloaded.getPosts()));
                //intent.putExtra(MapsPostsActivity.VAO_PRIMARIO_LIST, new ArrayList<>(downloaded.getVaoPrimarioList()));
                intent.putExtra(MapsPostsActivity.SERVICE_ORDER_PONTES_ENTREGA, new ArrayList<>(downloaded.getPontoEntregaList()));
                startActivity(intent);

            } catch (IOException e) {
                alertDialog.dismiss();
                LogUtils.error(this, e);

                Toast.makeText(MapsServiceOrderActivity.this,
                        getString(R.string.maps_service_posts_error, orderDetails.getNumber()),
                        Toast.LENGTH_LONG).show();
            }

        } else {

            PostController.get().getPosts(orderDetails.getId(), new VisiumApiCallback<List<Post>>() {
                @Override
                public void callback(List<Post> posts, ErrorResponse e) {
                    alertDialog.dismiss();

                    if (e == null && !posts.isEmpty()) {
                        Intent intent = new Intent(MapsServiceOrderActivity.this, MapsPostsActivity.class);
                        intent.putExtra(MapsPostsActivity.SERVICE_ORDER_DETAILS, orderDetails);
                        intent.putExtra(MapsPostsActivity.SERVICE_ORDER_POINTS, new ArrayList<>(lastServiceOrderSelected.getPoints()));
                        intent.putExtra(MapsPostsActivity.POST_LIST, new ArrayList<>(posts));
                        startActivity(intent);

                    } else if (e != null && e.isCustomized()) {
                        Toast.makeText(MapsServiceOrderActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(MapsServiceOrderActivity.this,
                                getString(R.string.maps_service_posts_error, orderDetails.getNumber()),
                                Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    public void onServiceOrderPublishButtonClicked(final ServiceOrderDetails orderDetails) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Vai ser preciso trabalhar nessa Ordem novamente?");
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                final ProgressDialog alertDialog = new ProgressDialog(MapsServiceOrderActivity.this, R.style.AlertDialogTheme);
                alertDialog.setTitle(getString(R.string.maps_service_publish_dialog_title, orderDetails.getNumber()));
                alertDialog.setMessage(getString(R.string.maps_service_publish_dialog_message));
                alertDialog.setCancelable(false);
                alertDialog.show();


                if (FileUtils.serviceOrderFileExists(orderDetails.getId())) {

                    ServiceOrderController.get().publishOfflineOrder(orderDetails.getId(), new VisiumApiCallback<Object>() {
                        @Override
                        public void callback(Object object, ErrorResponse e) {

                            if (e == null) {

                                FileUtils.deleteServiceOrderFile(orderDetails.getId());
                               //polygonMap.get(orderDetails.getId()).remove();
                                DownloadOrderOffline.setResponse(null);
                                alertDialog.dismiss();

                                Toast.makeText(MapsServiceOrderActivity.this,
                                        getString(R.string.maps_service_publish_success, orderDetails.getNumber()),
                                        Toast.LENGTH_LONG).show();


                            } else if (e != null && e.isCustomized()) {
                                alertDialog.dismiss();
                                Toast.makeText(MapsServiceOrderActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            } else {
                                alertDialog.dismiss();
                                Toast.makeText(MapsServiceOrderActivity.this,
                                        getString(R.string.maps_service_publish_error, orderDetails.getNumber()),
                                        Toast.LENGTH_LONG).show();
                            }

                        }
                    });

                } else {
                    Toast.makeText(MapsServiceOrderActivity.this,
                            getString(R.string.maps_service_publish_not_found, orderDetails.getNumber()),
                            Toast.LENGTH_LONG).show();
                }

            }
        }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                   // OfflineDownloadResponse downloaded = FileUtils.retrieve(orderDetails.getId());
                    OfflineDownloadResponse download = null;
                    if(DownloadOrderOffline.getResponse() == null){
                        DownloadOrderOffline.setResponse(FileUtils.retrieve(orderDetails.getId()));
                        download = DownloadOrderOffline.getResponse();
                    }

                    else{
                        download = DownloadOrderOffline.getResponse();
                    }
                    download.setColaboradorFinalizou(true);

                    if (FileUtils.saveOfflineDownload(download)) {
                        final ProgressDialog alertDialog = new ProgressDialog(MapsServiceOrderActivity.this, R.style.AlertDialogTheme);
                        alertDialog.setTitle(getString(R.string.maps_service_publish_dialog_title, orderDetails.getNumber()));
                        alertDialog.setMessage(getString(R.string.maps_service_publish_dialog_message));
                        alertDialog.setCancelable(false);
                        alertDialog.show();


                        if (FileUtils.serviceOrderFileExists(orderDetails.getId())) {

                            ServiceOrderController.get().publishOfflineOrder(orderDetails.getId(), new VisiumApiCallback<Object>() {
                                @Override
                                public void callback(Object object, ErrorResponse e) {

                                    if (e == null) {

                                        FileUtils.deleteServiceOrderFile(orderDetails.getId());
                                        polygonMap.get(orderDetails.getId()).remove();
                                        DownloadOrderOffline.setResponse(null);
                                        alertDialog.dismiss();

                                        Toast.makeText(MapsServiceOrderActivity.this,
                                                getString(R.string.maps_service_publish_success, orderDetails.getNumber()),
                                                Toast.LENGTH_LONG).show();



                                    } else if (e != null && e.isCustomized()) {
                                        alertDialog.dismiss();
                                        Toast.makeText(MapsServiceOrderActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                    } else {
                                        alertDialog.dismiss();
                                        Toast.makeText(MapsServiceOrderActivity.this,
                                                getString(R.string.maps_service_publish_error, orderDetails.getNumber()),
                                                Toast.LENGTH_LONG).show();
                                    }

                                }
                            });

                        } else {
                            Toast.makeText(MapsServiceOrderActivity.this,
                                    getString(R.string.maps_service_publish_not_found, orderDetails.getNumber()),
                                    Toast.LENGTH_LONG).show();
                        }

                    } else {

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        builder.show();
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
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, MapsServiceOrderActivity.this);
    }

    private void stopLocationUpdate(){
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, MapsServiceOrderActivity.this);
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
            SharedPreferences preferences = getSharedPreferences("LocalUsuario", Context.MODE_PRIVATE);
            //String usuario = preferences.getString("userLocal", null);
            //int panico = preferences.getInt("panico", 0);

            if(LocalUsuario.getIdUser() != null){
                    //LocalUsuario.setPanico(0);
                    UserController.get().LocalAtual(LocalUsuario.getIdUser(), location.getLatitude(), location.getLatitude(), LocalUsuario.getPanico(), LocalUsuario.getMensagem(), new VisiumApiCallback<LocalUsuario>() {
                        @Override
                        public void callback(LocalUsuario localUsuario, ErrorResponse e) {
                            // Toast.makeText(MapsServiceOrderActivity.this, "olaa", Toast.LENGTH_SHORT).show();
                        }
                    });

            }
        }
        startLocationUpdate();
    }

    @Override
    public void onLocationChanged(Location location) {
        super.onLocationChanged(location);

        if (!mInitializedLocation) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            mInitializedLocation = true;
        }

        //stop location updates
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }

        if(location != null){

            lastLocaltion = location;
            //   Toast.makeText(this, "Lat: "+ location.getLatitude()+" Lon: "+ location.getLongitude(), Toast.LENGTH_SHORT).show();
                UserController.get().LocalAtual(LocalUsuario.getIdUser(), location.getLatitude(), location.getLongitude(), LocalUsuario.getPanico(), LocalUsuario.getMensagem(), new VisiumApiCallback<LocalUsuario>() {
                    @Override
                    public void callback(LocalUsuario localUsuario, ErrorResponse e) {
                        Toast.makeText(MapsServiceOrderActivity.this, "olaa", Toast.LENGTH_SHORT).show();
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