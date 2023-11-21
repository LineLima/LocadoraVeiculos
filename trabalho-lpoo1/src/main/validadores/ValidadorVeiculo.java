package main.validadores;

import java.util.ArrayList;
import java.util.List;
// import java.util.regex.Matcher;
// import java.util.regex.Pattern;

import main.Veiculo;
import main.enums.Estado;

public class ValidadorVeiculo {
    private static List<String> erros;

    public static List<String> validar(Veiculo veiculo) {
        erros = new ArrayList<>();

        if (veiculo == null) {
            erros.add("Veiculo está nulo");
            return erros;
        }

        validarPlaca(veiculo.getPlaca());
        validarAno(veiculo.getAno());
        validarEstadoLocacao(veiculo);
        verificaNulo("Marca", veiculo.getMarca());
        verificaNulo("Estado", veiculo.getEstado());
        verificaNulo("Categoria", veiculo.getCategoria());

        if (veiculo.getEstado() == Estado.LOCADO){
            erros.addAll(ValidadorLocacao.validar(veiculo.getLocacao()));
        }

        return erros;
    }

    private static void validarPlaca(String placa) {
        if (verificaNuloVazio("Placa", placa)) {
            return;
        }

        // padrao regex para validar placas de carro do Brasil e Mercosul
        // Pattern pattern = Pattern.compile("[A-Z]{3}[0-9]{1}[A-Z]{1}[0-9]{2}|[A-Z]{3}[0-9]{4}");
        // Matcher mat = pattern.matcher(placa);

        // if (!mat.matches()) {
        //     String mensagem = "Placa inválida";
        //     erros.add(mensagem);
        // }
    }

    private static void validarAno(int ano) {
        if (ano < 1900) {
            String mensagem = "Ano precisa ser maior ou igual a 1900";
            erros.add(mensagem);
        }
    }

    private static void validarEstadoLocacao(Veiculo veiculo) {
        if (veiculo.getLocacao() == null && veiculo.getEstado() == Estado.LOCADO) {
            String mensagem = "Veículo está locado mas sem informações de locação";
            erros.add(mensagem);
        } else if (veiculo.getLocacao() != null && veiculo.getEstado() != Estado.LOCADO) {
            String mensagem = "Veículo não está locado mas possui informações de locação";
            erros.add(mensagem);
        }
    }

    private static boolean verificaNuloVazio(String campo, String valor) {
        return verificaNulo(campo, valor) || verificaVazio(campo, valor);
    }

    private static boolean verificaVazio(String campo, String valor) {
        if (valor.isBlank()) {
            String mensagem = String.format("%s precisa ser preenchido", campo);
            erros.add(mensagem);
            return true;
        }
        return false;
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