package br.com.rar.agenda;

import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.RatingBar;

import br.com.rar.agenda.modelo.Aluno;

/**
 * Created by ralmendro on 12/22/16.
 */

public class FormularioHelper {

    private FormularioActivity activity;

    private EditText campoNome;

    private EditText campoEndereco;

    private EditText campoTelefone;

    private EditText campoSite;

    RatingBar campoNota;

    public FormularioHelper(FormularioActivity activity) {

        this.activity = activity;

        campoNome = (EditText) activity.findViewById(R.id.formulario_nome);
        campoEndereco = (EditText) activity.findViewById(R.id.formulario_endereco);
        campoTelefone = (EditText) activity.findViewById(R.id.formulario_telefone);
        campoSite = (EditText) activity.findViewById(R.id.formulario_site);
        campoNota = (RatingBar) activity.findViewById(R.id.formulario_nota);
    }

    public Aluno getAluno() {

        Aluno aluno = new Aluno();

        aluno.setNome(campoNome.getText().toString());
        aluno.setEndereco(campoEndereco.getText().toString());
        aluno.setTelefone(campoTelefone.getText().toString());
        aluno.setSite(campoSite.getText().toString());
        aluno.setNota(Double.valueOf(campoNota.getProgress()));

        return aluno;

    }

}
