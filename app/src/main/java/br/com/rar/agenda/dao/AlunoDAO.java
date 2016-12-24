package br.com.rar.agenda.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import br.com.rar.agenda.modelo.Aluno;

/**
 * Created by ralmendro on 12/23/16.
 */

public class AlunoDAO extends SQLiteOpenHelper {

    public AlunoDAO(Context context) {
        super(context, "agendadb", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreateTableAlunos = "CREATE TABLE Alunos (id INTEGER PRIMARY KEY, nome TEXT NOT NULL, endereco TEXT, telefone TEXT, site TEXT, nota REAL);";
        db.execSQL(sqlCreateTableAlunos);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sqlDropTableAlunos = "DROP TABLE IF EXISTS Alunos";
        db.execSQL(sqlDropTableAlunos);
        onCreate(db);
    }

    public void insere(Aluno aluno) {
        //Obtem uma referência para o Banco de dados
        SQLiteDatabase db = this.getWritableDatabase();

        //Cria uma mapeamento para realizar a instrução insert
        ContentValues dados = new ContentValues();
        dados.put("nome", aluno.getNome());
        dados.put("endereco", aluno.getEndereco());
        dados.put("telefone", aluno.getTelefone());
        dados.put("site", aluno.getSite());
        dados.put("nota", aluno.getNota());

        db.insert("Alunos", null, dados);
    }

    public List<Aluno> buscaAlunos() {

        SQLiteDatabase db = this.getReadableDatabase();

        String sqlSelectAlunos = "SELECT * FROM Alunos";
        Cursor cursorAlunos = db.rawQuery(sqlSelectAlunos, null);

        List<Aluno> listaAlunos = new ArrayList<Aluno>();

        while (cursorAlunos.moveToNext()) {
            Aluno aluno = new Aluno();
            aluno.setId(cursorAlunos.getLong(cursorAlunos.getColumnIndex("id")))    ;
            aluno.setNome(cursorAlunos.getString(cursorAlunos.getColumnIndex("nome")));
            aluno.setEndereco(cursorAlunos.getString(cursorAlunos.getColumnIndex("endereco")));
            aluno.setTelefone(cursorAlunos.getString(cursorAlunos.getColumnIndex("telefone")));
            aluno.setSite(cursorAlunos.getString(cursorAlunos.getColumnIndex("site")));
            aluno.setNota(cursorAlunos.getDouble(cursorAlunos.getColumnIndex("nota")));
            listaAlunos.add(aluno);
        }

        cursorAlunos.close();

        return listaAlunos;
    }
}