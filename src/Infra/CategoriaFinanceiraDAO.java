package infra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.CategoriaFinanceira;

public class CategoriaFinanceiraDAO {

    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS CategoriaFinanceira (
                    id SERIAL PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL UNIQUE
                )
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela CategoriaFinanceira criada com sucesso.");
        } catch (SQLException e) {
            handleSQLException("criar a tabela", e);
        }
    }

    public void insert(CategoriaFinanceira categoria) {
        String sql = "INSERT INTO CategoriaFinanceira (nome) VALUES (?) RETURNING id";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, categoria.getNome());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                categoria.setId(rs.getInt("id"));
            }
            System.out.println("CategoriaFinanceira inserida com sucesso.");
        } catch (SQLException e) {
            handleSQLException("inserir categoria", e);
        }
    }

    public List<CategoriaFinanceira> getAll() {
        String sql = "SELECT * FROM CategoriaFinanceira";
        List<CategoriaFinanceira> categorias = new ArrayList<>();

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                categorias.add(extractCategoriaFromResultSet(rs));
            }
        } catch (SQLException e) {
            handleSQLException("listar categorias", e);
        }

        return categorias;
    }

    public CategoriaFinanceira getById(int id) {
        String sql = "SELECT * FROM CategoriaFinanceira WHERE id = ?";
        CategoriaFinanceira categoria = null;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    categoria = extractCategoriaFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            handleSQLException("buscar categoria por ID", e);
        }

        return categoria;
    }

    public boolean updateById(CategoriaFinanceira categoria) {
        String sql = "UPDATE CategoriaFinanceira SET nome = ? WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, categoria.getNome());
            stmt.setInt(2, categoria.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            handleSQLException("atualizar categoria", e);
            return false;
        }
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM CategoriaFinanceira WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            handleSQLException("excluir categoria", e);
            return false;
        }
    }

    private CategoriaFinanceira extractCategoriaFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String nome = rs.getString("nome");

        return new CategoriaFinanceira(id, nome);
    }

    private void handleSQLException(String operacao, SQLException e) {
        System.out.println("Erro ao " + operacao);
        e.printStackTrace();
    }
}
