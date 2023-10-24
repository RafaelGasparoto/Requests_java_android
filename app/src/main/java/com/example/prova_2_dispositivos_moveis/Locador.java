package com.example.prova_2_dispositivos_moveis;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Locador implements Serializable {
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocalidade() {
        return localidade;
    }

    public void setLocalidade(String localidade) {
        this.localidade = localidade;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    private String nome, cep, complemento, cpf, email, localidade, logradouro, numero, telefone, uf;

    public Locador(String nome, String cep, String complemento, String cpf, String email, String localidade, String logradouro, String numero, String telefone, String uf) {
        this.nome = nome;
        this.cep = cep;
        this.complemento = complemento;
        this.cpf = cpf;
        this.email = email;
        this.localidade = localidade;
        this.logradouro = logradouro;
        this.numero = numero;
        this.telefone = telefone;
        this.uf = uf;
    }

    @NonNull
    @Override
    public String toString() {
        return nome + " | " + cpf;
    }
}