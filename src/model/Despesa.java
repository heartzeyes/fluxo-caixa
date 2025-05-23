package model;

import java.time.LocalDate;

public class Despesa extends TransacaoFinanceira {
    private boolean paga;

    public Despesa(int id, double valor, LocalDate data, String descricao,
                   CategoriaFinanceira categoria, ParceiroNegocio parceiro, String status, boolean paga) {
        super(id, valor, data, descricao, categoria, parceiro, status);
        this.paga = paga;
    }

    public boolean isPaga() {
        return paga;
    }

    public void pagar() {
        this.paga = true;
        this.status = "liquidado";
    }
}