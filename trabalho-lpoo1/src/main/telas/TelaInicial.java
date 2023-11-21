package main.telas;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.Veiculo;
import main.armazenamento.ArmazenamentoVeiculo;
import main.enums.Estado;
import main.filtros.FiltrosVeiculo;
import main.telas.Cliente.ClienteTela;
import main.telas.Veiculo.AdicionarVeiculoFormulario;
import main.telas.Veiculo.DevolucaoVeiculoTela;
import main.telas.Veiculo.LocacaoVeiculo;
import main.telas.Veiculo.VendaVeiculos;

public class TelaInicial extends JFrame {
    public TelaInicial() {
        setTitle("Tela Inicial");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(475, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));

        JButton btnCliente = new JButton("Gerenciar Clientes");
        JButton btnVeiculo = new JButton("Adicionar Veículos");
        JButton btnLocacao = new JButton("Alugar Veículos");
        JButton btnDevolucao = new JButton("Devolver Veículos");
        JButton btnVendaVeiculos = new JButton("Vender Veículos");

        btnCliente.addActionListener(telaClientes);
        btnVeiculo.addActionListener(telaAdicionarVeiculo);
        btnLocacao.addActionListener(telaLocacao);
        btnDevolucao.addActionListener(telaDevolucaoVeiculo);
        btnVendaVeiculos.addActionListener(telaVenda);

        panel.add(btnCliente);
        panel.add(btnVeiculo);
        panel.add(btnLocacao);
        panel.add(btnDevolucao);
        panel.add(btnVendaVeiculos);

        getContentPane().add(panel);
    }

    private ActionListener telaClientes = e -> {
        ClienteTela tela = new ClienteTela();
        tela.setVisible(true);
    };

    private ActionListener telaAdicionarVeiculo = e -> {
        AdicionarVeiculoFormulario tela;
        try {
            tela = new AdicionarVeiculoFormulario();
            tela.setVisible(true);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
    };

    
    private ActionListener telaLocacao = e -> {
        SwingUtilities.invokeLater(() -> {
            LocacaoVeiculo tela = new LocacaoVeiculo();
            tela.setVisible(true);
        });
    };

      private ActionListener telaDevolucaoVeiculo = e -> {
        SwingUtilities.invokeLater(() -> {
            FiltrosVeiculo filtro = new FiltrosVeiculo(null, null, null, Estado.LOCADO);
            List<Veiculo> veiculos = ArmazenamentoVeiculo.obterTodos(filtro);
            DevolucaoVeiculoTela tela = new DevolucaoVeiculoTela(veiculos);
            tela.setVisible(true);
        });
    };


    private ActionListener telaVenda = e -> {
        SwingUtilities.invokeLater(() -> {
            VendaVeiculos tela = new VendaVeiculos();
            tela.setVisible(true);
        });
    };

    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TelaInicial().setVisible(true));
    }
}
