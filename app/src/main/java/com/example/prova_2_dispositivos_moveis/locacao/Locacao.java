package com.example.prova_2_dispositivos_moveis.locacao;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Locacao implements Serializable {
    private int id, valor;
    private String cpf, fim, inicio, placa;

    Locacao(int id, int valor, String cpf, String inicio, String fim, String placa) {
        this.id = id;
        this.valor = valor;
        this.cpf = cpf;
        this.inicio = inicio;
        this.fim = fim;
        this.placa = placa;
    }

    public int getId() {
        return id;
    }

    @NonNull
    @Override
    public String toString() {
        return id + " | " + cpf + " | " + placa + " | " + valor;
    }
}
