package dao.categoriafinanceira;

import model.CategoriaFinanceira;
import java.util.List;

public interface CategoriaFinanceiraDAO {
    List<CategoriaFinanceira> getAll();
    CategoriaFinanceira getById(int id);
    void insert(CategoriaFinanceira categoriaFinanceira);
    void update(CategoriaFinanceira categoriaFinanceira);
    void deleteById(int id);
}
