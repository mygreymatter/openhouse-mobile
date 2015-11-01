package com.mayo.openhouse;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihood;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class ActPropertyMap extends AppCompatActivity
        implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener {

    private static final int PERMISSION_REQUEST_FINE_LOCATION = 1;
    private GoogleApiClient mGoogleApiClient;
    private GoogleMap mGoogleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_property_map);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access")
                        .setMessage("Please grant location access so this app can detect beacons")
                        .setPositiveButton(android.R.string.ok,null)
                        .setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @TargetApi(Build.VERSION_CODES.M)
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_FINE_LOCATION);
                            }
                        });

                builder.show();
            }else{
                init();
            }
        }else
            init();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode){
            case PERMISSION_REQUEST_FINE_LOCATION:
                if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.i(Tag.LOG,"Permission granted");
                    init();
                    mGoogleApiClient.connect();
                }else{
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality Limited")
                            .setMessage("Since Location has not been granted, this app cannot detect beacons.")
                            .setPositiveButton(android.R.string.ok,null)
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @TargetApi(Build.VERSION_CODES.M)
                                @Override
                                public void onDismiss(DialogInterface dialog) {
                                }
                            });

                    builder.show();
                }
                break;
        }
    }

    private void init() {
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .enableAutoManage(this, 0 /* clientId */, this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        Log.i(Tag.LOG, "onMapReady");


        map.setMyLocationEnabled(true);
        LatLng latLng = new LatLng(12.933254, 77.611088);
        map.addMarker(new MarkerOptions().position(latLng).title("Umiya Emporium"));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
        map.getUiSettings().setZoomControlsEnabled(true);

    }

    private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (isGooglePlayServicesAvailable()) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mGoogleMap = mapFragment.getMap();
            mapFragment.getMapAsync(this);
        } else {
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (this.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
                mGoogleApiClient.connect();
            return;
        }else
            mGoogleApiClient.connect();
    }

    @Override
    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(Tag.LOG, "Google Client Connected");


        ArrayList<String> filter = new ArrayList<>();

        filter.add(Integer.toString(Place.TYPE_ATM));
        filter.add(Integer.toString(Place.TYPE_AIRPORT));
        filter.add(Integer.toString(Place.TYPE_BANK));
        filter.add(Integer.toString(Place.TYPE_BUS_STATION));
        filter.add(Integer.toString(Place.TYPE_CHURCH));
        filter.add(Integer.toString(Place.TYPE_HOSPITAL));
        filter.add(Integer.toString(Place.TYPE_MOSQUE));
        filter.add(Integer.toString(Place.TYPE_PLACE_OF_WORSHIP));
        filter.add(Integer.toString(Place.TYPE_SCHOOL));
        filter.add(Integer.toString(Place.TYPE_SHOPPING_MALL));
        filter.add(Integer.toString(Place.TYPE_TAXI_STAND));
        filter.add(Integer.toString(Place.TYPE_TRAIN_STATION));
        filter.add(Integer.toString(Place.TYPE_TRANSIT_STATION));
        filter.add(Integer.toString(Place.TYPE_UNIVERSITY));
        filter.add(Integer.toString(Place.TYPE_RESTAURANT));

        PlaceFilter filters = new PlaceFilter(false,filter);

        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, /*filters*/null);
//        PendingResult<PlaceLikelihoodBuffer> result = Places.PlaceDetectionApi.getCurrentPlace(mGoogleApiClient, filters);
        result.setResultCallback(new ResultCallback<PlaceLikelihoodBuffer>() {
            @Override
            public void onResult(PlaceLikelihoodBuffer likelyPlaces) {
                for (PlaceLikelihood placeLikelihood : likelyPlaces) {

                    if(placeLikelihood.getPlace().getPlaceTypes().contains(new Integer("79"))) {
                        Log.i(Tag.LOG, String.format("Place '%s' with " +
                                        "likelihood: %g",
                                placeLikelihood.getPlace().getName(),
                                placeLikelihood.getLikelihood()));

                        LatLng latLng = placeLikelihood.getPlace().getLatLng();

                        mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(placeLikelihood.getPlace().getName().toString()));
                        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

                    }


                }
                likelyPlaces.release();
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(Tag.LOG,"Google Client Connection Suspended");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(Tag.LOG,"Google Client Connection Failed");
    }


}

