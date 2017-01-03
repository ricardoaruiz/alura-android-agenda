package br.com.rar.agenda.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.List;

import br.com.rar.agenda.client.WebClient;
import br.com.rar.agenda.converter.AlunoConverter;
import br.com.rar.agenda.dao.AlunoDAO;
import br.com.rar.agenda.modelo.Aluno;

/**
 * Created by ralmendro on 1/3/17.
 */

public class EnviaAlunosTask extends AsyncTask<Void, Void, String> {

    private Context context;
    private ProgressDialog progressDialog;

    public EnviaAlunosTask(Context context) {
        this.context = context;
    }

    //Esse método será chamado pela activity que invocou o método execute, esse método é
    //executado antes do processamento ser iniciado (antes do doInBackground).
    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context,"Processando","Enviando Alunos para cálculo de média...", true, true);
    }

    //Esse método é executado quanto for invocado o método execute por quem estiver usando essa classe.
    @Override
    protected String doInBackground(Void... params) {
        AlunoDAO alunoDAO = new AlunoDAO(context);
        List<Aluno> alunos = alunoDAO.buscaAlunos();
        alunoDAO.close();

        AlunoConverter converter = new AlunoConverter();
        String json = converter.convertToJson(alunos);

        WebClient webClient = new WebClient();
        String resultado = webClient.post(json);

        return resultado;
    }

    //Esse método será chamado pela activity que invocou o método execute, esse método só é
    //executado após o processamento completo da task (após o doInBackground).
    @Override
    protected void onPostExecute(String resposta) {
        progressDialog.dismiss();
        //Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
    }
}
