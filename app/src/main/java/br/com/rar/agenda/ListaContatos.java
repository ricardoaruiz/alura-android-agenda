package br.com.rar.agenda;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.rar.agenda.dao.AlunoDAO;
import br.com.rar.agenda.modelo.Aluno;

public class ListaContatos extends AppCompatActivity {

    private ListView listAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_contatos);

        listAlunos = (ListView) findViewById(R.id.lista_alunos);
        registerForContextMenu(listAlunos);

        listAlunos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int position, long id) {
                Aluno aluno = (Aluno) lista.getItemAtPosition(position);
                Intent irParaFormulario = new Intent(ListaContatos.this, FormularioActivity.class);
                irParaFormulario.putExtra("aluno", aluno);
                startActivity(irParaFormulario);
            }
        });

        Button btnNovoAluno = (Button) findViewById(R.id.lista_aluno_btnNovoAluno);
        btnNovoAluno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentVaiParaFormulario = new Intent(ListaContatos.this, FormularioActivity.class);
                startActivity(intentVaiParaFormulario);
            }
        });

    }

    private void carregaLista() {
        AlunoDAO alunoDAO = new AlunoDAO(this);
        List<Aluno> alunos = alunoDAO.buscaAlunos();
        alunoDAO.close();

        ArrayAdapter<Aluno> adapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);

        listAlunos.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        carregaLista();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo adapterMenu = (AdapterView.AdapterContextMenuInfo) menuInfo;
        final Aluno aluno = (Aluno) listAlunos.getItemAtPosition(adapterMenu.position);


        //Menu SMS
        MenuItem menuSms = menu.add("Enviar SMS");
        Intent intentSMS = new Intent(Intent.ACTION_VIEW);
        intentSMS.setData(Uri.parse("sms:" + aluno.getTelefone()));
        menuSms.setIntent(intentSMS);

        MenuItem menuVerNoMapa = menu.add("Visualizar no mapa");
        Intent intentVerNoMapa = new Intent(Intent.ACTION_VIEW);
        intentVerNoMapa.setData(Uri.parse("geo:0,0?q=" + aluno.getEndereco()));
        menuVerNoMapa.setIntent(intentVerNoMapa);

        //Menu site
        MenuItem menuSite = menu.add("Visitar site");

        String site = aluno.getSite();
        if(!site.startsWith("http://")) {
            site = "http://" + site;
        }

        Intent intentSite = new Intent(Intent.ACTION_VIEW);
        intentSite.setData(Uri.parse(site));
        menuSite.setIntent(intentSite);

        //Menu Remover
        MenuItem menuRemover = menu.add("Remover");
        menuRemover.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AlunoDAO alunoDAO = new AlunoDAO(ListaContatos.this);
                alunoDAO.remover(aluno);
                alunoDAO.close();

                carregaLista();

                return false;
            }
        });

    }
}
