package dao.receita;
import model.Despesa;
import model.Receita;

import java.time.LocalDate;
import java.util.List;

public interface ReceitaDAO {
    List<Receita> getAll();
    List<Receita> getByPeriodo(LocalDate dataInicio, LocalDate dataFim);
    Receita getByParceiro(int id);
    void insert(Receita receita);
    void update(Receita receita);
    void deleteById(int id);
}
