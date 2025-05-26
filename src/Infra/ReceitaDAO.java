package infra;

import model.Receita;
import model.CategoriaFinanceira;
import model.ParceiroNegocio;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ReceitaDAO {

    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Receita (
                    id SERIAL PRIMARY KEY,
                    valor DECIMAL(10,2) NOT NULL,
                    data DATE NOT NULL,
                    descricao TEXT,
                    categoria_id INT REFERENCES CategoriaFinanceira(id),
                    parceiro_id INT REFERENCES ParceiroNegocio(id),
                    status VARCHAR(20)
                )
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela Receita criada com sucesso.");
        } catch (SQLException e) {
            handleSQLException("criar a tabela", e);
        }
    }

    public void insert(Receita receita) {
        String sql = "INSERT INTO Receita (valor, data, descricao, categoria_id, parceiro_id, status) " +
                     "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setDouble(1, receita.getValor());
            stmt.setDate(2, Date.valueOf(receita.getData()));
            stmt.setString(3, receita.descricao);
            stmt.setInt(4, receita.categoria.getId());
            stmt.setInt(5, receita.parceiro.getId());
            stmt.setString(6, receita.status);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                receita.id = rs.getInt("id");
            }
            System.out.println("Receita inserida com sucesso.");
        } catch (SQLException e) {
            handleSQLException("inserir receita", e);
        }
    }

    public List<Receita> getAll() {
        String sql = "SELECT * FROM Receita";
        List<Receita> receitas = new ArrayList<>();

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                receitas.add(extractReceitaFromResultSet(rs));
            }
        } catch (SQLException e) {
            handleSQLException("listar receitas", e);
        }

        return receitas;
    }

    public boolean updateById(Receita receita) {
        String sql = "UPDATE Receita SET valor = ?, data = ?, descricao = ?, categoria_id = ?, " +
                     "parceiro_id = ?, status = ? WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setDouble(1, receita.getValor());
            stmt.setDate(2, Date.valueOf(receita.getData()));
            stmt.setString(3, receita.descricao);
            stmt.setInt(4, receita.categoria.getId());
            stmt.setInt(5, receita.parceiro.getId());
            stmt.setString(6, receita.status);
            stmt.setInt(7, receita.id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException("atualizar receita", e);
            return false;
        }
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM Receita WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException("excluir receita", e);
            return false;
        }
    }

    private Receita extractReceitaFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        double valor = rs.getDouble("valor");
        LocalDate data = rs.getDate("data").toLocalDate();
        String descricao = rs.getString("descricao");
        int categoriaId = rs.getInt("categoria_id");
        int parceiroId = rs.getInt("parceiro_id");
        String status = rs.getString("status");

        CategoriaFinanceira categoria = new CategoriaFinanceira(categoriaId, "");
        ParceiroNegocio parceiro = new ParceiroNegocio(parceiroId, "", "", "");

        return new Receita(id, valor, data, descricao, categoria, parceiro, status);
    }

    private void handleSQLException(String operacao, SQLException e) {
        System.out.println("Erro ao " + operacao);
        e.printStackTrace();
    }
}
