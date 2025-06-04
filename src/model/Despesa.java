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

    @Override
    public String toString() {
        return String.format("Despesa: id=%d, valor=%.2f, data=%s, descricao='%s', categoria='%s', parceiro='%s', status='%s', paga=%s", 
                           getId(), valor, data, descricao, 
                           categoria != null ? categoria.getNome() : "N/A",
                           parceiro != null ? parceiro.getNome() : "N/A",
                           status, paga ? "Sim" : "Não");
    }
}
