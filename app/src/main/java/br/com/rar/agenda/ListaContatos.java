package br.com.rar.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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


        Button btnNovoAluno = (Button) findViewById(R.id.lista_aluno_btnNovoAluno);
        btnNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiParaFormulario = new Intent(ListaContatos.this, FormularioActivity.class);
                startActivity(intentVaiParaFormulario);
            }
        });

    }
}
