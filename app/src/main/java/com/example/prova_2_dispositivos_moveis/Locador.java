package com.example.prova_2_dispositivos_moveis;

public class Locador {
    String nome, cep, complemento, cpf, email, localidade, logradouro, numero, telefone, uf;

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

}