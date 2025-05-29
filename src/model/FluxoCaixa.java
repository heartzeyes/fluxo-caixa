package model;

import java.time.LocalDate;
import java.util.*;

public class FluxoCaixa {
    private List<Receita> receitas = new ArrayList<>();
    private List<Despesa> despesas = new ArrayList<>();

    public List<Receita> getReceitas() {
        return receitas;
    }

    public void setReceitas(List<Receita> receitas) {
        this.receitas = receitas;
    }

    public List<Despesa> getDespesas() {
        return despesas;
    }

    public void setDespesas(List<Despesa> despesas) {
        this.despesas = despesas;
    }

    public void adicionarReceita(Receita receita) {
        receitas.add(receita);
    }

    public void adicionarDespesa(Despesa despesa) {
        despesas.add(despesa);
    }

    public double calcularTotalReceitas(LocalDate inicio, LocalDate fim) {
        return receitas.stream()
                .filter(r -> !r.getData().isBefore(inicio) && !r.getData().isAfter(fim))
                .mapToDouble(Receita::getValor)
                .sum();
    }

    public double calcularTotalDespesas(LocalDate inicio, LocalDate fim) {
        return despesas.stream()
                .filter(d -> !d.getData().isBefore(inicio) && !d.getData().isAfter(fim))
                .mapToDouble(Despesa::getValor)
                .sum();
    }

    public double calcularSaldo(LocalDate inicio, LocalDate fim) {
        return calcularTotalReceitas(inicio, fim) - calcularTotalDespesas(inicio, fim);
    }
}
