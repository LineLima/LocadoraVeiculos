package main.validadores;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import main.Locacao;

public class ValidadorLocacao {
    private static List<String> erros;

    public static List<String> validar(Locacao locacao) {
        erros = new ArrayList<>();

        if(locacao == null) {
            erros.add("Locação está nula");
            return erros;
        }

        validarDias(locacao.getDias());
        validarData(locacao.getData());
        validarValor(locacao.getValor());
        
        erros.addAll(ValidadorCliente.validar(locacao.getCliente()));
        
        return erros;
    }

    private static void validarDias(int dias) {
        if(dias < 1) {
            String mensagem = "A quantidade de dias precisa ser maior que zero";
            erros.add(mensagem);
        }
    }

    private static void validarData(Calendar data) {
        verificaNulo("Data", data);
    }

    private static void validarValor(double valor) {
        if(valor < 1) {
            String mensagem = "O valor precisa ser maior que zero";
            erros.add(mensagem);
        }
    }

    private static <T> boolean verificaNulo(String campo, T valor) {
        if (valor == null) {
            String mensagem = String.format("%s não pode ser nulo", campo);
            erros.add(mensagem);
            return true;
        }
        return false;
    }
}

