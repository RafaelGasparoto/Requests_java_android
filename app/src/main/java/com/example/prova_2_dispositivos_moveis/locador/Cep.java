package com.example.prova_2_dispositivos_moveis.locador;

import java.io.Serializable;

public class Cep implements Serializable {
    String cep, logradouro, complemento, bairro, localidade, uf;

    public Cep(String cep, String logradouro, String complemento, String bairro, String localidade, String uf) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.localidade = localidade;
        this.uf = uf;
    }
}
