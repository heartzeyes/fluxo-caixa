package view;

import dao.categoriafinanceira.CategoriaFinanceiraDAO;
import dao.categoriafinanceira.CategoriaFinanceiraImpl;
import dao.despesa.DespesaDAO;
import dao.despesa.DespesaImpl;
import dao.parceironegocio.ParceiroNegocioDAO;
import dao.parceironegocio.ParceiroNegocioImpl;
import model.Despesa;

import java.time.LocalDate;

public class DespesaView {
    public static void exibirMenu() {
        DespesaDAO dao = new DespesaImpl();
        CategoriaFinanceiraDAO categoriaFinanceiraDAO= new CategoriaFinanceiraImpl();
        ParceiroNegocioDAO parceiroNegocioDAO = new ParceiroNegocioImpl();

        while (true) {
            System.out.println("============ DESPESAS ============");
            System.out.println("1. Registrar nova despesa");
            System.out.println("2. Listar despesas");
            System.out.println("3. Atualizar despesa");
            System.out.println("4. Remover despesa");
            System.out.println("5. Buscar por parceiro");
            System.out.println("6. Buscar por período");
            System.out.println("7. Marcar como paga");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            String opcao = Main.getScanner().nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Valor: ");
                    double valor = Double.parseDouble(Main.getScanner().nextLine());
                    System.out.print("Data (YYYY-MM-DD): ");
                    LocalDate data = LocalDate.parse(Main.getScanner().nextLine());
                    System.out.print("Descrição: ");
                    String descricao = Main.getScanner().nextLine();
                    System.out.print("ID Categoria: ");
                    int catId = Integer.parseInt(Main.getScanner().nextLine());
                    System.out.print("ID Parceiro: ");
                    int parceiroId = Integer.parseInt(Main.getScanner().nextLine());
                    System.out.print("Status: ");
                    String status = Main.getScanner().nextLine();
                    System.out.print("Paga (true/false): ");
                    boolean paga = Boolean.parseBoolean(Main.getScanner().nextLine());

                    var categoriaFinanceira = categoriaFinanceiraDAO.getById(catId);
                    var parceiro = parceiroNegocioDAO.getById(parceiroId);

                    dao.insert(new Despesa(0, valor, data, descricao, categoriaFinanceira, parceiro, status, paga));
                    break;
                case "2": dao.getAll().forEach(System.out::println); break;
                case "3":
                    System.out.print("ID: ");
                    int id = Integer.parseInt(Main.getScanner().nextLine());
                    System.out.print("Novo valor: ");
                    valor = Double.parseDouble(Main.getScanner().nextLine());
                    System.out.print("Nova data (YYYY-MM-DD): ");
                    data = LocalDate.parse(Main.getScanner().nextLine());
                    System.out.print("Nova descrição: ");
                    descricao = Main.getScanner().nextLine();
                    System.out.print("Nova categoria: ");
                    catId = Integer.parseInt(Main.getScanner().nextLine());
                    System.out.print("Novo parceiro: ");
                    parceiroId = Integer.parseInt(Main.getScanner().nextLine());
                    System.out.print("Novo status: ");
                    status = Main.getScanner().nextLine();
                    System.out.print("Paga (true/false): ");
                    paga = Boolean.parseBoolean(Main.getScanner().nextLine());

                    var categoriaFinanceiraUpdate = categoriaFinanceiraDAO.getById(catId);
                    var parceiroUpdate = parceiroNegocioDAO.getById(parceiroId);

                    dao.update(new Despesa(id, valor, data, descricao, categoriaFinanceiraUpdate, parceiroUpdate, status, paga));
                    break;
                case "4":
                    System.out.print("ID: ");
                    id = Integer.parseInt(Main.getScanner().nextLine());
                    dao.deleteById(id);
                    break;
                case "5":
                    System.out.print("ID do parceiro: ");
                    parceiroId = Integer.parseInt(Main.getScanner().nextLine());
                    dao.getByParceiro(parceiroId).forEach(System.out::println);
                    break;
                case "6":
                    System.out.print("Data início (YYYY-MM-DD): ");
                    LocalDate ini = LocalDate.parse(Main.getScanner().nextLine());
                    System.out.print("Data fim (YYYY-MM-DD): ");
                    LocalDate fim = LocalDate.parse(Main.getScanner().nextLine());
                    dao.getByPeriodo(ini, fim).forEach(System.out::println);
                    break;
                case "7":
                    System.out.print("ID da despesa a marcar como paga: ");
                    id = Integer.parseInt(Main.getScanner().nextLine());
                    Despesa d = dao.getAll().stream().filter(x -> x.getId() == id).findFirst().orElse(null);
                    if (d != null) {
                        d.setPaga(true);
                        dao.update(d);
                        System.out.println("Despesa marcada como paga.");
                    } else {
                        System.out.println("Despesa não encontrada.");
                    }
                    break;
                case "0": return;
                default: System.out.println("Opção inválida.");
            }
        }
    }
}
