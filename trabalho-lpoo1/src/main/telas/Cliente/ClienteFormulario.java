package main.telas.Cliente;

import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import main.Cliente;
import main.telas.FormularioBase;

public class ClienteFormulario extends FormularioBase {
    private JTextField txtNome = new JTextField();
    private JTextField txtSobrenome = new JTextField();
    private JFormattedTextField txtRG = new JFormattedTextField();
    private JFormattedTextField txtCPF = new JFormattedTextField();
    private JTextField txtEndereco = new JTextField();

    private JButton btnCadastrar;
    private JButton btnLimpar;
    private JButton btnCancelar;

    private Cliente cliente;
    private ClienteTableModel tabela;

    private boolean edicao;

    public ClienteFormulario(ClienteTableModel tabela) throws ParseException {
        super("Cadastrar Cliente", 5);

        desenharTela();
        
        this.tabela = tabela;

        this.edicao = false;
    }

    public ClienteFormulario(ClienteTableModel tabela, Cliente cliente) throws ParseException {
        super("Editar Cliente", 5);

        desenharTela();

        this.tabela = tabela;
        this.cliente = cliente;

        carregarDadosParaEdicao();

        this.edicao = true;
    }

    private void desenharTela() throws ParseException {
        campoDeTexto("Nome", txtNome);
        campoDeTexto("Sobrenome", txtSobrenome);
        campoDeTextoMascarado("RG", "##.###.###-#", txtRG);
        campoDeTextoMascarado("CPF", "###.###.###-##", txtCPF);
        campoDeTexto("Endereço", txtEndereco);

        botao("Cadastrar", btnCadastrar, cadastrar);
        botao("Limpar", btnLimpar, limpar);
        botao("Cancelar", btnCancelar, cancelar);
    }

    private void carregarDadosParaEdicao() {
        txtNome.setText(this.cliente.getNome());
        txtSobrenome.setText(this.cliente.getSobrenome());
        txtRG.setText(this.cliente.getRg());
        txtCPF.setText(this.cliente.getCpf());
        txtEndereco.setText(this.cliente.getEndereco());
    }

    private ActionListener cadastrar = e -> {
        String nome = txtNome.getText();
        String sobrenome = txtSobrenome.getText();
        String rg = txtRG.getText();
        String cpf = txtCPF.getText();
        String endereco = txtEndereco.getText();

        Cliente cliente = new Cliente(nome, sobrenome, rg, cpf, endereco);

        List<String> erros = new ArrayList<>();
        erros.addAll(cliente.validar());

        if (!erros.isEmpty()) {
            mostrarErros(erros);
            return;
        }

        if (!confirmarInclusao()) {
            return;
        }

        if (this.edicao)
            this.tabela.onEdit(cliente);

        this.tabela.onAdd(cliente);

        fecharTela();
    };

    private ActionListener limpar = e -> {
        if (confirmarLimpeza()) {
            txtNome.setText("");
            txtSobrenome.setText("");
            txtRG.setText("");
            txtCPF.setText("");
            txtEndereco.setText("");
        }
    };

    private ActionListener cancelar = e -> {
        confirmarCancelamento();
    };
}