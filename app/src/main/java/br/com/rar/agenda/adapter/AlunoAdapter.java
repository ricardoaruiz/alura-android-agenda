package br.com.rar.agenda.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.rar.agenda.modelo.Aluno;

/**
 * Created by ralmendro on 12/29/16.
 */

public class AlunoAdapter extends BaseAdapter{


    private final Context context;
    private final List<Aluno> alunos;

    public AlunoAdapter(Context context, List<Aluno> alunos) {
        this.context = context;
        this.alunos = alunos;
    }

    @Override
    public int getCount() {
        return this.alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return this.alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView = new TextView(this.context);
        textView.setText(this.alunos.get(position).toString());
        return textView;
    }
}

