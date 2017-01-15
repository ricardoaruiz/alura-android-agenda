package br.com.rar.agenda;

import android.*;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by ralmendro on 1/13/17.
 */

public class Localizador implements GoogleApiClient.ConnectionCallbacks, LocationListener, GoogleApiClient.OnConnectionFailedListener {

    private final GoogleApiClient client;
    private final Context context;
    private final MapaFragment mapFragment;
    private LocationRequest request;
    private Marker marker;

    public Localizador(Context context,  MapaFragment mapFragment) {

        this.context = context;
        this.mapFragment = mapFragment;

        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(context);

        client = builder
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        client.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        request = new LocationRequest();
        request.setSmallestDisplacement(5);
        request.setInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationServices.FusedLocationApi.requestLocationUpdates(client, request, this);

        LatLng posicaoMarcadorAtual = posicionaLocalizacaoAtual();
        posicionaMarcadorAtual(posicaoMarcadorAtual);

    }

    private LatLng posicionaLocalizacaoAtual() {
        Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(client);

        LatLng posicalAtual = null;
        if(lastLocation == null) {
            posicalAtual = new LatLng(-26, -46);
        } else {
            posicalAtual = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        }

        Toast.makeText(context, posicalAtual.latitude + " / " + posicalAtual.longitude, Toast.LENGTH_LONG).show();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(posicalAtual, 17);
        mapFragment.centralizar(cameraUpdate);
        return posicalAtual;
    }

    private void posicionaMarcadorAtual(LatLng posicalAtual) {
        if(marker != null) {
            marker.remove();
        }
        MarkerOptions marcadorAtual = new MarkerOptions();
        marcadorAtual = new MarkerOptions();
        marcadorAtual.position(posicalAtual);
        marcadorAtual.title("Você");
        marcadorAtual.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_accessibility_black_24dp));
        marker = mapFragment.plotaMarcadorPosciaoAtual(marcadorAtual);
    }

    public void pararConexaoComGoogleApi() {
        if (client.isConnected()) {
            client.disconnect();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        pararConexaoComGoogleApi();
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng posicao = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(posicao,17);
        mapFragment.centralizar(cameraUpdate);
        posicionaMarcadorAtual(posicao);
        Toast.makeText(context, posicao.latitude + " / " + posicao.longitude, Toast.LENGTH_LONG).show();
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        pararConexaoComGoogleApi();
    }
}
