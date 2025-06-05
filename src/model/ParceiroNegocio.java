package model;

public class ParceiroNegocio extends Pessoa {
    private String tipo;
    protected String identificacaoFiscal;

    public ParceiroNegocio(int id, String nome, String identificacaoFiscal, String tipo) {
        super(nome);
        this.id = id;
        this.tipo = tipo;
        this.identificacaoFiscal = identificacaoFiscal;
    }

    public String getTipo(){
        return this.tipo;
    }

    public void setTipo(String tipo){
        this.tipo = tipo;
    }

    public String getIdentificacaoFiscal(){
        return this.identificacaoFiscal;
    }
    public void setIdentificacaoFiscal(String identificacaoFiscal){
        this.identificacaoFiscal = identificacaoFiscal;
    }
    @Override
    public String toString() {
        return String.format(
            "Parceiro de Negócio:\n" +
            "id=%d\n" +
            "nome='%s'\n" +
            "identificacaoFiscal='%s'\n" +
            "tipo='%s'",
            getId(),
            getNome(),
            identificacaoFiscal,
            tipo
        );
    }
}