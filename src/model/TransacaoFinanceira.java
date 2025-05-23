package model;

import java.time.LocalDate;

public abstract class TransacaoFinanceira {
    protected int id;
    protected double valor;
    protected LocalDate data;
    protected String descricao;
    protected CategoriaFinanceira categoria;
    protected ParceiroNegocio parceiro;
    protected String status;

    public TransacaoFinanceira(int id, double valor, LocalDate data, String descricao,
                               CategoriaFinanceira categoria, ParceiroNegocio parceiro, String status) {
        this.id = id;
        this.valor = valor;
        this.data = data;
        this.descricao = descricao;
        this.categoria = categoria;
        this.parceiro = parceiro;
        this.status = status;
    }

    public boolean isLiquidada() {
        return "liquidado".equalsIgnoreCase(status);
    }
}