package br.com.gestaofrota.model;

public class Caminhao extends Veiculo {

    private double capacidadeCargaToneladas;

    public Caminhao(Integer id, String marca, String modelo, int ano, double capacidadeCargaToneladas) {
        super(id, marca, modelo, ano);
        this.capacidadeCargaToneladas = capacidadeCargaToneladas;
    }

    public Caminhao(String marca, String modelo, int ano, double capacidadeCargaToneladas) {
        super(marca, modelo, ano);
        this.capacidadeCargaToneladas = capacidadeCargaToneladas;
    }

    @Override
    public String exibirFichaTecnica() {
        return super.exibirFichaTecnica() + String.format(" | Carga: %.2ft", capacidadeCargaToneladas);
    }

    public double getCapacidadeCargaToneladas() {
        return capacidadeCargaToneladas;
    }

    public void setCapacidadeCargaToneladas(double capacidadeCargaToneladas) {
        this.capacidadeCargaToneladas = capacidadeCargaToneladas;
    }
}
