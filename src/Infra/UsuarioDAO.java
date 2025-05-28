package Infra;

import model.Usuario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Usuario (
                    id INT PRIMARY KEY,
                    email VARCHAR(100) NOT NULL UNIQUE,
                    senha VARCHAR(100) NOT NULL,
                    tipo VARCHAR(20) NOT NULL
                )
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela Usuario criada com sucesso.");
        } catch (SQLException e) {
            handleSQLException("criar a tabela", e);
        }
    }

    public void insert(Usuario usuario) {
        String sql = "INSERT INTO Usuario (id, email, senha, tipo) VALUES (?, ?, ?, ?)";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, usuario.getId());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getTipo());

            stmt.executeUpdate();
            System.out.println("Usuário inserido com sucesso.");
        } catch (SQLException e) {
            handleSQLException("inserir usuário", e);
        }
    }

    public List<Usuario> getAll() {
        String sql = "SELECT * FROM Usuario";
        List<Usuario> usuarios = new ArrayList<>();

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                usuarios.add(extractUsuarioFromResultSet(rs));
            }
        } catch (SQLException e) {
            handleSQLException("listar usuários", e);
        }

        return usuarios;
    }

    public Usuario getById(int id) {
        String sql = "SELECT * FROM Usuario WHERE id = ?";
        Usuario usuario = null;

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    usuario = extractUsuarioFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            handleSQLException("buscar usuário por ID", e);
        }

        return usuario;
    }

    public boolean updateById(Usuario usuario) {
        String sql = "UPDATE Usuario SET email = ?, senha = ?, tipo = ? WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, usuario.getEmail());
            stmt.setString(2, usuario.getSenha());
            stmt.setString(3, usuario.getTipo());
            stmt.setInt(4, usuario.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            handleSQLException("atualizar usuário", e);
            return false;
        }
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM Usuario WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            handleSQLException("excluir usuário", e);
            return false;
        }
    }

    private Usuario extractUsuarioFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String email = rs.getString("email");
        String senha = rs.getString("senha");
        String tipo = rs.getString("tipo");

        // Considerando valores fictícios para os atributos herdados de Pessoa:
        return new Usuario(id, "NomeDesconhecido", "IdentificacaoDesconhecida", email, senha, tipo);
    }

    private void handleSQLException(String operacao, SQLException e) {
        System.out.println("Erro ao " + operacao);
        e.printStackTrace();
    }
}
