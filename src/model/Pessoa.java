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

    public void setId(int id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setIdentificacaoFiscal(String identificacaoFisca){
        this.identificacaoFiscal = identificacaoFisca;
    }

    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public String getIdentificacaoFiscal(){
        return this.identificacaoFiscal;
    }
}