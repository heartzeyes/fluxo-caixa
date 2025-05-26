package infra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.ParceiroNegocio;

public class ParceiroNegocioDAO {

    private PessoaDAO pessoaDAO = new PessoaDAO();

    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS ParceiroNegocio (
                    id INT PRIMARY KEY,
                    tipo VARCHAR(50) NOT NULL,
                    FOREIGN KEY (id) REFERENCES Pessoa(id)
                )
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela ParceiroNegocio criada com sucesso.");
        } catch (SQLException e) {
            handleSQLException("criar a tabela", e);
        }
    }

    public void insert(ParceiroNegocio parceiro) {
        // Primeiro insere na tabela Pessoa
        pessoaDAO.insert(parceiro);

        String sql = "INSERT INTO ParceiroNegocio (id, tipo) VALUES (?, ?)";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, parceiro.getId());
            stmt.setString(2, parceiro.getTipo());

            stmt.executeUpdate();
            System.out.println("Parceiro de Negócio inserido com sucesso.");
        } catch (SQLException e) {
            handleSQLException("inserir parceiro de negócio", e);
        }
    }

    public List<ParceiroNegocio> getAll() {
        String sql = """
                SELECT p.id, p.nome, p.identificacaoFiscal, pn.tipo
                FROM Pessoa p
                INNER JOIN ParceiroNegocio pn ON p.id = pn.id
                """;

        List<ParceiroNegocio> parceiros = new ArrayList<>();

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                parceiros.add(extractParceiroFromResultSet(rs));
            }
        } catch (SQLException e) {
            handleSQLException("listar parceiros de negócio", e);
        }

        return parceiros;
    }

    public ParceiroNegocio getById(int id) {
        String sql = """
                SELECT p.id, p.nome, p.identificacaoFiscal, pn.tipo
                FROM Pessoa p
                INNER JOIN ParceiroNegocio pn ON p.id = pn.id
                WHERE p.id = ?
                """;

        ParceiroNegocio parceiro = null;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    parceiro = extractParceiroFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            handleSQLException("buscar parceiro de negócio por ID", e);
        }

        return parceiro;
    }

    public boolean updateById(ParceiroNegocio parceiro) {
        // Atualiza dados em Pessoa
        boolean pessoaAtualizada = pessoaDAO.updateById(parceiro);

        String sql = "UPDATE ParceiroNegocio SET tipo = ? WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, parceiro.getTipo());
            stmt.setInt(2, parceiro.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 && pessoaAtualizada;
        } catch (SQLException e) {
            handleSQLException("atualizar parceiro de negócio", e);
            return false;
        }
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM ParceiroNegocio WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                return pessoaDAO.deleteById(id);
            } else {
                return false;
            }
        } catch (SQLException e) {
            handleSQLException("excluir parceiro de negócio", e);
            return false;
        }
    }

    private ParceiroNegocio extractParceiroFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String identificacaoFiscal = rs.getString("identificacaoFiscal");
        String tipo = rs.getString("tipo");

        return new ParceiroNegocio(id, nome, identificacaoFiscal, tipo);
    }

    private void handleSQLException(String operacao, SQLException e) {
        System.out.println("Erro ao " + operacao);
        e.printStackTrace();
    }
}
