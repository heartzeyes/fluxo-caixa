package dao.despesa;

import dao.ConnectionFactory;
import dao.categoriafinanceira.CategoriaFinanceiraDAO;
import dao.categoriafinanceira.CategoriaFinanceiraImpl;
import dao.parceironegocio.ParceiroNegocioDAO;
import dao.parceironegocio.ParceiroNegocioImpl;
import model.Despesa;
import model.CategoriaFinanceira;
import model.ParceiroNegocio;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DespesaImpl implements DespesaDAO {

    @Override
    public List<Despesa> getAll() {
        String sql = "SELECT id, valor, data, descricao, categoria_id, parceiro_id, status, paga FROM Despesa";
        List<Despesa> despesas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                despesas.add(mapResultSetToDespesa(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar despesas: " + e.getMessage());
        }

        return despesas;
    }

    @Override
    public List<Despesa> getByPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        String sql = "SELECT id, valor, data, descricao, categoria_id, parceiro_id, status, paga " +
                "FROM Despesa WHERE data BETWEEN ? AND ? ORDER BY data";
        List<Despesa> despesas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(dataInicio));
            stmt.setDate(2, Date.valueOf(dataFim));

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    despesas.add(mapResultSetToDespesa(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar despesas por período: " + e.getMessage());
        }

        return despesas;
    }
    @Override
    public List<Despesa> getByParceiro(int id) {
        String sql = "SELECT id, valor, data, descricao, categoria_id, parceiro_id, status, paga FROM Despesa WHERE parceiro_id = ?";
        List<Despesa> despesas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    despesas.add(mapResultSetToDespesa(rs));
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar despesa por ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void insert(Despesa despesa) {
        String sql = "INSERT INTO Despesa (valor, data, descricao, categoria_id, parceiro_id, status, paga) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setDespesaParameters(stmt, despesa);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    despesa.setId(rs.getInt("id"));
                    System.out.println("Despesa inserida com sucesso.");
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir despesa: " + e.getMessage());
        }
    }

    @Override
    public void update(Despesa despesa) {
        String sql = "UPDATE Despesa SET valor = ?, data = ?, descricao = ?, categoria_id = ?, " +
                "parceiro_id = ?, status = ?, paga = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            setDespesaParameters(stmt, despesa);
            stmt.setInt(8, despesa.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Despesa atualizada com sucesso.");
            } else {
                System.out.println("Despesa não encontrada para atualização.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar despesa: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM Despesa WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Despesa deletada com sucesso.");
            } else {
                System.out.println("Despesa não encontrada para exclusão.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar despesa: " + e.getMessage());
        }
    }

    private Despesa mapResultSetToDespesa(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        double valor = rs.getDouble("valor");
        LocalDate data = rs.getDate("data").toLocalDate();
        String descricao = rs.getString("descricao");
        int categoriaId = rs.getInt("categoria_id");
        int parceiroId = rs.getInt("parceiro_id");
        String status = rs.getString("status");
        boolean paga = rs.getBoolean("paga");

        CategoriaFinanceiraDAO categoriaFinanceiraDAO= new CategoriaFinanceiraImpl();
        ParceiroNegocioDAO parceiroNegocioDAO = new ParceiroNegocioImpl();

        CategoriaFinanceira categoria = categoriaFinanceiraDAO.getById(categoriaId);
        ParceiroNegocio parceiro = parceiroNegocioDAO.getById(parceiroId);

        return new Despesa(id, valor, data, descricao, categoria, parceiro, status, paga);
    }

    private void setDespesaParameters(PreparedStatement stmt, Despesa despesa) throws SQLException {
        stmt.setDouble(1, despesa.getValor());
        stmt.setDate(2, Date.valueOf(despesa.getData()));
        stmt.setString(3, despesa.getDescricao());
        stmt.setInt(4, despesa.getCategoria().getId());
        stmt.setInt(5, despesa.getParceiro().getId());
        stmt.setString(6, despesa.getStatus());
        stmt.setBoolean(7, despesa.isPaga());
    }
}