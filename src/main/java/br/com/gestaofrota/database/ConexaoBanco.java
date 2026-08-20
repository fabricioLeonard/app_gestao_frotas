package br.com.gestaofrota.database;

import br.com.gestaofrota.excecoes.DadosVeiculoInvalidosException;
import br.com.gestaofrota.model.Carro;
import br.com.gestaofrota.model.Moto;
import br.com.gestaofrota.model.Veiculo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConexaoBanco {

    // Banco de dados em arquivo local 'frota.db' na raiz do projeto
    private static final String URL_CONEXAO = "jdbc:sqlite:frota.db";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL_CONEXAO);
    }

    public static void inicializarTabela() {
        String sql = """
            CREATE TABLE IF NOT EXISTS veiculos (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                tipo TEXT NOT NULL,
                marca TEXT NOT NULL,
                modelo TEXT NOT NULL,
                ano INTEGER NOT NULL,
                detalhe_especifico INTEGER NOT NULL
            );
        """;

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
        }
    }

    public static void salvarVeiculo(Veiculo veiculo) throws SQLException, DadosVeiculoInvalidosException {
        if (veiculo.getAno() < 1900 || veiculo.getAno() > 2027) {
            throw new DadosVeiculoInvalidosException("Ano do veículo é inválido: " + veiculo.getAno());
        }

        String sql = "INSERT INTO veiculos(tipo, marca, modelo, ano, detalhe_especifico) VALUES(?, ?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, veiculo.getClass().getSimpleName().toUpperCase());
            stmt.setString(2, veiculo.getMarca());
            stmt.setString(3, veiculo.getModelo());
            stmt.setInt(4, veiculo.getAno());

            if (veiculo instanceof Carro c) {
                stmt.setInt(5, c.getQuantidadePortas());
            } else if (veiculo instanceof Moto m) {
                stmt.setInt(5, m.getCilindradas());
            }

            stmt.executeUpdate();
        }
    }

    public static List<Veiculo> listarVeiculos() throws SQLException {
        List<Veiculo> frota = new ArrayList<>();
        String sql = "SELECT * FROM veiculos";

        try (Connection conn = conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String tipo = rs.getString("tipo");
                String marca = rs.getString("marca");
                String modelo = rs.getString("modelo");
                int ano = rs.getInt("ano");
                int detalhe = rs.getInt("detalhe_especifico");

                if ("CARRO".equalsIgnoreCase(tipo)) {
                    frota.add(new Carro(id, marca, modelo, ano, detalhe));
                } else if ("MOTO".equalsIgnoreCase(tipo)) {
                    frota.add(new Moto(id, marca, modelo, ano, detalhe));
                }
            }
        }
        return frota;
    }

}
