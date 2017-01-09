package br.com.rar.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import br.com.rar.agenda.modelo.Prova;

public class ProvasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provas);

        List<String> topicoPort = Arrays.asList("sujeito", "pronome", "verbo");
        Prova provaPortugues = new Prova("Portugues", "01/02/2017", topicoPort);

        List<String> topicoMat = Arrays.asList("equação de segundo grau", "trigonometria");
        Prova provaMatematica = new Prova("Matemática", "10/02/2017", topicoMat);

        List<Prova> provas = Arrays.asList(provaPortugues, provaMatematica);

        ArrayAdapter<Prova> provaAdapter = new ArrayAdapter<Prova>(this, android.R.layout.simple_list_item_1, provas);
        ListView listaProvas = (ListView) findViewById(R.id.lista_provas);
        listaProvas.setAdapter(provaAdapter);

        listaProvas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Intent irParaDetalhesProva = new Intent(ProvasActivity.this, DetalhesProvaActivity.class);
                irParaDetalhesProva.putExtra("prova", prova);
                startActivity(irParaDetalhesProva);
                Toast.makeText(ProvasActivity.this, "Clicou em " + prova.getMateria(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
