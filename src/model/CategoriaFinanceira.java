package model;

public class CategoriaFinanceira {
    private int id;
    private String nome;

    public CategoriaFinanceira(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Getter para id
    public int getId() {
        return id;
    }

    // Setter para id
    public void setId(int id) {
        this.id = id;
    }

    // Getter para nome
    public String getNome() {
        return nome;
    }

    // Setter para nome
    public void setNome(String nome) {
        this.nome = nome;
    }
}
