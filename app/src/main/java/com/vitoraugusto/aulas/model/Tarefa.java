package com.vitoraugusto.aulas.model;

public class Tarefa {
    private int id;
    private String descricao;
    private int alunoId;
    private int professorId;
    private boolean isAprovada;   // novo
    private String status;        // opcional (pendente/aprovada/reprovada)
    private String nomeAluno;     // só para exibição quando fizer JOIN

    public Tarefa() {}

    // Construtor principal usando boolean
    public Tarefa(int id, String descricao, int alunoId, int professorId, boolean isAprovada) {
        this.id = id;
        this.descricao = descricao;
        this.alunoId = alunoId;
        this.professorId = professorId;
        setAprovada(isAprovada); // seta status também
    }

    // Construtor usando status (string)
    public Tarefa(int id, String descricao, int alunoId, int professorId, String status) {
        this.id = id;
        this.descricao = descricao;
        this.alunoId = alunoId;
        this.professorId = professorId;
        setStatus(status); // seta isAprovada também
    }

    // getters / setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getAlunoId() { return alunoId; }
    public void setAlunoId(int alunoId) { this.alunoId = alunoId; }

    public int getProfessorId() { return professorId; }
    public void setProfessorId(int professorId) { this.professorId = professorId; }

    public boolean isAprovada() { return isAprovada; }
    // ao setar o boolean, sincroniza o status
    public void setAprovada(boolean aprovada) {
        this.isAprovada = aprovada;
        this.status = aprovada ? "aprovada" : "pendente";
    }

    public String getStatus() { return status; }
    // ao setar status, sincroniza o boolean
    public void setStatus(String status) {
        this.status = status;
        if (status == null) {
            this.isAprovada = false;
        } else {
            this.isAprovada = status.equalsIgnoreCase("aprovada");
        }
    }

    public String getNomeAluno() { return nomeAluno; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }
}
