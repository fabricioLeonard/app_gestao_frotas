package br.com.gestaofrota.model;

public class Veiculo {

    // 1 - Atributos
    private Integer id;
    private String marca;
    private String modelo;
    private int ano;

    // 2 - Construtor
    public Veiculo() {
    }

    public Veiculo(Integer id, String marca, String modelo, int ano) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
    }

    public Veiculo(String marca, String modelo, int ano) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
    }

    // 3 - Método polimórfico
    public String exibirFichaTecnica() {
        return String.format("[%s] %s %s - Ano: %d",
                getClass().getSimpleName().toUpperCase(), marca, modelo, ano);
    }

    // 4 - Getters
    public Integer getId() {
        return id;
    }

    public String getMarca() {
        return marca;
    }

    public String getModelo() {
        return modelo;
    }

    public int getAno() {
        return ano;
    }

    // 5 - Setters
    public void setId(Integer id) {
        this.id = id;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }
}
