package main.tiposVeiculos;
import main.Locacao;
import main.Veiculo;
import main.enums.Categoria;
import main.enums.Estado;
import main.enums.Marca;
import main.enums.ModeloMotocicleta;

public class Motocicleta extends Veiculo {
    private ModeloMotocicleta modelo;

    public ModeloMotocicleta getModelo() {
        return modelo;
    }

    public Motocicleta(Marca marca, Estado estado, Locacao locacao, Categoria categoria, double valorDeCompra, String placa, int ano, ModeloMotocicleta modelo) {
        super(marca, estado, locacao, categoria, valorDeCompra, placa, ano);
        this.modelo = modelo;
    }

    @Override
    public double getValorDiariaLocacao() {
        switch (this.getCategoria()) {
            case POPULAR:
                return 70.0;
            case INTERMEDIARIO:
                return 200.0;
            case LUXO:
                return 350.0;
        }
        return 0.0;
    }
}
