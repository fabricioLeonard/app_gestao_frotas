package br.com.gestaofrota.model;

public class Moto extends Veiculo {
    private int cilindradas;

    public Moto(Integer id, String marca, String modelo, int ano, int clilindradas) {
        super(id, marca, modelo, ano);
        this.cilindradas = clilindradas;
    }

    public Moto(String marca, String modelo, int ano, int clilindradas) {
        super(marca, modelo, ano);
        this.cilindradas = clilindradas;
    }

    @Override
    public String exibirFichaTecnica() {
        return super.exibirFichaTecnica() + String.format(" | Cilindradas: %dcc", cilindradas);
    }

    public int getCilindradas() {
        return cilindradas;
    }

    public void setCilindradas(int cilindradas) {
        this.cilindradas = cilindradas;
    }
}
