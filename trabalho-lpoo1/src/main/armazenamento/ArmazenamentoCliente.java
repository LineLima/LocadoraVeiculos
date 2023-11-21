package main.armazenamento;
import java.util.ArrayList;
import java.util.List;

import main.Cliente;

public class ArmazenamentoCliente {

    private static List<Cliente> clientes = new ArrayList<>();

    public static boolean inserir(Cliente cliente) {
        try {
            clientes.add(cliente);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean atualizar(Cliente cliente) {
        try {
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getCpf() == cliente.getCpf()) {
                    clientes.add(i, cliente);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean excluir(Cliente cliente) {
        try {
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getCpf() == cliente.getCpf()) {
                    clientes.remove(i);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static List<Cliente> obterTodos() {
        return clientes;
    }
}