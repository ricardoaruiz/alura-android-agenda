package br.com.rar.agenda.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import br.com.rar.agenda.R;
import br.com.rar.agenda.modelo.Prova;

public class DetalhesProvaFragment extends Fragment {

    private TextView campoMateria;
    private TextView campoData;
    private ListView listaTopicos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detalhes_prova, container, false);

        campoMateria = (TextView) view.findViewById(R.id.detalhes_prova_materia);
        campoData = (TextView) view.findViewById(R.id.detalhes_prova_data);
        listaTopicos = (ListView) view.findViewById(R.id.detalhes_prova_topicos);

        Bundle arguments = getArguments();
        if(arguments != null) {
            populaCampos((Prova) arguments.getSerializable("prova"));
        }

        return view;
    }

    public void populaCampos(Prova prova) {
        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());

        ArrayAdapter<String> arrTopicos = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, prova.getTopicos());
        listaTopicos.setAdapter(arrTopicos);
    }

}
