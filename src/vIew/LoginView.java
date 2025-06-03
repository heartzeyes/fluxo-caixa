package vIew;

import dao.usuario.UsuarioDAO;
import dao.usuario.UsuarioImpl;
import model.Usuario;

import java.util.List;

class LoginView {
    public static boolean realizarLogin() {
        System.out.println("=========== LOGIN ===========");
        System.out.print("Email: ");
        String email = Main.getScanner().nextLine();
        System.out.print("Senha: ");
        String senha = Main.getScanner().nextLine();

        UsuarioDAO usuarioDAO = new UsuarioImpl();
        List<Usuario> usuarios = usuarioDAO.getAll();

        for (Usuario u : usuarios) {
            if (u.autenticar(email, senha)) {
                Main.setUsuarioLogado(u);
                return true;
            }
        }
        return false;
    }
}