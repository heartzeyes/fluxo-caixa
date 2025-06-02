package interfaces;

public interface RelatorioFinanceiroInterface
 {
    void gerarRelatorioMensal(java.time.LocalDate mes);
    void gerarRelatorioPorCategoria(String categoria);
    void exportarParaPDF(String caminho);
}