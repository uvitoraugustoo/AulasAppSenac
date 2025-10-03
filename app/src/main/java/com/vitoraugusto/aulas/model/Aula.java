package com.vitoraugusto.aulas.model;

public class Aula {
    private int id;
    private String titulo;
    private int alunoId;
    private int professorId;

    public Aula(int id, String titulo, int alunoId, int professorId) {
        this.id = id;
        this.titulo = titulo;
        this.alunoId = alunoId;
        this.professorId = professorId;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getAlunoId() {
        return alunoId;
    }

    public int getProfessorId() {
        return professorId;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAlunoId(int alunoId) {
        this.alunoId = alunoId;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }


}
