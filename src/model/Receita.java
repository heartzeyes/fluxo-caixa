package model;

import java.time.LocalDate;

public class Receita extends TransacaoFinanceira {
    public Receita(int id, double valor, LocalDate data, String descricao,
                   CategoriaFinanceira categoria, ParceiroNegocio parceiro, String status) {
        super(id, valor, data, descricao, categoria, parceiro, status);
    }
}