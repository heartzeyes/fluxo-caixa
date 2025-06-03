package dao.despesa;

import model.Despesa;

import java.time.LocalDate;
import java.util.List;

public interface DespesaDAO {
    List<Despesa> getAll();
    List<Despesa> getByPeriodo(LocalDate dataInicio, LocalDate dataFim);
    List<Despesa> getByParceiro(int id);
    void insert(Despesa despesa);
    void update(Despesa despesa);
    void deleteById(int id);
}
