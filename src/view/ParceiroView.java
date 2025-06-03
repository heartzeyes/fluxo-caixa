package view;

import dao.parceironegocio.ParceiroNegocioDAO;
import dao.parceironegocio.ParceiroNegocioImpl;
import model.ParceiroNegocio;

public class ParceiroView {
    public static void exibirMenu() {
        ParceiroNegocioDAO dao = new ParceiroNegocioImpl();
        while (true) {
            System.out.println("===== PARCEIROS DE NEGÓCIO =====");
            System.out.println("1. Cadastrar novo parceiro");
            System.out.println("2. Listar parceiros");
            System.out.println("3. Atualizar parceiro");
            System.out.println("4. Remover parceiro");
            System.out.println("0. Voltar");
            System.out.print("Escolha uma opção: ");
            String opcao = Main.getScanner().nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("ID: ");
                    int id = Integer.parseInt(Main.getScanner().nextLine());
                    System.out.print("Tipo: ");
                    String tipo = Main.getScanner().nextLine();
                    System.out.print("Identificador Fiscal: ");
                    String cpfcnpj = Main.getScanner().nextLine();
                    System.out.print("Nome: ");
                    String nome = Main.getScanner().nextLine();
                    dao.insert(new ParceiroNegocio(id, tipo, cpfcnpj, nome));
                    break;
                case "2":
                    dao.getAll().forEach(System.out::println);
                    break;
                case "3":
                    System.out.print("ID: ");
                    id = Integer.parseInt(Main.getScanner().nextLine());
                    System.out.print("Novo tipo: ");
                    tipo = Main.getScanner().nextLine();
                    System.out.print("Novo identificador fiscal: ");
                    cpfcnpj = Main.getScanner().nextLine();
                    System.out.print("Novo nome: ");
                    nome = Main.getScanner().nextLine();
                    dao.update(new ParceiroNegocio(id, tipo, cpfcnpj, nome));
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
