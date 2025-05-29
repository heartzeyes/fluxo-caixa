package model;

import java.time.LocalDate;

public class Despesa extends TransacaoFinanceira {
    private boolean paga;

    public Despesa(int id, double valor, LocalDate data, String descricao,
                   CategoriaFinanceira categoria, ParceiroNegocio parceiro, String status, boolean paga) {
        super(id, valor, data, descricao, categoria, parceiro, status);
        this.paga = paga;
    }

    public double getValor() {
        return this.valor;
    }

    public LocalDate getData() {
        return this.data;
    }

    public boolean isPaga() {
        return paga;
    }

    public void setPaga(boolean paga) {
        this.paga = paga;
    }

    public void pagar() {
        this.paga = true;
        this.status = "liquidado";
    }

    public String getDescricao(){
        return this.descricao;
    }

    public void setDescricao(String descricao){
        this.descricao = descricao;
    }
}
