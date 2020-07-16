package com.sabayosja.fordcambodia.android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.sabayosja.fordcambodia.android.R;
import com.sabayosja.fordcambodia.android.util.MyFont;
import com.sabayosja.fordcambodia.android.util.Tools;

public class ActivityLocation extends AppCompatActivity implements OnMapReadyCallback {

    private TextView tvShow,tvGas,tvOSC;
    private View lineShow,lineGas,lineOSC;
    private LocationCallback locationCallback;
    private GoogleMap mMap;
    private final int PIN_W = 75;
    private final int PIN_H = 75;
    private Marker userMaker;
    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        Tools.setSystemBarColor(this, R.color.white);
        Tools.setSystemBarLight(this);
        MyFont.getInstance().setFont(ActivityLocation.this, getWindow().getDecorView().findViewById(android.R.id.content), 1);
        initView();

    }

    private void setSupportMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);
    }

    private void initView() {
        initToolbar();
        findView();

        try {
            Dexter.withActivity(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
                @Override
                public void onPermissionGranted(PermissionGrantedResponse response) {
                    getRequest();
                    getLocation();
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(ActivityLocation.this);
                    setSupportMap();
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

    private void findView(){
        tvGas = findViewById(R.id.tvGas);
        tvShow = findViewById(R.id.tvShow);
        tvOSC = findViewById(R.id.tvOSC);
        lineShow = findViewById(R.id.lineShow);
        lineGas = findViewById(R.id.lineGas);
        lineOSC = findViewById(R.id.lineOSC);
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

    public void initTab(View view) {
        switch (view.getId()){
            case R.id.tabShow:
                tvShow.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvGas.setTextColor(getResources().getColor(R.color.grey_5));
                tvOSC.setTextColor(getResources().getColor(R.color.grey_5));
                lineShow.setVisibility(View.VISIBLE);
                lineGas.setVisibility(View.GONE);
                lineOSC.setVisibility(View.GONE);
                break;
            case R.id.tabGas:
                tvGas.setTextColor(getResources().getColor(R.color.colorPrimary));
                tvShow.setTextColor(getResources().getColor(R.color.grey_5));
                tvOSC.setTextColor(getResources().getColor(R.color.grey_5));
                lineShow.setVisibility(View.GONE);
                lineGas.setVisibility(View.VISIBLE);
                lineOSC.setVisibility(View.GONE);
                break;
            case R.id.tabOSC:
                tvGas.setTextColor(getResources().getColor(R.color.grey_5));
                tvShow.setTextColor(getResources().getColor(R.color.grey_5));
                tvOSC.setTextColor(getResources().getColor(R.color.colorPrimary));
                lineShow.setVisibility(View.GONE);
                lineGas.setVisibility(View.GONE);
                lineOSC.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void getRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(50000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setSmallestDisplacement(10f);
    }

    private void getLocation() {
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (mMap != null) {
                    try {
                        BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.img_location);
                        Bitmap b = bitmapdraw.getBitmap();
                        Bitmap smallMarker = Bitmap.createScaledBitmap(b, PIN_W, PIN_H, false);

                        if (userMaker != null) userMaker.remove();
                        userMaker = mMap.addMarker(new MarkerOptions().position(new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude())).title("You").icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userMaker.getPosition(), 12f));
                    } catch (Exception e) {
                        Log.e("Err", e.getMessage() + "");
                    }
                }
            }
        };
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
}