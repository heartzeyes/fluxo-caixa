package model;

import java.util.*;
import java.time.LocalDate;

public class FluxoCaixa {
    private List<Receita> receitas = new ArrayList<>();
    private List<Despesa> despesas = new ArrayList<>();

    public void adicionarReceita(Receita receita) {
        receitas.add(receita);
    }

    public void adicionarDespesa(Despesa despesa) {
        despesas.add(despesa);
    }

    public double calcularTotalReceitas(LocalDate inicio, LocalDate fim) {
        return receitas.stream()
                .filter(r -> !r.data.isBefore(inicio) && !r.data.isAfter(fim))
                .mapToDouble(r -> r.valor)
                .sum();
    }

    public double calcularTotalDespesas(LocalDate inicio, LocalDate fim) {
        return despesas.stream()
                .filter(d -> !d.data.isBefore(inicio) && !d.data.isAfter(fim))
                .mapToDouble(d -> d.valor)
                .sum();
    }

    public double calcularSaldo(LocalDate inicio, LocalDate fim) {
        return calcularTotalReceitas(inicio, fim) - calcularTotalDespesas(inicio, fim);
    }
}