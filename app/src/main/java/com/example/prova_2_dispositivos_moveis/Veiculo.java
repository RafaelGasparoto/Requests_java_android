package com.example.prova_2_dispositivos_moveis;


import androidx.annotation.NonNull;

public class Veiculo {
    public Veiculo(int ano, int idModel, String cor, String placa, Modelo modelo) {
        this.ano = ano;
        this.idModel = idModel;
        this.cor = cor;
        this.placa = placa;
        this.modelo = modelo;
    }

    public int ano, idModel;
    public String cor, placa;
    public Modelo modelo;


    @Override
    public String toString() {
        return ano + " | " + placa + " | " + modelo;
    }
}

class Modelo {
    public String categoria, descricao;
    public int id, valorDiaria;

    public Modelo(String categoria, String descricao, int id, int valorDiaria, Marca marca) {
        this.categoria = categoria;
        this.descricao = descricao;
        this.id = id;
        this.valorDiaria = valorDiaria;
        this.marca = marca;
    }

    public Marca marca;

    @Override
    public String toString() {
        return id + " | " + descricao;
    }
}
