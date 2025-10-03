package com.vitoraugusto.aulas.model;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String tipo; // "aluno" ou "professor"

    public Usuario() {
    }
    public Usuario(int id, String nome, String email, String senha, String tipo) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    // Getters e Setters comuns
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Métodos "genéricos" para evitar erro no DAO
    public String getCpf() {
        return null; // só existe em Aluno
    }

    public String getArea() {
        return null; // só existe em Professor
    }
}
