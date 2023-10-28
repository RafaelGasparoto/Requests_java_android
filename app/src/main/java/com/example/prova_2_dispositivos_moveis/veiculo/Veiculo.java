package com.example.prova_2_dispositivos_moveis.veiculo;


import com.example.prova_2_dispositivos_moveis.Marca;

import java.io.Serializable;

public class Veiculo implements Serializable {
    public Veiculo(int ano, int idModelo, String cor, String placa) {
        this.ano = ano;
        this.idModelo = idModelo;
        this.cor = cor;
        this.placa = placa;
    }

    public int ano;

    public int getAno() {
        return ano;
    }


    public int getIdModelo() {
        return idModelo;
    }


    public String getCor() {
        return cor;
    }


    public String getPlaca() {
        return placa;
    }

    public int idModelo;
    public String cor, placa;


    @Override
    public String toString() {
        return idModelo + " | " + ano + " | " + placa + " | " +  cor;
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
