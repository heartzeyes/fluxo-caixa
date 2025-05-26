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

    // Getters
    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getTipo() {
        return tipo;
    }

    // Setters
    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
