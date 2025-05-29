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

    public int getId() {
        return id;
    }

    public double getValor() {
        return valor;
    }

    public LocalDate getData() {
        return data;
    }

    public String getDescricao() {
        return descricao;
    }

    public CategoriaFinanceira getCategoria() {
        return categoria;
    }

    public ParceiroNegocio getParceiro() {
        return parceiro;
    }

    public String getStatus() {
        return status;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCategoria(CategoriaFinanceira categoria) {
        this.categoria = categoria;
    }

    public void setParceiro(ParceiroNegocio parceiro) {
        this.parceiro = parceiro;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
