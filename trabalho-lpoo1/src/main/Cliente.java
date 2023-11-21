package main;


import java.util.List;

import main.validadores.ValidadorCliente;

public class Cliente {
    private String nome;
    private String sobrenome;
    private String rg;
    private String cpf;
    private String endereco;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobreNome) {
        this.sobrenome = sobreNome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public List<String> validar() {
        return ValidadorCliente.validar(this);
    }

    public Cliente(String nome, String sobreNome, String rg, String cpf, String endereco) {
        this.nome = nome;
        this.sobrenome = sobreNome;
        this.rg = rg;
        this.cpf = cpf;
        this.endereco = endereco;
    }
}
