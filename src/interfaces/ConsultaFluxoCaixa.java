package interfaces;

import java.time.LocalDate;

public interface ConsultaFluxoCaixa {
    double calcularTotalReceitas(LocalDate inicio, LocalDate fim);
    double calcularTotalDespesas(LocalDate inicio, LocalDate fim);
    double calcularSaldo(LocalDate inicio, LocalDate fim);
}