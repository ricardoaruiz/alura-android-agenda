package br.com.rar.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import br.com.rar.agenda.dao.AlunoDAO;
import br.com.rar.agenda.modelo.Aluno;

public class FormularioActivity extends AppCompatActivity {

    private FormularioHelper formularioHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        formularioHelper = new FormularioHelper(this);
    }


    //Sobrescreve o menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_formulario, menu);

        return super.onCreateOptionsMenu(menu);
    }

    // Adiciona comportamento aos itens do menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_formulario_ok :

                Aluno aluno = formularioHelper.getAluno();

                AlunoDAO alunoDAO = new AlunoDAO(FormularioActivity.this);

                if(aluno.getId() == null) {
                    alunoDAO.insere(aluno);
                } else {
                    alunoDAO.altera(aluno);
                }
                alunoDAO.close();

                Toast.makeText(FormularioActivity.this, aluno.getNome() + " salvo ", Toast.LENGTH_SHORT).show();
                finish( );

                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
