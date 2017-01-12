package br.com.rar.agenda;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import br.com.rar.agenda.dao.AlunoDAO;
import br.com.rar.agenda.modelo.Aluno;

/**
 * Created by ralmendro on 1/12/17.
 */

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng posicao = getLatLngDoEndereco("R. José Bervint, 1079 - Jd Arrivabene, Artur Nogueira - SP");
        if(posicao != null) {
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(posicao, 17);
            googleMap.moveCamera(cameraUpdate);
            MarkerOptions marcadorCentral = new MarkerOptions();
            marcadorCentral.position(posicao);
            marcadorCentral.title("RN Esportes");
            marcadorCentral.snippet("Academia esportiva");
            googleMap.addMarker(marcadorCentral);
        }

        AlunoDAO alunoDAO = new AlunoDAO(getContext());
        List<Aluno> alunos = alunoDAO.buscaAlunos();
        for(Aluno aluno : alunos) {
            MarkerOptions marcador = new MarkerOptions();
            LatLng posicaoAluno = getLatLngDoEndereco(aluno.getEndereco());
            if(posicaoAluno != null) {
                marcador.position(getLatLngDoEndereco(aluno.getEndereco()));
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }
        }
        alunoDAO.close();

    }

    private LatLng getLatLngDoEndereco(String endereco) {
        try {
            Geocoder geocoder = new Geocoder(getContext());
            List<Address> enderecos = geocoder.getFromLocationName(endereco, 1);
            if(!enderecos.isEmpty()) {
                return new LatLng(enderecos.get(0).getLatitude(), enderecos.get(0).getLongitude());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}


