package view;

import dao.DatabaseManager;
import model.Usuario;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Usuario usuarioLogado;

    public static void main(String[] args) {
        DatabaseManager.createAllTables();
        if (LoginView.realizarLogin()) {
            MenuView.exibirMenuPrincipal();
        } else {
            System.out.println("Acesso negado.");
        }
    }

    public static Scanner getScanner() {
        return scanner;
    }

    public static void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }

    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
}