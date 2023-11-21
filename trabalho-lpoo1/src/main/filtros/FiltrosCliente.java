package main.filtros;

import java.util.List;
import main.Cliente;

public class FiltrosCliente {
    private String filtroNome;
    private String filtroSobrenome;
    private String filtroCpf;

    public FiltrosCliente(String filtroNome, String filtroSobrenome, String filtroCpf) {
        this.filtroNome = filtroNome;
        this.filtroSobrenome = filtroSobrenome;
        this.filtroCpf = filtroCpf;
    }

    public List<Cliente> filtrar(List<Cliente> lista) {
        lista = filtrarNome(lista);
        lista = filtrarSobrenome(lista);
        lista = filtrarCpf(lista);

        return lista;
    }

    private List<Cliente> filtrarNome(List<Cliente> lista) {
        if (filtroNome == null || filtroNome.isEmpty()) {
            return lista;
        }

        return lista.stream().filter(x -> x.getNome().equalsIgnoreCase(filtroNome)).toList();
    }

    private List<Cliente> filtrarSobrenome(List<Cliente> lista) {
        if (filtroSobrenome == null || filtroSobrenome.isEmpty()) {
            return lista;
        }

        return lista.stream().filter(x -> x.getSobrenome().equalsIgnoreCase(filtroSobrenome)).toList();
    }

    private List<Cliente> filtrarCpf(List<Cliente> lista) {
        if (filtroCpf == null || filtroCpf.isEmpty()) {
            return lista;
        }

        return lista.stream().filter(x -> x.getCpf().equals(filtroCpf)).toList();
    }
}
