
package main.telas.Veiculo;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.Cliente;
import main.Veiculo;
import main.armazenamento.ArmazenamentoCliente;
import main.armazenamento.ArmazenamentoVeiculo;
import main.enums.Categoria;
import main.enums.Estado;
import main.enums.Marca;
import main.enums.ModeloAutomovel;
import main.enums.ModeloMotocicleta;
import main.enums.ModeloVan;
import main.enums.TipoVeiculo;
import main.filtros.FiltrosCliente;
import main.filtros.FiltrosVeiculo;
import main.telas.TelaInicial;
import main.tiposVeiculos.Automovel;
import main.tiposVeiculos.Motocicleta;
import main.tiposVeiculos.Van;

public class LocacaoVeiculo extends JFrame {
    private JComboBox<String> cmbFiltroCliente;
    private JComboBox<TipoVeiculo> cmbFiltroTipo;
    private JComboBox<Marca> cmbFiltroMarca;
    private JComboBox<Object> cmbFiltroModelo;
    private JComboBox<Categoria> cmbFiltroCategoria;
    private JTable tblVeiculosDisponiveis;
    private JTextField txtDadoCliente;
    private JTextField txtDataLocacao;
    private JTextField txtDiasLocacao;

    private JButton btnFiltrar;
    private JButton btnLocar;
    private JButton btnCancelar;

    private List<Veiculo> veiculosDisponiveis;
    private Cliente clienteSelecionado;

    public LocacaoVeiculo() {
        initComponents();
    }

    private void initComponents() {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        setLayout(new BorderLayout());

        JPanel pnlFiltros = new JPanel();
        pnlFiltros.setLayout(new FlowLayout());

        cmbFiltroModelo = new JComboBox<>();
        cmbFiltroCliente = new JComboBox<>(new String[]{"Nome", "Sobrenome", "CPF"});
        txtDadoCliente = new JTextField(10);
        cmbFiltroTipo = new JComboBox<>(TipoVeiculo.values());
        cmbFiltroMarca = new JComboBox<>(Marca.values());
        cmbFiltroCategoria = new JComboBox<>(Categoria.values());

        pnlFiltros.add(new JLabel("Filtrar Cliente por:"));
        pnlFiltros.add(cmbFiltroCliente);
        pnlFiltros.add(txtDadoCliente);
        pnlFiltros.add(new JLabel("Tipo:"));
        pnlFiltros.add(cmbFiltroTipo);
        pnlFiltros.add(new JLabel("Marca:"));
        pnlFiltros.add(cmbFiltroMarca);
        pnlFiltros.add(new JLabel("Modelo:"));
        pnlFiltros.add(cmbFiltroModelo);
        pnlFiltros.add(new JLabel("Categoria:"));
        pnlFiltros.add(cmbFiltroCategoria);

        btnFiltrar = new JButton("Filtrar");
        btnFiltrar.addActionListener(e -> filtrarVeiculosDisponiveis());
        pnlFiltros.add(btnFiltrar);

        add(pnlFiltros, BorderLayout.NORTH);

        cmbFiltroTipo.addActionListener(e -> {
            switch ((TipoVeiculo) cmbFiltroTipo.getSelectedItem()) {
                case MOTOCICLETA:
                    comboBoxModel(cmbFiltroModelo, ModeloMotocicleta.values());
                    break;
                case VAN:
                    comboBoxModel(cmbFiltroModelo, ModeloVan.values());
                    break;
                case AUTOMOVEL:
                default:
                    comboBoxModel(cmbFiltroModelo, ModeloAutomovel.values());
            }
        });

        tblVeiculosDisponiveis = new JTable(new VeiculoTableModel());
        add(new JScrollPane(tblVeiculosDisponiveis), BorderLayout.CENTER);

        JPanel pnlLocacao = new JPanel();
        pnlLocacao.setLayout(new FlowLayout());

        txtDataLocacao = new JTextField(10);
        pnlLocacao.add(new JLabel("Data de Locação (dd/MM/yyyy):"));
        pnlLocacao.add(txtDataLocacao);

        txtDiasLocacao = new JTextField(10);
        pnlLocacao.add(new JLabel("Dias de Locação:"));
        pnlLocacao.add(txtDiasLocacao);

        btnLocar = new JButton("Locar Veículo");
        btnLocar.addActionListener(e -> locarVeiculo());
        pnlLocacao.add(btnLocar);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> voltarTelaInicial());
        pnlLocacao.add(btnCancelar);

        add(pnlLocacao, BorderLayout.SOUTH);

        List<Cliente> todosClientes = ArmazenamentoCliente.obterTodos();

