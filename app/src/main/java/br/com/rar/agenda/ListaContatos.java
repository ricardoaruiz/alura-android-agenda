package br.com.rar.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListaContatos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);

        String[] alunos = {"Ricardo Ruiz", "Guilherme Ruiz", "Cinthya Carvalho", "Aluno1", "Aluno1", "Aluno1", "Aluno1", "Aluno1", "Aluno1", "Aluno1", "Aluno1"};

        ListView listAlunos = (ListView) findViewById(R.id.lista_alunos);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alunos);

        listAlunos.setAdapter(adapter);

    }
}
