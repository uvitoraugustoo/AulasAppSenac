package com.vitoraugusto.aulas.model;

public class Professor extends Usuario {
    private String area;

    public Professor(int id, String nome, String email, String senha, String area) {
        super(id, nome, email, senha, "professor");
        this.area = area;
    }

    @Override
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
