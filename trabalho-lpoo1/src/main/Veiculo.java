package main;

import java.time.Year;
import java.util.Calendar;
import java.util.List;

import main.enums.Categoria;
import main.enums.Estado;
import main.enums.Marca;
import main.validadores.ValidadorVeiculo;

public abstract class Veiculo implements VeiculoI {
    private Marca marca;
    private Estado estado;
    private Locacao locacao;
    private Categoria categoria;
    private double valorDeCompra;
    private String placa;
    private int ano;

    public Veiculo(Marca marca, Estado estado, Locacao locacao, Categoria categoria, double valorDeCompra, String placa, int ano) {
        this.marca = marca;
        this.estado = estado;
        this.locacao = locacao;
        this.categoria = categoria;
        this.valorDeCompra = valorDeCompra;
        this.placa = placa;
        this.ano = ano;
    }

    @Override
    public void locar(int dias, Calendar data, Cliente cliente) {
        this.estado = Estado.LOCADO;
        this.locacao = new Locacao(dias, dias, data, cliente);
    }

    @Override
    public void vender() {
        this.estado = Estado.VENDIDO;
    }

    @Override
    public void devolver() {
        this.estado = Estado.DISPONIVEL;
        this.locacao = null;
    }

    @Override
    public Estado getEstado() {
        return this.estado;
    }

    @Override
    public Marca getMarca() {
        return this.marca;
    }

    @Override
    public Categoria getCategoria() {
        return this.categoria;
    }

    @Override
    public Locacao getLocacao() {
        return this.locacao;
    }

    @Override
    public String getPlaca() {
        return this.placa;
    }

    @Override
    public int getAno() {
        return this.ano;
    }

    @Override
    public double getValorParaVenda() {
        double valor = this.valorDeCompra - ((Year.now().getValue() - this.ano) * 0.15 * this.valorDeCompra);
        double valorAlternativo = this.valorDeCompra * 0.1;
        return valor < valorAlternativo ? valorAlternativo : valor; 
    }

    @Override
    public abstract double getValorDiariaLocacao();

    public List<String> validar() {
        return ValidadorVeiculo.validar(this);
    }

 
}