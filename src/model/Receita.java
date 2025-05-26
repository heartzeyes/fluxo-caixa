package model;

import java.time.LocalDate;

public class Receita extends TransacaoFinanceira {
    public Receita(int id, double valor, LocalDate data, String descricao,
                   CategoriaFinanceira categoria, ParceiroNegocio parceiro, String status) {
        super(id, valor, data, descricao, categoria, parceiro, status);
    }

    // Getter para valor
    public double getValor() {
        return this.valor;
    }

    // Getter para data
    public LocalDate getData() {
        return this.data;
    }
}
