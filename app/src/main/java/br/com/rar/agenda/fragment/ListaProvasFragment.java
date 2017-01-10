package br.com.rar.agenda.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.rar.agenda.DetalhesProvaActivity;
import br.com.rar.agenda.ProvasActivity;
import br.com.rar.agenda.R;
import br.com.rar.agenda.modelo.Prova;

/**
 * Created by ralmendro on 1/9/17.
 */

public class ListaProvasFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_lista_provas, container, false);

        List<String> topicoPort = Arrays.asList("sujeito", "pronome", "verbo");
        Prova provaPortugues = new Prova("Portugues", "01/02/2017", topicoPort);

        List<String> topicoMat = Arrays.asList("equação de segundo grau", "trigonometria");
        Prova provaMatematica = new Prova("Matemática", "10/02/2017", topicoMat);

        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);

        ArrayAdapter<Prova> provaAdapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, provas);
        ListView listaProvas = (ListView) view.findViewById(R.id.lista_provas);
        listaProvas.setAdapter(provaAdapter);

        listaProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);

                //TODO aqui tem que melhorar, pois o fragment pode ser chamada de diversas activities
                ProvasActivity provasActivity = (ProvasActivity) getActivity();
                provasActivity.selecionaProva(prova);

            }
        });

        return view;
    }
}
