package interfaces;

public interface RelatorioFinanceiro {
    void gerarRelatorioMensal(java.time.LocalDate mes);
    void gerarRelatorioPorCategoria(String categoria);
    void exportarParaPDF(String caminho);
}