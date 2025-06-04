package view;

import dao.usuario.UsuarioDAO;
import dao.usuario.UsuarioImpl;
import model.Usuario;

import java.util.List;

class LoginView {
    public static boolean realizarLogin() {
        UsuarioDAO usuarioDAO = new UsuarioImpl();
        while (true) {
            System.out.println("=========== LOGIN ===========");
            System.out.println("1. Entrar");
            System.out.println("2. Cadastrar novo usuário");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = Main.getScanner().nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("Email: ");
                    String email = Main.getScanner().nextLine();
                    System.out.print("Senha: ");
                    String senha = Main.getScanner().nextLine();
                    List<Usuario> usuarios = usuarioDAO.getAll();
                    for (Usuario u : usuarios) {
                        if (u.autenticar(email, senha)) {
                            Main.setUsuarioLogado(u);
                            return true;
                        }
                    }
                    System.out.println("Credenciais inválidas.");
                    break;
                case "2":
                    System.out.print("Nome: ");
                    String nome = Main.getScanner().nextLine();
                    System.out.print("Email: ");
                    String novoEmail = Main.getScanner().nextLine();
                    System.out.print("Senha: ");
                    String novaSenha = Main.getScanner().nextLine();
                    usuarioDAO.insert(new Usuario(nome, novoEmail, novaSenha));
                    break;
                case "0":
                    return false;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}