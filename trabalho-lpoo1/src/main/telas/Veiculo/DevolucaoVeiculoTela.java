
package main.telas.Veiculo;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import main.Cliente;
import main.Locacao;
import main.Veiculo;
import main.tiposVeiculos.Automovel;
import main.tiposVeiculos.Motocicleta;
import main.tiposVeiculos.Van;

public class DevolucaoVeiculoTela extends JFrame {
    private JTable tblVeiculosLocados;
    private JButton btnDevolverVeiculo;

    private List<Veiculo> veiculosLocados;

    public DevolucaoVeiculoTela(List<Veiculo> veiculosLocados) {
        this.veiculosLocados = veiculosLocados;
        initComponents();
    }

    private void initComponents() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);

        setLayout(new BorderLayout());

        tblVeiculosLocados = new JTable(new VeiculoTableModel());
        add(new JScrollPane(tblVeiculosLocados), BorderLayout.CENTER);

        btnDevolverVeiculo = new JButton("Devolver Veículo");
        btnDevolverVeiculo.addActionListener(e -> devolverVeiculo());
        add(btnDevolverVeiculo, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void devolverVeiculo() {
        int linhaSelecionada = tblVeiculosLocados.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para devolver.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Veiculo veiculoSelecionado = veiculosLocados.get(linhaSelecionada);
        veiculoSelecionado.devolver();
        veiculosLocados.remove(veiculoSelecionado);

        ((VeiculoTableModel) tblVeiculosLocados.getModel()).atualizarLista(veiculosLocados);

        JOptionPane.showMessageDialog(this, "Veículo devolvido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private class VeiculoTableModel extends AbstractTableModel {
        private List<Veiculo> veiculos;

        public VeiculoTableModel() {
            this.veiculos = new ArrayList<>(veiculosLocados);
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
            return 9;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Veiculo veiculo = veiculos.get(rowIndex);
            Locacao locacao = veiculo.getLocacao();
            Cliente cliente = locacao.getCliente();

            switch (columnIndex) {
                case 0:
                    return cliente.getNome();
                case 1:
                    return veiculo.getPlaca();
                case 2:
                    return veiculo.getMarca();
                case 3:
                      if (veiculo instanceof Motocicleta) {
                        return ((Motocicleta) veiculo).getModelo();
                    } else if (veiculo instanceof Automovel) {
                        return ((Automovel) veiculo).getModelo();
                    } else if (veiculo instanceof Van) {
                        return ((Van) veiculo).getModelo();
                    } else {
                        return "N/A";
                    }
                case 4:
                    return veiculo.getAno();
                case 5:
                    return locacao.getData();
                case 6:
                    if (veiculo instanceof Motocicleta) {
                        return ((Motocicleta) veiculo).getValorDiariaLocacao();
                    } else if (veiculo instanceof Automovel) {
                        return ((Automovel) veiculo).getValorDiariaLocacao();
                    } else if (veiculo instanceof Van) {
                        return ((Van) veiculo).getValorDiariaLocacao();
                    } else {
                        return "N/A";
                    }
                case 7:
                    return locacao.getDias();
                case 8:
                    if (veiculo instanceof Motocicleta) {
                        return ((Motocicleta) veiculo).getValorDiariaLocacao() * locacao.getDias();
                    } else if (veiculo instanceof Automovel) {
                        return ((Automovel) veiculo).getValorDiariaLocacao() * locacao.getDias();
                    } else if (veiculo instanceof Van) {
                        return ((Van) veiculo).getValorDiariaLocacao() * locacao.getDias();
                    } else {
                        return "N/A";
                    }
                default:
                    return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            switch (column) {
                case 0:
                    return "Nome do Cliente";
                case 1:
                    return "Placa";
                case 2:
                    return "Marca";
                case 3:
                    return "Modelo";
                case 4:
                    return "Ano";
                case 5:
                    return "Data Locação";
                case 6:
                    return "Preço Diária";
                case 7:
                    return "Quantidade de dias locado";
                case 8:
                    return "Valor Total Locação";
                default:
                    return null;
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return false;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Veiculo> veiculosLocados = new ArrayList<>();
            new DevolucaoVeiculoTela(veiculosLocados);
        });
    }
}
