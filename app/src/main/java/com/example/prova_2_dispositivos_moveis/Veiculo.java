package com.example.prova_2_dispositivos_moveis;


public class Veiculo {
    public Veiculo(int ano, int idModelo, String cor, String placa) {
        this.ano = ano;
        this.idModelo = idModelo;
        this.cor = cor;
        this.placa = placa;
    }

    public int ano, idModelo;
    public String cor, placa;


    @Override
    public String toString() {
        return ano + " | " + placa + " | " + idModelo;
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
