package Infra;



import model.Despesa;
import model.CategoriaFinanceira;
import model.ParceiroNegocio;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DespesaDAO {

    public void createTable() {
        String sql = """
                CREATE TABLE IF NOT EXISTS Despesa (
                    id SERIAL PRIMARY KEY,
                    valor DECIMAL(10,2) NOT NULL,
                    data DATE NOT NULL,
                    descricao TEXT,
                    categoria_id INT REFERENCES CategoriaFinanceira(id),
                    parceiro_id INT REFERENCES ParceiroNegocio(id),
                    status VARCHAR(20),
                    paga BOOLEAN NOT NULL
                )
                """;

        try (
                Connection conn = ConnectionFactory.getConnection();
                Statement stmt = conn.createStatement()
        ) {
            stmt.executeUpdate(sql);
            System.out.println("Tabela Despesa criada com sucesso.");
        } catch (SQLException e) {
            handleSQLException("criar a tabela", e);
        }
    }

    public void insert(Despesa despesa) {
        String sql = "INSERT INTO Despesa (valor, data, descricao, categoria_id, parceiro_id, status, paga) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING id";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setDouble(1, despesa.getValor());
            stmt.setDate(2, Date.valueOf(despesa.getData()));
            stmt.setString(3, despesa.descricao);
            stmt.setInt(4, despesa.categoria.getId());
            stmt.setInt(5, despesa.parceiro.getId());
            stmt.setString(6, despesa.status);
            stmt.setBoolean(7, despesa.isPaga());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                despesa.id = rs.getInt("id");
            }
            System.out.println("Despesa inserida com sucesso.");
        } catch (SQLException e) {
            handleSQLException("inserir despesa", e);
        }
    }

    public List<Despesa> getAll() {
        String sql = "SELECT * FROM Despesa";
        List<Despesa> despesas = new ArrayList<>();

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                despesas.add(extractDespesaFromResultSet(rs));
            }
        } catch (SQLException e) {
            handleSQLException("listar despesas", e);
        }

        return despesas;
    }

    public boolean updateById(Despesa despesa) {
        String sql = "UPDATE Despesa SET valor = ?, data = ?, descricao = ?, categoria_id = ?, " +
                     "parceiro_id = ?, status = ?, paga = ? WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setDouble(1, despesa.getValor());
            stmt.setDate(2, Date.valueOf(despesa.getData()));
            stmt.setString(3, despesa.descricao);
            stmt.setInt(4, despesa.categoria.getId());
            stmt.setInt(5, despesa.parceiro.getId());
            stmt.setString(6, despesa.status);
            stmt.setBoolean(7, despesa.isPaga());
            stmt.setInt(8, despesa.id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException("atualizar despesa", e);
            return false;
        }
    }

    public boolean deleteById(int id) {
        String sql = "DELETE FROM Despesa WHERE id = ?";

        try (
                Connection conn = ConnectionFactory.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            handleSQLException("excluir despesa", e);
            return false;
        }
    }

    private Despesa extractDespesaFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        double valor = rs.getDouble("valor");
        LocalDate data = rs.getDate("data").toLocalDate();
        String descricao = rs.getString("descricao");
        int categoriaId = rs.getInt("categoria_id");
        int parceiroId = rs.getInt("parceiro_id");
        String status = rs.getString("status");
        boolean paga = rs.getBoolean("paga");

        CategoriaFinanceira categoria = new CategoriaFinanceira(categoriaId, "");
        ParceiroNegocio parceiro = new ParceiroNegocio(parceiroId, "", "", "");

        return new Despesa(id, valor, data, descricao, categoria, parceiro, status, paga);
    }

    private void handleSQLException(String operacao, SQLException e) {
        System.out.println("Erro ao " + operacao);
        e.printStackTrace();
    }
}