        setVisible(true);
    }

    private <T> void comboBoxModel(JComboBox<T> comboBox, T[] values) {
        comboBox.setModel(new DefaultComboBoxModel<>(values));
    }

    private void filtrarVeiculosDisponiveis() {
        String filtroCliente = txtDadoCliente.getText();
        TipoVeiculo filtroTipo = (TipoVeiculo) cmbFiltroTipo.getSelectedItem();
        Marca filtroMarca = (Marca) cmbFiltroMarca.getSelectedItem();
        Categoria filtroCategoria = (Categoria) cmbFiltroCategoria.getSelectedItem();
        String tipoFiltroCliente = cmbFiltroCliente.getSelectedItem().toString().toLowerCase();

        FiltrosCliente filtrosCliente;

        switch (tipoFiltroCliente) {
            case "nome":
                filtrosCliente = new FiltrosCliente(filtroCliente, null, null);
                break;
            case "sobrenome":
                filtrosCliente = new FiltrosCliente(null, filtroCliente, null);
                break;
            case "cpf":
                filtrosCliente = new FiltrosCliente(null, null, filtroCliente);
                break;
            default:
                JOptionPane.showMessageDialog(this, "Tipo de filtro de cliente inválido.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
        }

        List<Cliente> clientesFiltrados = filtrosCliente.filtrar(ArmazenamentoCliente.obterTodos());

        if (clientesFiltrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum cliente encontrado com o filtro fornecido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        clienteSelecionado = clientesFiltrados.get(0);

        FiltrosVeiculo filtrosVeiculo = new FiltrosVeiculo(filtroTipo, filtroMarca, filtroCategoria, Estado.DISPONIVEL);
        veiculosDisponiveis = ArmazenamentoVeiculo.obterTodos(filtrosVeiculo);

        ((VeiculoTableModel) tblVeiculosDisponiveis.getModel()).atualizarLista(veiculosDisponiveis);
    }

    private void locarVeiculo() {
        if (clienteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um cliente antes de locar um veículo.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        int linhaSelecionada = tblVeiculosDisponiveis.getSelectedRow();
    
        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo disponível antes de locar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
    
        Veiculo veiculoSelecionado = veiculosDisponiveis.get(linhaSelecionada);
    
        try {
            int diasLocacao = Integer.parseInt(txtDiasLocacao.getText());
    
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Calendar dataLocacao = Calendar.getInstance();
            dataLocacao.setTime(dateFormat.parse(txtDataLocacao.getText()));
    
            veiculoSelecionado.locar(diasLocacao, dataLocacao, clienteSelecionado);
    
            JOptionPane.showMessageDialog(this, "Veículo locado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            ArmazenamentoVeiculo.atualizar(null, veiculoSelecionado);
        } catch (NumberFormatException | ParseException e) {
            JOptionPane.showMessageDialog(this, "Erro ao locar o veículo. Certifique-se de inserir valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
    

    private void voltarTelaInicial() {
        new TelaInicial();
        dispose();
    }

    private class VeiculoTableModel extends AbstractTableModel {
        private List<Veiculo> veiculos;

        public VeiculoTableModel() {
            this.veiculos = new ArrayList<>();
        }

        public void atualizarLista(List<Veiculo> veiculos) {
            this.veiculos = veiculos;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return veiculos.size();
        }

        @Override
        public int getColumnCount() {
            return 6;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Veiculo veiculo = veiculos.get(rowIndex);

            switch (columnIndex) {
                case 0:
                    return veiculo.getPlaca();
                case 1:
                    return veiculo.getMarca();
                case 2:
                    if (veiculo instanceof Motocicleta) {
                        return ((Motocicleta) veiculo).getModelo();
                    } else if (veiculo instanceof Automovel) {
                        return ((Automovel) veiculo).getModelo();
                    } else if (veiculo instanceof Van) {
                        return ((Van) veiculo).getModelo();
                    } else {
                        return "N/A";
                    }
                case 3:
                    return veiculo.getAno();
                case 4:
                    double valorDiaria = veiculo.getValorDiariaLocacao();
                    return NumberFormat.getCurrencyInstance().format(valorDiaria);
                case 5:
                    if (veiculo instanceof Motocicleta) {
                        return ((Motocicleta) veiculo).getModelo();
                    } else if (veiculo instanceof Van) {
                        return ((Van) veiculo).getModelo();
                    } else if (veiculo instanceof Automovel) {
                        return ((Automovel) veiculo).getModelo();
                    } else {
                        return "";
                    }
                default:
                    return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Placa";
                case 1:
                    return "Marca";
                case 2:
                    return "Modelo";
                case 3:
                    return "Ano";
                case 4:
                    return "Preço Diária";
                default:
                    return null;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LocacaoVeiculo());
    }
}
