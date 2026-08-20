package br.com.gestaofrota.model;

public class Carro extends Veiculo {
    private int quantidadePortas;

    public Carro(Integer id, String marca, String modelo, int ano, int quantidadePortas) {
        super(id, marca, modelo, ano);
        this.quantidadePortas = quantidadePortas;
    }

    public Carro(String marca, String modelo, int ano, int quantidadePortas) {
        super(marca, modelo, ano);
        this.quantidadePortas = quantidadePortas;
    }

    @Override
    public String exibirFichaTecnica() {
        return super.exibirFichaTecnica() + String.format(" | Portas: %d", quantidadePortas);
    }

    public int getQuantidadePortas() {
        return quantidadePortas;
    }

    public void setQuantidadePortas(int quantidadePortas) {
        this.quantidadePortas = quantidadePortas;
    }

}
