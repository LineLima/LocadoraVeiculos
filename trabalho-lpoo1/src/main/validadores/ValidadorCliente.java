package main.validadores;

import java.util.ArrayList;
import java.util.List;

import main.Cliente;

public class ValidadorCliente {
    private static List<String> erros;

    public static List<String> validar(Cliente cliente) {
        erros = new ArrayList<>();

        if (cliente == null) {
            erros.add("Cliente está nulo");
            return erros;
        }

        validarNome(cliente.getNome());
        validarSobreNome(cliente.getSobrenome());
        validarRG(cliente.getRg());
        validarCPF(cliente.getCpf());
        validarEndereco(cliente.getEndereco());

        return erros;
    }

    private static void validarNome(String nome) {
        verificaNuloVazio("Nome", nome);
    }

    private static void validarSobreNome(String sobreNome) {
        verificaNuloVazio("Sobrenome", sobreNome);
    }

    private static void validarRG(String rg) {
        if (verificaNuloVazio("RG", rg)) {
            return;
        }
        verificaPadrao("RG", rg, "  .   .   - ");
    }

    private static void validarCPF(String cpf) {
        if (verificaNuloVazio("CPF", cpf)) {
            return;
        }
        verificaPadrao("CPF", cpf, "   .   .   -  ");
        // implementar validacao de CPF?
    }

    private static void validarEndereco(String endereco) {
        verificaNuloVazio("Endereco", endereco);
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

    private static void verificaPadrao(String campo, String valor, String padrao) {
        if (valor.equals(padrao)) {
            String mensagem = String.format("%s precisa ser preenchido", campo);
            erros.add(mensagem);
        }
    }
}
