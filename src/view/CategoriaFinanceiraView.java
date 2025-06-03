package view;

import dao.categoriafinanceira.CategoriaFinanceiraDAO;
import dao.categoriafinanceira.CategoriaFinanceiraImpl;
import model.CategoriaFinanceira;

public class CategoriaFinanceiraView {
    public static void exibirMenu() {
        CategoriaFinanceiraDAO dao = new CategoriaFinanceiraImpl();
        while (true) {
            System.out.println("===== CATEGORIAS FINANCEIRAS =====");
            System.out.println("1. Cadastrar nova categoria");
            System.out.println("2. Listar categorias");
            System.out.println("3. Atualizar categoria");
            System.out.println("4. Remover categoria");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            String opcao = Main.getScanner().nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Nome: ");
                    String nome = Main.getScanner().nextLine();
                    System.out.print("Descrição: ");
                    String desc = Main.getScanner().nextLine();
                    dao.insert(new CategoriaFinanceira(0, nome, desc));
                    break;
                case "2":
                    dao.getAll().forEach(System.out::println);
                    break;
                case "3":
                    System.out.print("ID: ");
                    int id = Integer.parseInt(Main.getScanner().nextLine());
                    System.out.print("Novo nome: ");
                    nome = Main.getScanner().nextLine();
                    System.out.print("Nova descrição: ");
                    desc = Main.getScanner().nextLine();
                    dao.update(new CategoriaFinanceira(id, nome, desc));
                    break;
                case "4":
                    System.out.print("ID: ");
                    id = Integer.parseInt(Main.getScanner().nextLine());
                    dao.deleteById(id);
                    break;
                case "0": return;
                default: System.out.println("Opção inválida.");
            }
        }
    }
}
