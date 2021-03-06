package com.sabayosja.fordcambodia.android.activity;

import androidx.annotation.DrawableRes;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.adapter.AdapterCollapsedList;
import com.sabayosja.fordcambodia.android.listener.LoadDataListener;
import com.sabayosja.fordcambodia.android.listener.VolleyCallback;
import com.sabayosja.fordcambodia.android.util.Global;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.MyFunction;
import com.sabayosja.fordcambodia.android.util.Tools;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;

public class ActivityLocation extends ActivityController implements OnMapReadyCallback {

    private TextView tvShow, tvGas, tvOSC;
    private View lineShow, lineGas, lineOSC;
    private CardView cardFilter;
    private LocationCallback locationCallback;
    private GoogleMap mMap;
    private final int PIN_W = 75;
    private final int PIN_H_GAS = 140;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private final String RADIUS = "5000";
    private final String TYPE = "gastation";
    private final String NAME = "gastation";
    private double lat = 11.556289;
    private double lng = 104.928170;
    private JSONArray arrTitle;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityLocation.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();

    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if (fusedLocationProviderClient != null)
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
    }

    private void setSupportMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);
    }

    private void initView() {
        initToolbar();
        findView();
        getCurrentLocation();
        initCurrentLocation();
        initCollapsedListLocation();
    }

    private void findView() {
        tvGas = findViewById(R.id.tvGas);
        tvShow = findViewById(R.id.tvShow);
        tvOSC = findViewById(R.id.tvOSC);
        lineShow = findViewById(R.id.lineShow);
        lineGas = findViewById(R.id.lineGas);
        lineOSC = findViewById(R.id.lineOSC);
        cardFilter = findViewById(R.id.cardFilter);
    }

    private void initToolbar() {
        final ImageView iv_ford = findViewById(R.id.iv_ford);
        final ImageView iv_search = findViewById(R.id.iv_search);
        final ImageView iv_back = findViewById(R.id.iv_back);
        iv_back.setImageDrawable(getResources().getDrawable(R.drawable.img_arrow));
        iv_back.setColorFilter(ContextCompat.getColor(this, R.color.colorPrimaryDark), android.graphics.PorterDuff.Mode.MULTIPLY);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_ford.setVisibility(View.GONE);
        iv_search.setVisibility(View.GONE);
    }

    private void getCurrentLocation() {
        try {
            Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    getRequest();
                    getLocation();
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ActivityLocation.this);
                    setSupportMap();
                    loadLocation();
                }

                @Override
                public void onPermissionDenied(PermissionDeniedResponse response) {
                    Toast.makeText(ActivityLocation.this, "You should allow permission location for map view", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                }
            }).check();
        } catch (Exception e) {
            Log.e("Err", "" + e.getMessage());
        }
    }

    public void initTab(View view) {
        try {
            switch (view.getId()) {
                case R.id.tabShow:
                    tvGas.setTextColor(getResources().getColor(R.color.grey_5));
                    tvShow.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tvOSC.setTextColor(getResources().getColor(R.color.grey_5));
                    lineShow.setVisibility(View.VISIBLE);
                    lineGas.setVisibility(View.GONE);
                    lineOSC.setVisibility(View.GONE);
                    cardFilter.setVisibility(View.VISIBLE);
                    loadAnyTab(2, R.drawable.ic_pin_ford, 12f);
                    break;
                case R.id.tabGas:
                    tvGas.setTextColor(getResources().getColor(R.color.colorPrimary));
                    tvShow.setTextColor(getResources().getColor(R.color.grey_5));
                    tvOSC.setTextColor(getResources().getColor(R.color.grey_5));
                    lineShow.setVisibility(View.GONE);
                    lineGas.setVisibility(View.VISIBLE);
                    lineOSC.setVisibility(View.GONE);
                    cardFilter.setVisibility(View.GONE);
                    loadGasStation();
                    break;
                case R.id.tabOSC:
                    tvGas.setTextColor(getResources().getColor(R.color.grey_5));
                    tvShow.setTextColor(getResources().getColor(R.color.grey_5));
                    tvOSC.setTextColor(getResources().getColor(R.color.colorPrimary));
                    lineShow.setVisibility(View.GONE);
                    lineGas.setVisibility(View.GONE);
                    lineOSC.setVisibility(View.VISIBLE);
                    cardFilter.setVisibility(View.VISIBLE);
                    loadAnyTab(0, R.drawable.ic_pin_osc, 7f);
                    break;
            }
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void getRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        //locationRequest.setInterval(50000);
        //locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);
    }

    private void getLocation() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (mMap != null) {
                    try {
                        lat = locationResult.getLastLocation().getLatitude();
                        lng = locationResult.getLastLocation().getLongitude();
                        mMap.setMyLocationEnabled(true);
                    } catch (Exception e) {
                        Log.e("Err", e.getMessage() + "");
                    }
                }
            }
        };
    }

    private void initCurrentLocation() {
        findViewById(R.id.cardCurrent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final LatLng latLng = new LatLng(lat, lng);
                moveCamera(latLng);
            }
        });
    }

    public void moveCamera(final LatLng latLng) {
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 12f));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

    private void initCollapsedListLocation() {
        final LinearLayout bottom_sheet = findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        final ImageView ivCollapsed = findViewById(R.id.ivCollapsed);
        findViewById(R.id.cardCollapsed).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
                    ivCollapsed.setImageDrawable(getResources().getDrawable(R.drawable.ic_zoom_in));
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                } else {
                    ivCollapsed.setImageDrawable(getResources().getDrawable(R.drawable.ic_zoom_out));
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
            }
        });
    }

    private void setMaker(final JSONArray arr, @DrawableRes final int id, final float camera) {
        try {
            BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(id);
            Bitmap b = bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, PIN_W, PIN_H_GAS, false);
            mMap.clear();

            for (int i = 0; i < arr.length(); i++) {
                final JSONObject objLocation = arr.getJSONObject(i);
                final double lat = Double.parseDouble(objLocation.getJSONObject(Global.arData[122]).getString(Global.arData[124]));
                final double lng = Double.parseDouble(objLocation.getJSONObject(Global.arData[122]).getString(Global.arData[125]));
                final LatLng latLng = new LatLng(lat, lng);
                mMap.addMarker(new MarkerOptions().position(latLng)
                        .title(objLocation.getString(Global.arData[35]))
                        .snippet(objLocation.getString(Global.arData[123]))
                        .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                    @Override
                    public View getInfoWindow(Marker marker) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        try{
                            View v = getLayoutInflater().inflate(R.layout.item_maker, null);
                            MyFont.getInstance().setFont(ActivityLocation.this,v,1);
                            final TextView tvTitle = v.findViewById(R.id.tvTitle);
                            final TextView tvSnippet = v.findViewById(R.id.tvSnippet);
                            tvTitle.setText(objLocation.getString(Global.arData[35]));
                            tvSnippet.setText(objLocation.getString(Global.arData[123]));
                            return v;
                        }catch (Exception e){
                            Log.e("Err",e.getMessage()+"");
                        }
                        return null;
                    }
                });

            }
            final LatLng latLng = new LatLng(lat, lng);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, camera));
            mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse(String.format("http://maps.google.com/maps?saddr=%s,%s&daddr=%s,%s",lat,lng,marker.getPosition().latitude,marker.getPosition().longitude)));
                    startActivity(intent);
                }
            });
        } catch (Exception e) {
            Log.e("Err", e.getMessage() + "");
        }
    }

    private void setCollapsedList(final JSONArray arr) {
        final LinearLayoutManager manager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        final RecyclerView recyclerCollapsed = findViewById(R.id.recycleCollapsed);
        final AdapterCollapsedList adapterCollapsedList = new AdapterCollapsedList(this, arr);
        recyclerCollapsed.setLayoutManager(manager);
        recyclerCollapsed.setAdapter(adapterCollapsedList);
    }

    private void loadGasStation() {
        final String url = Global.GOOGLE_PLACE_API + String.format(Global.GOOGLE_PLACE_PARAM, lat, lng, RADIUS, TYPE, NAME, getString(R.string.google_maps_key));
        loadDataServer(Request.Method.GET, null, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    final JSONArray arr = new JSONArray();
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        final JSONObject object = new JSONObject(response);
                        for (int i = 0; i < object.getJSONArray(Global.arData[120]).length(); i++) {
                            final JSONObject objData = new JSONObject();
                            final JSONObject objResult = object.getJSONArray(Global.arData[120]).getJSONObject(i);
                            final JSONObject objGeo = objResult.getJSONObject(Global.arData[121]);
                            objData.put(Global.arData[122], objGeo.getJSONObject(Global.arData[122]));
                            objData.put(Global.arData[35], objResult.getString(Global.arData[35]));
                            objData.put(Global.arData[123], objResult.getString(Global.arData[123]));
                            arr.put(i, objData);
                        }
                        setMaker(arr, R.drawable.ic_pin_gas_station, 12f);
                        setCollapsedList(arr);
                    } else {
                        MyFunction.getInstance().alertMessage(ActivityLocation.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    private void loadLocation() {
        final String lang = MyFunction.getInstance().getText(ActivityLocation.this, Global.arData[6]);
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[7], Global.arData[126]);
        loadDataServer(Request.Method.POST, param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        arrTitle = new JSONArray(response);
                        loadAnyTab(2, R.drawable.ic_pin_ford, 12f);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    private void loadAnyTab(final int position, @DrawableRes final int id, final float camera) throws JSONException {
        final String lang = MyFunction.getInstance().getText(ActivityLocation.this, Global.arData[6]);
        final String url = Global.arData[0] + Global.arData[1] + Global.arData[5];
        final HashMap<String, String> param = new HashMap<>();
        param.put(Global.arData[6], lang);
        param.put(Global.arData[124], lat + "");
        param.put(Global.arData[125], lng + "");
        param.put(Global.arData[127], arrTitle.getJSONObject(position).getString(Global.arData[7]));
        param.put(Global.arData[128], "0");
        loadDataServer(Request.Method.POST, param, url, new LoadDataListener() {
            @Override
            public void onSuccess(String response) {
                try {
                    final JSONArray arr = new JSONArray();
                    if (MyFunction.getInstance().isValidJSON(response)) {
                        final JSONArray array = new JSONArray(response);
                        for (int i = 0; i < array.length(); i++) {
                            final JSONObject objData = new JSONObject();
                            final JSONObject objLatLng = new JSONObject();
                            final JSONObject obj = array.getJSONObject(i);
                            objLatLng.put(Global.arData[124], obj.getString(Global.arData[124]));
                            objLatLng.put(Global.arData[125], obj.getString(Global.arData[125]));
                            objData.put(Global.arData[122], objLatLng);
                            objData.put(Global.arData[35], obj.getString(Global.arData[18]));
                            objData.put(Global.arData[123], obj.getString(Global.arData[129]));
                            arr.put(i, objData);
                        }
                        setMaker(arr, id, camera);
                        setCollapsedList(arr);
                    }
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
            }
        });
    }

    private void loadDataServer(final int method, HashMap<String, String> param, final String url, final LoadDataListener loadData) {

        showDialog();
        MyFunction.getInstance().requestString(this, method, url, param, new VolleyCallback() {
            @Override
            public void onResponse(String response) {
                try {
                    loadData.onSuccess(response);
                } catch (Exception e) {
                    Log.e("Err", e.getMessage() + "");
                }
                hideDialog();
            }

            @Override
            public void onErrorResponse(VolleyError e) {
                Log.e("Err", e.getMessage() + "");
                MyFunction.getInstance().alertMessage(ActivityLocation.this, getString(R.string.warning), getString(R.string.ok), getString(R.string.server_error), 1);
                hideDialog();
            }
        });
    }

}