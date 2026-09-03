package br.com.gestaofrota;

import br.com.gestaofrota.model.*;

public class Main {

    public static void main(String[] args) {

        Veiculo veiculo = new Veiculo(1, "VW", "Fusca", 1976);
        Carro carro = new Carro(2, "Fiat", "Uno",  1976, 4);
        Moto moto = new Moto(3, "Honda", "Biz", 2000, 50);
        Caminhao caminhao = new Caminhao(4, "VW", "1313",  1958, 1.5);
        Onibus onibus = new Onibus(5, "VW", "Buzao", 2000, 100);


        System.out.println(veiculo.exibirFichaTecnica());
        System.out.println(carro.exibirFichaTecnica());
        System.out.println(moto.exibirFichaTecnica());
        System.out.println(caminhao.exibirFichaTecnica());
        System.out.println(onibus.exibirFichaTecnica());


    }
}
