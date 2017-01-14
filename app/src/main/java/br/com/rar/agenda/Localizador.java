package br.com.rar.agenda;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by ralmendro on 1/13/17.
 */

public class Localizador implements GoogleApiClient.ConnectionCallbacks, LocationListener {

    private final GoogleApiClient client;
    private final Context context;
    private final MapaFragment mapFragment;
    private LocationRequest request;

    public Localizador(Context context,  MapaFragment mapFragment) {

        this.context = context;
        this.mapFragment = mapFragment;

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(context);

        client = builder
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .build();

        client.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        request = new LocationRequest();
        request.setSmallestDisplacement(50);
        request.setInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng posicao = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(posicao,17);
        mapFragment.centralizar(cameraUpdate);
    }
}
