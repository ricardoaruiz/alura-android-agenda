package br.com.rar.agenda.task;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import br.com.rar.agenda.MediaResultAcitivity;
import br.com.rar.agenda.client.WebClient;
import br.com.rar.agenda.client.request.MediaRequest;
import br.com.rar.agenda.client.request.MediaRequestAluno;
import br.com.rar.agenda.converter.AlunoConverter;
import br.com.rar.agenda.dao.AlunoDAO;
import br.com.rar.agenda.modelo.Aluno;
import br.com.rar.agenda.client.response.MediaResponse;
import br.com.rar.agenda.utils.JsonUtil;

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

        //Monta a request com GSON
        MediaRequestAluno mediaRequestAluno = new MediaRequestAluno(alunos);
        List<MediaRequestAluno> listMediaRequestAluno = new ArrayList<MediaRequestAluno>();
        listMediaRequestAluno.add(mediaRequestAluno);
        MediaRequest request = new MediaRequest(listMediaRequestAluno);
        String gsonJson = JsonUtil.toJson(request);

        //Monta a request com JSONStringer
        AlunoConverter converter = new AlunoConverter();
        String jsonStringerJson = converter.convertToJson(alunos);

        WebClient webClient = new WebClient();
        String resultado = webClient.post(gsonJson);

        return resultado;
    }

    //Esse método será chamado pela activity que invocou o método execute, esse método só é
    //executado após o processamento completo da task (após o doInBackground).
    @Override
    protected void onPostExecute(String resposta) {

        progressDialog.dismiss();

        MediaResponse mediaResponse = (MediaResponse) JsonUtil.fromJson(resposta, MediaResponse.class);

        Intent intent = new Intent(context, MediaResultAcitivity.class);
        intent.putExtra("result", mediaResponse);
        context.startActivity(intent);

    }
}
