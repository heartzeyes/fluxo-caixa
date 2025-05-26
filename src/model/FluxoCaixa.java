package model;

import java.util.*;
import java.time.LocalDate;

public class FluxoCaixa {
    private List<Receita> receitas = new ArrayList<>();
    private List<Despesa> despesas = new ArrayList<>();

    // Getter para receitas
    public List<Receita> getReceitas() {
        return receitas;
    }

    // Setter para receitas
    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    // Getter para despesas
    public List<Despesa> getDespesas() {
        return despesas;
    }

    // Setter para despesas
    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    // Método para adicionar Receita
    public void adicionarReceita(Receita receita) {
        receitas.add(receita);
    }

    // Método para adicionar Despesa
    public void adicionarDespesa(Despesa despesa) {
        despesas.add(despesa);
    }

    // Calcular total de receitas no período
    public double calcularTotalReceitas(LocalDate inicio, LocalDate fim) {
        return receitas.stream()
                .filter(r -> !r.getData().isBefore(inicio) && !r.getData().isAfter(fim))
                .mapToDouble(Receita::getValor)
                .sum();
    }

    // Calcular total de despesas no período
    public double calcularTotalDespesas(LocalDate inicio, LocalDate fim) {
        return despesas.stream()
                .filter(d -> !d.getData().isBefore(inicio) && !d.getData().isAfter(fim))
                .mapToDouble(Despesa::getValor)
                .sum();
    }

    // Calcular saldo no período
    public double calcularSaldo(LocalDate inicio, LocalDate fim) {
        return calcularTotalReceitas(inicio, fim) - calcularTotalDespesas(inicio, fim);
    }
}
