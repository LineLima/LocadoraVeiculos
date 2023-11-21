package main.telas;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.text.MaskFormatter;

public abstract class FormularioBase extends JFrame {

    private JPanel tela;
    private JPanel painelCampos;

    private int numeroCampos;

    private final int alturaBase = 10;
    private int alturaAtual = 10;

    private int larguraAtual = 40;

    private final int tamanhoEspaco = 5;
    private final int alturaCampo = 20;
    private final int larguraBotao = 105;
    private final int intervaloVertical = tamanhoEspaco + alturaCampo;
    private final int intervaloHorizontal = tamanhoEspaco + larguraBotao;

    private final int alturaTela = 500;
    private final int larguraTela = 450;

    public FormularioBase(String titulo, int numeroCampos) {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.numeroCampos = numeroCampos;

        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, larguraTela, alturaTela);

        tela = new JPanel();
        tela.setBackground(SystemColor.menu);
        tela.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(tela);
        tela.setLayout(null);

        painelCampos = new JPanel();
        painelCampos.setLayout(null);
        painelCampos.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
        painelCampos.setBounds(40, 55, 354, calcularAlturaPainelCampos());
        tela.add(painelCampos);

        titulo(titulo);
    }

    private int calcularAlturaPainelCampos() {
        return (alturaBase * 2) + (alturaCampo * numeroCampos) + (tamanhoEspaco * (numeroCampos - 1));
    }

    public void titulo(String texto) {
        this.setTitle(texto);
        JLabel lblTitulo = new JLabel(texto);
        lblTitulo.setHorizontalAlignment(SwingConstants.LEFT);
        lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblTitulo.setBounds(11, 12, 414, 19);
        tela.add(lblTitulo);
    }

    public void label(String texto) {
        JLabel label = new JLabel(texto);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        label.setBounds(10, alturaAtual, 95, alturaCampo);
        painelCampos.add(label);
    }

    public void campoDeTexto(String texto, JTextField campo) {
        label(texto);

        campo.setColumns(15);
        campo.setBounds(125, alturaAtual, 131, alturaCampo);
        painelCampos.add(campo);

        alturaAtual += intervaloVertical;
    }

    public void campoDeTextoMascarado(String texto, String mascara, JFormattedTextField campo) throws ParseException {
        label(texto);

        campo.setColumns(15);
        campo.setBounds(125, alturaAtual, 131, alturaCampo);
        if (mascara != null) {
            MaskFormatter maskData = new MaskFormatter(mascara);
            maskData.install(campo);
        }

        painelCampos.add(campo);

        alturaAtual += intervaloVertical;
    }

    public void comboBox(String texto, JComboBox combo, Object[] valores) {
        label(texto);

        comboBoxModel(combo, valores);

        combo.setBounds(125, alturaAtual, 131, alturaCampo);
        painelCampos.add(combo);

        alturaAtual += intervaloVertical;
    }

    public void comboBoxModel(JComboBox combo, Object[] valores) {
        if (valores != null) {
            combo.setModel(new DefaultComboBoxModel(valores));
        }
    }

    public void botao(String nome, JButton botao, ActionListener evento) {
        botao = new JButton(nome);
        botao.addActionListener(evento);
        botao.setBounds(larguraAtual, (calcularAlturaPainelCampos() + 65), larguraBotao, 23);
        tela.add(botao);

        larguraAtual += intervaloHorizontal;
    }

    protected boolean confirmarInclusao() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja incluir esse registro?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }

    protected boolean confirmarLimpeza() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja limpar todos os campos?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        return dialogResult == JOptionPane.YES_OPTION;
    }

    protected void confirmarCancelamento() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja fechar?", "Confirmar",
                JOptionPane.YES_NO_OPTION);
        if (dialogResult == JOptionPane.YES_OPTION) {
            fecharTela();
        }
    }

    protected void fecharTela() {
        this.setVisible(false);
        this.dispose();
    }

    protected void mostrarErros(List<String> erros) {
        String mensagem = "Por favor corrigir os erros abaixo:\n";
        for (String erro : erros) {
            mensagem += "- " + erro + '\n';
        }
        JOptionPane.showMessageDialog(null, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
    }
}
