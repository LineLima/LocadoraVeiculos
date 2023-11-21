package main.telas.Veiculo;

import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;

import main.Veiculo;
import main.armazenamento.ArmazenamentoVeiculo;
import main.enums.Categoria;
import main.enums.Estado;
import main.enums.Marca;
import main.enums.ModeloAutomovel;
import main.enums.ModeloMotocicleta;
import main.enums.ModeloVan;
import main.enums.TipoVeiculo;
import main.telas.FormularioBase;
import main.tiposVeiculos.Automovel;
import main.tiposVeiculos.Motocicleta;
import main.tiposVeiculos.Van;

public class AdicionarVeiculoFormulario extends FormularioBase {
    private JComboBox cmbTipo = new JComboBox();
    private JComboBox cmbMarca = new JComboBox();
    private JComboBox cmbEstado = new JComboBox();
    private JComboBox cmbCategoria = new JComboBox();
    private JComboBox cmbModelo = new JComboBox();
    private JFormattedTextField txtAno = new JFormattedTextField();
    private JFormattedTextField txtValorCompra = new JFormattedTextField(NumberFormat.getNumberInstance(getLocale()));
    private JFormattedTextField txtPlaca = new JFormattedTextField();

    private JButton btnCadastrar;
    private JButton btnLimpar;
    private JButton btnCancelar;

    public AdicionarVeiculoFormulario() throws ParseException {
        super("Cadastrar Veiculo", 8);

        desenharTela();
    }

    private void desenharTela() throws ParseException {
        comboBox("Tipo", cmbTipo, TipoVeiculo.values());
        comboBox("Marca", cmbMarca, Marca.values());
        comboBox("Estado", cmbEstado, Estado.values());
        comboBox("Categoria", cmbCategoria, Categoria.values());
        comboBox("Modelo", cmbModelo, ModeloAutomovel.values());
        campoDeTextoMascarado("Ano", "####", txtAno);
        campoDeTextoMascarado("Valor de compra", null, txtValorCompra);
        campoDeTextoMascarado("Placa", "UUU-#A##", txtPlaca);

        botao("Cadastrar", btnCadastrar, cadastrar);
        botao("Limpar", btnLimpar, limpar);
        botao("Cancelar", btnCancelar, cancelar);

        cmbTipo.addActionListener(alteracaoTipo);
    }

    private ActionListener cadastrar = e -> {
        TipoVeiculo tipo = (TipoVeiculo) cmbTipo.getSelectedItem();
        Marca marca = (Marca) cmbMarca.getSelectedItem();
        Estado estado = (Estado) cmbEstado.getSelectedItem();
        Categoria categoria = (Categoria) cmbCategoria.getSelectedItem();
        int ano = Integer.parseInt(txtAno.getText());
        double valor = ((Number) txtValorCompra.getValue()).doubleValue();
        String placa = txtPlaca.getText().toUpperCase();

        Veiculo veiculo;

        switch (tipo) {
            case MOTOCICLETA:
                ModeloMotocicleta modeloMot = (ModeloMotocicleta) cmbModelo.getSelectedItem();
                veiculo = new Motocicleta(marca, estado, null, categoria, valor, placa, ano, modeloMot);
                break;
            case VAN:
                ModeloVan modeloVan = (ModeloVan) cmbModelo.getSelectedItem();
                veiculo = new Van(marca, estado, null, categoria, valor, placa, ano, modeloVan);
                break;
            case AUTOMOVEL:
            default:
                ModeloAutomovel modeloAut = (ModeloAutomovel) cmbModelo.getSelectedItem();
                veiculo = new Automovel(marca, estado, null, categoria, valor, placa, ano, modeloAut);

        }

        List<String> erros = new ArrayList<>();
        erros.addAll(veiculo.validar());

        if (!erros.isEmpty()) {
            mostrarErros(erros);
            return;
        }

        if (!confirmarInclusao()) {
            return;
        }

        ArmazenamentoVeiculo.inserir(veiculo);

        fecharTela();
    };

    private ActionListener limpar = e -> {
        if (confirmarLimpeza()) {
            cmbTipo.setSelectedIndex(0);
            cmbMarca.setSelectedIndex(0);
            cmbEstado.setSelectedIndex(0);
            cmbCategoria.setSelectedIndex(0);
            cmbModelo.setSelectedIndex(0);
            txtAno.setText("");
            txtValorCompra.setText("");
            txtPlaca.setText("");
        }
    };

    private ActionListener cancelar = e -> {
        confirmarCancelamento();
    };

    private ActionListener alteracaoTipo = e -> {
        switch ((TipoVeiculo) cmbTipo.getSelectedItem()) {
            case MOTOCICLETA:
                comboBoxModel(cmbModelo, ModeloMotocicleta.values());
                break;
            case VAN:
                comboBoxModel(cmbModelo, ModeloVan.values());
                break;
            case AUTOMOVEL:
            default:
                comboBoxModel(cmbModelo, ModeloAutomovel.values());
        }
    };
}