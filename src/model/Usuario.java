package model;

public class Usuario extends Pessoa {
    private String email;
    private String senha;
    private String tipo;

    public Usuario(int id, String nome, String identificacaoFiscal, String email, String senha, String tipo) {
        super(id, nome, identificacaoFiscal);
        this.email = email;
        this.senha = senha;
        this.tipo = tipo;
    }

    public boolean autenticar(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }
}