package main.filtros;

import java.util.List;

import main.Veiculo;
import main.enums.Categoria;
import main.enums.Estado;
import main.enums.Marca;
import main.enums.TipoVeiculo;
import main.tiposVeiculos.Automovel;
import main.tiposVeiculos.Motocicleta;
import main.tiposVeiculos.Van;

public class FiltrosVeiculo {
    private TipoVeiculo tipoVeiculo;
    private Marca marcaVeiculo;
    private Categoria categoriaVeiculo;
    private Estado estadoVeiculo;

    public FiltrosVeiculo(TipoVeiculo tipoVeiculo, Marca marcaVeiculo, Categoria categoriaVeiculo, Estado estadoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
        this.marcaVeiculo = marcaVeiculo;
        this.categoriaVeiculo = categoriaVeiculo;
        this.estadoVeiculo = estadoVeiculo;
    }

    public List<Veiculo> filtrar(List<Veiculo> lista) {
        lista = filtrarTipo(lista);
        lista = filtrarMarca(lista);
        lista = filtrarCategoria(lista);
        lista = filtrarEstado(lista);

        return lista;
    }

    private List<Veiculo> filtrarTipo(List<Veiculo> lista) {
        if (tipoVeiculo == null) {
            return lista;
        }

        return lista.stream().filter(x -> obterTipoVeiculo(x) == tipoVeiculo).toList();
    }

    private List<Veiculo> filtrarMarca(List<Veiculo> lista) {
        if (marcaVeiculo == null) {
            return lista;
        }

        return lista.stream().filter(x -> x.getMarca() == marcaVeiculo).toList();
    }

    private List<Veiculo> filtrarCategoria(List<Veiculo> lista) {
        if (categoriaVeiculo == null) {
            return lista;
        }

        return lista.stream().filter(x -> x.getCategoria() == categoriaVeiculo).toList();
    }

    private List<Veiculo> filtrarEstado(List<Veiculo> lista) {
        if (estadoVeiculo == null) {
            return lista;
        }

        return lista.stream().filter(x -> x.getEstado() == estadoVeiculo).toList();
    }

    private TipoVeiculo obterTipoVeiculo(Veiculo veiculo) {
        if (veiculo instanceof Automovel)
            return TipoVeiculo.AUTOMOVEL;
        if (veiculo instanceof Motocicleta)
            return TipoVeiculo.MOTOCICLETA;
        if (veiculo instanceof Van)
            return TipoVeiculo.VAN;
        return null;
    }
}
