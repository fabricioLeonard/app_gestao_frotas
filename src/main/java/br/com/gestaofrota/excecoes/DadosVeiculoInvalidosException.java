package br.com.gestaofrota.excecoes;

public class DadosVeiculoInvalidosException extends RuntimeException {
    public DadosVeiculoInvalidosException(String mensagem) {
        super(mensagem);
    }
}
