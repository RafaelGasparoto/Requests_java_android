package com.example.prova_2_dispositivos_moveis;

public class Marca {
    private String nome;
    private int id;

    public Marca(int id, String nome) {
        this.nome = nome;
        this.id = id;
    }

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

}
