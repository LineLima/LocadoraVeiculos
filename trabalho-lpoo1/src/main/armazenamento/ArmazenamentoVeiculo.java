package main.armazenamento;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import main.Veiculo;
import main.filtros.FiltrosVeiculo;

public class ArmazenamentoVeiculo {

    private static List<Veiculo> veiculos = new ArrayList<>();

    public static boolean inserir(Veiculo veiculo) {
        try {
            veiculos.add(veiculo);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean atualizar(String placa, Veiculo veiculo) {
        try {
            for (int i = 0; i < veiculos.size(); i++) {
                if (veiculos.get(i).getPlaca().equals(placa)) {
                    veiculos.set(i, veiculo);
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean excluir(Veiculo veiculo) {
        Iterator<Veiculo> iterator = veiculos.iterator();
        while (iterator.hasNext()) {
            Veiculo v = iterator.next();
            if (v.getPlaca().equals(veiculo.getPlaca())) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public static List<Veiculo> obterTodos(FiltrosVeiculo filtros) {
        if (filtros == null) {
            return veiculos;
        }

        return filtros.filtrar(veiculos);
    }

}
