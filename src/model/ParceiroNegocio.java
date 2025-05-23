package model;

public class ParceiroNegocio extends Pessoa {
    private String tipo;

    public ParceiroNegocio(int id, String nome, String identificacaoFiscal, String tipo) {
        super(id, nome, identificacaoFiscal);
        this.tipo = tipo;
    }
}