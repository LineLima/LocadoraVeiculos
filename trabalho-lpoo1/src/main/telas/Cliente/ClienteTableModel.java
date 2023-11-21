package main.telas.Cliente;

import javax.swing.table.AbstractTableModel;
import main.Cliente;
import main.armazenamento.ArmazenamentoCliente;

public class ClienteTableModel extends AbstractTableModel {
    private final int NOME = 0;
    private final int SOBRENOME = 1;
    private final int RG = 2;
    private final int CPF = 3;
    private final int ENDERECO = 4;

    private final String colunas[] = {"Nome", "Sobrenome", "RG", "CPF", "Endereço"};

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public int getRowCount() {
        return ArmazenamentoCliente.obterTodos().size();
    }

    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case NOME:
            case SOBRENOME:
            case RG:
            case CPF:
            case ENDERECO:
                return String.class;
            default:
                throw new IndexOutOfBoundsException("Coluna Inválida!");
        }
    }

    @Override
    public String getColumnName(int columnIndex) {
        return colunas[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente cliente = ArmazenamentoCliente.obterTodos().get(rowIndex);

        switch (columnIndex) {
            case NOME:
                return cliente.getNome();
            case SOBRENOME:
                return cliente.getSobrenome();
            case RG:
                return cliente.getRg();
            case CPF:
                return cliente.getCpf();
            case ENDERECO:
                return cliente.getEndereco();
            default:
                throw new IndexOutOfBoundsException("Coluna Inválida!");
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex >= 0 && columnIndex <= ENDERECO;
    }

    public Cliente getValue(int rowIndex) {
        return ArmazenamentoCliente.obterTodos().get(rowIndex);
    }

    public int indexOf(Cliente cliente) {
        return ArmazenamentoCliente.obterTodos().indexOf(cliente);
    }

    public void onAdd(Cliente cliente) {
        ArmazenamentoCliente.inserir(cliente);
        fireTableDataChanged();
    }

    public void onRemove(int rowIndex) {
        ArmazenamentoCliente.excluir(getValue(rowIndex));
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public void onRemove(Cliente cliente) {
        int indexBefore = indexOf(cliente);
        ArmazenamentoCliente.excluir(cliente);
        fireTableRowsDeleted(indexBefore, indexBefore);
    }

    public void onEdit(Cliente cliente) {
        ArmazenamentoCliente.atualizar(cliente);
        fireTableDataChanged();
    }
}
