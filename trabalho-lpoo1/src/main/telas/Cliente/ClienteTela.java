package main.telas.Cliente;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import main.Cliente;

public class ClienteTela extends JFrame {

    private JTable table = new JTable();
    private ClienteTableModel tableModel;

    private Cliente clienteSelecionado;

    private JButton btnAdd = new JButton("Adicionar Cliente");
    private JButton btnEdit = new JButton("Editar Cliente");
    private JButton btnRem = new JButton("Remover Cliente ");

    public ClienteTela() {
        this.setTitle("Cliente");
        this.setLayout(new FlowLayout());
        this.setSize(new Dimension(600, 400));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        tableModel = new ClienteTableModel();

        tableModel.addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                if (e.getType() == TableModelEvent.DELETE && tableModel.getRowCount() == 0) {
                    btnEdit.setEnabled(false);
                    btnRem.setEnabled(false);
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                btnEdit.setEnabled(true);
                btnRem.setEnabled(true);
                clienteSelecionado = tableModel.getValue(table.rowAtPoint(e.getPoint()));
            }
        });

        table.setModel(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);

        btnAdd.addActionListener(btnAddListener);
        btnEdit.addActionListener(btnEditListener);
        btnRem.addActionListener(btnRemListener);

        btnEdit.setEnabled(false);
        btnRem.setEnabled(false);

        this.add(btnAdd);
        this.add(btnEdit);
        this.add(btnRem);
    }

    private ActionListener btnAddListener = e -> {
        mostrarFormulario(null);
    };

    private ActionListener btnEditListener = e -> {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            mostrarFormulario(clienteSelecionado);
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um cliente para editar");
        }
    };

    private ActionListener btnRemListener = e -> {
        if (table.getSelectedRow() != -1 && table.getSelectedRow() < tableModel.getRowCount()) {
            tableModel.onRemove(table.getSelectedRow());
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um cliente para remover");
        }
    };

    private void mostrarFormulario(Cliente cliente) {
        try {
            ClienteFormulario form;
            if (cliente == null)
                form = new ClienteFormulario(tableModel);
            else
                form = new ClienteFormulario(tableModel, clienteSelecionado);
            
            form.setVisible(true);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
}
