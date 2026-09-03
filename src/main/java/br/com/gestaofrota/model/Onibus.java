package br.com.gestaofrota.model;

public class Onibus extends Veiculo{

    private int capacidadePassageiros;

    public Onibus(Integer id, String marca, String modelo, int ano, int capacidadePassageiros) {
        super(id, marca, modelo, ano);
        this.capacidadePassageiros = capacidadePassageiros;
    }

    public Onibus(String marca, String modelo, int ano, int capacidadePassageiros) {
        super(marca, modelo, ano);
        this.capacidadePassageiros = capacidadePassageiros;
    }

    @Override
    public String exibirFichaTecnica() {
        return super.exibirFichaTecnica() + String.format(" | Capacidade: %d", capacidadePassageiros);
    }

    public int getCapacidadePassageiros() {
        return capacidadePassageiros;
    }

    public void setCapacidadePassageiros(int capacidadePassageiros) {
        this.capacidadePassageiros = capacidadePassageiros;
    }
}
