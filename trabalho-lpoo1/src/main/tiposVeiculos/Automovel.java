package main.tiposVeiculos;
import main.Locacao;
import main.Veiculo;
import main.enums.Categoria;
import main.enums.Estado;
import main.enums.Marca;
import main.enums.ModeloAutomovel;

public class Automovel extends Veiculo {
    private ModeloAutomovel modelo;

    public ModeloAutomovel getModelo() {
        return modelo;
    }

    public Automovel(Marca marca, Estado estado, Locacao locacao, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloAutomovel modelo) {
        super(marca, estado, locacao, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    @Override
    public double getValorDiariaLocacao() {
        switch (this.getCategoria()) {
            case POPULAR:
                return 100.0;
            case INTERMEDIARIO:
                return 300.0;
            case LUXO:
                return 450.0;
        }
        return 0.0;
    }
}
