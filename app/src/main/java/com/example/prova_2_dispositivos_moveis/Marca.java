package com.example.prova_2_dispositivos_moveis;

import java.util.Objects;

public class Marca {
    private String nome;
    private int id;

    public Marca(int id, String nome) {
        this.nome = nome;
        this.id = id;
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
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
    public String toString() {
        return id + " | " + nome;
    }
}
