package dao.receita;

import dao.ConnectionFactory;
import dao.categoriafinanceira.CategoriaFinanceiraDAO;
import dao.categoriafinanceira.CategoriaFinanceiraImpl;
import dao.parceironegocio.ParceiroNegocioDAO;
import dao.parceironegocio.ParceiroNegocioImpl;
import model.Receita;
import model.CategoriaFinanceira;
import model.ParceiroNegocio;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReceitaImpl implements ReceitaDAO {

    @Override
    public List<Receita> getAll() {
        String sql = "SELECT id, valor, data, descricao, categoria_id, parceiro_id, status FROM Receita";
        List<Receita> receitas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                receitas.add(mapResultSetToReceita(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar receitas: " + e.getMessage());
        }

        return receitas;
    }

    @Override
    public List<Receita> getByPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        String sql = "SELECT id, valor, data, descricao, categoria_id, parceiro_id, status " +
                "FROM Receita WHERE data BETWEEN ? AND ? ORDER BY data";
        List<Receita> receitas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(dataInicio));
            stmt.setDate(2, Date.valueOf(dataFim));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    receitas.add(mapResultSetToReceita(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar receitas por período: " + e.getMessage());
        }

        return receitas;
    }

    @Override
    public List<Receita> getByParceiro(int id) {
        List<Receita> receitas = new ArrayList<>();
        String sql = "SELECT id, valor, data, descricao, categoria_id, parceiro_id, status FROM Receita WHERE parceiro_id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    receitas.add(mapResultSetToReceita(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar receita por ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void insert(Receita receita) {
        String sql = "INSERT INTO Receita (valor, data, descricao, categoria_id, parceiro_id, status) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setReceitaParameters(stmt, receita);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    receita.setId(rs.getInt("id"));
                    System.out.println("Receita inserida com sucesso.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir receita: " + e.getMessage());
        }
    }

    @Override
    public void update(Receita receita) {
        String sql = "UPDATE Receita SET valor = ?, data = ?, descricao = ?, categoria_id = ?, " +
                "parceiro_id = ?, status = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setReceitaParameters(stmt, receita);
            stmt.setInt(7, receita.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Receita atualizada com sucesso.");
            } else {
                System.out.println("Receita não encontrada para atualização.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar receita: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM Receita WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Receita deletada com sucesso.");
            } else {
                System.out.println("Receita não encontrada para exclusão.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar receita: " + e.getMessage());
        }
    }

    private Receita mapResultSetToReceita(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        double valor = rs.getDouble("valor");
        LocalDate data = rs.getDate("data").toLocalDate();
        String descricao = rs.getString("descricao");
        int categoriaId = rs.getInt("categoria_id");
        int parceiroId = rs.getInt("parceiro_id");
        String status = rs.getString("status");

        CategoriaFinanceiraDAO categoriaFinanceiraDAO= new CategoriaFinanceiraImpl();
        ParceiroNegocioDAO parceiroNegocioDAO = new ParceiroNegocioImpl();

        CategoriaFinanceira categoria = categoriaFinanceiraDAO.getById(categoriaId);
        ParceiroNegocio parceiro = parceiroNegocioDAO.getById(parceiroId);


        return new Receita(id, valor, data, descricao, categoria, parceiro, status);
    }

    private void setReceitaParameters(PreparedStatement stmt, Receita receita) throws SQLException {
        stmt.setDouble(1, receita.getValor());
        stmt.setDate(2, Date.valueOf(receita.getData()));
        stmt.setString(3, receita.getDescricao());
        stmt.setInt(4, receita.getCategoria().getId());
        stmt.setInt(5, receita.getParceiro().getId());
        stmt.setString(6, receita.getStatus());
    }
}