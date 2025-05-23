import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/fluxocaixa";
    private static final String USUARIO = "root"; // substitua se seu usuário for diferente
    private static final String SENHA = "Galo2025$";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}