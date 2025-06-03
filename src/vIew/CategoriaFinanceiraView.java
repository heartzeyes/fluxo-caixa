package vIew;

import dao.categoriafinanceira.CategoriaFinanceiraDAO;
import dao.categoriafinanceira.CategoriaFinanceiraImpl;

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
                case "1": /* cadastrar */ break;
                case "2": /* listar */ break;
                case "3": /* atualizar */ break;
                case "4": /* remover */ break;
                case "0": return;
                default: System.out.println("Opção inválida.");
            }
        }
    }
}
