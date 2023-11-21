//testar as funcionalidades

package main.telas.Veiculo;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import main.Veiculo;
import main.armazenamento.ArmazenamentoVeiculo;
import main.enums.Categoria;
import main.enums.Estado;
import main.enums.Marca;
import main.enums.ModeloAutomovel;
import main.enums.ModeloMotocicleta;
import main.enums.ModeloVan;
import main.enums.TipoVeiculo;
import main.filtros.FiltrosVeiculo;
import main.tiposVeiculos.Automovel;
import main.tiposVeiculos.Motocicleta;
import main.tiposVeiculos.Van;

public class VendaVeiculos extends JFrame {
    private JComboBox<TipoVeiculo> cmbFiltroTipo;
    private JComboBox<Marca> cmbFiltroMarca;
    private JComboBox<Object> cmbFiltroModelo;
    private JComboBox<Categoria> cmbFiltroCategoria;
    private JTable tblVeiculosDisponiveis;
    private JButton btnFiltrar;
    private JButton btnVender;
    private JButton btnCancelar;
    private List<Veiculo> veiculosDisponiveis;
   

    public VendaVeiculos() {
        initComponents();
    }

    private void initComponents() {
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        setLayout(new BorderLayout());

        JPanel pnlFiltros = new JPanel();
        pnlFiltros.setLayout(new FlowLayout());

        cmbFiltroModelo = new JComboBox<>();
        cmbFiltroTipo = new JComboBox<>(TipoVeiculo.values());
        cmbFiltroMarca = new JComboBox<>(Marca.values());
        cmbFiltroCategoria = new JComboBox<>(Categoria.values());

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

        JPanel pnlVenda = new JPanel();
        pnlVenda.setLayout(new FlowLayout());


        btnVender = new JButton("Vender Veículo");
        btnVender.addActionListener(e -> venderVeiculo());
        pnlVenda.add(btnVender);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        pnlVenda.add(btnCancelar);

        add(pnlVenda, BorderLayout.SOUTH);

        setVisible(true);
    }

    private <T> void comboBoxModel(JComboBox<T> comboBox, T[] values) {
        comboBox.setModel(new DefaultComboBoxModel<>(values));
    }

    private void filtrarVeiculosDisponiveis() {
        TipoVeiculo filtroTipo = (TipoVeiculo) cmbFiltroTipo.getSelectedItem();
        Marca filtroMarca = (Marca) cmbFiltroMarca.getSelectedItem();
        Categoria filtroCategoria = (Categoria) cmbFiltroCategoria.getSelectedItem();

        FiltrosVeiculo filtrosVeiculo = new FiltrosVeiculo(filtroTipo, filtroMarca, filtroCategoria, Estado.DISPONIVEL);
        veiculosDisponiveis = ArmazenamentoVeiculo.obterTodos(filtrosVeiculo);

        ((VeiculoTableModel) tblVeiculosDisponiveis.getModel()).atualizarLista(veiculosDisponiveis);
    }

    private void venderVeiculo() {
        int linhaSelecionada = tblVeiculosDisponiveis.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo disponível antes de vender.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Veiculo veiculoSelecionado = veiculosDisponiveis.get(linhaSelecionada);

        try {
            ArmazenamentoVeiculo.excluir(veiculoSelecionado);
            filtrarVeiculosDisponiveis();
            JOptionPane.showMessageDialog(this, "Veículo vendido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e){
            JOptionPane.showMessageDialog(this, "Erro ao vender o veículo.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
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
            return 5; 
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
                    double valorVenda = veiculo.getValorParaVenda();
                    return NumberFormat.getCurrencyInstance().format(valorVenda);
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
                    return "Preço Venda";
                default:
                    return null;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VendaVeiculos());
    }
}
