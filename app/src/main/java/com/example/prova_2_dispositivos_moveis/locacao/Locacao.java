package com.example.prova_2_dispositivos_moveis.locacao;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Locacao implements Serializable {
    public void setId(int id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    private Integer id;
    private double valor;
    private String cpf, fim, inicio, placa;

    Locacao(Integer id, double valor, String cpf, String inicio, String fim, String placa) {
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
