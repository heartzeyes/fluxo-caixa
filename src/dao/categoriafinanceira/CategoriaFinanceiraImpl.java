package dao.categoriafinanceira;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import dao.ConnectionFactory;
import model.CategoriaFinanceira;

public class CategoriaFinanceiraImpl implements CategoriaFinanceiraDAO {

    @Override
    public List<CategoriaFinanceira> getAll() {
        List<CategoriaFinanceira> categorias = new ArrayList<>();

        String sql = "SELECT * FROM CategoriaFinanceira";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                categorias.add(mapResultSetToCategoria(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar categorias: " + e.getMessage());
        }

        return categorias;
    }

    @Override
    public CategoriaFinanceira getById(int id) {
        String sql = "SELECT * FROM CategoriaFinanceira WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToCategoria(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar categoria por ID: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void insert(CategoriaFinanceira categoria) {
        String sql = "INSERT INTO CategoriaFinanceira (nome, descricao) VALUES (?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Categoria inserida com sucesso.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao inserir categoria: " + e.getMessage());
        }
    }

    @Override
    public void update(CategoriaFinanceira categoria) {
        String sql = "UPDATE CategoriaFinanceira SET nome = ?, descricao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, categoria.getNome());
            stmt.setString(2, categoria.getDescricao());
            stmt.setInt(3, categoria.getId());

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Categoria atualizada com sucesso.");
            } else {
                System.out.println("Categoria não encontrada para atualização.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar categoria: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(int id) {
        String sql = "DELETE FROM CategoriaFinanceira WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                System.out.println("Categoria deletada com sucesso.");
            } else {
                System.out.println("Categoria não encontrada para exclusão.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao deletar categoria: " + e.getMessage());
        }
    }

    private CategoriaFinanceira mapResultSetToCategoria(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");
        String descricao = rs.getString("descricao");
        return new CategoriaFinanceira(id, nome, descricao);
    }
}