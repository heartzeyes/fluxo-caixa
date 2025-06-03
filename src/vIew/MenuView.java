package vIew;

class MenuView {
    public static void exibirMenuPrincipal() {
        while (true) {
            System.out.println("========== MENU PRINCIPAL ==========");
            System.out.println("1. Gerenciar Categorias Financeiras");
            System.out.println("2. Gerenciar Parceiros de Negócio");
            System.out.println("3. Gerenciar Despesas");
            System.out.println("4. Gerenciar Receitas");
            System.out.println("5. Relatórios");
            System.out.println("0. Sair");
            System.out.println("====================================");
            System.out.print("Escolha uma opção: ");

            String opcao = Main.getScanner().nextLine();
            switch (opcao) {
                case "1": CategoriaFinanceiraView.exibirMenu(); break;
                case "2": ParceiroNegocioView.exibirMenu(); break;
                case "3": DespesaView.exibirMenu(); break;
                case "4": ReceitaView.exibirMenu(); break;
                case "5": RelatorioView.exibirMenu(); break;
                case "0": System.out.println("Encerrando..."); return;
                default: System.out.println("Opção inválida.");
            }
        }
    }
}
