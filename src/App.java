import Infra.UsuarioDAO;
import model.*;

public class App {
    public static void main(String[] args) {
        // CategoriaFinanceira cat = new CategoriaFinanceira(1, "Vendas");
        // ParceiroNegocio cliente = new ParceiroNegocio(1, "Cliente A", "12345678900", "cliente");
        // Receita r1 = new Receita(1, 500.0, LocalDate.now(), "Venda A", cat, cliente, "liquidado");

        // FluxoCaixa caixa = new FluxoCaixa();
        // caixa.adicionarReceita(r1);

        // System.out.println("Total Receitas: R$ " + caixa.calcularTotalReceitas(LocalDate.now().minusDays(1), LocalDate.now().plusDays(1)));

        Usuario Biruta = new Usuario(1, "Biruta", "1232", "biruta@gmail.com", "mili", "Fornecedor");

        UsuarioDAO repositorioUsuario = new UsuarioDAO();

        repositorioUsuario.createTable();
        repositorioUsuario.insert(Biruta);
    }
}