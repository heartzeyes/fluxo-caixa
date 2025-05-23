package model;

public abstract class Pessoa {
    protected int id;
    protected String nome;
    protected String identificacaoFiscal;

    public Pessoa(int id, String nome, String identificacaoFiscal) {
        this.id = id;
        this.nome = nome;
        this.identificacaoFiscal = identificacaoFiscal;
    }

    // Getters e Setters
}