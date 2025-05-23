import java.time.LocalDate;
import model.*;

public class Principal {
    public static void main(String[] args) {
        // Criando uma categoria
        CategoriaFinanceira categoria = new CategoriaFinanceira(1, "Vendas");

        // Criando um parceiro de negócio (cliente)
        ParceiroNegocio cliente = new ParceiroNegocio(1, "Empresa XYZ", "12345678900", "cliente");

        // Criando uma receita
        Receita receita = new Receita(
                1, 
                1500.00, 
                LocalDate.now(), 
                "Venda de Produto", 
                categoria, 
                cliente, 
                "liquidado"
        );

        // Criando uma despesa
        CategoriaFinanceira catDespesa = new CategoriaFinanceira(2, "Manutenção");
        ParceiroNegocio fornecedor = new ParceiroNegocio(2, "Fornecedor ABC", "98765432100", "fornecedor");
        
        Despesa despesa = new Despesa(
                2, 
                500.00, 
                LocalDate.now(), 
                "Reparo na máquina", 
                catDespesa, 
                fornecedor, 
                "em aberto",
                false
        );

        // Criando fluxo de caixa
        FluxoCaixa fluxo = new FluxoCaixa();
        fluxo.adicionarReceita(receita);
        fluxo.adicionarDespesa(despesa);

        // Consultando fluxo de caixa
        LocalDate inicio = LocalDate.now().minusDays(30);
        LocalDate fim = LocalDate.now();

        double totalReceitas = fluxo.calcularTotalReceitas(inicio, fim);
        double totalDespesas = fluxo.calcularTotalDespesas(inicio, fim);

        System.out.println("Total Receitas: R$ " + totalReceitas);
        System.out.println("Total Despesas: R$ " + totalDespesas);
    }
}
