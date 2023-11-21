package main.tiposVeiculos;
import main.Locacao;
import main.Veiculo;
import main.enums.Categoria;
import main.enums.Estado;
import main.enums.Marca;
import main.enums.ModeloVan;

public class Van extends Veiculo {
    private ModeloVan modelo;

    public ModeloVan getModelo() {
        return modelo;
    }

    public Van(Marca marca, Estado estado, Locacao locacao, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloVan modelo) {
        super(marca, estado, locacao, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    @Override
    public double getValorDiariaLocacao() {
        switch (this.getCategoria()) {
            case POPULAR:
                return 200.0;
            case INTERMEDIARIO:
                return 400.0;
            case LUXO:
                return 600.0;
        }
        return 0.0;
    }
}
