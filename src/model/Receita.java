package model;

import java.time.LocalDate;

public class Receita extends TransacaoFinanceira {
    public Receita(int id, double valor, LocalDate data, String descricao,
                   CategoriaFinanceira categoria, ParceiroNegocio parceiro, String status) {
        super(id, valor, data, descricao, categoria, parceiro, status);
    }

    public double getValor() {
        return this.valor;
    }

    public LocalDate getData() {
        return this.data;
    }
    @Override
    public String toString() {
        return String.format("Receita: id=%d, valor=%.2f, data=%s, descricao='%s', categoria='%s', parceiro='%s', status='%s'", 
                           getId(), valor, data, descricao,
                           categoria != null ? categoria.getNome() : "N/A",
                           parceiro != null ? parceiro.getNome() : "N/A",
                           status);
    }
}
