package com.vitoraugusto.aulas.model;

public class Aluno extends Usuario {
    private String cpf;

    public Aluno(int id, String nome, String email, String senha, String cpf) {
        super(id, nome, email, senha, "aluno");
        this.cpf = cpf;
    }

    @Override
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
