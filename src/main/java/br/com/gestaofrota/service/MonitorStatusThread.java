package br.com.gestaofrota.service;

import br.com.gestaofrota.database.ConexaoBanco;

public class MonitorStatusThread implements Runnable {
    @Override
    public void run() {
        while (true) {
            try {
                int totalVeiculos = ConexaoBanco.listarVeiculos().size();
                System.out.printf("%n[BACKGROUND-THREAD] Sync status: Banco ativo | Total de veículos cadastrados: %d%n> ",
                        totalVeiculos);

                // Intervalo de verificação: 15 segundos
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                System.out.println("[BACKGROUND-THREAD] Monitor encerrado.");
                break;
            } catch (Exception e) {
                System.err.println("[BACKGROUND-THREAD] Falha de sincronização: " + e.getMessage());
            }
        }
    }
}
