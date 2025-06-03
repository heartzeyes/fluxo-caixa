package dao.parceironegocio;

import dao.ConnectionFactory;
import model.ParceiroNegocio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ParceiroNegocioImpl implements ParceiroNegocioDAO {

    @Override
    public List<ParceiroNegocio> getAll() {
        String sql = """
                SELECT *
                FROM ParceiroNegocio
                """;

        List<ParceiroNegocio> parceiros = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                parceiros.add(mapResultSetToParceiro(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar parceiros: " + e.getMessage());
        }

        return parceiros;
    }

    @Override
    public ParceiroNegocio getById(int id) {
        String sql = """
                SELECT * FROM ParceiroNegocio WHERE id = ?
                """;

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToParceiro(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar parceiro por ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void insert(ParceiroNegocio parceiro) {
        String sql = "INSERT INTO ParceiroNegocio (id, tipo) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, parceiro.getId());
            stmt.setString(2, parceiro.getTipo());

            stmt.executeUpdate();
            System.out.println("Parceiro de Negócio inserido com sucesso.");

        } catch (SQLException e) {
            System.out.println("Erro ao inserir parceiro: " + e.getMessage());
        }
    }

    @Override
    public void update(ParceiroNegocio parceiro) {
        String sql = "UPDATE ParceiroNegocio SET tipo = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, parceiro.getTipo());
            stmt.setInt(2, parceiro.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Parceiro atualizado com sucesso.");
            } else {
                System.out.println("Parceiro não encontrado para atualização.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar parceiro: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM ParceiroNegocio WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Parceiro deletado com sucesso.");
            } else {
                System.out.println("Parceiro não encontrado para exclusão.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar parceiro: " + e.getMessage());
        }
    }

    private ParceiroNegocio mapResultSetToParceiro(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String identificacaoFiscal = rs.getString("identificacaoFiscal");
        String tipo = rs.getString("tipo");

        return new ParceiroNegocio(id, nome, identificacaoFiscal, tipo);
    }
}