package br.com.rar.agenda.modelo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ralmendro on 1/5/17.
 */

public class Prova implements Serializable {

    private static final long serialVersionUID = 5296644987068847310L;

    private String materia;
    private String data;
    private List<String> topicos;

    public Prova(String materia, String data, List<String> topicos) {
        this.materia = materia;
        this.data = data;
        this.topicos = topicos;
    }

    public String getMateria() {
        return materia;
    }

    public String getData() {
        return data;
    }

    public List<String> getTopicos() {
        return topicos;
    }

    @Override
    public String toString() {
        return this.materia;
    }
}
